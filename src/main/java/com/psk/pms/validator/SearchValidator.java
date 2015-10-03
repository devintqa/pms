package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.SearchDetail;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SearchValidator extends BaseValidator implements Validator {

    public static final String GLOBAL = "Global";

    @Override
    public boolean supports(Class<?> clazz) {
        return SearchDetail.class.isAssignableFrom(clazz);
    }

    public void indentValidator(Object target, Errors errors) {

        SearchDetail searchDetail = (SearchDetail) target;
            if (StringUtils.isNullOrEmpty(searchDetail.getAliasProjectName())) {
                errors.rejectValue("aliasProjectName", "required.aliasProjectName", "Please select Alias Project Name.");
            }else{
            	String projId = fetchProjectId(searchDetail.getAliasProjectName(),searchDetail.getEmployeeId());
                if (projId == null) {
                    errors.rejectValue("aliasProjectName",
                            "invalid.aliasProjectName",
                            "Please select valid Alias Project Name.");
                } else {
                    searchDetail.setProjId(Integer.valueOf(projId));
                }
            }
        
    }
    
    @Override
    public void validate(Object target, Errors errors) {

        SearchDetail searchDetail = (SearchDetail) target;
        if (!GLOBAL.equalsIgnoreCase(searchDetail.getSearchUnder())) {
            if ((searchDetail.isEditSubProject())
                    && StringUtils
                    .isNullOrEmpty(searchDetail.getAliasProjectName())) {
                errors.rejectValue("aliasProjectName", "required.aliasProjectName",
                        "Please select Alias Project Name.");
            }

            if ((searchDetail.isEditSubProject())
                    && !StringUtils.isNullOrEmpty(searchDetail
                    .getAliasProjectName())) {
                String projId = fetchProjectId(searchDetail.getAliasProjectName(),searchDetail.getEmployeeId());
                if (projId == null) {
                    errors.rejectValue("aliasProjectName",
                            "invalid.aliasProjectName",
                            "Please select valid Alias Project Name.");
                } else {
                    searchDetail.setProjId(Integer.valueOf(projId));
                }
            }

            if (!StringUtils.isNullOrEmpty(searchDetail
                    .getAliasProjectName())) {
                String projId;
                if ("project".equalsIgnoreCase(searchDetail.getSearchUnder())) {
                    projId = fetchProjectId(searchDetail.getAliasProjectName(), searchDetail.getEmployeeId());
                } else {
                    projId = fetchSubProjectId(searchDetail.getAliasProjectName(),searchDetail.getEmployeeId());
                }

                if (projId == null) {
                    errors.rejectValue("aliasProjectName",
                            "invalid.aliasProjectName",
                            "Please select valid Alias Project/ Sub Project Name.");
                } else {
                    searchDetail.setProjId(Integer.valueOf(projId));
                }
            }
        }
    }
}
