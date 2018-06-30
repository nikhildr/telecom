package com.config.entity;

import java.sql.Timestamp;

public class NETemplates {

	private String templateId;
	private String name;
	private String type;
	private String status;
	private byte[] templateFile;
	private Timestamp cDate;
	private Timestamp updateDate;
	private String createdBy;
	private String updatedBy;
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public byte[] getTemplateFile() {
		return templateFile;
	}
	public void setTemplateFile(byte[] templateFile) {
		this.templateFile = templateFile;
	}
	public Timestamp getcDate() {
		return cDate;
	}
	public void setcDate(Timestamp cDate) {
		this.cDate = cDate;
	}
	public Timestamp getUpdateDate() {
		return updateDate;
	}
	public void setUpdateDate(Timestamp updateDate) {
		this.updateDate = updateDate;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getUpdatedBy() {
		return updatedBy;
	}
	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	
}
