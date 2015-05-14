package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.ProjectService;

public class ProjDescDetailValidator extends BaseValidator implements Validator{
	
	private Pattern pattern;  
	private Matcher matcher;
	
	@Autowired
	ProjectService projectService;
 
	@Override
	public boolean supports(Class<?> clazz) {
		return ProjDescDetail.class.isAssignableFrom(clazz);
	}
 
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasProjectName",
				"required.aliasProjectName", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasSubProjectName",
				"required.aliasSubProjectName", "Please Select Alias Sub Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workType",
			"required.workType", "Enter Work Type");		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInFig",
				"required.quantityInFig", "Enter Quantity in Numbers");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantityInWords",
				"required.quantityInWords", "Enter Quantity in Words.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
				"required.description", "Enter Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasDescription",
				"required.aliasDescription", "Enter Alias Description.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateInFig",
				"required.rateInFig", "Enter Rate in Numbers,");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "rateInWords",
				"required.rateInWords", "Enter Rate in Words.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projDescAmount",
				"required.projDescAmount", "Enter Project Amount In Numbers.");	
		
		ProjDescDetail projectDescDetail = (ProjDescDetail)target;
		
		if ("0".equalsIgnoreCase(projectDescDetail.getAliasProjectName())) {  
			errors.rejectValue("aliasProjectName", "aliasProjectName.incorrect","Please select a valid project");
		}
		
		if ("0".equalsIgnoreCase(projectDescDetail.getAliasSubProjectName())) {  
			errors.rejectValue("aliasSubProjectName", "aliasSubProjectName.incorrect","Please select a valid sub project");
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
		}
		
		if(projectDescDetail.getQuantityInWords().length() > 50){
            errors.rejectValue("quantityInWords","quantityInWords.incorrect", "Field Should Not Exceed 50 characters");
        }
		if(projectDescDetail.getAliasDescription().length() > 100){
            errors.rejectValue("aliasDescription","aliasDescription.incorrect", "Field Should Not Exceed 100 characters");
        } 
		else if(!"Y".equalsIgnoreCase(projectDescDetail.getIsUpdate())) {
			System.out.println("is update:"+projectDescDetail.getIsUpdate());
			System.out.println(projectDescDetail.getProjId());
			boolean isAliasDescriptionAlreadyExisting = projectService.isAliasDescriptionAlreadyExisting(projectDescDetail.getAliasProjectName(), projectDescDetail.getAliasSubProjectName(), projectDescDetail.getAliasDescription());
			if(isAliasDescriptionAlreadyExisting){
				errors.rejectValue("aliasDescription", "aliasDescription.incorrect","Alias Description Already Found To Be Existing.");
			}

		}
		if(projectDescDetail.getRateInWords().length() > 50){
            errors.rejectValue("rateInWords", "rateInWords.incorrect", "Field Should Not Exceed 50 characters");
        }		
		
	}
	
	
}

