package com.psk.pms.dao;

import com.psk.pms.model.StoreDetail;

import java.util.List;

/**
 * Created by Sony on 26-09-2015.
 */
public interface StoreDetailDAO {
    void saveStoreDetail(StoreDetail storeDetail);

    List<StoreDetail> getStoreDetails(int projId);
}
