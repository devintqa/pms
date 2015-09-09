package com.psk.pms.controller;

import static com.psk.pms.Constants.PROJECT_TYPE;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
import com.psk.pms.validator.ProjectDetailValidator;

@Controller
public class ProjectController {

    @Autowired
    ProjectDetailValidator projectDetailValidator;

    @Autowired
    ProjectService projectService;

    @Autowired
    SubProjectService subProjectService;

    private static final Logger LOGGER = Logger
            .getLogger(ProjectController.class);

    @RequestMapping(value = "/emp/myview/buildProject/{employeeId}", method = RequestMethod.GET)
    public String buildProject(@PathVariable String employeeId,
                               @RequestParam(value = "team", required = true) String team,
                               Model model) {
        LOGGER.info("method = buildProject()");
        ProjectDetail projDetail = new ProjectDetail();
        projDetail.setEmployeeId(employeeId);
        model.addAttribute("projectForm", projDetail);
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        model.addAttribute("employee", employee);
        return "BuildProject";
    }

    @RequestMapping(value = "/emp/myview/updateProject/{employeeId}", method = RequestMethod.GET)
    public String updateProject(@PathVariable String employeeId,
                                @RequestParam(value = "team", required = true) String team,
                                @RequestParam(value = "action", required = false) String action,
                                @RequestParam(value = "project", required = false) String project,
                                Model model) {

        LOGGER.info("method = updateProject() ,Action : " + action);
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        model.addAttribute("employee", employee);

        ProjectDetail projectDetail = new ProjectDetail();
        projectDetail = projectService.getProjectDocument(project);
        projectDetail.setIsUpdate("Y");
        projectDetail.setEmployeeId(employeeId);
        model.addAttribute("projectForm", projectDetail);
        return "BuildProject";
    }

    @RequestMapping(value = "/emp/myview/buildProject/createProject.do", method = RequestMethod.POST)
    public String saveProjectAction(
            @ModelAttribute("projectForm") ProjectDetail projectDetail,
            BindingResult result, Model model, SessionStatus status) {
        boolean isProjectSaveSuccessful = false;
        projectDetailValidator.validate(projectDetail, result);
        if (!result.hasErrors()) {
            isProjectSaveSuccessful = projectService
                    .createEditProject(projectDetail);
        }
        if (result.hasErrors() || !isProjectSaveSuccessful) {
            return "BuildProject";
        } else {
            status.setComplete();
            Employee employee = new Employee();
            employee.setEmployeeId(projectDetail.getEmployeeId());
            model.addAttribute("employee", employee);
            model.addAttribute("projectCreationMessage",
                    "Project Creation Successful.");
            return "BuildProject";
        }
    }

    @RequestMapping(value = "/emp/myview/updateProject/createProject.do", method = RequestMethod.POST)
    public String updateProjectAction(
            @ModelAttribute("projectForm") ProjectDetail projectDetail,
            BindingResult result, Model model, SessionStatus status) {
        boolean isProjectSaveSuccessful = false;
        projectDetailValidator.validate(projectDetail, result);
        if (!result.hasErrors()) {
            isProjectSaveSuccessful = projectService
                    .createEditProject(projectDetail);
        }
        if (result.hasErrors() || !isProjectSaveSuccessful) {
            return "BuildProject";
        } else {
            status.setComplete();
            Employee employee = new Employee();
            employee.setEmployeeId(projectDetail.getEmployeeId());
            model.addAttribute("employee", employee);
            isProjectSaveSuccessful = projectService
                    .createEditProject(projectDetail);
            model.addAttribute("projectUpdationMessage",
                    "Project Updated Successfully.");
            return "BuildProject";
        }
    }

    public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
        List<SubProjectDetail> subProjectDocumentList = subProjectService
                .getSubProjectDocumentList(projectId);
        return subProjectDocumentList;
    }

    @ModelAttribute("projectTypeList")
    public List<String> populateProjectTypes() {
        LOGGER.info("method = populateProjectTypes()");
        List<String> projectTypeNames = projectService.getDropDownValuesFor(PROJECT_TYPE);
        LOGGER.info("The Project Type Name Size:" + projectTypeNames.size());
        return projectTypeNames;
    }
}