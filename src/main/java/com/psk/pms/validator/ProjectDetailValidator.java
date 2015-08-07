package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjectDetail;
import com.psk.pms.service.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectDetailValidator extends BaseValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;
	
	@Autowired
	ProjectService projectService;

	private static final Logger LOGGER = Logger.getLogger(ProjectDetailValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ProjectDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("Project Validation Start");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projectName",
                "required.projectName", "Enter Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasName",
			"required.aliasName", "Enter Alias Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "amount",
				"required.amount", "Enter Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractorName",
				"required.contractorName", "Enter Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasContractorName",
				"required.aliasContractorName", "Enter Alias Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "contractorAddress",
				"required.contractorAddress", "Enter Contractor Address.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "agreementValue",
				"required.agreementValue", "Enter Agreement Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderValue",
				"required.tenderValue", "Enter Tender Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exAmount",
				"required.exAmount", "Enter Expected Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tenderDate",
				"required.tenderDate", "Select Tender Date.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "addSecurityDeposit",
                "required.addSecurityDeposit", "Enter Additional Security Deposit Amount.");
		
		ProjectDetail projectDetail = (ProjectDetail)target;
		
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
		if (!StringUtils.isNullOrEmpty(projectDetail.getLessPercentage())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(projectDetail.getLessPercentage());
			if (!matcher.matches()) {
				errors.rejectValue("lessPercentage", "lessPercentage.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			}else if(projectDetail.getLessPercentage().length() > 15){
				errors.rejectValue("lessPercentage", "lessPercentage.incorrect", "Field must not exceed 15 characters.");
			}
		}
        if (!StringUtils.isNullOrEmpty(projectDetail.getAddSecurityDeposit())) {
            pattern = Pattern.compile(AMOUNT_PATTERN);
            matcher = pattern.matcher(projectDetail.getAddSecurityDeposit());
            if (!matcher.matches()) {
                errors.rejectValue("addSecurityDeposit", "addSecurityDeposit.incorrect",
                        "Enter a numeric value and only a single dot is allowed");
            }else if(projectDetail.getExAmount().length() > 15){
                errors.rejectValue("addSecurityDeposit", "addSecurityDeposit.incorrect", "Field must not exceed 15 characters.");
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

		if (StringUtils.isNullOrEmpty(projectDetail.getLessPercentage())) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "exPercentage",
				"required.exPercentage", "Enter one among Excess or Less in Percentage.");
		}

		if (!StringUtils.isNullOrEmpty(projectDetail.getExPercentage()) && !StringUtils.isNullOrEmpty(projectDetail.getLessPercentage())) {
			errors.rejectValue("exPercentage", "exPercentage.incorrect", "Only one among Excess or Less in Percentage can be entered.");
		}

		if (!StringUtils.isNullOrEmpty(projectDetail.getPerformanceGuarantee())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(projectDetail.getPerformanceGuarantee());
			if (!matcher.matches()) {
				errors.rejectValue("performanceGuarantee", "performanceGuarantee.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			}else if(projectDetail.getPerformanceGuarantee().length() > 15){
				errors.rejectValue("performanceGuarantee", "performanceGuarantee.incorrect", "Field must not exceed 15 characters.");
			}
		}
	}

}

