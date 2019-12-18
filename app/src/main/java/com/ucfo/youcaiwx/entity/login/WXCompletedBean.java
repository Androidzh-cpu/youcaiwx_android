package com.ucfo.youcaiwx.entity.login;

/**
 * Author:29117
 * Time: 2019-3-29.  上午 11:59
 * Email:2911743255@qq.com
 * ClassName: WXCompletedBean
 * Description:
 * Detail:
 */
public class WXCompletedBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":32,"username":"","mobile":"13501089764","user_status":0,"head":"","status":4,"type":"","sex":0,"balance":"0.00","devices":"","integral":""}
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
         * id : 32
         * username :
         * mobile : 13501089764
         * user_status : 0
         * head :
         * status : 4
         * type :
         * sex : 0
         * balance : 0.00
         * devices :
         * integral :
         */

        private String id;
        private String username;
        private String mobile;
        private String user_status;
        private String head;
        private String status;
        private String type;
        private String sex;
        private String balance;
        private String devices;
        private String integral;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUser_status() {
            return user_status == null ? "" : user_status;
        }

        public void setUser_status(String user_status) {
            this.user_status = user_status;
        }

        public String getHead() {
            return head == null ? "" : head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getType() {
            return type == null ? "" : type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSex() {
            return sex == null ? "" : sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getBalance() {
            return balance == null ? "" : balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDevices() {
            return devices == null ? "" : devices;
        }

        public void setDevices(String devices) {
            this.devices = devices;
        }

        public String getIntegral() {
            return integral == null ? "" : integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }
    }
}
