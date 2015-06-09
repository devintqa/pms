package com.psk.pms.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.psk.pms.service.ProjectService;
 
@Controller
public class BaseController {
	
	@Autowired
	ProjectService projectService;
	
	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}

}
