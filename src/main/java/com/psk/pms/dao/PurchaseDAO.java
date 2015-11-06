package com.psk.pms.dao;

import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;

import java.util.List;

public interface PurchaseDAO {

    Supplier fetchSupplierDetail(String supplierId);

    void saveSupplierDetail(Supplier supplierDetail);

    void deleteSupplierDetail(String supplierId);

    List<Supplier> fetchSupplierDetails();

    List<Supplier> fetchSupplierDetails(String supplierName);

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

    void saveSupplierQuoteDetails(QuoteDetails quoteDetails);

    List<QuoteDetails.SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName);

    void deleteSupplierQuoteDetails(String projName, String itemType, String itemName);
}
