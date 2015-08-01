package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
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

import com.psk.pms.model.ProjectConfiguration.ItemDetail;
import com.psk.pms.model.ProjectConfiguration;
import com.psk.pms.model.ViewDetail;
import com.psk.pms.service.ItemService;
import com.psk.pms.validator.ViewValidator;

@Controller
public class ViewController extends BaseController {
	
	private static final Logger LOGGER = Logger.getLogger(ViewController.class);
	
	@Autowired
	private ViewValidator viewValidator;

	@Autowired
	ItemService itemService;
	
	@RequestMapping(value = "/emp/myview/viewDetails/{employeeId}", method = RequestMethod.GET)
	public String viewDetails(@PathVariable String employeeId, Model model) {		
		LOGGER.info("View Controller : viewDetails()");
		ViewDetail viewDetail = new ViewDetail();
		viewDetail.setViewProjectItemPrice(true);
		model.addAttribute("viewDetailsForm", viewDetail);
		return "ViewDetails";
	}
	
	@RequestMapping(value = "/emp/myview/viewDetails/searchProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getProjects(@RequestParam("term") String name) {
		LOGGER.info("method = getProjectList()");
		return fetchProjectsInfo(name);
	}


	@RequestMapping(value = "/emp/myview/viewDetails/searchSubProject.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getSubProjectList(@RequestParam("term") String name) {
		LOGGER.info("method = getSubProjectList()");
		return fetchSubProjectsInfo(name);
	}
	
	@RequestMapping(value = "/emp/myview/viewDetails/viewDetails.do", method = RequestMethod.POST)
	public String viewProjectDetails(
			@ModelAttribute("viewDetailsForm") ViewDetail viewDetail,
			BindingResult result, Model model, SessionStatus status) {
		LOGGER.info("method = viewProjectDetails()");
		viewValidator.validate(viewDetail, result);
		if(!result.hasErrors()){
			ProjectConfiguration projectConfiguration = new ProjectConfiguration();
			projectConfiguration.setProjId(viewDetail.getProjId());
			if(viewDetail.isViewProjectItemPrice()){
				ProjectConfiguration itemConfiguration = itemService.getProjectItemConfiguration(projectConfiguration);
				List<ItemDetail> itemPriceDetails = itemConfiguration.getItemDetail();
				if(itemPriceDetails.size() > 0){
					model.addAttribute("itemPriceDetails", itemPriceDetails);
					model.addAttribute("itemPriceDetailsSize", itemPriceDetails.size());
					model.addAttribute("projectAliasName", viewDetail.getAliasProjectName());
				}else{
					model.addAttribute("noDetailsFound", "No Item Price Configuration Found For The Project.");
				}
			}
		}
		return "ViewDetails";
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

}
