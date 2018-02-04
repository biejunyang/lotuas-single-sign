package com.bjy.lotuas.common.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class DefaultReadExcel {
	private Log log4j = LogFactory.getLog(this.getClass());
	
	private File file = null;
	private List<Class<?>> clazzs= null;
	private List<String[]> fieldNamesList = null;
	private int readStartRow = 0;
	private Workbook workbook = null;
	private Sheet sheet = null;
	private boolean initFlag = false;
	private String errorMessage = null;
	private List<List<Object>> entitysList = new ArrayList<List<Object>>();

	public DefaultReadExcel(File file,Class<?> clazz, String[] fieldNames){
		this.file = file;
		try {
			this.workbook = new XSSFWorkbook(new FileInputStream(this.file));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.workbook = new HSSFWorkbook(new FileInputStream(this.file));
			} catch (Exception ef) {
				ef.printStackTrace();
				this.setEerrorInfoAndLog("excel文档版本不被系统支持！");
				return;
			}
		}
		List<Class<?>> clazzsList = new ArrayList<Class<?>>();
		clazzsList.add(clazz);
		this.clazzs = clazzsList;
		List<String[]> fieldNamesList = new ArrayList<String[]>();
		fieldNamesList.add(fieldNames);
		this.fieldNamesList = fieldNamesList;
		this.readStartRow = 1;
		int i = 0;
		for(Class<?> clazztemp:clazzs) {
			this.sheet = this.workbook.getSheetAt(i);
			ReadSheet readSheet = new ReadSheet(this.sheet,i,clazztemp,this.fieldNamesList.get(i),this.readStartRow);
			if(!readSheet.isInitFlag()){
				this.setEerrorInfoAndLog(readSheet.getErrorMessage());
				return;
			}else{
				this.entitysList.add(readSheet.getEntitys());
			}
			i++;
		}
		this.initFlag = true;
	}
	
	public DefaultReadExcel(File file,List<Class<?>> clazzsList, List<String[]> fieldNamesList,int readStartRow){
		try {
			this.workbook = new XSSFWorkbook(new FileInputStream(this.file));
		} catch (Exception e) {
			try {
				this.workbook = new HSSFWorkbook(new FileInputStream(this.file));
			} catch (Exception ef) {
				this.setEerrorInfoAndLog("excel文档版本不被系统支持！");
				return;
			}
		}
		this.clazzs = clazzsList;
		this.fieldNamesList = fieldNamesList;
		int i = 0;
		for(Class<?> clazz:clazzs) {
			this.sheet = this.workbook.getSheetAt(i);
			ReadSheet readSheet = new ReadSheet(this.sheet,i,clazz,this.fieldNamesList.get(i),this.readStartRow);
			if(!readSheet.isInitFlag()){
				this.setEerrorInfoAndLog(readSheet.getErrorMessage());
				return;
			}else{
				this.entitysList.add(readSheet.getEntitys());
			}
			i++;
		}
		this.initFlag = true;
	}
	
	private void setEerrorInfoAndLog(String message) {
		this.initFlag = false;
		this.errorMessage = message;
		log4j.error(message);
	}

	public boolean isInitFlag() {
		return initFlag;
	}

	public void setInitFlag(boolean initFlag) {
		this.initFlag = initFlag;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	public List<List<Object>> getEntitysList() {
		return entitysList;
	}
}
