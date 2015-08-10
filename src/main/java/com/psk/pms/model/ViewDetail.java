package com.psk.pms.model;

public class ViewDetail {

	private String aliasProjectName;
	private Integer projId;
	private boolean editSubProject;
	private boolean viewProjectItemPrice;
	private String searchUnder;
	private boolean searchAggregateItemDetails;
	private boolean searchComparisonData;

	public boolean isSearchComparisonData() {
		return searchComparisonData;
	}

	public void setSearchComparisonData(boolean searchComparisonData) {
		this.searchComparisonData = searchComparisonData;
	}

	public boolean isSearchAggregateItemDetails() {
		return searchAggregateItemDetails;
	}

	public void setSearchAggregateItemDetails(boolean searchAggregateItemDetails) {
		this.searchAggregateItemDetails = searchAggregateItemDetails;
	}

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
