package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.ProjectService;

public class ProjectDetailValidator extends BaseValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;
	
	@Autowired
	ProjectService projectService;
 
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount",
				"required.amount", "Enter Amount.");
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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdAmount",
				"required.emdAmount", "Enter EMD Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdStartDate",
				"required.emdStartDate", "Enter EMD Start Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdEndDate",
				"required.emdEndDate", "Enter EMD End Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderDate",
				"required.tenderDate", "Select Tender Date.");
		
		ProjectDetail projectDetail = (ProjectDetail)target;
		
		String agreementPeriod = String.format ("%d", projectDetail.getAgreementPeriod());
		if (agreementPeriod != null) {  
			   pattern = Pattern.compile(ID_PATTERN);  
			   matcher = pattern.matcher(agreementPeriod);  
			   if (!matcher.matches()) {  
			    errors.rejectValue("agreementPeriod", "agreementPeriod.incorrect",  
			      "Enter a numeric value and it must be greater than 0.");
			   }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getEmdAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getEmdAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("emdAmount", "emdAmount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getEmdAmount().length() > 15){
		            errors.rejectValue("emdAmount", "emdAmount.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("amount", "amount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getAmount().length() > 15){
		            errors.rejectValue("amount", "amount.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getAgreementValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getAgreementValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("agreementValue", "agreementValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getAgreementValue().length() > 15){
		            errors.rejectValue("agreementValue", "agreementValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getTenderValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getTenderValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("tenderValue", "tenderValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getTenderValue().length() > 15){
		            errors.rejectValue("tenderValue", "tenderValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getContractValue())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getContractValue());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("contractValue", "contractValue.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getContractValue().length() > 15){
		            errors.rejectValue("contractValue", "contractValue.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getExAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getExAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("exAmount", "exAmount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getExAmount().length() > 15){
		            errors.rejectValue("exAmount", "exAmount.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDetail.getExPercentage())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDetail.getExPercentage());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("exPercentage", "exPercentage.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDetail.getExPercentage().length() > 15){
		            errors.rejectValue("exPercentage", "exPercentage.incorrect", "Field must not exceed 15 characters.");
		        }
		}		
		
		if(projectDetail.getAliasName().length() > 50){
            errors.rejectValue("aliasName", "aliasName.incorrect","Field must not exceed 50 characters.");
        } else if(!"Y".equalsIgnoreCase(projectDetail.getIsUpdate())) {
			boolean isAliasProjectAlreadyExisting = projectService.isAliasProjectAlreadyExisting(projectDetail.getAliasName());
			if(isAliasProjectAlreadyExisting){
				errors.rejectValue("aliasName", "aliasName.incorrect","Alias Project Name Already Found To Be Existing.");
			}
		}
		if(projectDetail.getAgreementNo().length() > 50){
            errors.rejectValue("agreementNo","agreementNo.incorrect", "Field must not exceed 50 characters.");
        }
		if(projectDetail.getCerNo().length() > 30){
            errors.rejectValue("cerNo", "cerNo.incorrect", "Field must not exceed 30 characters");
        }
		if(projectDetail.getContractorName().length() > 50){
            errors.rejectValue("contractorName","contractorName.incorrect", "Field must not exceed 50 characters.");
        }
		if(agreementPeriod.length() > 10){
            errors.rejectValue("agreementPeriod","agreementPeriod.incorrect", "Field must not exceed 10 characters");
        }
		
	}
	
	
}

