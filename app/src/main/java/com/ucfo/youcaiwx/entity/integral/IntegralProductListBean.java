package com.ucfo.youcaiwx.entity.integral;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 5:48
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralProductListBean
 * Description:TODO 积分兑换商品列表
 */
public class IntegralProductListBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":10,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/fddd96a1316f9972f0aaf5c722145187.jpeg","type":3,"integral_price":2000,"end_time":"2019-10-15 00:00:00","range":3,"coupon_id":17,"name":"满15000减2000","coupon_price":"2000","amount_price":"15000.00","is_type":1},{"id":2,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190823/f21c6f9627135700fd91ada8bbf47939.jpeg","type":3,"integral_price":123,"end_time":"2019-10-16 15:03:03","range":1,"coupon_id":16,"name":"满15000减2000","coupon_price":"2000","amount_price":"15000.00","is_type":1},{"id":6,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/ed8ca6813fa42372a7f8108ab45155c7.png","type":3,"integral_price":10,"end_time":"2019-10-26 00:00:00","range":1,"coupon_id":17,"name":"满15000减2000","coupon_price":"2000","amount_price":"15000.00","is_type":1}]
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
         * id : 10
         * image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/fddd96a1316f9972f0aaf5c722145187.jpeg
         * type : 3
         * integral_price : 2000
         * end_time : 2019-10-15 00:00:00
         * range : 3
         * coupon_id : 17
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
        private int coupon_id;
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

        public int getCoupon_id() {
            return coupon_id;
        }

        public void setCoupon_id(int coupon_id) {
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
}
