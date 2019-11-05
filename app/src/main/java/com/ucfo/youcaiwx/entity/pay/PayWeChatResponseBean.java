package com.ucfo.youcaiwx.entity.pay;

import com.google.gson.annotations.SerializedName;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 10:01
 * Package: com.ucfo.youcaiwx.entity.pay
 * FileName: PayWeChatResponseBean
 * Description:TODO 从服务器获取的微信订单信息以调用App进行支付
 */
public class PayWeChatResponseBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"appid":"wx55d839e60fb4b35f","partnerid":"1317252201","prepayid":"wx0514060718551170397f56d41410763700","package":"Sign=WXPay","noncestr":"xyEzFQKXL7T1HKte","timestamp":1572933967,"sign":"9700054D79F7EF36C014DACC6B180A56"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        //应用id
        private String appid;
        //随机字符串
        private String noncestr;
        //扩展字段
        @SerializedName("package")
        private String packageX;
        //商户号
        private String partnerid;
        //预支付交易会话ID
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

        public String getNoncestr() {
            return noncestr == null ? "" : noncestr;
        }

        public void setNoncestr(String noncestr) {
            this.noncestr = noncestr;
        }

        public String getPackage() {
            return packageX == null ? "" : packageX;
        }

        public void setPackage(String packageX) {
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


    /* *//**
     * appid : wxa694a8105b02619a
     * code : RE1513750758639793
     * noncestr : NONCE1513750758880-1-1-1-1
     * package : Sign=WXPay
     * partnerid : 1494228292
     * prepayid : wx2017122014192124c6b22af30750915188
     * sign : 4A320D2B2EBFCD7BCF95698BB5D7C192
     * timestamp : 1513750758
     *//*

    //应用id
    private String appid;
    //随机字符串
    private String noncestr;
    //扩展字段
    @SerializedName("package")
    private String packageX;
    //商户号
    private String partnerid;
    //预支付交易会话ID
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

    public String getNoncestr() {
        return noncestr == null ? "" : noncestr;
    }

    public void setNoncestr(String noncestr) {
        this.noncestr = noncestr;
    }

    public String getPackage() {
        return packageX == null ? "" : packageX;
    }

    public void setPackage(String packageX) {
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
    }*/
}
