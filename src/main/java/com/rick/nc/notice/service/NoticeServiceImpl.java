package com.rick.nc.notice.service;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rick.base.context.Constants;
import com.rick.base.dao.BaseDaoImpl;
import com.rick.base.plugin.fileupload.Upload2Disk;
import com.rick.base.plugin.jqgird.JqgridJsonBO;
import com.rick.base.plugin.jqgird.service.JqgridService;
import com.rick.base.plugin.jqgird.service.JqgridService.PageModel;
import com.rick.nc.notice.model.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	private static final String HTML_FOLDER = "notice";
	
	@Resource
	private Upload2Disk ud;
	
	@Resource
	private BaseDaoImpl baseDao;
	
	@Resource
	private JqgridService jqService;

	public void addNotice(Notice notice) {
		baseDao.save(notice);
		createHtml(notice);
	}

	public void updateNotice(Notice notice) {
		baseDao.update(notice);
		createHtml(notice);
	}

	public Notice getNoticeById(Integer id) {
		return baseDao.get(Notice.class, id);
	}

	public JqgridJsonBO getPageList(String title, int currentPage, int rows) throws Exception {
		Map<String,Object> param = new HashMap<String,Object>(1);
		param.put("title", title);
		 
		PageModel model = new PageModel();
		model.setQueryName("notice.list");
		model.setPage(currentPage);
		model.setRows(rows);
		model.setSidx("publishTime");
		model.setSord("desc");
		return jqService.getJqgirdData(model, param);
		
	}
	
	private void createHtml(Notice notice) {
		Map<String,Object> context = new HashMap<String,Object>();
		context.put("ctx", Constants.contextPath);
		context.put("publishTime", Constants.SDF_TIME.format(notice.getPublishTime()));
		context.put("notice", notice);
		ud.createHtml("notice_detail.vm",HTML_FOLDER,notice.getId().toString() + Constants.HTML_EXT,context);
	}
	
	
}
