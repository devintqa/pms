package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.psk.pms.factory.EmployeeTeam;
import com.psk.pms.factory.EmployeeTeamFactory;
import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;

@Controller
public class BaseController {

    @Autowired
    protected ProjectService projectService;

    @Autowired
    protected SubProjectService subProjectService;


    @Autowired
    EmployeeTeamFactory employeeTeamFactory;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    ItemService itemService;

    public Map<String, String> populateAliasProjectList(String empId) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        Map<String, String> aliasProjectList = employeeTeam.fetchProjects(employee.getEmployeeId());
        return aliasProjectList;
    }

    public Map<String, String> populateSubAliasProjectList(String projectId, String empId) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        return employeeTeam.fetchSubProjects(projectId, employee.getEmployeeId());
    }

    List<String> fetchProjectInfo(String name, String empId) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        return employeeTeam.fetchProjects(name, empId);
    }

    public List<ProjectDetail> fetchProjectDocumentList(String employeeId) {
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        return employeeTeam.getProjectDocumentList(employeeId);
    }

    public List<String> fetchSubProjectsInfo(String subaliasProjectName, String empId) {
        Employee employee = employeeService.getEmployeeDetails(empId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
        return employeeTeam.fetchSubProjectInfo(subaliasProjectName, empId);
    }

    public List<DepositDetail>  fetchDepositDetails(String employeeId) {
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        EmployeeTeam employeeTeam = employeeTeamFactory.getEmployeeTeam(employee.getEmployeeTeam());
       return employeeTeam.fetchDepositDetails(employee.getEmployeeId());
    }


}
