package com.psk.pms.model;

import java.util.Date;

public class EmdDetail {
	private int emdId;
	private int projId;
	private int subProjId;
	private String emdAmount;
	private String emdStartDate;
	private Date sqlEmdStartDate;
	private String emdEndDate;
	private Date sqlEmdEndDate;
	private String emdType;
	private String bgNumber;
	private int emdPeriod;
	private String emdExtensionDate;
	private Date emdExtensionSqlDate;
	private String emdLedgerNumber;
	private String employeeId;
	private String isUpdate;
	private boolean subProjectEMD;
	private String aliasProjectName;
	private String aliasSubProjectName;
	private String lastUpdatedBy ;
	private Date lastUpdatedAt;
	private String emdSubmitter;
	private String emdFor;
	
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
	public boolean isSubProjectEMD() {
		return subProjectEMD;
	}
	public void setSubProjectEMD(boolean subProjectEMD) {
		this.subProjectEMD = subProjectEMD;
	}
	public int getProjId() {
		return projId;
	}
	public void setProjId(int projId) {
		this.projId = projId;
	}
	public int getSubProjId() {
		return subProjId;
	}
	public void setSubProjId(int subProjId) {
		this.subProjId = subProjId;
	}
	public String getEmdAmount() {
		return emdAmount;
	}
	public void setEmdAmount(String emdAmount) {
		this.emdAmount = emdAmount;
	}
	public String getEmdStartDate() {
		return emdStartDate;
	}
	public void setEmdStartDate(String emdStartDate) {
		this.emdStartDate = emdStartDate;
	}
	public String getEmdEndDate() {
		return emdEndDate;
	}
	public void setEmdEndDate(String emdEndDate) {
		this.emdEndDate = emdEndDate;
	}
	public String getEmdType() {
		return emdType;
	}
	public void setEmdType(String emdType) {
		this.emdType = emdType;
	}
	public String getBgNumber() {
		return bgNumber;
	}
	public void setBgNumber(String bgNumber) {
		this.bgNumber = bgNumber;
	}
	public int getEmdPeriod() {
		return emdPeriod;
	}
	public void setEmdPeriod(int emdPeriod) {
		this.emdPeriod = emdPeriod;
	}
	public String getEmdExtensionDate() {
		return emdExtensionDate;
	}
	public void setEmdExtensionDate(String emdExtensionDate) {
		this.emdExtensionDate = emdExtensionDate;
	}
	public Date getEmdExtensionSqlDate() {
		return emdExtensionSqlDate;
	}
	public void setEmdExtensionSqlDate(Date emdExtensionSqlDate) {
		this.emdExtensionSqlDate = emdExtensionSqlDate;
	}
	public String getEmdLedgerNumber() {
		return emdLedgerNumber;
	}
	public void setEmdLedgerNumber(String emdLedgerNumber) {
		this.emdLedgerNumber = emdLedgerNumber;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
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

	public int getEmdId() {
		return emdId;
	}

	public void setEmdId(int emdId) {
		this.emdId = emdId;
	}

	public Date getSqlEmdStartDate() {
		return sqlEmdStartDate;
	}

	public void setSqlEmdStartDate(Date sqlEmdStartDate) {
		this.sqlEmdStartDate = sqlEmdStartDate;
	}

	public Date getSqlEmdEndDate() {
		return sqlEmdEndDate;
	}

	public String getEmdSubmitter() {
		return emdSubmitter;
	}

	public void setEmdSubmitter(String emdSubmitter) {
		this.emdSubmitter = emdSubmitter;
	}

	public void setSqlEmdEndDate(Date sqlEmdEndDate) {
		this.sqlEmdEndDate = sqlEmdEndDate;
	}

	public String getEmdFor() {
		return emdFor;
	}

	public void setEmdFor(String emdFor) {
		this.emdFor = emdFor;
	}
}
