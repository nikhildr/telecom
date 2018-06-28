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

import com.config.entity.NetworkDomain;
import com.config.service.NetworkDomainService;

@RestController
public class NetworkDomainController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NetworkDomainController.class);
	@Autowired
	private NetworkDomainService service;

	@PostMapping("/config/networkdomain")
	public ResponseEntity<?> addNetworkElement(@RequestBody NetworkDomain networkDomain) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNetworkDomain(networkDomain);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/networkdomain")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NetworkDomain element) {
		ResponseEntity<?> responseEntity = null;
		if(service.updateNetworkDomain(element))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/networkdomain/{id}")
	public ResponseEntity<?> deleteNetworkDomain(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if(service.deleteNetworkDomainById(id))
		responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/networkdomain/{id}")
	public ResponseEntity<?> getNetworkDomain(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		NetworkDomain domain = service.getNetworkDomainById(id);
		responseEntity = new ResponseEntity<>(domain, HttpStatus.OK);
		return responseEntity;
	}

}
