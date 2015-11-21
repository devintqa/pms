package com.psk.pms.dao;

import static com.psk.pms.dao.PmsMasterQuery.ALIAS_SUPPLIER_NAME_EXIST;
import static com.psk.pms.dao.PmsMasterQuery.CREATE_QUOTE_DETAILS;
import static com.psk.pms.dao.PmsMasterQuery.DELETE_SUPPLIER_DETAIL;
import static com.psk.pms.dao.PmsMasterQuery.DELETE_SUPPLIER_QUOTE_DETAILS;
import static com.psk.pms.dao.PmsMasterQuery.GET_SUPPLIER_DETAIL;
import static com.psk.pms.dao.PmsMasterQuery.GET_SUPPLIER_DETAILS;
import static com.psk.pms.dao.PmsMasterQuery.GET_SUPPLIER_QUOTE_DETAILS;
import static com.psk.pms.dao.PmsMasterQuery.INSERT_SUPPLIER_DETAIL;
import static com.psk.pms.dao.PmsMasterQuery.UPDATE_INDENT_DESC_STATUS;
import static com.psk.pms.dao.PmsMasterQuery.UPDATE_SUPPLIER_DETAIL;
import static com.psk.pms.dao.PmsMasterQuery.UPDATE_SUPPLIER_QUOTE_DETAILS;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.model.Supplier;

public class PurchaseDAOImpl implements PurchaseDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    ResultTransformer transformer = new ResultTransformer();

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
                SupplierQuoteDetails supplierQuoteDetails = quoteDetails.getSupplierQuoteDetails().get(i);
                ps.setString(1, quoteDetails.getProjName());
                ps.setString(2, quoteDetails.getItemName());
                ps.setString(3, quoteDetails.getItemQty());
                ps.setString(4, quoteDetails.getItemType());
                ps.setString(5, supplierQuoteDetails.getSupplierAliasName());
                ps.setString(6, supplierQuoteDetails.getEmailAddress());
                ps.setString(7, supplierQuoteDetails.getPhoneNumber());
                ps.setString(8, supplierQuoteDetails.getQuotedPrice());
                ps.setString(9, status);
            }

            @Override
            public int getBatchSize() {
                return quoteDetails.getSupplierQuoteDetails().size();
            }
        });
    }

    @Override
    public void updateSupplierDetails(final QuoteDetails quoteDetails, final String status) {
        jdbcTemplate.batchUpdate(UPDATE_SUPPLIER_QUOTE_DETAILS, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                SupplierQuoteDetails supplierQuoteDetails = quoteDetails.getSupplierQuoteDetails().get(i);
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                Date temp = null;

                try {
                    if (null != quoteDetails.getTentativeDeliveryDate()) {
                        String dateInString = quoteDetails.getTentativeDeliveryDate();
                        java.util.Date date = formatter.parse(dateInString);
                        temp = new java.sql.Date(date.getTime());
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                ps.setString(1, supplierQuoteDetails.getItemQty());
                ps.setString(2, status);
                ps.setDate(3, temp);
                ps.setString(4, StringUtils.isNullOrEmpty(quoteDetails.getComments()) ? null : quoteDetails.getComments());
                ps.setString(5, quoteDetails.getItemName());
                ps.setString(6, supplierQuoteDetails.getSupplierAliasName());
                ps.setString(7, quoteDetails.getProjName());

            }

            @Override
            public int getBatchSize() {
                return quoteDetails.getSupplierQuoteDetails().size();
            }
        });
    }


    @Override
    public List<SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName) {
        List<SupplierQuoteDetails> supplierQuoteDetails = new ArrayList<>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_SUPPLIER_QUOTE_DETAILS, projName, itemType, itemName);
        for (Map<String, Object> row : rows) {
            supplierQuoteDetails.add(buildSupplierQuoteDetails(row));
        }
        return supplierQuoteDetails;
    }

    @Override
    public void deleteSupplierQuoteDetails(String projName, String itemType, String itemName) {
        jdbcTemplate.update(DELETE_SUPPLIER_QUOTE_DETAILS, projName, itemType, itemName);
    }

    @Override
    public void updateIndentDescStatus(String status, String itemName, String itemType, String fromStatus, Integer projectId) {
        jdbcTemplate.update(UPDATE_INDENT_DESC_STATUS, status, itemName, itemType, fromStatus, projectId);
    }


    @Override
    public Integer getProjectId(String projName) {
        String sql = "select projId from project where aliasProjName=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{projName}, Integer.class);
    }

    private SupplierQuoteDetails buildSupplierQuoteDetails(Map<String, Object> row) {
        SupplierQuoteDetails supplierQuoteDetail = new SupplierQuoteDetails();
        supplierQuoteDetail.setSupplierAliasName((String) row.get("SupplierAliasName"));
        supplierQuoteDetail.setEmailAddress((String) row.get("emailAddress"));
        supplierQuoteDetail.setPhoneNumber((String) row.get("PhoneNumber"));
        supplierQuoteDetail.setSupplierQuoteStatus((String) row.get("supplierQuoteStatus"));
        Object quotePrice = row.get("quotePrice");
        supplierQuoteDetail.setQuotedPrice(quotePrice.toString());
        return supplierQuoteDetail;
    }


    @Override
    public List<SupplierQuoteDetails> getPurchaseListByStatus(String status, String empId) {
        List<SupplierQuoteDetails> purchaseList = new ArrayList<SupplierQuoteDetails>();
        String sql = null;
        if (null != status) {
            sql = "select * from supplierquotedetails where supplierQuoteStatus= ?\n" +
                    "and AliasProjName in (select aliasProjName from project where projId in\n" +
                    " (select projectId from authoriseproject where empId = ?)) group by SupplierAliasName";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, status, empId);

        for (Map<String, Object> row : rows) {
            SupplierQuoteDetails supplier = transformer.buildSupplierList(row);
            supplier.setEmailAddress((String) row.get("emailAddress"));
            supplier.setPhoneNumber((String) row.get("PhoneNumber"));
            BigDecimal quotePrice = (BigDecimal) row.get("quotePrice");
            supplier.setQuotedPrice(String.valueOf(quotePrice));
            supplier.setSupplierAliasName((String) row.get("supplierAliasName"));
            purchaseList.add(supplier);
        }
        return purchaseList;
    }


    @Override
    public List<SupplierQuoteDetails> getPurchaseSupplierDetails(String projName, String itemName, String status) {
        List<SupplierQuoteDetails> purchaseList = new ArrayList<>();
        String sql = null;
        if (null != status) {
            sql = "select * from supplierquotedetails where AliasProjName = ? and itemName= ? and supplierQuoteStatus =?";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, projName, itemName, status);

        for (Map<String, Object> row : rows) {
            SupplierQuoteDetails supplier = transformer.buildSupplierList(row);
            supplier.setEmailAddress((String) row.get("emailAddress"));
            supplier.setPhoneNumber((String) row.get("PhoneNumber"));
            BigDecimal quotePrice = (BigDecimal) row.get("quotePrice");
            supplier.setQuotedPrice(String.valueOf(quotePrice));
            supplier.setSupplierAliasName((String) row.get("supplierAliasName"));
            purchaseList.add(supplier);
        }
        return purchaseList;
    }


    @Override
    public List<SupplierQuoteDetails> getSupplierByStatus(String supplierStatus) {
        List<SupplierQuoteDetails> supplierList = new ArrayList<>();
        String sql = null;
        if (null != supplierStatus) {
            sql = "SELECT p.aliasProjName, idi.ItemName, idi.ItemType, sum(idi.ItemQty) as ItemQty, idi.indentitemstatus as supplierquotestatus FROM indentdescitem idi,"
                    + "indentdesc id, indent i, project p where idi.indentDescId = id.indentdescid and p.ProjId= i.ProjId and idi.indentitemstatus ='" + supplierStatus + "'"
                    + "and id.IndentId = i.indentid group by idi.ItemName,p.ProjId";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            supplierList.add(transformer.buildSupplierList(row));
        }
        return supplierList;
    }

    @Override
    public SupplierQuoteDetails getSupplierDetails(String projName, String itemName, String itemType, String supplierName) {
        Map<String, Object> rows = jdbcTemplate.queryForMap(GET_SUPPLIER_DETAIL, projName, itemType, itemName, supplierName);
        QuoteDetails.SupplierQuoteDetails detail = buildSupplierQuoteDetails(rows);
        detail.setItemQty(rows.get("itemQty").toString());
        return detail;
    }
}