package com.psk.pms.controller;

import com.google.gson.Gson;
import com.psk.pms.model.*;
import com.psk.pms.model.ProjectConfiguration.ItemDetail;
import com.psk.pms.service.ItemService;
import com.psk.pms.service.ProjectDescriptionService;
import com.psk.pms.service.ProjectService;
import com.psk.pms.service.StoreService;
import com.psk.pms.validator.ViewValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

import static com.psk.pms.Constants.ITEM_TYPE;

@Controller
public class ViewController extends BaseController {

    private static final Logger LOGGER = Logger.getLogger(ViewController.class);

    @Autowired
    private ViewValidator viewValidator;

    @Autowired
    private ItemService itemService;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectDescriptionService projectDescriptionService;

    @Autowired
    private StoreService storeService;

    @RequestMapping(value = "/emp/myview/viewDetails/{employeeId}", method = RequestMethod.GET)
    public String viewDetails(@PathVariable String employeeId, Model model) {
        LOGGER.info("View Controller : viewDetails()");
        ViewDetail viewDetail = new ViewDetail();
        viewDetail.setViewProjectItemPrice(true);
        viewDetail.setEmployeeId(employeeId);
        setModelAttribute(model, viewDetail);
        return "ViewDetails";
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

    @RequestMapping(value = "/emp/myview/viewDetails/searchProject.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getProjects(
            @RequestParam("term") String name, @RequestParam("employeeId") String empId) {
        LOGGER.info("method = getProjectList()");
        return fetchProjectInfo(name, empId);
    }

    @RequestMapping(value = "/emp/myview/viewDetails/searchSubProject.do", method = RequestMethod.GET)
    public
    @ResponseBody
    List<String> getSubProjectList(
            @RequestParam("term") String name, @RequestParam("employeeId") String empId) {
        LOGGER.info("method = getSubProjectList()");
        return fetchSubProjectsInfo(name, empId);
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


    @RequestMapping(value = "/emp/myview/viewDetails/viewDetails.do", method = RequestMethod.POST)
    public String viewProjectDetails(
            @ModelAttribute("viewDetailsForm") ViewDetail viewDetail,
            BindingResult result, Model model, SessionStatus status) {
        LOGGER.info("method = viewProjectDetails()");
        viewValidator.validate(viewDetail, result);
        String descType = viewDetail.getDescType();
        if (!result.hasErrors()) {
            ProjectConfiguration projectConfiguration = new ProjectConfiguration();
            projectConfiguration.setProjId(viewDetail.getProjId());
            projectConfiguration.setSubProjId(viewDetail.getSubProjId());
            if (viewDetail.isSearchAggregateItemDetails()) {
                List<DescItemDetail.ItemDetail> aggregateItemDetails = itemService.getProjectData(projectConfiguration, viewDetail.isEditSubProject(), descType);
                if (aggregateItemDetails.size() > 0) {
                    model.addAttribute("aggregateItemDetails", aggregateItemDetails);
                    model.addAttribute("aggregateItemDetailsSize", aggregateItemDetails.size());
                    model.addAttribute("projectAliasName", viewDetail.getAliasProjectName());
                } else {
                    model.addAttribute("noDetailsFound", "No Item Price Configuration Found For The Project.");
                }
            } else if (viewDetail.isViewProjectItemPrice()) {
                ProjectConfiguration itemConfiguration = itemService.getProjectItemConfiguration(projectConfiguration, viewDetail.isEditSubProject());
                List<ItemDetail> itemPriceDetails = itemConfiguration
                        .getItemDetail();
                if (itemPriceDetails.size() > 0) {
                    model.addAttribute("itemPriceDetails", itemPriceDetails);
                    model.addAttribute("itemPriceDetailsSize",
                            itemPriceDetails.size());
                    model.addAttribute("projectAliasName",
                            viewDetail.getAliasProjectName());
                } else {
                    model.addAttribute("noDetailsFound",
                            "No Item Price Configuration Found For The Project.");
                }
            } else if (viewDetail.isSearchComparisonData()) {
                List<ProjDescComparisonDetail> projDescComparisonDetails = projectDescriptionService
                        .getProjectDescComparisonDetail(viewDetail.getProjId());
                if (projDescComparisonDetails.size() > 0) {
                    model.addAttribute("projDescComparisonDetails",
                            projDescComparisonDetails);
                    model.addAttribute("projDescComparisonDetailsSize",
                            projDescComparisonDetails.size());
                    model.addAttribute("projectAliasName",
                            viewDetail.getAliasProjectName());
                } else {

                    model.addAttribute("noDetailsFound",
                            "No Project Comparison Data Found For The Project.");
                }
            } else if (viewDetail.isProjectItemDescription()) {
                List<ProjectItemDescription> projectItemDescription = itemService.getProjectItemDescription(projectConfiguration, viewDetail);
                if (projectItemDescription.size() > 0) {
                    Double sumOfQuantity = getSumOfQuantity(projectItemDescription);
                    model.addAttribute("projectItemDescriptions", projectItemDescription);
                    model.addAttribute("projectItemDescriptionSize", projectItemDescription.size());
                    model.addAttribute("sumOfQuantity", sumOfQuantity);
                } else {
                    model.addAttribute("noDetailsFound", "No Project Description Data Found For The Project.");
                }
            }
        }
        setModelAttribute(model, viewDetail);
        return "ViewDetails";
    }


    @RequestMapping(value = "/emp/myview/viewDetails/getItemNames.do", method = RequestMethod.GET)
    @ResponseBody
    public String getItemNames(HttpServletRequest httpServletRequest) {
        LOGGER.info("getItemNames for Item Type :" + httpServletRequest.getParameter("itemType"));
        String json = null;
        String itemType = httpServletRequest.getParameter("itemType");
        String aliasProjName = httpServletRequest.getParameter("aliasProjName");
        String employeeId = httpServletRequest.getParameter("employeeId");
        String projectId = viewValidator.fetchProjectId(aliasProjName, employeeId);
        List<String> itemNames = itemService.getItemNames(itemType, projectId);
        if (!itemNames.isEmpty()) {
            Gson gson = new Gson();
            json = gson.toJson(itemNames);
        }
        return json;
    }

    private Double getSumOfQuantity(List<ProjectItemDescription> projectItemDescriptions) {
        Double totalQuantity = 0.0;
        for (int i = 0; i < projectItemDescriptions.size(); i++) {
            totalQuantity = totalQuantity
                    + Double.valueOf(projectItemDescriptions.get(i).getItemQuantity());
        }
        return totalQuantity;
    }

    private void setModelAttribute(Model model, ViewDetail viewDetail) {
        List<String> itemTypes = projectService.getDropDownValuesFor(ITEM_TYPE);
        itemTypes.add(0, "--Please Select--");
        viewDetail.setItemType(itemTypes.get(0));
        model.addAttribute("itemTypes", itemTypes);
        model.addAttribute("viewDetailsForm", viewDetail);
    }

}
