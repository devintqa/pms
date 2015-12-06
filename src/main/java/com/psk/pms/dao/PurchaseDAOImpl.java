package com.psk.pms.dao;

import com.mysql.jdbc.StringUtils;
import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;
import com.psk.pms.model.Supplier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

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

import static com.psk.pms.dao.PmsMasterQuery.*;

public class PurchaseDAOImpl implements PurchaseDAO {

    @Autowired
    JdbcTemplate jdbcTemplate;

    ResultTransformer transformer = new ResultTransformer();

    private static final Logger LOGGER = Logger.getLogger(PurchaseDAOImpl.class);

    @Override
    public Supplier fetchSupplierDetail(String supplierId) {
        String GET_SUPPLIER_DETAIL = "select * from supplierdetails where SupplierId = " + supplierId;
        return buildSupplier(GET_SUPPLIER_DETAIL);
    }

    @Override
    public void saveSupplierDetail(Supplier supplierDetail) {
        if ("Y".equalsIgnoreCase(supplierDetail.getIsUpdate())) {
            LOGGER.info("updating  supplier details for supplierid:" + supplierDetail.getSupplierId());
            jdbcTemplate.update(UPDATE_SUPPLIER_DETAIL, supplierDetail.getTinNumber(), supplierDetail.getReason(),
                    supplierDetail.getName(), supplierDetail.getAliasName(), supplierDetail.getPhoneNumber(),
                    supplierDetail.getEmailAddress(), supplierDetail.getDescription(), supplierDetail.getLastUpdatedBy(),
                    supplierDetail.getSupplierType(), supplierDetail.getLastUpdatedAt(), supplierDetail.getSupplierId());
        } else {
            LOGGER.info("saving new supplier details supplier Nme" + supplierDetail.getAliasName());
            jdbcTemplate.update(INSERT_SUPPLIER_DETAIL, supplierDetail.getTinNumber(), supplierDetail.getReason(),
                    supplierDetail.getName(), supplierDetail.getAliasName(), supplierDetail.getPhoneNumber(),
                    supplierDetail.getEmailAddress(), supplierDetail.getDescription(), supplierDetail.getLastUpdatedBy(),
                    supplierDetail.getLastUpdatedAt(), supplierDetail.getSupplierType());
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
        supplier.setSupplierType((String) row.get("SupplierType"));
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
                ps.setString(9, supplierQuoteDetails.getTotalPrice());
                ps.setString(10, status);
                ps.setString(11, supplierQuoteDetails.getBrandName());
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
                ps.setString(2, supplierQuoteDetails.getTotalPrice());
                ps.setString(3, status);
                ps.setDate(4, temp);
                ps.setString(5, StringUtils.isNullOrEmpty(quoteDetails.getComments()) ? null : quoteDetails.getComments());
                ps.setString(6, quoteDetails.getItemName());
                ps.setString(7, supplierQuoteDetails.getSupplierAliasName());
                ps.setString(8, quoteDetails.getProjName());
                ps.setString(9, supplierQuoteDetails.getBrandName());

            }

            @Override
            public int getBatchSize() {
                return quoteDetails.getSupplierQuoteDetails().size();
            }
        });
    }


    @Override
    public List<SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName) {
        List<SupplierQuoteDetails> supplierQuoteDetails = new ArrayList<SupplierQuoteDetails>();
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(GET_SUPPLIER_QUOTE_DETAILS, projName, itemType, itemName);
        for (Map<String, Object> row : rows) {
            SupplierQuoteDetails detail = buildSupplierQuoteDetails(row);
            detail.setSupplierType((String) row.get("supplierType"));
            supplierQuoteDetails.add(detail);
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
        supplierQuoteDetail.setBrandName((String) row.get("brandName"));
        Object quotePrice = row.get("quotePrice");
        supplierQuoteDetail.setQuotedPrice(quotePrice.toString());
        Object totalPrice = row.get("totalPrice");
        if (null != totalPrice) {
            supplierQuoteDetail.setTotalPrice(totalPrice.toString());
        }
        return supplierQuoteDetail;
    }


    @Override
    public List<SupplierQuoteDetails> getPurchaseListByStatus(String status, String empId) {
        String sql = null;
        if (null != status) {
            sql = "select * from supplierquotedetails where supplierQuoteStatus= ?\n" +
                    "and AliasProjName in (select aliasProjName from project where projId in\n" +
                    " (select projectId from authoriseproject where empId = ?))";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, status, empId);
        return buildPurchaseDetails(rows);
    }


    @Override
    public List<SupplierQuoteDetails> getPurchaseSupplierDetails(String projName, String itemName, String status) {
        String sql = null;
        if (null != status) {
            sql = "select * from supplierQuotedetails where AliasProjName = ? and itemName= ? and supplierQuoteStatus =?";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, projName, itemName, status);
        return buildPurchaseDetails(rows);
    }

    private List<SupplierQuoteDetails> buildPurchaseDetails(List<Map<String, Object>> rows) {
        List<SupplierQuoteDetails> purchaseList = new ArrayList<SupplierQuoteDetails>();
        for (Map<String, Object> row : rows) {
            SupplierQuoteDetails supplier = transformer.buildSupplierList(row);
            supplier.setEmailAddress((String) row.get("emailAddress"));
            supplier.setPhoneNumber((String) row.get("PhoneNumber"));
            BigDecimal quotePrice = (BigDecimal) row.get("quotePrice");
            supplier.setQuotedPrice(String.valueOf(quotePrice));
            supplier.setSupplierAliasName((String) row.get("supplierAliasName"));
            supplier.setBrandName((String) row.get("brandName"));
            supplier.setItemQty((String) row.get("itemQty"));
            purchaseList.add(supplier);
        }
        return purchaseList;
    }


    @Override
    public List<SupplierQuoteDetails> getSupplierByStatus(String supplierStatus) {
        List<SupplierQuoteDetails> supplierList = new ArrayList<SupplierQuoteDetails>();
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
    public SupplierQuoteDetails getSupplierDetails(String projName, String itemName, String supplierName, String brandName) {
        Map<String, Object> rows = jdbcTemplate.queryForMap(GET_SUPPLIER_DETAIL, projName, itemName, supplierName, brandName);
        QuoteDetails.SupplierQuoteDetails detail = buildSupplierQuoteDetails(rows);
        detail.setItemQty(rows.get("itemQty").toString());
        return detail;
    }

    @Override
    public void updateIndentDescStatusForPurchase(String indentStatus, String itemName, String itemType, Integer projectId) {
        jdbcTemplate.update(UPDATE_INDENT_DESC_STATUS_FOR_PURCHASE, indentStatus, itemName, projectId);
    }


    @Override
    public boolean isPendingPurchase(String projName) {
        String sql = "select count(*) from indentdescitem where IndentDescId in (select IndentDescId from indentdesc where IndentId in (\n" +
                "select IndentId from indent where ProjId in (select ProjId from project where AliasProjName= ?))) and IndentItemStatus not in ('PURCHASED')";
        return jdbcTemplate.queryForObject(sql, Integer.class, projName) != 0;
    }


    @Override
    public void updateIndentStatus(String status, Date todayDate, String employeeId, Integer projectId) {
        String updateIndentStatusSql = "UPDATE Indent set Status = ?, LastUpdatedBy = ? ,LastUpdatedAt = ? WHERE projId = ?";
        jdbcTemplate.update(updateIndentStatusSql, status, employeeId, todayDate, projectId);
    }

    @Override
    public boolean isTinNumberExists(String tinNumber) {
        String sql = "select count(*) from supplierdetails where TINNumber = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, tinNumber) != 0;
    }

    private Supplier buildSupplier(String GET_SUPPLIER_DETAIL) {
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
                supplier.setSupplierType(rs.getString("SupplierType"));
                return supplier;
            }
        });
    }

    @Override
    public List<SupplierQuoteDetails> getPurchasesByStatus(String status) {
        List<SupplierQuoteDetails> supplierList = new ArrayList<>();
        String sql = null;
        if (null != status) {
            sql = "select * from supplierQuoteDetails where supplierQuoteStatus = '" + status + "'";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql);
        for (Map<String, Object> row : rows) {
            supplierList.add(transformer.buildSupplierList(row));
        }
        return supplierList;
    }


    @Override
    public SupplierQuoteDetails getSupplierQuoteDetailsByStatus(String projName, String itemName, String supplierName, String status, String brandName) {
        Map<String, Object> rows = jdbcTemplate.queryForMap(GET_SUPPLIER_DETAIL_BY_STATUS, projName, itemName, supplierName, status, brandName);
        return transformer.buildSupplierList(rows);
    }


    @Override
    public Supplier getSupplierDetail(String supplierAliasName) {
        String GET_SUPPLIER_DETAIL = "select * from supplierdetails where SupplierAliasName = '" + supplierAliasName + "'";
        return buildSupplier(GET_SUPPLIER_DETAIL);
    }

    @Override
    public List<SupplierQuoteDetails> getSuppliersForPayment(String status, String empId) {
        String sql = null;
        List<SupplierQuoteDetails> supplierList = new ArrayList<>();
        if (null != status) {
            sql = "select sq.aliasprojName,sq.SupplierAliasName,sq.brandName,sum(sd.quantityRecieved)receivedQty,sq.ItemName,sq.ItemType\n" +
                    " from supplierquotedetails sq,storedetail sd where sq.itemName = sd.itemName and sq.brandName= sd.brandName\n" +
                    " and sq.supplierAliasName=sd.supplierName and sq.supplierQuoteStatus=? group by sq.supplierAliasName,\n" +
                    " sq.brandName,sq.itemName and sq.aliasProjName in (select aliasProjName from project where projId in(\n" +
                    " select projectId from authoriseproject where empId = ?));";
        }
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, status, empId);
        for (Map<String, Object> row : rows) {
            SupplierQuoteDetails detail = new SupplierQuoteDetails();
            detail.setAliasProjName((String) row.get("aliasprojName"));
            detail.setSupplierAliasName((String) row.get("SupplierAliasName"));
            detail.setBrandName((String) row.get("brandName"));
            detail.setItemQty( row.get("receivedQty").toString()); // recieved qty is taken and itemQty for payment
            detail.setItemName((String) row.get("ItemName"));
            detail.setItemType((String) row.get("ItemType"));
            supplierList.add(detail);
        }
        return supplierList;
    }
}