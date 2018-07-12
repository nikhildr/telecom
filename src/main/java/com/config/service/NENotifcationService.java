package com.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NENotifications;
import com.config.repositories.NENotificationsRepository;

@Component
public class NENotifcationService {

	@Autowired
	public NENotificationsRepository repository;

	public String addNENotification(NENotifications notification) {
		return repository.addNEnotification(notification);
	}

	public boolean deleteNENotificationById(String id) {
		return repository.deleteNeNotificationById(id);
	}

	public boolean updateNENotification(NENotifications notification) {
		return repository.updateNEnotification(notification);
	}

	public NENotifications getNENotificationById(String id) {
		return repository.getNeNotificationById(id);
	}

	public List<NENotifications> getAllNENotifications()
	{
		return repository.getAllNeNotifications();
		
	}
}
