package com.ucfo.youcaiwx.entity.home;

/**
 * Author: AND
 * Time: 2019-8-23.  上午 10:43
 * Package: com.ucfo.youcaiwx.entity.home
 * FileName: ActiveEventBean
 * Description:TODO首页活动数据
 */
public class ActiveEventBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"status":1,"image_url":null}
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
         * status : 1
         * image_url : null
         */

        private int status;
        private String image_url;

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
