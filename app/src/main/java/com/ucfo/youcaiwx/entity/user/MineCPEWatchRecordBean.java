package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-30.  上午 11:13
 * Package: com.ucfo.youcaiwx.entity.user
 * FileName: MineCPEWatchRecordBean
 * Description:TODO CPE观看记录
 */
public class MineCPEWatchRecordBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"package_id":1,"name":"CMA中文高清网课-Part1","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg","type_id":1,"complete":2,"is_purchase":1,"section_id":1,"VideoId":"d27e7d76eae5475f9306081b2267ea5a","video_name":"视频1","video_time":"00:08:29","course_id":1,"video_id":1}]
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
         * package_id : 1
         * name : CMA中文高清网课-Part1
         * app_img : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg
         * type_id : 1
         * complete : 2
         * is_purchase : 1
         * section_id : 1
         * VideoId : d27e7d76eae5475f9306081b2267ea5a
         * video_name : 视频1
         * video_time : 00:08:29
         * course_id : 1
         * video_id : 1
         */

        private String package_id;
        private String name;
        private String app_img;
        private String type_id;
        private String complete;
        private String is_purchase;
        private String section_id;
        private String VideoId;
        private String video_name;
        private String video_time;
        private String course_id;
        private String video_id;

        public String getPackage_id() {
            return package_id == null ? "" : package_id;
        }

        public void setPackage_id(String package_id) {
            this.package_id = package_id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getType_id() {
            return type_id == null ? "" : type_id;
        }

        public void setType_id(String type_id) {
            this.type_id = type_id;
        }

        public String getComplete() {
            return complete == null ? "" : complete;
        }

        public void setComplete(String complete) {
            this.complete = complete;
        }

        public String getIs_purchase() {
            return is_purchase == null ? "" : is_purchase;
        }

        public void setIs_purchase(String is_purchase) {
            this.is_purchase = is_purchase;
        }

        public String getSection_id() {
            return section_id == null ? "" : section_id;
        }

        public void setSection_id(String section_id) {
            this.section_id = section_id;
        }

        public String getVideoId() {
            return VideoId == null ? "" : VideoId;
        }

        public void setVideoId(String videoId) {
            VideoId = videoId;
        }

        public String getVideo_name() {
            return video_name == null ? "" : video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
        }

        public String getVideo_time() {
            return video_time == null ? "" : video_time;
        }

        public void setVideo_time(String video_time) {
            this.video_time = video_time;
        }

        public String getCourse_id() {
            return course_id == null ? "" : course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getVideo_id() {
            return video_id == null ? "" : video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }
}
