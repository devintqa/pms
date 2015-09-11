package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.SubProjectDetail;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.SubProjectService;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SubProjectDetailValidator extends BaseValidator implements
		Validator {

	private Pattern pattern;
	private Matcher matcher;

	@Autowired
	ProjectService projectService;

	@Autowired
	SubProjectService subProjectService;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger
			.getLogger(SubProjectDetailValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return SubProjectDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "projId",
				"required.projId", "Please Select Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subProjectName",
				"required.subProjectName", "Enter Sub Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "AliasSubProjName",
				"required.subAliasName", "Enter Alias Sub Project Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAmount",
				"required.subAmount", "Enter Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subContractorName",
				"required.subContractorName", "Enter Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"subAliasContractorName", "required.subAliasContractorName",
				"Enter alias Contractor Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors,
				"subContractorAddress", "required.subContractorAddress",
				"Enter Contractor Address.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subAgreementValue",
				"required.subAgreementValue", "Enter Agreement Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subTenderValue",
				"required.subTenderValue", "Enter Tender Value.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subExAmount",
				"required.subExAmount", "Enter Expected Amount.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "subTenderDate",
				"required.subTenderDate", "Select Tender Date.");
		SubProjectDetail subProjectDetail = (SubProjectDetail) target;

		if (subProjectDetail.getProjId() == 0) {
			errors.rejectValue("projId", "projId.incorrect",
					"Please select a valid project");
		}

		if (subProjectDetail.getAliasSubProjName().length() > 50) {
			errors.rejectValue("aliasSubProjName",
					"aliasSubProjName.incorrect",
					"Field must not exceed 50 characters.");
		} else if (!"Y".equalsIgnoreCase(subProjectDetail.getIsUpdate())) {
			boolean isAliasSubProjectAlreadyExisting = subProjectService
					.isAliasSubProjectAlreadyExisting(
							subProjectDetail.getAliasSubProjName(),
							subProjectDetail.getProjId());
			if (isAliasSubProjectAlreadyExisting) {
				errors.rejectValue("aliasSubProjName",
						"aliasSubProjName.incorrect",
						"Alias Sub Project Name Already Found To Be Existing.");
			}
		}

		if (subProjectDetail.getSubAgreementNo().length() > 50) {
			errors.rejectValue("subAgreementNo", "subAgreementNo.incorrect",
					"Field must not exceed 50 characters.");
		}
		if (subProjectDetail.getSubCerNo().length() > 30) {
			errors.rejectValue("subCerNo", "subCerNo.incorrect",
					"Field must not exceed 30 characters");
		}
		if (subProjectDetail.getSubContractorName().length() > 50) {
			errors.rejectValue("subContractorName",
					"subContractorName.incorrect",
					"Field must not exceed 50 characters.");
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubAmount())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubAmount());
			if (!matcher.matches()) {
				errors.rejectValue("subAmount", "subAmount.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubAmount().length() > 15) {
				errors.rejectValue("subAmount", "subAmount.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubAgreementValue())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubAgreementValue());
			if (!matcher.matches()) {
				errors.rejectValue("subAgreementValue",
						"subAgreementValue.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubAgreementValue().length() > 15) {
				errors.rejectValue("subAgreementValue",
						"subAgreementValue.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubTenderValue())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubTenderValue());
			if (!matcher.matches()) {
				errors.rejectValue("subTenderValue",
						"subTenderValue.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubTenderValue().length() > 15) {
				errors.rejectValue("subTenderValue",
						"subTenderValue.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubExAmount())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubExAmount());
			if (!matcher.matches()) {
				errors.rejectValue("subExAmount", "subExAmount.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubExAmount().length() > 15) {
				errors.rejectValue("subExAmount", "subExAmount.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubExPercentage())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubExPercentage());
			if (!matcher.matches()) {
				errors.rejectValue("subExPercentage",
						"subExPercentage.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubExPercentage().length() > 15) {
				errors.rejectValue("subExPercentage",
						"subExPercentage.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubLessPercentage())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(subProjectDetail.getSubLessPercentage());
			if (!matcher.matches()) {
				errors.rejectValue("subLessPercentage",
						"subLessPercentage.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (subProjectDetail.getSubLessPercentage().length() > 15) {
				errors.rejectValue("subLessPercentage",
						"subLessPercentage.incorrect",
						"Field must not exceed 15 characters.");
			}
		}


		if (StringUtils.isNullOrEmpty(subProjectDetail.getSubLessPercentage())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"subExPercentage", "required.subExPercentage",
					"Enter one among Expected or Less in Percentage.");
		}

		if (!StringUtils.isNullOrEmpty(subProjectDetail.getSubLessPercentage())
				&& !StringUtils.isNullOrEmpty(subProjectDetail
						.getSubExPercentage())) {
			errors.rejectValue("subExPercentage", "subExPercentage.incorrect",
					"Only one among Excess or Less in Percentage can be entered.");
		}

	}
}
