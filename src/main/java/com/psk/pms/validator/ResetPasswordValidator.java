package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;

public class ResetPasswordValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeePwd",
				"required.employeePwd", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeConfirmPwd",
				"required.employeeConfirmPwd", "Field name is required.");

		Employee employee = (Employee) target;

		if (!(employee.getEmployeePwd()
				.equals(employee.getEmployeeConfirmPwd()))) {
			errors.rejectValue("empPassword", "notmatch.password");
		}
	}

}
