package com.psk.pms.service;

import java.util.List;

import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreService {
	
    void saveStoreDetail(StoreDetail storeDetail);

    List<StoreDetail> getStoreDetails(int projId);
    
    List<String> getItemNamesInStore(String projId);
}
