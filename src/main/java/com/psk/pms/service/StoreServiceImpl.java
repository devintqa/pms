package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.psk.pms.dao.StoreDetailDAO;
import com.psk.pms.model.StoreDetail;

/**
 * Created by Sony on 26-09-2015.
 */
@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger LOGGER = Logger.getLogger(StoreServiceImpl.class);

    private StoreDetailDAO storeDetailDAO;


    @Override
    public void saveStoreDetail(StoreDetail storeDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        storeDetail.setSqlRecievedDate(getSQLDate(storeDetail.getRecievedDate(),
                formatter));
        storeDetailDAO.saveStoreDetail(storeDetail);
    }


    @Override
    public List<StoreDetail> getStoreDetails(int projId) {
        return storeDetailDAO.getStoreDetails(projId);
    }

    private Date getSQLDate(String dateToBeFormatted, SimpleDateFormat formatter) {
        Date date = null;
        try {
            if (null != dateToBeFormatted) {
                date = formatter.parse(dateToBeFormatted);
            }
        } catch (ParseException e) {
            LOGGER.error("Error in parsing the date " + e.getMessage());
        }
        return date;
    }


    public void setStoreDetailDAO(StoreDetailDAO storeDetailDAO) {
        this.storeDetailDAO = storeDetailDAO;
    }
}
