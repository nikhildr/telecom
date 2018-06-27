package com.config.repositories;

import java.util.Map;
import java.util.UUID;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.config.entity.NETemplates;
import com.config.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NETemplatesRepository {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	private String index = Constants.NE_TEMPLATE_INDEX;
	private String type = Constants.NE_TEMPLATE_TYPE;

	public String addNEtemplate(NETemplates templates) {
		templates.setTemplateId(UUID.randomUUID().toString());
		String responseId = null;
		Map<String, Object> dataMap = objectMapper.convertValue(templates, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, templates.getTemplateId()).source(dataMap);
		try {
			IndexResponse response = restHighLevelClient.index(indexRequest);
			responseId = response.getId();
		} catch (ElasticsearchException e) {
			e.getDetailedMessage();
		} catch (java.io.IOException ex) {
			ex.getLocalizedMessage();
		}
		return responseId;
	}

	public boolean updateNEtemplate(NETemplates template) {
		boolean status = false;
		UpdateRequest updateRequest = new UpdateRequest(index, type, template.getTemplateId()).fetchSource(true);

		try {
			String operatorJson = objectMapper.writeValueAsString(template);
			updateRequest.doc(operatorJson, XContentType.JSON);
			UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
			if (updateResponse.getId() != null)
				status = true;
		} catch (JsonProcessingException e) {
			e.getMessage();
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return status;
	}

	public NETemplates getNEtemplateById(String id) {
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest);
			NETemplates template = objectMapper.convertValue(getResponse.getSourceAsMap(), NETemplates.class);
			return template;
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return null;
	}

	public boolean deleteNEtemplateById(String id) {
		DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
		boolean status = false;
		try {
			DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
			if (deleteResponse.getId() != null)
				status = true;
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return status;
	}

}
