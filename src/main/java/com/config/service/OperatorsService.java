package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.Operators;
import com.config.repositories.OperatorsRepository;

@Component
public class OperatorsService {
	
	@Autowired
	public OperatorsRepository repository;
	

	public String addOperator(Operators operator) {
		return repository.addOperator(operator);
	}

	public void deleteOperatorById(String operatorId) {
		repository.deleteOperatorById(operatorId);
	}
	
	public Operators updateOperator(Operators operator) {
		return repository.updateOperator(operator);
	}
	
	public Operators getOperatorById(String operatorId)
	{
		return repository.getOperatorById(operatorId);
	}


}
