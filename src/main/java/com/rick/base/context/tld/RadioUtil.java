package com.rick.base.context.tld;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.rick.base.dictionary.service.DictionaryUtils;


class RadioUtil {
	
	static String selectString(String name,String key,String selected) {
		
		Map<String,String> option = DictionaryUtils.getItemByKeyorAlias(key);
		
		StringBuilder sb = new StringBuilder();
		Set<Entry<String,String>> set = option.entrySet();
		int i = 0;
		for(Entry<String,String> en : set) {
			String id = name + (i++);
			String selectkey = en.getKey();
			sb.append("<input type=\"radio\" name=\"").append(name)
					.append("\"").append(" id=\"").append(id).append("\"")
					.append("value=\"").append(en.getKey()).append("\"");
			if(selectkey.equals(selected))
				sb.append(" checked");
			
			 sb.append("/>");
			 
			 //label
			sb.append("<label for=\"").append(id).append("\"").append(">")
					.append(en.getValue()).append("</label>");
		}
		return sb.toString();
 
	}
}
