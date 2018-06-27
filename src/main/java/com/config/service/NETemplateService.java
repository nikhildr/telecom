package com.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.config.entity.NETemplates;
import com.config.repositories.NETemplatesRepository;

@Component
public class NETemplateService {

	@Autowired
	public NETemplatesRepository repository;

	public String addNEtemplate(NETemplates neTemplate) {
		return repository.addNEtemplate(neTemplate);
	}

	public boolean deleteNEtemplateById(String id) {
		return repository.deleteNEtemplateById(id);
	}

	public boolean updateNEtemplate(NETemplates templates) {
		return repository.updateNEtemplate(templates);
	}

	public NETemplates getNEtemplateById(String id) {
		return repository.getNEtemplateById(id);
	}

}
