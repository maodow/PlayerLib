package com.hw.demoplayer.network;

import com.hw.demoplayer.utils.ProgressListener;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * des:
 */

public class NetWordInterceptor implements Interceptor {

    private ProgressListener progressListener;

    public NetWordInterceptor(ProgressListener progressListener){
        this.progressListener = progressListener;
    }


    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder().body(
                new ProgressResponseBody(originalResponse.body(), progressListener))
                .build();
    }
}
