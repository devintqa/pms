package com.psk.pms.model;

/**
 * Created by Sony on 03-10-2015.
 */
public class StockDetail {

    private int projId;
    private String totalQuantity;
    private String itemName;
    private String dispatchedQuantity;
    private String employeeId;

    public String getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public int getProjId() {
        return projId;
    }

    public void setProjId(int projId) {
        this.projId = projId;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

	public String getDispatchedQuantity() {
		return dispatchedQuantity;
	}

	public void setDispatchedQuantity(String dispatchedQuantity) {
		this.dispatchedQuantity = dispatchedQuantity;
	}
    
}
