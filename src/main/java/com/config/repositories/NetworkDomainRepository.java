package com.config.repositories;

import com.config.entity.NetworkDomain;
import com.config.util.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Repository
public class NetworkDomainRepository {

	@Autowired
	private RestHighLevelClient restHighLevelClient;

	@Autowired
	private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

	private String index = Constants.NETWORK_DOMAIN_INDEX;
	private String type = Constants.NETWORK_DOMAIN_TYPE;

	public String addNetworkDomain(NetworkDomain domain) {
		domain.setId(UUID.randomUUID().toString());
		String responseId = null;
		Map<String, Object> dataMap = objectMapper.convertValue(domain, Map.class);
		IndexRequest indexRequest = new IndexRequest(index, type, domain.getId()).source(dataMap);
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

	public boolean updateNetworkDomain(String id,NetworkDomain domain) {
		boolean status = false;
		;
		UpdateRequest updateRequest = new UpdateRequest(index, type, id).fetchSource(true);
		try {
			String operatorJson = objectMapper.writeValueAsString(domain);
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

	public NetworkDomain getNetworkDomainById(String id) {
		GetResponse getResponse = null;
		NetworkDomain element = null;
		GetRequest getRequest = new GetRequest(index, type, id);
		try {
			getResponse = restHighLevelClient.get(getRequest);
			element = objectMapper.convertValue(getResponse.getSourceAsMap(), NetworkDomain.class);
		} catch (java.io.IOException e) {
			e.getLocalizedMessage();
		}
		return element;
	}

	public boolean deleteNetworkDomainById(String id) {
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

    public List<NetworkDomain> getAllNetowrkDomains() {
            List<NetworkDomain> elements = new ArrayList<>();
            String url = env.getProperty("elasticsearch.url") + index + Constants.SEARCH;
            RestTemplate restTemplate = new RestTemplate();
            Object response = restTemplate.getForObject(url, Object.class);
            if (response != null && response instanceof LinkedHashMap<?, ?>) {
                LinkedHashMap<?, ?> map = objectMapper.convertValue(response, LinkedHashMap.class);
                LinkedHashMap<?, ?> hits = (LinkedHashMap<?, ?>) map.get("hits");
                int hitsSize = (Integer) hits.get("total");
                if (hitsSize > 0) {
                    List<?> hitsArray = (List<?>) hits.get("hits");
                    for (Object object : hitsArray) {
                        LinkedHashMap<?, ?> hitsMap = (LinkedHashMap<?, ?>) object;
                        NetworkDomain networkDomain = null;
                        if (hitsMap.containsKey("_source") && hitsMap.get("_source") != null)
                            networkDomain = (NetworkDomain) objectMapper.convertValue(hitsMap.get("_source"), NetworkDomain.class);
                        elements.add(networkDomain);
                    }
                }
            }
            return elements;
        }
    }
