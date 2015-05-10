package com.psk.pms.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

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
		
		String rateInFig = String.format ("%d", projectDescDetail.getRateInFig());
		if (rateInFig != null) {  
			   pattern = Pattern.compile(ID_PATTERN);  
			   matcher = pattern.matcher(rateInFig);  
			   if (!matcher.matches()) {  
			    errors.rejectValue("rateInFig", "rateInFig.incorrect",  
			      "Enter a numeric value and it must be greater than 0.");  
			   }
		} 
		
		String quantityInFig = String.format ("%d", projectDescDetail.getQuantityInFig());
		if (quantityInFig != null) {  
			   pattern = Pattern.compile(ID_PATTERN);  
			   matcher = pattern.matcher(quantityInFig);  
			   if (!matcher.matches()) {  
			    errors.rejectValue("quantityInFig", "quantityInFig.incorrect",  
			      "Enter a numeric value and it must be greater than 0.");  
			   }
		} 
		
		String projDescAmount = String.format ("%d", projectDescDetail.getProjDescAmount());
		if (projDescAmount != null) {  
			   pattern = Pattern.compile(ID_PATTERN);  
			   matcher = pattern.matcher(projDescAmount);  
			   if (!matcher.matches()) {  
			    errors.rejectValue("projDescAmount", "projDescAmount.incorrect",  
			      "Enter a numeric value and it must be greater than 0.");  
			   }
		}
		
		if(quantityInFig.length() > 30){
            errors.rejectValue("quantityInFig","quantityInFig.incorrect", "Field Should Not Exceed 30 numeric");
        }
		if(projectDescDetail.getQuantityInWords().length() > 50){
            errors.rejectValue("quantityInWords","quantityInWords.incorrect", "Field Should Not Exceed 50 characters");
        }
		if(projectDescDetail.getAliasDescription().length() > 100){
            errors.rejectValue("aliasDescription","aliasDescription.incorrect", "Field Should Not Exceed 100 characters");
        } 
		else if(!"Y".equalsIgnoreCase(projectDescDetail.getIsUpdate())) {
			boolean isAliasDescriptionAlreadyExisting = projectService.isAliasDescriptionAlreadyExisting(projectDescDetail.getProjId(), projectDescDetail.getSubProjId(), projectDescDetail.getAliasDescription());
			if(isAliasDescriptionAlreadyExisting){
				errors.rejectValue("aliasDescription", "aliasDescription.incorrect","Alias Description Already Found To Be Existing.");
			}
		}
		if(rateInFig.length() > 30){
            errors.rejectValue("rateInFig", "rateInFig.incorrect", "Field Should Not Exceed 30 numeric");
        }
		if(projectDescDetail.getRateInWords().length() > 50){
            errors.rejectValue("rateInWords", "rateInWords.incorrect", "Field Should Not Exceed 50 characters");
        }
		if(projDescAmount.length() > 50){
            errors.rejectValue("projDescAmount","projDescAmount.incorrect", "Field Should Not Exceed 50 numeric");
        }		
		
	}
	
	
}

