package com.bjy.lotuas.common.excel.write;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
@SuppressWarnings({"unchecked","rawtypes"})
public abstract class AbstractWriteExcel {
	private Log log4j = LogFactory.getLog(this.getClass());
	private final static String excelPath = getPackagePath(AbstractWriteExcel.class)+"/file/template.xls"; 
	protected Workbook workbook = null;
	protected List<String> titles = null;
	protected List<List<ExcelHead>> excelHeadsList = null;
	protected List<Collection> datasetList = null;
	protected String pattern = null;
	protected int currentIndex;
	protected Sheet sheet = null;
	protected boolean initFlag = false;
	protected String errorMessage = null;
	
	public AbstractWriteExcel(String fileTitle, List<ExcelHead> excelHeads,
			Collection dataset) {
		List<String> titles = new ArrayList<String>();
		titles.add(fileTitle);
		List<List<ExcelHead>> excelHeadsList = new ArrayList<List<ExcelHead>>();
		excelHeadsList.add(excelHeads);
		List<Collection> datasetList = new ArrayList<Collection>();
		datasetList.add(dataset);
		this.titles = titles ;
		this.excelHeadsList = excelHeadsList;
		this.datasetList = datasetList;
		this.pattern = "yyyy-MM-dd";
		this.currentIndex = 0;
		Resource resource = new ClassPathResource(excelPath);
		try {
			this.workbook = new XSSFWorkbook(resource.getInputStream()); 
		} catch (Exception e) {
			try { 
				this.workbook = new HSSFWorkbook(resource.getInputStream());
			} catch (Exception ef) {
				this.setEerrorInfoAndLog("excel文档版本不被系统支持！");
				return;
			}
		}
		try {
			int i = 0;
			for (String title : titles) {
				this.sheet = this.workbook.createSheet(title);
				new WriteSheet(this.workbook, this.sheet,this.excelHeadsList.get(i), this.datasetList.get(i),this.pattern, this.currentIndex);
			}
			this.workbook.removeSheetAt(0);
		} catch (Exception e) {
			this.setEerrorInfoAndLog("excel写入过程中出错！");
			return; 
		}
	}
	
	
	public AbstractWriteExcel(List<String> titles,List<List<ExcelHead>> excelHeadsList,
			List<Collection> datasetList,String pattern, int currentIndex) {
		super();
		this.titles = titles;
		this.excelHeadsList = excelHeadsList;
		this.datasetList = datasetList;
		this.pattern = pattern;
		this.currentIndex = currentIndex;
		Resource resource = new ClassPathResource(excelPath);
		try {
			this.workbook = new XSSFWorkbook(resource.getInputStream()); 
		} catch (Exception e) {
			try { 
				this.workbook = new HSSFWorkbook(resource.getInputStream());
			} catch (Exception ef) {
				ef.printStackTrace();
				this.setEerrorInfoAndLog("excel文档版本不被系统支持！");
				return;
			}
		}
		try {
			int i = 0;
			for (String title : titles) {
				this.sheet = this.workbook.createSheet(title);
				new WriteSheet(this.workbook, this.sheet,this.excelHeadsList.get(i), this.datasetList.get(i),this.pattern, this.currentIndex);
				i++;
			}
			this.workbook.removeSheetAt(0);
		} catch (Exception e) {
			this.setEerrorInfoAndLog("excel写入过程中出错！");
			return; 
		}
	}

	public abstract void doExport();
	
	private void setEerrorInfoAndLog(String message) {
		this.initFlag = false;
		this.errorMessage = message;
		log4j.error(message);
	}

	public boolean isInitFlag() {
		return initFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}
	
	private static String getPackagePath(Class<?> clazz){
		String fullName=clazz.getName();
		String fullPath=fullName.substring(0, fullName.lastIndexOf("."));
		fullPath=fullPath.substring(0, fullPath.lastIndexOf("."));
		fullPath=fullPath.replaceAll("[.]", "/");
		return fullPath;
	}
}
