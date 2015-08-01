package com.psk.pms.validator;

import com.mysql.jdbc.StringUtils;
import com.psk.exception.BulkUploadException;
import com.psk.pms.model.ProjDescDetail;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.psk.pms.Constants.*;

/**
 * Created by prakashbhanu57 on 7/27/2015.
 */
public class BulkUploadDetailsValidator {

    public void validateExtractedProjectDescriptionDetails(List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        rejectIfEmptyFieldsFound(projDescDetails);
        rejectIfSerialNumberIsDuplicated(projDescDetails);
        rejectIfAliasDescriptionIsDuplicated(projDescDetails);
    }

    public void rejectIfEmptyFieldsFound(List<ProjDescDetail> projDescDetails) throws BulkUploadException {
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

    public void rejectIfSerialNumberIsDuplicated(List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        int sizeOfExtractedDetails = projDescDetails.size();
        Set<String> uniqueSerialNumbers = new HashSet<String>();
        for (ProjDescDetail projDescDetail : projDescDetails) {
            uniqueSerialNumbers.add(projDescDetail.getSerialNumber());
        }
        if (sizeOfExtractedDetails > uniqueSerialNumbers.size()) {
            throw new BulkUploadException(SERIALNOTUNIQUE);
        }
    }

    public void rejectIfAliasDescriptionIsDuplicated(List<ProjDescDetail> projDescDetails) throws BulkUploadException {
        int sizeOfExtractedDetails = projDescDetails.size();
        Set<String> uniqueAliasDescription = new HashSet<String>();
        for (ProjDescDetail projDescDetail : projDescDetails) {
            uniqueAliasDescription.add(projDescDetail.getAliasDescription());
        }
        if (sizeOfExtractedDetails > uniqueAliasDescription.size()) {
            throw new BulkUploadException(ALIASNOTUNIQUE);
        }
    }
}
