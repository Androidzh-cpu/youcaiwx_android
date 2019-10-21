package com.ucfo.youcaiwx.entity.integral;

/**
 * Author: AND
 * Time: 2019-10-18.  下午 2:44
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralOrderExchangeResultBean
 * Description:TODO 积分订单兑换结果
 */
public class IntegralOrderExchangeResultBean {

    /**
     * code : 200
     * message : 提示信息
     * data : {"order_num":"订单号","integral":"积分"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_num : 订单号
         * integral : 积分
         */

        private String order_num;
        private String integral;

        public String getOrder_num() {
            return order_num == null ? "" : order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getIntegral() {
            return integral == null ? "" : integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
