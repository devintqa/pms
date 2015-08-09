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
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity",
				"required.quantity", "Enter Quantity In Figures.");
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metric",
				"required.metric", "Enter Quantity Metric Unit.");

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
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getQuantity())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getQuantity());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("quantity", "quantity.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getQuantity().length() > 15){
		            errors.rejectValue("quantity", "quantity.incorrect", "Field must not exceed 15 characters.");
		        }
		}
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getPricePerQuantity())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getPricePerQuantity());  
			   if (!matcher.matches()) {  
				  errors.rejectValue("pricePerQuantity", "pricePerQuantity.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getPricePerQuantity().length() > 15){
		            errors.rejectValue("pricePerQuantity", "pricePerQuantity.incorrect", "Field must not exceed 15 characters.");
		        }
		}else{
			projectDescDetail.setPricePerQuantity("0");
		}
		
		if (!StringUtils.isNullOrEmpty(projectDescDetail.getTotalCost())) {  
			   pattern = Pattern.compile(AMOUNT_PATTERN);  
			   matcher = pattern.matcher(projectDescDetail.getTotalCost());  
			   if (!matcher.matches()) {  
			    errors.rejectValue("totalCost", "totalCost.incorrect",  
			      "Enter a numeric value and only a single dot is allowed");
			   }else if(projectDescDetail.getTotalCost().length() > 15){
		            errors.rejectValue("totalCost", "totalCost.incorrect", "Field must not exceed 15 characters.");
		        }
		}else{
			projectDescDetail.setTotalCost("0");
		}

		if(projectDescDetail.getAliasDescription().length() > 100){
            errors.rejectValue("aliasDescription","aliasDescription.incorrect", "Field Should Not Exceed 100 characters");
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

