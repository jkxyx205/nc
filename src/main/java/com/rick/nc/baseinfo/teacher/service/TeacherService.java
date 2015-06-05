package com.rick.nc.baseinfo.teacher.service;

import com.rick.nc.baseinfo.teacher.model.Teacher;

public interface TeacherService {
	public void add(Teacher teacher);
	
	public void update(Teacher teacher);
	
	public Teacher findById(Integer id);
}
