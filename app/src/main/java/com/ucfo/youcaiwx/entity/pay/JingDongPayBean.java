package com.ucfo.youcaiwx.entity.pay;

/**
 * Author: AND
 * Time: 2019-11-8.  下午 5:13
 * Package: com.ucfo.youcaiwx.entity.pay
 * FileName: JingDongPayBean
 * Description:TODO 京东支付
 */
public class JingDongPayBean {
    private String version = "V2.0";
    private String merchant = "111934986001";
    private String device = "111";
    private String tradeNum;
    private String tradeName = "p1";
    private String tradeDesc = "p1";
    private String tradeTime;
    private String amount;
    private String currency = "CNY";
    private String note = "beizhu";
    private String callbackUrl = "http://localhost/jdPay2Demo/com/jdjr/pay/demo/action/CallBack.php";
    private String notifyUrl = "http://localhost/jdPay2Demo/com/jdjr/pay/demo/action/AsnyNotify.php";
    private String ip = "10.45.251.153";
    private String userType = "";
    private String userId;
    private String expireTime = "";
    private String industryCategoryCode = "";
    private String orderType = "1";
    private String specCardNo = "";
    private String specId = "";
    private String specName = "";
    private String saveUrl = "https://wepay.jd.com/jdpay/saveOrder";

    public String getVersion() {
        return version == null ? "" : version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMerchant() {
        return merchant == null ? "" : merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }

    public String getDevice() {
        return device == null ? "" : device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getTradeNum() {
        return tradeNum == null ? "" : tradeNum;
    }

    public void setTradeNum(String tradeNum) {
        this.tradeNum = tradeNum;
    }

    public String getTradeName() {
        return tradeName == null ? "" : tradeName;
    }

    public void setTradeName(String tradeName) {
        this.tradeName = tradeName;
    }

    public String getTradeDesc() {
        return tradeDesc == null ? "" : tradeDesc;
    }

    public void setTradeDesc(String tradeDesc) {
        this.tradeDesc = tradeDesc;
    }

    public String getTradeTime() {
        return tradeTime == null ? "" : tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getAmount() {
        return amount == null ? "" : amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency == null ? "" : currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getNote() {
        return note == null ? "" : note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCallbackUrl() {
        return callbackUrl == null ? "" : callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getNotifyUrl() {
        return notifyUrl == null ? "" : notifyUrl;
    }

    public void setNotifyUrl(String notifyUrl) {
        this.notifyUrl = notifyUrl;
    }

    public String getIp() {
        return ip == null ? "" : ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getUserType() {
        return userType == null ? "" : userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserId() {
        return userId == null ? "" : userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getExpireTime() {
        return expireTime == null ? "" : expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    public String getIndustryCategoryCode() {
        return industryCategoryCode == null ? "" : industryCategoryCode;
    }

    public void setIndustryCategoryCode(String industryCategoryCode) {
        this.industryCategoryCode = industryCategoryCode;
    }

    public String getOrderType() {
        return orderType == null ? "" : orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getSpecCardNo() {
        return specCardNo == null ? "" : specCardNo;
    }

    public void setSpecCardNo(String specCardNo) {
        this.specCardNo = specCardNo;
    }

    public String getSpecId() {
        return specId == null ? "" : specId;
    }

    public void setSpecId(String specId) {
        this.specId = specId;
    }

    public String getSpecName() {
        return specName == null ? "" : specName;
    }

    public void setSpecName(String specName) {
        this.specName = specName;
    }

    public String getSaveUrl() {
        return saveUrl == null ? "" : saveUrl;
    }

    public void setSaveUrl(String saveUrl) {
        this.saveUrl = saveUrl;
    }
}
