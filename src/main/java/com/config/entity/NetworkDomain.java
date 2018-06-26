package com.config.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "indexName" })
public class NetworkDomain {
	
	private String id;
	private String name;
	private String status;
	private String type;
	private String location;
	private Timestamp cDate;
	private String createdBy;
	private String addressRange;
	private String updatedBy;
	private String gateway;

	private String indexName="NetworkDomain";

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Timestamp getcDate() {
		return cDate;
	}

	public void setcDate(Timestamp cDate) {
		this.cDate = cDate;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getAddressRange() {
		return addressRange;
	}

	public void setAddressRange(String addressRange) {
		this.addressRange = addressRange;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	public String getGateway() {
		return gateway;
	}

	public void setGateway(String gateway) {
		this.gateway = gateway;
	}

	public String getIndexName() {
		return indexName;
	}

	
	
	

}
