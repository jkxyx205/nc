package com.rick.nc.doc.web;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.rick.nc.doc.model.Document;
import com.rick.nc.doc.service.DocumentService;

@Controller
@RequestMapping("/doc")
public class DocmentController {

	@Resource
	private DocumentService docService;

	@RequestMapping("/index")
	public String gotoIndex() {
		return "/document/index";
	}

	@RequestMapping(value = { "/addDoc" }, method = RequestMethod.POST)
	@ResponseBody
	public String addDocument(Document doc) {
		doc.setUpdateBy("admin");
		doc.setUpdateTime(new Date());
		doc.setFileType(DocumentService.FILE_FOLDER);
		docService.addDocument(doc);
		return String.valueOf(doc.getId());
	}

	@RequestMapping(value = { "/renameDoc" }, method = RequestMethod.POST)
	@ResponseBody
	public String renameDocument(Document doc) {
		docService.updateDocument(doc);
		return "success";
	}

	@RequestMapping(value = { "/delDoc/{id}" }, method = RequestMethod.POST)
	@ResponseBody
	public String delDoc(@PathVariable int id) {
		docService.delDocumentById(id);
		return "success";
	}

}
