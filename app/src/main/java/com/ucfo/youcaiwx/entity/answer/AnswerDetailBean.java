package com.ucfo.youcaiwx.entity.answer;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-17.  下午 3:14
 * Email:2911743255@qq.com
 * ClassName: AnswerDetailBean
 * Description:TODO 课程问答详情
 */
public class AnswerDetailBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"data":{"id":"3","video_time":"09:46:28","quiz":"你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子","username":"333","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"],"head":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132","section_name":"总论","creates_time":"2天前"},"reply":{"reply_quiz":"这道需要这么做的同学","reply_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"],"head_img":"","reply_user_name":"产品部","repls_time":"8天前"}}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * data : {"id":"3","video_time":"09:46:28","quiz":"你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子","username":"333","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"],"head":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132","section_name":"总论","creates_time":"2天前"}
         * reply : {"reply_quiz":"这道需要这么做的同学","reply_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"],"head_img":"","reply_user_name":"产品部","repls_time":"8天前"}
         */

        private DataBean data;
        private ReplyBean reply;
        private TitleBean Title;

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public ReplyBean getReply() {
            return reply;
        }

        public void setReply(ReplyBean reply) {
            this.reply = reply;
        }

        public TitleBean getTitleBean() {
            return Title;
        }

        public void setTitleBean(TitleBean titleBean) {
            this.Title = titleBean;
        }

        public static class TitleBean {
            private String title;
            private String video_time;
            private String VideoId;
            private String user_id;
            private String package_id;
            private String course_id;
            private String section_id;
            private String video_id;
            private int is_purchase;

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

            public int getIs_purchase() {
                return is_purchase;
            }

            public void setIs_purchase(int is_purchase) {
                this.is_purchase = is_purchase;
            }
        }

        public static class DataBean {
            /**
             * id : 3
             * video_time : 09:46:28
             * quiz : 你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子你可真是一个大傻子
             * username : 333
             * quiz_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"]
             * head : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132
             * section_name : 总论
             * creates_time : 2天前
             */

            private String id;
            private String video_time;
            private String quiz;
            private String username;
            private String head;
            private String section_name;
            private String creates_time;
            private List<String> quiz_image;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getVideo_time() {
                return video_time;
            }

            public void setVideo_time(String video_time) {
                this.video_time = video_time;
            }

            public String getQuiz() {
                return quiz;
            }

            public void setQuiz(String quiz) {
                this.quiz = quiz;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getHead() {
                return head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getSection_name() {
                return section_name;
            }

            public void setSection_name(String section_name) {
                this.section_name = section_name;
            }

            public String getCreates_time() {
                return creates_time;
            }

            public void setCreates_time(String creates_time) {
                this.creates_time = creates_time;
            }

            public List<String> getQuiz_image() {
                return quiz_image;
            }

            public void setQuiz_image(List<String> quiz_image) {
                this.quiz_image = quiz_image;
            }
        }

        public static class ReplyBean {
            /**
             * reply_quiz : 这道需要这么做的同学
             * reply_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"]
             * head_img :
             * reply_user_name : 产品部
             * repls_time : 8天前
             */

            private String reply_quiz;
            private String head_img;
            private String reply_user_name;
            private String repls_time;
            private List<String> reply_image;

            public String getReply_quiz() {
                return reply_quiz;
            }

            public void setReply_quiz(String reply_quiz) {
                this.reply_quiz = reply_quiz;
            }

            public String getHead_img() {
                return head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getReply_user_name() {
                return reply_user_name;
            }

            public void setReply_user_name(String reply_user_name) {
                this.reply_user_name = reply_user_name;
            }

            public String getRepls_time() {
                return repls_time;
            }

            public void setRepls_time(String repls_time) {
                this.repls_time = repls_time;
            }

            public List<String> getReply_image() {
                return reply_image;
            }

            public void setReply_image(List<String> reply_image) {
                this.reply_image = reply_image;
            }
        }
    }
}
