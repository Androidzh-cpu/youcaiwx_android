package com.ucfo.youcaiwx.entity.learncenter;

/**
 * Author: AND
 * Time: 2019-7-23.  下午 4:39
 * FileName: StudyClockInBean
 * Description:TODO 学习打卡
 */
public class StudyClockInBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"num":1,"image_url":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/9dc67e33132ade93b8ac194baafaff1b.jpeg","username":"青春遗言","head":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190703/1e6736c64fa1ab5c89e3a80a1ecdbd8b.jpeg"}
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
         * num : 1
         * image_url : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/9dc67e33132ade93b8ac194baafaff1b.jpeg
         * username : 青春遗言
         * head : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190703/1e6736c64fa1ab5c89e3a80a1ecdbd8b.jpeg
         */

        private int num;
        private String image_url;
        private String username;
        private String head;

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public String getImage_url() {
            return image_url == null ? "" : image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

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
    }
}
