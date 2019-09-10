package com.ucfo.youcaiwx.entity.pay;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 10:01
 * Package: com.ucfo.youcaiwx.entity.pay
 * FileName: PayWeChatResponseBean
 * Description:TODO 从服务器获取的微信订单信息以调用App进行支付
 */
public class PayWeChatResponseBean {
    /**
     * appid : wxa694a8105b02619a
     * code : RE1513750758639793
     * noncestr : NONCE1513750758880-1-1-1-1
     * package : Sign=WXPay
     * partnerid : 1494228292
     * prepayid : wx2017122014192124c6b22af30750915188
     * sign : 4A320D2B2EBFCD7BCF95698BB5D7C192
     * timestamp : 1513750758
     */

    //应用id
    private String appid;
    //状态码
    private String code;
    //随机支付
    private String noncestr;

    private String packageX;
    //商户号
    private String partnerid;
    //预支付id
    private String prepayid;
    //时间戳
    private String timestamp;
    //签名
    private String sign;

    public String getAppid() {
        return appid == null ? "" : appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public String getCode() {
        return code == null ? "" : code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getNoncestr() {
        return noncestr == null ? "" : noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackageX() {
        return packageX == null ? "" : packageX;
    }

    public void setPackageX(String packageX) {
        this.packageX = packageX;
    }

    public String getPartnerid() {
        return partnerid == null ? "" : partnerid;
    }

    public void setPartnerid(String partnerid) {
        this.partnerid = partnerid;
    }

    public String getPrepayid() {
        return prepayid == null ? "" : prepayid;
    }

    public void setPrepayid(String prepayid) {
        this.prepayid = prepayid;
    }

    public String getTimestamp() {
        return timestamp == null ? "" : timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getSign() {
        return sign == null ? "" : sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
