package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.ProjDescDetail;

public class ProjDescDetailValidator implements Validator{
 
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjDescDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
				"required.projectName", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subProjectName",
				"required.subProjectName", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workType",
			"required.workType", "Enter Work Type");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInFig",
				"required.quantityInFig", "Enter Quantity in Fig.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInWords",
				"required.quantityInWords", "Enter Quantity in Words.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"required.description", "Enter Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasDescription",
				"required.aliasDescription", "Enter Alias Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateInFig",
				"required.rateInFig", "Enter Rate in Words.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateInWords",
				"required.rateInWords", "Enter Rate in Words.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projDescAmount",
				"required.projDescAmount", "Enter Project Amount.");	
		
	}
	
	
}

