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
            errors.rejectValue("showSearchItemDesc", "required.showSearchItemDesc","Please select a valid view option");
        }

        if(viewDetail.isProjectItemDescription() && viewDetail.getItemType().equalsIgnoreCase("--Please Select--")){
            errors.rejectValue("projectItemDescription", "required.projectItemDescription","Please select a valid Type");
        }

		if ((viewDetail.isViewProjectItemPrice() || viewDetail.isSearchAggregateItemDetails()
				 || viewDetail.isProjectItemDescription() || viewDetail.isSearchComparisonData()) && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(viewDetail.getSearchUnder())) {
				projId = fetchProjectId(viewDetail.getAliasProjectName());
				if(projId != null){
					viewDetail.setProjId(Integer.valueOf(projId));
					viewDetail.setSubProjId(0);
				}
			} else {			
				projId = fetchSubProjectId(viewDetail.getAliasProjectName());			
				if(projId != null){
					viewDetail.setEditSubProject(true);
					viewDetail.setSubProjId(Integer.valueOf(projId));
				}
			}
			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			}
		}
	}

}

