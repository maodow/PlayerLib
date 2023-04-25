package com.hw.demoplayer.bean;

import java.io.Serializable;

public class PayUrlBean implements Serializable {

    private String payUrl;

    public String getPayUrl() {
        return payUrl;
    }

    public void setPayUrl(String payUrl) {
        this.payUrl = payUrl;
    }

    @Override
    public String toString() {
        return "PayUrlBean{" +
                "payUrl='" + payUrl + '\'' +
                '}';
    }
}
