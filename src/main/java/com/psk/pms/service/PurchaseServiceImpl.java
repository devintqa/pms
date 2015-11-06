package com.psk.pms.service;


import com.psk.pms.dao.PurchaseDAO;
import com.psk.pms.model.QuoteDetails;
import com.psk.pms.model.Supplier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


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
    public List<Supplier> fetchSupplierDetails() {
        return purchaseDAO.fetchSupplierDetails();
    }


    @Override
    @Transactional
    public void saveSupplierQuoteDetails(QuoteDetails quoteDetails) {
        purchaseDAO.deleteSupplierQuoteDetails(quoteDetails.getProjName(),quoteDetails.getItemType(),quoteDetails.getItemName());
        purchaseDAO.saveSupplierQuoteDetails(quoteDetails);
    }

    @Override
    public List<QuoteDetails.SupplierQuoteDetails> getSupplierQuoteDetails(String projName, String itemType, String itemName) {
       return purchaseDAO.getSupplierQuoteDetails(projName,itemType,itemName);
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
