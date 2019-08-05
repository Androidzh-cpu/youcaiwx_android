package com.ucfo.youcai.entity.user;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-20.  上午 11:09
 * Package: com.ucfo.youcai.entity.user
 * FileName: MineCourseChildListBean
 * Description:TODO 课程收藏二级列表
 */
public class MineCourseChildListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"course_id":4,"name":"价值与创造","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/19a55b6640c88d833ce5bad67684b525.jpeg","teacher_id":"6","teacher_name":"代坤"},{"course_id":1,"name":"CMA中文Part-1高清网课","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/77b3f17dd94d6ab9f8559aad8bc24717.jpeg","teacher_id":"4","teacher_name":"杨晔"}]
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
         * course_id : 4
         * name : 价值与创造
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/19a55b6640c88d833ce5bad67684b525.jpeg
         * teacher_id : 6
         * teacher_name : 代坤
         */

        private int course_id;
        private String name;
        private String app_img;
        private String teacher_id;
        private String is_zhengke;
        private String teacher_name;

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getTeacher_id() {
            return teacher_id == null ? "" : teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getTeacher_name() {
            return teacher_name == null ? "" : teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getIs_zhengke() {
            return is_zhengke == null ? "" : is_zhengke;
        }

        public void setIs_zhengke(String is_zhengke) {
            this.is_zhengke = is_zhengke;
        }
    }
}
