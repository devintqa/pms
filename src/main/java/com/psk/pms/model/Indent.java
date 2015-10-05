package com.psk.pms.model;

import java.util.Date;
import java.util.List;

public class Indent {
	private String projId;
	private String indentId;
	private String startDate;
	private String endDate;
	private String employeeId;
	private Date lastUpdateAt;
	private List<IndentDesc> indentDescList;
	
	public String getIndentId() {
		return indentId;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public Date getLastUpdateAt() {
		return lastUpdateAt;
	}
	public void setLastUpdateAt(Date lastUpdateAt) {
		this.lastUpdateAt = lastUpdateAt;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public List<IndentDesc> getIndentDescList() {
		return indentDescList;
	}
	public void setIndentDescList(List<IndentDesc> indentDescList) {
		this.indentDescList = indentDescList;
	}
}
