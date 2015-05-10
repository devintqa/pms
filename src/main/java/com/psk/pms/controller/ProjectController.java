package com.psk.pms.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;
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
	public String buildProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team,  
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project, 
			Model model) {		

		if(null!=project){
			ProjectDetail projectDetail = new ProjectDetail();
			projectDetail = projectService.getProjectDocument(project);
			projectDetail.setIsUpdate("Y");
			projectDetail.setEmployeeId(employeeId);
			model.addAttribute("projectForm", projectDetail);
			List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(projectDetail.getProjId());
			model.addAttribute("subProjectDocumentList", subProjectDocumentList);
			model.addAttribute("subProjectDocumentSize", subProjectDocumentList.size());			

		}else{
			model.addAttribute("projectForm", new ProjectDetail());
		}

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		model.addAttribute("employee", employee);

		return "BuildProject";
	}

	@RequestMapping(value = "/emp/myview/buildSubProject/{employeeId}", method = RequestMethod.GET)
	public String buildSubProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project,
			@RequestParam(value="subproject", required=false) String subProject, 
			Model model) {
		if(null!=subProject){
			SubProjectDetail subProjectDetail = projectService.getSubProjectDocument(subProject);
			subProjectDetail.setEmployeeId(employeeId);
			subProjectDetail.setIsUpdate("Y");
			Map<String, String> aliasProjectList = new HashMap<String, String>();
			aliasProjectList.put(subProjectDetail.getProjId().toString(), subProjectDetail.getAliasProjName());
			model.addAttribute("aliasProjectList", aliasProjectList);
			model.addAttribute("subProjectForm", subProjectDetail);

			List<ProjDescDetail> projDescDocList = projectService.getProjectDescDetailList(subProjectDetail.getSubProjId());
			model.addAttribute("projDescDocList", projDescDocList);
			model.addAttribute("projDescDocListSize", projDescDocList.size());		

		}else{
			model.addAttribute("subProjectForm", new SubProjectDetail());
			Map<String, String> aliasProjectList = populateAliasProjectList();

			if(aliasProjectList.size() == 0){
				model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
				return "Welcome";
			} else{
				model.addAttribute("aliasProjectList", aliasProjectList);
			}	
		}

		return "BuildSubProject";
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/{employeeId}", method = RequestMethod.GET)
	public String buildProjDesc(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			@RequestParam(value="action", required=false) String action, 
			@RequestParam(value="project", required=false) String project,
			@RequestParam(value="subproject", required=false) String subProject,
			@RequestParam(value="desc", required=false) String descDetail, 
			Model model) {
		
		if(null!=descDetail){
			ProjDescDetail projDescDetail = projectService.getProjectDescDetail(descDetail);
			projDescDetail.setEmployeeId(employeeId);
			projDescDetail.setIsUpdate("Y");
			Map<String, String> aliasProjectList = new HashMap<String, String>();
			aliasProjectList.put(projDescDetail.getProjId().toString(), projDescDetail.getAliasProjectName());
			model.addAttribute("aliasProjectList", aliasProjectList);
			model.addAttribute("projDescForm", projDescDetail);

		}else{
			model.addAttribute("projDescForm", new ProjDescDetail());
			Map<String, String> aliasProjectList = populateAliasProjectList();

			if(aliasProjectList.size() == 0){
				model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
				return "Welcome";
			} else{
				model.addAttribute("aliasProjectList", aliasProjectList);
			}	
		}
		return "BuildDescription";
	}

	@RequestMapping(value = "/emp/myview/buildProject/createProject.do", method = RequestMethod.POST)
	public String saveProjectAction(
			@ModelAttribute("projectForm") ProjectDetail projectDetail,
			BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		projectDetailValidator.validate(projectDetail, result);
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createProject(projectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			return "BuildProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projectDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			if(!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())){
				model.addAttribute("projectCreationMessage", "Project Creation Successful.");
				return "BuildProject";
			} else{
				isProjectSaveSuccessful = projectService.createProject(projectDetail);
				model.addAttribute("projectUpdationMessage", "Project Updated Successfully.");
				List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(projectDetail.getProjId());
				model.addAttribute("subProjectDocumentList", subProjectDocumentList);
				model.addAttribute("subProjectDocumentSize", subProjectDocumentList.size());			
				return "BuildProject";
			}			
		}
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
			isProjectSaveSuccessful = projectService.createSubProject(subProjectDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(subProjectDetail.getEmployeeId());
			model.addAttribute("employee", employee);

			if(!"Y".equalsIgnoreCase(subProjectDetail.getIsUpdate())){
				model.addAttribute("subProjectCreationMessage", "Sub Project Creation Successful.");
			} else{
				isProjectSaveSuccessful = projectService.createSubProject(subProjectDetail);
				model.addAttribute("subProjectCreationMessage", "Sub Project Updated Successfully.");
			}
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildSubProject";			
		}
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/createProjDesc.do", method = RequestMethod.POST)
	public String saveProjDescAction(
			@ModelAttribute("projDescForm") ProjDescDetail projDescDetail,
			BindingResult result, Model model, SessionStatus status) {	
		boolean isProjectSaveSuccessful = false;
		Map<String, String> aliasProjectList = populateAliasProjectList();
		projDescDetailValidator.validate(projDescDetail, result);
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createProjDesc(projDescDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildDescription";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projDescDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			if(!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())){
				model.addAttribute("projDescCreationMessage", "Project Description Creation Successful.");
			} else{
				isProjectSaveSuccessful = projectService.createProjDesc(projDescDetail);
				model.addAttribute("projDescCreationMessage", "Project Description Updated Successfully.");
			}
			model.addAttribute("aliasProjectList", aliasProjectList);
			return "BuildDescription";
		}
	}

	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/getSubAliasProject.do", method = RequestMethod.GET)
	@ResponseBody 
	public String getSubAliasProject(HttpServletRequest request, HttpServletResponse response) {
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		Gson gson = new Gson(); 
		String subAliasProjectJson = gson.toJson(subAliasProjectList); 
		return subAliasProjectJson;
	}

	public Map<String, String> populateSubAliasProjectList(String project) {
		Map<String, String> aliasSubProjectName = projectService.getSubAliasProjectNames(project);
		return aliasSubProjectName;
	}

	@ModelAttribute("workTypeList")
	public Map<String, String> populateWorkTypeList() {
		Map<String, String> workType = new LinkedHashMap<String, String>();
		workType.put("Main Work", "Main Work");
		workType.put("Electrical", "Electrical");
		workType.put("Plumbing", "Plumbing");
		return workType;
	}

	public List<ProjectDetail> getProjectDocumentList() {
		List<ProjectDetail> projectDocumentList = projectService.getProjectDocumentList();
		return projectDocumentList;
	}

	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		List<SubProjectDetail> subProjectDocumentList = projectService.getSubProjectDocumentList(projectId);
		return subProjectDocumentList;
	}

}