package com.psk.pms.controller;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.psk.pms.model.Item;
import com.psk.pms.service.ProjectService;

@Controller
public class ItemController {
	
	@Autowired
	ProjectService projectService;
	
	private static final Logger LOGGER = Logger.getLogger(ItemController.class);
	
	@RequestMapping(value = "/emp/myview/buildItem/{employeeId}", method = RequestMethod.GET)
	public String buildItem(@PathVariable String employeeId, Model model) {		
		LOGGER.info("method = buildItem()");
		Item item = new Item();
		Set<String> itemNames = projectService.fetchItemNames();
		LOGGER.info("The Item Name Size:" + itemNames.size());
		item.setItemNames(itemNames);
		item.setEmployeeId(employeeId);
		model.addAttribute("itemForm", item);
		return "BuildItem";
	}

}
