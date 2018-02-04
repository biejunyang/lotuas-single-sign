package com.bjy.lotuas.common.excel.write;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFClientAnchor;
import org.apache.poi.hssf.usermodel.HSSFComment;
import org.apache.poi.hssf.usermodel.HSSFPatriarch;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Comment;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import com.bjy.lotuas.common.excel.DefExcelException;
import com.bjy.lotuas.common.exception.VOCastException;
import com.bjy.lotuas.common.util.VoUtil;

public class WriteSheet {

	private final static int DEFAULT_COLUMN_WIDTH = 25;
	private Workbook workbook = null;
	private List<ExcelHead> excelHeads = null;
	private Collection<Object> dataset = null;
	private String pattern = null;
	private int currentIndex;
	private Sheet sheet = null;
	
	/**
	 * 记录下要进行宽度适应的列索引(0开始)
	 * 别君羊
	 */
	private List<Integer> autoSizeColumns=new ArrayList<Integer>();
	
	public WriteSheet(Workbook workbook,Sheet sheet, List<ExcelHead> excelHeads,
			Collection<Object> dataset, String pattern, int currentIndex) throws DefExcelException {
		super();
		this.workbook = workbook;
		this.sheet = sheet;
		this.excelHeads = excelHeads;
		this.dataset = dataset;
		this.pattern = pattern;
		this.currentIndex = currentIndex;
		this.prepare();
	}
	
	private void prepare() throws DefExcelException {
		try {
			this.autoSizeColumns.clear();//清空集合
			this.sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
			this.writeHead();
			this.writeContents();
			
			/**
			 * 使需要进行宽度适应的列宽度适应
			 * 别君羊
			 */
			for(int val: this.autoSizeColumns){
				this.sheet.autoSizeColumn(val);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new DefExcelException(e);
		}
	}
	
	private void writeHead(){
		Row row = sheet.createRow(this.currentIndex);
		Cell cell = null;
		int i = 0;
		for (ExcelHead excelHead : this.excelHeads) {
			 CellStyle cellStyle = this.workbook.createCellStyle();         
             Font font = this.workbook.createFont();     
             font.setFontName("宋体");
             font.setBoldweight(Font.BOLDWEIGHT_BOLD); 
             cellStyle.setFont(font);
             cell = row.createCell(i);
             cell.setCellStyle(cellStyle);
			 cell.setCellValue(excelHead.getHeadValue());
			 
			 /**
			  * 将需要宽度适应的列的索引添加到集合中
			  */
			 if(excelHead.isAutoSizeColumn()){
				 autoSizeColumns.add(i);
			 }
			 cell.setCellComment(this.createComment(excelHead.getComment()));
			i++;
		}
		this.currentIndex++;
	}
	
	private void writeContents() throws VOCastException {
		if (this.dataset != null) {
			for (Object o : this.dataset) {
				Row row = sheet.createRow(this.currentIndex);
				this.createCellAndSetValue(row, o);
				this.currentIndex++;
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void createCellAndSetValue(Row row, Object bean)
			throws VOCastException {
		Cell cell = null;
		int i = 0;
		for (ExcelHead excelHead : this.excelHeads) {
			String field = excelHead.getFiledName();
			cell = row.createCell(i);
			CellStyle cellStyle = null;
			cellStyle = this.workbook.createCellStyle();
			cellStyle.setWrapText(true);
			cell.setCellStyle(cellStyle);
			Class<?> clazz = null;
			Object value = null;
			if(bean instanceof Map){
				value  = ((Map) bean).get(field);
				if(value == null){
					clazz = String.class;	
				}else{
					clazz = value.getClass();
				}
			}else{
				clazz = VoUtil.getPropertyType(bean, field);
				value = VoUtil.getPropertyValue(bean, field);	
			}
			String o = null;
			if (clazz == java.util.Date.class || clazz == java.sql.Date.class) {
				if (value == null) {
					cell.setCellValue("-");
				} else {
					cellStyle = this.workbook.createCellStyle();
					DataFormat format = this.workbook.createDataFormat();
					cellStyle.setDataFormat(format.getFormat(this.pattern));
					cell.setCellStyle(cellStyle);
					cell.setCellValue((Date) value);
				}
			} else if (clazz == Integer.class || clazz == int.class
					|| clazz == Long.class || clazz == long.class) {
				o = String.valueOf(value);
				cell.setCellValue(o);
			} else if (clazz == Float.class || clazz == float.class
					|| clazz == Double.class || clazz == double.class) {
				o = String.valueOf(value);
				cell.setCellValue(o);
			} else {
				o = String.valueOf(value);
				if(o.equals("null")){
					o="";
				}
				cell.setCellValue(o);
			}
			i++;
		}
	}
	
	private Comment createComment(String content){
		if(this.sheet instanceof XSSFSheet){
			@SuppressWarnings("deprecation")
			XSSFComment comment=((XSSFSheet)this.sheet).createComment();
			comment.setString(content);
			return comment;
		}else{
			HSSFPatriarch p=((HSSFSheet)this.sheet).createDrawingPatriarch();
			HSSFComment comment=p.createComment(new HSSFClientAnchor(0,0,0,0,(short)3,3,(short)5,6));
			comment.setString(new HSSFRichTextString(content));
			return comment;
		}
	}
}
