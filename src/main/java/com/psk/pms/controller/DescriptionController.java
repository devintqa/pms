package com.psk.pms.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

import com.google.gson.Gson;
import com.psk.pms.model.Employee;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.validator.BaseDescriptionValidator;
import com.psk.pms.validator.ProjDescDetailValidator;

@Controller
public class DescriptionController extends BaseController {

	@Autowired
	private ProjDescDetailValidator projDescDetailValidator;

	@Autowired
	private ProjectDescriptionService projectDescriptionService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private BaseDescriptionValidator baseDescriptionValidator;

	private static final Logger LOGGER = Logger.getLogger(DescriptionController.class);

	@ModelAttribute("itemUnits")
	public List < String > populateItemUnits() {
		return itemService.fetchUniqueItemUnits();
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/{employeeId}", method = RequestMethod.GET)
	public String buildProjDesc(@PathVariable String employeeId, @RequestParam(value = "team", required = true) String team, 
			@RequestParam(value = "action", required = false) String action, 
			@RequestParam(value = "project", required = false) String project, 
			@RequestParam(value = "subproject", required = false) String subProject, 
			@RequestParam(value = "desc", required = false) String descId, 
			@RequestParam(value = "type", required = false) String descType,
			Model model) {
		ProjDescDetail projDescDetail = null;
		if (null != descId) {
			Map < String, String > aliasProjectList = new HashMap < String, String > ();
			Map < String, String > subAliasProjectList = new HashMap < String, String > ();
			//				if (Constants.PSK.equalsIgnoreCase(descType)) {
			//					projDescDetail = projectDescriptionService.getProjectDescDetail(descId, subProject);
			//				} else {
			//					projDescDetail = projectDescriptionService.getGovProjectDescDetail(descId);
			//				}
			projDescDetail = projectDescriptionService.getProjectDescDetail(descId,subProject,descType);
			projDescDetail.setIsUpdate("Y");
			projDescDetail.setEmployeeId(employeeId);
			aliasProjectList.put(projDescDetail.getProjId().toString(),
					projDescDetail.getAliasProjectName());
			model.addAttribute("aliasProjectList", aliasProjectList);
			projDescDetail.setDescType(descType);

			if (null != projDescDetail.getAliasSubProjectName()) {
				subAliasProjectList.put(projDescDetail.getSubProjId().toString(), projDescDetail.getAliasSubProjectName());
				projDescDetail.setSubProjectDesc(true);
				model.addAttribute("subAliasProjectList", subAliasProjectList);
			}

			model.addAttribute("projDescForm", projDescDetail);
		} 

		return "BuildDescription";
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/createProjDesc.do", method = RequestMethod.POST)
	public String saveProjDescAction(@ModelAttribute("projDescForm") ProjDescDetail projDescDetail, BindingResult result, Model model, SessionStatus status) {
		boolean isProjectSaveSuccessful = false;
		Map < String, String > aliasProjectList = populateAliasProjectList();
		projDescDetailValidator.validate(projDescDetail, result);
		LOGGER.info("Result has errors ?? " + result.hasErrors() + result.toString());
		if (!result.hasErrors()) {
			isProjectSaveSuccessful = projectDescriptionService.createEditProjDesc(projDescDetail);
		}
		if (result.hasErrors() || !isProjectSaveSuccessful) {
			model.addAttribute("aliasProjectList", aliasProjectList);
			model.addAttribute("subAliasProjectList", fetchSubAliasProjectList(projDescDetail.getAliasProjectName()));
			return "BuildDescription";
		} else {
			status.setComplete();
			Employee employee = new Employee();
			employee.setEmployeeId(projDescDetail.getEmployeeId());
			model.addAttribute("employee", employee);
			if (!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
				model.addAttribute("projDescCreationMessage", "Project Description Creation Successful.");
				model.addAttribute("aliasProjectList", aliasProjectList);
				model.addAttribute("subAliasProjectList",
						fetchSubAliasProjectList(projDescDetail.getAliasProjectName()));
			} else {
				isProjectSaveSuccessful = projectDescriptionService.createEditProjDesc(projDescDetail);
				aliasProjectList = new HashMap < String, String > ();
				aliasProjectList.put(projDescDetail.getProjId().toString(),
						projDescDetail.getAliasProjectName());
				model.addAttribute("aliasProjectList", aliasProjectList);
				if (null != projDescDetail.getAliasSubProjectName()) {
					projDescDetail.setIsUpdate("Y");
					model.addAttribute("aliasProjectList", aliasProjectList);
					model.addAttribute("subAliasProjectList", fetchSubAliasProjectList(projDescDetail.getAliasProjectName()));
					model.addAttribute("projDescForm", projDescDetail);
					model.addAttribute("projDescCreationMessage", "Project Description Updated Successfully.");
				}
			}
			return "BuildDescription";
		}
	}

	@RequestMapping(value = "/emp/myview/searchProjectDescription/deleteProjectDescription.do", method = RequestMethod.POST)
	public void deleteProjectDescriptionDetail(HttpServletRequest request,
			HttpServletResponse response) {
		String projectDescriptionId = request.getParameter("projectDescriptionId");
		String projectDescriptionType = request.getParameter("projectDescriptionType");
		LOGGER.info("Deleting project description ,projectDescriptionId : " + projectDescriptionId);
		projectDescriptionService.deleteProjectDescriptionDetail(projectDescriptionId,projectDescriptionType);
	}

	@RequestMapping(value = "/emp/myview/buildProjectDesc/getSubAliasProject.do", method = RequestMethod.GET)@ResponseBody
	public String getSubAliasProject(HttpServletRequest request,
			HttpServletResponse response) {
		LOGGER.info("method = getSubAliasProject() , Sub Project Id : " + request.getParameter("subProjId"));
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"));
		subAliasProjectList.put("0", "--Please Select--");
		Gson gson = new Gson();
		String subAliasProjectJson = gson.toJson(subAliasProjectList);
		return subAliasProjectJson;
	}

	@ModelAttribute("workTypeList")
	public Map < String, String > populateWorkTypeList() {
		Map < String, String > workType = new LinkedHashMap < String, String > ();
		workType.put("Main Work", "Main Work");
		workType.put("Electrical", "Electrical");
		workType.put("Plumbing", "Plumbing");
		return workType;
	}

	public List < ProjectDetail > getProjectDocumentList() {
		List < ProjectDetail > projectDocumentList = projectService.getProjectDocumentList();
		return projectDocumentList;
	}

	public List < SubProjectDetail > getSubProjectDocumentList(Integer projectId) {
		List < SubProjectDetail > subProjectDocumentList = subProjectService.getSubProjectDocumentList(projectId);
		return subProjectDocumentList;
	}

	private Map < String, String > fetchSubAliasProjectList(String aliasProjectName) {
		Map < String, String > subAliasProjectList = populateSubAliasProjectList(aliasProjectName);
		subAliasProjectList.put("0", "--Please Select--");
		return subAliasProjectList;
	}

	@RequestMapping(value = "/emp/myview/buildBaseDesc/{employeeId}", method = RequestMethod.GET)
	public String buildBaseProjDesc(@PathVariable String employeeId, @RequestParam(value = "team", required = true) String team, @RequestParam(value = "action", required = false) String action, @RequestParam(value = "project", required = false) String aliasDescription,
			Model model) {
		ProjDescDetail projDescDetail = new ProjDescDetail();
		if (null != action && "edit".equalsIgnoreCase(action)) {
			projDescDetail = projectDescriptionService.getBaseDescription(aliasDescription);
			projDescDetail.setIsUpdate("Y");
			model.addAttribute("baseDescForm", projDescDetail);
			return "BaseDescription";
		}

		projDescDetail.setEmployeeId(employeeId);
		projDescDetail.setQuantity("1");
		model.addAttribute("baseDescForm", projDescDetail);
		return "BaseDescription";
	}

	@RequestMapping(value = "/emp/myview/buildBaseDesc/createOrUpdate.do", method = RequestMethod.POST)
	public String saveBaseProjDesc(@ModelAttribute("baseDescForm") ProjDescDetail projDescDetail,
			BindingResult result, Model model, SessionStatus status) {
		System.out.println(projDescDetail.getBaseCategory());
		baseDescriptionValidator.validate(projDescDetail, result);
		LOGGER.info("Result has errors ?? " + result.hasErrors() + result.toString());
		if (result.hasErrors()) {
			return "BaseDescription";
		}
		model.addAttribute("baseDescForm", projDescDetail);
		if ("Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
			model.addAttribute("projDescCreationMessage",
					"Base Description updated successfully");
		} else {
			model.addAttribute("projDescCreationMessage",
					"Base Description creation successfully");
		}
		projectDescriptionService.saveBaseProjectDescription(projDescDetail);
		return "BaseDescription";
	}

	@RequestMapping(value = "/emp/myview/searchBaseDescription/deleteGlobalProjectDescription.do", method = RequestMethod.POST)
	public void deleteGlobalProjectDescriptionDetail(
			HttpServletRequest request, HttpServletResponse response) {
		String projectDescriptionId = request.getParameter("baseDescriptionId");
		LOGGER.info("Deleting project description ,projectDescriptionId : " + projectDescriptionId);
		projectDescriptionService.deleteBaseProjectDescription(projectDescriptionId);
	}
}