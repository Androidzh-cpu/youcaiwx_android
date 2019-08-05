package com.ucfo.youcai.entity.answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 2:27
 * Package: com.ucfo.youcai.entity.questionbank
 * FileName: QuestionAnswerListBean
 * Description:TODO 题库答疑列表实体类
 */
public class QuestionAnswerListBean {

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
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * username : 旧城凉
         * head : https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132
         * Id : 6
         * quiz : 因为你长得丑，但是你想的美啊
         * reply_status : 1
         * know_name : ["总论","案例-成本和意识"]
         * quiz_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190527/c51e1058fea8861908001b2726a2c9de.jpeg"]
         * create_times : 3天前
         */
        private String username;
        private String head;
        private int Id;
        private String quiz;
        private int reply_status;
        private String create_times;
        private List<String> know_name;
        private List<String> quiz_image;

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

        public int getId() {
            return Id;
        }

        public void setId(int id) {
            Id = id;
        }

        public String getQuiz() {
            return quiz == null ? "" : quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }

        public String getCreate_times() {
            return create_times == null ? "" : create_times;
        }

        public void setCreate_times(String create_times) {
            this.create_times = create_times;
        }

        public List<String> getKnow_name() {
            if (know_name == null) {
                return new ArrayList<>();
            }
            return know_name;
        }

        public void setKnow_name(List<String> know_name) {
            this.know_name = know_name;
        }

        public List<String> getQuiz_image() {
            if (quiz_image == null) {
                return new ArrayList<>();
            }
            return quiz_image;
        }

        public void setQuiz_image(List<String> quiz_image) {
            this.quiz_image = quiz_image;
        }
    }
}


