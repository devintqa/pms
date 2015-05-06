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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
				"required.projectName", "Enter Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasName",
			"required.aliasName", "Enter Alias Project Name.");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agreementNo",
				"required.agreementNo", "Enter Agreement Number.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "cerNo",
				"required.cerNo", "Enter CER Number.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "Amount",
				"required.Amount", "Enter Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractorName",
				"required.contractorName", "Enter Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractorAddress",
				"required.contractorAddress", "Enter Contractor Address.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractValue",
				"required.contractValue", "Enter Contract Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agreementValue",
				"required.agreementValue", "Enter Agreement Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderValue",
				"required.tenderValue", "Enter Tender Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exAmount",
				"required.exAmount", "Enter Expected Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exPercentage",
				"required.exPercentage", "Enter Expected Amount in Percentage.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderDate",
				"required.tenderDate", "Select Tender Date.");
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agreementDate",
				"required.agreementDate", "Select Agreement Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "commencementDate",
				"required.commencementDate", "Select Commencement Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "completionDate",
				"required.completionDate", "Select Completion Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agreementPeriod",
				"required.agreementPeriod", "Select Agreement Period.");*/
		
	}
	
	
}

