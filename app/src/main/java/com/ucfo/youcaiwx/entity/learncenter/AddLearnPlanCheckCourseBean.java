package com.ucfo.youcaiwx.entity.learncenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 9:53
 * FileName: AddLearnPlanCheckCourseBean
 * Description:TODO 学习计划选择课程
 */
public class AddLearnPlanCheckCourseBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"course_id":1,"course_name":"CMA中文Part-1高清网课"},{"course_id":2,"course_name":"CMA中文Part-2高清网课"},{"course_id":5,"course_name":"仅供诗进测试使用英文p1"},{"course_id":6,"course_name":"英文p2高清网课"},{"course_id":8,"course_name":"刘诚测试课程1"},{"course_id":9,"course_name":"公司战略与风险管理"}]
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
         * course_id : 1
         * course_name : CMA中文Part-1高清网课
         */

        private int course_id;
        private String course_name;
        private String state;

        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public String getCourse_name() {
            return course_name == null ? "" : course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }
    }
}
