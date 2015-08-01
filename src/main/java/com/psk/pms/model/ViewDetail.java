package com.psk.pms.model;

public class ViewDetail {

	private String aliasProjectName;
	private Integer projId;
	private boolean editSubProject;
	private boolean viewProjectItemPrice;
	private String searchUnder;

	public boolean isViewProjectItemPrice() {
		return viewProjectItemPrice;
	}
	public void setViewProjectItemPrice(boolean viewProjectItemPrice) {
		this.viewProjectItemPrice = viewProjectItemPrice;
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
	public boolean isEditSubProject() {
		return editSubProject;
	}
	public void setEditSubProject(boolean editSubProject) {
		this.editSubProject = editSubProject;
	}
    public String getSearchUnder() {
        return searchUnder;
    }
    public void setSearchUnder(String searchUnder) {
        this.searchUnder = searchUnder;
    }
}
