package com.rick.nc.baseinfo.teacher.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rick.base.plugin.fileupload.Upload2Disk;
import com.rick.base.plugin.fileupload.vo.UploadFile;
import com.rick.nc.baseinfo.teacher.model.Teacher;
import com.rick.nc.baseinfo.teacher.service.TeacherService;

@Controller
@RequestMapping("/teacher")
public class TeacherController {
	private static String HEAD_FOLDER = File.separator + "head";
	
	@Resource
	private Upload2Disk ud;
	
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
	
	@RequestMapping(value = "/uploadHead",method=RequestMethod.POST)
	@ResponseBody
	public UploadFile uploadHead(HttpServletRequest request,     
            HttpServletResponse response) throws FileNotFoundException, IOException {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
		 MultipartFile file = multipartRequest.getFile("head");
		 return ud.store(HEAD_FOLDER, file);
	}
	
}
