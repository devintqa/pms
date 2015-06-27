package com.psk.pms.model;

import java.util.Date;


public class DescItemDetail {
	
	private String itemCode;
	private Integer projDescId;
	private String serialNumber;
	private String amount;
	private String unit;
	private String material;
	private String quantityInFig;
	private String rateInFig;
	private String lastUpdatedBy ;
    private Date lastUpdatedAt;
    
    public Integer getProjDescId() {
		return projDescId;
	}
	public void setProjDescId(Integer projDescId) {
		this.projDescId = projDescId;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getQuantityInFig() {
		return quantityInFig;
	}
	public void setQuantityInFig(String quantityInFig) {
		this.quantityInFig = quantityInFig;
	}
	public String getRateInFig() {
		return rateInFig;
	}
	public void setRateInFig(String rateInFig) {
		this.rateInFig = rateInFig;
	}
	public String getLastUpdatedBy() {
		return lastUpdatedBy;
	}
	public void setLastUpdatedBy(String lastUpdatedBy) {
		this.lastUpdatedBy = lastUpdatedBy;
	}
	public Date getLastUpdatedAt() {
		return lastUpdatedAt;
	}
	public void setLastUpdatedAt(Date lastUpdatedAt) {
		this.lastUpdatedAt = lastUpdatedAt;
	}
	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
}

