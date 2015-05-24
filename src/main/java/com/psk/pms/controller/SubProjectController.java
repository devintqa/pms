package com.psk.pms.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.SubProjectDetailValidator;

@Controller
public class SubProjectController {

	@Autowired
	SubProjectDetailValidator subProjectDetailValidator;

	@Autowired
	ProjectService projectService;

	@RequestMapping(value = "/emp/myview/buildSubProject/{employeeId}", method = RequestMethod.GET)
	public String buildSubProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project,
			@RequestParam(value="subproject", required=false) String subProject, 
			Model model) {
			SubProjectDetail subProjectDetail = new SubProjectDetail();
			subProjectDetail.setEmployeeId(employeeId);
			model.addAttribute("subProjectForm", subProjectDetail);
			Map<String, String> aliasProjectList = populateAliasProjectList();
			System.out.println(aliasProjectList);
			if(aliasProjectList.size() == 0){
				model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
				return "Welcome";
			} else{
				model.addAttribute("aliasProjectList", aliasProjectList);
			}

		return "BuildSubProject";
	}
	
	@RequestMapping(value = "/emp/myview/updateSubProject/{employeeId}", method = RequestMethod.GET)
	public String updateSubProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project,
			@RequestParam(value="subproject", required=false) String subProject, 
			Model model) {
			
		System.out.println("Into Sub Project: " + action);
		
		if("editProjectDesc".equalsIgnoreCase(action)){
			SubProjectDetail subProjectDetail = projectService.getSubProjectDocument(subProject);
			subProjectDetail.setEmployeeId(employeeId);
			List<ProjDescDetail> projDescDocList = projectService.getProjectDescDetailList(subProjectDetail.getSubProjId());
			model.addAttribute("projDescDocList", projDescDocList);
			model.addAttribute("projDescDocListSize", projDescDocList.size());
			return "UpdateProjectDesc";
		}else {
			SubProjectDetail subProjectDetail = projectService.getSubProjectDocument(subProject);
			subProjectDetail.setEmployeeId(employeeId);
			subProjectDetail.setIsUpdate("Y");
			Map<String, String> aliasProjectList = new HashMap<String, String>();
			aliasProjectList.put(subProjectDetail.getProjId().toString(), subProjectDetail.getAliasProjName());
			model.addAttribute("aliasProjectList", aliasProjectList);
			model.addAttribute("subProjectForm", subProjectDetail);
		}
		return "BuildSubProject";
	}

	@RequestMapping(value = "/emp/myview/buildSubProject/createSubProject.do", method = RequestMethod.POST)
	public String saveSubProjectAction(
			@ModelAttribute("subProjectForm") SubProjectDetail subProjectDetail,
			BindingResult result, 
			Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		subProjectDetailValidator.validate(subProjectDetail, result);
		Map<String, String> aliasProjectList = populateAliasProjectList();
		
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createEditSubProject(subProjectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(subProjectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			model.addAttribute("subProjectCreationMessage", "Sub Project Creation Successful.");
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";			
		}
	}
	
	@RequestMapping(value = "/emp/myview/updateSubProject/createSubProject.do", method = RequestMethod.POST)
	public String updateSubProjectAction(
			@ModelAttribute("subProjectForm") SubProjectDetail subProjectDetail,
			BindingResult result, 
			Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		subProjectDetailValidator.validate(subProjectDetail, result);
		Map<String, String> aliasProjectList = populateAliasProjectList();
		
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createEditSubProject(subProjectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(subProjectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			model.addAttribute("subProjectCreationMessage", "Sub Project Updated Successfully.");
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";			
		}
	}

	

	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}

	/*public Map<String, String> populateSubAliasProjectList(String project) {
		Map<String, String> aliasSubProjectName = projectService.getSubAliasProjectNames(project);
		return aliasSubProjectName;
	}*/

	/*public List<ProjectDetail> getProjectDocumentList() {
		List<ProjectDetail> projectDocumentList = projectService.getProjectDocumentList();
		return projectDocumentList;
	}

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		List<SubProjectDetail> subProjectDocumentList = projectService.getSubProjectDocumentList(projectId);
		return subProjectDocumentList;
	}*/

}
