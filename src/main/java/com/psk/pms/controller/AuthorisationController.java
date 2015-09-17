package com.psk.pms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;
import com.psk.pms.Constants;
import com.psk.pms.model.Authorize;
import com.psk.pms.model.JsonData;
import com.psk.pms.model.Permission;
import com.psk.pms.service.AuthorisationService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.validator.AuthorisationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static com.psk.pms.Constants.DEPOSIT_TYPE;
import static com.psk.pms.Constants.TEAM;

@Controller
public class AuthorisationController {

    @Autowired
    protected ProjectService projectService;

    @Autowired
    AuthorisationService authorisationService;

    @Autowired
    private
    AuthorisationValidator authorisationValidator;


    @RequestMapping(value = "/emp/myview/manageAccess/{employeeId}", method = RequestMethod.GET)
    public String grantRights(@PathVariable String employeeId, Model model) {


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
        List<Permission> permissions = authorisationService.getPermissionList(teamName);
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
                                             @RequestParam(value = "toAuthorize") List<String> users,
                                             Model model) {
        Gson gson = new Gson();
        JsonData jsonData = new JsonData();
        authorisationValidator.validate(users, projectId, jsonData);
        if (!isNullOrEmpty(jsonData.getData())) {
            return jsonData;
        }
        List<Permission> permissions = authorisationService.saveProjectUserPrivilage(projectId, users);
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

    @ModelAttribute("teamList")
    public List<String> populateDepositTypeList() {
        List<String> dropDownValues = projectService.getDropDownValuesFor(TEAM);
        dropDownValues.remove("Admin");
        dropDownValues.add(0, "--Please Select--");
        return dropDownValues;
    }

}
