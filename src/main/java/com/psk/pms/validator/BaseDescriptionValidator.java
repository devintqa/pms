package com.psk.pms.validator;

import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.ProjectDescriptionService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * Created by prakashbhanu57 on 8/9/2015.
 */
public class BaseDescriptionValidator extends BaseValidator implements Validator {

    private static final Logger LOGGER = Logger.getLogger(BaseDescriptionValidator.class);

    @Autowired
    ProjectDescriptionService projectDescriptionService;

    @Override
    public boolean supports(Class<?> clazz) {
        return ProjDescDetail.class.isAssignableFrom(clazz);
    }


    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "workType",
                "required.workType", "Enter Work Type");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description",
                "required.description", "Enter Description.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "aliasDescription",
                "required.aliasDescription", "Enter Base Description.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "quantity",
                "required.quantity", "Enter Quantity In Figures.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "metric",
                "required.metric", "Enter Quantity Metric Unit.");
        ProjDescDetail projectDescDetail = (ProjDescDetail) target;

        if (projectDescDetail.getAliasDescription().length() > 180) {
            errors.rejectValue("aliasDescription", "aliasDescription.incorrect", "Field Should Not Exceed 180 characters");
        }

        if ( null!=projectDescDetail.getAliasDescription() && !"Y".equalsIgnoreCase(projectDescDetail.getIsUpdate())) {
            LOGGER.info("Basedescription  " + projectDescDetail.getAliasDescription());
            boolean isAliasDescriptionAlreadyExisting = projectDescriptionService.isGlobalDescriptionAlreadyExisting(projectDescDetail.getAliasDescription());
            if (isAliasDescriptionAlreadyExisting) {
                errors.rejectValue("aliasDescription", "aliasDescription.incorrect", "Base Description Already Found To Be Existing.");
            }
        }

    }
}
