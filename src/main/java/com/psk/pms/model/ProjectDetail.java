package com.psk.pms.model;

import java.util.Date;

public class ProjectDetail {
	
	private String projectName;
	private String aliasName;
	private String agreementNo;
	private String cerNo;
	private String Amount;	
	private String contractorName;
	private String contractorAddress;
	private String contractValue;
	private String agreementValue;
	private String tenderValue;
	private String exAmount;
	private String exPercentage;
	private Date tenderDate;
	private Date agreementDate;
	private Date commencementDate;
	private Date completionDate;
	private int agreementPeriod;
	private String employeeId;
		
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectNam(String projectName) {
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
		return Amount;
	}
	public void setAmount(String Amount) {
		this.Amount = Amount;
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
	public String getContractValue() {
		return contractValue;
	}
	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
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
	public Date getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
	}
	public Date getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = agreementDate;
	}
	public Date getCommencementDate() {
		return commencementDate;
	}
	public void setCommencementDate(Date commencementDate) {
		this.commencementDate = commencementDate;
	}
	public Date getCompletionDate() {
		return completionDate;
	}
	public void setCompletionDate(Date completionDate) {
		this.completionDate = completionDate;
	}
	public int getAgreementPeriod() {
		return agreementPeriod;
	}
	public void setAgreementPeriod(int agreementPeriod) {
		this.agreementPeriod = agreementPeriod;
	}
}
