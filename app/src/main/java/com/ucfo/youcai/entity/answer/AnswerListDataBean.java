package com.ucfo.youcai.entity.answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 4:14
 * Email:2911743255@qq.com
 * ClassName: AnswerListDataBean
 * Package: com.ucfo.youcai.entity.answer
 * Description:TODO 课程答疑列表
 */
public class AnswerListDataBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"username":"333","id":3,"video_time":"09:46:28","quiz":"你可真是一个大傻子","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"],"create_time":"2019-04-15 10:20:17","reply_status":1,"head":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132","creates_time":"1天前","section_name":"总论","is_status":1},{"username":"333","id":4,"video_time":"09:46:28","quiz":"你可真是二个大傻子","quiz_image":[],"create_time":"2019-04-15 10:27:07","reply_status":1,"head":"https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132","creates_time":"1天前","section_name":"总论","is_status":1}]
     */

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
         * username : 333
         * id : 3
         * video_time : 09:46:28
         * quiz : 你可真是一个大傻子
         * quiz_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190404/3b4e5595e4c6921102d20831f4f63cf9.jpeg"]
         * create_time : 2019-04-15 10:20:17
         * reply_status : 1
         * head : https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTKDic46swlunhljsXBv7Ntt7icpTRpHz0YNPH1BZnxjoPGZCXCQJKWFNINkvCibzsnMVxuicGjlMHJ7ag/132
         * creates_time : 1天前
         * section_name : 总论
         * is_status : 1
         */

        private String username;
        private int id;
        private String video_time;
        private String quiz;
        private String create_time;
        private String creates_time;
        private String create_times;
        private int reply_status;
        private String head;
        private String section_name;
        private int is_status;
        private List<String> quiz_image;
        private List<String> know_name;

        public List<String> getKnow_name() {
            if (know_name == null) {
                return new ArrayList<>();
            }
            return know_name;
        }

        public void setKnow_name(List<String> know_name) {
            this.know_name = know_name;
        }

        public String getUsername() {
            return username == null ? "" : username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCreate_times() {
            return create_times == null ? "" : create_times;
        }

        public void setCreate_times(String create_times) {
            this.create_times = create_times;
        }
        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getVideo_time() {
            return video_time == null ? "" : video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getQuiz() {
            return quiz == null ? "" : quiz;
        }

        public void setQuiz(String quiz) {
            this.quiz = quiz;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public int getReply_status() {
            return reply_status;
        }

        public void setReply_status(int reply_status) {
            this.reply_status = reply_status;
        }

        public String getHead() {
            return head == null ? "" : head;
        }

        public void setHead(String head) {
            this.head = head;
        }

        public String getCreates_time() {
            return creates_time == null ? "" : creates_time;
        }

        public void setCreates_time(String creates_time) {
            this.creates_time = creates_time;
        }

        public String getSection_name() {
            return section_name == null ? "" : section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public int getIs_status() {
            return is_status;
        }

        public void setIs_status(int is_status) {
            this.is_status = is_status;
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
