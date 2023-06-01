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
     * 用户鉴权
     * */
    @Headers({"Content-Type: application/json","Accept: application/json"})
    @POST("pay/1.0/pay/iptv/auth")
    Observable<AuthResponse> getAuth(@Body RequestBody body);


    /**
     * 获取支付页的H5地址
     *  productcode=IPTV_PER_01
     *  &transid=hzju2022913121648210
     *  &orderno=hzju2022913121648211
     *  &money=3900
     *  &account=5113304547B02
     *  &notifyurl=http://172.25.243.162:8089/jyBizMgr/orderRelationship/paymentManagement
     *  &backurl=http%253A//111.48.16.234%253A33200/EPG/jsp/testepg/en/page/transfer.html%253FdingIPTVO3%253DIPTV_PER_01%2526categoryCode%253D%2526programCode%253D6732336%2526programType%253D14%2526parentProgramCode%253D6732244%2526contentCode%253DHBGD7707453463196508165369336899%2526sitnum%253D0%2526returnurl%253Dhttp%25253A//111.48.16.234%25253A33200/EPG/jsp/testepg/en/page/detail-much.jsp%25253FcategoryCode%25253D%252526programCode%25253D6732244%252526contentCode%25253DHBGD7707453463196508165369336899%252526programType%25253D1%252526returnurl%25253Dhttp%2525253A%2525252F%2525252F111.48.16.234%2525253A33200%2525252FEPG%2525252Fjsp%2525252Ftestepg%2525252Fen%2525252Fpage%2525252Findex.jsp%2525253FboxId%2525253D2%25252526indexId%2525253D0%25252526box1Index%2525253D2%252526boxId%25253D2%252526dark6%25253D0%252526indexId%25253D1
     *  &paymode=9
     *  &productcount=1
     *  &productunit=%E4%B8%AA
     *  &hvstoken=10CDC5DB38766C33337C246C8F337C35
     *  &rightspcode=HBGD7707453463196508165369336899
     * */
    @POST("pay/1.0/hop/svc/pay/toPay.ajax")
    Observable<PayResponse<String>> getPayUrl(@Query("productcode") String productcode,
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
