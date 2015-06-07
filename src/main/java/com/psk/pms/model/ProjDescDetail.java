package com.psk.pms.model;

import java.util.Date;

public class ProjDescDetail {
	
	private String aliasProjectName;
	private String aliasSubProjectName;
	private String workType;
	private String quantityInFig;
	private String quantityInWords;
	private String description;	
	private String aliasDescription;
	private String rateInFig;
	private String rateInWords;
	private String projDescAmount;
	private String employeeId;
	private Integer projId;
	private Integer subProjId;
	private Integer projDescId;
	private String isUpdate;
	private boolean subProjectDesc;
	private String lastUpdatedBy ;
    private Date lastUpdatedAt;
	
	public String getAliasProjectName() {
		return aliasProjectName;
	}
	public void setAliasProjectName(String aliasProjectName) {
		this.aliasProjectName = aliasProjectName;
	}
	public String getAliasSubProjectName() {
		return aliasSubProjectName;
	}
	public void setAliasSubProjectName(String aliasSubProjectName) {
		this.aliasSubProjectName = aliasSubProjectName;
	}
	public String getWorkType() {
		return workType;
	}
	public void setWorkType(String workType) {
		this.workType = workType;
	}
	public String getQuantityInWords() {
		return quantityInWords;
	}
	public void setQuantityInWords(String quantityInWords) {
		this.quantityInWords = quantityInWords;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAliasDescription() {
		return aliasDescription;
	}
	public void setAliasDescription(String aliasDescription) {
		this.aliasDescription = aliasDescription;
	}
	public String getRateInWords() {
		return rateInWords;
	}
	public void setRateInWords(String rateInWords) {
		this.rateInWords = rateInWords;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Integer getProjId() {
		return projId;
	}
	public void setProjId(Integer projId) {
		this.projId = projId;
	}
	public Integer getSubProjId() {
		return subProjId;
	}
	public void setSubProjId(Integer subProjId) {
		this.subProjId = subProjId;
	}
	public Integer getProjDescId() {
		return projDescId;
	}
	public void setProjDescId(Integer projDescId) {
		this.projDescId = projDescId;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getQuantityInFig() {
		return quantityInFig;
	}
	public void setQuantityInFig(String quantityInFig) {
		this.quantityInFig = quantityInFig;
	}
	public String getRateInFig() {
		return rateInFig;
	}
	public void setRateInFig(String rateInFig) {
		this.rateInFig = rateInFig;
	}
	public String getProjDescAmount() {
		return projDescAmount;
	}
	public void setProjDescAmount(String projDescAmount) {
		this.projDescAmount = projDescAmount;
	}

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }
	public boolean isSubProjectDesc() {
		return subProjectDesc;
	}
	public void setSubProjectDesc(boolean subProjectDesc) {
		this.subProjectDesc = subProjectDesc;
	}
}

