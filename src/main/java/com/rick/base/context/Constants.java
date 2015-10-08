package com.rick.base.context;

import java.io.File;

import org.apache.commons.lang3.time.FastDateFormat;


public final class Constants {
	public static final String ENCODING = "UTF-8";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final String HTML_EXT = ".html";
	
	public static final FastDateFormat SDF_DATE = FastDateFormat.getInstance(DATE_FORMAT);
	
	public static final FastDateFormat SDF_TIME = FastDateFormat.getInstance(DATETIME_FORMAT);
	
	public static String realContextPath;
	
	public static String contextPath;
	
	public static File tempDir = new File("");
}
