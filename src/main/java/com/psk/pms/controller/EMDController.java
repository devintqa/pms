package com.psk.pms.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.psk.pms.model.SubProjectDetail;

@Controller
public class EMDController {
	
	/*@RequestMapping(value = "/emp/myview/buildEmd/{employeeId}", method = RequestMethod.GET)
	public String buildEmd(@PathVariable String employeeId, 
			Model model) {
			SubProjectDetail subProjectDetail = new SubProjectDetail();
			subProjectDetail.setEmployeeId(employeeId);
			model.addAttribute("emdForm", subProjectDetail);
			Map<String, String> aliasProjectList = populateAliasProjectList();
			System.out.println(aliasProjectList);
			if(aliasProjectList.size() == 0){
				model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
				return "Welcome";
			} else{
				model.addAttribute("aliasProjectList", aliasProjectList);
			}

		return "BuildSubProject";
	}*/

}
