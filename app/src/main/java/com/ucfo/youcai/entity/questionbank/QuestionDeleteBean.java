package com.ucfo.youcai.entity.questionbank;

/**
 * Author: AND
 * Time: 2019-5-29.  上午 10:16
 * Package: com.ucfo.youcai.entity.questionbank
 * FileName: QuestionDeleteBean
 * Description:TODO 题目删除结果
 */
public class QuestionDeleteBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"state":0}
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
         * state : 0
         */
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
