package com.config.util;

import java.io.IOException;

import com.config.entity.FileData;

public class ConfigUtil {

	public static FileData prepareFileDataEntity(byte[] fileData, String fileName, String fileType, String userName)
			throws IOException {
		FileData dataEntity = null;
		dataEntity = new FileData();
		dataEntity.setUserName(userName);
		dataEntity.setFileName(fileName);
		dataEntity.setStatus(Constants.STATUS_CREATED);
		dataEntity.setUpdatedBy(userName);
		dataEntity.setFileName(fileName);
		dataEntity.setType(fileType);
		dataEntity.setUpdatedTime(TimestampHelper.getCurrentTimestamp());
		dataEntity.setCompletedTime(TimestampHelper.getCurrentTimestamp());
		dataEntity.setData(fileData);
		return dataEntity;
	}
}
