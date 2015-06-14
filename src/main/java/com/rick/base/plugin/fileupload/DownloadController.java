package com.rick.base.plugin.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.rick.base.util.ServletContextUtil;
import com.rick.nc.doc.model.Document;
import com.rick.nc.doc.service.DocumentService;

@Controller
@RequestMapping("/download")
public class DownloadController{
	
	@Resource
	private DocumentService docService;
	
	@Resource
	private Upload2Disk ud;
 
	@RequestMapping("/{id}")
	public void download(HttpServletRequest request,HttpServletResponse response,@PathVariable int id) throws IOException {
		Document doc = docService.findDocumentById(id);
		File file = new File(ud.getRealPath(doc.getFilePath()));
		if(file.isFile() && file.exists()) {
			OutputStream os = ServletContextUtil.getOsFromResponse(response, request, doc.getTitle());
			IOUtils.write(FileUtils.readFileToByteArray(file), os);
			os.close();
		} else {
			throw new FileNotFoundException("no such file");
		}
	}
	
	@RequestMapping("/downloadUrl")
	public void downloadUrl(String filePath,String fileName,HttpServletRequest request,HttpServletResponse response) throws IOException {
		File file = new File(ud.getRealPath(filePath));
		if(file.isFile() && file.exists()) {
			OutputStream os = ServletContextUtil.getOsFromResponse(response, request, fileName);
			IOUtils.write(FileUtils.readFileToByteArray(file), os);
			os.close();
		} else {
			throw new FileNotFoundException("no such file");
		}
	}
	
	@RequestMapping("/src")
	public void imageSrc(String filePath,HttpServletResponse response) throws IOException {
		File file = new File(ud.getRealPath(filePath));
		
		if(file.isFile() && file.exists()) {
			OutputStream os = response.getOutputStream();
			IOUtils.write(FileUtils.readFileToByteArray(file), os);
			os.close();
		} else {
			throw new FileNotFoundException("no such file");
		}
	}
}
