package com.psk.pms.service;

import static com.psk.pms.Constants.PARTIALLY_RECEIVED;
import static com.psk.pms.Constants.PURCHASED;
import static com.psk.pms.Constants.RECEIVED;
import static java.lang.Integer.parseInt;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.psk.pms.dao.PurchaseDAO;
import com.psk.pms.model.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.psk.pms.Constants;
import com.psk.pms.dao.EmployeeDAO;
import com.psk.pms.dao.StoreDetailDAO;

/**
 * Created by Sony on 26-09-2015.
 */
@Service
public class StoreServiceImpl implements StoreService {
    private static final Logger LOGGER = Logger
            .getLogger(StoreServiceImpl.class);

    private StoreDetailDAO storeDetailDAO;

    @Autowired
    private PurchaseDAO purchaseDAO;

    @Autowired
    EmployeeDAO employeeDAO;

    @Override
    @Transactional
    public void saveStoreDetail(StoreDetail storeDetail) throws IOException {
        storeDetailDAO.saveStoreDetail(storeDetail);
        uploadFiles(storeDetail, storeDetail.getEmployeeId());
        saveStockDetails(storeDetail);
    }

    @Override
    @Transactional
    public void saveDispatchedDetail(DispatchDetail dispatchDetail) {
        dispatchDetail.setSqlDispatchedDate(new java.sql.Date(Calendar
                .getInstance().getTimeInMillis()));
        storeDetailDAO.saveDispatchedDetails(dispatchDetail,
                Constants.DISPATCHED);
        deductFromStock(dispatchDetail);
    }

    @Override
    @Transactional
    public void saveReturnedDetail(DispatchDetail dispatchDetail) {
        dispatchDetail.setSqlReturnedDate(new java.sql.Date(Calendar
                .getInstance().getTimeInMillis()));
        storeDetailDAO.saveReturnedDetails(dispatchDetail, Constants.RETURNED);
        returnToStock(dispatchDetail);
    }

    @Override
    public List<DispatchDetail> getDispatchedDetails(
            DispatchDetail dispatchDetail) {
        return storeDetailDAO.getDispatchedDetails(dispatchDetail);
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
    public List<StockDetail> getItemNamesInStore(String projId, String itemName) {
        return storeDetailDAO.getItemNamesInStore(projId, itemName);
    }

    @Override
    public List<StockDetail> getItemsToReturn(String projId,
                                              String fieldUser) {
        return storeDetailDAO.getItemsToReturn(projId, fieldUser);
    }

    @Override
    public List<StockDetail> getStockDetails(int projId) {
        return storeDetailDAO.getStockDetails(projId);
    }

    @Override
    public String getItemQuantityInStock(String projId, String itemName) {
        String quantity = null;
        List<StockDetail> stockDetails = storeDetailDAO.getStockDetails(
                Integer.parseInt(projId), itemName);
        if (!stockDetails.isEmpty()) {
            quantity = stockDetails.get(0).getTotalQuantity();
        }
        return quantity;
    }

    private void saveStockDetails(StoreDetail storeDetail) {
        List<StockDetail> stockDetails = storeDetailDAO.getStockDetails(
                storeDetail.getProjId(), storeDetail.getItemName());
        if (stockDetails.isEmpty()) {
            storeDetailDAO.saveStockDetail(storeDetail);
        } else {
            addIntoStocks(storeDetail, stockDetails);
        }
    }

    private void addIntoStocks(StoreDetail storeDetail,
                               List<StockDetail> stockDetails) {
        int totalQuantity = parseInt(storeDetail.getRecievedQuantity())
                + parseInt(stockDetails.get(0).getTotalQuantity());
        storeDetailDAO.updateStockDetail(storeDetail.getProjId(),
                storeDetail.getItemName(), String.valueOf(totalQuantity));
    }

    private void deductFromStock(DispatchDetail dispatchDetail) {
        List<DispatchDetail.DispatchItems> dispatchItems = dispatchDetail
                .getDispatchItems();
        for (DispatchDetail.DispatchItems dispatchItem : dispatchItems) {
            int totalQuantity = parseInt(dispatchItem.getTotalQuantity())
                    - parseInt(dispatchItem.getRequestedQuantity());
            storeDetailDAO.updateStockDetail(dispatchDetail.getProjId(),
                    dispatchItem.getItemName(), String.valueOf(totalQuantity));
        }
    }

    private void returnToStock(DispatchDetail dispatchDetail) {

        List<DispatchDetail.DispatchItems> returnItems = dispatchDetail
                .getDispatchItems();
        for (DispatchDetail.DispatchItems returnItem : returnItems) {
            String quantity = getItemQuantityInStock(dispatchDetail.getProjId()
                    .toString(), returnItem.getItemName());
            int totalQuantity = Integer.parseInt(quantity)
                    + Integer.parseInt(returnItem.getReturnedQuantity());
            storeDetailDAO.updateStockDetail(dispatchDetail.getProjId(),
                    returnItem.getItemName(), String.valueOf(totalQuantity));
        }

    }

    @SuppressWarnings("unused")
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
    public String validateFieldUserForReturn(String projId, String fieldUser) {
        return storeDetailDAO.validateFieldUserForReturn(projId, fieldUser);
    }

    @Override
    @Transactional
    public void updateSupplierQuoteDetailStatusAndIndentStatus(StoreDetail storeDetail, String status) {
        storeDetailDAO.updateSupplierQuoteDetailStatus(storeDetail, status);
        String indentStatus = getIndentStatus(storeDetail);
        storeDetailDAO.updateIndentDescStatusForStore(indentStatus, storeDetail.getItemName(), storeDetail.getItemType(), storeDetail.getProjId());
        boolean pendingToReceive = storeDetailDAO.isPendingToReceive(storeDetail.getAliasProjName());
        if (!pendingToReceive) {
            java.sql.Date todayDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            purchaseDAO.updateIndentStatus(RECEIVED, todayDate, storeDetail.getEmployeeId(), storeDetail.getProjId());
        }
    }

    private String getIndentStatus(StoreDetail storeDetail) {
        List<QuoteDetails.SupplierQuoteDetails> purchaseList = purchaseDAO.getPurchaseSupplierDetails(storeDetail.getAliasProjName(), storeDetail.getItemName(), PURCHASED);
        if (purchaseList.isEmpty() || purchaseList.size() == 0) {
            return RECEIVED;
        }
        return PARTIALLY_RECEIVED;
    }


    public void uploadFiles(StoreDetail fileUpload, String employeeId) throws IOException {
        File files;
        String saveDirectory;
        LOGGER.info("method = uploadFiles() , Alias Project Name" + fileUpload.getAliasProjName());
        files = new File("C:\\PMS\\" + fileUpload.getAliasProjName() + "\\StoreInvoice");
        saveDirectory = "C:/PMS/" + fileUpload.getAliasProjName() + "/StoreInvoice/";
        createFileDirectory(files);
        List<MultipartFile> storeFiles = fileUpload.getStoreFiles();
        saveFiles(saveDirectory, storeFiles);
    }

    private void createFileDirectory(File files) {
        try {
            if (!files.exists()) {
                if (files.mkdirs()) {
                    LOGGER.info("Multiple directories are created!");
                } else {
                    LOGGER.info("Failed to create multiple directories!");
                }
            }
        } catch (Exception e) {
            LOGGER.info("Something went wrong!!", e);
        }
    }

    private void saveFiles(String saveDirectory, List<MultipartFile> files) throws IOException {
        List<String> fileNames = new ArrayList<String>();
        if (null != files && files.size() > 0) {
            for (MultipartFile multipartFile : files) {
                String fileName = multipartFile.getOriginalFilename();
                if (!"".equalsIgnoreCase(fileName)) {
                    LOGGER.info("File Name: " + fileName);
                    multipartFile.transferTo(new File(saveDirectory + fileName));
                    fileNames.add(fileName);
                }
            }
        }
    }

    @Override
    public Integer isRecordExists(String attribute) {
        return storeDetailDAO.isRecordExists(attribute);
    }


}
