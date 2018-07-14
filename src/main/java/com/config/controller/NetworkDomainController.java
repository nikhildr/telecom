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

import com.config.entity.NetworkDomain;
import com.config.service.NetworkDomainService;
import com.config.util.Constants;
import com.config.util.MediaTypeUtils;
import com.config.util.TimestampHelper;
import com.config.util.WorkBookHelper;

@RestController
public class NetworkDomainController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NetworkDomainController.class);
	@Autowired
	private NetworkDomainService service;

    @Autowired
    private ServletContext servletContext;

	@PostMapping("/config/networkdomain")
	public ResponseEntity<?> addNetworkElement(@RequestBody NetworkDomain networkDomain) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNetworkDomain(networkDomain);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/networkdomain/{id}")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NetworkDomain element,@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if(service.updateNetworkDomain(id,element))
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

    @GetMapping("/config/networkdomain/downloadExcelFile")
    public ResponseEntity<?> downloadNetworkDomainExcel() {
        String fileName = Constants.NETWORK_DOMAIN_INDEX+"_"+TimestampHelper.getFilenameTimestamp()+".xlsx";
        String filePath = Constants.FILE_DESTINATION_FOLDER + fileName;
        List<NetworkDomain> networkDomains = service.getAllNetworkDomains();
        if (networkDomains != null && networkDomains.size() > 0) {
            WorkBookHelper.writeToExcel(filePath, networkDomains);
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
