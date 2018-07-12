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
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.config.entity.Operators;
import com.config.service.OperatorsService;
import com.config.util.Constants;
import com.config.util.MediaTypeUtils;
import com.config.util.TimestampHelper;
import com.config.util.WorkBookHelper;

@RestController
public class OperatorsController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OperatorsController.class);
	@Autowired
	private OperatorsService service;

	@Autowired
	private ServletContext servletContext;

	@PostMapping("/config/addOperator")
	public ResponseEntity<?> addOperator(@RequestBody Operators operator) {
		log.info("enter OperatorsController.addOperator{}", operator);
		ResponseEntity<?> responseEntity = null;
		operator.setcDate(TimestampHelper.getCurrentTimestamp());
		String response = service.addOperator(operator);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/updateOperator")
	public ResponseEntity<?> updateOperator(@RequestBody Operators operator) {
		ResponseEntity<?> responseEntity = null;
		if (service.updateOperator(operator))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/deleteOperator/{id}")
	public ResponseEntity<?> deleteOperator(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if (service.deleteOperatorById(id))
			responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/getOperator/{id}")
	public ResponseEntity<?> getOperator(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		Operators operator = service.getOperatorById(id);
		responseEntity = new ResponseEntity<>(operator, HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/getOperator/downloadExcelFile")
	public ResponseEntity<?> downloadNENotificationExcel() {
		String fileName = "operator.xlsx";
		String filePath = Constants.FILE_DESTINATION_FOLDER + fileName;
		List<Operators> operators = service.getAllOperators();
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
			return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file.getName())
					.contentType(mediaType).contentLength(file.length()).body(resource);
		} else {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

	}
}
