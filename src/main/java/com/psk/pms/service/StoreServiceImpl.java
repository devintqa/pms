package com.psk.pms.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.psk.pms.Constants;
import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.model.DispatchDetail;
import com.psk.pms.model.StockDetail;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EmployeeDAO employeeDAO;


    @Override
    @Transactional
    public void saveStoreDetail(StoreDetail storeDetail) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        storeDetail.setSqlRecievedDate(getSQLDate(storeDetail.getRecievedDate(),
                formatter));
        storeDetailDAO.saveStoreDetail(storeDetail);
        saveStockDetails(storeDetail);
    }

    @Override
    @Transactional
    public void saveDispatchedDetail(DispatchDetail dispatchDetail) {
        dispatchDetail.setSqlDispatchedDate(new Date());
        storeDetailDAO.saveDispatchedDetails(dispatchDetail, Constants.DISPATCHED);
        deductFromStock(dispatchDetail);
    }


    @Override
    public List<StoreDetail> getStoreDetails(int projId) {
        return storeDetailDAO.getStoreDetails(projId);
    }


    @Override
    public List<String> getSelectedUser(String teamName, String projectId) {
        return employeeDAO.getSelectedEmployees(teamName, projectId);
    }

    @Override
    public List<String> getItemNamesInStore(String projId) {
        List<String> stockDetails = storeDetailDAO.getItemNamesInStore(projId);
        return stockDetails;
    }

    @Override
    public String getItemQuantityInStock(String projId, String itemName) {
        String quantity = null;
        List<StockDetail> stockDetails = storeDetailDAO.getStockDetails(Integer.parseInt(projId), itemName);
        if (!stockDetails.isEmpty()) {
            quantity = stockDetails.get(0).getTotalQuantity();
        }
        return quantity;
    }


    private void saveStockDetails(StoreDetail storeDetail) {
        List<StockDetail> stockDetails = storeDetailDAO.getStockDetails(storeDetail.getProjId(), storeDetail.getItemName());
        if (stockDetails.isEmpty()) {
            storeDetailDAO.saveStockDetail(storeDetail);
        } else {
            addIntoStocks(storeDetail, stockDetails);
        }
    }

    private void addIntoStocks(StoreDetail storeDetail, List<StockDetail> stockDetails) {
        int totalQuantity = parseInt(storeDetail.getRecievedQuantity()) + parseInt(stockDetails.get(0).getTotalQuantity());
        storeDetailDAO.updateStockDetail(storeDetail.getProjId(), storeDetail.getItemName(), String.valueOf(totalQuantity));
    }

    private void deductFromStock(DispatchDetail dispatchDetail) {
        int totalQuantity = parseInt(dispatchDetail.getTotalQuantity()) - parseInt(dispatchDetail.getRequestedQuantity());
        storeDetailDAO.updateStockDetail(dispatchDetail.getProjId(), dispatchDetail.getItemName(), String.valueOf(totalQuantity));
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
