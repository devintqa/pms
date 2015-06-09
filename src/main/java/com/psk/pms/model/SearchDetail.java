package com.psk.pms.model;

import java.util.Map;

public class SearchDetail {
	
	private String aliasProjectName;
	private Integer projId;
	private boolean editProject;
	private boolean searchProjectDescription;

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
	public boolean isEditProject() {
		return editProject;
	}
	public void setEditProject(boolean editProject) {
		this.editProject = editProject;
	}
	public boolean isSearchProjectDescription() {
		return searchProjectDescription;
	}
	public void setSearchProjectDescription(boolean searchProjectDescription) {
		this.searchProjectDescription = searchProjectDescription;
	}
	

}
