package com.psk.pms.controller;

import com.psk.pms.model.Employee;
import com.psk.pms.model.Supplier;
import com.psk.pms.service.PurchaseService;
import com.psk.pms.validator.SupplierValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

import static com.psk.pms.constants.JSPFileNames.BUILD_SUPPLIER;

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
}
