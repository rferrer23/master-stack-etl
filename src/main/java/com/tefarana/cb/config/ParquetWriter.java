package com.tefarana.cb.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericData.Record;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.parquet.avro.AvroParquetReader;
import org.apache.parquet.avro.AvroParquetWriter;
import org.apache.parquet.avro.AvroParquetWriter.Builder;
import org.apache.parquet.hadoop.ParquetFileWriter;
import org.apache.parquet.hadoop.ParquetReader;
import org.apache.parquet.hadoop.metadata.CompressionCodecName;
import org.apache.parquet.hadoop.metadata.FileMetaData;
import org.apache.parquet.hadoop.util.HadoopInputFile;
import org.apache.parquet.hadoop.util.HiddenFileFilter;
import org.apache.parquet.tools.Main;
import org.springframework.stereotype.Service;

@Service
public class ParquetWriter {
    private final Schema SCHEMA;
    private Path OUT_PATH ;
	private Configuration conf;
	private ApplicationProperties props;
    private static final int MAX_FILE_NUM = 100;
    private static final long TOO_SMALL_FILE_THRESHOLD = 64 * 1024 * 1024;
    
    static {
    	try {
    	    Main.out = System.out;
    	    Main.err = System.err;

    	    PrintStream VoidStream = new PrintStream(new OutputStream() {
    	      @Override
    	      public void write(int b) throws IOException {}
    	      @Override
    	      public void write(byte[] b) throws IOException {}
    	      @Override
    	      public void write(byte[] b, int off, int len) throws IOException {}
    	      @Override
    	      public void flush() throws IOException {}
    	      @Override
    	      public void close() throws IOException {}
    	    });
    	    System.setOut(VoidStream);
    	    System.setErr(VoidStream);
    	}catch(Throwable e) {
    		// va a hacer un die
    	}
    	
    }
    
    public ParquetWriter(ApplicationProperties props) {
    	OUT_PATH=getPath(props);
    	this.props=props;
        try (InputStream inStream = new FileInputStream(props.getSchema())) {
            SCHEMA = new Schema.Parser().parse(IOUtils.toString(inStream, "UTF-8"));
            conf = new Configuration();
        } catch (IOException e) {
            throw new RuntimeException("Can't read SCHEMA file from" + props.getSchema(), e);
        }
    }

	private Path getPath(ApplicationProperties props) {
		return new Path(props.getFile());
	}
	private Path getPath(ApplicationProperties props,String id) {
		String ruta = props.getFile();
		String file =ruta.substring(0, ruta.lastIndexOf("/")+1) ;
		String name =ruta.substring(ruta.lastIndexOf("/")+1,ruta.length()) ;
		return new Path(file+id+name);
	}
	
	public Path getPath(String id) {
		String ruta = props.getFile();
		String file =ruta.substring(0, ruta.lastIndexOf("/")+1) ;
		String name =ruta.substring(ruta.lastIndexOf("/")+1,ruta.length()) ;
		return new Path(file+id+name);
	}
    
    public WriterMap getSchema() {
        return new WriterMap(SCHEMA);
    }
    
    public List<Path> getDir(String queryName){
    	java.io.File f = new java.io.File(props.getFile().substring(0, props.getFile().lastIndexOf('/')));
    	java.io.File[] matchingFiles = f.listFiles(new java.io.FilenameFilter() {
		    public boolean accept(java.io.File dir, String name) {
		        return name.contains(queryName) && name.endsWith("parquet");
		    }
		});
    	return Arrays.stream(matchingFiles).map(n->new Path(n.getPath())).collect(Collectors.toList());
    }
    
    @SuppressWarnings("unchecked")
    public List<Record> readFromParquetL(String id) throws IOException {
    	Path filePathToRead=getPath(props,id);

    	List<GenericData.Record> sampleData = new ArrayList<>();
        try (ParquetReader<GenericData.Record> reader = AvroParquetReader
                .<GenericData.Record>builder(filePathToRead)
                .withConf(new Configuration())
                .build()) {

            GenericData.Record record;
            while ((record = reader.read()) != null) {
            	sampleData.add(record);
            }
            return sampleData;
        }
    }
    
    public void writeToParquet(List<GenericData.Record> recordsToWrite,String id) throws IOException {
    	Path filePathToRead=getPath(props,id);
        Builder<Record> builder = AvroParquetWriter
                .<GenericData.Record>builder(filePathToRead);
		Builder<Record> buildWithValues = builder
                .withSchema(SCHEMA)
                .withConf(new Configuration())
                .withCompressionCodec(CompressionCodecName.SNAPPY);
		try ( org.apache.parquet.hadoop.ParquetWriter<Record> writer = buildWithValues
                .build()) {

            for (GenericData.Record record : recordsToWrite) {
                writer.write(record);
            }
        }
    }
    
    public void mergeFiles(List<Path> inputFiles, Path outputFile) throws IOException {
        // Merge schema and extraMeta
        FileMetaData mergedMeta = mergedMetadata(inputFiles);
        PrintWriter out = new PrintWriter(Main.out, true);

        // Merge data
        try {
            ParquetFileWriter writer = new ParquetFileWriter(conf,
                    mergedMeta.getSchema(), outputFile, ParquetFileWriter.Mode.CREATE);
            writer.start();
            boolean tooSmallFilesMerged = false;
            for (Path input: inputFiles) {
              if (input.getFileSystem(conf).getFileStatus(input).getLen() < TOO_SMALL_FILE_THRESHOLD) {
                out.format("Warning: file %s is too small, length: %d\n",
                  input,
                  input.getFileSystem(conf).getFileStatus(input).getLen());
                tooSmallFilesMerged = true;
              }

              writer.appendFile(HadoopInputFile.fromPath(input, conf));
            }

            if (tooSmallFilesMerged) {
              out.println("Warning: you merged too small files. " +
                "Although the size of the merged file is bigger, it STILL contains small row groups, thus you don't have the advantage of big row groups, " +
                "which usually leads to bad query performance!");
            }
            writer.end(mergedMeta.getKeyValueMetaData());
            for (Path input: inputFiles) {
            	String string = input.toString();
    			File file = new File(string);
    			file.delete();
            }
        }catch(Exception e) {
        	System.out.print(e.getMessage());
        }


    }
    
    private FileMetaData mergedMetadata(List<Path> inputFiles) throws IOException {
        return ParquetFileWriter.mergeMetadataFiles(inputFiles, conf).getFileMetaData();
      }
    

    /**
     * Get all input files.
     * @param input input files or directory.
     * @return ordered input files.
     */
    private List<Path> getInputFiles(List<String> input) throws IOException {
      List<Path> inputFiles = null;

      if (input.size() == 1) {
        Path p = new Path(input.get(0));
        FileSystem fs = p.getFileSystem(conf);
        FileStatus status = fs.getFileStatus(p);

        if (status.isDir()) {
          inputFiles = getInputFilesFromDirectory(status);
        }
      } else {
        inputFiles = parseInputFiles(input);
      }

      checkParquetFiles(inputFiles);

      return inputFiles;
    }

    /**
     * Check input files basically.
     * ParquetFileReader will throw exception when reading an illegal parquet file.
     *
     * @param inputFiles files to be merged.
     * @throws IOException
     */
    private void checkParquetFiles(List<Path> inputFiles) throws IOException {
      if (inputFiles == null || inputFiles.size() <= 1) {
        throw new IllegalArgumentException("Not enough files to merge");
      }

      for (Path inputFile: inputFiles) {
        FileSystem fs = inputFile.getFileSystem(conf);
        FileStatus status = fs.getFileStatus(inputFile);

        if (status.isDir()) {
          throw new IllegalArgumentException("Illegal parquet file: " + inputFile.toUri());
        }
      }
    }

    /**
     * Get all parquet files under partition directory.
     * @param partitionDir partition directory.
     * @return parquet files to be merged.
     */
    private List<Path> getInputFilesFromDirectory(FileStatus partitionDir) throws IOException {
      FileSystem fs = partitionDir.getPath().getFileSystem(conf);
      FileStatus[] inputFiles = fs.listStatus(partitionDir.getPath(), HiddenFileFilter.INSTANCE);

      List<Path> input = new ArrayList<Path>();
      for (FileStatus f: inputFiles) {
        input.add(f.getPath());
      }
      return input;
    }

    private List<Path> parseInputFiles(List<String> input) {
      List<Path> inputFiles = new ArrayList<Path>();

      for (String name: input) {
        inputFiles.add(new Path(name));
      }

      return inputFiles;
    }

    
    public static class WriterMap implements Map<String ,Object>{
    	private  GenericData.Record record;
		private Schema s;
		private Map<String, Object> map = new HashMap<>();
    	public WriterMap(Schema s) {
    		record = new GenericData.Record(s);
    		this.s=s;
    	}
    	
		@Override
		public int size() {
			// TODO Auto-generated method stub
			return map.size();
		}

		@Override
		public boolean isEmpty() {
			// TODO Auto-generated method stub
			return map.isEmpty();
		}

		@Override
		public boolean containsKey(Object key) {
			// TODO Auto-generated method stub
			return map.containsKey(key);
		}

		@Override
		public boolean containsValue(Object value) {
			// TODO Auto-generated method stub
			return map.containsValue(value);
		}

		@Override
		public Object get(Object key) {
			// TODO Auto-generated method stub
			return map.get(key);
		}

		@Override
		public Object put(String key, Object value) {
			// TODO Auto-generated method stub
			return map.put(key, value);
		}

		@Override
		public Object remove(Object key) {
			// TODO Auto-generated method stub
			return map.remove(key);
		}

		@Override
		public void putAll(Map<? extends String, ? extends Object> m) {
			map.putAll(m);
			
		}

		@Override
		public void clear() {
			record = new GenericData.Record(s);
			map.clear();
			
		}

		@Override
		public Set<String> keySet() {
			// TODO Auto-generated method stub
			return map.keySet();
		}

		@Override
		public Collection<Object> values() {
			// TODO Auto-generated method stub
			return map.values();
		}

		@Override
		public Set<Entry<String, Object>> entrySet() {
			// TODO Auto-generated method stub
			return map.entrySet();
		}
		
		public GenericData.Record getGenericData(){
			if(!map.isEmpty()) {
				map.forEach((k,v)->record.put(k, v));
			}
			return record;
		}
    	
    }

}
