package com.psk.pms.controller;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.psk.pms.Constants;
import com.psk.pms.constants.JSPFileNames;
import com.psk.pms.model.*;
import com.psk.pms.service.PurchaseService;
import com.psk.pms.validator.SupplierValidator;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.psk.pms.constants.JSPFileNames.BUILD_SUPPLIER;
import static com.psk.pms.constants.JSPFileNames.SUPPLIER_QUOTE_DETAILS;

@Controller
public class PurchaseController {

    @Autowired
    private PurchaseService purchaseService;

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
                                 Model model) {
        LOGGER.info("Supplier detail update page for supplierId." + itemName);
        QuoteDetails quoteDetails =new QuoteDetails();
        model.addAttribute("itemName",itemName);
        model.addAttribute("projName",projName);
        model.addAttribute("itemType",itemType);
        List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = purchaseService.getSupplierQuoteDetails(projName, itemType, itemName);
        quoteDetails.setSupplierQuoteDetails(supplierQuoteDetails);
        Gson gson = new Gson();
        JsonElement element = gson.toJsonTree(supplierQuoteDetails, new TypeToken<List<QuoteDetails.SupplierQuoteDetails>>() {
        }.getType());
        JsonArray jsonArray = element.getAsJsonArray();
        quoteDetails.setQuoteDetailsValue(jsonArray.toString());
        model.addAttribute("quoteDetailsForm",quoteDetails);
        return SUPPLIER_QUOTE_DETAILS;
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
    public String saveDispatchedDetail(
            @RequestBody QuoteDetails quoteDetails, Model model,
            SessionStatus status) {
        LOGGER.info("Store Controller : saveDispatchedDetail()");
        String result = "Quote Details saved successfully";
        try {
            ObjectMapper mapper = new ObjectMapper();
            List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = mapper
                    .readValue(
                            quoteDetails.getQuoteDetailsValue(),
                            mapper.getTypeFactory().constructCollectionType(
                                    List.class,
                                    QuoteDetails.SupplierQuoteDetails.class));
            quoteDetails.setSupplierQuoteDetails(supplierQuoteDetails);
            supplierValidator.validate(quoteDetails);
            purchaseService.saveSupplierQuoteDetails(quoteDetails);
            model.addAttribute("successMessage",
                    "Quote Details saved successfully");
        } catch (Exception e) {
            return e.getMessage();
        }
        return result;
    }
}
