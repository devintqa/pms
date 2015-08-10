package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;

public class ForgotpasswordValidator implements Validator {

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeId",
				"required.employeeId", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"employeeMotherMaidenName",
				"required.employeeMotherMaidenName", "Field name is required.");

	}

	public void validateForgotPwdEmpId(Object target, Errors errors,
			boolean status) {
		if (status) {
			errors.rejectValue("employeeId", "employee.notexisting");
		}
	}

	public void validateForgotPwdMotherMaiden(Object target, Errors errors,
			boolean status) {
		if (status) {
			errors.rejectValue("employeeMotherMaidenName",
					"employee.MotherMaiden");
		}
	}

}
