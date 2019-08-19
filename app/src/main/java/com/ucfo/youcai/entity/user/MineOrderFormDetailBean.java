package com.ucfo.youcai.entity.user;

/**
 * Author: AND
 * Time: 2019-6-21.  下午 5:24
 * Package: com.ucfo.youcai.adapter.user
 * FileName: MineOrderFormDetailBean
 * Description:TODO 订单详情
 */
public class MineOrderFormDetailBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"course":{"order_num":"2019061484191859089132","pay_status":3,"price":13800,"pay_price":0,"coupon_price":0,"add_time":"2019-06-14 11:49:51","package_id":1,"is_live":2,"teacher_name":"杨晔,王谦","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","package_name":"中文p1高清网课","study_days":180},"address":{"address_id":1,"consignee":"sd","telephone":"dsad","address":"dsadasdas"}}
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
        /**
         * course : {"order_num":"2019061484191859089132","pay_status":3,"price":13800,"pay_price":0,"coupon_price":0,"add_time":"2019-06-14 11:49:51","package_id":1,"is_live":2,"teacher_name":"杨晔,王谦","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","package_name":"中文p1高清网课","study_days":180}
         * address : {"address_id":1,"consignee":"sd","telephone":"dsad","address":"dsadasdas"}
         */

        private CourseBean course;
        private AddressBean address;

        public CourseBean getCourse() {
            return course;
        }

        public void setCourse(CourseBean course) {
            this.course = course;
        }

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public static class CourseBean {
            /**
             * order_num : 2019061484191859089132
             * pay_status : 3
             * price : 13800
             * pay_price : 0
             * coupon_price : 0
             * add_time : 2019-06-14 11:49:51
             * package_id : 1
             * is_live : 2
             * teacher_name : 杨晔,王谦
             * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg
             * package_name : 中文p1高清网课
             * study_days : 180
             */

            private String order_num;
            private int pay_status;
            private String price;
            private String pay_price;
            private String coupon_price;
            private String add_time;
            private int package_id;
            private int is_live;
            private String teacher_name;
            private String app_img;
            private String package_name;
            private int study_days;

            public String getOrder_num() {
                return order_num == null ? "" : order_num;
            }

            public void setOrder_num(String order_num) {
                this.order_num = order_num;
            }

            public int getPay_status() {
                return pay_status;
            }

            public void setPay_status(int pay_status) {
                this.pay_status = pay_status;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getPay_price() {
                return pay_price == null ? "" : pay_price;
            }

            public void setPay_price(String pay_price) {
                this.pay_price = pay_price;
            }

            public String getCoupon_price() {
                return coupon_price == null ? "" : coupon_price;
            }

            public void setCoupon_price(String coupon_price) {
                this.coupon_price = coupon_price;
            }

            public String getAdd_time() {
                return add_time == null ? "" : add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }

            public int getPackage_id() {
                return package_id;
            }

            public void setPackage_id(int package_id) {
                this.package_id = package_id;
            }

            public int getIs_live() {
                return is_live;
            }

            public void setIs_live(int is_live) {
                this.is_live = is_live;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getApp_img() {
                return app_img == null ? "" : app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }

            public String getPackage_name() {
                return package_name == null ? "" : package_name;
            }

            public void setPackage_name(String package_name) {
                this.package_name = package_name;
            }

            public int getStudy_days() {
                return study_days;
            }

            public void setStudy_days(int study_days) {
                this.study_days = study_days;
            }
        }

        public static class AddressBean {
            /**
             * address_id : 1
             * consignee : sd
             * telephone : dsad
             * address : dsadasdas
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

            @Override
            public String toString() {
                return "AddressBean{" +
                        "address_id=" + address_id +
                        ", consignee='" + consignee + '\'' +
                        ", telephone='" + telephone + '\'' +
                        ", address='" + address + '\'' +
                        '}';
            }
        }
    }
}
