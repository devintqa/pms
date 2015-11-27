package com.psk.pms.model;

import java.sql.Date;
import java.util.List;

/**
 * Created by Sony on 05-11-2015.
 */
public class QuoteDetails {
    private Integer projId;
    private String itemName;
    private String itemQty;
    private String projName;
    private String itemType;
    private String quoteDetailsValue;
    private String submittedForApproval;
    private List<SupplierQuoteDetails> supplierQuoteDetails;
    private String tentativeDeliveryDate;
    private Date sqlTentativeDeliveryDate;
    private String comments;
    private String employeeId;

    public String getTentativeDeliveryDate() {
        return tentativeDeliveryDate;
    }

    public void setTentativeDeliveryDate(String tentativeDeliveryDate) {
        this.tentativeDeliveryDate = tentativeDeliveryDate;
    }

    public Date getSqlTentativeDeliveryDate() {
        return sqlTentativeDeliveryDate;
    }

    public void setSqlTentativeDeliveryDate(Date sqlTentativeDeliveryDate) {
        this.sqlTentativeDeliveryDate = sqlTentativeDeliveryDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }


    public Integer getProjId() {
        return projId;
    }

    public void setProjId(Integer projId) {
        this.projId = projId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getSubmittedForApproval() {
        return submittedForApproval;
    }

    public void setSubmittedForApproval(String submittedForApproval) {
        this.submittedForApproval = submittedForApproval;
    }

    public List<SupplierQuoteDetails> getSupplierQuoteDetails() {
        return supplierQuoteDetails;
    }

    public void setSupplierQuoteDetails(
            List<SupplierQuoteDetails> supplierQuoteDetails) {
        this.supplierQuoteDetails = supplierQuoteDetails;
    }

    public String getProjName() {
        return projName;
    }

    public void setProjName(String projName) {
        this.projName = projName;
    }

    public String getQuoteDetailsValue() {
        return quoteDetailsValue;
    }

    public void setQuoteDetailsValue(String quoteDetailsValue) {
        this.quoteDetailsValue = quoteDetailsValue;
    }

    public String getItemQty() {
        return itemQty;
    }

    public void setItemQty(String itemQty) {
        this.itemQty = itemQty;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public static class SupplierQuoteDetails {
        private String supplierAliasName;
        private String emailAddress;
        private String phoneNumber;
        private String quotedPrice;
        private String supplierQuoteStatus;
        private String aliasProjName;
        private String itemName;
        private String itemQty;
        private String itemType;
        private String brandName;
        private String supplierType;


        public String getItemType() {
            return itemType;
        }

        public void setItemType(String itemType) {
            this.itemType = itemType;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemQty() {
            return itemQty;
        }

        public void setItemQty(String itemQty) {
            this.itemQty = itemQty;
        }

        public String getAliasProjName() {
            return aliasProjName;
        }

        public void setAliasProjName(String aliasProjName) {
            this.aliasProjName = aliasProjName;
        }

        public SupplierQuoteDetails() {

        }

        public SupplierQuoteDetails(String supplierAliasName,
                                    String emailAddress, String phoneNumber, String quotedPrice) {
            this.supplierAliasName = supplierAliasName;
            this.emailAddress = emailAddress;
            this.phoneNumber = phoneNumber;
            this.quotedPrice = quotedPrice;
        }

        public String getSupplierAliasName() {
            return supplierAliasName;
        }

        public void setSupplierAliasName(String supplierAliasName) {
            this.supplierAliasName = supplierAliasName;
        }

        public String getEmailAddress() {
            return emailAddress;
        }

        public void setEmailAddress(String emailAddress) {
            this.emailAddress = emailAddress;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getQuotedPrice() {
            return quotedPrice;
        }

        public void setQuotedPrice(String quotedPrice) {
            this.quotedPrice = quotedPrice;
        }

        public String getSupplierQuoteStatus() {
            return supplierQuoteStatus;
        }

        public void setSupplierQuoteStatus(String supplierQuoteStatus) {
            this.supplierQuoteStatus = supplierQuoteStatus;
        }

        public String getBrandName() {
            return brandName;
        }

        public void setBrandName(String brandName) {
            this.brandName = brandName;
        }

        public String getSupplierType() {
            return supplierType;
        }

        public void setSupplierType(String supplierType) {
            this.supplierType = supplierType;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            SupplierQuoteDetails that = (SupplierQuoteDetails) o;

            if (!supplierAliasName.equals(that.supplierAliasName) && !brandName.equals(that.brandName)) {
                return false;
            }

            return true;
        }

        @Override
        public int hashCode() {
            int result = supplierAliasName != null ? supplierAliasName.hashCode() : 0;
            result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
            return result;
        }
    }

}
