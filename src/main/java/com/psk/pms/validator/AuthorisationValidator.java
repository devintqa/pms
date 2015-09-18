package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.JsonData;

import java.util.List;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

/**
 * Created by Sony on 17-09-2015.
 */
public class AuthorisationValidator {

    public void validate(String teamName, String projectId, JsonData jsonData) {
        if ("0".equalsIgnoreCase(projectId)) {
            jsonData.setData("Please select the Project");
        }
        if (isNullOrEmpty(jsonData.getData()) && teamName.equalsIgnoreCase("--Please Select--")) {
            jsonData.setData("Please select a team");
        }
    }

    public void validate(List<String> users, String projectId, JsonData jsonData, List<String> availableUsers, String teamName) {
        validate(teamName, projectId, jsonData);
        if(isNullOrEmpty(jsonData.getData()) && users.isEmpty() && availableUsers.isEmpty()){
            jsonData.setData("No Employees to Authorize");
        }
    }
}
