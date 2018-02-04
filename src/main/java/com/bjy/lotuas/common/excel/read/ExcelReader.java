package com.bjy.lotuas.common.excel.read;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import com.bjy.lotuas.common.exception.VOCastException;
import com.bjy.lotuas.common.util.VoUtil;

public abstract class ExcelReader {

	protected File excel;// 文件
	private List<Class<?>> valueTypes = new ArrayList<Class<?>>();// 返回类型
	private List<Map<String, String>> headAttributeMap = new ArrayList<Map<String, String>>();// excel表头与对象属性的关联映射
	private List<List<String>> sheetHeads = new ArrayList<List<String>>();// excel的表头信息
	private Map<String, List<Object>> sheetValues=new LinkedHashMap<String, List<Object>>();//excel值信息
	private Workbook workbook;
	private int startRow = 1;// 默认每个sheet页从第2行开始读取
	private boolean success = true;// 读取结果
	private String message;

	public ExcelReader(File file) {
		this.excel = file;
		try {
			this.workbook = new HSSFWorkbook(new FileInputStream(excel));
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.workbook = new HSSFWorkbook(new FileInputStream(excel));
			} catch (Exception ex) {
				ex.printStackTrace();
				this.success = false;
				this.message = "excel文档版本不被系统支持!";
			}
		}
		if (this.success) {
			beforeRead();
			readExcel();
		}
	}
	public ExcelReader(String file) {
		this(new File(file));
	}
	
	/**
	 * 解析Excel之前的准备工作
	 */
	private void beforeRead() {
		// 设置返回类型
		setValueTypes(this.valueTypes);
		if (valueTypes.size() <= 0) {
			this.valueTypes.add(setValueType());
		}
		
		// 设置Excel表头和对象属性的映射关联
		mapHead2Attribute(this.headAttributeMap);
		if (this.headAttributeMap.size() <= 0) {
			this.headAttributeMap.add(setHeadAttributeMap());
		}
		
		//设置Excel的表头信息
		setSheetExcelHeads(this.sheetHeads);
		if(setExcelHeads()!=null && setExcelHeads().size()>0){
			this.sheetHeads.clear();
			this.sheetHeads.add(setExcelHeads());
		}
		this.startRow=setStartRow();
	}

	/**
	 * 读取Excel内容
	 * @throws VOCastException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 */
	private void readExcel(){
		try{
			for (int i = 0; i < valueTypes.size(); i++) {
				if (i < workbook.getNumberOfSheets()) {
					Sheet sheet = this.workbook.getSheetAt(i);
					Class<?> valueType = null;
					if (i < valueTypes.size()) {
						valueType = valueTypes.get(i);
					} else {
						valueType = LinkedHashMap.class;
					}
					Map<String, String> fieldsMapInfo = null;
					if (i < headAttributeMap.size()) {
						fieldsMapInfo = headAttributeMap.get(i);
					}
					List<String> excelHeads=null;
					if(i < sheetHeads.size()){
						excelHeads=sheetHeads.get(i);
					}
					if(isSuccess()){
						readSheet(sheet, valueType, fieldsMapInfo, excelHeads);
						if(!afterReadSheet(sheet)){
							break;
						}
					}else{
						break;
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
	}

	
	private void readSheet(Sheet sheet, Class<?> valueType,
			Map<String, String> fieldsMapInfo, List<String> excelxHeads) throws InstantiationException, IllegalAccessException, VOCastException {
		List<Object> values=new ArrayList<Object>();
		for(int i=startRow; i<sheet.getPhysicalNumberOfRows(); i++){
			Row row=sheet.getRow(i);
			Map<String, Object> rowValues=new HashMap<String, Object>();
			for(int j=0; j<excelxHeads.size(); j++){
				String head=excelxHeads.get(j);
				String attributeName=head;
				if(fieldsMapInfo!=null && fieldsMapInfo.containsKey(head)){
					attributeName=fieldsMapInfo.get(head);
				}
				Object value=getCellStringValue(row.getCell(j));
				rowValues.put(attributeName, value);
			}
			if(afterReadRow(rowValues, i, sheet)){
				Object target=valueType.newInstance();
				if(target instanceof Map){
					values.add(rowValues);
				}else{
					VoUtil.copyValuesNotNull(rowValues, target, false);
					values.add(target);					
				}
			}else{
				setSuccess(false);
				break;
			}
		}
		sheetValues.put(sheet.getSheetName(), values);
	}

	
	/**
	 * 读取一行后触发
	 * @param row
	 * @param rowIndex
	 * @return true:继续向后读取；false：停止读取
	 */
	public boolean afterReadRow(Map<String, Object> row, int rowIndex, Sheet sheet){
		return isSuccess();
	}
	
	
	/**
	 * 读取完每个sheet页触发
	 * @param sheet
	 * @return
	 */
	public boolean afterReadSheet(Sheet sheet){
		return isSuccess();
	}
	
	/**
	 * 读取完全后触发
	 */
	public void afterReadExcel(){
	}
	
	
	public void removeExcel(){
		this.excel.delete();
	}
	
	/**
	 * 设置返回值类型(多个表示多个sheet页面中)
	 * 
	 * @param valueTypes
	 */
	public void setValueTypes(List<Class<?>> valueTypes) {

	}

	/**
	 * 设置返回值类型
	 * 
	 * @param valueTypes
	 */
	public Class<?> setValueType() {
		return LinkedHashMap.class;
	}

	/**
	 * 设置excel表头与对象属性的映射关系
	 * 
	 * @param headAttributeMap
	 */
	public void mapHead2Attribute(List<Map<String, String>> headAttributeMap) {

	}

	/**
	 * 设置excel表头与对象属性的映射关系
	 * 
	 * @param headAttributeMap
	 */
	public Map<String, String> setHeadAttributeMap() {
		return null;
	}

	/**
	 * 设置每个sheet页从开始读取的索引,0开始
	 * 
	 * @return
	 */
	public int setStartRow() {
		return 1;
	}


	/**
	 * 设置读取Excel的表头信息,默认为每个sheet页的第一行数据
	 * @param sheetExcelHeads
	 */
	public void setSheetExcelHeads(List<List<String>> sheetExcelHeads) {
		if(this.workbook.getNumberOfSheets()>0){
			for(int i=0; i<workbook.getNumberOfSheets(); i++){
				Sheet sheet=this.workbook.getSheetAt(i);
				List<String> heads= getSheetHeads(sheet);
				sheetExcelHeads.add(heads);
			}
		}
	}

	/**
	 * 设置读取Excel的表头信息
	 * @return
	 */
	public List<String> setExcelHeads(){
		return null;
	}

	/**
	 * 手动设置是否继续读取
	 * @param success
	 */
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
	/**
	 * 获取消息设置
	 * @return
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * 设置消息
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
	/**
	 * 获取读取道的Excel值信息
	 * @return
	 */
	public final Map<String, List<Object>> getSheetValues() {
		return sheetValues;
	}
	
	/**
	 * 获取读取道的Excel值信息
	 * @return
	 */
	public final List<Object> getValues(){
		if(this.sheetValues.size()>0){
			return this.sheetValues.values().iterator().next();
		}
		return null;
	}
	
	
	private List<String> getSheetHeads(Sheet sheet) {
		if (sheet.getPhysicalNumberOfRows() > 0) {
			Row row = sheet.getRow(0);
			List<String> heads = new ArrayList<String>();
			for (int i = 0; i < row.getPhysicalNumberOfCells(); i++) {
				heads.add(String.valueOf(getCellStringValue(row.getCell(i))));
			}
			return heads;
		}
		return null;
	}

	
	public boolean isSuccess() {
		return success;
	}

	

	private Object getCellStringValue(Cell cell) {
		if(cell==null)
			return "";
		Object cellValue = "";
		switch (cell.getCellType()) {
		case HSSFCell.CELL_TYPE_STRING: {// 字符串类型
			cellValue = cell.getStringCellValue();
			if (cellValue.toString().trim().equals("")
					|| cellValue.toString().trim().length() <= 0)
				cellValue = "";
			break;
		}
		case HSSFCell.CELL_TYPE_NUMERIC: {// 数值类型
			if (HSSFDateUtil.isCellDateFormatted(cell)) {
				cellValue = cell.getDateCellValue(); // 把Date转换成本地格式的字符串
			} else {
				NumberFormat nf = new DecimalFormat("0");
				cellValue = nf.format(cell.getNumericCellValue());
			}
			break;
		}
		case HSSFCell.CELL_TYPE_FORMULA: {// 公式
			cellValue = cell.getStringCellValue();// cell.getCellFormula();
			break;
		}
		case HSSFCell.CELL_TYPE_BLANK: {
			cellValue = "";
			break;
		}
		case HSSFCell.CELL_TYPE_BOOLEAN: {
			cellValue = "";
			break;
		}
		case HSSFCell.CELL_TYPE_ERROR: {
			cellValue = "";
			break;
		}
		default:
			break;
		}
		return cellValue==null ? "" : cellValue;
	}

}
