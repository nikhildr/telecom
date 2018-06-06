package com.config.dto;

import lombok.Data;

@Data
public class DeviceData {

	private String devicename;
	private String devicetype;
	private String swversion;
	private String hwversion;
	private String macaddress;
	private String hostname;
	private String protocol;
	private String vendorname;
	private String datacenter;
	private String template;
	private String operations;
}
