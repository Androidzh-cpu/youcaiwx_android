package com.ucfo.youcai.entity.learncenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-24.  下午 5:47
 * Package: com.ucfo.youcai.entity.learncenter
 * FileName: LearnPlanDetailVideoBean
 * Description:TODO 学习计划详情视频列表
 */
public class LearnPlanDetailVideoBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"video":[{"is_watch":2,"watch_time":0,"package_id":2,"course_id":"2","is_shoucang":2,"video_name":"下视频","video_id":13,"handouts":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/pdf/20190320/87c12941d38bb7724e273a9d58e69b28.pdf","video_time":"00:42:30","VideoId":"2c0cc85819b44715b8d69546dcb16606","sameday":"1","is_rest":1,"section_id":6,"is_zhengke":1,"know_id":null,"CoverURL":"http://video.youcaiwx.com/2c0cc85819b44715b8d69546dcb16606/snapshots/226ca2f8361e4d74b338a78479c7333c-00005.jpg"},{"is_watch":2,"watch_time":0,"package_id":2,"course_id":"2","is_shoucang":2,"video_name":"test测试视频","video_id":14,"handouts":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/pdf/20190320/87c12941d38bb7724e273a9d58e69b28.pdf","video_time":"00:37:26","VideoId":"329af0dcac8c4787b2ccde804b2ac90a","sameday":"1","is_rest":1,"section_id":5,"is_zhengke":1,"know_id":"4,5,6","CoverURL":"http://video.youcaiwx.com/329af0dcac8c4787b2ccde804b2ac90a/snapshots/d37a3392c75643c4ace9bb13087e09f6-00005.jpg"}]}
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
        private List<VideoBean> video;

        public List<VideoBean> getVideo() {
            if (video == null) {
                return new ArrayList<>();
            }
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class VideoBean {
            /**
             * is_watch : 2
             * watch_time : 0
             * package_id : 2
             * course_id : 2
             * is_shoucang : 2
             * video_name : 下视频
             * video_id : 13
             * handouts : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/pdf/20190320/87c12941d38bb7724e273a9d58e69b28.pdf
             * video_time : 00:42:30
             * VideoId : 2c0cc85819b44715b8d69546dcb16606
             * sameday : 1
             * is_rest : 1
             * section_id : 6
             * is_zhengke : 1
             * know_id : null
             * CoverURL : http://video.youcaiwx.com/2c0cc85819b44715b8d69546dcb16606/snapshots/226ca2f8361e4d74b338a78479c7333c-00005.jpg
             */

            private int is_watch;
            private int watch_time;
            private int package_id;
            private String course_id;
            private int is_shoucang;
            private String video_name;
            private int video_id;
            private String handouts;
            private String video_time;
            private String VideoId;
            private String sameday;
            private int is_rest;
            private int section_id;
            private int is_zhengke;
            private String know_id;
            private String CoverURL;

            public int getIs_watch() {
                return is_watch;
            }

            public void setIs_watch(int is_watch) {
                this.is_watch = is_watch;
            }

            public int getWatch_time() {
                return watch_time;
            }

            public void setWatch_time(int watch_time) {
                this.watch_time = watch_time;
            }

            public int getPackage_id() {
                return package_id;
            }

            public void setPackage_id(int package_id) {
                this.package_id = package_id;
            }

            public String getCourse_id() {
                return course_id == null ? "" : course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public int getIs_shoucang() {
                return is_shoucang;
            }

            public void setIs_shoucang(int is_shoucang) {
                this.is_shoucang = is_shoucang;
            }

            public String getVideo_name() {
                return video_name == null ? "" : video_name;
            }

            public void setVideo_name(String video_name) {
                this.video_name = video_name;
            }

            public int getVideo_id() {
                return video_id;
            }

            public void setVideo_id(int video_id) {
                this.video_id = video_id;
            }

            public String getHandouts() {
                return handouts == null ? "" : handouts;
            }

            public void setHandouts(String handouts) {
                this.handouts = handouts;
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

            public String getSameday() {
                return sameday == null ? "" : sameday;
            }

            public void setSameday(String sameday) {
                this.sameday = sameday;
            }

            public int getIs_rest() {
                return is_rest;
            }

            public void setIs_rest(int is_rest) {
                this.is_rest = is_rest;
            }

            public int getSection_id() {
                return section_id;
            }

            public void setSection_id(int section_id) {
                this.section_id = section_id;
            }

            public int getIs_zhengke() {
                return is_zhengke;
            }

            public void setIs_zhengke(int is_zhengke) {
                this.is_zhengke = is_zhengke;
            }

            public String getKnow_id() {
                return know_id == null ? "" : know_id;
            }

            public void setKnow_id(String know_id) {
                this.know_id = know_id;
            }

            public String getCoverURL() {
                return CoverURL == null ? "" : CoverURL;
            }

            public void setCoverURL(String coverURL) {
                CoverURL = coverURL;
            }
        }
    }
}
