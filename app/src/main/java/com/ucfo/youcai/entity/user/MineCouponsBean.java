package com.ucfo.youcai.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-30.  上午 9:53
 * Package: com.ucfo.youcai.entity.user
 * FileName: MineCouponsBean
 * Description:TODO
 * Detail:TODO
 */
public class MineCouponsBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"coupon_id":30,"range":1,"name":null,"coupon_price":null,"amount_price":null}]
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
         * coupon_id : 30
         * range : 1
         * name : null
         * coupon_price : null
         * amount_price : null
         */

        private int coupon_id;
        private int range;
        private String name;
        private String coupon_price;
        private String end_time;
        private int is_type;

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
            this.coupon_id = coupon_id;
        }

        public int getRange() {
            return range;
        }

        public void setRange(int range) {
            this.range = range;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCoupon_price() {
            return coupon_price == null ? "" : coupon_price;
        }

        public void setCoupon_price(String coupon_price) {
            this.coupon_price = coupon_price;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public int getIs_type() {
            return is_type;
        }

        public void setIs_type(int is_type) {
            this.is_type = is_type;
        }
    }
}
