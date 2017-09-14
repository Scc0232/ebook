package com.zhijian.ebook.bean;

import java.io.Serializable;

public class IndustryAnalysisYearBetCount implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2725084335276772075L;
	private String key;
	private String industry;
    private int count;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getIndustry() {
		return industry;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
    
}
