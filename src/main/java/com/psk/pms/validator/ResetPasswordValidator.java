package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;

public class ResetPasswordValidator  implements Validator{
	 
		@Override
		public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
			return Employee.class.isAssignableFrom(clazz);
		}
	 
		@Override
		public void validate(Object target, Errors errors) {
	 
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "empPassword",
					"required.empPassword", "Field name is required.");		
				ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeConfirmPWD",
						"required.employeeConfirmPWD", "Field name is required.");
			

				Employee employee = (Employee)target;
				 
				if(!(employee.getEmpPassword().equals(employee.getEmployeeConfirmPWD()))){
					errors.rejectValue("empPassword", "notmatch.password");
				}
		}
		
		
	}
