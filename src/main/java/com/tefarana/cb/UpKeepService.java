package com.tefarana.cb;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ListIterator;

import org.apache.avro.generic.GenericData.Record;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import com.google.code.stackexchange.client.query.QuestionApiQuery;
import com.google.code.stackexchange.client.query.StackExchangeApiQueryFactory;
import com.google.code.stackexchange.common.PagedList;
import com.google.code.stackexchange.schema.Answer;
import com.google.code.stackexchange.schema.Paging;
import com.google.code.stackexchange.schema.Question;
import com.google.code.stackexchange.schema.StackExchangeSite;
import com.google.code.stackexchange.schema.TimePeriod;
import com.tefarana.cb.config.ParquetWriter;
import com.tefarana.cb.config.ParquetWriter.WriterMap;
import com.tefarana.cb.domain.StackOverFlowApiClient;
import com.tefarana.cb.repository.StackOverFlowApiClientRepository;


@Configuration
@EnableScheduling
public class UpKeepService {
	
	private static final long nextDay = 24*3600*1000L;
	@Autowired
    private  StackOverFlowApiClientRepository stackOverFlowApiClientRepository;
	@Autowired
	private ParquetWriter writer;
	
	private boolean mark=true;
	
	
    private static final Logger log = LoggerFactory.getLogger(EtlBigdataApp.class);

	@Scheduled(initialDelay  = 15000, fixedDelay = 8*3600*1000)
	@Transactional
	public void scheduleFixedDelayTask() {
		List<StackOverFlowApiClient> queries = stackOverFlowApiClientRepository.findAllWithCreationDateTimeBefore(transformNowDateToUTC());
		if(CollectionUtils.isEmpty(queries) || !mark) {
			return;
		}
		try {
			process(queries);
		}catch(Throwable t) {
			log.error("error fatal con alguna query",t);
		}
		
	}

	private void process(List<StackOverFlowApiClient> queries) {
		for(StackOverFlowApiClient query:queries)
			if(query.isActive()) {
				try {
					log.info("se inicia la query "+getIdOrBlank(query));
					runQuery(query);
				} catch (IOException e) {
					log.error("error durante el procesdo de la query",e);
				}
			}

	}

	private String getIdOrBlank(StackOverFlowApiClient query) {
		if(query==null || query.getId()==null ) {
			return "";
		}
		return Long.toString(query.getId());
	}
	
	private LocalDate transformNowDateToUTC() {
		 Date date = new Date();
	     Instant instant = date.toInstant();
	     return instant.atZone(ZoneOffset.UTC).toLocalDate();
	}

	private void runQuery(StackOverFlowApiClient queryDB) throws IOException {
		if(queryDB==null || StringUtils.isEmpty(queryDB.getSecret())) {
			return;
		}
		
		StackExchangeApiQueryFactory queryFactory = StackExchangeApiQueryFactory.newInstance(queryDB.getSecret(),StackExchangeSite.STACK_OVERFLOW);  
		 QuestionApiQuery query = queryFactory.newQuestionApiQuery();  
		 QuestionApiQuery withSort = query		 
				 .withPaging(new Paging(4, 30))    
				 .withSort(Question.SortOrder.LEAST_HOT);
		 
		 
		 Date firstPeriod = DateUtils.asDate(queryDB.getFirstPeriod());
		 Date secondPeriod = DateUtils.asDate(queryDB.getLastPeriod());
		 
		 
		 if(firstPeriod!=null && secondPeriod!=null) {
			withSort=withSort.withTimePeriod(new TimePeriod(firstPeriod, secondPeriod));
		 }else if(firstPeriod!=null) {
			 withSort= withSort.withTimePeriod(new TimePeriod(firstPeriod, new Date()));
		 }
		 if(StringUtils.isNotEmpty(queryDB.getUsers())) {
			 List<Long> users = new ArrayList<>();
			 for(String user:queryDB.getUsers().split(";")) {
				 try {
					 users.add(Long.parseLong(user));
				 }catch(Exception e) {
					 continue;
				 }
			 }
			 if(CollectionUtils.isNotEmpty(users)) {
				 withSort= withSort.withUserIds(users);
			 }
		 }
		 if(StringUtils.isNotEmpty(queryDB.getTags())) {
			 withSort= withSort.withTags(queryDB.getTags().split(";")) ;
		 }
		 
		PagedList<Question> questions = withSort.withFilter("!-*jbN-lAlw-5").list();
		queryDB.setNextSendTime(DateUtils.asLocalDate(new Date(System.currentTimeMillis()+nextDay)));
		boolean quoteEnd = false;
		List<Record> recordsToWrite = new ArrayList<>();
		
		while (questions.hasMore() && !quoteEnd) {  

			  recordsToWrite.addAll(writeResultOfQuery(questions));
			  if(questions.getBackoff()>0) {
				  queryDB.setNextSendTime(DateUtils.asLocalDate(new Date(System.currentTimeMillis()+questions.getBackoff()*1000L)));
				  return;
			  }
			  if(questions.getQuotaRemaining()==0) {
				  queryDB.setNextSendTime(DateUtils.asLocalDate(new Date(System.currentTimeMillis()+(24*3600*1000L))));
				  return;
			  }
			  if((questions.getPage() - 1)==0) {
				  return;
			  }
			  questions = query.withPaging(new Paging(questions.getPage() - 1, questions.getPageSize())).list();

		}
		String queryNameS = ""+getIdOrBlank(queryDB);
		String queryName = ""+getIdOrBlank(queryDB);
		List<Path> path = writer.getDir(queryNameS);
		if(CollectionUtils.isNotEmpty(path)) { 
			writer.mergeFiles(path, writer.getPath(queryNameS+((int)(Math.random()*1000))));
			queryName="merge"+queryNameS;
		}
		writer.writeToParquet(recordsToWrite, queryName);
	}

	private List<Record> writeResultOfQuery(PagedList<Question> questions) {
		ListIterator<Question> list = questions.listIterator();
		List<Record> recordsToWrite=new ArrayList<>();
		  while(list.hasNext()) {
			  WriterMap set = writer.getSchema();
			  Question row = list.next();
			  String key = "body";
			  set.put(key, row.getBody());
			  set.put("title", row.getTitle());
			  set.put("url", row.getQuestionUrl());
			  set.put("viewCount", row.getViewCount());
			  set.put("score", row.getScore());
			  if(row.getClosedDate()!=null) {
				  set.put("dateClose", DateUtils.dateString(row.getClosedDate()));
			  }

			  set.put("dateCreate", DateUtils.dateString(row.getCreationDate()));
			  
			  List<Record> n = new ArrayList<>();
			  List<Answer> ans = row.getAnswers();
			  if(!CollectionUtils.isEmpty(ans)) {
				  for(Answer anss:ans) {
					  Record level2Record = writer.getSchema().getGenericData();
					  level2Record.put("body", anss.getBody());
					  level2Record.put("viewCount", anss.getViewCount());
					  level2Record.put("score", anss.getScore());
					  level2Record.put("title", anss.getTitle()); 
				  }
				  set.put("lvl2_record", n);
			  }
			  
			  recordsToWrite.add(set.getGenericData());
		  }
		  return recordsToWrite;
	}

	
}