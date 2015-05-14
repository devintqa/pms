package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;
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
				"required.subAgreementNo", "Enter Agreement Number.");
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
		
		SubProjectDetail subProjectDetail = (SubProjectDetail)target;
		
		if (subProjectDetail.getProjId() == 0) {  
				errors.rejectValue("projId", "projId.incorrect","Please select a valid project");
		}
		
		if(subProjectDetail.getAliasSubProjName().length() > 50){
	        errors.rejectValue("aliasSubProjName", "aliasSubProjName.incorrect","Field must not exceed 50 characters.");
	    } else if(!"Y".equalsIgnoreCase(subProjectDetail.getIsUpdate())) {
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
		if(subProjectDetail.getSubContractorName().length() > 50){
	        errors.rejectValue("subContractorName","subContractorName.incorrect", "Field must not exceed 50 characters.");
	    }
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subAmount", "subAmount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubAmount().length() > 15){
		            errors.rejectValue("subAmount", "subAmount.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubAgreementValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubAgreementValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subAgreementValue", "subAgreementValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubAgreementValue().length() > 15){
		            errors.rejectValue("subAgreementValue", "subAgreementValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubTenderValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubTenderValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subTenderValue", "subTenderValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubTenderValue().length() > 15){
		            errors.rejectValue("subTenderValue", "subTenderValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubContractValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubContractValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subContractValue", "subContractValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubContractValue().length() > 15){
		            errors.rejectValue("subContractValue", "subContractValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubExAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubExAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subExAmount", "subExAmount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubExAmount().length() > 15){
		            errors.rejectValue("subExAmount", "subExAmount.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubExPercentage())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(subProjectDetail.getSubExPercentage());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("subExPercentage", "subExPercentage.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(subProjectDetail.getSubExPercentage().length() > 15){
		            errors.rejectValue("subExPercentage", "subExPercentage.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
	}
	
	
}

