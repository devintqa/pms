package com.psk.pms.model;

public class SearchDetail {

	private String aliasProjectName;
	private Integer projId;
	private boolean editSubProject;
	private boolean searchProjectDescription;
	private String searchOn;
	private String searchUnder;
    private String employeeId;
    private String role;

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

	public String getSearchOn() {
		return searchOn;
	}

	public void setSearchOn(String searchOn) {
		this.searchOn = searchOn;
	}

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
