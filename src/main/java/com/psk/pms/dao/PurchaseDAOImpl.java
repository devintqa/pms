package com.psk.pms.dao;

import com.psk.pms.model.Supplier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.psk.pms.dao.PmsMasterQuery.*;

public class PurchaseDAOImpl implements PurchaseDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = Logger.getLogger(PurchaseDAOImpl.class);

    @Override
    public Supplier fetchSupplierDetail(String supplierId) {
        String GET_SUPPLIER_DETAIL = "select * from supplierdetails where SupplierId = " + supplierId;
        Supplier supplier = (Supplier) jdbcTemplate.queryForObject(GET_SUPPLIER_DETAIL, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt(1));
                supplier.setTinNumber(rs.getString(2));
                supplier.setName(rs.getString(3));
                supplier.setAliasName(rs.getString(4));
                supplier.setPhoneNumber(rs.getString(5));
                supplier.setEmailAddress(rs.getString(6));
                supplier.setDescription(rs.getString(7));
                return supplier;
            }
        });
        return supplier;
    }

    @Override
    public void saveSupplierDetail(Supplier supplierDetail) {
        if ("Y".equalsIgnoreCase(supplierDetail.getIsUpdate())) {
            LOGGER.info("updating  supplier details for supplierid:" + supplierDetail.getSupplierId());
            jdbcTemplate.update(UPDATE_SUPPLIER_DETAIL, supplierDetail.getTinNumber(),
                    supplierDetail.getName(), supplierDetail.getAliasName(), supplierDetail.getPhoneNumber(),
                    supplierDetail.getEmailAddress(), supplierDetail.getDescription(), supplierDetail.getLastUpdatedBy(),
                    supplierDetail.getLastUpdatedAt(), supplierDetail.getSupplierId());
        } else {
            LOGGER.info("saving new supplier details supplier Nme" + supplierDetail.getAliasName());
            jdbcTemplate.update(INSERT_SUPPLIER_DETAIL, supplierDetail.getTinNumber(),
                    supplierDetail.getName(), supplierDetail.getAliasName(), supplierDetail.getPhoneNumber(),
                    supplierDetail.getEmailAddress(), supplierDetail.getDescription(), supplierDetail.getLastUpdatedBy(),
                    supplierDetail.getLastUpdatedAt());
        }
    }

    @Override
    public void deleteSupplierDetail(String supplierId) {
        LOGGER.info("Deleting supplier details for supplierId " + supplierId);
        jdbcTemplate.update(DELETE_SUPPLIER_DETAIL, Integer.parseInt(supplierId));
    }

    @Override
    public List<Supplier> fetchSupplierDetails() {
        List<Supplier> suppliers = new ArrayList<Supplier>();
        LOGGER.info("Fetching all supplier details");
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_SUPPLIER_DETAILS);
        for (Map<String, Object> row : rows) {
            suppliers.add(buildSupplierDetails(row));
        }
        return suppliers;
    }

    private Supplier buildSupplierDetails(Map<String, Object> row) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId((Integer) row.get("SupplierId"));
        supplier.setTinNumber((String) row.get("TINNumber"));
        supplier.setName((String) row.get("SupplierName"));
        supplier.setAliasName((String) row.get("SupplierAliasName"));
        supplier.setPhoneNumber((String) row.get("PhoneNumber"));
        supplier.setEmailAddress((String) row.get("Email"));
        supplier.setDescription((String) row.get("SupplierDescription"));
        return supplier;
    }

    @Override
    public boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName) {
        int count = (Integer) jdbcTemplate.queryForObject(ALIAS_SUPPLIER_NAME_EXIST, Integer.class, aliasSupplierName);
        if (count > 1) {
            return true;
        } else {
            return false;
        }
    }
}