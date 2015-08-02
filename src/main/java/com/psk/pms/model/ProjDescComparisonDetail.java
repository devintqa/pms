package com.psk.pms.model;

public class ProjDescComparisonDetail {
	
	private String serialNumber;
	private String aliasDescription;
	private String quantity;
	private String metric;
	private String pricePerQuantity;
	private String totalCost;
	private String deptPricePerQuantity;
	private String deptTotalCost;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAliasDescription() {
		return aliasDescription;
	}
	public void setAliasDescription(String aliasDescription) {
		this.aliasDescription = aliasDescription;
	}
	public String getQuantity() {
		return quantity;
	}
	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}
	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}
	public String getPricePerQuantity() {
		return pricePerQuantity;
	}
	public void setPricePerQuantity(String pricePerQuantity) {
		this.pricePerQuantity = pricePerQuantity;
	}
	public String getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(String totalCost) {
		this.totalCost = totalCost;
	}
	public String getDeptPricePerQuantity() {
		return deptPricePerQuantity;
	}
	public void setDeptPricePerQuantity(String deptPricePerQuantity) {
		this.deptPricePerQuantity = deptPricePerQuantity;
	}
	public String getDeptTotalCost() {
		return deptTotalCost;
	}
	public void setDeptTotalCost(String deptTotalCost) {
		this.deptTotalCost = deptTotalCost;
	}

}
