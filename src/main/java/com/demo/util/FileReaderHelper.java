package com.demo.util;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

@Component
public class FileReaderHelper {

	
	
	public List<String> getFileHeader(String filePath) throws IOException
	{
		
		  Reader reader = Files.newBufferedReader(Paths.get(filePath));
          CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                  .withFirstRecordAsHeader()
                  .withIgnoreHeaderCase()
                  .withTrim());
          
          List<CSVRecord> headers=csvParser.getRecords();
          System.out.println("all headers \n" + headers.get(0));
          
      /*    csvParser.getRecords().forEach(line  ->{
        	  
        	  System.out.println("data is  :"+line.get(0));
        	  
          });*/
		
		return null;
	}
	
	public boolean validateFile(String filePath) throws IOException
	{
		getFileHeader(filePath);
		return true;
	}
}
