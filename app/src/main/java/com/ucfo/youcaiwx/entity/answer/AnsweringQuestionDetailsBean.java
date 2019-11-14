package com.ucfo.youcaiwx.entity.answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-11-13.  上午 9:21
 * Package: com.ucfo.youcaiwx.entity.answer
 * FileName: AnsweringQuestionDetailsBean
 * Description:TODO 题库答疑追问详情
 */
public class AnsweringQuestionDetailsBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"topics":{"topic":"下列关于同基报表的表述，哪项是正确的？","question_id":"1"},"reply":[{"is_close":"1","reply_status":"2","quiz":"哦破用摸摸摸摸哦的","quiz_image":[],"head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","is_teacher":"1","creates_time":"8分钟前","username":"铁骨铮铮","user_self":1,"know":["百分比式财务报表"]}]}
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
         * topics : {"topic":"下列关于同基报表的表述，哪项是正确的？","question_id":"1"}
         * reply : [{"is_close":"1","reply_status":"2","quiz":"哦破用摸摸摸摸哦的","quiz_image":[],"head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","is_teacher":"1","creates_time":"8分钟前","username":"铁骨铮铮","user_self":1,"know":["百分比式财务报表"]}]
         */

        private TopicsBean topics;
        private List<ReplyBean> reply;

        public TopicsBean getTopics() {
            return topics;
        }

        public void setTopics(TopicsBean topics) {
            this.topics = topics;
        }

        public List<ReplyBean> getReply() {
            if (reply == null) {
                return new ArrayList<>();
            }
            return reply;
        }

        public void setReply(List<ReplyBean> reply) {
            this.reply = reply;
        }

        public static class TopicsBean {
            /**
             * topic : 下列关于同基报表的表述，哪项是正确的？
             * question_id : 1
             */

            private String topic;
            private String question_id;

            public String getTopic() {
                return topic == null ? "" : topic;
            }

            public void setTopic(String topic) {
                this.topic = topic;
            }

            public String getQuestion_id() {
                return question_id == null ? "" : question_id;
            }

            public void setQuestion_id(String question_id) {
                this.question_id = question_id;
            }
        }

        public static class ReplyBean {
            /**
             * is_close : 1
             * reply_status : 2
             * quiz : 哦破用摸摸摸摸哦的
             * quiz_image : []
             * head_image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg
             * is_teacher : 1
             * creates_time : 8分钟前
             * username : 铁骨铮铮
             * user_self : 1
             * know : ["百分比式财务报表"]
             */

            private String is_close;
            private String reply_status;
            private String quiz;
            private String head_image;
            private String is_teacher;
            private String creates_time;
            private String username;
            private String user_self;
            private List<String> quiz_image;
            private List<String> know;

            public String getIs_close() {
                return is_close;
            }

            public void setIs_close(String is_close) {
                this.is_close = is_close;
            }

            public String getReply_status() {
                return reply_status;
            }

            public void setReply_status(String reply_status) {
                this.reply_status = reply_status;
            }

            public String getQuiz() {
                return quiz;
            }

            public void setQuiz(String quiz) {
                this.quiz = quiz;
            }

            public String getHead_image() {
                return head_image;
            }

            public void setHead_image(String head_image) {
                this.head_image = head_image;
            }

            public String getIs_teacher() {
                return is_teacher;
            }

            public void setIs_teacher(String is_teacher) {
                this.is_teacher = is_teacher;
            }

            public String getCreates_time() {
                return creates_time;
            }

            public void setCreates_time(String creates_time) {
                this.creates_time = creates_time;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getUser_self() {
                return user_self == null ? "" : user_self;
            }

            public void setUser_self(String user_self) {
                this.user_self = user_self;
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

            public List<String> getKnow() {
                if (know == null) {
                    return new ArrayList<>();
                }
                return know;
            }

            public void setKnow(List<String> know) {
                this.know = know;
            }
        }
    }
}
