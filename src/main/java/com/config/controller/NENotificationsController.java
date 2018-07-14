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

import com.config.entity.NENotifications;
import com.config.service.NENotifcationService;
import com.config.util.Constants;
import com.config.util.MediaTypeUtils;
import com.config.util.TimestampHelper;
import com.config.util.WorkBookHelper;

@RestController
public class NENotificationsController {

	private static final Logger log = (Logger) LoggerFactory.getLogger(NENotificationsController.class);
	@Autowired
	private NENotifcationService service;

    @Autowired
    private ServletContext servletContext;

	@PostMapping("/config/nenotifcation")
	public ResponseEntity<?> addNetworkElement(@RequestBody NENotifications notifications) {
		ResponseEntity<?> responseEntity = null;
		String response = service.addNENotification(notifications);
		if (response != null) {
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		}
		return responseEntity;
	}

	@PutMapping("/config/nenotifcation/{id}")
	public ResponseEntity<?> updateNetworkElement(@RequestBody NENotifications notifications,@PathVariable String id) {
		ResponseEntity<?> responseEntity = null;
		if (service.updateNENotification(id,notifications))
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
	

    @GetMapping("/config/nenotifcation/downloadExcelFile")
    public ResponseEntity<?> downloadNeNotificationsExcel() {
        String fileName = Constants.NE_NOTIFICATION_INDEX+"_"+TimestampHelper.getFilenameTimestamp()+".xlsx";
        String filePath = Constants.FILE_DESTINATION_FOLDER + fileName;
        List<NENotifications> notifications = service.getAllNENotifications();
        if (notifications != null && notifications.size() > 0) {
            WorkBookHelper.writeToExcel(filePath, notifications);
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
