package com.psk.pms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
 
@Controller
public class BaseController {
	
	@Autowired
	protected ProjectService projectService;

	@Autowired
	protected SubProjectService subProjectService;

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
	

}
