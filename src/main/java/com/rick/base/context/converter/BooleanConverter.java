package com.rick.base.context.converter;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

public class BooleanConverter implements Converter<String, Boolean>{

	public Boolean convert(String source) {  
		if(StringUtils.isBlank(source))
			return Boolean.FALSE;
		
	       return source.equals("0") ? Boolean.FALSE : Boolean.TRUE; 
	}  
}
