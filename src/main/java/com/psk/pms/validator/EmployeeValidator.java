package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;
 
public class EmployeeValidator implements Validator{
 
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empId",
			"required.empId", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empPassword",
			"required.empPassword", "Field name is required.");
		
	}
}
