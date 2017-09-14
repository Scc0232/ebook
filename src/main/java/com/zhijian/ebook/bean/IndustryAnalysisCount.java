package com.zhijian.ebook.bean;

import java.io.Serializable;

/**
 * Created by conlin on 2016/4/28.
 */
public class IndustryAnalysisCount implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 2552790175939498797L;
	private Integer year;
    private String industry;
    private int count;

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
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
