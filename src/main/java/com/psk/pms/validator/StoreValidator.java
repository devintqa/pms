package com.psk.pms.validator;

import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.ValidationException;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.service.StoreService;

/**
 * Created by Sony on 26-09-2015.
 */
public class StoreValidator extends BaseValidator implements Validator {


	@Autowired
	private StoreService storeService;
	
    private Pattern pattern;
    private Matcher matcher;

    @Override
    public boolean supports(Class<?> aClass) {
        return StoreDetail.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        StoreDetail storeDetail = (StoreDetail) o;
        if (StringUtils.isNullOrEmpty(storeDetail.getAliasProjName())) {
            errors.rejectValue("projId", "required.projId",
                    "Please Select Project Name.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recievedDate",
                "required.recievedDate", "Enter Recieved Date");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "vehicleNumber",
                "required.vehicleNumber", "Enter Vehicle Number");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recievedQuantity",
                "required.recievedQuantity", "Enter Recieved Quantity");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "supplierName",
                "required.supplierName", "Enter the Supplier Name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "invoiceNumber",
                "required.supplierName", "Enter the Invoice Number");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "recievedBy",
                "required.supplierName", "Enter Revieved By Name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "checkedBy",
                "required.supplierName", "Enter Checked By Name");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "tripSheetNumber",
                "required.supplierName", "Enter the TripSheet Number");

        if (!StringUtils.isNullOrEmpty(storeDetail.getRecievedQuantity())) {
            pattern = Pattern.compile(ID_PATTERN);
            matcher = pattern.matcher(storeDetail.getRecievedQuantity());
            if (!matcher.matches()) {
                errors.rejectValue("recievedQuantity", "recievedQuantity.incorrect",
                        "Enter a numeric value");
            }
        }
        
        if (!StringUtils.isNullOrEmpty(storeDetail.getInvoiceNumber())) {
            Integer invoiceNumber = storeService.isRecordExists(storeDetail.getInvoiceNumber());
            if (invoiceNumber.intValue() > 0) {
                errors.rejectValue("invoiceNumber", "invoiceNumber.incorrect", "Invoice number already exist");
            }

        }
        
        if (!StringUtils.isNullOrEmpty(storeDetail.getTripSheetNumber())) {
        	Integer tripSheetNumber = storeService.isRecordExists(storeDetail.getInvoiceNumber());
            if (tripSheetNumber.intValue() > 0) {
                errors.rejectValue("tripSheetNumber", "tripSheetNumber.incorrect", "Trip Sheet Number number already exist");
            }

        }
        
        List<MultipartFile> files = storeDetail.getStoreFiles();
        validateFileExistance(errors, files);

    }
    
    public void validateFileExistance(Errors errors, List<MultipartFile> files) {
        if (null != files) {
            Iterator<MultipartFile> listOfFiles = files
                    .listIterator();
            while (listOfFiles.hasNext()) {
                MultipartFile multipartFile = listOfFiles.next();
                if (null == multipartFile || multipartFile.getSize() == 0) {
                    listOfFiles.remove();
                }
            }
            if (files.size() == 0) {
                errors.reject("no files found");
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
        if ("--Please Select--".equalsIgnoreCase(dispatchDetail.getFieldUser())) {
            throw new ValidationException("Please select a Field Engineer");
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
                    throw new ValidationException("Requested quantity of " + dispatchItem.getItemName() + " should not be empty");
                } else {
                    pattern = Pattern.compile(ID_PATTERN);
                    matcher = pattern.matcher(dispatchItem.getRequestedQuantity());
                    if (!matcher.matches()) {
                        throw new ValidationException("Requested quantity of " + dispatchItem.getItemName() + " should be numeric");
                    }
                }
                int totalQuantity = Integer.parseInt(dispatchItem.getTotalQuantity());
                int requestedItemQuantity = Integer.parseInt(dispatchItem.getRequestedQuantity());
                if (totalQuantity == 0) {
                    throw new ValidationException("There are no " + dispatchItem.getItemName() + " in the store");
                }
                if (requestedItemQuantity > totalQuantity) {
                    throw new ValidationException("Requested Quantity of " + dispatchItem.getItemName() + " is more than available Quantity");
                }
            }
        }
    }

    public void validateReturned(Object o, String returned) throws ValidationException {
        DispatchDetail returnDetail = (DispatchDetail) o;
        if (returnDetail.getProjId() == 0) {
            throw new ValidationException("Please Select Project Name.");
        }
        if ("--Please Select--".equalsIgnoreCase(returnDetail.getItemName())) {
            throw new ValidationException("Please select a valid Item Name");
        }
        if ("--Please Select--".equalsIgnoreCase(returnDetail.getFieldUser())) {
            throw new ValidationException("Please select a Field Engineer");
        }
        List<DispatchDetail.DispatchItems> returnedItems = returnDetail.getDispatchItems();
        if (null == returnedItems || returnedItems.isEmpty()) {
            throw new ValidationException("There are no items selected to be Returned");
        }

        if (returnedItems.size() > 0) {
            for (DispatchDetail.DispatchItems returnedItem : returnedItems) {
                String returnedQuantity = returnedItem.getReturnedQuantity();
                if (returnedItem.getItemName().isEmpty() || returnedItem.getDispatchedQuantity().isEmpty()) {
                    throw new ValidationException("There are no items selected to be Returned");
                }
                if (returnedQuantity == null || returnedQuantity.isEmpty()) {
                    throw new ValidationException("Returning quantity of " + returnedItem.getItemName() + " should not be empty");
                } else {
                    pattern = Pattern.compile(ID_PATTERN);
                    matcher = pattern.matcher(returnedItem.getReturnedQuantity());
                    if (!matcher.matches()) {
                        throw new ValidationException("Returned quantity of " + returnedItem.getItemName() + " should be Numeric");
                    }
                }
                double totalQuantity = Double.valueOf(returnedItem.getDispatchedQuantity());
                double returnedQty = Double.valueOf(returnedItem.getReturnedQuantity());
                if (totalQuantity == 0) {
                    throw new ValidationException("There are no " + returnedItem.getItemName() + " Dispatched to Return");
                }
                int compare = Double.compare(returnedQty, totalQuantity);
                if (compare>0) {
                    throw new ValidationException("Returned Quantity of " + returnedItem.getItemName() + " is more than Dispatched Quantity");
                }
            }
        }
    }

}
