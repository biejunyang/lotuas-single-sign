package com.bjy.lotuas.common.excel.read;

import java.lang.reflect.Method;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.bjy.lotuas.common.excel.DefExcelException;
import com.bjy.lotuas.common.util.VoUtil;

public class ReadSheet {
	private Log log4j = LogFactory.getLog(this.getClass());
	
	private int sheetIndex;
	private Class<?> clazz = null;
	private String[] fieldNames = null;
	private int readStartRow = 0;
	private Sheet sheet = null;
	private boolean initFlag = false;
	private String errorMessage = null;
	private List<Object> entitys = new ArrayList<Object>();
	private int currentIndex = 0;
	private boolean isMap = false;
	
	private final static SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	private final static NumberFormat nf = new DecimalFormat("0");

	public ReadSheet(Sheet sheet,int sheetIndex,Class<?> clazz, String[] fieldNames,int readStartRow) {
		super();
		this.sheet = sheet;
		this.sheetIndex = sheetIndex;
		this.clazz = clazz;
		this.isMap = (clazz==Map.class);
		this.fieldNames = fieldNames;
		this.readStartRow = readStartRow;
		this.currentIndex = this.readStartRow;
		try {
			this.analyse();
		} catch (DefExcelException e) {
			e.printStackTrace();
			this.setEerrorInfoAndLog(e.getMessage());
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void analyse() throws DefExcelException {
		int lastIndex = this.sheet.getPhysicalNumberOfRows();
		while (this.currentIndex < lastIndex) {
			Row row = this.sheet.getRow(this.currentIndex);
			int rows = row.getPhysicalNumberOfCells();
			Object bean = null;
			try {
				bean = this.isMap?new HashMap<String,Object>():clazz.newInstance();
			} catch (Exception e) {
				this.setEerrorInfoAndLog("Excel表格中的数据转换失败");
				throw new DefExcelException(this.errorMessage, e);
			}
			for (int j = 0; j < rows; j++) {
				Cell cell = row.getCell(j);
				if (cell == null) {
					this.setEerrorInfoAndLog("Excel表格中的数据初始化失败，请检查第"+this.sheetIndex+"个Sheet页中"+"第 "+ (this.currentIndex + 1) + "行 ，第" + (j + 1)+ "列的数据！");
					throw new DefExcelException(this.errorMessage);
				}
				Object value = this.getCellStringValue(cell);
				if(this.isMap){
					((Map)bean).put(this.fieldNames[j], value);
				}else{
					if ("".equals(value)) {
						this.setEerrorInfoAndLog("Excel表格中的数据初始化失败，请检查第"+this.sheetIndex+"个Sheet页中"+"第 "+ (this.currentIndex + 1) + "行 ，第" + (j + 1)+ "列的数据不能为空！");
						throw new DefExcelException(this.errorMessage);
					} else {
						boolean ye = false;
						try {
							Class<?> clazz = VoUtil.getPropertyType(bean,this.fieldNames[j]);
							Object temp = null;
							if (clazz == Integer.class || clazz == int.class) {
								if ("-".equals(value.toString())) {
									temp = null;
								} else {
									temp = this.getNumber(value).intValue();
								}
								ye = true;
							} else if (clazz == Long.class || clazz == long.class) {
								temp = this.getNumber(value).longValue();
								ye = true;
							} else if (clazz == float.class || clazz == Float.class) {
								temp = Float.parseFloat(value.toString());
								ye = true;
							} else if (clazz == Double.class|| clazz == double.class) {
								temp = Double.parseDouble(value.toString());
							} else if (clazz == java.util.Date.class|| clazz == java.sql.Date.class) {
								if (value instanceof java.util.Date|| value instanceof java.sql.Date) {
									temp = value;
								} else {
									temp = sf.parse(value.toString());
								}
								ye = true;
							} else if(clazz == String.class){
								temp = value;
								ye = true;
							}else{
								temp = value;
							}
							if(ye){
								Method writer = VoUtil.getPropertyDescriptor(bean, this.fieldNames[j]).getWriteMethod();
								writer.invoke(bean, new Object[]{temp});	
							}else{
								BeanUtils.setProperty(bean, this.fieldNames[j], temp);
							}
						} catch (Exception e) {
							this.setEerrorInfoAndLog("Excel表格中的数据初始化失败，请检查第"+this.sheetIndex+"个Sheet页中"+"第 "+ (this.currentIndex + 1) + "行 ，第" + (j + 1)+ "列的数据！");
							throw new DefExcelException(this.errorMessage, e);
						}
					}	
				}
			}
			this.entitys.add(bean);
			this.currentIndex++;
		}
		this.initFlag = true;
	}

	private Number getNumber(Object value) throws ParseException {
		return value == null ? null : nf.parse(value.toString());
	}

	private void setEerrorInfoAndLog(String message) {
		this.initFlag = false;
		this.errorMessage = message;
		log4j.error(message);
	}

	private Object getCellStringValue(Cell cell) {
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
		return cellValue;
	}

	public boolean isInitFlag() {
		return initFlag;
	}

	public List<Object> getEntitys() {
		return entitys;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
}
