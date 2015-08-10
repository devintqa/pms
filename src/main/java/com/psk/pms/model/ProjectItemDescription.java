package com.psk.pms.model;


public class ProjectItemDescription {

    private Integer projectDescId;
    private String projectDescSerialNumber;
    private String itemName;
    private String itemQuantity;
    private String itemUnit;
    private String aliasDescription;

    public Integer getProjectDescId() {
        return projectDescId;
    }

    public void setProjectDescId(Integer projectDescId) {
        this.projectDescId = projectDescId;
    }

    public String getProjectDescSerialNumber() {
        return projectDescSerialNumber;
    }

    public void setProjectDescSerialNumber(String projectDescSerialNumber) {
        this.projectDescSerialNumber = projectDescSerialNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(String itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getAliasDescription() {
        return aliasDescription;
    }

    public void setAliasDescription(String aliasDescription) {
        this.aliasDescription = aliasDescription;
    }
}
