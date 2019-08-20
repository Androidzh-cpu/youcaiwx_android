package com.ucfo.youcaiwx.entity.questionbank;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-26.  上午 10:52
 * Email:2911743255@qq.com
 * ClassName: QuestionMyProjectBean
 * Description:TODO  获取我的题库
 * Detail:TODO
 */
public class QuestionMyProjectBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":1,"major_name":"中文part-1"},{"id":2,"major_name":"中文part-2"}]
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
         * id : 1
         * name : 中文part-1
         */

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
