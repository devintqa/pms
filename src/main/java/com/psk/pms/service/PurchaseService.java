package com.psk.pms.service;

import com.psk.pms.model.Supplier;

import java.util.List;

public interface PurchaseService {

    Supplier fetchSupplierDetails(String supplierId);

    void deleteSupplier(String supplier);

    void saveSupplierDetail(Supplier supplier);

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

    List<Supplier> fetchSupplierDetails();
}
