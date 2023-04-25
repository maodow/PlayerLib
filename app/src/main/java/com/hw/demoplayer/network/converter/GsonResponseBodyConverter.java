package com.hw.demoplayer.network.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.hw.demoplayer.utils.LogUtil;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * Create by 高岳 on 2017/11/29.
 * des:网络请求gson封装--数据统一解析
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    GsonResponseBodyConverter(TypeAdapter<T> adapter, Gson gson) {
        this.adapter = adapter;
        this.gson = gson;
    }


    @Override
    public T convert(ResponseBody value) throws IOException {
        String body = value.string();
        LogUtil.e("response:" + body);
        body = body.replaceAll("%(?![0-9a-fA-F]{2})", "%25");
        T response = adapter.fromJson(body);
        return response;
    }
}
