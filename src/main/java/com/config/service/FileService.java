package com.config.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.FileData;
import com.config.repositories.FileRepository;

@Component
public class FileService {

	@Autowired
	public FileRepository fileRepository;

	public long saveFileData(FileData fileData) {
		return fileRepository.save(fileData).getID();
	}

	public void deleteFileDataById(Long fileId) {
		fileRepository.deleteById(fileId);
	}

	public FileData getFileDataById(Long fileId) {
		Optional<FileData> optionalData = fileRepository.findById(fileId);
		return optionalData.isPresent() ? optionalData.get() : null;
	}
	
	public boolean updateFileData(FileData fileData)
	{
		FileData dbFileData=getFileDataById(fileData.getID());
		if(dbFileData!=null)
		{
			dbFileData.setData(fileData.getData());
			dbFileData.setUpdatedTime(fileData.getUpdatedTime());
			dbFileData.setUpdatedBy(fileData.getUpdatedBy());
			dbFileData.setFileName(fileData.getFileName());
		 fileRepository.save(dbFileData);
		 return true;
		}
		return false;
	}
}
