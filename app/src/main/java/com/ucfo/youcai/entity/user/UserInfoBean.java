package com.ucfo.youcai.entity.user;

/**
 * Author:29117
 * Time: 2019-4-2.  上午 11:21
 * Email:2911743255@qq.com
 * ClassName: UserInfoBean
 * Package: com.ucfo.youcai.entity.user
 * Description:TODO 用户个人信息
 */
public class UserInfoBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"username":"旧城","head":"https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132","sex":1,"balance":"8847488.00","integral":6666676,"mobile":"15321789873","is_read":2,"coupon":3}
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
         * username : 旧城
         * head : https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132
         * sex : 1
         * balance : 8847488.00
         * integral : 6666676
         * mobile : 15321789873
         * is_read : 2
         * coupon : 3
         */

        private String username;
        private String head;
        private int sex;
        private String balance;
        private int integral;
        private String mobile;
        private int is_read;
        private int coupon;

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getHead() {
            return head == null ? "" : head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getBalance() {
            return balance == null ? "" : balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public int getIntegral() {
            return integral;
        }

        public void setIntegral(int integral) {
            this.integral = integral;
        }

        public String getMobile() {
            return mobile == null ? "" : mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getIs_read() {
            return is_read;
        }

        public void setIs_read(int is_read) {
            this.is_read = is_read;
        }

        public int getCoupon() {
            return coupon;
        }

        public void setCoupon(int coupon) {
            this.coupon = coupon;
        }
    }
}
