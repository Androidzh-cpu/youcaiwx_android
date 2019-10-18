package com.ucfo.youcaiwx.entity.integral;

/**
 * Author: AND
 * Time: 2019-10-17.  下午 4:38
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralAddOrderNumBean
 * Description:TODO 添加订单
 */
public class IntegralAddOrderNumBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"address":{"address_id":6,"consignee":"阿迪达斯","telephone":"15695426961","address":"北京市朝阳区"},"goods":{"id":10,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/fddd96a1316f9972f0aaf5c722145187.jpeg","type":3,"name":"满15000减2000","end_time":"2019-10-15 00:00:00","integral_price":2000,"range":3,"type_id":17,"number":10,"coupon_id":17,"coupon_price":"2000","amount_price":"15000.00","is_type":1}}
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
        /**
         * address : {"address_id":6,"consignee":"阿迪达斯","telephone":"15695426961","address":"北京市朝阳区"}
         * goods : {"id":10,"image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/fddd96a1316f9972f0aaf5c722145187.jpeg","type":3,"name":"满15000减2000","end_time":"2019-10-15 00:00:00","integral_price":2000,"range":3,"type_id":17,"number":10,"coupon_id":17,"coupon_price":"2000","amount_price":"15000.00","is_type":1}
         */

        private AddressBean address;
        private GoodsBean goods;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public GoodsBean getGoods() {
            return goods;
        }

        public void setGoods(GoodsBean goods) {
            this.goods = goods;
        }

        public static class AddressBean {
            /**
             * address_id : 6
             * consignee : 阿迪达斯
             * telephone : 15695426961
             * address : 北京市朝阳区
             */

            private int address_id;
            private String consignee;
            private String telephone;
            private String address;

            public int getAddress_id() {
                return address_id;
            }

            public void setAddress_id(int address_id) {
                this.address_id = address_id;
            }

            public String getConsignee() {
                return consignee == null ? "" : consignee;
            }

            public void setConsignee(String consignee) {
                this.consignee = consignee;
            }

            public String getTelephone() {
                return telephone == null ? "" : telephone;
            }

            public void setTelephone(String telephone) {
                this.telephone = telephone;
            }

            public String getAddress() {
                return address == null ? "" : address;
            }

            public void setAddress(String address) {
                this.address = address;
            }
        }

        public static class GoodsBean {
            /**
             * id : 10
             * image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191014/fddd96a1316f9972f0aaf5c722145187.jpeg
             * type : 3
             * name : 满15000减2000
             * end_time : 2019-10-15 00:00:00
             * integral_price : 2000
             * range : 3
             * type_id : 17
             * number : 10
             * coupon_id : 17
             * coupon_price : 2000
             * amount_price : 15000.00
             * is_type : 1
             */

            private String id;
            private String image;
            private String type;
            private String name;
            private String end_time;
            private String integral_price;
            private String range;
            private String type_id;
            private String number;
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

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getEnd_time() {
                return end_time == null ? "" : end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getIntegral_price() {
                return integral_price == null ? "" : integral_price;
            }

            public void setIntegral_price(String integral_price) {
                this.integral_price = integral_price;
            }

            public String getRange() {
                return range == null ? "" : range;
            }

            public void setRange(String range) {
                this.range = range;
            }

            public String getType_id() {
                return type_id == null ? "" : type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getNumber() {
                return number == null ? "" : number;
            }

            public void setNumber(String number) {
                this.number = number;
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
}
