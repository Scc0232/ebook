package com.zhijian.ebook.base.entity;

public class Occupation {
	/**
	 * id
	 */
    private String id;
    /**
     * 职业名称
     */
    private String occupationName;
    /**
     * 父职业的id
     */
    private String fOccupationId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOccupationName() {
        return occupationName;
    }

    public void setOccupationName(String occupationName) {
        this.occupationName = occupationName == null ? null : occupationName.trim();
    }

    public String getfOccupationId() {
        return fOccupationId;
    }

    public void setfOccupationId(String fOccupationId) {
        this.fOccupationId = fOccupationId == null ? null : fOccupationId.trim();
    }
}