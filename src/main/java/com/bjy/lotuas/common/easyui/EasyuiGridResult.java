package com.bjy.lotuas.common.easyui;

import java.io.Serializable;

public class EasyuiGridResult implements Serializable
{
	private static final long serialVersionUID = -6258486204774120793L;
	
	private long total;
	private Object rows;
	
	public EasyuiGridResult()
	{
		// TODO Auto-generated constructor stub
	}
	
	public EasyuiGridResult(Object rows, long total)
	{
		this.rows=rows;
		this.total=total;
	}

	
	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public Object getRows()
	{
		return rows;
	}

	public void setRows(Object rows)
	{
		this.rows = rows;
	}
	
	
}
