package com.hw.demoplayer.network.interceptor;

import com.hw.demoplayer.utils.AuthUtils;
import com.hw.demoplayer.utils.Constants;
import com.hw.demoplayer.utils.LogUtil;

import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 有关请求的参数设置(请求头)
 */
public class HeadInterceptor implements Interceptor {

    public HeadInterceptor() {
        super();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("Content-Type", "application/json;charset=UTF-8");
        builder.addHeader("Accept", "application/json");
        String token = AuthUtils.getInstance().makeToken(Constants.APP_KEY, builder.build().body().toString());
        builder.addHeader("Authorization", "HDCAUTH appid=\"" + Constants.APP_ID + "\",token=\"" + token + "\"");
        Request request = builder.build();
        return chain.proceed(request);
    }
}
