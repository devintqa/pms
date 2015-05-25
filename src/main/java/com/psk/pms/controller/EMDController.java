package com.psk.pms.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.psk.pms.model.EMDDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.service.ProjectService;

@Controller
public class EMDController {
	
	@Autowired
	ProjectService projectService;
	
	private static final Logger LOGGER = Logger.getLogger(EMDController.class);
	
	@RequestMapping(value = "/emp/myview/buildEmd/{employeeId}", method = RequestMethod.GET)
	public String buildEmd(@PathVariable String employeeId, 
			Model model) {		
		LOGGER.info("Into Build EMD");
		EMDDetail emdDetail = new EMDDetail();
		model.addAttribute("emdForm", emdDetail);
		Map<String, String> aliasProjectList = populateAliasProjectList();
		if(aliasProjectList.size() == 0){
			model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
			return "Welcome";
		} else{
			model.addAttribute("aliasProjectList", aliasProjectList);
		}
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);

		return "BuildEmd";
	}
	
	@RequestMapping(value = "/emp/myview/buildEmd/getSubAliasProject.do", method = RequestMethod.GET)
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
	
	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}
	
	@ModelAttribute("emdTypeList")
	public Map<String, String> populateEmdTypeList() {
		Map<String, String> emdType = new LinkedHashMap<String, String>();
		emdType.put("Bank Guarantee", "Bank Guarantee");
		emdType.put("DD", "DD");
		emdType.put("FD", "FD");
		emdType.put("IUP", "IUP");
		emdType.put("KVP", "KVP");
		return emdType;
	}

}
