package com.config.controller;

import java.util.List;

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

import com.config.entity.NENotifications;
import com.config.service.NENotifcationService;

@RestController
public class NENotificationsController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NENotificationsController.class);
	@Autowired
	private NENotifcationService service;

	@PostMapping("/config/nenotifcation")
	public ResponseEntity<?> addNetworkElement(@RequestBody NENotifications networkDomain) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNENotification(networkDomain);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/nenotifcation")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NENotifications element) {
		ResponseEntity<?> responseEntity = null;
		if (service.updateNENotification(element))
			responseEntity = new ResponseEntity<>("updated successfully", HttpStatus.OK);
		return responseEntity;
	}

	@DeleteMapping("/config/nenotifcation/{id}")
	public ResponseEntity<?> deleteNetworkDomain(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if (service.deleteNENotificationById(id))
			responseEntity = new ResponseEntity<>("deleted successfully", HttpStatus.OK);
		return responseEntity;
	}

	@GetMapping("/config/nenotifcation/{id}")
	public ResponseEntity<?> getNENotification(@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		NENotifications notifications = service.getNENotificationById(id);
		responseEntity = new ResponseEntity<>(notifications, HttpStatus.OK);
		return responseEntity;
	}
	
	@GetMapping("/config/nenotifcation/excel")
	public ResponseEntity<?> downloadNENotificationExcel() {
		ResponseEntity<?> responseEntity = null;
		List<NENotifications> notifications = service.getAllNENotifications();
		responseEntity = new ResponseEntity<>(notifications, HttpStatus.OK);
		return responseEntity;
	}

}
