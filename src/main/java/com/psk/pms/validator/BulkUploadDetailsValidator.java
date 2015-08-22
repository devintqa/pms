package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.BulkUploadException;
import com.psk.pms.model.ItemRateDescription;
import com.psk.pms.model.ProjDescDetail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
		for (ProjDescDetail projDescDetail : projDescDetails) {
			uniqueSerialNumbers.add(projDescDetail.getSerialNumber());
		}
		if (sizeOfExtractedDetails > uniqueSerialNumbers.size()) {
			throw new BulkUploadException(SERIALNOTUNIQUE);
		}
	}

	private void rejectIfAliasDescriptionIsDuplicated(
			List<ProjDescDetail> projDescDetails) throws BulkUploadException {
		int sizeOfExtractedDetails = projDescDetails.size();
		Set<String> uniqueAliasDescription = new HashSet<String>();
		for (ProjDescDetail projDescDetail : projDescDetails) {
			uniqueAliasDescription.add(projDescDetail.getAliasDescription());
		}
		if (sizeOfExtractedDetails > uniqueAliasDescription.size()) {
			throw new BulkUploadException(ALIASNOTUNIQUE);
		}
	}

    public void validateFields(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        rejectIfFieldIsEmpty(itemRateDescriptions);
        rejectIfItemRateDescriptionIsDuplicated(itemRateDescriptions);
        rejectIfItemNameExceedLimit(itemRateDescriptions);
    }

    private void rejectIfFieldIsEmpty(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        for (ItemRateDescription itemRateDescription:itemRateDescriptions){
            if(StringUtils.isNullOrEmpty(itemRateDescription.getItemName())){
                throw new BulkUploadException(ITEM_NAME_EMPTY);
            }
            if(StringUtils.isNullOrEmpty(itemRateDescription.getItemUnit())){
                throw new BulkUploadException(ITEM_UNIT_EMPTY);
            }
            if(StringUtils.isNullOrEmpty(itemRateDescription.getItemRate())){
                throw new BulkUploadException(ITEM_RATE_EMPTY);
            }
        }
    }

    private void rejectIfItemNameExceedLimit(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        for (ItemRateDescription itemRateDescription:itemRateDescriptions){
            if(itemRateDescription.getItemName().length()>100){
                throw new BulkUploadException(PROJECT_ITEM_NAME_TOO_BIG);
            }
        }
    }

    private void rejectIfItemRateDescriptionIsDuplicated(List<ItemRateDescription> itemRateDescriptions) throws BulkUploadException {
        int size = itemRateDescriptions.size();
        Set<ItemRateDescription>  items = new HashSet<ItemRateDescription>();
        for (ItemRateDescription itemRateDescription:itemRateDescriptions){
            items.add(itemRateDescription);
        }
        if(size>items.size()){
            throw new BulkUploadException(PROJECT_ITEM_DESCRIPTION_NOT_UNIQUE);
        }
    }
}
