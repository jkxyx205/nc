package com.rick.base.util;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.context.ContextLoader;

public class I18nUtil {

	public static String getMessageByCode(String messageCode) {
		return getMessageByCode(messageCode,null);
	}
	
	public static String getMessageByCode(String messageCode,Object[] param) {
		if(StringUtils.isBlank(messageCode)) {
			return messageCode;
		}
		return ContextLoader.getCurrentWebApplicationContext().getMessage(messageCode, param, LocaleContextHolder.getLocale());
	}
}
