package com.psk.pms.model;

import java.util.Date;

public class ProjectDetail {
	
	private int projId;
	private String projectName;
    private String projectType;
	private String aliasName;
	private String agreementNo;
	private String cerNo;
	private String amount;	
	private String contractorName;
	private String aliasContractorName;
	private String contractorAddress;
	private String agreementValue;
	private String tenderValue;
	private String exAmount;
	private String exPercentage;
	private String lessPercentage;
	private String tenderDate;
	private String agreementDate;
	private String commencementDate;
	private String completionDate;
    private String completionDateForBonus;
    private Date completionDateSqlForBonus;
	private Date tenderSqlDate;
	private Date agreementSqlDate;
	private Date commencementSqlDate;
	private Date completionSqlDate;
	private int agreementPeriod;
	private String employeeId;
	private String isUpdate;
	private String projectStatus;
    private String lastUpdatedBy ;
    private Date lastUpdatedAt;
    private String addSecurityDeposit;
	private String performanceGuarantee;
	

	public String getProjectStatus() {
		return projectStatus;
	}
	public void setProjectStatus(String projectStatus) {
		this.projectStatus = projectStatus;
	}
	public String getIsUpdate() {
		return isUpdate;
	}
	public void setIsUpdate(String isUpdate) {
		this.isUpdate = isUpdate;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getAliasName() {
		return aliasName;
	}
	public void setAliasName(String aliasName) {
		this.aliasName = aliasName;
	}
	public String getAgreementNo() {
		return agreementNo;
	}
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	public String getCerNo() {
		return cerNo;
	}
	public void setCerNo(String cerNo) {
		this.cerNo = cerNo;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getContractorName() {
		return contractorName;
	}
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}
	public String getContractorAddress() {
		return contractorAddress;
	}
	public void setContractorAddress(String contractorAddress) {
		this.contractorAddress = contractorAddress;
	}

	public String getAgreementValue() {
		return agreementValue;
	}
	public void setAgreementValue(String agreementValue) {
		this.agreementValue = agreementValue;
	}
	public String getTenderValue() {
		return tenderValue;
	}
	public void setTenderValue(String tenderValue) {
		this.tenderValue = tenderValue;
	}
	public String getExAmount() {
		return exAmount;
	}
	public void setExAmount(String exAmount) {
		this.exAmount = exAmount;
	}
	public String getExPercentage() {
		return exPercentage;
	}
	public void setExPercentage(String exPercentage) {
		this.exPercentage = exPercentage;
	}
	public String getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(String tenderDate) {
		this.tenderDate = tenderDate;
	}
	public String getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(String agreementDate) {
		this.agreementDate = agreementDate;
	}
	public String getCommencementDate() {
		return commencementDate;
	}
	public void setCommencementDate(String commencementDate) {
		this.commencementDate = commencementDate;
	}
	public String getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(String completionDate) {
		this.completionDate = completionDate;
	}
	public Date getTenderSqlDate() {
		return tenderSqlDate;
	}
	public void setTenderSqlDate(Date tenderSqlDate) {
		this.tenderSqlDate = tenderSqlDate;
	}
	public Date getAgreementSqlDate() {
		return agreementSqlDate;
	}
	public void setAgreementSqlDate(Date agreementSqlDate) {
		this.agreementSqlDate = agreementSqlDate;
	}
	public Date getCommencementSqlDate() {
		return commencementSqlDate;
	}
	public void setCommencementSqlDate(Date commencementSqlDate) {
		this.commencementSqlDate = commencementSqlDate;
	}
	public Date getCompletionSqlDate() {
		return completionSqlDate;
	}
	public void setCompletionSqlDate(Date completionSqlDate) {
		this.completionSqlDate = completionSqlDate;
	}
	public int getAgreementPeriod() {
		return agreementPeriod;
	}
	public void setAgreementPeriod(int agreementPeriod) {
		this.agreementPeriod = agreementPeriod;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
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

    public String getAddSecurityDeposit() {
        return addSecurityDeposit;
    }

    public void setAddSecurityDeposit(String addSecurityDeposit) {
        this.addSecurityDeposit = addSecurityDeposit;
    }

	public String getAliasContractorName() {
		return aliasContractorName;
	}

	public void setAliasContractorName(String aliasContractorName) {
		this.aliasContractorName = aliasContractorName;
	}

	public String getLessPercentage() {
		return lessPercentage;
	}

	public void setLessPercentage(String lessPercentage) {
		this.lessPercentage = lessPercentage;
	}

	public String getPerformanceGuarantee() {
		return performanceGuarantee;
	}

	public void setPerformanceGuarantee(String performanceGuarantee) {
		this.performanceGuarantee = performanceGuarantee;
	}

    public String getCompletionDateForBonus() {
        return completionDateForBonus;
    }

    public void setCompletionDateForBonus(String completionDateForBonus) {
        this.completionDateForBonus = completionDateForBonus;
    }

    public Date getCompletionDateSqlForBonus() {
        return completionDateSqlForBonus;
    }

    public void setCompletionDateSqlForBonus(Date completionDateSqlForBonus) {
        this.completionDateSqlForBonus = completionDateSqlForBonus;
    }

    public String getProjectType() {
        return projectType;
    }

    public void setProjectType(String projectType) {
        this.projectType = projectType;
    }
}
