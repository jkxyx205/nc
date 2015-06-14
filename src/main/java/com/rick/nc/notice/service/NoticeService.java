package com.rick.nc.notice.service;

import com.rick.base.plugin.jqgird.JqgridJsonBO;
import com.rick.nc.notice.model.Notice;

public interface NoticeService {
	public void addNotice(Notice notice);
	
	public void updateNotice(Notice notice);
	
	public Notice getNoticeById(Integer id);	
	
	public JqgridJsonBO getPageList(String title, int currentPage,int rows) throws Exception;
}
