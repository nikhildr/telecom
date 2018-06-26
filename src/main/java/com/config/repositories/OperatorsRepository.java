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

import com.config.entity.Operators;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class OperatorsRepository {
	
	    private final String INDEX = "operators";
	    private final String TYPE = "user";
	    
	    @Autowired
	    private RestHighLevelClient restHighLevelClient;

	    @Autowired
	    private ObjectMapper objectMapper;
	    
	    public String addOperator(Operators operator)
	    {
	    	operator.setOperatorId(UUID.randomUUID().toString());
	    	String responseId = null;
	    	 Map<String, Object> dataMap = objectMapper.convertValue(operator, Map.class);
		        IndexRequest indexRequest = new IndexRequest(operator.getIndexName(), TYPE, operator.getOperatorId())
		                .source(dataMap);
		        try {
		            IndexResponse response = restHighLevelClient.index(indexRequest);
		            responseId=response.getId();
		        } catch(ElasticsearchException e) {
		            e.getDetailedMessage();
		        } catch (java.io.IOException ex){
		            ex.getLocalizedMessage();
		        }
		        return responseId;
	    }
	    
	    public Operators updateOperator( Operators operator){
	    	Operators response=null;
	        UpdateRequest updateRequest = new UpdateRequest(operator.getIndexName(), TYPE, operator.getOperatorId())
	                .fetchSource(true);    // Fetch Object after its update
	        try {
	            String operatorJson = objectMapper.writeValueAsString(operator);
	            updateRequest.doc(operatorJson, XContentType.JSON);
	            UpdateResponse updateResponse = restHighLevelClient.update(updateRequest);
	             response= objectMapper.convertValue(updateResponse.getGetResult().sourceAsMap(), Operators.class);
	        }catch (JsonProcessingException e){
	            e.getMessage();
	        } catch (java.io.IOException e){
	            e.getLocalizedMessage();
	        }
	        return response;
	    }
	   public Operators getOperatorById(String id){
	        GetRequest getRequest = new GetRequest(INDEX, TYPE, id);
	        GetResponse getResponse = null;
	        try {
	            getResponse = restHighLevelClient.get(getRequest);
	            Operators operator= objectMapper.convertValue(getResponse.getSourceAsMap(), Operators.class);
	            return operator;
	        } catch (java.io.IOException e){
	            e.getLocalizedMessage();
	        }
	        return null;
	    }

	   
	    public void deleteOperatorById(String id) {
	        DeleteRequest deleteRequest = new DeleteRequest(INDEX, TYPE, id);
	        try {
	            DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest);
	        } catch (java.io.IOException e){
	            e.getLocalizedMessage();
	        }
	    }
	

}
