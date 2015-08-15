package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ViewDetail;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ViewValidator extends BaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return ViewDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ViewDetail viewDetail = (ViewDetail) target;
		
		if(StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())){
            errors.rejectValue("aliasProjectName", "required.aliasProjectName","Please select Alias Project Name.");
        }

        if(!(viewDetail.isProjectItemDescription() || viewDetail.isSearchAggregateItemDetails()
                ||viewDetail.isSearchComparisonData() || viewDetail.isViewProjectItemPrice())){
            errors.rejectValue("viewProjectItemPrice", "required.viewProjectItemPrice","Please select a valid view option");
        }

		if (viewDetail.isViewProjectItemPrice() && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(viewDetail.getSearchUnder())) {
				projId = fetchProjectId(viewDetail.getAliasProjectName());
				viewDetail.setProjId(Integer.valueOf(projId));
				viewDetail.setSubProjId(0);
			} else {
				viewDetail.setEditSubProject(true);
				projId = fetchSubProjectId(viewDetail.getAliasProjectName());
				viewDetail.setSubProjId(Integer.valueOf(projId));
			}
			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			}
		}
		
		if (viewDetail.isSearchAggregateItemDetails() && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(viewDetail.getSearchUnder())) {
				projId = fetchProjectId(viewDetail.getAliasProjectName());
				viewDetail.setProjId(Integer.valueOf(projId));
				viewDetail.setSubProjId(0);
			} else {
				viewDetail.setEditSubProject(true);
				projId = fetchSubProjectId(viewDetail.getAliasProjectName());
				viewDetail.setSubProjId(Integer.valueOf(projId));
			}
			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			}
		}
		
		if (viewDetail.isProjectItemDescription() && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(viewDetail.getSearchUnder())) {
				projId = fetchProjectId(viewDetail.getAliasProjectName());
				viewDetail.setProjId(Integer.valueOf(projId));
				viewDetail.setSubProjId(0);
			} else {
				viewDetail.setEditSubProject(true);
				projId = fetchSubProjectId(viewDetail.getAliasProjectName());
				viewDetail.setSubProjId(Integer.valueOf(projId));
			}
			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			}
		}
		
		
        /*if((viewDetail.isViewProjectItemPrice() || viewDetail.isEditSubProject()) && StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())){
            errors.rejectValue("aliasProjectName", "required.aliasProjectName","Please select Alias Project Name.");
        }
        	
        if((viewDetail.isEditSubProject()) && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())){
        	String projId = fetchProjectId(viewDetail.getAliasProjectName());
        	if(projId == null){
        		errors.rejectValue("aliasProjectName", "invalid.aliasProjectName","Please select valid Alias Project Name.");
        	}else{
        		viewDetail.setProjId(Integer.valueOf(projId));
        	}
        }
        
        if(viewDetail.isSearchAggregateItemDetails() || viewDetail.isSearchComparisonData() || viewDetail.isProjectItemDescription()){
        	String projId = fetchProjectId(viewDetail.getAliasProjectName());
        	if(projId == null){
        		errors.rejectValue("aliasProjectName", "invalid.aliasProjectName","Please select valid Alias Project Name.");
        	}else{
        		viewDetail.setProjId(Integer.valueOf(projId));
        	}
        }*/
	}

}

