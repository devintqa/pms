package com.psk.pms.model;

import java.util.List;

public class Indent {
	
	private String metric;
	private String projId;
	private String subProjId;
	private String projDescId;
	private String aliasProjDesc;
	private String employeeId;
	private String indentId;
	private String startDate;
	private String endDate;
	private Double plannedArea;
	private Double totalArea;
	private List<ItemDetail> itemDetails;
	
	public Double getPlannedArea() {
		return plannedArea;
	}
	public void setPlannedArea(Double plannedArea) {
		this.plannedArea = plannedArea;
	}
	public Double getTotalArea() {
		return totalArea;
	}
	public void setTotalArea(Double totalArea) {
		this.totalArea = totalArea;
	}
	public String getIndentId() {
		return indentId;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}
	public List<ItemDetail> getItemDetails() {
		return itemDetails;
	}
	public void setItemDetails(List<ItemDetail> itemDetails) {
		this.itemDetails = itemDetails;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getSubProjId() {
		return subProjId;
	}
	public void setSubProjId(String subProjId) {
		this.subProjId = subProjId;
	}
	public String getProjDescId() {
		return projDescId;
	}
	public void setProjDescId(String projDescId) {
		this.projDescId = projDescId;
	}
	public String getEmployeeId() {
		return employeeId;
	}
	public void setEmployeeId(String employeeId) {
		this.employeeId = employeeId;
	}

	public String getMetric() {
		return metric;
	}
	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getAliasProjDesc() {
		return aliasProjDesc;
	}
	public void setAliasProjDesc(String aliasProjDesc) {
		this.aliasProjDesc = aliasProjDesc;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public static class ItemDetail {

		public ItemDetail() {

		}

		public ItemDetail(String itemType, String itemName, String itemQty, String itemUnit, String itemPrice) {
			this.itemType = itemType;
			this.itemName = itemName;
			this.itemQty = itemQty;
			this.itemUnit = itemUnit;
			this.itemPrice = itemPrice;
		}

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
	}

}
