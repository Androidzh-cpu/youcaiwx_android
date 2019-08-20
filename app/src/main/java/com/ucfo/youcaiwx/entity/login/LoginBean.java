package com.ucfo.youcaiwx.entity.login;

/**
 * Author:29117
 * Time: 2019-3-25.  上午 9:40
 * Email:2911743255@qq.com
 * ClassName: LoginBean
 * Description:
 * Detail:
 */
public class LoginBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":33,"username":"","mobile":"13501089764","user_status":0,"head":"","status":4,"type":"","sex":0,"balance":"0.00","devices":"","integral":"","equipment":1}
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
         * id : 33
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
         * equipment : 1
         */

        private int id;
        private String username;
        private String mobile;
        private int user_status;
        private String head;
        private int status;
        private String type;
        private int sex;
        private String balance;
        private String devices;
        private String integral;
        private int equipment;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getUser_status() {
            return user_status;
        }

        public void setUser_status(int user_status) {
            this.user_status = user_status;
        }

        public String getHead() {
            return head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public String getDevices() {
            return devices;
        }

        public void setDevices(String devices) {
            this.devices = devices;
        }

        public String getIntegral() {
            return integral;
        }

        public void setIntegral(String integral) {
            this.integral = integral;
        }

        public int getEquipment() {
            return equipment;
        }

        public void setEquipment(int equipment) {
            this.equipment = equipment;
        }
    }
}