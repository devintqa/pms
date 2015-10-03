package com.psk.pms.controller;

import static com.psk.pms.Constants.ITEM_TYPE;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;
import com.psk.pms.model.JsonData;
import com.psk.pms.model.StoreDetail;
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
        LOGGER.info("getItemNames for Item Type :" + httpServletRequest.getParameter("itemType"));
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

    @RequestMapping(value = "/emp/myview/buildStoreDetail/saveStoreDetail.do", method = RequestMethod.POST)
    public String saveDepositAction(@ModelAttribute("storeDetailForm") StoreDetail storeDetail,
                                    BindingResult result, Model model, SessionStatus status) {
        try {
            Map<String, String> aliasProjectList = getProjectDetails(storeDetail.getEmployeeId());
            storeValidator.validate(storeDetail, result);
            model.addAttribute("storeDetailForm", storeDetail);
            model.addAttribute("aliasProjectList", aliasProjectList);
            if (result.hasErrors()) {
                return "BuildStoreDetail";
            }
            storeService.saveStoreDetail(storeDetail);
            model.addAttribute("successMessage", "Store Details saved successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return "BuildStoreDetail";

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