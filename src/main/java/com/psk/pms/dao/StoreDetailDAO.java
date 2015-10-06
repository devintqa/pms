package com.psk.pms.dao;

import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;

import java.util.List;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreDetailDAO {
    void saveStoreDetail(StoreDetail storeDetail);

    List<StoreDetail> getStoreDetails(int projId);

    void saveStockDetail(StoreDetail storeDetail);

    List<StockDetail> getStockDetails(int projId, String itemName);

    void updateStockDetail(int projId, String itemName, String totalQuantity);

	List<String> getItemNamesInStore(String projId);

}
