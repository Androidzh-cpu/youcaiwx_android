package com.ucfo.youcaiwx.entity.questionbank;

/**
 * Author: AND
 * Time: 2019-5-23.  下午 5:09
 * FileName: SubmitStatusResultBean
 * Description:TODO 交卷状态返回
 */
public class SubmitStatusResultBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"state":1}
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
         * state : 1
         */

        private int state;
        private String paper_id;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getPaper_id() {
            return paper_id == null ? "" : paper_id;
        }

        public void setPaper_id(String paper_id) {
            this.paper_id = paper_id;
        }
    }
}
