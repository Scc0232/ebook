package com.zhijian.ebook.bean;

import java.util.List;

/**
 * 
 * 分页
 * 
 * @author Administrator
 *
 */
public class EasyuiPagination<T> {

	public EasyuiPagination() {
		super();
	}

	public EasyuiPagination(Integer total, List<T> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

	private Integer total;

	private List<T> rows;

	public Integer getTotal() {
		return total;
	}

	public void setTotal(Integer total) {
		this.total = total;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

}
