package com.psk.pms.dao;

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

import static com.psk.pms.dao.PmsMasterQuery.CREATE_STORE_DETAIL;
import static com.psk.pms.dao.PmsMasterQuery.GET_STORE_DETAILS;

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

    private StoreDetail buildStoreDetails(Map<String, Object> row) {
        StoreDetail detail = new StoreDetail();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        detail.setItemType((String) row.get("itemType"));
        detail.setItemName((String) row.get("itemName"));
        detail.setSupplierName((String) row.get("supplierName"));
        detail.setRecievedQuantity((String) row.get("quantityRecieved"));
        Date recievedDate = (Date) row.get("recievedDate");
        detail.setSqlRecievedDate(recievedDate);
        detail.setRecievedDate(DateFormatter.getStringDate(recievedDate,formatter));
        return detail;
    }
}
