package com.config.entity;

import java.sql.Timestamp;

public class NENotifications {

	private String notifId;
	private String type;
	private String notifObject	;
	private String notifCause	;
	private String status	;
	private String affectedService	;
	private Timestamp cDate;
	private Timestamp updateDate;
	private String createdBy;
	private String updatedBy;

	private String indexName="NENotifications";

	public String getNotifId() {
		return notifId;
	}

	public void setNotifId(String notifId) {
		this.notifId = notifId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNotifObject() {
		return notifObject;
	}

	public void setNotifObject(String notifObject) {
		this.notifObject = notifObject;
	}

	public String getNotifCause() {
		return notifCause;
	}

	public void setNotifCause(String notifCause) {
		this.notifCause = notifCause;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAffectedService() {
		return affectedService;
	}

	public void setAffectedService(String affectedService) {
		this.affectedService = affectedService;
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

	public String getIndexName() {
		return indexName;
	}
	
	
	
}
