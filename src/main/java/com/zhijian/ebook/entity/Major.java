package com.zhijian.ebook.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class Major {
    private String id;

    private String collegeName;

    private String academyName;
    
    private Map<String, List<String>> academyList;

    private String professionName;

    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getCollegeName() {
        return collegeName;
    }

    public void setCollegeName(String collegeName) {
        this.collegeName = collegeName == null ? null : collegeName.trim();
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName == null ? null : academyName.trim();
    }

    public String getProfessionName() {
        return professionName;
    }

    public void setProfessionName(String professionName) {
        this.professionName = professionName == null ? null : professionName.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

	public Map<String, List<String>> getAcademyList() {
		return academyList;
	}

	public void setAcademyList(Map<String, List<String>> academyList) {
		this.academyList = academyList;
	}

	
}