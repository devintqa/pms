package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.ProjectDetail;

public class ProjectDetailValidator implements Validator{
 
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
 
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "requestDate",
			"required.requestDate", "Field name is required.");
		
	}
	
	public void validateNotNeedEmptyValue(Object target, Errors errors, boolean status) {	 
		if(status){
			errors.rejectValue("dropTime", "Required.dropTime");
		}		
	}
	
	public void validateDropTime(Object target, Errors errors, boolean status) {	 
		if(status){
			errors.rejectValue("dropTime", "Required.validDropTime");
		}		
	}
	
	
}

