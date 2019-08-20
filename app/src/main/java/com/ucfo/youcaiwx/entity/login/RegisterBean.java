package com.ucfo.youcaiwx.entity.login;

/**
 * Author:29117
 * Time: 2019-3-26.  上午 11:44
 * Email:2911743255@qq.com
 * ClassName: RegisterBean
 * Description: TODO 注册成功实体类
 */
public class RegisterBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":"7","username":"13501089764","mobile":"13501089764","user_status":"0","head":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/0ec7f3a1e2c520c8cf2ab5d24a626dc1.png","status":"3","type":"","sex":"0","balance":"0.00","devices":"","integral":"0"}
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
         * id : 7
         * username : 13501089764
         * mobile : 13501089764
         * user_status : 0
         * head : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/0ec7f3a1e2c520c8cf2ab5d24a626dc1.png
         * status : 3
         * type :
         * sex : 0
         * balance : 0.00
         * devices :
         * integral : 0
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
