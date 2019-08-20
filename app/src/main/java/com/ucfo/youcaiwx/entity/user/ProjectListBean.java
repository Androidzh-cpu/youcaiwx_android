package com.ucfo.youcaiwx.entity.user;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-19.  下午 2:23
 * FileName: ProjectListBean
 * Description:TODO 题库收藏标签
 */
public class ProjectListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"course_id":1,"name":"CMA中文Part-1高清网课"},{"course_id":2,"name":"CMA中文Part-2高清网课"}]
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
         * course_id : 1
         * name : CMA中文Part-1高清网课
         */

        private int course_id;
        private String name;

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
