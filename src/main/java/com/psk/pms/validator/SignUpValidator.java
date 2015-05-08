package com.psk.pms.validator;

import java.util.regex.Matcher;  
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Employee;
 
public class SignUpValidator extends BaseValidator implements Validator{
	
	 private Pattern pattern;  
	 private Matcher matcher;
 
	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return Employee.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeFName",
				"required.employeeFName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeLName",
				"required.employeeLName", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeePwd",
			"required.employeePwd", "Field name is required.");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeConfirmPwd",
				"required.employeeConfirmPwd", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeTeam",
				"required.employeeTeam", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeAddress",
				"required.employeeAddress", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeGender",
				"required.employeeGender", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeMobile",
				"required.employeeMobile", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeMail",
				"required.employeeMail", "Field name is required.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "employeeMotherMaidenName",
				"required.employeeMotherMaidenName", "Field name is required.");
		
		Employee employee = (Employee)target;
		 
		if(!(employee.getEmployeePwd().equals(employee.getEmployeeConfirmPwd()))){
			errors.rejectValue("employeePwd", "notmatch.password");
		}	
		
		if (!(employee.getEmployeeMobile() != null && employee
				.getEmployeeMobile().isEmpty())) {
			pattern = Pattern.compile(MOBILE_PATTERN);
			matcher = pattern.matcher(employee.getEmployeeMobile());
			if (!matcher.matches()) {
				errors.rejectValue("employeeMobile",
						"employeeMobile.incorrect",
						"Enter a correct phone number");
			}
		}
		if (!(employee.getEmployeeMail() != null && employee
				.getEmployeeMail().isEmpty())) {
			pattern = Pattern.compile(EMAIL_PATTERN);
			matcher = pattern.matcher(employee.getEmployeeMail());
			if (!matcher.matches()) {
				errors.rejectValue("employeeMail",
						"employeeMail.incorrect",
						"Enter a correct mail id");
			}
		}
		if (employee.getEmployeeFName() != null && !employee.getEmployeeFName().isEmpty()) {
			pattern = Pattern.compile(NAME_PATTERN);
			matcher = pattern.matcher(employee.getEmployeeFName());
			if (!matcher.matches()) {
				errors.rejectValue("employeeFName",
						"employeeFName.incorrect",
						"First Name Should Be All Alphabets. Minimum of 2 characters and Maximum of 20 characters makes perfect First Name");
			}
		}
		if (employee.getEmployeeLName() != null && !employee.getEmployeeLName().isEmpty()) {
			pattern = Pattern.compile(NAME_PATTERN);
			matcher = pattern.matcher(employee.getEmployeeLName());
			if (!matcher.matches()) {
				errors.rejectValue("employeeLName",
						"employeeLName.incorrect",
						"Last Name Should Be All Alphabets. Minimum of 2 characters and Maximum of 20 characters makes perfect Last Name");
			}
		}
}
}