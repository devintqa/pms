package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.ViewDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

public class ViewValidator extends BaseValidator implements Validator{
	
	@Autowired
	ProjectService projectService;

	@Autowired
	SubProjectService subProjectService;

	private static final Logger LOGGER = Logger.getLogger(ViewValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return ViewDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		ViewDetail viewDetail = (ViewDetail)target;

        if((viewDetail.isViewProjectItemPrice() || viewDetail.isEditSubProject()) && StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())){
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
        
        if(viewDetail.isSearchAggregateItemDetails() || viewDetail.isSearchComparisonData()){
        	String projId = fetchProjectId(viewDetail.getAliasProjectName());
        	if(projId == null){
        		errors.rejectValue("aliasProjectName", "invalid.aliasProjectName","Please select valid Alias Project Name.");
        	}else{
        		viewDetail.setProjId(Integer.valueOf(projId));
        	}
        }

		if (viewDetail.isViewProjectItemPrice() && !StringUtils.isNullOrEmpty(viewDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(viewDetail.getSearchUnder())) {
				projId = fetchProjectId(viewDetail.getAliasProjectName());
			} else {
				projId = fetchSubProjectId(viewDetail.getAliasProjectName());
			}

			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			} else {
				viewDetail.setProjId(Integer.valueOf(projId));
			}
		}
	}
	
	private String fetchProjectId(String aliasProjectName) {
		LOGGER.info("method = fetchProjectId()");
		Map<String, String> aliasProjectList = populateAliasProjectList();
		for (Map.Entry<String, String> entry : aliasProjectList.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(aliasProjectName)) {
				return entry.getKey();
			}
		}
		return null;
	}

	private String fetchSubProjectId(String aliasSubProjName) {
		LOGGER.info("method = fetchSubProjectId()");
		Map<String, String> aliasSubProjectList = populateAliasSubProjectList();
		for (Map.Entry<String, String> entry : aliasSubProjectList.entrySet()) {
			if (entry.getValue().equalsIgnoreCase(aliasSubProjName)) {
				return entry.getKey();
			}
		}
		return null;
	}
	
	public Map<String, String> populateAliasProjectList() {
		Map<String, String> aliasProjectName = projectService.getAliasProjectNames();
		return aliasProjectName;
	}
	public Map<String, String> populateAliasSubProjectList() {
		Map<String, String> subAliasProjectNames = subProjectService.getSubAliasProjectNames("");
		return subAliasProjectNames;
	}

}

