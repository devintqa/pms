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
	public String buildProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			Model model) {		

		model.addAttribute("projectForm", new ProjectDetail());

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		model.addAttribute("employee", employee);

		return "BuildProject";
	}
	
	@RequestMapping(value = "/emp/myview/updateProject/{employeeId}", method = RequestMethod.GET)
	public String updateProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team,  
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project, 
			Model model) {		

		ProjectDetail projectDetail = new ProjectDetail();
		projectDetail = projectService.getProjectDocument(project);
		projectDetail.setIsUpdate("Y");
		projectDetail.setEmployeeId(employeeId);
		model.addAttribute("projectForm", projectDetail);
		/*List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(projectDetail
				.getProjId());
		model.addAttribute("subProjectDocumentList", subProjectDocumentList);
		model.addAttribute("subProjectDocumentSize",
				subProjectDocumentList.size());*/

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		model.addAttribute("employee", employee);

		return "BuildProject";
	}

	@RequestMapping(value = "/emp/myview/buildProject/createProject.do", method = RequestMethod.POST)
	public String saveProjectAction(
			@ModelAttribute("projectForm") ProjectDetail projectDetail,
			BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		projectDetailValidator.validate(projectDetail, result);
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createEditProject(projectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			model.addAttribute("projectCreationMessage", "Project Creation Successful.");
			return "BuildProject";			
		}
	}
	
	@RequestMapping(value = "/emp/myview/updateProject/createProject.do", method = RequestMethod.POST)
	public String updateProjectAction(
			@ModelAttribute("projectForm") ProjectDetail projectDetail,
			BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		projectDetailValidator.validate(projectDetail, result);
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createEditProject(projectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projectDetail.getEmployeeId());
			model.addAttribute("employee", employee);	
			isProjectSaveSuccessful = projectService.createEditProject(projectDetail);
			model.addAttribute("projectUpdationMessage", "Project Updated Successfully.");
			/*List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(projectDetail.getProjId());
			model.addAttribute("subProjectDocumentList", subProjectDocumentList);
			model.addAttribute("subProjectDocumentSize", subProjectDocumentList.size());*/			
			return "BuildProject";		
		}
	}

}