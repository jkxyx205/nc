package com.rick.base.plugin.jqgird.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.support.JdbcUtils;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rick.base.context.Constants;
import com.rick.base.dao.BaseDaoImpl;
import com.rick.base.dao.BaseDaoImpl.JdbcTemplateExecutor;
import com.rick.base.dao.PageSortModel;
import com.rick.base.dictionary.service.DictionaryUtils;
import com.rick.base.office.excel.ExcelRow.ExcelRowBuilder;
import com.rick.base.office.excel.ExcelVersion;
import com.rick.base.office.excel.ExcelWorkbook;
import com.rick.base.plugin.jqgird.JqgridJsonBO;
import com.rick.base.util.ServletContextUtil;
import com.rick.base.util.ZipUtil;

/**
 * @author Rick.Xu
 *
 */
@Service
public class JqgridService {
	private static final String JQGIRD_PARAM_SORD = "sord";
	private static final String JQGIRD_PARAM_PAGE = "page";
	private static final String JQGIRD_PARAM_ROW = "rows";
	private static final String JQGIRD_PARAM_QUERYNAME = "queryName";
	private static final String JQGIRD_PARAM_RELOADALL = "reloadAll";
	private static final String JQGIRD_PARAM_SIDX = "sidx";
	private static final String JQGIRD_EXPORT_JSON = "jqridJson";
	private static final String JQGIRD_EXPORT_COLMODEL_INDEX = "index";
	private static final String JQGIRD_EXPORT_COLMODEL_WIDTH = "width";
	private static final String JQGIRD_EXPORT_COLMODEL_NAME = "name";
	
	private static final ExcelVersion EXCEL_VERSION = ExcelVersion.V2007;
	
	/**
	 * 注意：对于Excel2003 最大只能是65536,
	 */
	private static final int GROUP_NUM = 5000;
	
	private static final String ZIP_EXT = ".zip";
	
	@Resource
	private BaseDaoImpl dao;
	
	public JqgridJsonBO getJqgirdData(HttpServletRequest request) throws Exception {
		Map<String,Object> param = ServletContextUtil.getMap(true, request);
		PageModel model = getPageModel(param);
		return getJqgirdData(model,param);
		
	}
	public JqgridJsonBO getJqgirdData(final PageModel model,Map<String,Object> param) throws Exception {
		List<Map<String, Object>> rows = dao.queryForSpecificParam(model.queryName, param, new JdbcTemplateExecutor<List<Map<String, Object>>>() {

			public List<Map<String, Object>> query(JdbcTemplate jdbcTemplate,
					String queryString, Object[] args) {
				queryString = wrapSordString(queryString, model.sidx, model.sord);
				if(!BOOLEAN_TRUE.equals(model.reloadAll)) {
					queryString = pageSql(queryString,model);
				}
					
					//
				//return jdbcTemplate.queryForList(sql, args);
				
				List<Map<String, Object>>  ret = jdbcTemplate.queryForList(queryString, args);
				translate(ret);
				return ret;
				
			}
		});
		
		long count = 0L;
		
		if(BOOLEAN_TRUE.equals(model.reloadAll)) {
			count = rows.size();
		} else {
			count = dao.queryForSpecificParamCount(model.queryName, param,new JdbcTemplateExecutor<Long>() {

				public Long query(JdbcTemplate jdbcTemplate, String queryString,
						Object[] args) {
					queryString = dao.formatSqlCount(queryString);
					return jdbcTemplate.queryForObject(queryString, args, Long.class);
				}
			}); 
		}
		
				
		JqgridJsonBO bo = new JqgridJsonBO();
		
		long total = 0L;
		
		if(!BOOLEAN_TRUE.equals(model.reloadAll)) {
			if(count%model.rows == 0) {
				total = count/model.rows; 
			} else {
				total = count/model.rows + 1;
			}
			bo.setTotal(total);
			bo.setPage(model.page);
		}
		
		bo.setRows(rows);
		bo.setRecords(count);
		
		return bo;
	}
	
	private void translate(List<Map<String, Object>>  ret) {
		for(Map<String, Object> m: ret) {
			Set<String> names = m.keySet();
			for(String name : names) {
				Object value = translate(name, m.get(name));
				m.put(name, value);
			}
		}
	}
	
	private Object translate(String name,Object value) {
		if(value!= null && (value.getClass() == String.class)) {
			String v = (String)value;
			if(StringUtils.isNotBlank(v)) {
				String[] vs = v.split(",");
				if(vs.length == 1) {
					return DictionaryUtils.getTrueValueByKeyorAlias(name, v);
				} else {
					StringBuilder lang = new StringBuilder();
					for (String vv : vs) {
						lang.append(DictionaryUtils.getTrueValueByKeyorAlias(name, vv)).append(",");
					}
					lang.deleteCharAt(lang.length()-1);
					return lang.toString();
				}
			}
		}
		
		return value;
	}
	
	private PageModel getPageModel(Map<String,Object> param) {
		PageModel model = new PageModel();
		model.queryName = (String) param.get(JQGIRD_PARAM_QUERYNAME);
		model.reloadAll = (String) param.get(JQGIRD_PARAM_RELOADALL);
		
		if(!BOOLEAN_TRUE.equals(model.reloadAll)) { //需要分页操作
			model.page = Integer.parseInt(param.get(JQGIRD_PARAM_PAGE).toString());
			model.rows = Integer.parseInt(param.get(JQGIRD_PARAM_ROW).toString());
		}
		
		model.sord = (String) param.get(JQGIRD_PARAM_SORD);
		model.sidx = (String) param.get(JQGIRD_PARAM_SIDX);
		
		return model;
	}
	private ExportModelBO getExportModelBO(String json) throws JsonParseException, JsonMappingException, IOException {
		ObjectMapper mapper = new ObjectMapper();
		ExportModelBO model = mapper.readValue(json, ExportModelBO.class);
		return model;
	}
	
	private void export(final ExportModelBO model,Map<String,Object> param,HttpServletRequest request,HttpServletResponse response, File folder) throws Exception {
		OutputStream os = null;
		try {
			long total = dao.queryForSpecificParamCount(model.getQueryName(), param,new JdbcTemplateExecutor<Long>() {

				public Long query(JdbcTemplate jdbcTemplate, String queryString,
						Object[] args) {
					queryString = dao.formatSqlCount(queryString);
					return jdbcTemplate.queryForObject(queryString, args, Long.class);
				}
			}); 
			
			List<ExcelWorkbook> bookList0 = null;
			final int groupContent =  GROUP_NUM - 1;
			if (total > groupContent) { //zip
				long excleNum = 0;
				if(total % groupContent ==0) {
					excleNum = total/groupContent;
				} else {
					excleNum = total/groupContent + 1;
				}
				bookList0 = new ArrayList<ExcelWorkbook>((int) excleNum);
				for(long i = 0; i < excleNum; i++) {
					ExcelWorkbook book = new ExcelWorkbook(EXCEL_VERSION);
					book.createSheet("sheet0");
					bookList0.add(book);
				}
				
			} else { //single
				bookList0 = new ArrayList<ExcelWorkbook>(1);
				ExcelWorkbook book = new ExcelWorkbook(EXCEL_VERSION);
				book.createSheet("sheet0");
				bookList0.add(book);
			}
			
			final List<ExcelWorkbook> bookList = new ArrayList<ExcelWorkbook>(bookList0);
			
			final List<String> colNames = new ArrayList<String>();
			
			List<Integer> hiddenIndex = new ArrayList<Integer>();
			
			int i = 0;
			for(Map<String,Object> colModel : model.getColModel()) {
				Object hiddenValue = colModel.get("hidden");
				Object expValue = colModel.get("exp");
				
				boolean hidden = hiddenValue == null ? false : (Boolean)hiddenValue;
				
				boolean exp = expValue == null ? true : (Boolean)expValue;
				
				String name = (String)colModel.get("name");
				if(!exp || hidden || "cb".equals(name) || "rn".equals(name)) {
					hiddenIndex.add(i);
				} else {
					colNames.add((String) colModel.get(JQGIRD_EXPORT_COLMODEL_INDEX));
				}
				i++;
			}
			
			final int len = colNames.size();
			
			final HeadProperty pros = getVisibleNames(model,hiddenIndex);
			
			for(ExcelWorkbook book: bookList) {
				Sheet sheet = book.getSheets().get(0);
				//create excel
				for(int w = 0; w < len; w++) {
					sheet.setColumnWidth(w, pros.width[w] * 30);
				}
					
				//write head
				book.createRow(sheet, new ExcelRowBuilder(0, 0, pros.names).style(book.getTheme().getS4()));
			}
			
			final SimpleDateFormat sdf = new SimpleDateFormat(Constants.DATE_FORMAT);
			
			//write body
			dao.queryForSpecificParam(model.getQueryName(), param, new JdbcTemplateExecutor<Serializable>() {

			public Serializable query(JdbcTemplate jdbcTemplate,
						String queryString, Object[] args) {
					queryString = wrapSordString(queryString, model.getSidx(), model.getSord());
					jdbcTemplate.query(queryString, args, new RowCallbackHandler() {
						private int idx = 0;
						private int rowIdx = 0;
						private int bookIndex = 0;
						private ExcelWorkbook book; 
						private Sheet sheet;
						
						private boolean first;
						private Map<String,Integer> nameIndex = new HashMap<String,Integer>(len);
						
						public void processRow(ResultSet rs) throws SQLException {
							if(!first) {
								first = true;
								ResultSetMetaData rsmd = rs.getMetaData();  
								int colCount = rsmd.getColumnCount();
								for(int i = 0; i< colCount; i++) {
									String s = rsmd.getColumnLabel(i+1);
									if(colNames.contains(s)) {
										nameIndex.put(s, i + 1);
									}
								}
							}
							
							String[] values = new String[len];
							
							//colNames
							for(int i = 0; i < len ; i++) {
								Integer index = nameIndex.get(colNames.get(i));
								
								if (index == null)
									continue;
								
								Object obj = JdbcUtils.getResultSetValue(rs, index);
								if(obj == null) {
									values[i] = "";
									continue;
								}
								
								if(obj.getClass() == java.sql.Timestamp.class) {
									values[i] = sdf.format(obj);
								} else if(obj.getClass() == java.sql.Date.class) {
									values[i] = sdf.format(obj);
								} else {
									//translate
									Object value = translate(pros.columnNames[i],obj);
									values[i] = String.valueOf(value);	
								}
							}
								
							
							if(idx % groupContent == 0) {
								rowIdx = 0;
								book = bookList.get(bookIndex);
								sheet = book.getSheets().get(0);
								bookIndex++;
							}
							
							book.createRow(sheet, new ExcelRowBuilder(0, rowIdx+1, values).style(book.getTheme().getS0()));
							idx++;
							rowIdx++;
						}
						
					});
					return null;
				}
				
			});
			
			//
			String fileName = model.getFileName();
			
			String fileExt = EXCEL_VERSION.getExt();
			
			os = getOutputStream(request,response,folder,fileName,bookList.size());
			
			if(bookList.size() == 1) { //single export
				bookList.get(0).write(os);
			} else { //zip export
				int j = 0;
				final File[] fileArr = new File[bookList.size()];
				
				for(ExcelWorkbook book : bookList) {
					File file = new File(Constants.tempDir,new StringBuilder(fileName).append("_").append(j +1).append(fileExt).toString());
					book.write(file);
					fileArr[j++] = file;
				}
				
				//zip
				final File file = new File(Constants.tempDir,fileName + ZIP_EXT);
				ZipUtil.compress(fileArr, file);
				os.write(FileUtils.readFileToByteArray(file));
				//delete on exit 
				Executor executor=Executors.newFixedThreadPool(1); 
				executor.execute(new Runnable(){ 
				public void run(){ 
					file.delete();
					for(File f : fileArr) {
						f.delete();
					}
				} 
				}); 
			}
		} finally {
			os.close();
		}
	}
	
	private OutputStream getOutputStream(HttpServletRequest request,HttpServletResponse response,
			File folder, String fileName, int len) throws IOException {
		OutputStream os = null;
		if (len == 1) {
			if (folder != null) 
				os = new FileOutputStream(new File(folder,fileName + EXCEL_VERSION.getExt()));
			else 
				os = ServletContextUtil.getOsFromResponse(response, request, fileName + EXCEL_VERSION.getExt());
		} else  {
			if (folder != null) 
				os = new FileOutputStream(new File(folder,fileName + ZIP_EXT));
			else 
				os = ServletContextUtil.getOsFromResponse(response, request, fileName + ZIP_EXT);
		}
		return os;
	}
	public void export(HttpServletRequest request,HttpServletResponse response) throws Exception {
		Map<String,Object> param = ServletContextUtil.getMap(true, request);
		final ExportModelBO model = getExportModelBO((String)param.get(JQGIRD_EXPORT_JSON));
	 
		export(model, param,request,response,null);
	}
	
	public void export(final ExportModelBO model,Map<String,Object> param,File file) throws Exception {
		if (file == null || file.isFile())
			throw new FileNotFoundException("the directory is not exists!");
		export(model, param,null,null,file);
		
	}
	
	private HeadProperty getVisibleNames(ExportModelBO model, List<Integer> hiddenIndex) {
		String[] colNames = model.getColNames();
		List<Map<String,Object>> colModel = model.getColModel();
		HeadProperty pros = new HeadProperty();
		
		int len = colNames.length;
		int visibleLen = len-hiddenIndex.size();
		String[] names = new String[visibleLen];
		String[] columnNames = new String[visibleLen];
		int[] width = new int[visibleLen];
		int j = 0;
		boolean hidden;
		for(int i = 0;i < len; i++) {
			hidden = false;
			for(int k : hiddenIndex) {
				if(k == i) {
					hidden = true;
					break;
				}
			}
			if(!hidden) {
				Map<String,Object> colModelMap = colModel.get(i);
				columnNames[j] = (String)colModelMap.get(JQGIRD_EXPORT_COLMODEL_NAME);
				names[j] = colNames[i];
				width[j] = (Integer)colModelMap.get(JQGIRD_EXPORT_COLMODEL_WIDTH);
				j++;
			}
		}
		pros.names = names;
		pros.width = width;
		pros.columnNames = columnNames;
		return pros;
	}
	
	private String pageSql(String sql, PageModel model) {
		PageSortModel m = new PageSortModel();
		if (model != null) {
			m.setPage(model.page);
			m.setRows(model.rows);
			m.setSidx(model.sidx);
			m.setSord(model.sord);
		}
		return dao.getSqlFormatter().pageSql(sql,m);
	}
	
	private String wrapSordString(String sql,String sidx, String sord) {
		StringBuilder sb = new StringBuilder("SELECT * FROM (");
		sb.append(sql).append(") temp");
		if(StringUtils.isNotBlank(sidx) && StringUtils.isNotBlank(sord)) {
			sb.append(" ORDER BY ").append(sidx).append(" ").append(sord);
			return sb.toString();
		} else {
			return sql;
		}
	}
	
	public static class PageModel {
		int page;
		int rows;
		String sidx;
		String sord;
		String queryName;
		//一次女性全部加载出来,不在分页
		String reloadAll;
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		public int getRows() {
			return rows;
		}
		public void setRows(int rows) {
			this.rows = rows;
		}
		public String getSidx() {
			return sidx;
		}
		public void setSidx(String sidx) {
			this.sidx = sidx;
		}
		public String getSord() {
			return sord;
		}
		public void setSord(String sord) {
			this.sord = sord;
		}
		public String getQueryName() {
			return queryName;
		}
		public void setQueryName(String queryName) {
			this.queryName = queryName;
		}
		public String getReloadAll() {
			return reloadAll;
		}
		public void setReloadAll(String reloadAll) {
			this.reloadAll = reloadAll;
		}
		
	}
	
	private class HeadProperty {
		private int[] width;
		private String[] names;
		private String[] columnNames;
	}
	
	private static final String BOOLEAN_TRUE = "true";
	
	@SuppressWarnings("unused")
	private static final String BOOLEAN_FALSE = "false";
	
}
