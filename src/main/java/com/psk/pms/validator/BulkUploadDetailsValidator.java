package com.psk.pms.validator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.BulkUploadException;
import com.psk.pms.Constants;
import com.psk.pms.model.ItemRateDescription;
import com.psk.pms.model.ProjDescDetail;

import static com.psk.pms.Constants.*;

/**
 * Created by prakashbhanu57 on 7/27/2015.
 */
public class BulkUploadDetailsValidator {

    public void validateExtractedProjectDescriptionDetails(
            List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        rejectIfEmptyFieldsFound(projDescDetails);
        rejectIfSerialNumberIsDuplicated(projDescDetails);
        rejectIfAliasDescriptionIsDuplicated(projDescDetails);
    }

    private void rejectIfEmptyFieldsFound(List<ProjDescDetail> projDescDetails)
            throws BulkUploadException {
        for (ProjDescDetail projDescDetail : projDescDetails) {
            if (StringUtils.isNullOrEmpty(projDescDetail.getSerialNumber())) {
                throw new BulkUploadException(SERIALNUMBEREMPTY);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getWorkType())) {
                throw new BulkUploadException(WORKTYPEEMPTY);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getQuantity())) {
                throw new BulkUploadException(QUANTITYINFIGEMPTY);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getDescription())) {
                throw new BulkUploadException(DESCEMPTY);
            }
            if (StringUtils.isNullOrEmpty(projDescDetail.getAliasDescription())) {
                throw new BulkUploadException(ALIASDESCEMPTY);
            }
        }
    }

    private void rejectIfSerialNumberIsDuplicated(
            List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        int sizeOfExtractedDetails = projDescDetails.size();
        Set<String> uniqueSerialNumbers = new HashSet<String>();
        Set<String> duplicateSerialNumbers = new HashSet<>();
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
        Set<String> duplicateAliasDescription = new HashSet<>();
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

    private StringBuilder getDuplicateValues(Set<String> duplicateAliasDescription) {
        StringBuilder buffer = new StringBuilder();
        if (duplicateAliasDescription.size() > 0) {
            String delim = "";
            for (String o : duplicateAliasDescription) {
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
        Set<String> duplicateItems = new HashSet<>();
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
