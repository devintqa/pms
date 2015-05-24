package com.psk.pms.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.model.EMDDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;

@Controller
public class EMDController {
	
	private static final Logger LOGGER = Logger.getLogger(EMDController.class);
	
	@RequestMapping(value = "/emp/myview/buildEmd/{employeeId}", method = RequestMethod.GET)
	public String buildProject(@PathVariable String employeeId,
			@ModelAttribute("projectForm") ProjectDetail projectDetail,
			BindingResult result, Model model, SessionStatus status) {		
		LOGGER.info("Into Build EMD");
		LOGGER.info("Alias Project Name" + projectDetail.getAliasName());
		EMDDetail emdDetail = new EMDDetail();
		model.addAttribute("emdForm", emdDetail);

		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);

		return "BuildEmd";
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
