package com.rick.base.context;

import java.io.File;
import java.text.SimpleDateFormat;


public final class Constants {
	public static final String ENCODING = "UTF-8";
	
	public static final String DATE_FORMAT = "yyyy-MM-dd";
	
	public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	public static final SimpleDateFormat SDF = new SimpleDateFormat(DATE_FORMAT);
	
	public static final SimpleDateFormat SDF_TIME = new SimpleDateFormat(DATETIME_FORMAT);
	
	public static String realContextPath;
	
	public static File tempDir;
}
