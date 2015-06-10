package com.psk.pms.model;

public class SearchDetail {
	
	private String aliasProjectName;
	private Integer projId;
	private boolean editProject;
	private boolean editSubProject;
	private boolean searchProjectDescription;

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
	public boolean isEditSubProject() {
		return editSubProject;
	}
	public void setEditSubProject(boolean editSubProject) {
		this.editSubProject = editSubProject;
	}
	

}
