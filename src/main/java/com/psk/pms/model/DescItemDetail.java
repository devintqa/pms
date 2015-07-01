package com.psk.pms.model;

import java.util.List;



public class DescItemDetail {
	
//	private String itemName;
//	private String itemUnit;
//	private String itemQty;
//	private String itemPrice;
//	private String itemCost;
	private String projDescSerial;
	private Integer projDescId;
	private Integer projId;
	private Integer subProjId;
	private String employeeId;
	private String projDescItemDetail;
	
	private List<ItemDetail> itemDetail;
	
//	public String getItemUnit() {
//		return itemUnit;
//	}
//	public void setItemUnit(String itemUnit) {
//		this.itemUnit = itemUnit;
//	}
//	public String getItemQty() {
//		return itemQty;
//	}
//	public void setItemQty(String itemQty) {
//		this.itemQty = itemQty;
//	}
//	public String getItemPrice() {
//		return itemPrice;
//	}
//	public void setItemPrice(String itemPrice) {
//		this.itemPrice = itemPrice;
//	}
//	public String getItemCost() {
//		return itemCost;
//	}
//	public void setItemCost(String itemCost) {
//		this.itemCost = itemCost;
//	}
//	public String getItemName() {
//		return itemName;
//	}
//	public void setItemName(String itemName) {
//		this.itemName = itemName;
//	}
	public Integer getProjDescId() {
		return projDescId;
	}
	public void setProjDescId(Integer projDescId) {
		this.projDescId = projDescId;
	}
	public Integer getProjId() {
		return projId;
	}
	public void setProjId(Integer projId) {
		this.projId = projId;
	}
	public String getProjDescItemDetail() {
		return projDescItemDetail;
	}
	public void setProjDescItemDetail(String projDescItemDetail) {
		this.projDescItemDetail = projDescItemDetail;
	}
	
	public String getProjDescSerial() {
		return projDescSerial;
	}
	public void setProjDescSerial(String projDescSerial) {
		this.projDescSerial = projDescSerial;
	}
	public Integer getSubProjId() {
		return subProjId;
	}
	public void setSubProjId(Integer subProjId) {
		this.subProjId = subProjId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}
	public List<ItemDetail> getItemDetail() {
		return itemDetail;
	}
	public void setItemDetail(List<ItemDetail> itemDetail) {
		this.itemDetail = itemDetail;
	}
	public static class ItemDetail {
		
		public ItemDetail(){
			
		}
		private String itemName;
		private String itemUnit;
		private String itemQty;
		private String itemPrice;
		private String itemCost;
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
		public String getItemQty() {
			return itemQty;
		}
		public void setItemQty(String itemQty) {
			this.itemQty = itemQty;
		}
		public String getItemPrice() {
			return itemPrice;
		}
		public void setItemPrice(String itemPrice) {
			this.itemPrice = itemPrice;
		}
		public String getItemCost() {
			return itemCost;
		}
		public void setItemCost(String itemCost) {
			this.itemCost = itemCost;
		}
	}
	
	/*
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
	*/
}

