package com.ucfo.youcaiwx.entity.integral;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-14.  下午 1:45
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralExchangeRecordBean
 * Description:TODO 积分兑换记录
 */
public class IntegralExchangeRecordBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":21,"name":"紫砂杯","detailed":"123","uptime":"2019-10-18 15:29:09","good_id":3,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190823/f21c6f9627135700fd91ada8bbf47939.jpeg","type":1,"type_id":0},{"id":22,"name":"5.8折优惠价","detailed":"123","uptime":"2019-10-18 15:34:05","good_id":2,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190823/f21c6f9627135700fd91ada8bbf47939.jpeg","type":3,"type_id":18,"coupon_id":18,"coupon_price":"5.8","amount_price":null,"is_type":2},{"id":23,"name":"玻璃杯","detailed":"123","uptime":"2019-10-18 17:37:17","good_id":4,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190823/f21c6f9627135700fd91ada8bbf47939.jpeg","type":1,"type_id":1}]
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
         * id : 21
         * name : 紫砂杯
         * detailed : 123
         * uptime : 2019-10-18 15:29:09
         * good_id : 3
         * image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190823/f21c6f9627135700fd91ada8bbf47939.jpeg
         * type : 1
         * type_id : 0
         * coupon_id : 18
         * coupon_price : 5.8
         * amount_price : null
         * is_type : 2
         */

        private String id;
        private String name;
        private String detailed;
        private String uptime;
        private String good_id;
        private String image;
        private String type;
        private String type_id;
        private String coupon_id;
        private String coupon_price;
        private String amount_price;
        private String is_type;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetailed() {
            return detailed == null ? "" : detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public String getUptime() {
            return uptime == null ? "" : uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public String getGood_id() {
            return good_id == null ? "" : good_id;
        }

        public void setGood_id(String good_id) {
            this.good_id = good_id;
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

        public String getType_id() {
            return type_id == null ? "" : type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getCoupon_id() {
            return coupon_id == null ? "" : coupon_id;
        }

        public void setCoupon_id(String coupon_id) {
            this.coupon_id = coupon_id;
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
