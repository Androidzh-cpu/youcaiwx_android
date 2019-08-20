package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-21.  上午 11:54
 * FileName: MineOrderListBean
 * Description:TODO 订单列表
 */
public class MineOrderListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"order_num":"2019072981041124258032","is_live":2,"package_id":2,"pay_status":1,"pay_price":0,"package_name":"中文p2高清网课","price":"13800.00","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg"},{"order_num":"2019072981041089427884","is_live":2,"package_id":1,"pay_status":1,"pay_price":0,"package_name":"中文p1高清网课","price":"23800.00","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * order_num : 2019072981041124258032
         * is_live : 2
         * package_id : 2
         * pay_status : 1
         * pay_price : 0
         * package_name : 中文p2高清网课
         * price : 13800.00
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg
         */

        private String order_num;
        private int is_live;
        private int package_id;
        private int pay_status;
        private String pay_price;
        private String package_name;
        private String price;
        private String app_img;

        public String getOrder_num() {
            return order_num == null ? "" : order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public int getIs_live() {
            return is_live;
        }

        public void setIs_live(int is_live) {
            this.is_live = is_live;
        }

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public int getPay_status() {
            return pay_status;
        }

        public void setPay_status(int pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_price() {
            return pay_price == null ? "" : pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getPackage_name() {
            return package_name == null ? "" : package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public String getPrice() {
            return price == null ? "" : price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }
    }
}
