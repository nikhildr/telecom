package com.config.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "CONFIG_DATA")
public class FileData {
	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long ID;
	@Column(name = "USER_NAME", length = 50)
	private String userName;
	@Column(name = "FILE_NAME", length = 250)
	private String fileName;
	@Column(name = "DATA",columnDefinition = "BLOB") 
	private byte[] data;
	@Column(name = "STATUS", length = 20)
	private String status;
	@Column(name = "TYPE", length = 20)
	private String type;
	@Column(name = "COMPLETED_TIME")
	private Timestamp completedTime;
	@Column(name = "UPDATED_BY", length = 50)
	private String updatedBy;
	@Column(name = "UPDATED_TIME")
	private Timestamp updatedTime;

}
