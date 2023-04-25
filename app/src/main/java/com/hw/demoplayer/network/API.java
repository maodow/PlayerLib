package com.hw.demoplayer.network;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * 说明：interface
 */
public interface API {

    /**
     * 请求鉴权
     * */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("pay/1.0/pay/iptv/auth")
    Observable<AuthResponse> getAuth(@Body RequestBody body);


    /**
     * 支付申请
     *
     * */
    @POST("pay/1.0/hop/svc/pay/toPay.ajax")
    Observable<PayResponse> goPay(@Query("productcode") String productcode,
                                  @Query("transid") String transid,
                                  @Query("orderno") String orderno,
                                  @Query("money") String money,
                                  @Query("account") String account,
                                  @Query("notifyurl") String notifyurl,
                                  @Query("backurl") String backurl,
                                  @Query("paymode") int paymode,
                                  @Query("productcount") int productcount,
                                  @Query("productunit") String productunit,
                                  @Query("hvstoken") String hvstoken,
                                  @Query("rightspcode") String rightspcode);

}
