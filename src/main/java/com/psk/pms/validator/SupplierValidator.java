package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.Supplier;
import com.psk.pms.service.PurchaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;

public class SupplierValidator extends BaseValidator implements Validator {

    @Autowired
    PurchaseService purchaseService;

    private static final Logger LOGGER = Logger.getLogger(SupplierValidator.class);

    @Override
    public boolean supports(Class<?> aClass) {
        return Supplier.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Pattern pattern;
        Matcher matcher;
        Supplier supplier = (Supplier) o;

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasName",
                "required.aliasName", "Enter Supplier Alias Name.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber",
                "required.phoneNumber", "Enter Supplier Phone Number.");

        if (isNullOrEmpty(supplier.getTinNumber()) && isNullOrEmpty(supplier.getReason())) {
            errors.rejectValue("tinNumber", "tinNumber.incorrect", "Enter Tin number or Reason for not entering the Tin Number");
        }

        if (!"Y".equalsIgnoreCase(supplier.getIsUpdate())) {
            LOGGER.info("Validating  supplier Alias Name " + supplier.getAliasName());
            boolean isAliasDescriptionAlreadyExisting = purchaseService.isAliasSupplierNameAlreadyExist(supplier.getAliasName());
            if (isAliasDescriptionAlreadyExisting) {
                errors.rejectValue("aliasName", "aliasName.incorrect", "Alias Name Already Found To Be Existing.");
            }
        }

        if (!isNullOrEmpty(supplier.getPhoneNumber())) {
            pattern = Pattern.compile(MOBILE_PATTERN);
            matcher = pattern.matcher(supplier.getPhoneNumber());
            if (!matcher.matches()) {
                errors.rejectValue("phoneNumber",
                        "phoneNumber.incorrect",
                        "Enter a correct phone number.");
            }
        }

        if (!isNullOrEmpty(supplier.getEmailAddress())) {
            pattern = Pattern.compile(EMAIL_PATTERN);
            matcher = pattern.matcher(supplier.getEmailAddress());
            if (!matcher.matches()) {
                errors.rejectValue("emailAddress",
                        "emailAddress.incorrect",
                        "Enter a correct email Id.");
            }
        }
    }
}
