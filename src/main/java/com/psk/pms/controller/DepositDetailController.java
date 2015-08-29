package com.psk.pms.controller;

import com.google.gson.Gson;
import com.psk.pms.model.DepositDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.service.DepositDetailService;
import com.psk.pms.validator.DepositDetailValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class DepositDetailController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(DepositDetailController.class);

    @Autowired
    private DepositDetailService depositDetailService;

    @Autowired
    private DepositDetailValidator depositDetailValidator;

    @RequestMapping(value = "/emp/myview/buildDepositDetail/{employeeId}", method = RequestMethod.GET)
    public String buildDepositDetail(
            @PathVariable String employeeId,
            @RequestParam(value = "depositId", required = false) String depositId,
            @RequestParam(value = "action", required = false) String action,
            @RequestParam(value = "aliasProjectName", required = false) String aliasProjectName,
            @RequestParam(value = "aliasSubProjectName", required = false) String aliasSubProjectName,
            Model model) {
        LOGGER.info("Into buildDepositDetail");
        DepositDetail depositDetail = new DepositDetail();
        if ("updateDepositDetail".equalsIgnoreCase(action)) {
            depositDetail = depositDetailService.getDepositDetailsById(depositId);
            depositDetail.setDepositId(Integer.parseInt(depositId));
            model.addAttribute("aliasProjectName", aliasProjectName);
            depositDetail.setIsUpdate("Y");
            depositDetail.setEmployeeId(employeeId);
            Employee employee = new Employee();
            employee.setEmployeeId(employeeId);
            model.addAttribute("depositDetailForm", depositDetail);
        } else {
            depositDetail.setEmployeeId(employeeId);
            model.addAttribute("depositDetailForm", depositDetail);
            Map<String, String> aliasProjectList = populateAliasProjectList();
            if (aliasProjectList.size() == 0) {
                model.addAttribute("noProjectCreated",
                        "No Project Found To Be Created. Please Create a Project.");
                return "Welcome";
            } else {
                model.addAttribute("aliasProjectList", aliasProjectList);
            }
        }
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        model.addAttribute("employee", employee);
        return "BuildDepositDetail";
    }

    @RequestMapping(value = "/emp/myview/buildDepositDetail/getSubAliasProject.do", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAliasProject(HttpServletRequest request,
                                     HttpServletResponse response) {
        LOGGER.info("Sub Proj Id : " + request.getParameter("subProjId"));
        Map<String, String> subAliasProjectList = populateSubAliasProjectList(request
                .getParameter("aliasProjectName"));
        subAliasProjectList.put("0", "--Please Select--");
        Gson gson = new Gson();
        String subAliasProjectJson = gson.toJson(subAliasProjectList);
        return subAliasProjectJson;
    }

    @RequestMapping(value = "/emp/myview/buildDepositDetail/createDepositDetail.do", method = RequestMethod.POST)
    public String saveDepositAction(@ModelAttribute("depositDetailForm") DepositDetail depositDetail,
                                BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList();
        Map<String, String> subAliasProjectList = populateSubAliasProjectList(depositDetail
                .getAliasProjectName());
        boolean isDepositDetailSaveSuccessful = false;

        if(new Integer("0").equals(depositDetail.getSubProjId())){
            depositDetail.setSubProjId(null);
        }
        LOGGER.info("depositDetail.getDepositFor()" + depositDetail.getDepositFor());
        depositDetailValidator.validate(depositDetail, result);
        if (!result.hasErrors()) {
            isDepositDetailSaveSuccessful = depositDetailService.createEditDeposit(depositDetail);
        }
        if (result.hasErrors() || !isDepositDetailSaveSuccessful) {
            model.addAttribute("depositDetailForm", depositDetail);
            model.addAttribute("aliasProjectList", aliasProjectList);
            subAliasProjectList.put("0", "--Please Select--");
            model.addAttribute("subAliasProjectList", subAliasProjectList);
            return "BuildDepositDetail";
        } else {
            status.setComplete();
            Employee employee = new Employee();
            employee.setEmployeeId(depositDetail.getEmployeeId());
            model.addAttribute("employee", employee);
            model.addAttribute("depositDetailForm", depositDetail);
            model.addAttribute("depositCreationMessage", "Deposit Detail Creation Successful.");
            model.addAttribute("aliasProjectList", aliasProjectList);
            if (depositDetail.isSubProjectDepositDetail()) {
                model.addAttribute("subAliasProjectList", subAliasProjectList);
                model.addAttribute("showSubProject", true);
            }
        }
        return "BuildDepositDetail";
    }



    @RequestMapping(value = "/emp/myview/updateDepositDetail/{employeeId}", method = RequestMethod.GET)
    public String updateDepositDetail(@PathVariable String employeeId,
                            @RequestParam(value = "team", required = false) String team,
                            @RequestParam(value = "action", required = false) String action,
                            Model model) {

        LOGGER.info("method = updateDepositDetail() ,Action : " + action);
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        model.addAttribute("employee", employee);
        List<DepositDetail> depositDetails = depositDetailService.getDepositDetails();
        model.addAttribute("depositDetailsSize", depositDetails.size());
        model.addAttribute("depositDetails", depositDetails);
        return "updateDepositDetail";
    }

    @RequestMapping(value = "/emp/myview/searchDepositDetail/deleteDeposit.do", method = RequestMethod.POST)
    public void deleteDeposit(HttpServletRequest request,
                          HttpServletResponse response) {
        String depositId = request.getParameter("depositId");
        LOGGER.info("method = deleteDeposit() , depositId Id : " + depositId);
        Integer numericDepositId = Integer.parseInt(depositId);
        depositDetailService.deleteDeposit(numericDepositId);
    }

    @ModelAttribute("depositTypeList")
    public List<String> populateDepositTypeList() {
        List<String> depositTypes = depositDetailService.fetchDepositTypes();
        return depositTypes;
    }

    @ModelAttribute("depositDetailList")
    public List<String> populateDepositDetailList() {
        List<String> depositDetails = Arrays.asList("EMD", "ASD", "WHA", "SD");
        return depositDetails;
    }

    @ModelAttribute("depositStatusList")
    public List<String> populateDepositStatusList() {
        List<String> depositStatusList = Arrays.asList("Submitted", "Completed");
        return depositStatusList;
    }
}
