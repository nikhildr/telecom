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

import com.config.entity.DataCenter;
import com.config.entity.DataCenter;
import com.config.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class DataCenterRepository {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	private String index = Constants.DATA_CENTER_INDEX;
	private String type = Constants.DATA_CENTER_TYPE;

	public String addDataCenter(DataCenter dataCenter) {
		dataCenter.setId(UUID.randomUUID().toString());
		String responseId = null;
		Map<String, Object> dataMap = objectMapper.convertValue(dataCenter, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, dataCenter.getId()).source(dataMap);
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

	public boolean updateDataCenter(DataCenter dataCenter) {
		boolean status = false;
		UpdateRequest updateRequest = new UpdateRequest(index, type, dataCenter.getId()).fetchSource(true);

		try {
			String operatorJson = objectMapper.writeValueAsString(dataCenter);
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

	public DataCenter getDataCenterById(String id) {
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest);
			DataCenter notification = objectMapper.convertValue(getResponse.getSourceAsMap(), DataCenter.class);
			return notification;
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return null;
	}

	public boolean deleteDataCenterById(String id) {
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
