package com.psk.pms.validator;
import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.SearchDetail;
import com.psk.pms.service.ProjectService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Map;

public class SearchValidator extends BaseValidator implements Validator{
	
	@Autowired
	ProjectService projectService;
	
	private static final Logger LOGGER = Logger.getLogger(SearchValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		
		SearchDetail searchDetail = (SearchDetail)target;

        if((searchDetail.isSearchProjectDescription() || searchDetail.isEditSubProject()) && StringUtils.isNullOrEmpty(searchDetail.getAliasProjectName())){
            errors.rejectValue("aliasProjectName", "required.aliasProjectName","Please select Alias Project Name.");
        }
        	
        if((searchDetail.isEditSubProject()) && !StringUtils.isNullOrEmpty(searchDetail.getAliasProjectName())){
        	String projId = fetchProjectId(searchDetail.getAliasProjectName());
        	if(projId == null){
        		errors.rejectValue("aliasProjectName", "invalid.aliasProjectName","Please select valid Alias Project Name.");
        	}else{
        		searchDetail.setProjId(Integer.valueOf(projId));
        	}
        }

		if (searchDetail.isSearchProjectDescription() && !StringUtils.isNullOrEmpty(searchDetail.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(searchDetail.getSearchUnder())) {
				projId = fetchProjectId(searchDetail.getAliasProjectName());
			} else {
				projId = fetchSubProjectId(searchDetail.getAliasProjectName());
			}

			if (projId == null) {
				errors.rejectValue("aliasProjectName", "invalid.aliasProjectName", "Please select valid Alias Project/ Sub Project Name.");
			} else {
				searchDetail.setProjId(Integer.valueOf(projId));
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
		Map<String, String> subAliasProjectNames = projectService.getSubAliasProjectNames("");
		return subAliasProjectNames;
	}

}

