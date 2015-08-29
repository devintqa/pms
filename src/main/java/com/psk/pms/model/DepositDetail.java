package com.psk.pms.model;

import java.util.Date;

public class DepositDetail {
    private int depositId;
    private Integer projId;
    private Integer subProjId;
    private String depositAmount;
    private String depositStartDate;
    private Date sqlDepositStartDate;
    private String depositEndDate;
    private Date sqlDepositEndDate;
    private String depositType;
    private String bgNumber;
    private int depositPeriod;
    private String depositExtensionDate;
    private Date sqlDepositRecievedDate;
    private String depositRecievedDate;
    private String depositRecievedComments;
    private Date depositExtensionSqlDate;
    private String depositLedgerNumber;
    private String employeeId;
    private String isUpdate;
    private boolean subProjectDepositDetail;
    private String aliasProjectName;
    private String aliasSubProjectName;
    private String lastUpdatedBy;
    private Date lastUpdatedAt;
    private String depositDetailSubmitter;
    private String depositFor;
    private String depDetail;
    private String depositStatus;


    public int getDepositId() {
        return depositId;
    }

    public void setDepositId(int depositId) {
        this.depositId = depositId;
    }

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

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    public String getDepositStartDate() {
        return depositStartDate;
    }

    public void setDepositStartDate(String depositStartDate) {
        this.depositStartDate = depositStartDate;
    }

    public Date getSqlDepositStartDate() {
        return sqlDepositStartDate;
    }

    public void setSqlDepositStartDate(Date sqlDepositStartDate) {
        this.sqlDepositStartDate = sqlDepositStartDate;
    }

    public String getDepositEndDate() {
        return depositEndDate;
    }

    public void setDepositEndDate(String depositEndDate) {
        this.depositEndDate = depositEndDate;
    }

    public Date getSqlDepositEndDate() {
        return sqlDepositEndDate;
    }

    public void setSqlDepositEndDate(Date sqlDepositEndDate) {
        this.sqlDepositEndDate = sqlDepositEndDate;
    }

    public String getDepositType() {
        return depositType;
    }

    public void setDepositType(String depositType) {
        this.depositType = depositType;
    }

    public String getBgNumber() {
        return bgNumber;
    }

    public void setBgNumber(String bgNumber) {
        this.bgNumber = bgNumber;
    }

    public int getDepositPeriod() {
        return depositPeriod;
    }

    public void setDepositPeriod(int depositPeriod) {
        this.depositPeriod = depositPeriod;
    }

    public String getDepositExtensionDate() {
        return depositExtensionDate;
    }

    public void setDepositExtensionDate(String depositExtensionDate) {
        this.depositExtensionDate = depositExtensionDate;
    }

    public Date getDepositExtensionSqlDate() {
        return depositExtensionSqlDate;
    }

    public void setDepositExtensionSqlDate(Date depositExtensionSqlDate) {
        this.depositExtensionSqlDate = depositExtensionSqlDate;
    }

    public String getDepositLedgerNumber() {
        return depositLedgerNumber;
    }

    public void setDepositLedgerNumber(String depositLedgerNumber) {
        this.depositLedgerNumber = depositLedgerNumber;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(String isUpdate) {
        this.isUpdate = isUpdate;
    }

    public boolean isSubProjectDepositDetail() {
        return subProjectDepositDetail;
    }

    public void setSubProjectDepositDetail(boolean subProjectDepositDetail) {
        this.subProjectDepositDetail = subProjectDepositDetail;
    }

    public String getAliasProjectName() {
        return aliasProjectName;
    }

    public void setAliasProjectName(String aliasProjectName) {
        this.aliasProjectName = aliasProjectName;
    }

    public String getAliasSubProjectName() {
        return aliasSubProjectName;
    }

    public void setAliasSubProjectName(String aliasSubProjectName) {
        this.aliasSubProjectName = aliasSubProjectName;
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

    public String getDepositDetailSubmitter() {
        return depositDetailSubmitter;
    }

    public void setDepositDetailSubmitter(String depositDetailSubmitter) {
        this.depositDetailSubmitter = depositDetailSubmitter;
    }

    public String getDepositFor() {
        return depositFor;
    }

    public void setDepositFor(String depositFor) {
        this.depositFor = depositFor;
    }

    public String getDepDetail() {
        return depDetail;
    }

    public void setDepDetail(String depDetail) {
        this.depDetail = depDetail;
    }

    public String getDepositStatus() {
        return depositStatus;
    }

    public void setDepositStatus(String depositStatus) {
        this.depositStatus = depositStatus;
    }

    public String getDepositRecievedDate() {
        return depositRecievedDate;
    }

    public void setDepositRecievedDate(String depositRecievedDate) {
        this.depositRecievedDate = depositRecievedDate;
    }


    public Date getSqlDepositRecievedDate() {
        return sqlDepositRecievedDate;
    }

    public void setSqlDepositRecievedDate(Date sqlDepositRecievedDate) {
        this.sqlDepositRecievedDate = sqlDepositRecievedDate;
    }

    public String getDepositRecievedComments() {
        return depositRecievedComments;
    }

    public void setDepositRecievedComments(String depositRecievedComments) {
        this.depositRecievedComments = depositRecievedComments;
    }
}
