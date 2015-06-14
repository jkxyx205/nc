package com.rick.base.plugin.ckeditor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.rick.base.plugin.fileupload.Upload2Disk;
import com.rick.base.plugin.fileupload.vo.UploadFile;

@Controller
@RequestMapping("/ckeditor")
public class CkEditorController {
	@Resource
	private Upload2Disk ud;

	@RequestMapping(value="/uploadImg",method=RequestMethod.POST)
	@ResponseBody
	public String uploadImg(HttpServletRequest request,     
            HttpServletResponse response) throws FileNotFoundException, IOException {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	     // 获得文件：     
		 MultipartFile file = multipartRequest.getFile("upload");
		 
		 UploadFile ufile = ud.store(file);
		 
		 String callback = request.getParameter("CKEditorFuncNum");  
		 
		 return "<script type=\"text/javascript\">window.parent.CKEDITOR.tools.callFunction("+callback+",'"+ufile.getVisitUrl()+"','');</script>";
		 
	}
	
	@RequestMapping(value="/uploadFile",method=RequestMethod.POST)
	@ResponseBody
	public List<UploadFile> uploadFile(HttpServletRequest request,     
            HttpServletResponse response) throws FileNotFoundException, IOException {
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
	     // 获得文件：     
		 List<MultipartFile> files = multipartRequest.getFiles("upload");
		 
	     // 获得文件：     
		 List<UploadFile> retList = new ArrayList<UploadFile>(files.size());
		 
		try {
			for (MultipartFile file : files) {
				UploadFile uf = ud.store(file);
				retList.add(uf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 return retList;
	}
	
}
