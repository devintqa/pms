package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.ValidationException;
import com.psk.pms.model.ItemRateDescription;
import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;
import com.psk.pms.service.PurchaseService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.mysql.jdbc.StringUtils.isNullOrEmpty;
import static com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import static com.psk.pms.validator.BulkUploadDetailsValidator.getDuplicateValues;

public class SupplierValidator extends BaseValidator implements Validator {

    @Autowired
    PurchaseService purchaseService;

    private Pattern pattern;
    private Matcher matcher;

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

        if (!StringUtils.isNullOrEmpty(supplier.getTinNumber())) {
            boolean tinNumberExists = purchaseService.isTinNumberExists(supplier.getTinNumber());
            if (tinNumberExists) {
                errors.rejectValue("tinNumber", "tinNumber.incorrect", "Tin number already exist");
            }

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

    public void validate(QuoteDetails quoteDetails) throws ValidationException {
        List<SupplierQuoteDetails> supplierQuoteDetails = quoteDetails.getSupplierQuoteDetails();
        if (null == supplierQuoteDetails || supplierQuoteDetails.isEmpty()) {
            throw new ValidationException("There are no Quote Details to be saved");
        }
        for (SupplierQuoteDetails supplierQuoteDetail : supplierQuoteDetails) {
            if (supplierQuoteDetail.getSupplierAliasName().isEmpty()) {
                throw new ValidationException("There are no Quote Details to be saved");
            }
            if (supplierQuoteDetail.getQuotedPrice().isEmpty()) {
                throw new ValidationException("Quote price is not available for " + supplierQuoteDetail.getSupplierAliasName());
            }
            Supplier supplierDetail = purchaseService.getSupplierDetail(supplierQuoteDetail.getSupplierAliasName());
            if("Dealer".equalsIgnoreCase(supplierDetail.getSupplierType())){
                validateDealerDetails(supplierQuoteDetail,quoteDetails);

            }
            if (!StringUtils.isNullOrEmpty(supplierQuoteDetail.getQuotedPrice())) {
                pattern = Pattern.compile(AMOUNT_PATTERN);
                matcher = pattern.matcher(supplierQuoteDetail.getQuotedPrice());
                if (!matcher.matches()) {
                    throw new ValidationException("Enter a numeric value and only a single dot is allowed for Quote Price field");
                } else if (supplierQuoteDetail.getQuotedPrice().length() > 15) {
                    throw new ValidationException("Quote price Field must not exceed 15 characters for " + supplierQuoteDetail.getSupplierAliasName());
                }
            }
        }
    }

    private void validateDealerDetails(SupplierQuoteDetails supplierQuoteDetail, QuoteDetails quoteDetails) throws ValidationException {
        if(StringUtils.isNullOrEmpty(supplierQuoteDetail.getBrandName())){
            throw new ValidationException("Brand name cant be empty for" + supplierQuoteDetail.getSupplierAliasName());
        }
        int size = quoteDetails.getSupplierQuoteDetails().size();
        Set<SupplierQuoteDetails> supplierQuoteDetails = new HashSet<>();
        Set<String> duplicateItems = new HashSet<>();
        for (SupplierQuoteDetails details : quoteDetails.getSupplierQuoteDetails()) {
            if (!supplierQuoteDetails.add(details)) {
                duplicateItems.add(details.getSupplierAliasName());
            }
        }

        StringBuilder duplicateValues = getDuplicateValues(duplicateItems);
        if(size>supplierQuoteDetails.size()){
            throw new ValidationException("Brand name cant Same for "+duplicateValues.toString());
        }
    }

    public void validateSupplier(QuoteDetails quoteDetails) throws ValidationException {
        List<SupplierQuoteDetails> supplierQuoteDetails = quoteDetails.getSupplierQuoteDetails();
        if (null == supplierQuoteDetails || supplierQuoteDetails.isEmpty()) {
            throw new ValidationException("There are no Supplier Details to Approve");
        }
        for (SupplierQuoteDetails supplierQuoteDetail : supplierQuoteDetails) {
            if (supplierQuoteDetail.getSupplierAliasName().isEmpty()) {
                throw new ValidationException("There are no Supplier Details to Approve");
            }
            if (StringUtils.isNullOrEmpty(supplierQuoteDetail.getItemQty())) {

                throw new ValidationException("Enter Approving Quantity");
            }
            if (!StringUtils.isNullOrEmpty(supplierQuoteDetail.getItemQty())) {
                pattern = Pattern.compile(AMOUNT_PATTERN);
                matcher = pattern.matcher(supplierQuoteDetail.getItemQty());
                if (!matcher.matches()) {
                    throw new ValidationException("Enter a numeric value and only a single dot is allowed for Approving Quantity field");
                }
            }
        }
    }

    public void validate(QuoteDetails quoteDetailsForm, BindingResult result, String save) {
        ValidationUtils.rejectIfEmptyOrWhitespace(result, "tentativeDeliveryDate",
                "required.tentativeDeliveryDate", "Enter Tentative Delivery Date");
    }
}
