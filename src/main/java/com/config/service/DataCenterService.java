package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.DataCenter;
import com.config.repositories.DataCenterRepository;

@Component
public class DataCenterService {

	@Autowired
	public DataCenterRepository repository;

	public String addDataCenter(DataCenter dataCenter) {
		return repository.addDataCenter(dataCenter);
	}

	public boolean deleteDataCenterById(String id) {
		return repository.deleteDataCenterById(id);
	}

	public boolean updateDataCenter(DataCenter operator) {
		return repository.updateDataCenter(operator);
	}

	public DataCenter getDataCenterById(String id) {
		return repository.getDataCenterById(id);
	}

}
