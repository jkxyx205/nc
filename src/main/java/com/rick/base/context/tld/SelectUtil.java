package com.rick.base.context.tld;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.rick.base.dictionary.service.DictionaryUtils;

class SelectUtil {
	public static final int SEARCH_SISE = 15;
	
	static String selectString(String name,String key,String selected,boolean multiple) {
		Map<String,String> option = DictionaryUtils.getItemByKeyorAlias(key);
		
		StringBuilder sb = new StringBuilder();
		sb.append("<select class=\"form-control selectpicker\" ").append("id").append("=\"").append(name).append("\"");
		
		if (option.size() > SEARCH_SISE) //查询框
			sb.append("data-live-search=\"true\"");
		
		if (multiple)
			sb.append(" multiple"); //don't need name attribute
		//else
		
		sb.append(" name").append("=\"").append(name).append("\"");
		   
			
		sb.append(">");
		
		if(!multiple) 
			sb.append("<option value= \"\">--请选择--</option>");
		Set<Entry<String,String>> set = option.entrySet();
		for(Entry<String,String> en : set) {
			String selectkey = en.getKey();
			sb.append("<option value=\"").append(en.getKey()).append("\"");
			if(selectkey.equals(selected))
				sb.append(" selected");
			
			sb.append(">").append(en.getValue()).append("</option>");
		}
		sb.append("</select>");
		
		//初始化值
		if(multiple && StringUtils.isNotBlank(selected)) {
			String[] values = selected.split(",");
			
			StringBuilder scriptBuilder = new StringBuilder();
			
			scriptBuilder.append("<script>$(function() { $('#")
			.append(name).append("').selectpicker().selectpicker('val', [");
			
			for (String value : values) {
				scriptBuilder.append("'").append(value).append("',");
			}
			scriptBuilder.deleteCharAt(scriptBuilder.length()-1);
			scriptBuilder.append("]); });</script>");
			sb.append(scriptBuilder);
		}
		
		return sb.toString();
	}
}
