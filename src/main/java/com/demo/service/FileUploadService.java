package com.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.demo.entity.FileData;
import com.demo.repositories.FileUploadRepository;

@Component
public class FileUploadService {

	@Autowired
	public FileUploadRepository fileUploadRepository;

	public void saveXlsFile(FileData fileData) {
		fileUploadRepository.save(fileData);
	}
	
	
	/*@Autowired
	private EntityManager entityManager;
	
	public void saveXlsFile(Document document)
	{
		entityManager.persist(document);
	}*/
}
