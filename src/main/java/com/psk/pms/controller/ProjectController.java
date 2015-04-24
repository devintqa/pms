package com.psk.pms.controller;

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
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.ProjectDetailValidator;

@Controller
public class ProjectController {

	@Autowired
	ProjectDetailValidator projectDetailValidator;
	
	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "/emp/myview/buildProject/{employeeId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String employeeId, @RequestParam(value="team", required=true) String team,  Model model) {
		ProjectDetail projectDetail = new ProjectDetail();
		projectDetail.setEmployeeId(employeeId);
		model.addAttribute("createProjectForm", projectDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
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
	
}