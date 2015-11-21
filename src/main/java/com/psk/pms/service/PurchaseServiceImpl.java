package com.psk.pms.service;


import com.psk.pms.Constants;
import com.psk.pms.dao.PurchaseDAO;
import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.psk.pms.Constants.*;
import static com.psk.pms.model.QuoteDetails.SupplierQuoteDetails;


public class PurchaseServiceImpl implements PurchaseService {

    private static final Logger LOGGER = Logger.getLogger(PurchaseServiceImpl.class);

    @Autowired
    PurchaseDAO purchaseDAO;

    @Override
    public Supplier fetchSupplierDetails(String supplierId) {
        return purchaseDAO.fetchSupplierDetail(supplierId);
    }

    @Override
    public List<Supplier> fetchSupplierDetail(String supplierAliasName) {
        return purchaseDAO.fetchSupplierDetails(supplierAliasName);
    }

    @Override
    public void deleteSupplier(String supplierId) {
        purchaseDAO.deleteSupplierDetail(supplierId);
    }

    @Override
    public void saveSupplierDetail(Supplier supplier) {
        supplier.setLastUpdatedBy("myself");
        supplier.setLastUpdatedAt(getCurrentDateTime());
        purchaseDAO.saveSupplierDetail(supplier);
    }

    @Override
    public boolean isAliasSupplierNameAlreadyExist(String aliasSupplierName) {
        return purchaseDAO.isAliasSupplierNameAlreadyExist(aliasSupplierName);
    }

    @Override
    @Transactional
    public void updateSupplierDetails(QuoteDetails quoteDetails, String status) {
        Integer projectId = purchaseDAO.getProjectId(quoteDetails.getProjName());
        if ("Y".equalsIgnoreCase(quoteDetails.getSubmittedForApproval())) {
            purchaseDAO.updateIndentDescStatus(status, quoteDetails.getItemName(), quoteDetails.getItemType(), Constants.PURCHASE_PENDING_APPROVAL, projectId);
            purchaseDAO.updateSupplierDetails(quoteDetails, status);
        } else if ("A".equalsIgnoreCase(quoteDetails.getSubmittedForApproval())) {
            purchaseDAO.updateSupplierDetails(quoteDetails, status);
            String indentStatus = getIndentStatus(quoteDetails);
            purchaseDAO.updateIndentDescStatusForPurchase(indentStatus, quoteDetails.getItemName(), quoteDetails.getItemType(), projectId);
            boolean pendingPurchase = purchaseDAO.isPendingPurchase(quoteDetails.getProjName());
            if(!pendingPurchase){
                java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
                purchaseDAO.updateIndentStatus(Constants.PURCHASED,todayDate,quoteDetails.getEmployeeId(),projectId);
            }
        }
       }


    @Override
    public List<Supplier> fetchSupplierDetails() {
        return purchaseDAO.fetchSupplierDetails();
    }

    @Override
    @Transactional
    public void saveSupplierQuoteDetails(QuoteDetails quoteDetails, String status) {
        purchaseDAO.deleteSupplierQuoteDetails(quoteDetails.getProjName(), quoteDetails.getItemType(), quoteDetails.getItemName());
        purchaseDAO.saveSupplierQuoteDetails(quoteDetails, status);
        if ("Y".equalsIgnoreCase(quoteDetails.getSubmittedForApproval())) {
            Integer projectId = purchaseDAO.getProjectId(quoteDetails.getProjName());
            purchaseDAO.updateIndentDescStatus(PURCHASE_PENDING_APPROVAL, quoteDetails.getItemName(), quoteDetails.getItemType(), Constants.PENDING_PURCHASE, projectId);
        }
    }

    private String getIndentStatus(QuoteDetails quoteDetails) {
        List<QuoteDetails.SupplierQuoteDetails> purchaseList = purchaseDAO.getPurchaseSupplierDetails(quoteDetails.getProjName(), quoteDetails.getItemName(), APPROVED);
        if (purchaseList.isEmpty() || purchaseList.size() == 0) {
            return PURCHASED;
        }
        return PARTIALLY_PURCHASED;
    }

    @Override
    public List<SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName) {
        return purchaseDAO.getSupplierQuoteDetails(projName, itemType, itemName);
    }


    @Override
    public List<SupplierQuoteDetails> getPurchaseListByStatus(String status, String empId) {
        return purchaseDAO.getPurchaseListByStatus(status, empId);
    }

    @Override
    public List<SupplierQuoteDetails> getPurchaseSupplierDetails(String projName, String itemName, String status) {
        return purchaseDAO.getPurchaseSupplierDetails(projName, itemName, status);
    }

    @Override
    public List<SupplierQuoteDetails> getSupplierByStatus(String supplierStatus) {
        return purchaseDAO.getSupplierByStatus(supplierStatus);
    }

    @Override
    public SupplierQuoteDetails getSupplierDetails(String projName, String itemName, String itemType, String supplierName) {
        return purchaseDAO.getSupplierDetails(projName, itemName, itemType, supplierName);
    }

    private Date getCurrentDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.getTimeInMillis();
        Date date = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");
        String formattedDate = simpleDateFormat.format(date);
        return getSQLDate(formattedDate, simpleDateFormat);
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter) {
        Date date = null;
        try {
            if (null != dateToBeFormatted) {
                date = formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date");
        }
        return date;
    }
}
