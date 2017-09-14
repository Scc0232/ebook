package com.zhijian.ebook.bean;

public class Page {
	private int startRow;
	private int endRow;
	
	public Page(int page,int rows){
		 startRow = (page-1)*rows;
		 endRow = rows;
	} 

	public int getStartRow() {
		return startRow;
	}

	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	public int getEndRow() {
		return endRow;
	}

	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
}
