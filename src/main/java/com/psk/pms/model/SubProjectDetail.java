package com.psk.pms.model;

import java.util.Date;

public class SubProjectDetail {
	
	private String aliasSubProjName;
	private String aliasProjName;	
	private Integer projId;
	private Integer subProjId;
	private String subProjectName;
	private String subAgreementNo;
	private String subCerNo;
	private String subAmount;	
	private String subContractorName;
	private String subContractorAddress;
	private String subContractValue;
	private String subAgreementValue;
	private String subTenderValue;
	private String subExAmount;
	private String subExPercentage;
	private String subTenderDate;
	private String subAgreementDate;
	private String subCommencementDate;
	private String subCompletionDate;
	private Date subTenderSqlDate;
	private Date subAgreementSqlDate;
	private Date subCommencementSqlDate;
	private Date subCompletionSqlDate;
	private int subAgreementPeriod;
	private String employeeId;
	private String isUpdate;
    private String lastUpdatedBy ;
    private Date lastUpdatedAt;
    private String subAddSecurityDeposit;

	public String getSubProjectName() {
		return subProjectName;
	}
	public void setSubProjectName(String subProjectName) {
		this.subProjectName = subProjectName;
	}
	public String getSubAgreementNo() {
		return subAgreementNo;
	}
	public void setSubAgreementNo(String subAgreementNo) {
		this.subAgreementNo = subAgreementNo;
	}
	public String getSubCerNo() {
		return subCerNo;
	}
	public void setSubCerNo(String subCerNo) {
		this.subCerNo = subCerNo;
	}
	public String getSubAmount() {
		return subAmount;
	}
	public void setSubAmount(String SubAmount) {
		subAmount = SubAmount;
	}
	public String getSubContractorName() {
		return subContractorName;
	}
	public void setSubContractorName(String subContractorName) {
		this.subContractorName = subContractorName;
	}
	public String getSubContractorAddress() {
		return subContractorAddress;
	}
	public void setSubContractorAddress(String subContractorAddress) {
		this.subContractorAddress = subContractorAddress;
	}
	public String getSubContractValue() {
		return subContractValue;
	}
	public void setSubContractValue(String subContractValue) {
		this.subContractValue = subContractValue;
	}
	public String getSubAgreementValue() {
		return subAgreementValue;
	}
	public void setSubAgreementValue(String subAgreementValue) {
		this.subAgreementValue = subAgreementValue;
	}
	public String getSubTenderValue() {
		return subTenderValue;
	}
	public void setSubTenderValue(String subTenderValue) {
		this.subTenderValue = subTenderValue;
	}
	public String getSubExAmount() {
		return subExAmount;
	}
	public void setSubExAmount(String subExAmount) {
		this.subExAmount = subExAmount;
	}
	public String getSubExPercentage() {
		return subExPercentage;
	}
	public void setSubExPercentage(String subExPercentage) {
		this.subExPercentage = subExPercentage;
	}
	public String getSubTenderDate() {
		return subTenderDate;
	}
	public void setSubTenderDate(String subTenderDate) {
		this.subTenderDate = subTenderDate;
	}
	public String getSubAgreementDate() {
		return subAgreementDate;
	}
	public void setSubAgreementDate(String subAgreementDate) {
		this.subAgreementDate = subAgreementDate;
	}
	public String getSubCommencementDate() {
		return subCommencementDate;
	}
	public void setSubCommencementDate(String subCommencementDate) {
		this.subCommencementDate = subCommencementDate;
	}
	public String getSubCompletionDate() {
		return subCompletionDate;
	}
	public void setSubCompletionDate(String subCompletionDate) {
		this.subCompletionDate = subCompletionDate;
	}
	public Date getSubTenderSqlDate() {
		return subTenderSqlDate;
	}
	public void setSubTenderSqlDate(Date subTenderSqlDate) {
		this.subTenderSqlDate = subTenderSqlDate;
	}
	public Date getSubAgreementSqlDate() {
		return subAgreementSqlDate;
	}
	public void setSubAgreementSqlDate(Date subAgreementSqlDate) {
		this.subAgreementSqlDate = subAgreementSqlDate;
	}
	public Date getSubCommencementSqlDate() {
		return subCommencementSqlDate;
	}
	public void setSubCommencementSqlDate(Date subCommencementSqlDate) {
		this.subCommencementSqlDate = subCommencementSqlDate;
	}
	public Date getSubCompletionSqlDate() {
		return subCompletionSqlDate;
	}
	public void setSubCompletionSqlDate(Date subCompletionSqlDate) {
		this.subCompletionSqlDate = subCompletionSqlDate;
	}
	public int getSubAgreementPeriod() {
		return subAgreementPeriod;
	}
	public void setSubAgreementPeriod(int subAgreementPeriod) {
		this.subAgreementPeriod = subAgreementPeriod;
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
	public String getAliasSubProjName() {
		return aliasSubProjName;
	}
	public void setAliasSubProjName(String aliasSubProjName) {
		this.aliasSubProjName = aliasSubProjName;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getAliasProjName() {
		return aliasProjName;
	}
	public void setAliasProjName(String aliasProjName) {
		this.aliasProjName = aliasProjName;
	}

    public Date getLastUpdatedAt() {
        return lastUpdatedAt;
    }

    public void setLastUpdatedAt(Date lastUpdatedAt) {
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getSubAddSecurityDeposit() {
        return subAddSecurityDeposit;
    }

    public void setSubAddSecurityDeposit(String subAddSecurityDeposit) {
        this.subAddSecurityDeposit = subAddSecurityDeposit;
    }
}

