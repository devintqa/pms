package com.psk.pms.validator;

import com.psk.pms.model.Team;
import com.psk.pms.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;

public class EmployeeValidator implements Validator {

    @Autowired
    EmployeeService employeeService;


	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

        Team team = (Team) target;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "roleName",
                "required.roleName", "Enter Role Name.");


        if (team.getRoleName().length() > 100) {
            errors.rejectValue("roleName", "roleName.incorrect",
                    "Role name must not exceed 100 characters.");
        } else {
            boolean isRoleExisting = employeeService
                    .isRoleExists(team.getRoleName(),team.getTeamName());
            if (isRoleExisting) {
                errors.rejectValue("roleName", "roleName.incorrect",
                        "Role Name Already Found To Be Existing.");
            }
        }

    }
}
