package com.config.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class CsvFileUtil {

	public List<String> getFileHeader(String filePath) throws IOException {

		BufferedReader br = new BufferedReader(new FileReader(filePath));
		List<String> fileHeaders = null;
		String header = br.readLine();
		if (header != null) {
			fileHeaders = new ArrayList<>();
			String[] columns = header.replaceAll(Constants.SPACE_REGEX, " ").split(" ");
			System.out.println(columns.toString());
			for (int i = 0; i < columns.length; i++) {
				fileHeaders.add(columns[i].toString().trim());
			}
		}
		return fileHeaders;
	}

	public boolean validateDeviceDataHeader(String filePath) throws IOException {
		List<String> fileHeaders= getFileHeader(filePath);
		return Constants.getFixedDeviceDataHeaders().containsAll(fileHeaders)?true:false;
	}
	
	public boolean validateServiceDataHeader(String filePath) throws IOException {
		List<String> fileHeaders= getFileHeader(filePath);
		return Constants.getFixedServiceDataHeaders().containsAll(fileHeaders)?true:false;
	}
	
	public static boolean saveFile(byte[] bytes, String fileName) throws IOException {
		Path path = Paths.get(Constants.FILE_DESTINATION_FOLDER + fileName);
		Files.createDirectories(path.getParent());
		Files.write(path, bytes);
		return true;
	}
}
