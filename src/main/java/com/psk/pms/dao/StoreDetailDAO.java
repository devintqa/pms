package com.psk.pms.dao;

import java.util.List;

import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreDetailDAO {
	void saveStoreDetail(StoreDetail storeDetail);

	List<StoreDetail> getStoreDetails(int projId);

	void saveStockDetail(StoreDetail storeDetail);

	List<StockDetail> getStockDetails(int projId, String itemName);

	void updateStockDetail(int projId, String itemName, String totalQuantity);

	List<StockDetail> getStockDetails(int projId);

	List<StockDetail> getItemsToReturn(String projId, 
			String fieldUser);

	List<StockDetail> getItemNamesInStore(String projId, String itemName);

	void saveDispatchedDetails(DispatchDetail dispatchDetail, String dispatched);

	List<DispatchDetail> getDispatchedDetails(DispatchDetail dispatchDetail);

	void saveReturnedDetails(DispatchDetail dispatchDetail, String returned);

	String validateFieldUserForReturn(String projId, String fieldUser);
	
	void updateSupplierQuoteDetailStatus(StoreDetail storeDetail, String status);
	
	Integer isRecordExists(String attribute);

    boolean isPendingToReceive(String aliasProjName);

    void updateIndentDescStatusForStore(String indentStatus, String itemName, String itemType, Integer projId);
    
    StoreDetail getStoreDetails( String itemName, String itemType, String supplierName, String brandName, Integer projId);
}
