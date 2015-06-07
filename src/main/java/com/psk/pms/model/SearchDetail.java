package com.psk.pms.model;

public class SearchDetail {
	
	private String aliasProjectName;
	private Integer projId;
	
	public SearchDetail(){
		
	}
	
	public SearchDetail(int projId, String aliasProjectName) {
		this.projId = projId;
		this.aliasProjectName = aliasProjectName;
	}
	
	public String getAliasProjectName() {
		return aliasProjectName;
	}
	public void setAliasProjectName(String aliasProjectName) {
		this.aliasProjectName = aliasProjectName;
	}
	public Integer getProjId() {
		return projId;
	}
	public void setProjId(Integer projId) {
		this.projId = projId;
	}

}
