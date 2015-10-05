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
import com.psk.pms.model.IndentDesc;
import com.psk.pms.model.IndentDesc.ItemDetail;
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
			@RequestParam(value = "action") String action,
			Model model) {
    	
    	String[] projDescId = projDescs.split(",");
    	List<IndentDesc> indentDescList = null;
    	Indent indent = null;
    	if(action.equals("view")){
    		
    	}else{
    		indent = new Indent();
    		indent.setStartDate(null);
    		indent.setEndDate(null);
    		indent.setProjId(projectId);
    		indent.setIndentId("_");
    	}
    	indentDescList = new ArrayList<IndentDesc>();
    	for(int i=0; i<projDescId.length; i++){
    		if(action.equals("view")){
//    			indentList = fieldDescriptionService.getIndentDescAndItems(new Integer(projDescId[i]));
    		}else{
	    		IndentDesc indentDesc = getNewIndentDesc(projDescId[i], employeeId, action);
				indentDescList.add(indentDesc);
    		}
    	}
    	indent.setIndentDescList(indentDescList);
    	model.addAttribute("indentDescList",indentDescList);
    	model.addAttribute("indent", indent);
    	model.addAttribute("projId", projectId);
    	model.addAttribute("employeeId", employeeId);

    	return "CreateIndent";
	}
    
    IndentDesc getNewIndentDesc(String projDescId, String employeeId, String action){
    	ProjDescDetail descDetail = fieldDescriptionService.getPskFieldProjectDescription(projDescId);
    	IndentDesc indentDesc = new IndentDesc();
    	indentDesc.setAliasProjDesc(descDetail.getAliasDescription());
    	indentDesc.setTotalQty(new Double(descDetail.getQuantity()));
    	indentDesc.setPlannedQty(0.0);
    	indentDesc.setMetric(descDetail.getMetric());
    	indentDesc.setProjDescId(descDetail.getProjDescId().toString());
    	indentDesc.setProjId(descDetail.getProjId().toString());
		if(action.equals("edit"))
			indentDesc.setIndentDescId("_");
		else
			indentDesc.setIndentDescId(null);
		return indentDesc;
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
