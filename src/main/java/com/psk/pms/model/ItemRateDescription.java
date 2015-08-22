package com.psk.pms.model;

/**
 * Created by Sony on 18-08-2015.
 */
public class ItemRateDescription {

    private String scheduleItemNumber;
    private String itemName;
    private String itemDescription;
    private String itemUnit;
    private String itemRate;
    private String workType;


    public String getItemRate() {
        return itemRate;
    }

    public void setItemRate(String itemRate) {
        this.itemRate = itemRate;
    }

    public String getScheduleItemNumber() {
        return scheduleItemNumber;
    }

    public void setScheduleItemNumber(String scheduleItemNumber) {
        this.scheduleItemNumber = scheduleItemNumber;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemUnit() {
        return itemUnit;
    }

    public void setItemUnit(String itemUnit) {
        this.itemUnit = itemUnit;
    }

    public String getWorkType() {
        return workType;
    }

    public void setWorkType(String workType) {
        this.workType = workType;
    }


    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + ((itemName == null) ? 0 : itemName.hashCode());
        result = 31 * result + ((workType == null) ? 0 : workType.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ItemRateDescription)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ItemRateDescription itemRateDescription = (ItemRateDescription) obj;
        if (itemName != itemRateDescription.itemName && workType != itemRateDescription.workType) {
            return false;
        }
        return true;
    }
}
