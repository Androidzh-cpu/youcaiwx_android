package com.ucfo.youcaiwx.entity.integral;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 4:10
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralShopHomeBean
 * Description:TODO 积分商城首页
 */
public class IntegralShopHomeBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"coupon":[{"id":2,"image":"666","type":3,"integral_price":123,"end_time":"2019-10-20 14:28:42","range":1,"coupon_id":16,"name":"满15000减2000","coupon_price":"2000","amount_price":"15000.00","is_type":1}],"shop":[{"id":4,"image":"666","type":1,"integral_price":123,"name":"10"},{"id":3,"image":"666","type":1,"integral_price":123,"name":"10"}]}
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
        private List<CouponBean> coupon;
        private List<ShopBean> shop;

        public List<CouponBean> getCoupon() {
            if (coupon == null) {
                return new ArrayList<>();
            }
            return coupon;
        }

        public void setCoupon(List<CouponBean> coupon) {
            this.coupon = coupon;
        }

        public List<ShopBean> getShop() {
            if (shop == null) {
                return new ArrayList<>();
            }
            return shop;
        }

        public void setShop(List<ShopBean> shop) {
            this.shop = shop;
        }

        public static class CouponBean {
            /**
             * id : 2
             * image : 666
             * type : 3
             * integral_price : 123
             * end_time : 2019-10-20 14:28:42
             * range : 1
             * coupon_id : 16
             * name : 满15000减2000
             * coupon_price : 2000
             * amount_price : 15000.00
             * is_type : 1
             */

            private int id;
            private String image;
            private String type;
            private String integral_price;
            private String end_time;
            private String range;
            private String coupon_id;
            private String name;
            private String coupon_price;
            private String amount_price;
            private String is_type;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getImage() {
                return image == null ? "" : image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIntegral_price() {
                return integral_price == null ? "" : integral_price;
            }

            public void setIntegral_price(String integral_price) {
                this.integral_price = integral_price;
            }

            public String getEnd_time() {
                return end_time == null ? "" : end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getRange() {
                return range == null ? "" : range;
            }

            public void setRange(String range) {
                this.range = range;
            }

            public String getCoupon_id() {
                return coupon_id == null ? "" : coupon_id;
            }

            public void setCoupon_id(String coupon_id) {
                this.coupon_id = coupon_id;
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

            public String getAmount_price() {
                return amount_price == null ? "" : amount_price;
            }

            public void setAmount_price(String amount_price) {
                this.amount_price = amount_price;
            }

            public String getIs_type() {
                return is_type == null ? "" : is_type;
            }

            public void setIs_type(String is_type) {
                this.is_type = is_type;
            }
        }

        public static class ShopBean {
            /**
             * id : 4
             * image : 666
             * type : 1
             * integral_price : 123
             * name : 10
             */
            private String id;
            private String image;
            private String type;
            private String integral_price;
            private String name;

            public String getId() {
                return id == null ? "" : id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getImage() {
                return image == null ? "" : image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getIntegral_price() {
                return integral_price == null ? "" : integral_price;
            }

            public void setIntegral_price(String integral_price) {
                this.integral_price = integral_price;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
