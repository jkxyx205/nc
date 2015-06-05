package com.rick.base.context.tld;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

public class SelectTag extends TagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;
	
	private String key;
	
	private String value;
	
	private boolean multiple;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public boolean isMultiple() {
		return multiple;
	}

	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	
	
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) throws JspException {
		// 对EL表达式的支持  
        this.value = (String) ExpressionEvaluatorManager.evaluate("value", value.toString(), Object.class, this, pageContext);  
	}

	@Override  
    public int doStartTag() throws JspException {  
        JspWriter out = this.pageContext.getOut();  
         
        try {  
            out.print(SelectUtil.selectString(name, key, value,multiple));  
        } catch (IOException e) {  
            throw new RuntimeException(e);  
        }  
        return super.doStartTag();  
    }  
}
