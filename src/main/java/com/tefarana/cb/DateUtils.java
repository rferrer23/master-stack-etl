package com.tefarana.cb;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;

public class DateUtils {

	  public static Date asDate(LocalDate localDate) {
		  if(localDate==null) {
			  return null;
		  }
		LocalTime time=LocalTime.NOON;
	    return Date.from(localDate.atTime(time).toInstant(ZoneOffset.UTC));
	  }

	  public static Date asDate(LocalDateTime localDateTime) {
		  if(localDateTime==null) {
			  return null;
		  }
	    return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	  }

	  public static LocalDate asLocalDate(Date date) {
		  if(date==null) {
			  return null;
		  }
	    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	  }

	  public static LocalDateTime asLocalDateTime(Date date) {
		  if(date==null) {
			  return null;
		  }
	    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
	  }
	  
	  public static String dateString(Date date) {
		  if(date==null) {
			  return null;
		  }
		  DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		  return  df.format(date);
	  }
	  
}