package com.rick.base.plugin.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.io.IOUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import com.rick.base.context.Constants;
import com.rick.base.plugin.fileupload.vo.UploadFile;
import com.rick.base.util.DateUtil;
import com.rick.base.util.FileUtils;

@Service
public class Upload2Disk {
	
	@Value("${upload}")
	private String upload;
	
	public static final String FOLDER_SEPARATOR = "|";
	
	@Resource 
	private VelocityConfigurer vconf;
	
	public String getUpload() {
		return upload;
	}
	
	public UploadFile store(String subFolder,MultipartFile file) throws FileNotFoundException, IOException {
		String fileStoreName = UUID.randomUUID().toString() + FileUtils.getFileExtend(file.getOriginalFilename());
		 
		String uploadPath = subFolder + File.separator + DateUtil.getTimeFolder();
		
		String folder = upload + uploadPath;
		
		 File ff = new File(folder);
		 if(!ff.exists()) {
			 ff.mkdirs();
		 }
		 
		 FileOutputStream fos = new FileOutputStream(new File(ff,fileStoreName));
		 IOUtils.write(file.getBytes(), fos);
		 fos.close();
		 
		 UploadFile f = new UploadFile();
		 
		 f.setContentType(file.getContentType());
		 f.setEmpty(file.isEmpty());
		 f.setName(file.getOriginalFilename());
		 f.setOriginalFilename(file.getOriginalFilename());
		 f.setSize(file.getSize());
		 f.setFilePath((uploadPath+ File.separator + fileStoreName).replace(File.separator, FOLDER_SEPARATOR));
		 String visitUrl = Constants.contextPath + "/download/src?filePath="  + f.getFilePath();
		 String downloadUrl = Constants.contextPath + "/download/downloadUrl?filePath="  + f.getFilePath();
		 f.setVisitUrl(visitUrl);
		 f.setDownloadUrl(downloadUrl);
		 return f;
	}

	/*public UploadFile store(MultipartFile file) throws FileNotFoundException, IOException {
		return store("", file);
	}*/
	
	public String getRealPath(String filePath) {
		filePath = filePath.replace(Upload2Disk.FOLDER_SEPARATOR,File.separator);
		return getUpload() + File.separator + filePath;
	}
	
	public void createHtml(String vm,String folder,String fileName,Map<String,Object> context) {
		VelocityEngine ve = vconf.getVelocityEngine();
		Template t = ve.getTemplate(vm);
		VelocityContext vc = new VelocityContext();
		
		if (MapUtils.isNotEmpty(context)) {
			for (Map.Entry<String, Object> en : context.entrySet()) {
				vc.put(en.getKey(), en.getValue());
			}
		}
		
		StringWriter writer = new StringWriter();
		t.merge(vc, writer);
		writer.flush();
		String html = writer.toString();
		try {
			String htmlPath = upload + File.separator + folder + File.separator + DateUtil.getTimeFolder(); 
			
			File htmlFolder = new File(htmlPath);
			if (!htmlFolder.exists()) {
				htmlFolder.mkdirs();
			}
			File htmlFile = new File(htmlFolder,fileName);
			org.apache.commons.io.FileUtils.writeStringToFile(htmlFile, html, Constants.ENCODING);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
