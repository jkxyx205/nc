package com.rick.base.plugin.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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
public class UploadController {
	
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
				UploadFile uf = store(file);
				
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
	
	private UploadFile store(MultipartFile file) throws FileNotFoundException, IOException {
		String fileStoreName = UUID.randomUUID().toString() + getFileExtend(file.getOriginalFilename());
		 
		Calendar now = Calendar.getInstance();  
				 
		String uploadPath =  "resources" + File.separator + "upload" + File.separator + now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) ;
		String folder = Constants.realContextPath + File.separator + uploadPath;
		
		 File ff = new File(folder);
		 if(!ff.exists()) {
			 ff.mkdirs();
		 }
		 
		 
		 IOUtils.write(file.getBytes(), new FileOutputStream(new File(ff,fileStoreName)));
		 
		 UploadFile f = new UploadFile();
		 
		 f.setContentType(file.getContentType());
		 f.setEmpty(file.isEmpty());
		 f.setName(file.getOriginalFilename());
		 f.setOriginalFilename(file.getOriginalFilename());
		 f.setSize(file.getSize());
		 f.setFilePath(uploadPath+ File.separator + fileStoreName);
		 return f;
		
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

	private String getFileExtend(String originalFilename) {
		int num =  originalFilename.lastIndexOf(".");
		
		if (num == -1)
			return "";
		
		return originalFilename.substring(num);
	}
}
