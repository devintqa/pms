package com.psk.pms.model;

import java.util.List;

public class IndentDesc {
	
	private String projId;
	private String indentId;
	private String indentDescId;
	private String projDescId;
	private String aliasProjDesc;
	private Double plannedQty;
	private String metric;
	private String comments;
	private String employeeId;
	private Double totalQty;
	
	private List<ItemDetail> itemDetails;
	
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

	public String getIndentDescId() {
		return indentDescId;
	}
	public void setIndentDescId(String indentDescId) {
		this.indentDescId = indentDescId;
	}

	public Double getPlannedQty() {
		return plannedQty;
	}
	public void setPlannedQty(Double plannedQty) {
		this.plannedQty = plannedQty;
	}

	public String getIndentId() {
		return indentId;
	}
	public void setIndentId(String indentId) {
		this.indentId = indentId;
	}

	public Double getTotalQty() {
		return totalQty;
	}
	public void setTotalQty(Double totalQty) {
		this.totalQty = totalQty;
	}

	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
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
		private String indentItemId;
		private String indentDescId;
		private String indentList;
        private String indentStatus;

		public String getIndentItemId() {
			return indentItemId;
		}

		public void setIndentItemId(String indentItemId) {
			this.indentItemId = indentItemId;
		}

		public String getIndentDescId() {
			return indentDescId;
		}

		public void setIndentDescId(String indentDescId) {
			this.indentDescId = indentDescId;
		}
		
		public String getIndentList() {
			return indentList;
		}

		public void setIndentList(String indentList) {
			this.indentList = indentList;
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

        public String getIndentStatus() {
            return indentStatus;
        }

        public void setIndentStatus(String indentStatus) {
            this.indentStatus = indentStatus;
        }
    }

}
