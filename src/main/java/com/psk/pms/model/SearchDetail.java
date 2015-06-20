package com.psk.pms.model;

public class SearchDetail {

	private String aliasProjectName;
	private Integer projId;
	private String searchProject;
	private String searchSubProject;
	private String searchProjectDesc;
	private boolean editProject;
	private boolean editSubProject;
	private boolean searchProjectDescription;
	private String searchUnder;

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
	public String getSearchProject() {
		return searchProject;
	}
	public void setSearchProject(String searchProject) {
		this.searchProject = searchProject;
	}
	public String getSearchSubProject() {
		return searchSubProject;
	}
	public void setSearchSubProject(String searchSubProject) {
		this.searchSubProject = searchSubProject;
	}
	public String getSearchProjectDesc() {
		return searchProjectDesc;
	}
	public void setSearchProjectDesc(String searchProjectDesc) {
		this.searchProjectDesc = searchProjectDesc;
	}

    public String getSearchUnder() {
        return searchUnder;
    }

    public void setSearchUnder(String searchUnder) {
        this.searchUnder = searchUnder;
    }
}
