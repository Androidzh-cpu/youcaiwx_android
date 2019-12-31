package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-27.  下午 4:06
 * FileName: MineWatchRecordBean
 * Description:TODO 观看记录
 */
public class MineWatchRecordBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"course_name":"CMA中文Part-2高清网课","app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/72c0ae410382265c30461bc0a05f2a98.jpeg","is_zhengke":1,"video_name":"1-1（1）成本度量和计量基本概念","section_id":5,"VideoId":"f4dea575079b4864b2e5e3825e1b646e","watch_time":320,"video_type":1,"package_id":2,"course_id":2,"video_id":4}]
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
         * course_name : CMA中文Part-2高清网课
         * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/72c0ae410382265c30461bc0a05f2a98.jpeg
         * is_zhengke : 1
         * video_name : 1-1（1）成本度量和计量基本概念
         * section_id : 5
         * VideoId : f4dea575079b4864b2e5e3825e1b646e
         * watch_time : 320
         * video_type : 1
         * package_id : 2
         * course_id : 2
         * video_id : 4
         */

        private String course_name;
        private String app_img;
        private String is_zhengke;
        private String is_purchase;
        private String video_name;
        private String section_id;
        private String VideoId;
        private String watch_time;
        private String video_type;
        private String package_id;
        private String course_id;
        private String video_id;

        public String getCourse_name() {
            return course_name == null ? "" : course_name;
        }

        public void setCourse_name(String course_name) {
            this.course_name = course_name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getIs_zhengke() {
            return is_zhengke == null ? "" : is_zhengke;
        }

        public void setIs_zhengke(String is_zhengke) {
            this.is_zhengke = is_zhengke;
        }

        public String getIs_purchase() {
            return is_purchase == null ? "" : is_purchase;
        }

        public void setIs_purchase(String is_purchase) {
            this.is_purchase = is_purchase;
        }

        public String getVideo_name() {
            return video_name == null ? "" : video_name;
        }

        public void setVideo_name(String video_name) {
            this.video_name = video_name;
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

        public String getWatch_time() {
            return watch_time == null ? "" : watch_time;
        }

        public void setWatch_time(String watch_time) {
            this.watch_time = watch_time;
        }

        public String getVideo_type() {
            return video_type == null ? "" : video_type;
        }

        public void setVideo_type(String video_type) {
            this.video_type = video_type;
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

        public String getVideo_id() {
            return video_id == null ? "" : video_id;
        }

        public void setVideo_id(String video_id) {
            this.video_id = video_id;
        }
    }
}
