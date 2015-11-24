package com.psk.pms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.psk.exception.ValidationException;
import com.psk.pms.Constants;
import com.psk.pms.model.*;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.service.EmployeeService;
import com.psk.pms.service.PurchaseService;
import com.psk.pms.validator.SupplierValidator;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.psk.pms.Constants.*;
import static com.psk.pms.constants.JSPFileNames.BUILD_SUPPLIER;
import static com.psk.pms.constants.JSPFileNames.SUPPLIER_QUOTE_DETAILS;
import static com.psk.pms.constants.JSPFileNames.VIEW_SUPPLIER_QUOTE_DETAILS;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

    @Autowired
    EmployeeService employeeService;

    @Autowired
    private SupplierValidator supplierValidator;

    private static final org.apache.log4j.Logger LOGGER = org.apache.log4j.Logger.getLogger(PurchaseController.class);

    @RequestMapping(value = "/emp/myview/buildSupplier/{employeeId}", method = RequestMethod.GET)
    public String buildSupplier(@PathVariable String employeeId,
                                @RequestParam(value = "team", required = true) String team,
                                Model model) {
        LOGGER.info("Supplier detail create page.");
        Supplier supplier = new Supplier();
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        model.addAttribute("employee", employee);
        model.addAttribute("supplierForm", supplier);
        return BUILD_SUPPLIER;
    }

    @RequestMapping(value = "/emp/myview/updateSupplier/{employeeId}", method = RequestMethod.GET)
    public String updateSupplier(@PathVariable String employeeId,
                                 @RequestParam(value = "team", required = true) String team,
                                 @RequestParam(value = "supplierId", required = true) String supplierId,
                                 Model model) {
        LOGGER.info("Supplier detail update page for supplierId." + supplierId);
        Supplier supplier = purchaseService.fetchSupplierDetails(supplierId);
        supplier.setIsUpdate("Y");
        Employee employee = new Employee();
        employee.setEmployeeId(employeeId);
        employee.setEmployeeTeam(team);
        supplier.setIsUpdate("Y");
        model.addAttribute("employee", employee);
        model.addAttribute("supplierForm", supplier);
        return BUILD_SUPPLIER;
    }

    @RequestMapping(value = {"/emp/myview/buildSupplier/createOrUpdateSupplier.do", "/emp/myview/updateSupplier/createOrUpdateSupplier.do"}
            , method = RequestMethod.POST)
    public String createOrEditSupplierDetails(@ModelAttribute("supplierForm") Supplier supplier, Model model,
                                              BindingResult result, SessionStatus status) {
        LOGGER.info("Supplier details save call.");
        try {
            supplierValidator.validate(supplier, result);
            if (result.hasErrors()) {
                model.addAttribute("supplierForm", supplier);
                return BUILD_SUPPLIER;
            } else {
                purchaseService.saveSupplierDetail(supplier);
                model.addAttribute("supplierForm", supplier);
                if ("Y".equalsIgnoreCase(supplier.getIsUpdate())) {
                    model.addAttribute("actionMessage", "Supplier Details updated Successfully");
                } else {
                    model.addAttribute("actionMessage", "Supplier Details saved Successfully");
                }
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving supplier details");
            model.addAttribute("actionMessage", "Failed  to save supplier Details." + e.getMessage());
            return BUILD_SUPPLIER;
        }
        return BUILD_SUPPLIER;
    }


    @RequestMapping(value = "/emp/myview/supplierQuoteDetails/{projName}", method = RequestMethod.GET)
    public String supplierQuoteDetails(@PathVariable String projName,
                                       @RequestParam(value = "itemName", required = true) String itemName,
                                       @RequestParam(value = "itemType", required = true) String itemType,
                                       @RequestParam(value = "itemQty", required = true) String itemQty,
                                       @RequestParam(value = "status", required = true) String status,
                                       @RequestParam(value = "employeeId", required = true) String employeeId,
                                       Model model) {
        LOGGER.info("Supplier detail update page for supplierId." + itemName);
        QuoteDetails quoteDetails = new QuoteDetails();
        model.addAttribute("itemName", itemName);
        model.addAttribute("projName", projName);
        model.addAttribute("itemType", itemType);
        model.addAttribute("itemQty", itemQty);
        Employee employee = employeeService.getEmployeeDetails(employeeId);
        List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = purchaseService.getSupplierQuoteDetails(projName, itemType, itemName);
        if (status.equalsIgnoreCase(Constants.PURCHASE_PENDING_APPROVAL)) {
            quoteDetails.setSubmittedForApproval("Y");
        }
        quoteDetails.setSupplierQuoteDetails(supplierQuoteDetails);
        quoteDetails.setEmployeeId(employeeId);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(supplierQuoteDetails, new TypeToken<List<QuoteDetails.SupplierQuoteDetails>>() {
        }.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        quoteDetails.setQuoteDetailsValue(jsonArray.toString());
        model.addAttribute("employeeobj", employee);
        model.addAttribute("quoteDetailsForm", quoteDetails);
        return SUPPLIER_QUOTE_DETAILS;
    }


   /* @RequestMapping(value = "/emp/myview/viewSupplierDetails/{projName}", method = RequestMethod.GET)
    public String viewSupplierQuoteDetails(@PathVariable String projName,
                                           @RequestParam(value = "itemName", required = true) String itemName,
                                           Model model) {
        LOGGER.info("Supplier detail update page for supplierId." + itemName);
        QuoteDetails quoteDetails = new QuoteDetails();
        model.addAttribute("itemName", itemName);
        model.addAttribute("projName", projName);
        List<QuoteDetails.SupplierQuoteDetails> purchaseList = purchaseService.getPurchaseSupplierDetails(projName, itemName, APPROVED);
        quoteDetails.setSupplierQuoteDetails(purchaseList);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(purchaseList, new TypeToken<List<QuoteDetails.SupplierQuoteDetails>>() {
        }.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        quoteDetails.setQuoteDetailsValue(jsonArray.toString());
        model.addAttribute("quoteDetailsForm", quoteDetails);
        return VIEW_SUPPLIER_QUOTE_DETAILS;
    }
*/

    @RequestMapping(value = "/emp/myview/viewPurchaseDetails/{projName}", method = RequestMethod.GET)
    public String viewPurchaseDetails(@PathVariable String projName,
                                      @RequestParam(value = "itemName", required = true) String itemName,
                                      @RequestParam(value = "itemType", required = true) String itemType,
                                      @RequestParam(value = "supplierName", required = true) String supplierName,
                                      @RequestParam(value = "employeeId", required = true) String employeeId,
                                      Model model) {
        LOGGER.info("Supplier detail update page for supplierId." + itemName);
        QuoteDetails quoteDetails = new QuoteDetails();
        quoteDetails.setProjName(projName);
        quoteDetails.setItemName(itemName);
        quoteDetails.setItemType(itemType);
        quoteDetails.setEmployeeId(employeeId);
        QuoteDetails.SupplierQuoteDetails supplierDetails = purchaseService.getSupplierDetails(projName, itemName, itemType, supplierName);

        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(supplierDetails, new TypeToken<QuoteDetails.SupplierQuoteDetails>() {
        }.getType());
        quoteDetails.setQuoteDetailsValue(element.toString());
        model.addAttribute("supplierDetails", supplierDetails);
        model.addAttribute("quoteDetailsForm", quoteDetails);
        return VIEW_SUPPLIER_QUOTE_DETAILS;
    }


    @RequestMapping(value = "/emp/myview/dispatchTransaction/getSupplierDetails.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Supplier> getItemNamesInStore(
            HttpServletRequest httpServletRequest) {
        String supplierAliasName = httpServletRequest.getParameter("supplierAliasName");
        return purchaseService.fetchSupplierDetail(supplierAliasName);
    }


    @RequestMapping(value = "/emp/myview/supplierQuoteDetails/saveQuoteDetails.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData saveQuoteDetails(
            @RequestBody QuoteDetails quoteDetails, Model model,
            SessionStatus status) {
        JsonData jsonData = new JsonData();
        LOGGER.info("Store Controller : saveQuoteDetails()");
        String result = "Quote Details saved successfully";
        try {
            validateAndStoreQuoteDetails(quoteDetails, PENDING_PURCHASE);
            model.addAttribute("successMessage",
                    "Quote Details saved successfully");
        } catch (Exception e) {
            jsonData.setData(e.getMessage());
            return jsonData;
        }
        jsonData.setSuccess(true);
        jsonData.setData(result);
        return jsonData;
    }


    @RequestMapping(value = "/emp/myview/supplierQuoteDetails/submitForApproval.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData submitForApproval(
            @RequestBody QuoteDetails quoteDetails, Model model,
            SessionStatus status) {
        JsonData jsonData = new JsonData();
        LOGGER.info("Store Controller : submitForApproval()");
        String result = "Submitted for Approval";
        try {
            quoteDetails.setSubmittedForApproval("Y");
            validateAndStoreQuoteDetails(quoteDetails, PURCHASE_PENDING_APPROVAL);
            model.addAttribute("successMessage",
                    "Submitted for Approval");
            jsonData.setSuccess(true);
        } catch (Exception e) {
            result = e.getMessage();
        }
        jsonData.setData(result);
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/supplierQuoteDetails/supplierApproval.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData supplierApproval(
            @RequestBody QuoteDetails quoteDetails, Model model,
            SessionStatus status) {
        JsonData jsonData = new JsonData();
        LOGGER.info("Purchase Controller : submitForApproval()");
        String result = "Supplier Approved";
        try {
            quoteDetails.setSubmittedForApproval("Y");
            updateSupplierDetails(quoteDetails, Constants.APPROVED);
            model.addAttribute("successMessage",
                    "Supplier Approved");
        } catch (Exception e) {
            e.printStackTrace();
            jsonData.setData(e.getMessage());
            return jsonData;
        }
        jsonData.setSuccess(true);
        jsonData.setData(result);
        return jsonData;
    }


    @RequestMapping(value = "/emp/myview/supplierQuoteDetails/rejectApproval.do", method = RequestMethod.POST)
    @ResponseBody
    public JsonData rejectApproval(
            HttpServletRequest request, HttpServletResponse response) {
        JsonData jsonData = new JsonData();
        String projName = request.getParameter("projName");
        String itemType = request.getParameter("itemType");
        String itemName = request.getParameter("itemName");
        LOGGER.info("Purchase Controller : rejectApproval()");
        String result = "Supplier Details rejected successfully";
        try {
            purchaseService.rejectSuppliers(projName, itemType, itemName);
            jsonData.setSuccess(true);
        } catch (Exception e) {
            result = e.getMessage();
        }
        jsonData.setData(result);
        return jsonData;
    }

    @RequestMapping(value = "/emp/myview/viewPurchaseDetails/savePurchaseDetails.do", method = RequestMethod.POST)
    public String savePurchaseDetails(
            @ModelAttribute("quoteDetailsForm") QuoteDetails quoteDetailsForm,
            Model model, BindingResult result, SessionStatus status)
            throws JsonParseException, JsonMappingException, IOException {
        try {
            model.addAttribute("quoteDetailsForm", quoteDetailsForm);
            quoteDetailsForm.setSubmittedForApproval("A");
            supplierValidator.validate(quoteDetailsForm, result, "SAVE");
            ObjectMapper mapper = new ObjectMapper();
            SupplierQuoteDetails supplierQuoteDetails = mapper.readValue(quoteDetailsForm.getQuoteDetailsValue(), SupplierQuoteDetails.class);
            model.addAttribute("supplierDetails", supplierQuoteDetails);
            quoteDetailsForm.setSupplierQuoteDetails(Arrays.asList(supplierQuoteDetails));
            if (!result.hasErrors()) {
                model.addAttribute("actionMessage",
                        "Purchase Indent raised Successfully");
                purchaseService.updateSupplierDetails(quoteDetailsForm, Constants.PURCHASED);
            } else {
                return VIEW_SUPPLIER_QUOTE_DETAILS;
            }
        } catch (Exception e) {
            LOGGER.error("Error occurred while saving supplier details");
            model.addAttribute("actionMessage",
                    "Failed  to save supplier Details." + e.getMessage());
            return VIEW_SUPPLIER_QUOTE_DETAILS;
        }

        return VIEW_SUPPLIER_QUOTE_DETAILS;
    }

    private void validateAndStoreQuoteDetails(QuoteDetails quoteDetails, String status) throws java.io.IOException, ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = mapper
                .readValue(
                        quoteDetails.getQuoteDetailsValue(),
                        mapper.getTypeFactory().constructCollectionType(
                                List.class,
                                QuoteDetails.SupplierQuoteDetails.class));
        quoteDetails.setSupplierQuoteDetails(supplierQuoteDetails);
        supplierValidator.validate(quoteDetails);
        purchaseService.saveSupplierQuoteDetails(quoteDetails, status);
    }

    private void updateSupplierDetails(QuoteDetails quoteDetails, String status) throws java.io.IOException, ValidationException {
        ObjectMapper mapper = new ObjectMapper();
        List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = mapper
                .readValue(
                        quoteDetails.getQuoteDetailsValue(),
                        mapper.getTypeFactory().constructCollectionType(
                                List.class,
                                QuoteDetails.SupplierQuoteDetails.class));
        quoteDetails.setSupplierQuoteDetails(supplierQuoteDetails);
        supplierValidator.validateSupplier(quoteDetails);
        purchaseService.updateSupplierDetails(quoteDetails, status);
    }
}
