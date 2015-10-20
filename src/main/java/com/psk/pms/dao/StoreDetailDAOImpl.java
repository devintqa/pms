package com.psk.pms.dao;

import com.psk.pms.Constants;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StockDetail;
import com.psk.pms.model.StoreDetail;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.utils.DateFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.psk.pms.Constants.DISPATCHED;
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
                storeDetail.getItemType(), storeDetail.getItemName(),
                storeDetail.getSupplierName(), storeDetail.getVehicleNumber(),
                storeDetail.getRecievedQuantity(),
                storeDetail.getSqlRecievedDate(), storeDetail.getComments());
    }

    @Override
    public List<StoreDetail> getStoreDetails(int projId) {
        List<StoreDetail> storeDetails = new ArrayList<StoreDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                GET_STORE_DETAILS, projId);
        for (Map<String, Object> row : rows) {
            storeDetails.add(buildStoreDetails(row));
        }
        return storeDetails;
    }

    @Override
    public void saveStockDetail(StoreDetail storeDetail) {
        jdbcTemplate.update(CREATE_STOCK_DETAILS, storeDetail.getProjId(),
                storeDetail.getItemName(), storeDetail.getRecievedQuantity());
    }

    @Override
    public List<StockDetail> getStockDetails(int projId, String itemName) {
        List<StockDetail> stockDetails = new ArrayList<StockDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                GET_STOCK_DETAILS, projId, itemName);
        for (Map<String, Object> row : rows) {
            stockDetails.add(buildStockDetails(row));
        }
        return stockDetails;
    }

    @Override
    public List<DispatchDetail> getDispatchedDetails(
            DispatchDetail dispatchDetail) {
        List<DispatchDetail> dispatchDetails = new ArrayList<DispatchDetail>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                GET_DISPATCH_DETAILS, dispatchDetail.getProjId());
        for (Map<String, Object> row : rows) {
            dispatchDetails.add(buildDispatchDetail(row));
        }
        return dispatchDetails;
    }

    @Override
    public void saveDispatchedDetails(final DispatchDetail dispatchDetail,
                                      final String dispatched) {
        jdbcTemplate.batchUpdate(CREATE_TRANSACTION_DETAILS,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        DispatchDetail.DispatchItems dispatchItem = dispatchDetail
                                .getDispatchItems().get(i);
                        ps.setInt(1, dispatchDetail.getProjId());
                        ps.setString(2, dispatchItem.getItemName());
                        ps.setDate(3, dispatchDetail.getSqlDispatchedDate());
                        ps.setString(4, dispatchDetail.getFieldUser());
                        ps.setString(5, dispatched);
                        ps.setString(6, dispatchItem.getRequestedQuantity());
                    }

                    @Override
                    public int getBatchSize() {
                        return dispatchDetail.getDispatchItems().size();
                    }
                });
    }

    @Override
    public void saveReturnedDetails(
            final DispatchDetail dispatchDetail,
            final String returned) {
        jdbcTemplate.batchUpdate(CREATE_TRANSACTION_DETAILS,
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i)
                            throws SQLException {
                        DispatchDetail.DispatchItems returnItem = dispatchDetail
                                .getDispatchItems().get(i);
                        ps.setInt(1, dispatchDetail.getProjId());
                        ps.setString(2, returnItem.getItemName());
                        ps.setDate(3,
                                dispatchDetail.getSqlReturnedDate());
                        ps.setString(4, dispatchDetail.getFieldUser());
                        ps.setString(5, returned);
                        ps.setString(6, returnItem.getReturnedQuantity());
                    }

                    @Override
                    public int getBatchSize() {
                        return dispatchDetail.getDispatchItems().size();
                    }
                });

    }

    @Override
	public List<StockDetail> getItemNamesInStore(String projectId,
			String itemName) {
		List<StockDetail> itemNamesInStock = new ArrayList<>();
		String sql;
		if (!"".equals(itemName)) {
			sql = "select s.projectId, s.itemname, s.totalQuantity, sum(d.Quantity) DispatchedQuantity from stockDetail s,"
					+"dispatchdetail d where s.projectId ='"+ projectId+"'  and s.itemName LIKE '%"+itemName+"%' "
 					+"and s.ItemName = d.ItemName and  d.dispatchdesc = 'Dispatched'";
			List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
			for (Map<String, Object> row : rows) {
				itemNamesInStock.add(buildStockDetails(row));
			}
		}
		return itemNamesInStock;
	}

    @Override
    public void updateStockDetail(int projId, String itemName,
                                  String totalQuantity) {
        jdbcTemplate.update(UPDATE_STOCK_DETAILS, totalQuantity, projId,
                itemName);
    }

    private StockDetail buildStockDetails(Map<String, Object> row) {
        StockDetail stockDetail = new StockDetail();
        stockDetail.setItemName((String) row.get("itemName"));
        stockDetail.setProjId((Integer) row.get("projectId"));
        stockDetail.setTotalQuantity((String) row.get("totalQuantity"));
        stockDetail.setDispatchedQuantity(String.valueOf(row.get("dispatchedQuantity")));
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
        detail.setRecievedDate(DateFormatter.getStringDate(recievedDate,
                formatter));
        return detail;
    }

    private DispatchDetail buildDispatchDetail(Map<String, Object> row) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        DispatchDetail dispatchDetail = new DispatchDetail();
        dispatchDetail.setItemName((String) row.get("itemName"));
        Double dispatchedQty = (Double) row.get("Dispatched_Qty");
        Double returnedQty = (Double) row.get("Returned_Qty");
        dispatchDetail.setRequestedQuantity(  String.valueOf(dispatchedQty));
        dispatchDetail.setReturnedQuantity(  String.valueOf(returnedQty));
        dispatchDetail.setFieldUser((String) row.get("fieldUser"));
        Date recievedDate = (Date) row.get("dispactchedDate");
        dispatchDetail.setDispatchedDate(DateFormatter.getStringDate(recievedDate,
                formatter));
        return dispatchDetail;
    }

}
