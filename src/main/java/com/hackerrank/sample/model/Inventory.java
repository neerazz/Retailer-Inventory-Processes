package com.hackerrank.sample.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Inventory {

    @Id
    private Long skuId;
    private String productName;
    private String productLabel;
    private int inventoryOnHand;
    private int minQtyReq;
    private Double price;

    public Inventory() {
    }

    public Long getSkuId() {
        return skuId;
    }

    public void setSkuId(Long skuId) {
        this.skuId = skuId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductLabel() {
        return productLabel;
    }

    public void setProductLabel(String productLabel) {
        this.productLabel = productLabel;
    }

    public int getInventoryOnHand() {
        return inventoryOnHand;
    }

    public void setInventoryOnHand(int inventoryOnHand) {
        this.inventoryOnHand = inventoryOnHand;
    }

    public int getMinQtyReq() {
        return minQtyReq;
    }

    public void setMinQtyReq(int minQtyReq) {
        this.minQtyReq = minQtyReq;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
