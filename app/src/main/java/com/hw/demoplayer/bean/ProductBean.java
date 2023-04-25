package com.hw.demoplayer.bean;

import java.io.Serializable;

public class ProductBean implements Serializable {

    private String productDesc; //为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场所有影视节目。",
    private String orderType; //1",
    private String expiryDateUnit; //2",
    private String productCode; //IPTV_PER_01",
    private String price; //3900,
    private String productName; //IPTV-超级影视单月包",
    private String expiryDateNum; //31"

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getExpiryDateUnit() {
        return expiryDateUnit;
    }

    public void setExpiryDateUnit(String expiryDateUnit) {
        this.expiryDateUnit = expiryDateUnit;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getExpiryDateNum() {
        return expiryDateNum;
    }

    public void setExpiryDateNum(String expiryDateNum) {
        this.expiryDateNum = expiryDateNum;
    }

    @Override
    public String toString() {
        return "ProductBean{" +
                "productDesc='" + productDesc + '\'' +
                ", orderType='" + orderType + '\'' +
                ", expiryDateUnit='" + expiryDateUnit + '\'' +
                ", productCode='" + productCode + '\'' +
                ", price='" + price + '\'' +
                ", productName='" + productName + '\'' +
                ", expiryDateNum='" + expiryDateNum + '\'' +
                '}';
    }
}
