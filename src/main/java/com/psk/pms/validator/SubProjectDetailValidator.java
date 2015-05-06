package com.psk.pms.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.SubProjectDetail;

public class SubProjectDetailValidator implements Validator{
 
	@Override
	public boolean supports(Class<?> clazz) {
		return SubProjectDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projId",
				"required.projId", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subProjectName",
				"required.subProjectName", "Enter Sub Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAliasName",
			"required.subAliasName", "Enter Alias Sub Project Name.");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAgreementNo",
				"required.subAgreementNo", "Enter Agrement Number.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subCerNo",
				"required.subCerNo", "Enter CER Number.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAmount",
				"required.subAmount", "Enter Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subContractorName",
				"required.subContractorName", "Enter Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subContractorAddress",
				"required.subContractorAddress", "Enter Contractor Address.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subContractValue",
				"required.subContractValue", "Enter Contract Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAgreementValue",
				"required.subAgreementValue", "Enter Agreement Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subTenderValue",
				"required.subTenderValue", "Enter Tender Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subExAmount",
				"required.subExAmount", "Enter Expected Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subExPercentage",
				"required.subExPercentage", "Enter Expected Amount in Percentage.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subTenderDate",
				"required.subTenderDate", "Select Tender Date.");
		/*ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAgreementDate",
				"required.subAgreementDate", "Select Agreement Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subCommencementDate",
				"required.subCommencementDate", "Select Commencement Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subCompletionDate",
				"required.subCompletionDate", "Select Completion Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAgreementPeriod",
				"required.subAgreementPeriod", "Select Agreement Period.");*/
		
	}
	
	
}

