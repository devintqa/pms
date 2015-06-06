package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.EMDDetail;
import org.apache.log4j.Logger;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmdValidator extends BaseValidator implements Validator {

    private Pattern pattern;
    private Matcher matcher;

    private static final Logger LOGGER = Logger.getLogger(EmdValidator.class);

    @Override
    public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
        return EMDDetail.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasProjectName",
                "required.aliasProjectName", "Please Select Project Name.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdType",
                "required.emdType", "Enter Emd Type");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdStartDate",
                "required.emdStartDate", "Select EMD Start Date.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdEndDate",
                "required.emdEndDate", "Select EMD End Date.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdPeriod",
                "required.emdPeriod", "Enter EMD Period,");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "emdExtensionDate",
                "required.emdExtensionDate", "Select EMD Extension Date,");
        ValidationUtils.rejectIfEmpty(errors, "emdAmount",
                "required.emdAmount", "Enter EMD Amount");
        ValidationUtils.rejectIfEmpty(errors,"emdLedgerNumber",
                "required.emdLedgerNumber","Enter EMD Ledger Number");

        EMDDetail emdDetail = (EMDDetail) target;

        if("0".equalsIgnoreCase(emdDetail.getAliasProjectName()))
        {
            errors.rejectValue("aliasProjectName","required.aliasProjectName", "Please Select Alias ProjectName");
        }

        if(emdDetail.isSubProjectEMD())
        {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasSubProjectName",
                    "required.aliasSubProjectName", "Please Select Alias Sub ProjectName");
            if("0".equals(emdDetail.getAliasSubProjectName()))
            {
                errors.rejectValue("aliasSubProjectName","required.aliasSubProjectName", "Please Select Alias Sub ProjectName");
            }
        }

        if (!StringUtils.isNullOrEmpty(emdDetail.getEmdAmount())) {
            pattern = Pattern.compile(AMOUNT_PATTERN);
            matcher = pattern.matcher(emdDetail.getEmdAmount());
            if (!matcher.matches()) {
                errors.rejectValue("emdAmount", "emdAmount.incorrect",
                        "Enter a numeric value and only a single dot is allowed");
            }else if(emdDetail.getEmdAmount().length() > 15){
                errors.rejectValue("emdAmount", "emdAmount.incorrect", "Field must not exceed 15 characters.");
            }
        }

        if(emdDetail.getEmdSubmitter()=="competitor")
        {
            ValidationUtils.rejectIfEmptyOrWhitespace(errors, "competitorName",
                    "required.competitorName", "Please enter Competitor Name,");
        }
    }
}
