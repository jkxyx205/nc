package com.rick.base.util;

public class FileUtils {
	public static String getFileExtend(String originalFilename) {
		int num =  originalFilename.lastIndexOf(".");
		
		if (num == -1)
			return "";
		
		return originalFilename.substring(num);
	}
}
