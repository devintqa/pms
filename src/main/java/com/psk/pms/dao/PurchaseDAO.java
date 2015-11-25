package com.psk.pms.dao;

import java.sql.Date;
import java.util.List;

import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.model.Supplier;

public interface PurchaseDAO {

    Supplier fetchSupplierDetail(String supplierId);

    void saveSupplierDetail(Supplier supplierDetail);

    void deleteSupplierDetail(String supplierId);

    List<Supplier> fetchSupplierDetails();

    List<Supplier> fetchSupplierDetails(String supplierName);

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

    void saveSupplierQuoteDetails(QuoteDetails quoteDetails, String status);
    
    void updateSupplierDetails(QuoteDetails quoteDetails, String status);

    List<SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName);

    void deleteSupplierQuoteDetails(String projName, String itemType, String itemName);

    void updateIndentDescStatus(String status, String itemName, String itemType, String fromStatus, Integer projectId);

    Integer getProjectId(String projName);

    List<SupplierQuoteDetails> getPurchaseListByStatus(String status, String empId);

    List<SupplierQuoteDetails> getPurchaseSupplierDetails(String projName, String itemName, String status);

    List<SupplierQuoteDetails> getSupplierByStatus(String supplierStatus);

    SupplierQuoteDetails getSupplierDetails(String projName, String itemName, String itemType, String supplierName, String brandName);

    void updateIndentDescStatusForPurchase(String indentStatus, String itemName, String itemType, Integer projectId);

    boolean isPendingPurchase(String projName);

    void updateIndentStatus(String purchased, Date todayDate, String employeeId, Integer projectId);

    boolean isTinNumberExists(String tinNumber);

	List<SupplierQuoteDetails> getPurchasesByStatus(String purchaseStatus);

	SupplierQuoteDetails getSupplierQuoteDetailsByStatus(String projName,
                                                         String itemName, String supplierName, String status, String brandName);
}
