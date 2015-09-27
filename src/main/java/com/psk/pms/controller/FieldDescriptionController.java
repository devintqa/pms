package com.psk.pms.controller;

import static com.psk.pms.Constants.ALREADYEXIST;
import static com.psk.pms.Constants.NOTEXIST;
import static com.psk.pms.Constants.NULL_STRING;
import static com.psk.pms.Constants.SUCCESS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
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
import com.psk.pms.model.DescItemDetail;
import com.psk.pms.model.Indent;
import com.psk.pms.model.Indent.ItemDetail;
import com.psk.pms.model.ProjDescDetail;
import com.psk.pms.service.FieldDescriptionService;
import com.psk.pms.service.ProjectDescriptionService;

/**
 * Created by prakashbhanu57 on 8/18/2015.
 */
@Controller
public class FieldDescriptionController extends BaseController {

    @Autowired
    private ProjectDescriptionService projectDescriptionService;

    @Autowired
    private FieldDescriptionService fieldDescriptionService;

    private static final Logger LOGGER = Logger.getLogger(FieldDescriptionController.class);

    //http://localhost:8080/pms/emp/myview/indent/createIndent?employeeId=akumar&projectId=1&subProjectId=&projDescs=1,2
    @RequestMapping(value = "/emp/myview/indent/createIndent", method = RequestMethod.GET)
	public String createIndent(
			@RequestParam(value = "employeeId") String employeeId,
			@RequestParam(value = "projectId") String projectId, 
			@RequestParam(value = "subProjectId") String subProjectId,
			@RequestParam(value = "projDescs") String projDescs,
			Model model) {
    	
    	List<ItemDetail> itemList = new ArrayList<ItemDetail>();
    	ItemDetail  itemDetail = new ItemDetail();
    	itemDetail.setItemName("CEMENT");
    	itemDetail.setItemType("MATERIAL");
    	itemDetail.setItemQty("1");
    	itemDetail.setItemUnit("KG");
    	itemList.add(itemDetail);
    	
    	itemDetail = new ItemDetail();
     	itemDetail.setItemName("SAND");
     	itemDetail.setItemType("MATERIAL");
     	itemDetail.setItemQty("1");
     	itemDetail.setItemUnit("CFT");
     	itemList.add(itemDetail);
     	
     	String[] projDescId = projDescs.split(",");
    	List<Indent> indentList = new ArrayList<Indent>();
    	for(int i=0; i<projDescId.length; i++){
    		ProjDescDetail descDetail = projectDescriptionService.getPskFieldProjectDescription(projDescId[i]);
    		Indent indent = new Indent();
    		indent.setAliasProjDesc(descDetail.getAliasDescription());
    		indent.setEmployeeId(employeeId);
    		try {
    			indent.setStartDate(null);
				indent.setEndDate(null);
			} catch (Exception e) {
				e.printStackTrace();
			}
    		indent.setPlannedArea(0.0);
    		indent.setTotalArea(new Double(descDetail.getQuantity()));
    		indent.setMetric(descDetail.getMetric());
    		indent.setProjDescId(descDetail.getProjDescId().toString());
    		indent.setProjId(descDetail.getProjId().toString());
    		indent.setSubProjId(null);
    		indent.setIndentId(null);
    		indent.setItemDetails(itemList);
    		indentList.add(indent);
    	}
    	model.addAttribute("indentList",indentList);
    	model.addAttribute("indentForm", new Indent());
    	model.addAttribute("projId", projectId);
    	model.addAttribute("subProjId", subProjectId);
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
    
    @RequestMapping(value = "/emp/myview/indent/saveIndentItem.do", method = RequestMethod.POST)
    @ResponseBody public boolean saveIndentItem(@RequestBody Indent indent) {
    	return fieldDescriptionService.saveIndentDescription(indent);
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

	@RequestMapping(value = "/emp/myview/buildFieldDescription/{employeeId}", method = RequestMethod.GET)
    public String buildFieldDescription(@PathVariable String employeeId,
                                        @RequestParam(value = "team", required = true) String team,
                                        @RequestParam(value = "action", required = false) String action,
                                        Model model) {
        LOGGER.info("Opening buildFieldDescription ");
        ProjDescDetail projDescDetail = new ProjDescDetail();
        model.addAttribute("aliasProjectList", populateAliasProjectList(employeeId));
        model.addAttribute("projDescForm", projDescDetail);
        return "BuildFieldDescription";
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/FieldDescriptionPresent.do", method = RequestMethod.GET)
    public
    @ResponseBody
    String isFieldDescriptionPresent(HttpServletRequest request) {
        int projectId = Integer.parseInt(request.getParameter("projectId"));
        int subProjectId;
        if (NULL_STRING.equalsIgnoreCase(request.getParameter("subProjectId"))) {
            subProjectId = 0;
        } else {
            subProjectId = Integer.parseInt(request.getParameter("subProjectId"));
        }
        LOGGER.info("Checking is filedDescription already present for projectId :" + projectId + "subProjectId :" + subProjectId);
        if (0 != subProjectId) {
            if (!projectDescriptionService.isProjectDescriptionDetailsExistsForSubProject(subProjectId, "N")) {
                return NOTEXIST;
            } else if (fieldDescriptionService.isFieldDescriptionDetailsExistsForSubProject(subProjectId)) {
                return ALREADYEXIST;
            }
            LOGGER.info("method = createFieldDescription , creating  filedDescription data for : projectId" + projectId
                    + "subProjectId" + subProjectId);
            fieldDescriptionService.createFieldDescription(projectId, subProjectId);
        } else {
            if (!projectDescriptionService.isProjectDescriptionDetailsExistsForProject(projectId, "N")) {
                return NOTEXIST;
            } else if (fieldDescriptionService.isFieldDescriptionDetailsExistsForProject(projectId)) {
                return ALREADYEXIST;
            }
            LOGGER.info("method = createFieldDescription , creating  filedDescription data for : projectId" + projectId
                    + "subProjectId" + subProjectId);
            fieldDescriptionService.createFieldDescription(projectId, 0);
        }
        return SUCCESS;
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/getSubAliasProject.do", method = RequestMethod.GET)
    @ResponseBody
    public String getSubAliasProject(HttpServletRequest request,
                                     HttpServletResponse response) {
        LOGGER.info("method = getSubAliasProject() , Sub Project Id : " + request.getParameter("subProjId"));
        Map<String, String> subAliasProjectList = populateSubAliasProjectList(request.getParameter("aliasProjectName"), request.getParameter("empId"));
        subAliasProjectList.put("0", "--Please Select--");
        Gson gson = new Gson();
        return gson.toJson(subAliasProjectList);
    }

    @RequestMapping(value = "/emp/myview/buildFieldDescription/createFieldDescription.do", method = RequestMethod.POST)
    @ResponseBody
    public void createFieldDescription(HttpServletRequest request,
                                       HttpServletResponse response) {
        String projectId = request.getParameter("projectId");
        String subProjectId = request.getParameter("subProjectId");
        int subProjectIdIntValue;
        if (NULL_STRING.equalsIgnoreCase(subProjectId)) {
            subProjectIdIntValue = 0;
        } else {
            subProjectIdIntValue = Integer.parseInt(subProjectId);
        }
        LOGGER.info("method = createFieldDescription , creating  filedDescription data for : projectId" + projectId
                + "subProjectId" + subProjectId);
        fieldDescriptionService.createFieldDescription(Integer.valueOf(projectId), subProjectIdIntValue);
    }
}
