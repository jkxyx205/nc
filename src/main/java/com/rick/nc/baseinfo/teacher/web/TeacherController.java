package com.rick.nc.baseinfo.teacher.web;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rick.nc.baseinfo.teacher.model.Teacher;
import com.rick.nc.baseinfo.teacher.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	@Resource
	private TeacherService teacherService;
	
	@RequestMapping("/list")
	public String list() {
		return "/baseinfo/teacher/list";
	}
	
	@RequestMapping("/gotoAdd")
	public String gotoAdd() {
		return "/baseinfo/teacher/add";
	}
	
	@RequestMapping("/gotoEdit/{id}")
	public String gotoEdit(@PathVariable int id,Model model) {
		Teacher teacher = teacherService.findById(id);
		model.addAttribute("teacher", teacher);
		return "/baseinfo/teacher/edit";
	}
	
	
	@RequestMapping(value = "/save",method=RequestMethod.POST)
	public String save(Teacher teacher,String submitType) {
		teacherService.add(teacher);
		if("0".equals(submitType)) 
			return "redirect:/teacher/list";
		
		return "redirect:/teacher/gotoAdd";
	}
	
	@RequestMapping(value = "/edit",method=RequestMethod.POST)
	@ResponseBody
	public String edit(Teacher teacher) {
		teacherService.update(teacher);
		return "success";
	}
	
}
