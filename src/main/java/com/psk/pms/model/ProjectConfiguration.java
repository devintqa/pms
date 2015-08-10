package com.psk.pms.model;

import java.util.List;

public class ProjectConfiguration {

	public ProjectConfiguration() {

	}

	private String itemPriceConfiguration;
	private Integer projId;
	private Integer subProjId = 0;
	private String employeeId;
	private String itemType;

	private List<ItemDetail> itemDetail;

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
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

	public String getItemPriceConfiguration() {
		return itemPriceConfiguration;
	}

	public void setItemPriceConfiguration(String itemPriceConfiguration) {
		this.itemPriceConfiguration = itemPriceConfiguration;
	}

	public static class ItemDetail {

		public ItemDetail() {

		}

		public ItemDetail(String itemName, String itemQty, String itemPrice) {
			this.itemName = itemName;
			this.itemQty = itemQty;
			this.itemPrice = itemPrice;
		}

		private String label;
		private String itemType;
		private String itemName;
		private String itemUnit;
		private String itemQty;
		private String itemPrice;

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
