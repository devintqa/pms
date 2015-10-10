package com.psk.pms.model;

import java.math.BigDecimal;

/**
 * Created by prakashbhanu57 on 10/3/2015.
 */
public class ItemDetailDto {

    private Integer projectDescItemId;
    private Integer projectId;
    private Integer subProjectId;
    private Integer projectDescId;
    private String projectDescSerial;
    private String itemName;
    private String itemUnit;
    private Integer itemQuantity;
    private BigDecimal itemPrice;
    private BigDecimal itemCost;

    public Integer getProjectDescItemId() {
        return projectDescItemId;
    }

    public void setProjectDescItemId(Integer projectDescItemId) {
        this.projectDescItemId = projectDescItemId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(Integer subProjectId) {
        this.subProjectId = subProjectId;
    }

    public Integer getProjectDescId() {
        return projectDescId;
    }

    public void setProjectDescId(Integer projectDescId) {
        this.projectDescId = projectDescId;
    }

    public String getProjectDescSerial() {
        return projectDescSerial;
    }

    public void setProjectDescSerial(String projectDescSerial) {
        this.projectDescSerial = projectDescSerial;
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

    public Integer getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(Integer itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getItemCost() {
        return itemCost;
    }

    public void setItemCost(BigDecimal itemCost) {
        this.itemCost = itemCost;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result
                + ((projectDescItemId == null) ? 0 : projectDescItemId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return true;
        }
        if (null == obj) {
            return false;
        }
        if (getClass()!=this.getClass()){
            return false;
        }
        final ItemDetailDto itemDetailDto = (ItemDetailDto) obj;
        return itemDetailDto.getProjectDescItemId() == this.getProjectDescItemId();
    }
}
