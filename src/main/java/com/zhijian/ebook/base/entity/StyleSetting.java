package com.zhijian.ebook.base.entity;

public class StyleSetting {
    private String id;

    private String orgId;

    private String cssResourceUrl;
    
    private Organization organization;
    
    private String orgName;

    

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Organization getOrganization() {
		return organization;
	}

	public void setOrganization(Organization organization) {
		this.organization = organization;
	}

	public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId == null ? null : orgId.trim();
    }

    public String getCssResourceUrl() {
        return cssResourceUrl;
    }

    public void setCssResourceUrl(String cssResourceUrl) {
        this.cssResourceUrl = cssResourceUrl == null ? null : cssResourceUrl.trim();
    }
}