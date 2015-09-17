package com.psk.pms.validator;

import com.psk.pms.model.JsonData;

import java.util.List;

/**
 * Created by Sony on 17-09-2015.
 */
public class AuthorisationValidator {

    public void validate(String teamName, String projectId, JsonData jsonData) {
        if ("0".equalsIgnoreCase(projectId)) {
            jsonData.setData("Please select the Project");
        }
        if (teamName.equalsIgnoreCase("--Please Select--")) {
            jsonData.setData("Please select a team");
        }
    }

    public void validate(List<String> users, String projectId, JsonData jsonData) {
        if ("0".equalsIgnoreCase(projectId)) {
            jsonData.setData("Please select the Project");
        }
        if (users.isEmpty()) {
            jsonData.setData("No employees selected");
        }
    }
}
