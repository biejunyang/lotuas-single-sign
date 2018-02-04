package com.bjy.lotuas.common.vo;

/**
 * 分页参数读取辅助类
 * 
 */
public class PaginatedHelper {
	protected int page = 1;

	protected int rows;

	// 开始位置
	protected int startIndex;

	public static final int PAGE_SIZE = 10;
	
	//排序字段
	protected String sort;
	
	//升序 还是降序
	protected String order;
	/**
	 * 获取当前页
	 * 
	 * @return
	 */
	public int getCurrentPage() {
		return page;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}
	public void setRows(int rows) {
		if (rows > 0) {
			this.rows = rows;
		} else {
			this.rows = PAGE_SIZE;
		}
	}

	// 计算查询开始位置
	private void calculatestartIndex() {
		if (this.page > 0) {
		} else {
			page = 1;
		}
		this.startIndex = (this.page - 1) * this.rows;
	}

	/**
	 * 取得开始位置
	 * 
	 * @return
	 */
	public int getStartIndex() {
		calculatestartIndex();
		return startIndex;
	}

	public int getPageSize() {
		if (rows < 1) {
			this.rows = PAGE_SIZE;
		}
		return this.rows;
	}
	public int getRows() {
		return rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	
	
}
