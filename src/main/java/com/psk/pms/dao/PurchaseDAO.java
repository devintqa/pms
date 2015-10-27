package com.psk.pms.dao;

import com.psk.pms.model.Supplier;

import java.util.List;

public interface PurchaseDAO {

    Supplier fetchSupplierDetail(String supplierId);

    void saveSupplierDetail(Supplier supplierDetail);

    void deleteSupplierDetail(String supplierId);

    List<Supplier> fetchSupplierDetails();

    boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName);

}
