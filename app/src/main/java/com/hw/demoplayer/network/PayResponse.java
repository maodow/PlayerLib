package com.hw.demoplayer.network;

/**
 * 支付申请返回
 */
public class PayResponse extends BaseResponse {

    private String data; //支付H5地址


    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PayResponse{" +
                "data='" + data + '\'' +
                '}';
    }

}
