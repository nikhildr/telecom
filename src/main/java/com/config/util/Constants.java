package com.config.util;

import java.util.ArrayList;
import java.util.List;

public class Constants {

	public static final String STATUS_CREATED="CREATED";
	public static final String SERVICE="service";
	public static final String DEVICE="device";
	public static final String FAILED_TO_UPLOAD_FILE="Failed to upload file";
	public static final String UPLOAD_VALID_FILE="Upload valid file";
	public static final String FILE_UPLOADED_SUCCESFULLY="File uploaded successfully";
	public static final String SUCCESS_MESSAGE="successMessage";
	public static final String ERROR_MESSAGE="errorMessage";
	public static final String INVALID_USER_LOGIN="Invalid login";
	public static final String SPACE_REGEX="\\s+";
	public static final String FILE_DESTINATION_FOLDER = "D://xlsupload//";
	public static final String FILE_DELETED_SUCCESSFULLY = "File deleted successfully";
	public static final String FILE_UPDATED_SUCCESFULLY="File updated successfully";
	public static final String FAILED_TO_UPDATE_FILE = "Failed to update file";
	public static List<String> getFixedServiceDataHeaders()
	{
		List<String> serviceDataHeaders=new ArrayList<>();
		serviceDataHeaders.add("servicename");
		serviceDataHeaders.add("servicetype");
		serviceDataHeaders.add("AEnd");
		serviceDataHeaders.add("ZEnd");
		serviceDataHeaders.add("template");
		serviceDataHeaders.add("Path");
		serviceDataHeaders.add("deviceList");
		serviceDataHeaders.add("linkslist");
		serviceDataHeaders.add("operations");
		serviceDataHeaders.add("customerName");
		return serviceDataHeaders;
	}
	
	public static List<String> getFixedDeviceDataHeaders()
	{
		List<String> deviceDataHeaders=new ArrayList<>();
		deviceDataHeaders.add("devicename");
		deviceDataHeaders.add("devicetype");
		deviceDataHeaders.add("swversion");
		deviceDataHeaders.add("hwversion");
		deviceDataHeaders.add("macaddress");
		deviceDataHeaders.add("hostname");
		deviceDataHeaders.add("protocol");
		deviceDataHeaders.add("vendorname");
		deviceDataHeaders.add("datacenter");
		deviceDataHeaders.add("template");
		deviceDataHeaders.add("operations");
		return deviceDataHeaders;
	}
}
