package com.rick.base.context.converter;

import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.springframework.core.convert.converter.Converter;

import com.rick.base.context.Constants;

public class DateConverter implements Converter<String, Date>{

	public Date convert(String source) {  
		if(StringUtils.isBlank(source))
			return null;
		
	    try {  
	        return Constants.SDF_DATE.parse(source);  
	    } catch (ParseException e) {  
	        e.printStackTrace();  
	    }    
	    return null;  
	}  
}
