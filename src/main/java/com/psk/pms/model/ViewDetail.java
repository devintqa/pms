package com.psk.pms.model;

import java.util.List;

public class ViewDetail {

    private String aliasProjectName;
    private Integer projId;
    private boolean editSubProject;
    private boolean viewProjectItemPrice;
    private String searchUnder;
    private boolean searchAggregateItemDetails;
    private boolean searchComparisonData;
    private Integer subProjId;
    private boolean projectItemDescription;
    private String itemType;
    private String itemName;


    public Integer getSubProjId() {
        return subProjId;
    }

    public void setSubProjId(Integer subProjId) {
        this.subProjId = subProjId;
    }

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


    public boolean isProjectItemDescription() {
        return projectItemDescription;
    }

    public void setProjectItemDescription(boolean projectItemDescription) {
        this.projectItemDescription = projectItemDescription;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

}
