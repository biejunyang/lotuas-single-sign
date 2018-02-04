package com.bjy.lotuas.common.excel.write;

public class ExcelHead {
	private String filedName;
	private String headValue;
	private String comment;
	private boolean autoSizeColumn=false;

	public ExcelHead(String filedName, String headValue) {
		super();
		this.filedName = filedName;
		this.headValue = headValue;
	}
	
	
	public ExcelHead(String filedName, String headValue, String comment) {
		super();
		this.filedName = filedName;
		this.headValue = headValue;
		this.comment = comment;
	}

	public ExcelHead(String filedName, String headValue, boolean autoSizeColumn) {
		super();
		this.filedName = filedName;
		this.headValue = headValue;
		this.autoSizeColumn = autoSizeColumn;
	}
	
	public String getFiledName() {
		return filedName;
	}
	public void setFiledName(String filedName) {
		this.filedName = filedName;
	}

	public String getHeadValue() {
		return headValue;
	}
	
	public void setHeadValue(String headValue) {
		this.headValue = headValue;
	}
	public boolean isAutoSizeColumn() {
		return autoSizeColumn;
	}
	public void setAutoSizeColumn(boolean autoSizeColumn) {
		this.autoSizeColumn = autoSizeColumn;
	}


	public String getComment() {
		return comment;
	}


	public void setComment(String comment) {
		this.comment = comment;
	}
}
