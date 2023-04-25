package com.hw.demoplayer.utils;

import org.apache.commons.codec.binary.Base16;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import java.util.Random;

/**
 * 鉴权工具类
 */
public class AuthUtils {

    private static AuthUtils mInstance;

    private AuthUtils() {
    }

    public static AuthUtils getInstance() {
        if (null == mInstance) {
            synchronized (AuthUtils.class) {
                if (null == mInstance) {
                    mInstance = new AuthUtils();
                }
            }
        }
        return mInstance;
    }


    private String getSnCode(String label, int length){
        String snCode = TimeUtils.getNowString(TimeUtils.getSafeDateFormat("yyyyMMddHHmmss"));
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(rand.nextInt(9));
        }
        return label + snCode + sb;
    }


    /**
     *  局方接口安全机制：跳转支付，发起支付申请时，
     *   请求Header中的Authorization的token字段
     *
     *   附：token的计算过程
     *     1、使用请求报文源文进行MD5的散列运算得到请求报文源文的散列值(byte[]数组，带不可见字符)；
     2、使用HMAC-SHA-256对第一步计算出来的散列值进行单项加密，得到加密的结果（byte[]数组，带不可见字符）；
     3、使用Base16对第二步的加密结果进行转换成可见字符。
     */
    public String makeToken(String appKey, String body) {
        byte[] md5 = DigestUtils.md5(body);
        HmacUtils hmacUtils = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, appKey.getBytes());
        byte[] hmaByte = hmacUtils.hmac(md5);
        return new Base16().encodeAsString(hmaByte);
    }

}