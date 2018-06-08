package com.config.controller;

import java.util.HashMap;
import java.util.Map;

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

import lombok.extern.slf4j.Slf4j;

/**
 * Created by nikhil on 6/2/2018.
 */

@RestController
@Slf4j
public class DeviceDataController {

	@Autowired
	private FileService fileService;

	@Autowired
	private CsvFileUtil fileUtil;

	@PostMapping("/config/device")
	public ResponseEntity<?> saveDeviceeDataFile(@RequestParam("file") MultipartFile file) {
		log.info("enter DeviceDataController.saveDeviceeDataFile{}", file);
		
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
						if (fileUtil.validateDeviceDataHeader(
								Constants.FILE_DESTINATION_FOLDER + file.getOriginalFilename())) {
							Long fileDataId = fileService.saveFileData(ConfigUtil.prepareFileDataEntity(bytes,file.getOriginalFilename(), Constants.DEVICE, userInfo.getUserName()));
							response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_UPLOADED_SUCCESFULLY);
							response.put("fileId", fileDataId);
							log.info("exit DeviceDataController.saveDeviceeDataFile{}", response);
							responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPLOAD_FILE);
					log.error("exit DeviceDataController.saveDeviceeDataFile {}{}", response, e);
					responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.put(Constants.ERROR_MESSAGE, Constants.INVALID_USER_LOGIN);
			log.error("exit DeviceDataController.saveDeviceeDataFile ");
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/config/device")
	public ResponseEntity<?> getDeviceFile() {
		// to do
		return new ResponseEntity<>("get device",HttpStatus.OK);

	}

	@PutMapping("/config/device")
	public ResponseEntity<?> updateDeviceDataFile(@RequestParam("file") MultipartFile file,
			@RequestParam("fileId") long fileId) {
		log.info("enter DeviceDataController.updateDeviceDataFile{},{}", file.getOriginalFilename(), fileId);
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
						if (fileUtil.validateDeviceDataHeader(
								Constants.FILE_DESTINATION_FOLDER + file.getOriginalFilename())) {
							FileData fileData = ConfigUtil.prepareFileDataEntity(bytes, file.getOriginalFilename(),Constants.DEVICE, userInfo.getUserName());
							fileData.setID(fileId);
							if (fileService.updateFileData(fileData)) {
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
					log.error("exit DeviceDataController.updateDeviceDataFile {}{}", response, e);
					responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.put(Constants.ERROR_MESSAGE, Constants.INVALID_USER_LOGIN);
			log.error("exit DeviceDataController.updateDeviceDataFile ");
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@DeleteMapping("/config/device")
	public ResponseEntity<?> deleteDeviceDataFile(@RequestParam("fileId") long fileId) {
		log.info("enter DeviceDataController.deleteDeviceDataFile{}", fileId);
		Map<String, Object> response = new HashMap<>();
		fileService.deleteFileDataById(fileId);
		response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_DELETED_SUCCESSFULLY);
		log.info("exit DeviceDataController.deleteDeviceDataFile{}");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private boolean validateUser(UserInfo userInfo) {
		return (userInfo.getUserName().equalsIgnoreCase("admin") && userInfo.getPassword().equalsIgnoreCase("admin123"))
				? true : false;
	}

}
