package com.psk.pms.controller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.psk.pms.Constants;
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Employee;
import com.psk.pms.model.Indent;
import com.psk.pms.model.IndentDesc;
import com.psk.pms.model.SearchDetail;
import com.psk.pms.model.IndentDesc.ItemDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.FieldDescriptionService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.validator.SearchValidator;

@Controller
public class IndentController extends BaseController {

	@Autowired
	ProjectDescriptionService projectDescriptionService;

	@Autowired
	private FieldDescriptionService fieldDescriptionService;

	@Autowired
	private SearchValidator searchValidator;

	@RequestMapping(value = "/emp/myview/indent/{employeeId}", method = RequestMethod.GET)
	public String searchDescriptionForIndenting(@PathVariable String employeeId, @RequestParam("team") String team,
			Model model) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		SearchDetail searchDetail = new SearchDetail();
		searchDetail.setEmployeeId(employeeId);
		model.addAttribute("searchProjDescForm", searchDetail);
		model.addAttribute("employeeObj", employee);
		return "BuildIndent";
	}

	@RequestMapping(value = "/emp/myview/indent/searchFieldDescDetail.do", method = RequestMethod.POST)
	public String searchFieldDescDetail(
			@ModelAttribute("searchProjDescForm") SearchDetail searchDetail,
			BindingResult result,
			Model model,
			SessionStatus status) {
		searchDetail.setSearchUnder("project");
		searchDetail.setSearchOn(Constants.FIELD);
		searchValidator.validate(searchDetail, result);
		if (!result.hasErrors()) {
			List<ProjDescDetail> projDescDocList = fieldDescriptionService.getFieldDescDetailList(searchDetail);
			if (projDescDocList.size() > 0) {
				model.addAttribute("projDescDocList", projDescDocList);
				model.addAttribute("projDescDocListSize", projDescDocList.size());
				model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
			} else {
				model.addAttribute("noDetailsFound", "No Project Descriptions Found For The Selection.");
			}
		}
		return "BuildIndent";
	}


	@RequestMapping(value = "/emp/myview/indent/search_{employeeId}", method = RequestMethod.GET)
	public String buildIndents(@PathVariable String employeeId, @RequestParam("team") String team,
			Model model) {
		Employee employee = new Employee();
		employee.setEmployeeId(employeeId);
		employee.setEmployeeTeam(team);
		SearchDetail searchDetail = new SearchDetail();
		searchDetail.setEmployeeId(employeeId);
		model.addAttribute("searchIndentForm", searchDetail);
		model.addAttribute("employee", employee);
		return "SearchIndent";
	}

	@RequestMapping(value = "/emp/myview/indent/searchIndentsOfProject.do", method = RequestMethod.POST)
	public String searchIndent(
			@ModelAttribute("searchIndentForm") SearchDetail searchDetail,
			BindingResult result,
			Model model,
			SessionStatus status) {
		searchValidator.indentValidator(searchDetail, result);
		if (!result.hasErrors()) {
			List<Indent> indentList = fieldDescriptionService.getIndentList(searchDetail);
			if (indentList.size() > 0) {
				model.addAttribute("indentList", indentList);
				model.addAttribute("indentListSize", indentList.size());
				model.addAttribute("projectAliasName", searchDetail.getAliasProjectName());
				model.addAttribute("employeeId", searchDetail.getEmployeeId());
			} else {
				model.addAttribute("noDetailsFound", "Project Descriptions not found for the selection.");
			}
		}
		return "SearchIndent";
	}

	@RequestMapping(value = "/emp/myview/indent/createIndent", method = RequestMethod.GET)
	public String createIndent(
			@RequestParam(value = "employeeId") String employeeId, 
			@RequestParam(value = "projectId") String projectId, 
			@RequestParam(value = "projDescs") String projDescs,
			@RequestParam(required = false, value = "indentId", defaultValue = "0") String indentId,
			Model model) {
		String[] projDescId = projDescs.split(",");
		List<IndentDesc> indentDescList = null;
		Indent indent = null;
		if(!indentId.equals("0")){
			indent = fieldDescriptionService.getIndent(indentId);
			indent.setEmployeeId(employeeId);
		}else{
			indent = new Indent();
			indent.setStartDate(null);
			indent.setEndDate(null);
			indent.setProjId(projectId);
			indent.setEmployeeId(employeeId);
			indent.setIndentId("_");
		}
		indentDescList = new ArrayList<IndentDesc>();
		for(int i=0; i<projDescId.length; i++){
			if(indentId.equals("0")){
				IndentDesc indentDesc = getNewIndentDesc(projDescId[i], employeeId, indentId);
				indentDescList.add(indentDesc);
			}
		}
		if(!indentId.equals("0")){
			indentDescList = indent.getIndentDescList();
		}else{
			indent.setIndentDescList(indentDescList);
		}
		model.addAttribute("indentDescList",indentDescList);
		model.addAttribute("indent", indent);
		model.addAttribute("projId", projectId);
		model.addAttribute("employeeId", employeeId);

		return "CreateIndent";
	}

	@RequestMapping(value = "/emp/myview/indent/getIndentItem", method = RequestMethod.GET)
	@ResponseBody public List<ItemDetail> getIndentItem(
			@RequestParam(value = "indentQty") String indentQty,
			@RequestParam(value = "projDescId") String projDescId,
			Model model) {

		List<ItemDetail> itemList= new ArrayList<ItemDetail>();
		DescItemDetail descItemDetail = itemService.getPskFieldDescriptionItems(projDescId);
		itemList = buildIndentedItem(indentQty, descItemDetail.getItemDetail());
		return itemList;
	}

	@RequestMapping(value = "/emp/myview/indent/validateIndentDescQty", method = RequestMethod.GET)
	@ResponseBody public String validateIndentDescQty(
			@RequestParam(value = "indentQty") String indentDescQty,
			@RequestParam(value = "projDescId") String projDescId,
			Model model) {
		String validation = "valid";
		BigDecimal availedDescIndentQtyBigD = new BigDecimal(0);
		Double askingIndentQty = new Double(indentDescQty);
		ProjDescDetail projDesc = fieldDescriptionService.getPskFieldProjectDescription(projDescId);
		Map<String, Object> availedDescIndentQty = fieldDescriptionService.getRequestedIndentQty(projDesc.getProjId());
		Double fixedProjDescQty = new Double(projDesc.getQuantity());
		if(askingIndentQty <= fixedProjDescQty){
			if(availedDescIndentQty.size() > 0 && null!=availedDescIndentQty.get(projDescId))
				availedDescIndentQtyBigD = (BigDecimal) availedDescIndentQty.get(projDescId);
			Double sumOfQty = availedDescIndentQtyBigD.doubleValue() + askingIndentQty;
			Double qtyAvailable = fixedProjDescQty - availedDescIndentQtyBigD.doubleValue();
			if(sumOfQty >  fixedProjDescQty){
				validation = "Quantity requested is more than the available limit of "+qtyAvailable;
			}
		}else{
			validation = "Quantity cannot be more than the fixed description limit";
		}
		return validation;
	}


	@RequestMapping(value = "/emp/myview/indent/saveIndentItem.do", method = RequestMethod.POST)
	@ResponseBody public Integer saveIndentItem(@RequestBody Indent indent) {
		return fieldDescriptionService.saveIndentDescription(indent);
	}

	@RequestMapping(value = "/emp/myview/indent/itemToRequest", method = RequestMethod.GET)
	public String getIndentItemForRequest(
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "indentId") String indentId,
			@RequestParam(value = "status") String status,
			Model model) {
		IndentDesc indentDesc = new IndentDesc();
		indentDesc.setIndentId(indentId);
		Employee employee = employeeService.getEmployeeDetails(employeeId);
		indentDesc.setItemDetails(itemService.getIndentItemForRequest(indentId));
		model.addAttribute("indentDesc", indentDesc);
		model.addAttribute("indentItems", indentDesc.getItemDetails());
		model.addAttribute("indentItemSize", indentDesc.getItemDetails().size());
		model.addAttribute("employeeId", employee.getEmployeeId());
		model.addAttribute("employeeObj", employee);
		model.addAttribute("indentStatus", status);
		return "PlaceIndentRequest";
	}

	@RequestMapping(value = "/emp/myview/indent/placeIndentRequest.do", method = RequestMethod.POST)
	@ResponseBody  public String placeIndentRequest(
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "indentId") String indentId,
			@RequestParam(value = "indentStatus") String indentStatus,
			Model model) {
		Indent indent = new Indent();
		indent.setEmployeeId(employeeId);
		indent.setIndentId(indentId);
		indent.setStatus(indentStatus);
		return fieldDescriptionService.placeIndentRequest(indent);
	}

	private List<ItemDetail> buildIndentedItem(String quantity, List<com.psk.pms.model.DescItemDetail.ItemDetail> itemList) {

		Double indentQty = new Double(quantity);

		List<ItemDetail> indentItem = new ArrayList<ItemDetail>();
		for(com.psk.pms.model.DescItemDetail.ItemDetail detail : itemList){
			ItemDetail item = new ItemDetail();
			item.setItemName(detail.getItemName());
			item.setItemPrice(detail.getItemPrice());
			item.setItemType(detail.getItemType());
			item.setItemUnit(detail.getItemUnit());
			Integer itemQty = new Integer(detail.getItemQty());
			double requiredItemQty = itemQty * indentQty;
			item.setItemQty(new Double(requiredItemQty).toString());
			indentItem.add(item);
		}
		return indentItem;
	}
	
	IndentDesc getNewIndentDesc(String projDescId, String employeeId, String indentId){
		ProjDescDetail descDetail = fieldDescriptionService.getPskFieldProjectDescription(projDescId);
		IndentDesc indentDesc = new IndentDesc();
		indentDesc.setAliasProjDesc(descDetail.getAliasDescription());
		indentDesc.setTotalQty(new Double(descDetail.getQuantity()));
		indentDesc.setPlannedQty(0.0);
		indentDesc.setMetric(descDetail.getMetric());
		indentDesc.setProjDescId(descDetail.getProjDescId().toString());
		indentDesc.setProjId(descDetail.getProjId().toString());
		if(indentId.equals("0"))
			indentDesc.setIndentDescId("_");
		else
			indentDesc.setIndentDescId(null);
		return indentDesc;
	}
}
