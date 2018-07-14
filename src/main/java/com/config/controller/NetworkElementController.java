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

import com.config.entity.NetworkElement;
import com.config.service.NetworkElementService;
import com.config.util.Constants;
import com.config.util.MediaTypeUtils;
import com.config.util.TimestampHelper;
import com.config.util.WorkBookHelper;

@RestController
public class NetworkElementController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NetworkElementController.class);
	@Autowired
	private NetworkElementService service;

	@Autowired
	private ServletContext servletContext;

	@PostMapping("/config/networkelement")
	public ResponseEntity<?> addNetworkElement(@RequestBody NetworkElement element) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNetworkElement(element);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/networkelement/{id}")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NetworkElement element,@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if(service.updateNetworkElement(id,element))
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

	@GetMapping("/config/networkelement/downloadExcelFile")
	public ResponseEntity<?> downloadNetworkElementExcel() {
		String fileName = Constants.NETWORK_ELEMENT_INDEX+"_"+TimestampHelper.getFilenameTimestamp()+".xlsx";
		String filePath = Constants.FILE_DESTINATION_FOLDER + fileName;
		List<NetworkElement> networkElements = service.getAllNetworkElements();
		if (networkElements != null && networkElements.size() > 0) {
			WorkBookHelper.writeToExcel(filePath, networkElements);
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
