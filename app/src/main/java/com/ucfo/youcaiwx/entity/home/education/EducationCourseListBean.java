package com.ucfo.youcaiwx.entity.home.education;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-24.  上午 11:23
 * Package: com.ucfo.youcaiwx.entity.home.education
 * FileName: EducationCourseListBean
 * Description:TODO 课程分类列表
 */
public class EducationCourseListBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"data":[{"package_id":1,"name":"CMA中文高清网课-Part1","teacher_id":"1","price":"0.10","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg","billing_status":2,"join_num":1273,"cpe_integral":20,"teacher_name":"杨晔"}],"total":1}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : [{"package_id":1,"name":"CMA中文高清网课-Part1","teacher_id":"1","price":"0.10","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg","billing_status":2,"join_num":1273,"cpe_integral":20,"teacher_name":"杨晔"}]
         * total : 1
         */

        private String total;
        private List<DataBean> data;

        public String getTotal() {
            return total == null ? "" : total;
        }

        public void setTotal(String total) {
            this.total = total;
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
             * package_id : 1
             * name : CMA中文高清网课-Part1
             * teacher_id : 1
             * price : 0.10
             * app_img : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg
             * billing_status : 2
             * join_num : 1273
             * cpe_integral : 20
             * teacher_name : 杨晔
             */
            private String package_id;
            private String name;
            private String teacher_id;
            private String price;
            private String app_img;
            private String billing_status;
            private String join_num;
            private String cpe_integral;
            private String teacher_name;

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

            public String getApp_img() {
                return app_img == null ? "" : app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }

            public String getBilling_status() {
                return billing_status == null ? "" : billing_status;
            }

            public void setBilling_status(String billing_status) {
                this.billing_status = billing_status;
            }

            public String getJoin_num() {
                return join_num == null ? "" : join_num;
            }

            public void setJoin_num(String join_num) {
                this.join_num = join_num;
            }

            public String getCpe_integral() {
                return cpe_integral == null ? "" : cpe_integral;
            }

            public void setCpe_integral(String cpe_integral) {
                this.cpe_integral = cpe_integral;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }
        }
    }
}
