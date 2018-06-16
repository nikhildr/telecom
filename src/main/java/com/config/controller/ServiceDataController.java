package com.config.controller;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.config.entity.FileData;
import com.config.request.UserInfo;
import com.config.service.FileService;
import com.config.util.ConfigUtil;
import com.config.util.Constants;
import com.config.util.CsvFileUtil;

/**
 * Created by nikhil on 6/2/2018.
 */

@RestController
public class ServiceDataController {

	
	private static final Logger log = (Logger) LoggerFactory.getLogger(ServiceDataController.class);
	@Autowired
	private FileService fileService;

	@Autowired
	private CsvFileUtil fileUtil;

	
	@PostMapping("/config/service")
	public ResponseEntity<?> saveServiceDataFile(@RequestParam("file") MultipartFile file) {
		log.info("enter ServiceDataController.saveServiceDataFile");
		
		UserInfo userInfo = new UserInfo(); // temp
		userInfo.setUserName("admin");
		userInfo.setPassword("admin123");
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> responseEntity = null;
		if (validateUser(userInfo)) {
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					if (CsvFileUtil.saveFile(bytes, file.getOriginalFilename())) {
						if (fileUtil.validateServiceDataHeader(
								Constants.FILE_DESTINATION_FOLDER + file.getOriginalFilename())) {
							String fileDataId = fileService.saveFileData(ConfigUtil.prepareFileDataEntity(bytes,file.getOriginalFilename(), Constants.SERVICE, userInfo.getUserName()));
							response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_UPLOADED_SUCCESFULLY);
							response.put("fileId", fileDataId);
							log.info("exit ServiceDataController.saveServiceDataFile{}");
							responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPLOAD_FILE);
					log.error("exit ServiceDataController.saveServiceDataFile {}{}", response, e);
					responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.put(Constants.ERROR_MESSAGE, Constants.INVALID_USER_LOGIN);
			log.error("exit ServiceDataController.saveServiceDataFile ");
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/config/service")
	public ResponseEntity<?> getServiceFile(@RequestParam("fileId") String fileId) {
	//	fileService.getFileDataById(fileId);
		return new ResponseEntity<>("getservice",HttpStatus.OK);

	}

	@PutMapping("/config/service")
	public ResponseEntity<?> updateServiceDataFile(@RequestParam("file") MultipartFile file,
		@RequestParam("fileId") String fileId) {
		log.info("enter ServiceDataController.updateServiceDataFile{},{}", file.getOriginalFilename(), fileId);
		Map<String, Object> response = new HashMap<>();
		ResponseEntity<?> responseEntity = null;

		UserInfo userInfo = new UserInfo();
		userInfo.setUserName("admin");
		userInfo.setPassword("admin123");
		if (validateUser(userInfo)) {
			if (!file.isEmpty()) {
				try {
					byte[] bytes = file.getBytes();
					if (CsvFileUtil.saveFile(bytes, file.getOriginalFilename())) {
						if (fileUtil.validateServiceDataHeader(
								Constants.FILE_DESTINATION_FOLDER + file.getOriginalFilename())) {
							FileData fileData = ConfigUtil.prepareFileDataEntity(bytes, file.getOriginalFilename(),Constants.SERVICE, userInfo.getUserName());
							fileData.setId(fileId);
							if (fileService.updateFileData(fileData) != null) {
								response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_UPDATED_SUCCESFULLY);
								response.put("fileId", fileId);
								log.info("exit ServiceDataController.updateServiceDataFile{}", response);
								responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
							} else {
								response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPDATE_FILE);
							}
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPLOAD_FILE);
					log.error("exit ServiceDataController.updateServiceDataFile {}{}", response, e);
					responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.put(Constants.ERROR_MESSAGE, Constants.INVALID_USER_LOGIN);
			log.error("exit ServiceDataController.updateServiceDataFile ");
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	
	@DeleteMapping("/config/service")
	public ResponseEntity<?> deleteServiceDataFile(@RequestParam("fileId") String fileId) {
		log.info("enter ServiceDataController.deleteServiceDataFile{}", fileId);
		Map<String, Object> response = new HashMap<>();
		fileService.deleteFileDataById(fileId);
		response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_DELETED_SUCCESSFULLY);
		log.info("exit ServiceDataController.deleteServiceDataFile{}");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private boolean validateUser(UserInfo userInfo) {
		return (userInfo.getUserName().equalsIgnoreCase("admin") && userInfo.getPassword().equalsIgnoreCase("admin123"))
				? true : false;
	}

}
