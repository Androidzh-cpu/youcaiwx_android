package com.ucfo.youcai.entity.address;

/**
 * Author: AND
 * Time: 2019-6-14.  下午 3:31
 * Package: com.ucfo.youcai.entity.address
 * FileName: StateStatusBean
 * Description:TODO 只有state的实体类
 */
public class StateStatusBean {

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
        return msg == null ? "" : msg;
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

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }
    }
}
