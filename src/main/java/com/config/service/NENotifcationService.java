package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NENotifications;
import com.config.repositories.NENotificationsRepository;

@Component
public class NENotifcationService {

	@Autowired
	public NENotificationsRepository repository;

	public String addNEtemplate(NENotifications notification) {
		return repository.addNEnotification(notification);
	}

	public boolean deleteNEtemplateById(String id) {
		return repository.deleteNeNotificationById(id);
	}

	public boolean updateNEtemplate(NENotifications notification) {
		return repository.updateNEnotification(notification);
	}

	public NENotifications getNEtemplateById(String id) {
		return repository.getNeNotificationById(id);
	}

}
