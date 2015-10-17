package com.psk.pms.controller;

import static com.psk.pms.Constants.FIELD;
import static com.psk.pms.Constants.ITEM_TYPE;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;
import com.psk.pms.Constants;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.JsonData;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.service.StoreService;
import com.psk.pms.validator.StoreValidator;

/**
 * Created by Sony on 25-09-2015.
 */
@Controller
public class StoreController extends BaseController {

    private static final Logger LOGGER = Logger
            .getLogger(SearchController.class);

    @Autowired
    private StoreValidator storeValidator;

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/emp/myview/buildStoreDetail/{employeeId}", method = RequestMethod.GET)
    public String buildStoreDetail(@PathVariable String employeeId, Model model) {
        LOGGER.info("Store Controller : buildStoreDetail()");
        Map<String, String> aliasProjectList = getProjectDetails(employeeId);
        StoreDetail storeDetail = new StoreDetail();
        storeDetail.setEmployeeId(employeeId);
        model.addAttribute("storeDetailForm", storeDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "BuildStoreDetail";
    }

    @RequestMapping(value = "/emp/myview/buildStoreDetail/getItemNames.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getItemNames(HttpServletRequest httpServletRequest) {
        LOGGER.info("getItemNames for Item Type :"
                + httpServletRequest.getParameter("itemType"));
        JsonData jsonData = new JsonData();
        String itemType = httpServletRequest.getParameter("itemType");
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        String projectId = fetchProjectId(projId, employeeId);
        List<String> itemNames = itemService.getItemNames(itemType, projectId);
        if (!itemNames.isEmpty()) {
            Gson gson = new Gson();
            jsonData.setData(gson.toJson(itemNames));
            jsonData.setSuccess(true);
        }
        return jsonData;
    }


    @RequestMapping(value = "/emp/myview/dispatchTransaction/getItemNamesInStore.do", method = RequestMethod.GET)
    @ResponseBody
    public List<StockDetail> getItemNamesInStore(HttpServletRequest httpServletRequest) {
        String projId = httpServletRequest.getParameter("projId");
        String itemName = httpServletRequest.getParameter("itemName");
        LOGGER.info("getItemNamesInStore for Project :"
                + projId);
        return storeService.getItemNamesInStore(projId, itemName);
    }

    private JsonData getItemAndFieldUser(HttpServletRequest httpServletRequest) {
        JsonData jsonData = new JsonData();
        DispatchDetail dispatchDetail = new DispatchDetail();
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        String projectId = fetchProjectId(projId, employeeId);
        List<String> itemNames = new ArrayList<>();
        List<String> fieldUsers = storeService.getSelectedUser(FIELD, projectId);
        if (!itemNames.isEmpty() && !fieldUsers.isEmpty()) {
            fieldUsers.add(0, "--Please Select--");
            itemNames.add(0, "--Please Select--");
            dispatchDetail.setFieldUsers(fieldUsers);
            dispatchDetail.setItems(itemNames);
            Gson gson = new Gson();
            jsonData.setObject(gson.toJson(dispatchDetail));
            jsonData.setSuccess(true);
        }
        if (itemNames.isEmpty()) {
            jsonData.setData("Items are not present in Store");
        }
        if (fieldUsers.isEmpty()) {
            jsonData.setData("There are no field users for the project");
        }
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/returnTransaction/getItemNamesInStoreForReturn.do", method = RequestMethod.GET)
    @ResponseBody
    public List<StockDetail> getItemNamesInStoreForReturn(HttpServletRequest httpServletRequest) {
        String projId = httpServletRequest.getParameter("projId");
        String itemName = httpServletRequest.getParameter("itemName");
        LOGGER.info("getItemNamesInStore for Project :"
                + projId);
        return storeService.getItemNamesInStore(projId, itemName);
    }


    @RequestMapping(value = "/emp/myview/dispatchTransaction/getTotalQuantity.do", method = RequestMethod.GET)
    @ResponseBody
    public String getTotalQuantity(HttpServletRequest httpServletRequest) {
        LOGGER.info("getTotalQuantity for Project :"
                + httpServletRequest.getParameter("projId"));
        String projId = httpServletRequest.getParameter("projId");
        String itemName = httpServletRequest.getParameter("itemName");
        return storeService.getItemQuantityInStock(projId, itemName);
    }


    @RequestMapping(value = "/emp/myview/dispatchTransaction/{employeeId}", method = RequestMethod.GET)
    public String dispatchTransaction(@PathVariable String employeeId, Model model) {
        LOGGER.info("Store Controller : buildStoreDetail()");
        Map<String, String> aliasProjectList = getProjectDetails(employeeId);
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setEmployeeId(employeeId);
        model.addAttribute("dispatchDetailForm", dispatchDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "StoreTransaction";
    }

    @RequestMapping(value = "/emp/myview/returnTransaction/{employeeId}", method = RequestMethod.GET)
    public String returnTransaction(@PathVariable String employeeId, Model model) {
        LOGGER.info("Store Controller : buildStoreDetail()");
        Map<String, String> aliasProjectList = getProjectDetails(employeeId);
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setEmployeeId(employeeId);
        model.addAttribute("returnDetailForm", dispatchDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "ReturnTransaction";
    }


    @RequestMapping(value = "/emp/myview/dispatchTransaction/saveDispatchedDetail.do", method = RequestMethod.POST)
    @ResponseBody
    public String saveDispatchedDetail(@RequestBody DispatchDetail dispatchDetail,
                                       Model model, SessionStatus status) {
        LOGGER.info("Store Controller : saveDispatchedDetail()");
        String result = "Dispatched Details saved successfully";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DispatchDetail.DispatchItems> dispatchItemsList = mapper.readValue(dispatchDetail.getDispatchItemsValue(), mapper.getTypeFactory().constructCollectionType(List.class, DispatchDetail.DispatchItems.class));
            dispatchDetail.setDispatchItems(dispatchItemsList);
            storeValidator.validate(dispatchDetail, Constants.DISPATCHED);
            storeService.saveDispatchedDetail(dispatchDetail);
            model.addAttribute("successMessage",
                    "Dispatched Details saved successfully");
        } catch (Exception e) {
            return e.getMessage();
        }
        return result;
    }



    @RequestMapping(value = "/emp/myview/returnTransaction/saveReturnedDetail.do", method = RequestMethod.POST)
    @ResponseBody
    public String saveReturnedDetail(@RequestBody DispatchDetail retuenDetail,
                                       Model model, SessionStatus status) {
        LOGGER.info("Store Controller : saveReturnedDetail()");
        String result = "Returned Details saved successfully";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DispatchDetail.DispatchItems> dispatchItemsList = mapper.readValue(retuenDetail.getReturnItemsValue(), mapper.getTypeFactory().constructCollectionType(List.class, DispatchDetail.DispatchItems.class));
            retuenDetail.setDispatchItems(dispatchItemsList);
            storeValidator.validateReturned(retuenDetail, Constants.RETURNED);
            storeService.saveReturnedDetail(retuenDetail);
            model.addAttribute("successMessage",
                    "Returned Details saved successfully");
        } catch (Exception e) {
            return e.getMessage();
        }
        return result;
    }


@RequestMapping(value = "/emp/myview/buildStoreDetail/saveStoreDetail.do", method = RequestMethod.POST)
    public String saveStoreDetail(
            @ModelAttribute("storeDetailForm") StoreDetail storeDetail,
            BindingResult result, Model model, SessionStatus status) {
        try {
            Map<String, String> aliasProjectList = getProjectDetails(storeDetail
                    .getEmployeeId());
            storeValidator.validate(storeDetail, result);
            model.addAttribute("storeDetailForm", storeDetail);
            model.addAttribute("aliasProjectList", aliasProjectList);
            if (result.hasErrors()) {
                return "BuildStoreDetail";
            }
            storeService.saveStoreDetail(storeDetail);
            model.addAttribute("successMessage",
                    "Store Details saved successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "BuildStoreDetail";
    }


    @RequestMapping(value = "/emp/myview/viewStoreDetails/{employeeId}", method = RequestMethod.GET)
    public String viewStoreDetails(@PathVariable String employeeId, Model model) {
        LOGGER.info("View Controller : viewStoreDetails()");
        StoreDetail storeDetail = new StoreDetail();
        storeDetail.setEmployeeId(employeeId);
        Map<String, String> aliasProjectList = populateAliasProjectList(employeeId);
        model.addAttribute("storeDetailForm", storeDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "ViewStoreDetails";
    }

    @RequestMapping(value = "/emp/myview/viewDispatchDetails/{employeeId}", method = RequestMethod.GET)
    public String viewDispatchDetails(@PathVariable String employeeId, Model model) {
        LOGGER.info("View Controller : viewStoreDetails()");
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setEmployeeId(employeeId);
        Map<String, String> aliasProjectList = populateAliasProjectList(employeeId);
        model.addAttribute("dispatchDetailForm", dispatchDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "ViewDispatchedDetails";
    }

    @RequestMapping(value = "/emp/myview/dispatchTransaction/getFieldUsers.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getFieldUsersForProject(HttpServletRequest httpServletRequest) {
        LOGGER.info("getFieldUsers for Project :"
                + httpServletRequest.getParameter("projId"));
        JsonData jsonData = new JsonData();
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        List<String> fieldUsers = getFieldUsersForProject(projId, employeeId);
        Gson gson = new Gson();
        jsonData.setObject(gson.toJson(fieldUsers));
        jsonData.setSuccess(true);
        return jsonData;
    }
    
    @RequestMapping(value = "/emp/myview/returnTransaction/getFieldUsersForReturn.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getFieldUsersForReturn(HttpServletRequest httpServletRequest) {
        LOGGER.info("getFieldUsers for Project :"
                + httpServletRequest.getParameter("projId"));
        JsonData jsonData = new JsonData();
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        List<String> fieldUsers = getFieldUsersForProject(projId, employeeId);
        Gson gson = new Gson();
        jsonData.setObject(gson.toJson(fieldUsers));
        jsonData.setSuccess(true);
        return jsonData;
    }

    private List<String> getFieldUsersForProject(String projId, String employeeId) {
        String projectId = fetchProjectId(projId, employeeId);
        return storeService.getSelectedUser(FIELD, projectId);
    }

    @RequestMapping(value = "/emp/myview/viewDispatchDetails/getFieldUsers.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getFieldUsers(HttpServletRequest httpServletRequest) {
        LOGGER.info("getFieldUsers for Project :"
                + httpServletRequest.getParameter("projId"));
        JsonData jsonData = new JsonData();
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        List<String> fieldUsers = getFieldUsersForProject(projId, employeeId);
        Gson gson = new Gson();
        jsonData.setObject(gson.toJson(fieldUsers));
        jsonData.setSuccess(true);
        return jsonData;
    }


    @RequestMapping(value = "/emp/myview/viewStoreDetails/viewStoreDetails.do", method = RequestMethod.POST)
    public String viewStoreDetails(
            @ModelAttribute("storeDetailForm") StoreDetail storeDetail,
            BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList(storeDetail.getEmployeeId());
        model.addAttribute("storeDetailForm", storeDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        if (storeDetail.getProjId() == 0) {
            model.addAttribute("errorMessage", "Please select the project");
            return "ViewStoreDetails";
        }
        List<StoreDetail> storeDetails = storeService.getStoreDetails(storeDetail.getProjId());
        if (storeDetails.size() > 0) {
            model.addAttribute("storeDetailsSize", storeDetails.size());
            model.addAttribute("storeDetails", storeDetails);
        } else {
            model.addAttribute("errorMessage", "There are no Materials in the Store");
        }

        return "ViewStoreDetails";
    }

    @RequestMapping(value = "/emp/myview/viewDispatchDetails/viewDispatchDetails.do", method = RequestMethod.POST)
    public String viewDispatchDetails(
            @ModelAttribute("dispatchDetailForm") DispatchDetail dispatchDetail,
            BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList(dispatchDetail.getEmployeeId());
        List<String> fieldUsers = storeService.getSelectedUser(FIELD, String.valueOf(dispatchDetail.getProjId()));
        model.addAttribute("fieldUsers", fieldUsers);
        model.addAttribute("dispatchDetailForm", dispatchDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        if (dispatchDetail.getProjId() == 0) {
            model.addAttribute("errorMessage", "Please select the project");
            return "ViewStoreDetails";
        }
        List<DispatchDetail> dispatchDetails = storeService.getDispatchedDetails(dispatchDetail);
        if (dispatchDetails.size() > 0) {
            model.addAttribute("dispatchDetailsSize", dispatchDetails.size());
            model.addAttribute("dispatchDetails", dispatchDetails);
        } else {
            model.addAttribute("errorMessage", "There were no Materials Dispatched from the Store");
        }

        return "ViewDispatchedDetails";
    }

    private Map<String, String> getProjectDetails(String empId) {
        return populateAliasProjectList(empId);
    }

    @ModelAttribute("itemTypes")
    private List<String> getItemTypes() {
        List<String> itemTypes = projectService.getDropDownValuesFor(ITEM_TYPE);
        itemTypes.add(0, "--Please Select--");
        return itemTypes;
    }
}
