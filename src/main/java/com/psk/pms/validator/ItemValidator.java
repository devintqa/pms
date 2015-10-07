package com.psk.pms.validator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.psk.pms.Constants;
import com.psk.pms.service.ItemService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.psk.pms.model.Item;
import com.psk.pms.model.ProjectConfiguration;
import com.psk.pms.service.ProjectService;

public class ItemValidator extends BaseValidator implements Validator {

	@Autowired
	ProjectService projectService;

	@Autowired
	ItemService itemService;

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

		Item item = (Item) target;

		if (item.getItemUnit().length() > 20) {
			errors.rejectValue("itemUnit", "itemUnit.incorrect",
					"Field must not exceed 20 characters.");
		}

		if (item.getItemName().length() > 100) {
			errors.rejectValue("itemName", "itemName.incorrect",
					"Field must not exceed 50 characters.");
		} else {
			boolean isItemAlreadyExisting = itemService
					.isItemAlreadyExisting(item.getItemName());
			if (isItemAlreadyExisting) {
				errors.rejectValue("itemName", "itemName.incorrect",
						"Item Name Already Found To Be Existing.");
			}
		}
	}

	public String validateItem(String result,
			ProjectConfiguration projectItemConfiguration) {
		List<String> itemNames = new ArrayList<String>();
		Set<String> itemName = new HashSet<String>();
		if (projectItemConfiguration.getItemDetail() != null && projectItemConfiguration.getItemDetail().size() > 1) {
			for (ProjectConfiguration.ItemDetail items : projectItemConfiguration
					.getItemDetail()) {
				String item = items.getItemName().toUpperCase();
				itemNames.add(item);
				itemName.add(item);
			}
			if (itemNames.size() != itemName.size()) {
				return Constants.DUPLICATED_ITEM_NAMES;
			}
		}else {
			if(projectItemConfiguration.getItemDetail().get(0).getItemName().isEmpty()){
				projectItemConfiguration.getItemDetail().remove(0);
			}
		}
		return result;
	}
}
