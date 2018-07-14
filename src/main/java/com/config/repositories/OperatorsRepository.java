package com.config.repositories;

import com.config.entity.Operators;
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
public class OperatorsRepository {

    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private Environment env;

    private String index = Constants.OPERATORS_INDEX;
    private String type = Constants.OPERATORS_TYPE;

    public String addOperator(Operators operator) {
        operator.setOperatorId(UUID.randomUUID().toString());
        String responseId = null;
        Map<String, Object> dataMap = objectMapper.convertValue(operator, Map.class);
        IndexRequest indexRequest = new IndexRequest(index, type, operator.getOperatorId()).source(dataMap);
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

    public boolean updateOperator(Operators operator) {
        boolean status = false;
        UpdateRequest updateRequest = new UpdateRequest(index, type, operator.getOperatorId()).fetchSource(true);
        try {
            String operatorJson = objectMapper.writeValueAsString(operator);
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

    public Operators getOperatorById(String id) {
        GetResponse getResponse = null;
        Operators operator = null;
        GetRequest getRequest = new GetRequest(index, type, id);
        try {
            getResponse = restHighLevelClient.get(getRequest);
            operator = objectMapper.convertValue(getResponse.getSourceAsMap(), Operators.class);
        } catch (java.io.IOException e) {
            e.getLocalizedMessage();
        }
        return operator;
    }

    public boolean deleteOperatorById(String id) {
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

    public List<Operators> getAllOperatos() {
        List<Operators> operators = new ArrayList<>();
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
                    Operators operator = null;
                    if (hitsMap.containsKey("_source") && hitsMap.get("_source") != null)
                        operator = (Operators) objectMapper.convertValue(hitsMap.get("_source"), Operators.class);
                    operators.add(operator);
                }
            }
        }
        return operators;
    }

}
