package com.psk.pms.service;

import java.util.List;

import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreService {
	
    void saveStoreDetail(StoreDetail storeDetail);

    List<StoreDetail> getStoreDetails(int projId);

    List<String> getSelectedUser(String teamName, String projectId);
    
    List<StockDetail> getItemNamesInStore(String projId, String itemName);
    
    List<StockDetail>  getItemsToReturn(String projId, String itemName, String fieldUser);

    List<StockDetail> getStockDetails(int projId);

    String getItemQuantityInStock(String projId, String itemName);
    
    String validateFieldUserForReturn(String projId, String fieldUser);

    void saveDispatchedDetail(DispatchDetail dispatchDetail);

    List<DispatchDetail> getDispatchedDetails(DispatchDetail dispatchDetail);
    
    void saveReturnedDetail(DispatchDetail dispatchDetail);
}
