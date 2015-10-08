package com.psk.pms.service;

import java.util.List;

import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StoreDetail;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreService {
	
    void saveStoreDetail(StoreDetail storeDetail);

    List<StoreDetail> getStoreDetails(int projId);

    List<String> getSelectedUser(String teamName, String projectId);
    
    List<String> getItemNamesInStore(String projId);

    String getItemQuantityInStock(String projId, String itemName);

    void saveDispatchedDetail(DispatchDetail dispatchDetail);
}
