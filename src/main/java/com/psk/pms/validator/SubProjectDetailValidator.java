package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;

public class SubProjectDetailValidator extends BaseValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;
	
	@Autowired
	ProjectService projectService;
 
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "AliasSubProjName",
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
		
		SubProjectDetail subProjectDetail = (SubProjectDetail)target;
		
		String subAgreementPeriod = String.format ("%d", subProjectDetail.getSubAgreementPeriod());
		if (subAgreementPeriod != null) {  
			   pattern = Pattern.compile(ID_PATTERN);  
			   matcher = pattern.matcher(subAgreementPeriod);  
			   if (!matcher.matches()) {  
			    errors.rejectValue("agreementPeriod", "agreementPeriod.incorrect",  
			      "Enter a numeric value and it must be greater than 0.");
			   }
		}
		
		if(subProjectDetail.getAliasSubProjName().length() > 50){
	        errors.rejectValue("aliasSubProjName", "aliasSubProjName.incorrect","Field must not exceed 50 characters.");
	    } else {
			boolean isAliasSubProjectAlreadyExisting = projectService.isAliasSubProjectAlreadyExisting(subProjectDetail.getAliasSubProjName());
			if(isAliasSubProjectAlreadyExisting){
				errors.rejectValue("aliasSubProjName", "aliasSubProjName.incorrect","Alias Sub Project Name Already Found To Be Existing.");
			}
		}
		
		if(subProjectDetail.getSubAgreementNo().length() > 50){
	        errors.rejectValue("subAgreementNo","subAgreementNo.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubCerNo().length() > 30){
	        errors.rejectValue("subCerNo", "subCerNo.incorrect", "Field must not exceed 30 characters");
	    }
		if(subProjectDetail.getSubAmount().length() > 50){
	        errors.rejectValue("subAmount", "subAmount.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubContractorName().length() > 50){
	        errors.rejectValue("subContractorName","subContractorName.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubAgreementValue().length() > 50){
	        errors.rejectValue("subAgreementValue","subAgreementValue.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubTenderValue().length() > 50){
	        errors.rejectValue("subTenderValue","subTenderValue.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubContractValue().length() > 50){
	        errors.rejectValue("subContractValue", "subContractValue.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubExAmount().length() > 50){
	        errors.rejectValue("subExAmount","subExAmount.incorrect", "Field must not exceed 50 characters.");
	    }
		if(subProjectDetail.getSubExPercentage().length() > 30){
	        errors.rejectValue("subExPercentage","subExPercentage.incorrect", "Field must not exceed 30 characters.");
	    }	
		if(subAgreementPeriod.length() > 10){
	        errors.rejectValue("subAgreementPeriod","subAgreementPeriod.incorrect", "Field must not exceed 10 characters");
	    }
		
	}
	
	
}

