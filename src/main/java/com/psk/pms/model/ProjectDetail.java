package com.psk.pms.model;

import java.util.Date;

public class ProjectDetail {
	
	private String project;
	private String alias;
	private String agreementNo;
	private String agreementValue;
	private Date agreementDate;
	private String agreementPeriod;
	private String estimationNo;
	private String estimationAmount;
	private String contractorName;
	private String contractorAddress;
	private Date tenderDate;
	private Date commencementDate;
	private Date completionDate;
	private String empId;
	
	private String subProject;
	private String won;
	private String area;
	private String requestDate;
	private Date requestCabDate;
	private String pickupTime;
	private String dropTime;
	
	public String getProject() {
		return project;
	}
	public void setProject(String project) {
		this.project = project;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public String getAgreementNo() {
		return agreementNo;
	}
	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
	public String getAgreementValue() {
		return agreementValue;
	}
	public void setAgreementValue(String agreementValue) {
		this.agreementValue = agreementValue;
	}
	public Date getAgreementDate() {
		return agreementDate;
	}
	public void setAgreementDate(Date agreementDate) {
		this.agreementDate = agreementDate;
	}
	public String getAgreementPeriod() {
		return agreementPeriod;
	}
	public void setAgreementPeriod(String agreementPeriod) {
		this.agreementPeriod = agreementPeriod;
	}
	public String getEstimationNo() {
		return estimationNo;
	}
	public void setEstimationNo(String estimationNo) {
		this.estimationNo = estimationNo;
	}
	public String getEstimationAmount() {
		return estimationAmount;
	}
	public void setEstimationAmount(String estimationAmount) {
		this.estimationAmount = estimationAmount;
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
	public Date getTenderDate() {
		return tenderDate;
	}
	public void setTenderDate(Date tenderDate) {
		this.tenderDate = tenderDate;
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
	public String getEmpId() {
		return empId;
	}
	public void setEmpId(String empId) {
		this.empId = empId;
	}
	
	public String getSubProject() {
		return subProject;
	}
	public void setSubProject(String subProject) {
		this.subProject = subProject;
	}
	public String getWon() {
		return won;
	}
	public void setWon(String won) {
		this.won = won;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getPickupTime() {
		return pickupTime;
	}
	public void setPickupTime(String pickupTime) {
		this.pickupTime = pickupTime;
	}
	public String getDropTime() {
		return dropTime;
	}
	public void setDropTime(String dropTime) {
		this.dropTime = dropTime;
	}
	public String getRequestDate() {
		return requestDate;
	}
	public void setRequestDate(String requestDate) {
		this.requestDate = requestDate;
	}
	public Date getRequestCabDate() {
		return requestCabDate;
	}
	public void setRequestCabDate(Date requestCabDate) {
		this.requestCabDate = requestCabDate;
	}
}
