package com.ucfo.youcai.entity.pay;

/**
 * Author: AND
 * Time: 2019-8-7.  上午 9:43
 * Package: com.ucfo.youcai.entity.pay
 * FileName: CommitOrderFormBean
 * Description:TODO 添加订单
 */
public class CommitOrderFormBean {
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
        return msg == null ? "" : msg;
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
        private String order_num;
        private String package_id;
        private String name;
        private float pay_price;
        private String app_img;
        private String pc_img;
        private String wx_img;
        private String mobile_img;

        public String getOrder_num() {
            return order_num == null ? "" : order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPackage_id() {
            return package_id == null ? "" : package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getPc_img() {
            return pc_img == null ? "" : pc_img;
        }

        public void setPc_img(String pc_img) {
            this.pc_img = pc_img;
        }

        public String getWx_img() {
            return wx_img == null ? "" : wx_img;
        }

        public void setWx_img(String wx_img) {
            this.wx_img = wx_img;
        }

        public String getMobile_img() {
            return mobile_img == null ? "" : mobile_img;
        }

        public void setMobile_img(String mobile_img) {
            this.mobile_img = mobile_img;
        }

        public float getPay_price() {
            return pay_price;
        }

        public void setPay_price(float pay_price) {
            this.pay_price = pay_price;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "order_num='" + order_num + '\'' +
                    ", package_id='" + package_id + '\'' +
                    ", name='" + name + '\'' +
                    ", pay_price=" + pay_price +
                    ", app_img='" + app_img + '\'' +
                    ", pc_img='" + pc_img + '\'' +
                    ", wx_img='" + wx_img + '\'' +
                    ", mobile_img='" + mobile_img + '\'' +
                    '}';
        }
    }
}
