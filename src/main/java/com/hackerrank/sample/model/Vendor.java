package com.hackerrank.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Vendor {

    @Id
    private Long vendorId;
    private String vendorName;
    private String vendorContactNo;
    private String vendorEmail;
    private String vendorUsername;
    private String vendorAddress;

    public Vendor() {
    }

    public Long getVendorId() {
        return vendorId;
    }

    public void setVendorId(Long vendorId) {
        this.vendorId = vendorId;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorContactNo() {
        return vendorContactNo;
    }

    public void setVendorContactNo(String vendorContactNo) {
        this.vendorContactNo = vendorContactNo;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorUsername() {
        return vendorUsername;
    }

    public void setVendorUsername(String vendorUsername) {
        this.vendorUsername = vendorUsername;
    }

    public String getVendorAddress() {
        return vendorAddress;
    }

    public void setVendorAddress(String vendorAddress) {
        this.vendorAddress = vendorAddress;
    }
}
