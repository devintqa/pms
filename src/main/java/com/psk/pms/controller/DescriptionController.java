package com.psk.pms.controller;

import static com.psk.pms.Constants.METRIC;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.psk.pms.constants.DescriptionType;
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
import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.ProjectService;
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
    private ProjectService projectService;


    @Autowired
    private BaseDescriptionValidator baseDescriptionValidator;

    private static final Logger LOGGER = Logger.getLogger(DescriptionController.class);

    @ModelAttribute("itemUnits")
    public List<String> populateItemUnits() {
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
        Map<String, String> aliasProjectList = new HashMap<String, String>();
        Map<String, String> subAliasProjectList = new HashMap<String, String>();

        if (null != descId) {
            projDescDetail = projectDescriptionService.getProjectDescDetail(descId, subProject, descType);
            projDescDetail.setIsUpdate("Y");
            projDescDetail.setEmployeeId(employeeId);
            aliasProjectList.put(projDescDetail.getProjId().toString(), projDescDetail.getAliasProjectName());
            model.addAttribute("aliasProjectList", aliasProjectList);
            projDescDetail.setDescType(descType);

            if (null != projDescDetail.getAliasSubProjectName()) {
                subAliasProjectList.put(projDescDetail.getSubProjId().toString(), projDescDetail.getAliasSubProjectName());
                projDescDetail.setSubProjectDesc(true);
                model.addAttribute("subAliasProjectList", subAliasProjectList);
            }
        } else {
            projDescDetail = new ProjDescDetail();
            projDescDetail.setEmployeeId(employeeId);
            aliasProjectList.putAll(populateAliasProjectList(employeeId));
            if (aliasProjectList.size() == 0) {
                model.addAttribute("noProjectCreated",
                        "No Project Found To Be Created. Please Create a Project.");
                return "Welcome";
            }
            model.addAttribute("subAliasProjectList", subAliasProjectList);
            model.addAttribute("aliasProjectList", aliasProjectList);
        }
        model.addAttribute("projDescForm", projDescDetail);
        return "BuildDescription";
    }

    @RequestMapping(value = "/emp/myview/buildProjectDesc/createProjDesc.do", method = RequestMethod.POST)
    public String saveProjDescAction(@ModelAttribute("projDescForm") ProjDescDetail projDescDetail, BindingResult result, Model model, SessionStatus status) {
        try {
            Map<String, String> aliasProjectList = populateAliasProjectList(projDescDetail.getEmployeeId());
            String employeeId = projDescDetail.getEmployeeId();
            Employee employee = new Employee();
            employee.setEmployeeId(employeeId);
            model.addAttribute("employee", employee);
            projDescDetailValidator.validate(projDescDetail, result);
            LOGGER.info("Result has errors ?? " + result.hasErrors() + result.toString());
            if (result.hasErrors()) {
                model.addAttribute("aliasProjectList", aliasProjectList);
                model.addAttribute("subAliasProjectList", fetchSubAliasProjectList(projDescDetail.getAliasProjectName(), projDescDetail.getEmployeeId()));
                return "BuildDescription";
            } else {
                projectDescriptionService.createEditProjDesc(projDescDetail);
                if (!"Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
                    model.addAttribute("projDescCreationMessage", "Project Description Creation Successful.");
                    model.addAttribute("aliasProjectList", aliasProjectList);
                    model.addAttribute("subAliasProjectList", fetchSubAliasProjectList(projDescDetail.getAliasProjectName(), projDescDetail.getEmployeeId()));
                    status.setComplete();
                    return "BuildDescription";
                } else {
                    projDescDetail.setIsUpdate("Y");
                    projDescDetail.setEmployeeId(employeeId);
                    aliasProjectList = new HashMap<String, String>();
                    aliasProjectList.put(projDescDetail.getProjId().toString(), projDescDetail.getAliasProjectName());
                    model.addAttribute("aliasProjectList", aliasProjectList);
                    model.addAttribute("subAliasProjectList", new ArrayList<String>());
                    model.addAttribute("projDescForm", projDescDetail);
                    model.addAttribute("projDescCreationMessage", "Project Description Updated Successfully.");
                    status.setComplete();
                }
                return "BuildDescription";
            }
        } catch (Exception e) {
            model.addAttribute("projDescCreationMessage", "Project Description creation failed.");
            LOGGER.error("project description save failed." + e);
            return "BuildDescription";
        }
    }

    @RequestMapping(value = "/emp/myview/searchProjectDescription/deleteProjectDescription.do", method = RequestMethod.POST)
    public void deleteProjectDescriptionDetail(HttpServletRequest request,
                                               HttpServletResponse response) {
        String projectDescriptionId = request.getParameter("projectDescriptionId");
        String projectDescriptionType = request.getParameter("projectDescriptionType");
        LOGGER.info("Deleting project description ,projectDescriptionId : " + projectDescriptionId);
        projectDescriptionService.deleteProjectDescriptionDetail(projectDescriptionId, projectDescriptionType);
    }

    @RequestMapping(value = "/emp/myview/buildProjectDesc/getSubAliasProject.do", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAliasProject(HttpServletRequest request,
                                     HttpServletResponse response) {
        LOGGER.info("method = getSubAliasProject() , Sub Project Id : " + request.getParameter("subProjId"));
        Map<String, String> subAliasProjectList = fetchSubAliasProjectList(request.getParameter("aliasProjectName"), request.getParameter("empId"));
        Gson gson = new Gson();
        String subAliasProjectJson = gson.toJson(subAliasProjectList);
        return subAliasProjectJson;
    }

    @ModelAttribute("workTypeList")
    public Map<String, String> populateWorkTypeList() {
        Map<String, String> workType = new LinkedHashMap<String, String>();
        workType.put("Main Work", "Main Work");
        workType.put("Electrical", "Electrical");
        workType.put("Plumbing", "Plumbing");
        return workType;
    }

    private Map<String, String> fetchSubAliasProjectList(String aliasProjectName, String employeeId) {
        Map<String, String> subAliasProjectList = populateSubAliasProjectList(aliasProjectName, employeeId);
        subAliasProjectList.put("0", "--Please Select--");
        return subAliasProjectList;
    }

    @RequestMapping(value = "/emp/myview/buildBaseDesc/{employeeId}", method = RequestMethod.GET)
    public String buildBaseProjDesc(@PathVariable String employeeId,
                                    @RequestParam(value = "team", required = true) String team,
                                    @RequestParam(value = "action", required = false) String action,
                                    @RequestParam(value = "desc", required = false) String descId,
                                    Model model) {
        ProjDescDetail projDescDetail = new ProjDescDetail();
        if (null != action && "edit".equalsIgnoreCase(action)) {
            projDescDetail = projectDescriptionService.getBaseDescription(descId);
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
        baseDescriptionValidator.validate(projDescDetail, result);
        LOGGER.info("Result has errors ?? " + result.hasErrors() + result.toString());
        if (result.hasErrors()) {
            return "BaseDescription";
        }
        model.addAttribute("baseDescForm", projDescDetail);
        if ("Y".equalsIgnoreCase(projDescDetail.getIsUpdate())) {
            model.addAttribute("projDescCreationMessage",
                    "Base Description Updated Successfully");
        } else {
            model.addAttribute("projDescCreationMessage",
                    "Base Description Created Successfully.");
        }
        projectDescriptionService.saveBaseProjectDescription(projDescDetail);
        return "BaseDescription";
    }

    @RequestMapping(value = "/emp/myview/searchBaseDescription/deleteGlobalProjectDescription.do", method = RequestMethod.POST)
    public void deleteGlobalProjectDescriptionDetail(
            HttpServletRequest request, HttpServletResponse response) {
        String aliasBaseDescription = request.getParameter("aliasDescription");
        LOGGER.info("Deleting project description ,projectDescriptionId : " + aliasBaseDescription);
        projectDescriptionService.deleteBaseProjectDescription(aliasBaseDescription);
    }


    @ModelAttribute("metricList")
    public List<String> metricList() {
        List<String> metrics = projectService.getDropDownValuesFor(METRIC);
        Collections.sort(metrics);
        return metrics;

    }
}