package com.psk.pms.controller;

import com.psk.pms.model.DescItemDetail.ItemDetail;
import com.psk.pms.model.*;
import com.psk.pms.service.*;
import com.psk.pms.validator.SearchValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class SearchController extends BaseController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class);
	
	@Autowired
	private SearchValidator searchValidator;
	
	@Autowired
	SearchService searchService;

	@Autowired
	EmdService emdService;

	@Autowired
	SubProjectService subProjectService;

	@Autowired
	ItemService itemService;

	@Autowired
	ProjectDescriptionService projectDescriptionService;
	
	@RequestMapping(value = "/emp/myview/searchProject/{employeeId}", method = RequestMethod.GET)
	public String searchProject(@PathVariable String employeeId,Model model) {		
		LOGGER.info("Search Controller : searchProject()");
		List<ProjectDetail> projectDocumentList = projectService.getProjectDocumentList();
		if(!projectDocumentList.isEmpty()){
			model.addAttribute("projectDocumentList", projectDocumentList);
		}else{
			model.addAttribute("noDetailsFound", "No Projects Found For The Selection.");
		}
		return "SearchProject";
	}
	
	@RequestMapping(value = "/emp/myview/searchSubProject/{employeeId}", method = RequestMethod.GET)
	public String searchSubProject(@PathVariable String employeeId,Model model) {		
		LOGGER.info("Search Controller : searchSubProject()");
		SearchDetail searchDetail = new SearchDetail();
		searchDetail.setEditSubProject(true);
		model.addAttribute("searchSubForm", searchDetail);
		return "SearchSubProject";
	}
	
	@RequestMapping(value = "/emp/myview/searchProjectDescription/{employeeId}", method = RequestMethod.GET)
	public String searchProjectDescription(@PathVariable String employeeId,Model model) {		
		LOGGER.info("Search Controller : searchProjectDescription()");
		SearchDetail searchDetail = new SearchDetail();
		searchDetail.setSearchProjectDescription(true);
		model.addAttribute("searchProjDescForm", searchDetail);
		return "SearchProjectDescription";
	}
	
	@RequestMapping(value = "/emp/myview/searchSubProject/searchProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getProjectList(@RequestParam("term") String name) {
		LOGGER.info("method = getProjectList()");
		return fetchProjectsInfo(name);
	}
	
	@RequestMapping(value = "/emp/myview/searchProjectDescription/searchProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getProjects(@RequestParam("term") String name) {
		LOGGER.info("method = getProjectList()");
		return fetchProjectsInfo(name);
	}


	@RequestMapping(value = "/emp/myview/searchProjectDescription/searchSubProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getSubProjectList(@RequestParam("term") String name) {
		LOGGER.info("method = getSubProjectList()");
		return fetchSubProjectsInfo(name);
	}
	
	@RequestMapping(value = "/emp/myview/searchSubProject/searchSubProjectDetails.do", method = RequestMethod.POST)
	public String searchSubProjectDetail(
			@ModelAttribute("searchSubForm") SearchDetail searchDetail,
			BindingResult result, Model model, SessionStatus status) {
		LOGGER.info("method = searchProjectDetail()");
		searchValidator.validate(searchDetail, result);
		LOGGER.info("method = searchProjectDetail()" + "after validate");
		if(!result.hasErrors()){
			LOGGER.info("method = searchProjectDetail()" + "into fetch");
				List<SubProjectDetail> subProjectDocumentList = getSubProjectDocumentList(searchDetail.getProjId());
				if(subProjectDocumentList.size() > 0){
					model.addAttribute("subProjectDocumentList", subProjectDocumentList);
					model.addAttribute("subProjectDocumentSize", subProjectDocumentList.size());
					model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Sub Projects Found For The Selection.");
				}
		}
		return "SearchSubProject";
	}
	
	@RequestMapping(value = "/emp/myview/searchProjectDescription/searchProjectDescDetails.do", method = RequestMethod.POST)
	public String searchProjectDescDetail(
			@ModelAttribute("searchProjDescForm") SearchDetail searchDetail,
			BindingResult result, Model model, SessionStatus status) {
		LOGGER.info("method = searchProjectDetail()");
		searchValidator.validate(searchDetail, result);
		if(!result.hasErrors()){
			if(searchDetail.isSearchAggregateItemDetails()){
				List<ItemDetail> aggregateItemDetails = itemService.getProjectData(searchDetail.getProjId());
				if(aggregateItemDetails.size() > 0){
					model.addAttribute("aggregateItemDetails", aggregateItemDetails);
					model.addAttribute("aggregateItemDetailsSize", aggregateItemDetails.size());
					model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Aggregate Material Data Found For The Project.");
				}
			} else{
				boolean searchUnderProject = "project".equalsIgnoreCase(searchDetail.getSearchUnder())?true:false;
				LOGGER.info("method = fetchProjectsInfo()" + searchDetail.getProjId());
				List<ProjDescDetail> projDescDocList = projectDescriptionService.getProjectDescDetailList(searchDetail.getProjId(), searchUnderProject);
				if(projDescDocList.size() > 0){
					model.addAttribute("projDescDocList", projDescDocList);
					model.addAttribute("projDescDocListSize", projDescDocList.size());
					model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Project Descriptions Found For The Selection.");
				}
			}
		}
		return "SearchProjectDescription";
	}

	@RequestMapping(value = "/emp/myview/searchEmd/searchEmdDetails.do", method = RequestMethod.POST)
	public String searchProjectEmdDetails(
			@ModelAttribute("searchEmdForm") SearchDetail searchDetail,
			BindingResult result, Model model, SessionStatus status) {
		List<EmdDetail> emdDetails;
		searchValidator.validate(searchDetail, result);
		if (!result.hasErrors()) {
			LOGGER.info("method = searchProjectEmdDetails()" + searchDetail.getProjId());
			boolean searchUnderProject = "project".equalsIgnoreCase(searchDetail.getSearchUnder()) ? true : false;
			if (searchUnderProject) {
				emdDetails = emdService.getEmdDetailsByProjectId(null != searchDetail.getProjId() ? Integer.valueOf(searchDetail.getProjId()) : Integer.valueOf(0));
			} else {
				emdDetails = emdService.getEmdDetailsBySubProjectId(null != searchDetail.getProjId() ? Integer.valueOf(searchDetail.getProjId()) : Integer.valueOf(0));
			}
			if (emdDetails.size() > 0) {
				model.addAttribute("emdList", emdDetails);
				model.addAttribute("emdDetailsSize", emdDetails.size());
				model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
			} else {
				model.addAttribute("noDetailsFound", "No Project Emd Details Found For The Selection.");
			}
		}
		return "SearchEmd";
	}


	@RequestMapping(value = "/emp/myview/searchProjectDescription/searchDescItems.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getDescItem(@RequestParam("itemName") String itemName) {
		LOGGER.info("method = getDescItem()");
		return fetchItemCode(itemName);
	}
	
	public List<SubProjectDetail> getSubProjectDocumentList(Integer projectId) {
		List<SubProjectDetail> subProjectDocumentList = subProjectService.getSubProjectDocumentList(projectId);
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

	private List<String> fetchSubProjectsInfo(String subaliasProjectName) {
		LOGGER.info("method = fetchProjectsInfo()");
		List<String> result = new ArrayList<String>();
		//intentionally passing empty to get all sub projectNames
		Map<String, String> aliasProjectList = populateSubAliasProjectList("");
		for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
			if (entry.getValue().toUpperCase().indexOf(subaliasProjectName.toUpperCase())!= -1) {
				result.add(entry.getValue());
			}
		}
		return result;
	}
	
	private List<String> fetchItemCode(String itemName) {
		LOGGER.info("method = fetchItemCode()");
		List<String> result = new ArrayList<String>();
		Map<String, String> itemCodeList = populateDescItemCodes(itemName);
		for (Map.Entry<String, String> entry : itemCodeList.entrySet()) {
			if (entry.getValue().toUpperCase().indexOf(itemName.toUpperCase())!= -1) {
				result.add(entry.getValue());
			}
		}
		return result;
	}

	@RequestMapping(value = "/emp/myview/searchProject/deleteProject.do", method = RequestMethod.POST)
	public void deleteProject(HttpServletRequest request, HttpServletResponse response) {
		String projectId = request.getParameter("projectId");
		LOGGER.info("method = deleteProject() , projectId :"+ projectId);
		Integer projectIdNumeric = Integer.parseInt(projectId);
		projectService.deleteProject(projectIdNumeric);
	}

	@RequestMapping(value = "/emp/myview/searchSubProject/deleteSubProject.do", method = RequestMethod.POST)
	public void deleteSubProject(HttpServletRequest request, HttpServletResponse response) {
		String subProjectId = request.getParameter("subProjectId");
		LOGGER.info("method = deleteSubProject() , sub projectid : "+subProjectId );
		Integer subProjectIdNumeric = Integer.parseInt(subProjectId);
		subProjectService.deleteSubProject(subProjectIdNumeric);

	}

	@RequestMapping(value = "/emp/myview/searchEmd/{employeeId}", method = RequestMethod.GET)
	public String searchEmd(@PathVariable String employeeId,Model model) {
		LOGGER.info("Search Controller : searchProjectEmd()");
		SearchDetail searchDetail = new SearchDetail();
		searchDetail.setSearchProjectDescription(true);
		model.addAttribute("searchEmdForm", searchDetail);
		return "SearchEmd";
	}


}
