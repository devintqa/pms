package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.SearchDetail;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class SearchValidator extends BaseValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		return SearchDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		SearchDetail searchDetail = (SearchDetail) target;

		if ((searchDetail.isSearchProjectDescription() || searchDetail
				.isEditSubProject())
				&& StringUtils
						.isNullOrEmpty(searchDetail.getAliasProjectName())) {
			errors.rejectValue("aliasProjectName", "required.aliasProjectName",
					"Please select Alias Project Name.");
		}

		if ((searchDetail.isEditSubProject())
				&& !StringUtils.isNullOrEmpty(searchDetail
						.getAliasProjectName())) {
			String projId = fetchProjectId(searchDetail.getAliasProjectName());
			if (projId == null) {
				errors.rejectValue("aliasProjectName",
						"invalid.aliasProjectName",
						"Please select valid Alias Project Name.");
			} else {
				searchDetail.setProjId(Integer.valueOf(projId));
			}
		}

		if (searchDetail.isSearchProjectDescription()
				&& !StringUtils.isNullOrEmpty(searchDetail
						.getAliasProjectName())) {
			String projId;
			if ("project".equalsIgnoreCase(searchDetail.getSearchUnder())) {
				projId = fetchProjectId(searchDetail.getAliasProjectName());
			} else {
				projId = fetchSubProjectId(searchDetail.getAliasProjectName());
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
