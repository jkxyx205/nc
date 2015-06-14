package com.rick.nc.doc.service;

import com.rick.nc.doc.model.Document;

public interface DocumentService {
	public static final String FILE_FILE = "1";
	
	public static final String FILE_FOLDER = "0";
	
	public void addDocument(Document doc);
	
	public void updateDocument(Document doc);
	
	public void delDocumentById(int id);
	
	public Document findDocumentById(int id);
	
}
