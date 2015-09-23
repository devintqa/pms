package com.psk.pms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.psk.pms.model.Authorize;
import com.psk.pms.model.JsonData;
import com.psk.pms.model.Permission;
import com.psk.pms.service.AuthorisationService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.AuthorisationValidator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static com.psk.pms.Constants.TEAM;

@Controller
public class AuthorisationController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private AuthorisationService authorisationService;

    @Autowired
    private AuthorisationValidator authorisationValidator;
    
    private static final Logger LOGGER = Logger.getLogger(AuthorisationController.class);


    @RequestMapping(value = "/emp/myview/manageAccess/{employeeId}", method = RequestMethod.GET)
    public String grantRights(@PathVariable String employeeId, Model model) {
    	LOGGER.info("Build Authorization Start");
        Authorize authorize = new Authorize();
        authorize.setEmployeeId(employeeId);
        authorize.setPrivilegeDetails(null);
        model.addAttribute("authorize", authorize);
        Map<String, String> aliasProjectNames = projectService.getAliasProjectNames();
        model.addAttribute("projectList", aliasProjectNames);
        return "Authorization";
    }

    @RequestMapping(value = "/emp/myview/manageAccess/getProjectUserPrivilege.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getProjectUserPrivilege(@RequestParam(value = "employeeId") String employeeId,
                                            @RequestParam(value = "projectId") String projectId,
                                            @RequestParam(value = "teamName") String teamName,
                                            Model model) {
        JsonData jsonData = new JsonData();
        authorisationValidator.validate(teamName, projectId, jsonData);
        if (!isNullOrEmpty(jsonData.getData())) {
            return jsonData;
        }
        List<Permission> permissions = authorisationService.getPermissionList(teamName, projectId);
        if (permissions.isEmpty()) {
            jsonData.setData("No Employees present.Please select another Team");
            return jsonData;
        }
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(permissions, new TypeToken<List<Permission>>() {
        }.getType());

        JsonArray jsonArray = element.getAsJsonArray();
        Authorize authorize = new Authorize();
        authorize.setEmployeeId(employeeId);
        authorize.setPrivilegeDetails(jsonArray.toString());
        model.addAttribute("authorize", authorize);
        jsonData.setData(jsonArray.toString());
        jsonData.setSuccess(true);
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/manageAccess/saveProjectUserPrivilege.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData saveProjectUserPrivilege(@RequestParam(value = "employeeId") String employeeId,
                                             @RequestParam(value = "projectId") String projectId,
                                             @RequestParam(value = "teamName") String teamName,
                                             @RequestParam(value = "toAuthorize") List<String> selectedUsers,
                                             @RequestParam(value = "toDeAuthorize") List<String> availableUsers,
                                             Model model) {
        JsonData jsonData = new JsonData();
        try {
            Gson gson = new Gson();
            authorisationValidator.validate(selectedUsers, projectId, jsonData,availableUsers,teamName);
            if (!isNullOrEmpty(jsonData.getData())) {
                return jsonData;
            }
            List<Permission> permissions = authorisationService.saveProjectUserPrivilege(projectId, selectedUsers, teamName, availableUsers);
            JsonElement element = gson.toJsonTree(permissions, new TypeToken<List<Permission>>() {
            }.getType());
            JsonArray jsonArray = element.getAsJsonArray();
            Authorize authorize = new Authorize();
            authorize.setEmployeeId(employeeId);
            authorize.setPrivilegeDetails(jsonArray.toString());
            model.addAttribute("authorize", authorize);
            jsonData.setData(jsonArray.toString());
            jsonData.setSuccess(true);
        } catch (Exception e) {
        	LOGGER.info("Exception In saveProjectUserPrivilege()",e);
            jsonData.setData(e.getMessage());
        }
        return jsonData;
    }

    @ModelAttribute("teamList")
    public List<String> populateDepositTypeList() {
        List<String> dropDownValues = projectService.getDropDownValuesFor(TEAM);
        dropDownValues.remove("Admin");
        dropDownValues.add(0, "--Please Select--");
        return dropDownValues;
    }

}
