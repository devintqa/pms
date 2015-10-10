package com.psk.pms.model;

import java.util.Date;

/**
 * Created by Sony on 25-09-2015.
 */
public class StoreDetail {

    private int projId;
    private String recievedDate;
    private Date sqlRecievedDate;
    private String itemType;
    private String employeeId;
    private String itemName;
    private String vehicleNumber;
    private String recievedQuantity;
    private String comments;
    private String supplierName;

    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getRecievedDate() {
        return recievedDate;
    }

    public void setRecievedDate(String recievedDate) {
        this.recievedDate = recievedDate;
    }

    public Date getSqlRecievedDate() {
        return sqlRecievedDate;
    }

    public void setSqlRecievedDate(Date sqlRecievedDate) {
        this.sqlRecievedDate = sqlRecievedDate;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getVehicleNumber() {
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) {
        this.vehicleNumber = vehicleNumber;
    }

    public String getRecievedQuantity() {
        return recievedQuantity;
    }

    public void setRecievedQuantity(String recievedQuantity) {
        this.recievedQuantity = recievedQuantity;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

}
