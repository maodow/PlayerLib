package com.hw.demoplayer.network;

/**
 * 支付申请返回
 *  样例：
 *  {
 *     "data":"http://111.48.254.19:8097/pay/pro/index.html?flag=1&productCode=IPTV_PER_01&isBlack=false&validCode=0&validDesc=成功&validPass=true&orderId=129011975204250&timeStamp=20221013151301&account=5113304547B02&orderNo=hzju2022913121648211&retentionOrderNo=¬ifyurl=http://172.25.243.162:8089/jyBizMgr/orderRelationship/paymentManagement&backUrl=http%3A//111.48.16.234%3A33200/EPG/jsp/testepg/en/page/transfer.html%3FdingIPTVO3%3DIPTV_PER_01%26categoryCode%3D%26programCode%3D6732336%26programType%3D14%26parentProgramCode%3D6732244%26contentCode%3DHBGD7707453463196508165369336899%26sitnum%3D0%26returnurl%3Dhttp%253A//111.48.16.234%253A33200/EPG/jsp/testepg/en/page/detail-much.jsp%253FcategoryCode%253D%2526programCode%253D6732244%2526contentCode%253DHBGD7707453463196508165369336899%2526programType%253D1%2526returnurl%253Dhttp%25253A%25252F%25252F111.48.16.234%25253A33200%25252FEPG%25252Fjsp%25252Ftestepg%25252Fen%25252Fpage%25252Findex.jsp%25253FboxId%25253D2%252526indexId%25253D0%252526box1Index%25253D2%2526boxId%253D2%2526dark6%253D0%2526indexId%253D1&isIptvUser=true",
 *     "success":true
 *  }
 */
public class PayResponse<T> extends BaseResponse<T> {

    private boolean success;


    @Override
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "PayResponse{" +
                "success=" + success +
                '}';
    }
}
