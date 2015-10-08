package com.rick.base.plugin.jqgird.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

public class ExportModelBO {
	private String queryName;
	private	String sidx;
	private String sord;
	private String[] colNames;
	private List<Map<String,Object>> colModel;
	
	private String fileName;
	
	private String reloadAll;
	
	public String getReloadAll() {
		return reloadAll;
	}
	public void setReloadAll(String reloadAll) {
		this.reloadAll = reloadAll;
	}
	public String getFileName() {
		return StringUtils.isBlank(fileName) ? getQueryName() : fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String[] getColNames() {
		return colNames;
	}
	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}
	public String getQueryName() {
		return queryName;
	}
	public void setQueryName(String queryName) {
		this.queryName = queryName;
	}
	public String getSidx() {
		return sidx;
	}
	public void setSidx(String sidx) {
		this.sidx = sidx;
	}
	public String getSord() {
		return sord;
	}
	public void setSord(String sord) {
		this.sord = sord;
	}
	public List<Map<String, Object>> getColModel() {
		return colModel;
	}
	public void setColModel(List<Map<String, Object>> colModel) {
		this.colModel = colModel;
	}
}
