package com.rick.base.plugin.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

import com.rick.base.context.Constants;
import com.rick.base.plugin.fileupload.vo.UploadFile;
import com.rick.nc.doc.model.Document;
import com.rick.nc.doc.service.DocumentService;

@Controller
@RequestMapping("/upload")
public class UploadController{
	private static String NOTICE_FOLDER = File.separator + "document";
	
	@Resource
	private Upload2Disk ud;
	
	@Resource
	private DocumentService docService;
	
	@RequestMapping(value={"/file"},method=RequestMethod.POST)
	@ResponseBody
	public List<UploadFile> uploadFile(HttpServletRequest request,     
            HttpServletResponse response) {
		
		 
		 MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;   
		 int pid = Integer.parseInt(multipartRequest.getParameter("pid"));
	     // 获得文件：     
		 List<MultipartFile> files = multipartRequest.getFiles("files[]");
		 List<UploadFile> retList = new ArrayList<UploadFile>(files.size());
		 
		 
		try {
			for (MultipartFile file : files) {
				UploadFile uf = ud.store(NOTICE_FOLDER,file);
				
				Document doc = save2Db(pid,file,uf.getFilePath());
				uf.setId(doc.getId());
				uf.setUpdateTime(Constants.SDF_TIME.format(doc.getUpdateTime()));
				retList.add(uf);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		 return retList;
	}

	
	private Document save2Db(int pid,MultipartFile file,String filePath) {
		Document doc = new Document();
		doc.setPid(pid);
		doc.setDocSize(file.getSize());
		doc.setFilePath(filePath);
		doc.setFileType(DocumentService.FILE_FILE);
		doc.setDocType(file.getContentType());
		doc.setTitle(file.getOriginalFilename());
		doc.setUpdateBy("admin");
		doc.setUpdateTime(new Date());
		docService.addDocument(doc);
		return doc;
	}
}
