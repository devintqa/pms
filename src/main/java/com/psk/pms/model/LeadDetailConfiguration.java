package com.psk.pms.model;

import java.util.List;

/**
 * Created by prakashbhanu57 on 9/19/2015.
 */
public class LeadDetailConfiguration {

    private String employeeId;
    private int projectId;
    private int subProjectId;
    private List<LeadDetail> leadDetails;
    private String leadConfiguration;
    public LeadDetailConfiguration() {
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getSubProjectId() {
        return subProjectId;
    }

    public void setSubProjectId(int subProjectId) {
        this.subProjectId = subProjectId;
    }

    public List<LeadDetail> getLeadDetails() {
        return leadDetails;
    }

    public void setLeadDetails(List<LeadDetail> leadDetails) {
        this.leadDetails = leadDetails;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeadConfiguration() {
        return leadConfiguration;
    }

    public void setLeadConfiguration(String leadConfiguration) {
        this.leadConfiguration = leadConfiguration;
    }

    public static class LeadDetail {

        public LeadDetail() {
        }

        public LeadDetail(String leadDetailId, String material, String sourceOfSupply, String distance,String unit, String cost,
                          String ic, String leadCharges, String loadingUnloading, String total, String lastUpdatedBy) {
            this.leadDetailId = leadDetailId;
            this.material = material;
            this.sourceOfSupply = sourceOfSupply;
            this.distance = distance;
            this.unit = unit;
            this.cost = cost;
            this.ic = ic;
            this.leadCharges = leadCharges;
            this.loadingUnloading = loadingUnloading;
            this.total = total;
            this.lastUpdatedBy = lastUpdatedBy;
        }

        private String leadDetailId;
        private String material;
        private String sourceOfSupply;
        private String distance;
        private String unit;
        private String cost;
        private String ic;
        private String leadCharges;
        private String loadingUnloading;
        private String total;
        private String lastUpdatedBy;

        public String  getLeadDetailId() {
            return leadDetailId;
        }

        public void setLeadDetailId(String leadDetailId) {
            this.leadDetailId = leadDetailId;
        }

        public String getMaterial() {
            return material;
        }

        public void setMaterial(String material) {
            this.material = material;
        }

        public String getSourceOfSupply() {
            return sourceOfSupply;
        }

        public void setSourceOfSupply(String sourceOfSupply) {
            this.sourceOfSupply = sourceOfSupply;
        }

        public String getDistance() {
            return distance;
        }

        public void setDistance(String distance) {
            this.distance = distance;
        }

        public String getCost() {
            return cost;
        }

        public void setCost(String cost) {
            this.cost = cost;
        }

        public String getIc() {
            return ic;
        }

        public void setIc(String ic) {
            this.ic = ic;
        }

        public String getLeadCharges() {
            return leadCharges;
        }

        public void setLeadCharges(String leadCharges) {
            this.leadCharges = leadCharges;
        }

        public String getLoadingUnloading() {
            return loadingUnloading;
        }

        public void setLoadingUnloading(String loadingUnloading) {
            this.loadingUnloading = loadingUnloading;
        }

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getLastUpdatedBy() {
            return lastUpdatedBy;
        }

        public void setLastUpdatedBy(String lastUpdatedBy) {
            this.lastUpdatedBy = lastUpdatedBy;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }
    }
}
