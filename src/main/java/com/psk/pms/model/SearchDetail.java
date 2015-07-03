package com.psk.pms.model;

public class SearchDetail {

	private String aliasProjectName;
	private Integer projId;
	private boolean editSubProject;
	private boolean searchProjectDescription;
	private boolean searchAggregateItemDetails;
	private String searchUnder;

	public boolean isSearchAggregateItemDetails() {
		return searchAggregateItemDetails;
	}
	public void setSearchAggregateItemDetails(boolean searchAggregateItemDetails) {
		this.searchAggregateItemDetails = searchAggregateItemDetails;
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
    public String getSearchUnder() {
        return searchUnder;
    }
    public void setSearchUnder(String searchUnder) {
        this.searchUnder = searchUnder;
    }
}
