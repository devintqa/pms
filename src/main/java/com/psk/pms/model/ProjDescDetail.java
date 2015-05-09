package com.psk.pms.model;

public class ProjDescDetail {
	
	private String aliasProjectName;
	private String aliasSubProjectName;
	private String workType;
	private int quantityInFig;
	private String quantityInWords;
	private String description;	
	private String aliasDescription;
	private int rateInFig;
	private String rateInWords;
	private Integer projDescAmount;
	private String employeeId;
	private Integer projId;
	private Integer subProjId;
	private Integer ProjDescId;
	
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
	public int getQuantityInFig() {
		return quantityInFig;
	}
	public void setQuantityInFig(int quantityInFig) {
		this.quantityInFig = quantityInFig;
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
	public int getRateInFig() {
		return rateInFig;
	}
	public void setRateInFig(int rateInFig) {
		this.rateInFig = rateInFig;
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
		return ProjDescId;
	}
	public void setProjDescId(Integer projDescId) {
		ProjDescId = projDescId;
	}
	public Integer getProjDescAmount() {
		return projDescAmount;
	}
	public void setProjDescAmount(Integer projDescAmount) {
		this.projDescAmount = projDescAmount;
	}
	
}

