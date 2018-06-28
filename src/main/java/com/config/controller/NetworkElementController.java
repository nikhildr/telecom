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

import com.config.entity.NetworkElement;
import com.config.service.NetworkElementService;

@RestController
public class NetworkElementController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NetworkElementController.class);
	@Autowired
	private NetworkElementService service;

	@PostMapping("/config/networkelement")
	public ResponseEntity<?> addNetworkElement(@RequestBody NetworkElement element) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNetworkElement(element);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/networkelement")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NetworkElement element) {
		ResponseEntity<?> responseEntity = null;
		if(service.updateNetworkElement(element))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/networkelement/{id}")
	public ResponseEntity<?> deleteNetworkElement(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if(service.deleteNetworkElementById(id))
		responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/networkelement/{id}")
	public ResponseEntity<?> getNetworkElement(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		NetworkElement element = service.getNetworkElementById(id);
		responseEntity = new ResponseEntity<>(element, HttpStatus.OK);
		return responseEntity;
	}

}
