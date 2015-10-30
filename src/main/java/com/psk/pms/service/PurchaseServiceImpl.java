package com.psk.pms.service;


import com.psk.pms.dao.PurchaseDAO;
import com.psk.pms.model.Supplier;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

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