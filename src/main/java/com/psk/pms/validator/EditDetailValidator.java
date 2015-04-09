package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;

public class EditDetailValidator implements Validator{
	 
		@Override
		public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
			return Employee.class.isAssignableFrom(clazz);
		}
	 
		@Override
		public void validate(Object target, Errors errors) {
	
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empPassword",
				"required.empPassword", "Field name is required.");		
			
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeFName",
					"required.employeeFName", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeLName",
					"required.employeeLName", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeAddress",
					"required.employeeAddress", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeDOJ",
					"required.employeeDOJ", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeMobile",
					"required.employeeMobile", "Field name is required.");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeMail",
					"required.employeeMail", "Field name is required.");

		}
		
	}
