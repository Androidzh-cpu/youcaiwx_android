package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-27.  下午 3:42
 * Package: com.ucfo.youcaiwx.entity.user
 * FileName: MineCPECourseBean
 * Description:TODO 后续教育课程
 */
public class MineCPECourseBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"package_id":1,"teacher_id":"1","name":"CMA中文高清网课-Part1","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg","type_id":1,"join_num":1525,"teacher_name":"杨晔","cpe_integral":20}]
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
         * package_id : 1
         * teacher_id : 1
         * name : CMA中文高清网课-Part1
         * app_img : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg
         * type_id : 1
         * join_num : 1525
         * teacher_name : 杨晔
         * cpe_integral : 20
         */

        private String package_id;
        private String teacher_id;
        private String name;
        private String app_img;
        private String type_id;
        private String join_num;
        private String teacher_name;
        private String cpe_integral;
        private String study_days;

        public String getStudy_days() {
            return study_days == null ? "" : study_days;
        }

        public void setStudy_days(String study_days) {
            this.study_days = study_days;
        }

        public String getPackage_id() {
            return package_id == null ? "" : package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getTeacher_id() {
            return teacher_id == null ? "" : teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
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

        public String getType_id() {
            return type_id == null ? "" : type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getJoin_num() {
            return join_num == null ? "" : join_num;
        }

        public void setJoin_num(String join_num) {
            this.join_num = join_num;
        }

        public String getTeacher_name() {
            return teacher_name == null ? "" : teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getCpe_integral() {
            return cpe_integral == null ? "" : cpe_integral;
        }

        public void setCpe_integral(String cpe_integral) {
            this.cpe_integral = cpe_integral;
        }
    }
}
