package com.ucfo.youcai.entity.pay;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:36
 * Package: com.ucfo.youcai.entity.pay
 * FileName: OrderFormDetailBean
 * Description:TODO 订单详情
 */
public class OrderFormDetailBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"coupon_num":1,"packages":{"package_id":1,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","name":"中文p1高清网课","view_class":1,"study_date":"2019-08-01 16:00:00","study_days":180,"teacher_id":"4,5","price":"23800.00","teacher_name":"吴宁,姚立","validity":-3}}
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
         * coupon_num : 1
         * packages : {"package_id":1,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","name":"中文p1高清网课","view_class":1,"study_date":"2019-08-01 16:00:00","study_days":180,"teacher_id":"4,5","price":"23800.00","teacher_name":"吴宁,姚立","validity":-3}
         */

        private int coupon_num;
        private PackagesBean packages;

        public int getCoupon_num() {
            return coupon_num;
        }

        public void setCoupon_num(int coupon_num) {
            this.coupon_num = coupon_num;
        }

        public PackagesBean getPackages() {
            return packages;
        }

        public void setPackages(PackagesBean packages) {
            this.packages = packages;
        }

        public static class PackagesBean {
            /**
             * package_id : 1
             * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg
             * name : 中文p1高清网课
             * view_class : 1
             * study_date : 2019-08-01 16:00:00
             * study_days : 180
             * teacher_id : 4,5
             * price : 23800.00
             * teacher_name : 吴宁,姚立
             * validity : -3
             */

            private int package_id;
            private String app_img;
            private String name;
            private int view_class;
            private String study_date;
            private int study_days;
            private String teacher_id;
            private String price;
            private String teacher_name;
            private int validity;

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

            public String getStudy_date() {
                return study_date == null ? "" : study_date;
            }

            public void setStudy_date(String study_date) {
                this.study_date = study_date;
            }

            public int getStudy_days() {
                return study_days;
            }

            public void setStudy_days(int study_days) {
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

            public int getValidity() {
                return validity;
            }

            public void setValidity(int validity) {
                this.validity = validity;
            }
        }
    }
}
