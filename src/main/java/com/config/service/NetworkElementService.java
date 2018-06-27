package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NetworkElement;
import com.config.repositories.NetworkElementRepository;

@Component
public class NetworkElementService {
	
	@Autowired
	public NetworkElementRepository repository;
	

	public String addNetworkElement(NetworkElement networkElement) {
		return repository.addNetworkElement(networkElement);
	}

	public boolean deleteNetworkElementById(String id) {
		return repository.deleteNetworkElementById(id);
	}
	
	public boolean updateNetworkElement(NetworkElement networkElement) {
		return repository.updateNetworkElement(networkElement);
	}
	
	public NetworkElement getNetworkElementById(String id)
	{
		return repository.getNetworkElementById(id);
	}


}
