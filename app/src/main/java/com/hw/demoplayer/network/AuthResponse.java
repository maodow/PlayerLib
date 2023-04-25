package com.hw.demoplayer.network;

import com.hw.demoplayer.bean.ProductBean;
import java.util.List;

/**
 * 鉴权返回
 */
public class AuthResponse extends BaseResponse {

    private String desc;
    private List<ProductBean> products;


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public List<ProductBean> getProducts() {
        return products;
    }

    public void setProducts(List<ProductBean> products) {
        this.products = products;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code='" + code + '\'' +
                ", desc='" + desc + '\'' +
                ", products=" + products +
                '}';
    }

}
