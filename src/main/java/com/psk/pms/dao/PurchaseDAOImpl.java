package com.psk.pms.dao;

import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.PreparedStatement;
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
        return (Supplier) jdbcTemplate.queryForObject(GET_SUPPLIER_DETAIL, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getInt("SupplierId"));
                supplier.setTinNumber(rs.getString("TINNumber"));
                supplier.setReason(rs.getString("Reason"));
                supplier.setName(rs.getString("SupplierName"));
                supplier.setAliasName(rs.getString("SupplierAliasName"));
                supplier.setPhoneNumber(rs.getString("PhoneNumber"));
                supplier.setEmailAddress(rs.getString("Email"));
                supplier.setDescription(rs.getString("SupplierDescription"));
                return supplier;
            }
        });
    }

    @Override
    public void saveSupplierDetail(Supplier supplierDetail) {
        if ("Y".equalsIgnoreCase(supplierDetail.getIsUpdate())) {
            LOGGER.info("updating  supplier details for supplierid:" + supplierDetail.getSupplierId());
            jdbcTemplate.update(UPDATE_SUPPLIER_DETAIL, supplierDetail.getTinNumber(), supplierDetail.getReason(),
                    supplierDetail.getName(), supplierDetail.getAliasName(), supplierDetail.getPhoneNumber(),
                    supplierDetail.getEmailAddress(), supplierDetail.getDescription(), supplierDetail.getLastUpdatedBy(),
                    supplierDetail.getLastUpdatedAt(), supplierDetail.getSupplierId());
        } else {
            LOGGER.info("saving new supplier details supplier Nme" + supplierDetail.getAliasName());
            jdbcTemplate.update(INSERT_SUPPLIER_DETAIL, supplierDetail.getTinNumber(), supplierDetail.getReason(),
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

    @Override
    public List<Supplier> fetchSupplierDetails(String supplierName) {
        List<Supplier> suppliers = new ArrayList<Supplier>();
        LOGGER.info("Fetching all supplier details " + supplierName);
        String sql;
        if (!"".equalsIgnoreCase(supplierName)) {
            sql = "select * from supplierdetails where SupplierAliasName LIKE '%" + supplierName + "%'";
            List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
            for (Map<String, Object> row : rows) {
                suppliers.add(buildSupplierDetails(row));
            }
        }
        return suppliers;
    }

    private Supplier buildSupplierDetails(Map<String, Object> row) {
        Supplier supplier = new Supplier();
        supplier.setSupplierId((Integer) row.get("SupplierId"));
        supplier.setTinNumber((String) row.get("TINNumber"));
        supplier.setReason((String) row.get("Reason"));
        supplier.setName((String) row.get("SupplierName"));
        supplier.setAliasName((String) row.get("SupplierAliasName"));
        supplier.setPhoneNumber((String) row.get("PhoneNumber"));
        supplier.setEmailAddress((String) row.get("Email"));
        supplier.setDescription((String) row.get("SupplierDescription"));
        return supplier;
    }

    @Override
    public boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName) {
        int count = jdbcTemplate.queryForObject(ALIAS_SUPPLIER_NAME_EXIST, Integer.class, aliasSupplierName);
        return count > 0;
    }

    @Override
    public void saveSupplierQuoteDetails(final QuoteDetails quoteDetails, final String status) {
        jdbcTemplate.batchUpdate(CREATE_QUOTE_DETAILS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                QuoteDetails.SupplierQuoteDetails supplierQuoteDetails = quoteDetails.getSupplierQuoteDetails().get(i);
                ps.setString(1, quoteDetails.getProjName());
                ps.setString(2, quoteDetails.getItemName());
                ps.setString(3, quoteDetails.getItemType());
                ps.setString(4, supplierQuoteDetails.getSupplierAliasName());
                ps.setString(5, supplierQuoteDetails.getEmailAddress());
                ps.setString(6, supplierQuoteDetails.getPhoneNumber());
                ps.setString(7, supplierQuoteDetails.getQuotedPrice());
                ps.setString(8, status);
            }

            @Override
            public int getBatchSize() {
                return quoteDetails.getSupplierQuoteDetails().size();
            }
        });
    }


    @Override
    public List<QuoteDetails.SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName) {
        List<QuoteDetails.SupplierQuoteDetails> supplierQuoteDetails = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_SUPPLIER_QUOTE_DETAILS, projName, itemType, itemName);
        for (Map<String, Object> row : rows) {
            supplierQuoteDetails.add(buildSupplierQuoteDetails(row));
        }
        return supplierQuoteDetails;
    }

    @Override
    public void deleteSupplierQuoteDetails(String projName, String itemType, String itemName) {
        jdbcTemplate.update(DELETE_SUPPLIER_QUOTE_DETAILS,projName,itemType,itemName);
    }

    @Override
    public void updateIndentDescStatus(String status, String itemName, String itemType, Integer projectId) {
     jdbcTemplate.update(UPDATE_INDENT_DESC_STATUS,status,itemName,itemType,projectId);
    }


    @Override
    public Integer getProjectId(String projName) {
        String sql= "select projId from project where aliasProjName=?";
       return jdbcTemplate.queryForObject(sql,new Object[]{projName},Integer.class);
    }

    private QuoteDetails.SupplierQuoteDetails buildSupplierQuoteDetails(Map<String, Object> row) {
        QuoteDetails.SupplierQuoteDetails supplierQuoteDetail = new QuoteDetails.SupplierQuoteDetails();
        supplierQuoteDetail.setSupplierAliasName((String) row.get("SupplierAliasName"));
        supplierQuoteDetail.setEmailAddress((String) row.get("emailAddress"));
        supplierQuoteDetail.setPhoneNumber((String) row.get("PhoneNumber"));
        supplierQuoteDetail.setSupplierQuoteStatus((String) row.get("supplierQuoteStatus"));
        Object quotePrice = row.get("quotePrice");
        supplierQuoteDetail.setQuotedPrice( quotePrice.toString());
        return supplierQuoteDetail;
    }
}