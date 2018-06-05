package com.demo.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.demo.entity.FileData;
import com.demo.request.UserInfo;
import com.demo.service.FileUploadService;
import com.demo.util.Constants;
import com.demo.util.FileReaderHelper;
import com.demo.util.TimestampHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by nikhil on 6/2/2018.
 */

@RestController
@Slf4j
public class FileUploadController {

	private static String UPLOADED_FOLDER = "D://xlsupload//";

	@Autowired
	private FileUploadService uploadService;
	
	@Autowired
	private FileReaderHelper fileReader;  

	@PostMapping("/uploadFile")
	public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file,@RequestParam("type") String type) {
		log.info("enter FileUploadController.uploadXlsFile{}{}",file,type);
		UserInfo userInfo=new UserInfo();
		userInfo.setUserName("admin");
		userInfo.setPassword("admin123");
		if (validateUser(userInfo)) {
			if (!file.isEmpty()) {
				try {
					byte[] fileData = file.getBytes();
					String filePaht=UPLOADED_FOLDER+file.getOriginalFilename();
					System.out.println("file path is :"+filePaht);
					saveUploadedFiles(fileData,file.getOriginalFilename());
					fileReader.validateFile(UPLOADED_FOLDER+file.getOriginalFilename());
					uploadService.saveXlsFile(
							prepareXlsDataEntity(fileData, file.getOriginalFilename(), type, userInfo.getUserName()));
				} catch (IOException e) {
					e.printStackTrace();
					log.error("error in uploading file, FileUploadController.uploadXlsFile {}", e);
					return new ResponseEntity<String>("unable to upload file", HttpStatus.BAD_REQUEST);
				}
			}
		}
		log.error("exit FileUploadController.uploadXlsFile ");
		return new ResponseEntity<String>("uploaded sucessfully", HttpStatus.OK);
	}

	private FileData prepareXlsDataEntity(byte[] fileData, String fileName, String fileType, String userName)
			throws IOException {
		FileData dataEntity = null;
		dataEntity = new FileData();
		dataEntity.setUserName(userName);
		dataEntity.setFileName(fileName);
		dataEntity.setStatus(Constants.STATUS_CREATED);
		dataEntity.setUpdatedBy(userName);
		dataEntity.setFileName(fileName);
		dataEntity.setType(fileType);
		dataEntity.setUpdatedTime(TimestampHelper.getCurrentTimestamp());
		dataEntity.setCompletedTime(TimestampHelper.getCurrentTimestamp());
		dataEntity.setData(fileData);
		return dataEntity;
	}

	private boolean validateUser(UserInfo userInfo) {
		return (userInfo.getUserName().equalsIgnoreCase("admin") && userInfo.getPassword().equalsIgnoreCase("admin123"))
				? true : false;
	}

	private boolean saveUploadedFiles(byte[] bytes,String fileName) throws IOException {
		Path path = Paths.get(UPLOADED_FOLDER + fileName);
		Files.createDirectories(path.getParent());
		Files.write(path, bytes);
		return true;
	}
}
