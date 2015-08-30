package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.DepositDetail;

import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DepositDetailValidator extends BaseValidator implements Validator {

	private Pattern pattern;
	private Matcher matcher;

	@SuppressWarnings("unused")
	private static final Logger LOGGER = Logger.getLogger(DepositDetailValidator.class);

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return DepositDetail.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositType",
				"required.depositType", "Enter Deposit Type");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositStartDate",
				"required.depositStartDate", "Select Deposit Start Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositEndDate",
				"required.depositEndDate", "Select Deposit End Date.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositPeriod",
				"required.depositPeriod", "Enter Deposit Period,");
		ValidationUtils.rejectIfEmpty(errors, "depositAmount",
				"required.depositAmount", "Enter Deposit Amount");
		ValidationUtils.rejectIfEmpty(errors, "depositLedgerNumber",
				"required.depositLedgerNumber", "Enter Deposit Ledger Number");

		DepositDetail depositDetail = (DepositDetail) target;

        if("Bank Guarantee".equalsIgnoreCase(depositDetail.getDepositType())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositExtensionDate",
                    "required.depositExtensionDate", "Select Deposit Extension Date.");
        }
        if("Completed".equalsIgnoreCase(depositDetail.getDepositStatus())){
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositRecievedDate",
                    "required.depositRecievedDate", "Select Deposit Recieved Date.");
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositRecievedComments",
                    "required.depositRecievedComments", "Select Enter Deposit recieved comments.");
        }

		if (0 == depositDetail.getDepositId()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"aliasProjectName", "required.aliasProjectName",
					"Please Select Project Name.");
		}

		if ("0".equalsIgnoreCase(depositDetail.getAliasProjectName())
				&& depositDetail.getDepositId() == 0) {
			errors.rejectValue("aliasProjectName", "required.aliasProjectName",
					"Please Select Alias Project Name");
		}

		if (depositDetail.isSubProjectDepositDetail() && depositDetail.getDepositId() == 0) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors,
					"aliasSubProjectName", "required.aliasSubProjectName",
					"Please Select Alias Sub ProjectName");
			if ("0".equals(depositDetail.getAliasSubProjectName())) {
				errors.rejectValue("aliasSubProjectName",
						"required.aliasSubProjectName",
						"Please Select Alias Sub ProjectName");
			}
		}

		if (!StringUtils.isNullOrEmpty(depositDetail.getDepositAmount())) {
			pattern = Pattern.compile(AMOUNT_PATTERN);
			matcher = pattern.matcher(depositDetail.getDepositAmount());
			if (!matcher.matches()) {
				errors.rejectValue("depositAmount", "depositAmount.incorrect",
						"Enter a numeric value and only a single dot is allowed");
			} else if (depositDetail.getDepositAmount().length() > 15) {
				errors.rejectValue("depositAmount", "depositAmount.incorrect",
						"Field must not exceed 15 characters.");
			}
		}

		if ("competitor".equalsIgnoreCase(depositDetail.getDepositFor())) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "depositDetailSubmitter",
					"required.depositDetailSubmitter", "Please enter Competitor Name.");
		}
	}
}
