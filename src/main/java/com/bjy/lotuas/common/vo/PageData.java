package com.bjy.lotuas.common.vo;

import java.util.List;

public class PageData<T>
{
	private List<T> records;
	private long totalCount;
	
	public PageData()
	{
		// TODO Auto-generated constructor stub
	}
	
	public PageData(List<T> records, long totalCount)
	{
		this.records=records;
		this.totalCount=totalCount;
	}
	
	public List<T> getRecords()
	{
		return records;
	}
	public void setRecords(List<T> records)
	{
		this.records = records;
	}

	public long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	
	
}
