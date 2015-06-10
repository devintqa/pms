package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SearchDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.validator.SearchValidator;

@Controller
public class SearchController extends BaseController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class);
	
	@Autowired
	private SearchValidator searchValidator;
	
	@RequestMapping(value = "/emp/myview/searchProject/{employeeId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String employeeId, 
			@RequestParam(value="team", required=true) String team, 
			Model model) {		
		LOGGER.info("method = searchProject()");
		SearchDetail searchDetail = new SearchDetail();
		model.addAttribute("searchForm", searchDetail);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		model.addAttribute("employee", employee);
		return "SearchProject";
	}
	
	@RequestMapping(value = "/emp/myview/searchProject/searchProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getProjectList(@RequestParam("term") String name) {
		LOGGER.info("method = getProjectList()");
		return fetchProjectsInfo(name);
	}
	
	@RequestMapping(value = "/emp/myview/searchProject/searchDetails.do", method = RequestMethod.POST)
	public String searchProjectDetail(
			@ModelAttribute("searchForm") SearchDetail searchDetail,
			BindingResult result, Model model, SessionStatus status) {
		LOGGER.info("method = searchProjectDetail()");
		searchValidator.validate(searchDetail, result);
		if(!result.hasErrors()){
			if(searchDetail.isEditProject()){
				List<ProjectDetail> projectDocumentList = projectService.getProjectDocumentList();
				if(!projectDocumentList.isEmpty()){
					model.addAttribute("projectDocumentList", projectDocumentList);
				}
			}
			if(searchDetail.isEditSubProject()){
				List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(searchDetail.getProjId());
				if(subProjectDocumentList.size() > 0){
					model.addAttribute("subProjectDocumentList", subProjectDocumentList);
					model.addAttribute("subProjectDocumentSize", subProjectDocumentList.size());
					model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Sub Projects Found For The Selection.");
				}
			}
			if(searchDetail.isSearchProjectDescription()){
				LOGGER.info("method = fetchProjectsInfo()" + searchDetail.getProjId());
				List<ProjDescDetail> projDescDocList = projectService.getProjectDescDetailList(searchDetail.getProjId());
				if(projDescDocList.size() > 0){
					model.addAttribute("projDescDocList", projDescDocList);
					model.addAttribute("projDescDocListSize", projDescDocList.size());
					model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Project Descriptions Found For The Selection.");
				}
			}
		}
		return "SearchProject";
	}
	
	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		List<SubProjectDetail> subProjectDocumentList = projectService.getSubProjectDocumentList(projectId);
		return subProjectDocumentList;
	}
	
	private List<String> fetchProjectsInfo(String aliasProjectName) {
		LOGGER.info("method = fetchProjectsInfo()");
		List<String> result = new ArrayList<String>();
		Map<String, String> aliasProjectList = populateAliasProjectList();
		for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
			if (entry.getValue().toUpperCase().indexOf(aliasProjectName.toUpperCase())!= -1) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

}
