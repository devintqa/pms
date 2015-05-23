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

@Controller
public class ProjectDescriptionController {

	@Autowired
	ProjDescDetailValidator projDescDetailValidator;

	@Autowired
	ProjectService projectService;


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
			Map<String, String> aliasProjectList = new HashMap<String, String>();
			aliasProjectList.put(projDescDetail.getProjId().toString(), projDescDetail.getAliasProjectName());
			Map<String, String> subAliasProjectList = populateSubAliasProjectList(projDescDetail.getProjId().toString());
			subAliasProjectList.put("0", "--Please Select--");
			model.addAttribute("subAliasProjectList", subAliasProjectList);
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

	@RequestMapping(value = "/emp/myview/buildProjectDesc/createProjDesc.do", method = RequestMethod.POST)
	public String saveProjDescAction(
			@ModelAttribute("projDescForm") ProjDescDetail projDescDetail,
			BindingResult result, Model model, SessionStatus status) {	
		boolean isProjectSaveSuccessful = false;
		Map<String, String> aliasProjectList = populateAliasProjectList();
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(projDescDetail.getAliasProjectName());
		projDescDetailValidator.validate(projDescDetail, result);
		System.out.println(result.hasErrors());
		if(!result.hasErrors()){
			isProjectSaveSuccessful = projectService.createEditProjDesc(projDescDetail);
		}
		if(result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			subAliasProjectList.put("0", "--Please Select--");
			model.addAttribute("subAliasProjectList", subAliasProjectList);
			return "BuildDescription";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projDescDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			if(!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())){
				model.addAttribute("projDescCreationMessage", "Project Description Creation Successful.");
			} else{
				isProjectSaveSuccessful = projectService.createEditProjDesc(projDescDetail);
				projDescDetail = projectService.getProjectDescDetail(projDescDetail.getProjDescId().toString());
				aliasProjectList = new HashMap<String, String>();
				aliasProjectList.put(projDescDetail.getProjId().toString(), projDescDetail.getAliasProjectName());
				model.addAttribute("aliasProjectList", aliasProjectList);
				model.addAttribute("projDescForm", projDescDetail);
				model.addAttribute("projDescCreationMessage", "Project Description Updated Successfully.");
			}
			model.addAttribute("aliasProjectList", aliasProjectList);
			subAliasProjectList.put("0", "--Please Select--");
			model.addAttribute("subAliasProjectList", subAliasProjectList);
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
		System.out.println("Sub Proj Id" + request.getParameter("subProjId"));
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
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
