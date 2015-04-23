package com.psk.pms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.ProjDescDetailValidator;
import com.psk.pms.validator.ProjectDetailValidator;
import com.psk.pms.validator.SubProjectDetailValidator;

@Controller
public class ProjectController {

	@Autowired
	ProjectDetailValidator projectDetailValidator;
	
	@Autowired
	ProjDescDetailValidator projDescDetailValidator;
	
	@Autowired
	SubProjectDetailValidator subProjectDetailValidator;
	
	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "/emp/myview/buildProject/{employeeId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String employeeId, Model model) {
		ProjectDetail projectDetail = new ProjectDetail();
		projectDetail.setEmployeeId(employeeId);
		model.addAttribute("createProjectForm", projectDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "BuildProject";
	}
	
	@RequestMapping(value = "/emp/myview/buildProject/createProject.do", method = RequestMethod.POST)
    public String saveProjectAction(
            @ModelAttribute("createProjectForm") ProjectDetail projectDetail,
            BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		//signupValidator.validate(employee, result); - project validation needs to be done here
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createProject(projectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			model.addAttribute("projectCreationMessage", "Project Creation Successful.");
			employee.setEmployeeId(projectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			return "Welcome";
		}
    }

	@RequestMapping(value = "/emp/myview/buildSubProject/{employeeId}", method = RequestMethod.GET)
	public String buildSubProject(@PathVariable String employeeId, Model model) {
		SubProjectDetail subProjectDetail = new SubProjectDetail();
		subProjectDetail.setEmployeeId(employeeId);
		model.addAttribute("createSubProjectForm", subProjectDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "BuildSubProject";
	}
	
	@RequestMapping(value = "/emp/myview/buildSubProject/createSubProject.do", method = RequestMethod.POST)
    public String saveSubProjectAction(
            @ModelAttribute("createSubProjectForm") SubProjectDetail subProjectDetail,
            BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		//signupValidator.validate(employee, result); - subProject validation needs to be done here
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createSubProject(subProjectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildSubProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			model.addAttribute("subProjectCreationMessage", "Sub Project Creation Successful.");
			employee.setEmployeeId(subProjectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			return "Welcome";
		}
    }

	@RequestMapping(value = "/emp/myview/buildProjDesc/{employeeId}", method = RequestMethod.GET)
	public String buildProjDesc(@PathVariable String employeeId, Model model) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		projDescDetail.setEmployeeId(employeeId);
		model.addAttribute("createProjDescForm", projDescDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "BuildProjDesc";
	}
	
	@RequestMapping(value = "/emp/myview/buildProjDesc/createProjDesc.do", method = RequestMethod.POST)
    public String saveProjDescAction(
            @ModelAttribute("createProjDescForm") ProjDescDetail projDescDetail,
            BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		//signupValidator.validate(employee, result); - projDesc validation needs to be done here
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createProjDesc(projDescDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildProjDesc";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			model.addAttribute("projDescCreationMessage", "Project Description added successfully.");
			employee.setEmployeeId(projDescDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			return "Welcome";
		}
    }
	
}