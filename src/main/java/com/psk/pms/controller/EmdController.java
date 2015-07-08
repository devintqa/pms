package com.psk.pms.controller;

import com.google.gson.Gson;
import com.psk.pms.model.EmdDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.service.EmdService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
import com.psk.pms.validator.EmdValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmdController {

	@Autowired
	ProjectService projectService;

	@Autowired
	EmdService emdService;

	@Autowired
	EmdValidator emdValidator;

	@Autowired
	SubProjectService subProjectService;

	private static final Logger LOGGER = Logger.getLogger(EmdController.class);

	@RequestMapping(value = "/emp/myview/buildEmd/{employeeId}", method = RequestMethod.GET)
	public String buildEmd(@PathVariable String employeeId,@RequestParam(value="emdId", required=false) String emdId,
						   @RequestParam(value="action", required=false) String action,@RequestParam(value="aliasProjectName", required=false) String aliasProjectName,
						   @RequestParam(value="aliasSubProjectName", required=false) String aliasSubProjectName,
			Model model) {
		LOGGER.info("Into Build EMD");
		EmdDetail emdDetail = new EmdDetail();
		if("updateEmd".equalsIgnoreCase(action))
		{
			emdDetail = emdService.getEmdDetailsByEmdId(emdId);
			emdDetail.setEmdId(Integer.parseInt(emdId));
			model.addAttribute("aliasProjectName",aliasProjectName);
			emdDetail.setIsUpdate("Y");
			emdDetail.setEmployeeId(employeeId);
			Employee employee = new Employee();
			employee.setEmployeeId(employeeId);
			model.addAttribute("emdForm", emdDetail);
		} else {
			emdDetail.setEmployeeId(employeeId);
			model.addAttribute("emdForm", emdDetail);
			Map<String, String> aliasProjectList = populateAliasProjectList();
			if (aliasProjectList.size() == 0) {
				model.addAttribute("noProjectCreated", "No Project Found To Be Created. Please Create a Project.");
				return "Welcome";
			} else {
				model.addAttribute("aliasProjectList", aliasProjectList);
			}
		}
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		model.addAttribute("employee", employee);
		return "BuildEmd";
	}

	@RequestMapping(value = "/emp/myview/buildEmd/getSubAliasProject.do", method = RequestMethod.GET)
	@ResponseBody
	public String getSubAliasProject(HttpServletRequest request, HttpServletResponse response) {
		LOGGER.info("Sub Proj Id : " + request.getParameter("subProjId"));
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
		Gson gson = new Gson();
		String subAliasProjectJson = gson.toJson(subAliasProjectList);
		return subAliasProjectJson;
	}

	public Map<String, String> populateSubAliasProjectList(String project) {
		Map<String, String> aliasSubProjectName = subProjectService.getSubAliasProjectNames(project);
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
		emdType.put("IVP", "IVP");
		emdType.put("KVP", "KVP");
		return emdType;
	}


	@RequestMapping(value = "/emp/myview/buildEmd/createEmd.do", method = RequestMethod.POST)
	public String saveEmdAction(
			@ModelAttribute("emdForm") EmdDetail emdDetail, BindingResult result, Model model, SessionStatus status) {
		Map<String, String> aliasProjectList = populateAliasProjectList();
		Map<String, String> subAliasProjectList = populateSubAliasProjectList(emdDetail.getAliasProjectName());
		boolean isEmdSaveSuccessful = false;
		LOGGER.info("emdDetail.getEmdFor()" + emdDetail.getEmdFor());
		emdValidator.validate(emdDetail, result);
		if (!result.hasErrors()) {
			isEmdSaveSuccessful = emdService.createEditEmd(emdDetail);
		}
		if (result.hasErrors() || !isEmdSaveSuccessful) {
			model.addAttribute("emdForm",emdDetail);
			model.addAttribute("aliasProjectList", aliasProjectList);
			subAliasProjectList.put("0", "--Please Select--");
			model.addAttribute("subAliasProjectList",subAliasProjectList);
			return "BuildEmd";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(emdDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			model.addAttribute("emdForm", emdDetail);
			model.addAttribute("emdCreationMessage", "Emd Creation Successful.");
			model.addAttribute("aliasProjectList",aliasProjectList);
			if(emdDetail.isSubProjectEMD()) {
				model.addAttribute("subAliasProjectList", subAliasProjectList);
				model.addAttribute("showSubProject",true);
			}
		}
		return "BuildEmd";
	}

	@RequestMapping(value = "/emp/myview/updateEmd/{employeeId}", method = RequestMethod.GET)
	public String updateEmd(@PathVariable String employeeId,
								@RequestParam(value="team", required=false) String team,
								@RequestParam(value="action", required=false) String action,
								Model model) {

		LOGGER.info("method = updateEmd() ,Action : " + action);
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		model.addAttribute("employee", employee);
        List<EmdDetail> emdDetails = emdService.getEmdDetails();
		model.addAttribute("emdDetailsSize", emdDetails.size());
		model.addAttribute("emdDetails", emdDetails);
		return "updateEmd";
	}

	@RequestMapping(value = "/emp/myview/searchEmd/deleteEmd.do", method = RequestMethod.POST)
	public void deleteEmd(HttpServletRequest request, HttpServletResponse response) {
		String emdId = request.getParameter("emdId");
		LOGGER.info("method = deleteEmd() , emd Id : " + emdId);
		Integer numericEmdId = Integer.parseInt(emdId);
		emdService.deleteEmd(numericEmdId);
	}
}

