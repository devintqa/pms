package com.psk.pms.controller;

import org.apache.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ExcelViewController extends BaseController {

	private static final Logger LOGGER = Logger
			.getLogger(ExcelViewController.class);

	@RequestMapping(value = "/emp/myview/excel/{empId}/editOrViewExcel.do", method = RequestMethod.GET)
	public String buildProject(@PathVariable String empId, Model model) {
		LOGGER.info("view excel");
		return "ExcelViewer";
	}

	@RequestMapping(value = "/emp/myview/excel/{empId}/saveEdition.do", method = RequestMethod.POST, produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public String saveEdition(@PathVariable String empId,
			@RequestBody String edition) {
		LOGGER.info("method = saveEdition()" + edition);
		return edition;
	}
	
	/*
	 * @RequestMapping(value = "/emp/myview/searchProject/searchProject.do",
	 * method = RequestMethod.GET) public @ResponseBody List<String>
	 * getProjectList(@RequestParam("term") String name) { return null; }
	 * 
	 * @RequestMapping(value = "/emp/myview/searchProject/searchDetails.do",
	 * method = RequestMethod.POST) public String searchProjectDetail(
	 * 
	 * @ModelAttribute("searchForm") SearchDetail searchDetail, BindingResult
	 * result, Model model, SessionStatus status) {
	 * LOGGER.info("method = searchProjectDetail()");
	 * if(searchDetail.isSearchProject()){ List<ProjectDetail>
	 * projectDocumentList = projectService.getProjectDocumentList();
	 * if(!projectDocumentList.isEmpty()){
	 * model.addAttribute("projectDocumentList", projectDocumentList); } }
	 * return "SearchProject"; }
	 */
}
