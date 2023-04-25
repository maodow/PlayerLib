package com.hw.demoplayer.bean;

import java.io.Serializable;

public class AuthRequestBean implements Serializable {

    private String account;
    private String contentId;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    @Override
    public String toString() {
        return "AuthRequestBean{" +
                "account='" + account + '\'' +
                ", contentId='" + contentId + '\'' +
                '}';
    }
}
