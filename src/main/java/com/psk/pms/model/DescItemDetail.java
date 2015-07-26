package com.psk.pms.model;

import java.util.List;

public class DescItemDetail {
	
	private String projDescSerial;
	private Integer projDescId;
	private Integer projId;
	private Integer subProjId;
	private String employeeId;
	private String projDescItemDetail;
	private String itemType;
	
	private List<ItemDetail> itemDetail;
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
	public String getItemType() {
		return itemType;
	}
	public void setItemType(String itemType) {
		this.itemType = itemType;
	}
	public static class ItemDetail {
		
		public ItemDetail(){
			
		}
		
		public ItemDetail(String itemName, String itemQty, String itemPrice, String itemCost){
			this.itemName = itemName;
			this.itemQty = itemQty;
			this.itemPrice = itemPrice;
			this.itemCost = itemCost;
		}
		
		private String label;
		private String itemType;
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

		public String getItemType() {
			return itemType;
		}

		public void setItemType(String itemType) {
			this.itemType = itemType;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
	
}
