package com.psk.pms.model;

import java.util.List;

/**
 * Created by Sony on 05-11-2015.
 */
public class QuoteDetails {
    private Integer projId;
    private String itemName;
    private String projName;
    private String itemType;
    private String quoteDetailsValue;
    private List<SupplierQuoteDetails> supplierQuoteDetails;

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

    public List<SupplierQuoteDetails> getSupplierQuoteDetails() {
        return supplierQuoteDetails;
    }

    public void setSupplierQuoteDetails(List<SupplierQuoteDetails> supplierQuoteDetails) {
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

    public static class SupplierQuoteDetails {
        private String supplierAliasName;
        private String emailAddress;
        private String phoneNumber;
        private String quotedPrice;

        public SupplierQuoteDetails() {

        }

        public SupplierQuoteDetails(String supplierAliasName, String emailAddress, String phoneNumber, String quotedPrice) {
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
    }
}
