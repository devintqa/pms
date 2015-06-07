package com.psk.pms.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.psk.pms.model.Employee;
import com.psk.pms.model.SearchDetail;

@Controller
public class SearchController {
	
	private static final Logger LOGGER = Logger.getLogger(SearchController.class);
	List<SearchDetail> data = new ArrayList<SearchDetail>();
	SearchController(){
		data.add(new SearchDetail(1, "Kalai"));
		data.add(new SearchDetail(2, "MMC"));
		data.add(new SearchDetail(3, "Secretariat"));
		data.add(new SearchDetail(4, "MMC Trichy"));
		data.add(new SearchDetail(5, "Kalai Madurai"));
	}
	
	
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
	List<String> getAddressList(@RequestParam String input) {
		return simulateSearchResult(input);
	}
	
	private List<String> simulateSearchResult(String aliasProjectName) {
 
		List<String> result = new ArrayList<String>();
		// iterate a list and filter by tagName
		for (SearchDetail searchDetail : data) {
			if (searchDetail.getAliasProjectName().contains(aliasProjectName)) {
				LOGGER.info("Search Detail:" + aliasProjectName);
				result.add(searchDetail.getAliasProjectName());
			}
		}
		return result;
	}

}
