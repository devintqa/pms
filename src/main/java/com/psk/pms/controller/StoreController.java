package com.psk.pms.controller;

import static com.psk.pms.Constants.FIELD;
import static com.psk.pms.Constants.ITEM_TYPE;
import static com.psk.pms.Constants.RECEIVED;
import static com.psk.pms.constants.JSPFileNames.BUILD_STORE_DETAIL;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.SessionStatus;

import com.google.gson.Gson;
import com.psk.pms.Constants;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.JsonData;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.service.PurchaseService;
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
    private PurchaseService purchaseService;

    @Autowired
    private StoreService storeService;


    @RequestMapping(value = "/emp/myview/buildStoreDetail/{employeeId}", method = RequestMethod.GET)
    public String buildStoreDetail(
            @PathVariable String employeeId,
            @RequestParam(value = "aliasProjName", required = true) String aliasProjName,
            @RequestParam(value = "supplierAliasName", required = true) String supplierAliasName,
            @RequestParam(value = "itemName", required = true) String itemName,
            @RequestParam(value = "supplierQuoteStatus", required = true) String supplierQuoteStatus,
            @RequestParam(value = "brandName", required = true) String brandName,
            Model model) {
        LOGGER.info("Store Controller : buildStoreDetail()");
        StoreDetail storeDetail = new StoreDetail();
        storeDetail.setEmployeeId(employeeId);
        storeDetail.setAliasProjName(aliasProjName);
        SupplierQuoteDetails supplierQuoteDetails = purchaseService
                .getSupplierQuoteDetailsByStatus(aliasProjName, itemName,
                        supplierAliasName, supplierQuoteStatus, brandName);
        model.addAttribute("storeDetailForm", storeDetail);
        model.addAttribute("supplierQuoteDetails", supplierQuoteDetails);

        return BUILD_STORE_DETAIL;
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
    public List<StockDetail> getItemNamesInStore(
            HttpServletRequest httpServletRequest) {
        String projId = httpServletRequest.getParameter("projId");
        String itemName = httpServletRequest.getParameter("itemName");
        LOGGER.info("getItemNamesInStore for Project :" + projId);
        return storeService.getItemNamesInStore(projId, itemName);
    }

    @RequestMapping(value = "/emp/myview/returnTransaction/getItemNamesInStoreForReturn.do", method = RequestMethod.GET)
    @ResponseBody
    public List<StockDetail> getItemNamesInStoreForReturn(
            HttpServletRequest httpServletRequest) {
        String projId = httpServletRequest.getParameter("projId");
        String fieldUser = httpServletRequest.getParameter("fieldUser");
        LOGGER.info("getItemNamesInStore for Project :" + projId);
        return storeService.getItemsToReturn(projId, fieldUser);
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
    public String dispatchTransaction(@PathVariable String employeeId,
                                      Model model) {
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
    public String saveDispatchedDetail(
            @RequestBody DispatchDetail dispatchDetail, Model model,
            SessionStatus status) {
        LOGGER.info("Store Controller : saveDispatchedDetail()");
        String result = "Dispatched Details saved successfully";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<DispatchDetail.DispatchItems> dispatchItemsList = mapper
                    .readValue(
                            dispatchDetail.getDispatchItemsValue(),
                            mapper.getTypeFactory().constructCollectionType(
                                    List.class,
                                    DispatchDetail.DispatchItems.class));
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
            ObjectMapper mapper = new ObjectMapper() ;
            List<DispatchDetail.DispatchItems> returnedItemsList = mapper
                    .readValue(
                            retuenDetail.getReturnItemsValue(),
                            mapper.getTypeFactory().constructCollectionType(
                                    List.class,
                                    DispatchDetail.DispatchItems.class));
            retuenDetail.setDispatchItems(returnedItemsList);
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
            Integer projectId = purchaseService.getProjectId(storeDetail.getAliasProjName());
            storeDetail.setProjId(projectId);
            storeValidator.validate(storeDetail, result);
            model.addAttribute("storeDetailForm", storeDetail);
            if (result.hasErrors()) {
                if (storeDetail.getStoreFiles().size() == 0) {
                    model.addAttribute("errorMessage", "Please select one or more files");
                }
                return BUILD_STORE_DETAIL;
            }
            storeService.saveStoreDetail(storeDetail);
            storeService.updateSupplierQuoteDetailStatusAndIndentStatus(storeDetail, RECEIVED);
            model.addAttribute("successMessage",
                    "Store Details saved successfully");
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
        }
        return BUILD_STORE_DETAIL;
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
    public String viewDispatchDetails(@PathVariable String employeeId,
                                      Model model) {
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
    public JsonData getFieldUsersForProject(
            HttpServletRequest httpServletRequest) {
        return getFieldUser(httpServletRequest);
    }

    @RequestMapping(value = "/emp/myview/returnTransaction/getFieldUsersForReturn.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData getFieldUsersForReturn(HttpServletRequest httpServletRequest) {
        return getFieldUser(httpServletRequest);
    }

    private JsonData getFieldUser(HttpServletRequest httpServletRequest) {
        LOGGER.info("getFieldUsers for Project :"
                + httpServletRequest.getParameter("projId"));
        JsonData jsonData = new JsonData();
        String projId = httpServletRequest.getParameter("projId");
        String employeeId = httpServletRequest.getParameter("employeeId");
        List<String> fieldUsers = getFieldUsersForProject(projId, employeeId);
        fieldUsers.add(0, "--Please Select--");
        if (!fieldUsers.isEmpty()) {
            Gson gson = new Gson();
            jsonData.setObject(gson.toJson(fieldUsers));
            jsonData.setSuccess(true);
        }
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/returnTransaction/validateFieldUsersForReturn.do", method = RequestMethod.GET)
    @ResponseBody
    public JsonData validateFieldUsersForReturn(
            HttpServletRequest httpServletRequest) {
        LOGGER.info("getFieldUsers for Project :"
                + httpServletRequest.getParameter("projId"));
        JsonData jsonData = new JsonData();
        jsonData.setData("No Dispatched Details Found For The Selected Field Engineer");
        String projId = httpServletRequest.getParameter("projId");
        String fieldUser = httpServletRequest.getParameter("fieldUser");
        String result = storeService.validateFieldUserForReturn(projId,
                fieldUser);
        List<StockDetail> items = storeService.getItemsToReturn(projId,
                fieldUser);

        Gson gson = new Gson();
        if (Integer.parseInt(result) > 0 && items.size() > 0) {

            jsonData.setObject(gson.toJson(items));
            jsonData.setSuccess(true);

        }
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/viewStockDetails/{employeeId}", method = RequestMethod.GET)
    public String viewStockDetails(@PathVariable String employeeId, Model model) {
        LOGGER.info("View Controller : viewStockDetails()");
        StockDetail stockDetail = new StockDetail();
        stockDetail.setEmployeeId(employeeId);
        Map<String, String> aliasProjectList = populateAliasProjectList(employeeId);
        model.addAttribute("stockDetailForm", stockDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        return "ViewStockDetails";
    }

    @RequestMapping(value = "/emp/myview/viewStockDetails/viewStockDetails.do", method = RequestMethod.POST)
    public String viewStockDetails(
            @ModelAttribute("stockDetailForm") StockDetail stockDetail,
            BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList(stockDetail
                .getEmployeeId());
        model.addAttribute("stockDetailForm", stockDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        if (stockDetail.getProjId() == 0) {
            model.addAttribute("errorMessage", "Please select the project");
            return "ViewStockDetails";
        }
        List<StockDetail> stockDetails = storeService
                .getStockDetails(stockDetail.getProjId());
        if (stockDetails.size() > 0) {
            model.addAttribute("stockDetailsSize", stockDetails.size());
            model.addAttribute("stockDetails", stockDetails);
        } else {
            model.addAttribute("errorMessage",
                    "There are no Materials in the Stock");
        }
        return "ViewStockDetails";
    }

    private List<String> getFieldUsersForProject(String projId,
                                                 String employeeId) {
        String projectId = fetchProjectId(projId, employeeId);
        return storeService.getSelectedUser(FIELD, projectId);
    }

    @RequestMapping(value = "/emp/myview/viewStoreDetails/viewStoreDetails.do", method = RequestMethod.POST)
    public String viewStoreDetails(
            @ModelAttribute("storeDetailForm") StoreDetail storeDetail,
            BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList(storeDetail
                .getEmployeeId());
        model.addAttribute("storeDetailForm", storeDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        if (storeDetail.getProjId() == 0) {
            model.addAttribute("errorMessage", "Please select the project");
            return "ViewStoreDetails";
        }
        List<StoreDetail> storeDetails = storeService
                .getStoreDetails(storeDetail.getProjId());
        if (storeDetails.size() > 0) {
            model.addAttribute("storeDetailsSize", storeDetails.size());
            model.addAttribute("storeDetails", storeDetails);
        } else {
            model.addAttribute("errorMessage",
                    "There are no Materials in the Store");
        }
        return "ViewStoreDetails";
    }

    @RequestMapping(value = "/emp/myview/viewDispatchDetails/viewDispatchDetails.do", method = RequestMethod.POST)
    public String viewDispatchDetails(
            @ModelAttribute("dispatchDetailForm") DispatchDetail dispatchDetail,
            BindingResult result, Model model, SessionStatus status) {
        Map<String, String> aliasProjectList = populateAliasProjectList(dispatchDetail
                .getEmployeeId());
        model.addAttribute("dispatchDetailForm", dispatchDetail);
        model.addAttribute("aliasProjectList", aliasProjectList);
        if (dispatchDetail.getProjId() == 0) {
            model.addAttribute("errorMessage", "Please select the project");
            return "ViewDispatchedDetails";
        }
        List<DispatchDetail> dispatchDetails = storeService
                .getDispatchedDetails(dispatchDetail);
        if (dispatchDetails.size() > 0) {
            model.addAttribute("dispatchDetailsSize", dispatchDetails.size());
            model.addAttribute("dispatchDetails", dispatchDetails);
        } else {
            model.addAttribute("errorMessage",
                    "There were no Materials Dispatched from the Store");
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
