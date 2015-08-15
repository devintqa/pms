package com.psk.pms.model;

import java.util.List;
import java.util.Set;

import org.springframework.web.multipart.MultipartFile;

public class Item {

	private String itemName;
	private String itemUnit;
	private String employeeId;
	private Set<String> itemNames;
	private String itemType;
	private boolean baseItem;
	private List<MultipartFile> files;
	private String fileName;
	private String filePath;
	
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

	public String getItemType() {
		return itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	public boolean isBaseItem() {
		return baseItem;
	}

	public void setBaseItem(boolean baseItem) {
		this.baseItem = baseItem;
	}

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

	public List<MultipartFile> getFiles() {
		return files;
	}

	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}

}
