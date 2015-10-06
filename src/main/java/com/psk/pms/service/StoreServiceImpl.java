package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.psk.pms.model.StockDetail;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.psk.pms.dao.StoreDetailDAO;
import com.psk.pms.model.StoreDetail;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.Integer.parseInt;

/**
 * Created by Sony on 26-09-2015.
 */
@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger LOGGER = Logger.getLogger(StoreServiceImpl.class);

    private StoreDetailDAO storeDetailDAO;


    @Override
    @Transactional
    public void saveStoreDetail(StoreDetail storeDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        storeDetail.setSqlRecievedDate(getSQLDate(storeDetail.getRecievedDate(),
                formatter));
        storeDetailDAO.saveStoreDetail(storeDetail);
        saveStockDetails(storeDetail);
    }

    private void saveStockDetails(StoreDetail storeDetail) {
        List<StockDetail> stockDetails = storeDetailDAO.getStockDetails(storeDetail.getProjId(), storeDetail.getItemName());
        if (stockDetails.isEmpty()) {
            storeDetailDAO.saveStockDetail(storeDetail);
        } else {
            int totalQuantity = parseInt(storeDetail.getRecievedQuantity()) + parseInt(stockDetails.get(0).getTotalQuantity());
            storeDetailDAO.updateStockDetail(storeDetail.getProjId(), storeDetail.getItemName(), String.valueOf(totalQuantity));
        }
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

	@Override
	public List<String> getItemNamesInStore(String projId) {
		 List<String> stockDetails = storeDetailDAO.getItemNamesInStore(projId);	
		 return stockDetails;
	}

	
}
