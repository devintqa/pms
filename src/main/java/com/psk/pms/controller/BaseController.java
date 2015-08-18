package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;
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
		Map<String, String> aliasProjectName = projectService
				.getAliasProjectNames();
		return aliasProjectName;
	}

	public Map<String, String> populateSubAliasProjectList(String projectId) {
		Map<String, String> subAliasProjectNames = subProjectService
				.getSubAliasProjectNames(projectId);
		return subAliasProjectNames;
	}
	
    public List<String> fetchProjectsInfo(String aliasProjectName) {
        List<String> result = new ArrayList<String>();
        Map<String, String> aliasProjectList = populateAliasProjectList();
        for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
            if (entry.getValue().toUpperCase()
                    .indexOf(aliasProjectName.toUpperCase()) != -1) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

    public List<String> fetchSubProjectsInfo(String subaliasProjectName) {
        List<String> result = new ArrayList<String>();
        // intentionally passing empty to get all sub projectNames
        Map<String, String> aliasProjectList = populateSubAliasProjectList("");
        for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
            if (entry.getValue().toUpperCase()
                    .indexOf(subaliasProjectName.toUpperCase()) != -1) {
                result.add(entry.getValue());
            }
        }
        return result;
    }

}
