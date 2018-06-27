package com.config.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.config.entity.Operators;
import com.config.service.OperatorsService;

@RestController
public class OperatorsController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(OperatorsController.class);
	@Autowired
	private OperatorsService service;

	@PostMapping("/config/addOperator")
	public ResponseEntity<?> addOperator(@RequestBody Operators operator) {
		log.info("enter OperatorsController.addOperator{}", operator);
		ResponseEntity<?> responseEntity = null;
		String response = service.addOperator(operator);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/updateOperator")
	public ResponseEntity<?> updateOperator(@RequestBody Operators operator) {
		ResponseEntity<?> responseEntity = null;
		if(service.updateOperator(operator))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/deleteOperator/{id}")
	public ResponseEntity<?> deleteOperator(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if(service.deleteOperatorById(id))
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

}
