package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.DataCenter;
import com.config.repositories.DataCenterRepository;

import java.util.List;

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

	public boolean updateDataCenter(String id,DataCenter operator) {
		return repository.updateDataCenter(id,operator);
	}

	public DataCenter getDataCenterById(String id) {
		return repository.getDataCenterById(id);
	}

	public List<DataCenter> getAllDataCenters(){ return repository.getAllDataCenters();}

}
