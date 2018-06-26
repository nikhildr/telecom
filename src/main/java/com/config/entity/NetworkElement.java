package com.config.entity;

import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(value = { "indexName" })
public class NetworkElement {
	
	private String elementId;
	private String name;
	private String type;
	private String status;
	private String version;
	private String protocol;
	private String softwareVersion;
	private String yangVersion;
	private Timestamp cDate;
	private String createdBy;
	private String updatedBy;
	private Timestamp updatedDate;
	private String templateId;
	private String ipAddress;
	private String portNumber;
	private String macAddress;
	private String userId;
	private String password;
	private String sshKey;

	private String indexName="NetworkElement";

}
