package com.psk.pms.model;

public class Authorize {

    private String employeeId;
    private Integer projectId;
    private String privilegeDetails;

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

}
