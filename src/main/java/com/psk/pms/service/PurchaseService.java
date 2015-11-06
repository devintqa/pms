package com.psk.pms.service;

import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;

import java.util.List;

public interface PurchaseService {

    Supplier fetchSupplierDetails(String supplierId);

    void deleteSupplier(String supplier);

    void saveSupplierDetail(Supplier supplier);

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

    List<Supplier> fetchSupplierDetails();

    List<Supplier> fetchSupplierDetail(String supplierAliasName);

    void saveSupplierQuoteDetails(QuoteDetails quoteDetails);

    List<QuoteDetails.SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName);
}
