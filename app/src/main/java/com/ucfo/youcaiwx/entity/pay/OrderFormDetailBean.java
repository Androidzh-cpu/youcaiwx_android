package com.ucfo.youcaiwx.entity.pay;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:36
 * FileName: OrderFormDetailBean
 * Description:TODO 订单详情
 */
public class OrderFormDetailBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"address":{"address_id":52,"consignee":"林耀东","telephone":"17610116887","address":"广东省东山市塔寨村"},"coupon_num":1,"packages":{"package_id":3,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190614/f18f1144499a05782700b96a4da90180.jpeg","name":"中文高清网课套餐包","view_class":1,"study_date":null,"study_days":730,"teacher_id":"5,6","price":"28000.00","teacher_name":"姚立,王谦","validity":-18114}}
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

        private AddressBean address;
        private PackagesBean packages;

        public AddressBean getAddress() {
            return address;
        }

        public void setAddress(AddressBean address) {
            this.address = address;
        }

        public PackagesBean getPackages() {
            return packages;
        }

        public void setPackages(PackagesBean packages) {
            this.packages = packages;
        }

        public static class AddressBean {
            /**
             * address_id : 52
             * consignee : 林耀东
             * telephone : 17610116887
             * address : 广东省东山市塔寨村
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

        public static class PackagesBean {
            /**
             * package_id : 3
             * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190614/f18f1144499a05782700b96a4da90180.jpeg
             * name : 中文高清网课套餐包
             * view_class : 1
             * study_date : null
             * study_days : 730
             * teacher_id : 5,6
             * price : 28000.00
             * teacher_name : 姚立,王谦
             * validity : -18114
             */

            private int package_id;
            private String app_img;
            private String name;
            private int view_class;
            private Object study_date;
            private String study_days;
            private String coupon_num;
            private String teacher_id;
            private String price;
            private String teacher_name;
            private String validity;

            public String getCoupon_num() {
                return coupon_num;
            }

            public void setCoupon_num(String coupon_num) {
                this.coupon_num = coupon_num;
            }

            public int getPackage_id() {
                return package_id;
            }

            public void setPackage_id(int package_id) {
                this.package_id = package_id;
            }

            public String getApp_img() {
                return app_img == null ? "" : app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getView_class() {
                return view_class;
            }

            public void setView_class(int view_class) {
                this.view_class = view_class;
            }

            public Object getStudy_date() {
                return study_date;
            }

            public void setStudy_date(Object study_date) {
                this.study_date = study_date;
            }

            public String getStudy_days() {
                return study_days;
            }

            public void setStudy_days(String study_days) {
                this.study_days = study_days;
            }

            public String getTeacher_id() {
                return teacher_id == null ? "" : teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getValidity() {
                return validity;
            }

            public void setValidity(String validity) {
                this.validity = validity;
            }
        }
    }
}


