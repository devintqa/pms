package com.psk.pms.dao;

import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.utils.DateFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.psk.pms.dao.PmsMasterQuery.*;

/**
 * Created by Sony on 26-09-2015.
 */
public class StoreDetailDAOImpl implements StoreDetailDAO {


    @Qualifier("jdbcTemplate")
    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public void saveStoreDetail(StoreDetail storeDetail) {
        jdbcTemplate.update(CREATE_STORE_DETAIL, storeDetail.getProjId(),
                storeDetail.getItemType(),
                storeDetail.getItemName(),
                storeDetail.getSupplierName(),
                storeDetail.getVehicleNumber(),
                storeDetail.getRecievedQuantity(),
                storeDetail.getSqlRecievedDate(),
                storeDetail.getComments());
    }

    @Override
    public List<StoreDetail> getStoreDetails(int projId) {
        List<StoreDetail> storeDetails = new ArrayList<StoreDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_STORE_DETAILS, projId);
        for (Map<String, Object> row : rows) {
            storeDetails.add(buildStoreDetails(row));
        }
        return storeDetails;
    }

    @Override
    public void saveStockDetail(StoreDetail storeDetail) {
        jdbcTemplate.update(CREATE_STOCK_DETAILS, storeDetail.getProjId(),
                storeDetail.getItemName(),
                storeDetail.getRecievedQuantity());
    }

    @Override
    public List<StockDetail> getStockDetails(int projId, String itemName) {
        List<StockDetail> stockDetails = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_STOCK_DETAILS, projId, itemName);
        for (Map<String, Object> row : rows) {
            stockDetails.add(buildStockDetails(row));
        }
        return stockDetails;
    }

    @Override
    public List<DispatchDetail> getDispatchedDetails(DispatchDetail dispatchDetail) {
        List<DispatchDetail> dispatchDetails = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_DISPATCH_DETAILS, dispatchDetail.getProjId(), dispatchDetail.getFieldUser());
        for (Map<String, Object> row : rows) {
            dispatchDetails.add(buildDispatchDetail(row));
        }
        return dispatchDetails;
    }



    @Override
    public void saveDispatchedDetails(DispatchDetail dispatchDetail, String dispatched) {
        jdbcTemplate.update(CREATE_DISPATCH_DETAILS, dispatchDetail.getProjId(), dispatchDetail.getItemName()
                , dispatchDetail.getSqlDispatchedDate(), dispatchDetail.getFieldUser(), dispatched,dispatchDetail.getRequestedQuantity());
    }

    @Override
    public List<String> getItemNamesInStore(String projectId ) {
        return jdbcTemplate.queryForList(GET_ITEM_NAMES_STORE, new Object[]{projectId}, String.class);

    }

    @Override
    public void updateStockDetail(int projId, String itemName, String totalQuantity) {
        jdbcTemplate.update(UPDATE_STOCK_DETAILS, totalQuantity, projId, itemName);
    }

    private StockDetail buildStockDetails(Map<String, Object> row) {
        StockDetail stockDetail = new StockDetail();
        stockDetail.setItemName((String) row.get("itemName"));
        stockDetail.setProjId((Integer) row.get("projectId"));
        stockDetail.setTotalQuantity((String) row.get("totalQuantity"));
        return stockDetail;
    }

    private StoreDetail buildStoreDetails(Map<String, Object> row) {
        StoreDetail detail = new StoreDetail();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        detail.setItemType((String) row.get("itemType"));
        detail.setItemName((String) row.get("itemName"));
        detail.setSupplierName((String) row.get("supplierName"));
        detail.setRecievedQuantity((String) row.get("quantityRecieved"));
        Date recievedDate = (Date) row.get("recievedDate");
        detail.setSqlRecievedDate(recievedDate);
        detail.setRecievedDate(DateFormatter.getStringDate(recievedDate, formatter));
        return detail;
    }

    private DispatchDetail buildDispatchDetail(Map<String, Object> row) {
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setItemName((String) row.get("itemName"));
        dispatchDetail.setRequestedQuantity((String) row.get("quantity"));
        dispatchDetail.setDescription((String) row.get("dispatchDesc"));
        return dispatchDetail;
    }

}
