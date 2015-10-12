package com.psk.pms.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Sony on 08-10-2015.
 */
public class StoreTransactionDetail {

    private Integer projId;
    private String employeeId;
    private String itemName;
    private String fieldUser;
    private String totalQuantity;
    private String dispatchedDate;
    private Date sqlDispatchedDate;
    private String requestedQuantity;
    private String returnedDate;
    private Date sqlReturnedDate;
    private String returnedQuantity;
    private List<String> fieldUsers;
    private List<String>items;

    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
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

	public String getReturnedDate() {
		return returnedDate;
	}

	public void setReturnedDate(String returnedDate) {
		this.returnedDate = returnedDate;
	}

	public Date getSqlReturnedDate() {
		return sqlReturnedDate;
	}

	public void setSqlReturnedDate(Date sqlReturnedDate) {
		this.sqlReturnedDate = sqlReturnedDate;
	}

	public String getReturnedQuantity() {
		return returnedQuantity;
	}

	public void setReturnedQuantity(String returnedQuantity) {
		this.returnedQuantity = returnedQuantity;
	}
    
    
}
