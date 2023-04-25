package com.hw.demoplayer.network;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * des:通用api
 */

public interface BaseAPI {

    @Streaming
    @GET
    Call<ResponseBody> downloadFile(@Url String   fileUrl);

}
