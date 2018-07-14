package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NetworkDomain;
import com.config.repositories.NetworkDomainRepository;

import java.util.List;

@Component
public class NetworkDomainService {
	
	@Autowired
	public NetworkDomainRepository repository;
	

	public String addNetworkDomain(NetworkDomain networkDomain) {
		return repository.addNetworkDomain(networkDomain);
	}

	public boolean deleteNetworkDomainById(String id) {
		return repository.deleteNetworkDomainById(id);
	}
	
	public boolean updateNetworkDomain(String id,NetworkDomain networkDomain) {
		return repository.updateNetworkDomain(id,networkDomain);
	}
	
	public NetworkDomain getNetworkDomainById(String id)
	{
		return repository.getNetworkDomainById(id);
	}


    public List<NetworkDomain> getAllNetworkDomains() {
	    return repository.getAllNetowrkDomains();
    }
}
