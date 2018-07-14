package com.config.service;

import com.config.entity.Operators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NetworkElement;
import com.config.repositories.NetworkElementRepository;

import java.util.List;

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
	
	public boolean updateNetworkElement(String id,NetworkElement networkElement) {
		return repository.updateNetworkElement(id,networkElement);
	}
	
	public NetworkElement getNetworkElementById(String id)
	{
		return repository.getNetworkElementById(id);
	}

	public List<NetworkElement> getAllNetworkElements() {
		return repository.getAllNetworkElements();
	}

}
