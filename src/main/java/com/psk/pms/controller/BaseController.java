package com.psk.pms.controller;

import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Map;
 
@Controller
public class BaseController {
	
	@Autowired
	ProjectService projectService;

	@Autowired
	SubProjectService subProjectService;

	@Autowired
	ItemService itemService;
	
	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}

	public Map<String, String> populateSubAliasProjectList(String projectId) {
		Map<String, String> subAliasProjectNames = subProjectService.getSubAliasProjectNames(projectId);
		return subAliasProjectNames;
	}
	
	public Map<String, String> populateDescItemCodes(String itemCode) {
		Map<String, String> subAliasProjectNames = itemService.getDescItemCodes(itemCode);
		return subAliasProjectNames;
	}

}
