package com.psk.pms.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Item;
import com.psk.pms.model.DescItemDetail.ItemDetail;
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
		item.setEmployeeId(employeeId);
		model.addAttribute("itemForm", item);
		return "BuildItem";
	}
	
	@RequestMapping(value = "/emp/myview/buildItem/searchItem.do", method = RequestMethod.GET)
	public @ResponseBody
	List<String> getProjectList(@RequestParam("term") String name) {
		LOGGER.info("method = getItemList()");
		return fetchItemInfo(name);
	}
	
	private List<String> fetchItemInfo(String itemName) {
		LOGGER.info("method = fetchItemInfo()");
		List<String> result = new ArrayList<String>();
		Set<String> itemNames = projectService.fetchItemNames();
		LOGGER.info("The Item Name Size:" + itemNames.size());
		for (String item : itemNames) {
			if (item.toUpperCase().indexOf(itemName.toUpperCase())!= -1) {
				result.add(item);
			}
		}
		return result;
	}
	
	@RequestMapping(value = "/emp/myview/buildProjectDesc/loadProjDescItems.do")
	public String loadProjDescItems(Model model, @RequestParam String projDescSerial, 
								@RequestParam String projId, @RequestParam String subProjId, 
								@RequestParam String projDescId, @RequestParam String employeeId) {
		DescItemDetail descItemDetail = new  DescItemDetail();
		descItemDetail.setProjId(new Integer(projId));
		descItemDetail.setSubProjId(new Integer(subProjId));
		descItemDetail.setProjDescId(new Integer(projDescId));
		descItemDetail.setProjDescSerial(projDescSerial);
		descItemDetail = projectService.getDataDescription(descItemDetail);
		
		Gson gson = new Gson();
		JsonElement element = gson.toJsonTree(descItemDetail.getItemDetail(), new TypeToken<List<ItemDetail>>() {}.getType());
		if (! element.isJsonArray()) {
			
		}
		JsonArray jsonArray = element.getAsJsonArray();
		descItemDetail.setProjDescItemDetail(jsonArray.toString());
		descItemDetail.setEmployeeId(employeeId);
		model.addAttribute("descItemForm", descItemDetail);
		return "DescItem";
	}
	
	@RequestMapping(value = "/emp/myview/buildProjectDesc/saveProjDescItems.do", method = RequestMethod.POST, consumes="application/json")
	public @ResponseBody boolean saveProjDescItems(@RequestBody DescItemDetail descItemDetail) throws JsonParseException, JsonMappingException, IOException{
		ObjectMapper mapper = new ObjectMapper();
		List<com.psk.pms.model.DescItemDetail.ItemDetail> itemList = mapper.readValue(descItemDetail.getProjDescItemDetail(), mapper.getTypeFactory().constructCollectionType(List.class, com.psk.pms.model.DescItemDetail.ItemDetail.class));
		descItemDetail.setItemDetail(itemList);
		boolean status = projectService.insertDataDescription(descItemDetail);
		return status;
	}

}
