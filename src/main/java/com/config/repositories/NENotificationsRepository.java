package com.config.repositories;

import java.util.List;
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

import com.config.entity.NENotifications;
import com.config.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class NENotificationsRepository {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

	private String index = Constants.NE_NOTIFICATION_INDEX;
	private String type = Constants.NE_NOTIFICATION_TYPE;

	public String addNEnotification(NENotifications notification) {
		notification.setNotifId(UUID.randomUUID().toString());
		String responseId = null;
		Map<String, Object> dataMap = objectMapper.convertValue(notification, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, notification.getNotifId()).source(dataMap);
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

	public boolean updateNEnotification(NENotifications notification) {
		boolean status = false;
		UpdateRequest updateRequest = new UpdateRequest(index, type, notification.getNotifId()).fetchSource(true);

		try {
			String operatorJson = objectMapper.writeValueAsString(notification);
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

	public NENotifications getNeNotificationById(String id) {
		GetRequest getRequest = new GetRequest(index, type, id);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest);
			NENotifications notification = objectMapper.convertValue(getResponse.getSourceAsMap(),
					NENotifications.class);
			return notification;
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return null;
	}

	public boolean deleteNeNotificationById(String id) {
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

	public List<NENotifications> getAllNeNotifications() {
		GetRequest getRequest = new GetRequest(index);
		GetResponse getResponse = null;
		try {
			getResponse = restHighLevelClient.get(getRequest);
			List<NENotifications> list = (List<NENotifications>) objectMapper.convertValue(getResponse.getSourceAsMap(),
					NENotifications.class);
			return list;
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return null;
	}

}
