package com.rick.nc.baseinfo.teacher.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rick.base.dao.BaseDaoImpl;
import com.rick.nc.baseinfo.teacher.model.Teacher;

@Service
public class TeacherServiceImpl implements TeacherService {
	
	@Resource
	private BaseDaoImpl baseDao;

	public void add(Teacher teacher) {
		baseDao.save(teacher);
	}

	public void update(Teacher teacher) {
		baseDao.update(teacher);
	}

	public Teacher findById(Integer id) {
		return baseDao.get(Teacher.class, id);
	}

}
