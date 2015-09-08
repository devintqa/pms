package com.psk.pms.controller;

import com.psk.pms.Constants;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.SubProjectService;
import com.psk.pms.validator.SubProjectDetailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.psk.pms.Constants.PROJECT_TYPE;

@Controller
public class SubProjectController extends BaseController {

    @Autowired
    private SubProjectDetailValidator subProjectDetailValidator;

    @Autowired
    private SubProjectService subProjectService;

    @Autowired
    private ProjectDescriptionService projectDescriptionService;

    private static final Logger LOGGER = Logger
            .getLogger(SubProjectController.class);

    @RequestMapping(value = "/emp/myview/buildSubProject/{employeeId}", method = RequestMethod.GET)
    public String buildSubProject(
            @PathVariable String employeeId,
            @RequestParam(value = "team", required = true) String team,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "project", required = false) String project,
            @RequestParam(value = "subproject", required = false) String subProject,
            Model model) {
        SubProjectDetail subProjectDetail = new SubProjectDetail();
        subProjectDetail.setEmployeeId(employeeId);
        model.addAttribute("subProjectForm", subProjectDetail);
        Map<String, String> aliasProjectList = populateAliasProjectList();
        LOGGER.info("Alias project list :" + aliasProjectList);
        if (aliasProjectList.size() == 0) {
            model.addAttribute("noProjectCreated",
                    "No Project Found To Be Created. Please Create a Project.");
            return "Welcome";
        } else {
            model.addAttribute("aliasProjectList", aliasProjectList);
        }

        return "BuildSubProject";
    }

    @RequestMapping(value = "/emp/myview/updateSubProject/{employeeId}", method = RequestMethod.GET)
    public String updateSubProject(
            @PathVariable String employeeId,
            @RequestParam(value = "team", required = true) String team,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "project", required = false) String project,
            @RequestParam(value = "subproject", required = false) String subProject,
            Model model) {

        LOGGER.info("method = updateSubProject() , Action :" + action);

        if ("editProjectDesc".equalsIgnoreCase(action)) {
            SubProjectDetail subProjectDetail = subProjectService
                    .getSubProjectDocument(subProject);
            subProjectDetail.setEmployeeId(employeeId);
            List<ProjDescDetail> projDescDocList = projectDescriptionService
                    .getSubProjectDescDetailList(subProjectDetail
                            .getSubProjId());
            model.addAttribute("projDescDocList", projDescDocList);
            model.addAttribute("projDescDocListSize", projDescDocList.size());
            model.addAttribute("subProjectAliasName",
                    subProjectDetail.getAliasSubProjName());
            return "UpdateProjectDesc";
        } else {
            SubProjectDetail subProjectDetail = subProjectService
                    .getSubProjectDocument(subProject);
            subProjectDetail.setEmployeeId(employeeId);
            subProjectDetail.setIsUpdate("Y");
            Map<String, String> aliasProjectList = new HashMap<String, String>();
            aliasProjectList.put(subProjectDetail.getProjId().toString(),
                    subProjectDetail.getAliasProjName());
            model.addAttribute("aliasProjectList", aliasProjectList);
            model.addAttribute("subProjectForm", subProjectDetail);
        }
        return "BuildSubProject";
    }

    @RequestMapping(value = "/emp/myview/buildSubProject/createSubProject.do", method = RequestMethod.POST)
    public String saveSubProjectAction(
            @ModelAttribute("subProjectForm") SubProjectDetail subProjectDetail,
            BindingResult result, Model model, SessionStatus status) {
        boolean isProjectSaveSuccessful = false;
        subProjectDetailValidator.validate(subProjectDetail, result);
        Map<String, String> aliasProjectList = populateAliasProjectList();

        if (!result.hasErrors()) {
            isProjectSaveSuccessful = subProjectService
                    .createEditSubProject(subProjectDetail);
        }
        if (result.hasErrors() || !isProjectSaveSuccessful) {
            model.addAttribute("aliasProjectList", aliasProjectList);
            return "BuildSubProject";
        } else {
            status.setComplete();
            Employee employee = new Employee();
            employee.setEmployeeId(subProjectDetail.getEmployeeId());
            model.addAttribute("employee", employee);
            model.addAttribute("subProjectCreationMessage",
                    "Sub Project Creation Successful.");
            model.addAttribute("aliasProjectList", aliasProjectList);
            return "BuildSubProject";
        }
    }

    @RequestMapping(value = "/emp/myview/updateSubProject/createSubProject.do", method = RequestMethod.POST)
    public String updateSubProjectAction(
            @ModelAttribute("subProjectForm") SubProjectDetail subProjectDetail,
            BindingResult result, Model model, SessionStatus status) {
        boolean isProjectSaveSuccessful = false;
        subProjectDetailValidator.validate(subProjectDetail, result);
        Map<String, String> aliasProjectList = populateAliasProjectList();

        if (!result.hasErrors()) {
            isProjectSaveSuccessful = subProjectService
                    .createEditSubProject(subProjectDetail);
        }
        if (result.hasErrors() || !isProjectSaveSuccessful) {
            model.addAttribute("aliasProjectList", aliasProjectList);
            return "BuildSubProject";
        } else {
            status.setComplete();
            Employee employee = new Employee();
            employee.setEmployeeId(subProjectDetail.getEmployeeId());
            model.addAttribute("employee", employee);
            model.addAttribute("subProjectCreationMessage",
                    "Sub Project Updated Successfully.");
            model.addAttribute("aliasProjectList", aliasProjectList);
            return "BuildSubProject";
        }
    }

    @ModelAttribute("projectTypeList")
    public List<String> populateProjectTypes() {
        LOGGER.info("method = populateProjectTypes()");
        List<String> projectTypeNames = projectService.getDropDownValuesFor(PROJECT_TYPE);
        LOGGER.info("The Project Type Name Size:" + projectTypeNames.size());
        return projectTypeNames;
    }
}
