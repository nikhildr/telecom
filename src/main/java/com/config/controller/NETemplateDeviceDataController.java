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

import com.config.entity.NETemplates;
import com.config.request.UserInfo;
import com.config.service.NETemplateService;
import com.config.util.ConfigUtil;
import com.config.util.Constants;
import com.config.util.CsvFileUtil;

/**
 * Created by nikhil on 6/2/2018.
 */

@RestController
public class NETemplateDeviceDataController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NETemplateDeviceDataController.class);

	@Autowired
	private NETemplateService templateService;

	@Autowired
	private CsvFileUtil fileUtil;

	@PostMapping("netemplate/config/device")
	public ResponseEntity<?> addNETempalateData(@RequestParam("file") MultipartFile file) {
		log.info("enter NETemplateDeviceDataController.addNETempalateData{}", file);
		
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
							String templateId = templateService.addNEtemplate(ConfigUtil.prepareNETemplateEntity(bytes,file.getOriginalFilename(), Constants.DEVICE, userInfo.getUserName()));
							response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_UPLOADED_SUCCESFULLY);
							response.put("templateId", templateId);
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

	@GetMapping("netemplate/config/device")
	public ResponseEntity<?> getNETemplateDevice() {
		// to do
		return new ResponseEntity<>("get device",HttpStatus.OK);

	}

	@PutMapping("netemplate/config/device")
	public ResponseEntity<?> updateNETemplateDevice(@RequestParam("file") MultipartFile file,
			@RequestParam("templateId") String templateId) {
		log.info("enter NETemplateDeviceDataController.updateNETemplateDevice{},{}", file.getOriginalFilename(), templateId);
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
							NETemplates template = ConfigUtil.prepareNETemplateEntity(bytes, file.getOriginalFilename(),Constants.DEVICE, userInfo.getUserName());
							template.setTemplateId(templateId);
							if (templateService.updateNEtemplate(template)) {
								response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_UPDATED_SUCCESFULLY);
								response.put("templateId", templateId);
								log.info("exit NETemplateDeviceDataController.updateNETemplateDevice{}", response);
								responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
							} else {
								response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPDATE_FILE);
							}
						}

					}
				} catch (Exception e) {
					e.printStackTrace();
					response.put(Constants.ERROR_MESSAGE, Constants.FAILED_TO_UPLOAD_FILE);
					log.error("exit NETemplateDeviceDataController.updateNETemplateDevice {}{}", response, e);
					responseEntity = new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
				}
			}
		} else {
			response.put(Constants.ERROR_MESSAGE, Constants.INVALID_USER_LOGIN);
			log.error("exit NETemplateDeviceDataController.updateNETemplateDevice ");
			responseEntity = new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@DeleteMapping("/config/device")
	public ResponseEntity<?> deleteNETemplateDevice(@RequestParam("templateId") String templateId) {
		log.info("enter NETemplateDeviceDataController.deleteNETemplateDevice{}", templateId);
		Map<String, Object> response = new HashMap<>();
		templateService.deleteNEtemplateById(templateId);
		response.put(Constants.SUCCESS_MESSAGE, Constants.FILE_DELETED_SUCCESSFULLY);
		log.info("exit NETemplateDeviceDataController.deleteNETemplateDevice{}");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	private boolean validateUser(UserInfo userInfo) {
		return (userInfo.getUserName().equalsIgnoreCase("admin") && userInfo.getPassword().equalsIgnoreCase("admin123"))
				? true : false;
	}

}
