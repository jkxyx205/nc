package com.rick.base.plugin.fileupload;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.rick.base.context.Constants;
import com.rick.base.plugin.fileupload.vo.UploadFile;
import com.rick.base.util.FileUtils;

@Service
public class Upload2Disk {
	
	@Value("${upload}")
	private String upload;
	
	public static final String FOLDER_SEPARATOR = "|";
	
	public String getUpload() {
		return upload;
	}

	public UploadFile store(MultipartFile file) throws FileNotFoundException, IOException {
		String fileStoreName = UUID.randomUUID().toString() + FileUtils.getFileExtend(file.getOriginalFilename());
		 
		Calendar now = Calendar.getInstance();  
		
		String uploadPath = now.get(Calendar.YEAR) + File.separator + (now.get(Calendar.MONTH) + 1) ;
		
		String folder = upload + File.separator + uploadPath;
		
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
	
	public String getRealPath(String filePath) {
		filePath = filePath.replace(Upload2Disk.FOLDER_SEPARATOR,File.separator);
		return getUpload() + File.separator + filePath;
	}
}
