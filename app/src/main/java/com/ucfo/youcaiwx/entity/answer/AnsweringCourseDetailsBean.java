package com.ucfo.youcaiwx.entity.answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-11-12.  下午 1:55
 * Package: com.ucfo.youcaiwx.entity.answer
 * FileName: AnsweringCourseDetailsBean
 * Description:TODO 课程追问答疑详情
 */
public class AnsweringCourseDetailsBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"Title":{"title":"3-1-01成本和差异核算内容","video_time":5280,"VideoId":"073db65988d747878b3d151a4f99f778","user_id":14,"package_id":5,"course_id":1,"section_id":5,"video_id":68,"is_purchase":1},"reply":[{"is_close":2,"reply_status":2,"quiz":"个咯一开机西游记续集这里住咯","quiz_image":[],"head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","answer_type":"1","is_teacher":1,"creates_time":"5天前"},{"quiz":null,"quiz_image":[],"is_teacher":2,"head_image":null,"creates_time":"一个月前"},{"quiz":"还是不懂啊 啊啊啊啊啊","quiz_image":[],"is_teacher":1,"answer_type":"2","head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","creates_time":"一个月前"},{"quiz":"还是不懂啊 啊啊啊啊啊","quiz_image":[],"is_teacher":2,"head_image":null,"creates_time":"一个月前"}]}
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
         * Title : {"title":"3-1-01成本和差异核算内容","video_time":5280,"VideoId":"073db65988d747878b3d151a4f99f778","user_id":14,"package_id":5,"course_id":1,"section_id":5,"video_id":68,"is_purchase":1}
         * reply : [{"is_close":2,"reply_status":2,"quiz":"个咯一开机西游记续集这里住咯","quiz_image":[],"head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","answer_type":"1","is_teacher":1,"creates_time":"5天前"},{"quiz":null,"quiz_image":[],"is_teacher":2,"head_image":null,"creates_time":"一个月前"},{"quiz":"还是不懂啊 啊啊啊啊啊","quiz_image":[],"is_teacher":1,"answer_type":"2","head_image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg","creates_time":"一个月前"},{"quiz":"还是不懂啊 啊啊啊啊啊","quiz_image":[],"is_teacher":2,"head_image":null,"creates_time":"一个月前"}]
         */

        private TitleBean Title;
        private List<ReplyBean> reply;

        public TitleBean getTitle() {
            return Title;
        }

        public void setTitle(TitleBean title) {
            Title = title;
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

        public static class TitleBean {
            /**
             * title : 3-1-01成本和差异核算内容
             * video_time : 5280
             * VideoId : 073db65988d747878b3d151a4f99f778
             * user_id : 14
             * package_id : 5
             * course_id : 1
             * section_id : 5
             * video_id : 68
             * is_purchase : 1
             */

            private String title;
            private String video_time;
            private String VideoId;
            private String user_id;
            private String package_id;
            private String course_id;
            private String section_id;
            private String video_id;
            private String is_purchase;

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getVideo_time() {
                return video_time == null ? "" : video_time;
            }

            public void setVideo_time(String video_time) {
                this.video_time = video_time;
            }

            public String getVideoId() {
                return VideoId == null ? "" : VideoId;
            }

            public void setVideoId(String videoId) {
                VideoId = videoId;
            }

            public String getUser_id() {
                return user_id == null ? "" : user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
            }

            public String getPackage_id() {
                return package_id == null ? "" : package_id;
            }

            public void setPackage_id(String package_id) {
                this.package_id = package_id;
            }

            public String getCourse_id() {
                return course_id == null ? "" : course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getSection_id() {
                return section_id == null ? "" : section_id;
            }

            public void setSection_id(String section_id) {
                this.section_id = section_id;
            }

            public String getVideo_id() {
                return video_id == null ? "" : video_id;
            }

            public void setVideo_id(String video_id) {
                this.video_id = video_id;
            }

            public String getIs_purchase() {
                return is_purchase == null ? "" : is_purchase;
            }

            public void setIs_purchase(String is_purchase) {
                this.is_purchase = is_purchase;
            }
        }

        public static class ReplyBean {
            /**
             * is_close : 2
             * reply_status : 2
             * quiz : 个咯一开机西游记续集这里住咯
             * quiz_image : []
             * head_image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191023/31864bbf6f075c5f6e7b5417dba840ff.jpeg
             * answer_type : 1
             * is_teacher : 1
             * creates_time : 5天前
             */

            private String is_close;
            private String reply_status;
            private String quiz;
            private String head_image;
            private String answer_type;
            private String is_teacher;
            private String creates_time;
            private String username;
            private String user_self;
            private String is_complain;
            private String video_name;
            private List<String> quiz_image;

            public String getIs_close() {
                return is_close == null ? "" : is_close;
            }

            public void setIs_close(String is_close) {
                this.is_close = is_close;
            }

            public String getReply_status() {
                return reply_status == null ? "" : reply_status;
            }

            public void setReply_status(String reply_status) {
                this.reply_status = reply_status;
            }

            public String getQuiz() {
                return quiz == null ? "" : quiz;
            }

            public void setQuiz(String quiz) {
                this.quiz = quiz;
            }

            public String getHead_image() {
                return head_image == null ? "" : head_image;
            }

            public void setHead_image(String head_image) {
                this.head_image = head_image;
            }

            public String getAnswer_type() {
                return answer_type == null ? "" : answer_type;
            }

            public void setAnswer_type(String answer_type) {
                this.answer_type = answer_type;
            }

            public String getIs_teacher() {
                return is_teacher == null ? "" : is_teacher;
            }

            public void setIs_teacher(String is_teacher) {
                this.is_teacher = is_teacher;
            }

            public String getCreates_time() {
                return creates_time == null ? "" : creates_time;
            }

            public void setCreates_time(String creates_time) {
                this.creates_time = creates_time;
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

            public String getUsername() {
                return username == null ? "" : username;
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

            public String getSection_name() {
                return video_name == null ? "" : video_name;
            }

            public void setSection_name(String section_name) {
                this.video_name = section_name;
            }

            public String getIs_complain() {
                return is_complain == null ? "" : is_complain;
            }

            public void setIs_complain(String is_complain) {
                this.is_complain = is_complain;
            }
        }
    }
}
