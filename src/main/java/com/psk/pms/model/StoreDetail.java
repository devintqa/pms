package com.psk.pms.model;

import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Sony on 25-09-2015.
 */
public class StoreDetail {

	private Integer projId;
	private String aliasProjName;
	private String recievedDate;
	private Date sqlRecievedDate;
	private String itemType;
	private String itemQty;
	private String employeeId;
	private String itemName;
	private String invoiceNumber;
	private String vehicleNumber;
	private String recievedQuantity;
	private String comments;
	private String supplierName;
	private String brandName;
	private String recievedBy;
	private String checkedBy;
	private String tripSheetNumber;
	private String storeType;
	private String storeDetailsValue;
	private String fileName;
	private String filePath;
	private List<MultipartFile> storeFiles;

	
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public List<MultipartFile> getStoreFiles() {
		return storeFiles;
	}

	public void setStoreFiles(List<MultipartFile> storeFiles) {
		this.storeFiles = storeFiles;
	}

	public String getInvoiceNumber() {
		return invoiceNumber;
	}

	public void setInvoiceNumber(String invoiceNumber) {
		this.invoiceNumber = invoiceNumber;
	}

	public String getStoreDetailsValue() {
		return storeDetailsValue;
	}

	public void setStoreDetailsValue(String storeDetailsValue) {
		this.storeDetailsValue = storeDetailsValue;
	}

	public String getAliasProjName() {
		return aliasProjName;
	}

	public void setAliasProjName(String aliasProjName) {
		this.aliasProjName = aliasProjName;
	}

	public Integer getProjId() {
		return projId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public String getRecievedDate() {
		return recievedDate;
	}

	public void setRecievedDate(String recievedDate) {
		this.recievedDate = recievedDate;
	}

	public Date getSqlRecievedDate() {
		return sqlRecievedDate;
	}

	public String getItemQty() {
		return itemQty;
	}

	public void setItemQty(String itemQty) {
		this.itemQty = itemQty;
	}

	public void setSqlRecievedDate(Date sqlRecievedDate) {
		this.sqlRecievedDate = sqlRecievedDate;
	}

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
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

	public String getVehicleNumber() {
		return vehicleNumber;
	}

	public void setVehicleNumber(String vehicleNumber) {
		this.vehicleNumber = vehicleNumber;
	}

	public String getRecievedQuantity() {
		return recievedQuantity;
	}

	public void setRecievedQuantity(String recievedQuantity) {
		this.recievedQuantity = recievedQuantity;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getRecievedBy() {
		return recievedBy;
	}

	public void setRecievedBy(String recievedBy) {
		this.recievedBy = recievedBy;
	}

	public String getCheckedBy() {
		return checkedBy;
	}

	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}

	public String getTripSheetNumber() {
		return tripSheetNumber;
	}

	public void setTripSheetNumber(String tripSheetNumber) {
		this.tripSheetNumber = tripSheetNumber;
	}

	public String getStoreType() {
		return storeType;
	}

	public void setStoreType(String storeType) {
		this.storeType = storeType;
	}

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
