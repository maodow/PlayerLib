package com.hw.demoplayer.network;

import android.text.TextUtils;
import java.io.Serializable;

/**
 * des:解析数据基类
 */
public class BaseResponse<T> implements Serializable {

    public static final String RESPONSE_ERROR = "0";//请求失败
    private static final long serialVersionUID = 1654943301630344850L;

    public T data;

    public boolean isSuccess() {
        return TextUtils.isEmpty(code) ? false : "1".equals(code);
    }

    public String code;
    public String msg;

}
