package com.config.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.config.entity.DataCenter;
import com.config.service.DataCenterService;
import com.config.util.Constants;
import com.config.util.MediaTypeUtils;
import com.config.util.TimestampHelper;
import com.config.util.WorkBookHelper;

@RestController
public class DataCenterController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(DataCenterController.class);
	@Autowired
	private DataCenterService service;

	@Autowired
	private ServletContext servletContext;

	@PostMapping("/config/datacenter")
	public ResponseEntity<?> addDataCenter(@RequestBody DataCenter dataCenter) {
		ResponseEntity<?> responseEntity = null;
		dataCenter.setcDate(TimestampHelper.getCurrentTimestamp());
		String response = service.addDataCenter(dataCenter);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/dataCenter/{id}")
	public ResponseEntity<?> updateDataCenter(@RequestBody DataCenter dataCenter,@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if (service.updateDataCenter(id,dataCenter))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/datacenter/{id}")
	public ResponseEntity<?> deleteDataCenterById(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if (service.deleteDataCenterById(id))
			responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/datacenter/{id}")
	public ResponseEntity<?> getDataCenterById(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
        DataCenter dataCenter = service.getDataCenterById(id);
		responseEntity = new ResponseEntity<>(dataCenter, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/datacenter/downloadexcelfile")
	public ResponseEntity<?> downloadDataCentersExcel() {
		String fileName = Constants.DATA_CENTER_INDEX+"_"+TimestampHelper.getFilenameTimestamp()+".xlsx";
		String filePath = Constants.FILE_DESTINATION_FOLDER + fileName;
		List<DataCenter> operators = service.getAllDataCenters();
		if (operators != null && operators.size() > 0) {
			WorkBookHelper.writeToExcel(filePath, operators);
			MediaType mediaType = MediaTypeUtils.getMediaTypeForFileName(this.servletContext, filePath);
			File file = new File(filePath);
			InputStreamResource resource = null;
			try {
				resource = new InputStreamResource(new FileInputStream(file));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName)
					.contentType(mediaType).contentLength(file.length()).body(resource);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}
}
