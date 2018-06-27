package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NetworkDomain;
import com.config.repositories.NetworkDomainRepository;

@Component
public class NetworkDomainService {
	
	@Autowired
	public NetworkDomainRepository repository;
	

	public String addNetworkElement(NetworkDomain networkDomain) {
		return repository.addNetworkDomain(networkDomain);
	}

	public boolean deleteNetworkElementById(String id) {
		return repository.deleteNetworkDomainById(id);
	}
	
	public boolean updateNetworkElement(NetworkDomain networkDomain) {
		return repository.updateNetworkDomain(networkDomain);
	}
	
	public NetworkDomain getNetworkElementById(String id)
	{
		return repository.getNetworkDomainById(id);
	}


}
