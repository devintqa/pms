package com.psk.pms.model;

import java.util.Set;

public class Item {
	
	private String itemName;
	private String itemUnit;
	private String employeeId;
	private Set<String> itemNames;
	
	public Set<String> getItemNames() {
		return itemNames;
	}
	public void setItemNames(Set<String> itemNames) {
		this.itemNames = itemNames;
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
	public String getItemUnit() {
		return itemUnit;
	}
	public void setItemUnit(String itemUnit) {
		this.itemUnit = itemUnit;
	}

}
