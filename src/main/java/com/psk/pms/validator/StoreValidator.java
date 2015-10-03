package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.StoreDetail;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Sony on 26-09-2015.
 */
public class StoreValidator extends BaseValidator implements Validator {


    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> aClass) {
        return StoreDetail.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        StoreDetail storeDetail = (StoreDetail) o;
        if (storeDetail.getProjId() == 0) {
            errors.rejectValue("projId", "required.projId",
                    "Please Select Project Name.");
        }
        if ("--Please Select--".equalsIgnoreCase(storeDetail.getItemType())) {
            errors.rejectValue("itemType", "required.itemType",
                    "Please select a valid Item Type");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recievedDate",
                "required.recievedDate", "Enter Recieved Date");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicleNumber",
                "required.vehicleNumber", "Enter Vehicle Number");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recievedQuantity",
                "required.recievedQuantity", "Enter Recieved Quantity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "comments",
                "required.comments", "Enter Comments");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierName",
                "required.supplierName", "Enter the Supplier Name");

        if (!StringUtils.isNullOrEmpty(storeDetail.getRecievedQuantity())) {
            pattern = Pattern.compile(ID_PATTERN);
            matcher = pattern.matcher(storeDetail.getRecievedQuantity());
            if (!matcher.matches()) {
                errors.rejectValue("recievedQuantity", "recievedQuantity.incorrect",
                        "Enter a numeric value");
            }
        }

    }
}
