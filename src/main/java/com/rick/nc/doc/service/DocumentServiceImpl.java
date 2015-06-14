package com.rick.nc.doc.service;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.rick.base.dao.BaseDaoImpl;
import com.rick.nc.doc.model.Document;

@Service
public class DocumentServiceImpl implements DocumentService {

	@Resource
	private BaseDaoImpl baseDao;

	public void addDocument(Document doc) {
		baseDao.save(doc);
	}

	public void delDocumentById(int id) {
		Document doc = findDocumentById(id);
		if(doc != null) {
			//baseDao.delete(doc);
			//逻辑删除
			String sql = baseDao.getNamedQueryString("tree.document.deleteById");
			baseDao.getJdbcTemplate().update(sql, new Object[]{id});
		}
	}

	public Document findDocumentById(int id) {
		Document doc = baseDao.get(Document.class, new Integer(id));
		return doc;
	}

	
	public void updateDocument(Document doc) {
		//仅仅修改名字
		Document docSrc = baseDao.get(Document.class, new Integer(doc.getId()));
		docSrc.setTitle(doc.getTitle());
		docSrc.setUpdateBy("admin");
		docSrc.setUpdateTime(new Date());
		baseDao.update(docSrc);
	}

}
