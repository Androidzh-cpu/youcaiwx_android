package com.ucfo.youcai.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 2:17
 * Package: com.ucfo.youcai.entity.user
 * FileName: MineCourseBean
 * Description:TODO 我的课程
 */
public class MineCourseBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"package_id":2,"name":"中文p2高清网课","study_days":300,"teacher_id":"6,7","join_num":1477,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg","teacher_name":"代坤,卢英祺"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"},{"package_id":2,"name":"中文p2高清网课","study_days":300,"teacher_id":"6,7","join_num":1477,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg","teacher_name":"代坤,卢英祺"},{"package_id":2,"name":"中文p2高清网课","study_days":300,"teacher_id":"6,7","join_num":1477,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg","teacher_name":"代坤,卢英祺"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"},{"package_id":2,"name":"中文p2高清网课","study_days":300,"teacher_id":"6,7","join_num":1477,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg","teacher_name":"代坤,卢英祺"},{"package_id":1,"name":"中文p1高清网课","study_days":180,"teacher_id":"4,5","join_num":2023,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","teacher_name":"杨晔,王谦"}]
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
         * package_id : 2
         * name : 中文p2高清网课
         * study_days : 300
         * teacher_id : 6,7
         * join_num : 1477
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/29af2d754a8cc3f9d3f61a61f8810208.jpeg
         * teacher_name : 代坤,卢英祺
         */
        /**
         * collect_id : 10
         * package_id : 1
         * name : 中文p1高清网课
         * study_days : 180
         * teacher_id : 4,5
         * join_num : 2023
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg
         * course_id : 1
         * section_id : 1
         * video_id : 1
         */

        private int package_id;
        private String name;
        private int study_days;
        private String teacher_id;
        private int join_num;
        private int video_id;
        private int section_id;
        private int course_id;
        private int collect_id;
        private String app_img;
        private String teacher_name;

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
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

        public int getJoin_num() {
            return join_num;
        }

        public void setJoin_num(int join_num) {
            this.join_num = join_num;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getTeacher_name() {
            return teacher_name == null ? "" : teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public int getVideo_id() {
            return video_id;
        }

        public void setVideo_id(int video_id) {
            this.video_id = video_id;
        }

        public int getSection_id() {
            return section_id;
        }

        public void setSection_id(int section_id) {
            this.section_id = section_id;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getCollect_id() {
            return collect_id;
        }

        public void setCollect_id(int collect_id) {
            this.collect_id = collect_id;
        }
    }

}
