package com.psk.pms.validator;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.ValidationException;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.model.DispatchDetail;

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

    public void validate(Object o, String dispatched) throws ValidationException {
        DispatchDetail dispatchDetail = (DispatchDetail) o;
        if (dispatchDetail.getProjId() == 0) {
            throw new ValidationException("Please Select Project Name.");
        }
        if ("--Please Select--".equalsIgnoreCase(dispatchDetail.getItemName())) {
            throw new ValidationException("Please select a valid Item Name");
        }
        List<DispatchDetail.DispatchItems> dispatchItems = dispatchDetail.getDispatchItems();
        if (null == dispatchItems || dispatchItems.isEmpty()) {
            throw new ValidationException("There are no items selected to be dispatched");
        }

        if (dispatchItems.size() > 0) {
            for (DispatchDetail.DispatchItems dispatchItem : dispatchItems) {
                String requestedQuantity = dispatchItem.getRequestedQuantity();
                if (dispatchItem.getItemName().isEmpty() || dispatchItem.getTotalQuantity().isEmpty()) {
                    throw new ValidationException("There are no items selected to be dispatched");
                }
                if (requestedQuantity == null || requestedQuantity.isEmpty()) {
                    throw new ValidationException("Requested quantity of " + dispatchItem.getItemName() + "should not be empty");
                } else {
                    pattern = Pattern.compile(ID_PATTERN);
                    matcher = pattern.matcher(dispatchItem.getRequestedQuantity());
                    if (!matcher.matches()) {
                        throw new ValidationException("Requested quantity of " + dispatchItem.getItemName() + "should be numeric");
                    }
                }
                int totalQuantity = Integer.parseInt(dispatchItem.getTotalQuantity());
                int requestedItemQuantity = Integer.parseInt(dispatchItem.getRequestedQuantity());
                if (totalQuantity == 0) {
                    throw new ValidationException("There are no " + dispatchItem.getItemName() + " in the store");
                }
                if (requestedItemQuantity > totalQuantity) {
                    throw new ValidationException("Requested Quantity of " + dispatchItem.getItemName() + "is more than available Quantity");
                }
            }
        }
    }
    public void validateReturned(Object o, String returned) throws ValidationException {
        DispatchDetail returnDetail = (DispatchDetail) o;
        if (returnDetail.getProjId() == 0) {
            throw new ValidationException("Please Select Project Name.");
        }
        List<DispatchDetail.DispatchItems> returnItems = returnDetail.getDispatchItems();
        if (null == returnItems || returnItems.isEmpty()) {
            throw new ValidationException("There are no items selected to be dispatched");
        }

    }
}
