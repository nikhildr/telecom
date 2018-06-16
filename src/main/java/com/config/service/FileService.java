package com.config.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.FileData;
import com.config.repositories.FileRepository;

@Component
public class FileService {

	@Autowired
	public FileRepository fileRepository;
	

	public String saveFileData(FileData fileData) {
		return fileRepository.insertFile(fileData).getId();
	}

	public void deleteFileDataById(String fileId) {
		fileRepository.deleteBookById(fileId);
	}
	
	public String updateFileData(FileData fileData) {
		Map<String, Object> response=fileRepository.updateBookById(fileData.getId(),fileData);
		if(!response.containsKey("error"))
		{
			response.get("");
		}
		 return null;
	}

	 	
	
}
