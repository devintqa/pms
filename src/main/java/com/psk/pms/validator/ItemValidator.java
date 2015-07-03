package com.psk.pms.validator;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Item;
import com.psk.pms.service.ProjectService;

public class ItemValidator extends BaseValidator implements Validator{
	
	@Autowired
	ProjectService projectService;

	private static final Logger LOGGER = Logger.getLogger(ItemValidator.class);

	@Override
	public boolean supports(Class<?> clazz) {
		return Item.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		LOGGER.info("Item Validation Start");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemName",
				"required.itemName", "Enter Item Name.");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "itemUnit",
			"required.itemUnit", "Enter Item Unit.");
		
		Item item = (Item)target;
		
		if(item.getItemUnit().length() > 20){
			errors.rejectValue("itemUnit", "itemUnit.incorrect","Field must not exceed 20 characters.");
		}

        if(item.getItemName().length() > 100){
            errors.rejectValue("itemName", "itemName.incorrect","Field must not exceed 50 characters.");
        } else {
        	boolean isItemAlreadyExisting = projectService.isItemAlreadyExisting(item.getItemName());
			if(isItemAlreadyExisting){
				errors.rejectValue("itemName", "itemName.incorrect","Item Name Already Found To Be Existing.");
			}
        }
	}
}

