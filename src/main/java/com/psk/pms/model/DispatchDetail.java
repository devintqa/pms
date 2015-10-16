package com.psk.pms.model;

import java.sql.Date;
import java.util.List;

/**
 * Created by Sony on 08-10-2015.
 */
public class DispatchDetail {

    private int projId;
    private String employeeId;
    private String itemName;
    private String dispatchItemsValue;
    private String fieldUser;
    private String totalQuantity;
    private String dispatchedDate;
    private Date sqlDispatchedDate;
    private String requestedQuantity;
    private List<String> fieldUsers;
    private List<String>items;
    private String description;
    private List<DispatchItems>dispatchItems;



    public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getFieldUser() {
        return fieldUser;
    }

    public void setFieldUser(String fieldUser) {
        this.fieldUser = fieldUser;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getRequestedQuantity() {
        return requestedQuantity;
    }

    public void setRequestedQuantity(String requestedQuantity) {
        this.requestedQuantity = requestedQuantity;
    }

    public List<String> getFieldUsers() {
        return fieldUsers;
    }

    public void setFieldUsers(List<String> fieldUsers) {
        this.fieldUsers = fieldUsers;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getDispatchedDate() {
        return dispatchedDate;
    }

    public void setDispatchedDate(String dispatchedDate) {
        this.dispatchedDate = dispatchedDate;
    }

    public Date getSqlDispatchedDate() {
        return sqlDispatchedDate;
    }

    public void setSqlDispatchedDate(Date sqlDispatchedDate) {
        this.sqlDispatchedDate = sqlDispatchedDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<DispatchItems> getDispatchItems() {
        return dispatchItems;
    }

    public void setDispatchItems(List<DispatchItems> dispatchItems) {
        this.dispatchItems = dispatchItems;
    }

    public String getDispatchItemsValue() {
        return dispatchItemsValue;
    }

    public void setDispatchItemsValue(String dispatchItemsValue) {
        this.dispatchItemsValue = dispatchItemsValue;
    }

    public static class DispatchItems {
        private String itemName;
        private String totalQuantity;
        private String requestedQuantity;

        public DispatchItems(){

        }
        public DispatchItems(String itemName, String totalQuantity, String requestedQuantity) {
            this.itemName = itemName;
            this.totalQuantity = totalQuantity;
            this.requestedQuantity = requestedQuantity;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getTotalQuantity() {
            return totalQuantity;
        }

        public void setTotalQuantity(String totalQuantity) {
            this.totalQuantity = totalQuantity;
        }

        public String getRequestedQuantity() {
            return requestedQuantity;
        }

        public void setRequestedQuantity(String requestedQuantity) {
            this.requestedQuantity = requestedQuantity;
        }
    }
}
