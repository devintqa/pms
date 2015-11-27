package com.psk.pms.validator;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.BulkUploadException;
import com.psk.pms.model.ItemRateDescription;
import com.psk.pms.model.ProjDescDetail;

import static com.psk.pms.Constants.*;

public class BulkUploadDetailsValidator {

    private final String DECIMAL_PATTERN = "\\d+(?:\\.\\d+)?$";

    public void validateExtractedProjectDescriptionDetails(
            List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        rejectIfEmptyFieldsFound(projDescDetails);
        rejectIfSerialNumberIsDuplicated(projDescDetails);
        rejectIfAliasDescriptionIsDuplicated(projDescDetails);
    }

    private void rejectIfEmptyFieldsFound(List<ProjDescDetail> projDescDetails)
            throws BulkUploadException {
        Iterator<ProjDescDetail> projectDescDetailIterator = projDescDetails.iterator();
        int rowCount = 2;
        while (projectDescDetailIterator.hasNext()) {
            ProjDescDetail projDescDetail = (ProjDescDetail) projectDescDetailIterator.next();
            if (StringUtils.isNullOrEmpty(projDescDetail.getSerialNumber())) {
                throw new BulkUploadException(SERIALNUMBEREMPTY + ROW_NUMBER + rowCount);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getWorkType())) {
                throw new BulkUploadException(WORKTYPEEMPTY + ROW_NUMBER + rowCount);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getMetric())) {
                throw new BulkUploadException(METRIC_IS_EMPTY + ROW_NUMBER + rowCount);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getQuantity())) {
                throw new BulkUploadException(QUANTITYINFIGEMPTY + ROW_NUMBER + rowCount);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getDescription())) {
                throw new BulkUploadException(DESCEMPTY + ROW_NUMBER + rowCount);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getAliasDescription())) {
                throw new BulkUploadException(ALIASDESCEMPTY + ROW_NUMBER + rowCount);
            }
            if (180 < projDescDetail.getAliasDescription().length()) {
                throw new BulkUploadException(ALIAS_DESCRIPTION_EXCEEDED + ROW_NUMBER + rowCount);
            }
            if (validateForDecimalValue(projDescDetail.getQuantity())) {
                throw new BulkUploadException(QUANTITY_SHOULD_BE_NUMBER + ROW_NUMBER + rowCount);
            }
            if (projDescDetail.getQuantity().length() > 15) {
                throw new BulkUploadException(QUANTITY_LENGTH_EXCEEDED + ROW_NUMBER + rowCount);
            }
            rowCount++;
        }
    }

    public boolean validateForDecimalValue(String input) {
        Pattern pattern = Pattern.compile(DECIMAL_PATTERN);
        Matcher matcher = pattern.matcher(input);
        return !matcher.matches();
    }

    private void rejectIfSerialNumberIsDuplicated(
            List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        int sizeOfExtractedDetails = projDescDetails.size();
        Set<String> uniqueSerialNumbers = new HashSet<String>();
        Set<String> duplicateSerialNumbers = new HashSet<String>();
        for (ProjDescDetail projDescDetail : projDescDetails) {
            if (!uniqueSerialNumbers.add(projDescDetail.getSerialNumber())) {
                duplicateSerialNumbers.add(projDescDetail.getSerialNumber());
            }
        }
        StringBuilder duplicateValues = getDuplicateValues(duplicateSerialNumbers);
        if (sizeOfExtractedDetails > uniqueSerialNumbers.size()) {
            throw new BulkUploadException(duplicateValues.toString() + " are not unique");
        }
    }

    private void rejectIfAliasDescriptionIsDuplicated(
            List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        int sizeOfExtractedDetails = projDescDetails.size();
        Set<String> uniqueAliasDescription = new HashSet<String>();
        Set<String> duplicateAliasDescription = new HashSet<String>();
        for (ProjDescDetail projDescDetail : projDescDetails) {
            if (!uniqueAliasDescription.add(projDescDetail.getAliasDescription())) {
                duplicateAliasDescription.add(projDescDetail.getAliasDescription());
            }
        }
        StringBuilder buffer = getDuplicateValues(duplicateAliasDescription);
        if (sizeOfExtractedDetails > uniqueAliasDescription.size()) {
            throw new BulkUploadException(buffer.toString() + " are not unique");
        }
    }

    public static StringBuilder getDuplicateValues(Set<String> values) {
        StringBuilder buffer = new StringBuilder();
        if (values.size() > 0) {
            String delim = "";
            for (String o : values) {
                buffer.append(delim);
                delim = ", ";
                buffer.append(o);
            }
        }
        return buffer;
    }

    public void validateFields(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        rejectIfFieldIsEmpty(itemRateDescriptions);
        rejectIfItemRateDescriptionIsDuplicated(itemRateDescriptions);
        rejectIfItemNameExceedLimit(itemRateDescriptions);
    }

    private void rejectIfFieldIsEmpty(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        for (ItemRateDescription itemRateDescription : itemRateDescriptions) {
            if (StringUtils.isNullOrEmpty(itemRateDescription.getItemName())) {
                throw new BulkUploadException(ITEM_NAME_EMPTY +" in "+itemRateDescription.getWorkType()+" page");
            }
            if (StringUtils.isNullOrEmpty(itemRateDescription.getItemUnit())) {
                throw new BulkUploadException("'"+itemRateDescription.getItemName() +"'"+ ITEM_UNIT_EMPTY+" in "+itemRateDescription.getWorkType()+" page");
            }
            if (StringUtils.isNullOrEmpty(itemRateDescription.getItemRate())) {
                throw new BulkUploadException("'"+itemRateDescription.getItemName()+"'"+ ITEM_RATE_EMPTY+" in "+itemRateDescription.getWorkType()+" page");
            }
            if(StringUtils.isNullOrEmpty(itemRateDescription.getScheduleItemNumber())){
                throw new BulkUploadException("'"+itemRateDescription.getItemName()+"'"+ ITEM_SCHDEDULE_NUMBER_EMPTY+" in "+itemRateDescription.getWorkType()+" page");
            }
        }
    }

    private void rejectIfItemNameExceedLimit(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        for (ItemRateDescription itemRateDescription : itemRateDescriptions) {
            if (itemRateDescription.getItemName().length() > 100) {
                throw new BulkUploadException("Item Name '"+itemRateDescription.getItemName() +"' should not exceed 100 ");
            }
        }
    }

    private void rejectIfItemRateDescriptionIsDuplicated(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        int size = itemRateDescriptions.size();
        Set<ItemRateDescription> items = new HashSet<ItemRateDescription>();
        Set<String> duplicateItems = new HashSet<String>();
        for (ItemRateDescription itemRateDescription : itemRateDescriptions) {
            if (!items.add(itemRateDescription)) {
                duplicateItems.add(itemRateDescription.getItemName());
            }
        }
        StringBuilder duplicateValues = getDuplicateValues(duplicateItems);
        if (size > items.size()) {
            throw new BulkUploadException(duplicateValues.toString() + " are not unique");
        }
    }
}
