package com.config.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NENotifications;
import com.config.entity.Operators;
import com.config.repositories.OperatorsRepository;

@Component
public class OperatorsService {
	
	@Autowired
	public OperatorsRepository repository;
	

	public String addOperator(Operators operator) {
		return repository.addOperator(operator);
	}

	public boolean deleteOperatorById(String operatorId) {
		return repository.deleteOperatorById(operatorId);
	}
	
	public boolean updateOperator(String id,Operators operator) {
		return repository.updateOperator(operator);
	}
	
	public Operators getOperatorById(String operatorId)
	{
		return repository.getOperatorById(operatorId);
	}

	public List<Operators> getAllOperators() {
		return repository.getAllOperatos();
	}


}
