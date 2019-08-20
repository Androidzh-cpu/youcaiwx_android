package com.ucfo.youcaiwx.entity.course;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 9:35
 * Email:2911743255@qq.com
 * ClassName: CourseSubjectsBean
 * Description:课程筛选
 * Detail:
 */
public class CourseSubjectsBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":2,"class_name":"中文part2"},{"id":3,"class_name":"英文part1"},{"id":5,"class_name":"微实战"},{"id":6,"class_name":"非财基础提升课"},{"id":10,"class_name":"后续教育"},{"id":11,"class_name":"管理会计提升课"},{"id":13,"class_name":"其他"},{"id":15,"class_name":"CMA精品体验课"},{"id":16,"class_name":"【王谦】优财CMA中文实景Part-2录制"},{"id":17,"class_name":"【杨晔】优财CMA中文实景Part-1录制"},{"id":18,"class_name":"【代坤】中文实景录制课Part-1（深圳）"},{"id":19,"class_name":"【卢英祺】优财CMA中文实景Part-2录制（深圳）"},{"id":20,"class_name":"【代坤】优财CMA中文实景Part-2录制"},{"id":21,"class_name":"【王谦】优财CMA中文实景Part-1录制"},{"id":23,"class_name":"英文考经直播"},{"id":24,"class_name":"翻转课堂"},{"id":35,"class_name":"成本管理"},{"id":36,"class_name":"                   测试标签         "}]
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
         * id : 2
         * class_name : 中文part2
         */

        private int id;
        private String class_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getClass_name() {
            return class_name;
        }

        public void setClass_name(String class_name) {
            this.class_name = class_name;
        }
    }
}
