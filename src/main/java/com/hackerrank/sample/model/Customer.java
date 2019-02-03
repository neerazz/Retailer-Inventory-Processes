package com.hackerrank.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    private Long customerId;
    private String customerName;
    private Long contactNumber;
    private String address;
    private String gender;

    public Customer() {
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Long getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Long contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", contactNumber=" + contactNumber +
                ", address='" + address + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
