package com.rick.nc.baseinfo.teacher.web;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rick.base.context.Constants;
import com.rick.base.plugin.fileupload.vo.UploadFile;
import com.rick.base.util.FileUtils;
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
	
	@RequestMapping(value = "/uploadHead",method=RequestMethod.POST)
	@ResponseBody
	public UploadFile uploadHead(HttpServletRequest request,     
            HttpServletResponse response) throws FileNotFoundException, IOException {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
		 
		// int pid = Integer.parseInt(multipartRequest.getParameter("pid"));
		 MultipartFile file = multipartRequest.getFile("head");
		 
		String uploadPath =  "resources" + File.separator + "head";
	    String folder = Constants.realContextPath + File.separator + uploadPath;
			
	    File ff = new File(folder);
	    
	    String fileStoreName = UUID.randomUUID().toString() + FileUtils.getFileExtend(file.getOriginalFilename());
	    
	    FileOutputStream fos = new FileOutputStream(new File(ff,fileStoreName));
	    IOUtils.write(file.getBytes(), fos);
	    fos.close();
	    
	    UploadFile f = new UploadFile();
		 
		f.setContentType(file.getContentType());
		f.setEmpty(file.isEmpty());
		f.setName(file.getOriginalFilename());
		f.setOriginalFilename(file.getOriginalFilename());
		f.setSize(file.getSize());
		f.setFilePath(uploadPath+ File.separator + fileStoreName);
		return f;
	}
	
}
