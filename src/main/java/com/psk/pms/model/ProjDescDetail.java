package com.psk.pms.model;

import java.util.Date;

public class ProjDescDetail {
	
	private String aliasProjectName;
	private String aliasSubProjectName;
	private String serialNumber;
	private String workType;
	private String quantity;
	private String description;	
	private String aliasDescription;
	private String pricePerQuantity;
	private String totalCost;
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

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getPricePerQuantity() {
		return pricePerQuantity;
	}
	public void setPricePerQuantity(String pricePerQuantity) {
		this.pricePerQuantity = pricePerQuantity;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
}

