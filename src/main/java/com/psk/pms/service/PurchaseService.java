package com.psk.pms.service;

import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;

import java.util.List;

import static com.psk.pms.model.QuoteDetails.*;

public interface PurchaseService {

    Supplier fetchSupplierDetails(String supplierId);

    void deleteSupplier(String supplier);

    void saveSupplierDetail(Supplier supplier);

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

    List<Supplier> fetchSupplierDetails();

    List<Supplier> fetchSupplierDetail(String supplierAliasName);

    void saveSupplierQuoteDetails(QuoteDetails quoteDetails, String status);
    
    void updateSupplierDetails(QuoteDetails quoteDetails, String status);

    List<QuoteDetails.SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName);

    List<QuoteDetails.SupplierQuoteDetails> getPurchaseListByStatus(String status, String empId);

    List<QuoteDetails.SupplierQuoteDetails> getPurchaseSupplierDetails(String projName, String itemName, String approved);

    List<QuoteDetails.SupplierQuoteDetails> getSupplierByStatus(String supplierStatus);

    SupplierQuoteDetails getSupplierDetails(String projName, String itemName, String itemType, String supplierName, String brandName);

    boolean isTinNumberExists(String tinNumber);  void rejectSuppliers(String projName, String itemType, String itemName);
    
    List<QuoteDetails.SupplierQuoteDetails> getPurchasesByStatus(String supplierStatus);
    
    Integer getProjectId(String projName);
    
    SupplierQuoteDetails getSupplierQuoteDetailsByStatus(String projName, String itemName, String supplierName, String status);


  
}
