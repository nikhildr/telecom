package com.config.util;

import java.io.IOException;

import com.config.entity.NETemplates;

public class ConfigUtil {

	public static NETemplates prepareNETemplateEntity(byte[] fileData, String fileName, String fileType, String userName)
			throws IOException {
		NETemplates template = new NETemplates();
		template.setName(fileName);
		template.setStatus(Constants.STATUS_CREATED);
		template.setUpdatedBy(userName);
		template.setType(fileType);
		template.setUpdateDate(TimestampHelper.getCurrentTimestamp());
		template.setcDate(TimestampHelper.getCurrentTimestamp());
		template.setTemplateFile(fileData);
		template.setCreatedBy(userName);
		return template;
	}
}
