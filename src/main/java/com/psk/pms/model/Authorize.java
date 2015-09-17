package com.psk.pms.model;

public class Authorize {

    private String employeeId;
    private Integer projectId;
    private String privilegeDetails;
    private String projectName;
    private String teamName;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getPrivilegeDetails() {
        return privilegeDetails;
    }

    public void setPrivilegeDetails(String privilegeDetails) {
        this.privilegeDetails = privilegeDetails;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
