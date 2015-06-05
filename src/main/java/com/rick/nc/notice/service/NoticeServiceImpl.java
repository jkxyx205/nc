package com.rick.nc.notice.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rick.base.dao.BaseDaoImpl;
import com.rick.nc.notice.model.Notice;

@Service
public class NoticeServiceImpl implements NoticeService {
	
	@Resource
	private BaseDaoImpl baseDao;

	public void addNotice(Notice notice) {
		baseDao.save(notice);
	}

	public void updateNotice(Notice notice) {
		baseDao.update(notice);
	}

}
