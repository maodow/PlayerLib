package com.hw.demoplayer.network;

import com.hw.demoplayer.bean.ProductBean;
import java.util.List;

/**
 * 鉴权返回
 *  样例：
 *   {
 *     "code":"01",
 *     "desc":"鉴权不通过",
 *     "products":[
 *         {
 *             "productDesc":"为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场所有影视节目。",
 *             "orderType":"1",
 *             "expiryDateUnit":"2",
 *             "productCode":"IPTV_PER_01",
 *             "price":3900,
 *             "productName":"IPTV-超级影视单月包",
 *             "expiryDateNum":"31"
 *         },
 *         {
 *             "productDesc":"为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场、综艺所有影视节目。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_45",
 *             "price":7800,
 *             "productName":"IPTV-超级影视季包"
 *         },
 *         {
 *             "productDesc":"订购赠送超级综艺包，最新院线大片、海内外精选好剧、爆款综艺一价尊享。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_03",
 *             "price":2900,
 *             "productName":"IPTV-超级影视优惠包"
 *         },
 *         {
 *             "productDesc":"为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场所有影视节目。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_67",
 *             "price":22800,
 *             "productName":"IPTV-超级影视年包（畅享）"
 *         },
 *         {
 *             "productDesc":"一价享受全站优质付费内容，含全量付费点播及付费直播频道。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_63",
 *             "price":4900,
 *             "productName":"IPTV-超级尊享包"
 *         },
 *         {
 *             "productDesc":"为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场所有影视节目。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_49",
 *             "price":12000,
 *             "productName":"IPTV-超级影视特惠年包"
 *         },
 *         {
 *             "productDesc":"最新院线大片、口碑佳片，国产热播剧、精品海外剧，活动期间，新用户1元可享受全量付费影视、综艺内容7天体验权益。",
 *             "orderType":"1",
 *             "expiryDateUnit":"3",
 *             "productCode":"IPTV_PER_134",
 *             "price":100,
 *             "productName":"IPTV-超级影视7天体验包",
 *             "expiryDateNum":"0"
 *         },
 *         {
 *             "productDesc":"为您提供国内外最新、最热、最经典大片佳剧，畅享电影、剧场、综艺所有影视节目。",
 *             "orderType":"1",
 *             "expiryDateUnit":"6",
 *             "productCode":"IPTV_PER_128",
 *             "price":27600,
 *             "productName":"IPTV-超级影视年包",
 *             "expiryDateNum":"0"
 *         },
 *         {
 *             "productDesc":"IPTV-超级影视包，产品已停售。",
 *             "orderType":"2",
 *             "productCode":"IPTV_MON_01",
 *             "price":3900,
 *             "productName":"IPTV-超级影视包"
 *         }
 *     ]
 * }
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
