package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.ProjectService;

public class ProjDescDetailValidator extends BaseValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;
	
	@Autowired
	ProjectService projectService;

	@Autowired
	ProjectDescriptionService projectDescriptionService;

	private static final Logger LOGGER = Logger.getLogger(ProjDescDetailValidator.class);
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjDescDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasProjectName",
				"required.aliasProjectName", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "serialNumber",
				"required.serialNumber", "Please Select Serial Number.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workType",
			"required.workType", "Enter Work Type");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"required.description", "Enter Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasDescription",
				"required.aliasDescription", "Enter Alias Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInFig",
				"required.quantityInFig", "Enter Quantity In Figures.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInUnit",
				"required.quantityInUnit", "Enter Quantity In Unit.");

		ProjDescDetail projectDescDetail = (ProjDescDetail)target;
		
		if ("0".equalsIgnoreCase(projectDescDetail.getAliasProjectName())) {  
			errors.rejectValue("aliasProjectName", "aliasProjectName.incorrect","Please select a valid project");
		}
		
		if (projectDescDetail.isSubProjectDesc()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasSubProjectName",
					"required.aliasSubProjectName", "Please Select Alias Sub Project Name.");
			if ("0".equalsIgnoreCase(projectDescDetail.getAliasSubProjectName())) {
				errors.rejectValue("aliasSubProjectName",
						"aliasSubProjectName.incorrect",
						"Please select a valid sub project");
			}
		}
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getQuantityInFig())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getQuantityInFig());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("quantityInFig", "quantityInFig.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getQuantityInFig().length() > 15){
		            errors.rejectValue("quantityInFig", "quantityInFig.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getRateInFig())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getRateInFig());  
			   if (!matcher.matches()) {  
				  errors.rejectValue("rateInFig", "rateInFig.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getRateInFig().length() > 15){
		            errors.rejectValue("rateInFig", "rateInFig.incorrect", "Field must not exceed 15 characters.");
		        }
		}else{
			projectDescDetail.setRateInFig("0");
		}
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getProjDescAmount())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getProjDescAmount());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("projDescAmount", "projDescAmount.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getProjDescAmount().length() > 15){
		            errors.rejectValue("projDescAmount", "projDescAmount.incorrect", "Field must not exceed 15 characters.");
		        }
		}else{
			projectDescDetail.setProjDescAmount("0");
		}

		if(projectDescDetail.getAliasDescription().length() > 100){
            errors.rejectValue("aliasDescription","aliasDescription.incorrect", "Field Should Not Exceed 100 characters");
        }
		if(projectDescDetail.getRateInWords().length() > 50){
            errors.rejectValue("rateInWords", "rateInWords.incorrect", "Field Should Not Exceed 50 characters");
        }
		
		if(!"Y".equalsIgnoreCase(projectDescDetail.getIsUpdate())) {
			LOGGER.info("Project description detail is update: " + projectDescDetail.getIsUpdate());
			LOGGER.info(" project description detail Id "+ projectDescDetail.getProjId());
			boolean isAliasDescriptionAlreadyExisting = projectDescriptionService.isAliasDescriptionAlreadyExisting(projectDescDetail);
			if(isAliasDescriptionAlreadyExisting){
				errors.rejectValue("aliasDescription", "aliasDescription.incorrect","Alias Description Already Found To Be Existing.");
			}
		}

		if(!"Y".equalsIgnoreCase(projectDescDetail.getIsUpdate())) {
			LOGGER.info("Project description detail is update: "+projectDescDetail.getIsUpdate());
			LOGGER.info(" project description detail Id "+ projectDescDetail.getProjId());
			boolean isAliasDescriptionAlreadyExisting = projectDescriptionService.isSerialNumberAlreadyExisting(projectDescDetail);
			if(isAliasDescriptionAlreadyExisting){
				errors.rejectValue("serialNumber", "serialNumber.incorrect","Serial Number Already Found To Be Existing.");
			}
		}
		
	}
	
	
}

