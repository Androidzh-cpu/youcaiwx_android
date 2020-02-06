package com.ucfo.youcaiwx.entity.course;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-3.  下午 3:41
 * Email:2911743255@qq.com
 * ClassName: CourseIntroductionBean
 */
public class CourseIntroductionBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"package_id":5,"name":"【代坤】中文实景录制课Part-1（深圳）","teacher_id":"1","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190403/577b911b0b317f3aebc9000eb8489bc2.jpeg","billing_status":1,"price":"12800.00","brief_img":"http://www.youcaiwx.com/upload/images/20180822/1534929196850313.jpg","description":"","join_num":985,"study_days":0,"teacehr_list":[{"id":1,"teacher_name":"王强","longevity":"123123","introduce":"312312","pictrue":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190304/fedbf8b81b2d6b72eb5594242f86d8c3.jpeg","en_name":"wangq","teacher_title":"优财CAM研发老师"}]}
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
         * package_id : 5
         * name : 【代坤】中文实景录制课Part-1（深圳）
         * teacher_id : 1
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190403/577b911b0b317f3aebc9000eb8489bc2.jpeg
         * billing_status : 1
         * price : 12800.00
         * brief_img : http://www.youcaiwx.com/upload/images/20180822/1534929196850313.jpg
         * description :
         * join_num : 985
         * study_days : 0
         * teacehr_list : [{"id":1,"teacher_name":"王强","longevity":"123123","introduce":"312312","pictrue":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190304/fedbf8b81b2d6b72eb5594242f86d8c3.jpeg","en_name":"wangq","teacher_title":"优财CAM研发老师"}]
         */

        private int package_id;
        private String is_purchase;
        private String userstatus;
        private String name;
        private String teacher_id;
        private String app_img;
        private String billing_status;
        private String price;
        private String brief_img;
        private String description;
        private String teacher_name;
        private String join_num;
        private String study_days;
        private String cpe_integral;
        private List<TeacehrListBean> teacehr_list;

        public String getCpe_integral() {
            return cpe_integral == null ? "" : cpe_integral;
        }

        public void setCpe_integral(String cpe_integral) {
            this.cpe_integral = cpe_integral;
        }

        public String getUserstatus() {
            return userstatus == null ? "" : userstatus;
        }

        public void setUserstatus(String userstatus) {
            this.userstatus = userstatus;
        }

        public String getBilling_status() {
            return billing_status == null ? "" : billing_status;
        }

        public void setBilling_status(String billing_status) {
            this.billing_status = billing_status;
        }

        public String getStudy_days() {
            return study_days == null ? "" : study_days;
        }

        public void setStudy_days(String study_days) {
            this.study_days = study_days;
        }

        public String getJoin_num() {
            return join_num == null ? "" : join_num;
        }

        public void setJoin_num(String join_num) {
            this.join_num = join_num;
        }

        public String getIs_purchase() {
            return is_purchase == null ? "" : is_purchase;
        }

        public void setIs_purchase(String is_purchase) {
            this.is_purchase = is_purchase;
        }

        public String getTeacher_name() {
            return teacher_name == null ? "" : teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
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

        public String getApp_img() {
            return app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBrief_img() {
            return brief_img;
        }

        public void setBrief_img(String brief_img) {
            this.brief_img = brief_img;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<TeacehrListBean> getTeacehr_list() {
            if (teacehr_list == null) {
                return new ArrayList<>();
            }
            return teacehr_list;
        }

        public void setTeacehr_list(List<TeacehrListBean> teacehr_list) {
            this.teacehr_list = teacehr_list;
        }

        public static class TeacehrListBean {
            /**
             * id : 1
             * teacher_name : 王强
             * longevity : 123123
             * introduce : 312312
             * pictrue : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190304/fedbf8b81b2d6b72eb5594242f86d8c3.jpeg
             * en_name : wangq
             * teacher_title : 优财CAM研发老师
             */

            private int id;
            private String teacher_name;
            private String longevity;
            private String introduce;
            private String pictrue;
            private String en_name;
            private String teacher_title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTeacher_name() {
                return teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getLongevity() {
                return longevity;
            }

            public void setLongevity(String longevity) {
                this.longevity = longevity;
            }

            public String getIntroduce() {
                return introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getPictrue() {
                return pictrue;
            }

            public void setPictrue(String pictrue) {
                this.pictrue = pictrue;
            }

            public String getEn_name() {
                return en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public String getTeacher_title() {
                return teacher_title;
            }

            public void setTeacher_title(String teacher_title) {
                this.teacher_title = teacher_title;
            }
        }
    }
}
