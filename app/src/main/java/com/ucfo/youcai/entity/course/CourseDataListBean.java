package com.ucfo.youcai.entity.course;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 10:40
 * Email:2911743255@qq.com
 * ClassName: CourseDataListBean
 * Package: com.ucfo.youcai.entity.course
 * Description:
 * Detail:
 */
public class CourseDataListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":6,"name":"CMA英文全科高清网课","teacher_id":"1","billing_status":2,"price":"","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190403/577b911b0b317f3aebc9000eb8489bc2.jpeg","study_days":0,"is_zheng":2,"join_num":790,"teacher_name":"王强","is_purchase":2},{"id":8,"name":"【卢英祺】优财CMA中文实景Part-2录制（深圳）","teacher_id":"1,2","billing_status":2,"price":"","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190403/577b911b0b317f3aebc9000eb8489bc2.jpeg","study_days":0,"is_zheng":1,"join_num":720,"teacher_name":"王强,金龟","is_purchase":2}]
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 6
         * name : CMA英文全科高清网课
         * teacher_id : 1
         * billing_status : 2
         * price :
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190403/577b911b0b317f3aebc9000eb8489bc2.jpeg
         * study_days : 0
         * is_zheng : 2
         * join_num : 790
         * teacher_name : 王强
         * is_purchase : 2
         */

        private int id;
        private String name;
        private String teacher_id;
        private int billing_status;
        private String price;
        private String app_img;
        private int study_days;
        private int is_zheng;
        private int join_num;
        private String teacher_name;
        private int is_purchase;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getTeacher_id() {
            return teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public int getBilling_status() {
            return billing_status;
        }

        public void setBilling_status(int billing_status) {
            this.billing_status = billing_status;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getApp_img() {
            return app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public int getStudy_days() {
            return study_days;
        }

        public void setStudy_days(int study_days) {
            this.study_days = study_days;
        }

        public int getIs_zheng() {
            return is_zheng;
        }

        public void setIs_zheng(int is_zheng) {
            this.is_zheng = is_zheng;
        }

        public int getJoin_num() {
            return join_num;
        }

        public void setJoin_num(int join_num) {
            this.join_num = join_num;
        }

        public String getTeacher_name() {
            return teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public int getIs_purchase() {
            return is_purchase;
        }

        public void setIs_purchase(int is_purchase) {
            this.is_purchase = is_purchase;
        }
    }
}
