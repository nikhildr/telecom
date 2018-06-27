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

import com.config.entity.NetworkElement;
import com.config.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NetworkElementRepository {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	private String index = Constants.NETWORK_ELEMENT_INDEX;
	private String type = Constants.NETWORK_ELEMENT_TYPE;

	public String addNetworkElement(NetworkElement element) {
		element.setElementId(UUID.randomUUID().toString());
		String responseId = null;
		Map<String, Object> dataMap = objectMapper.convertValue(element, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, element.getElementId()).source(dataMap);
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

	public boolean updateNetworkElement(NetworkElement element) {
		boolean status = false;
		UpdateRequest updateRequest = new UpdateRequest(index, type, element.getElementId()).fetchSource(true);
		try {
			String operatorJson = objectMapper.writeValueAsString(element);
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

	public NetworkElement getNetworkElementById(String id) {
		GetResponse getResponse = null;
		NetworkElement element = null;
		GetRequest getRequest = new GetRequest(index, type, id);
		try {
			getResponse = restHighLevelClient.get(getRequest);
			element = objectMapper.convertValue(getResponse.getSourceAsMap(), NetworkElement.class);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return element;
	}

	public boolean deleteNetworkElementById(String id) {
		boolean status = false;
		DeleteRequest deleteRequest = new DeleteRequest(index, type, id);
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
