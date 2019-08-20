package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-24.  下午 4:09
 * FileName: MineCourseCollectionDirBean
 * Description:TODO 课程收藏播放目录
 */
public class MineCourseCollectionDirBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"section":[{"section_id":1,"section_name":"对外财务报告决策","video":[{"video_name":"星巴克成本的构成","VideoId":"066a5dcd13f94f608dfffeabaaafe7cd"},{"video_name":"成本管理与概念","VideoId":"066a5dcd13f94f608dfffeabaaafe7cd"}]},{"section_id":2,"section_name":"规划、预算编制与预测","video":[{"video_name":"1-1（1）成本度量和计量基本概念","VideoId":"f4dea575079b4864b2e5e3825e1b646e"}]}]}
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
        private List<SectionBean> section;

        public List<SectionBean> getSection() {
            if (section == null) {
                return new ArrayList<>();
            }
            return section;
        }

        public void setSection(List<SectionBean> section) {
            this.section = section;
        }

        public static class SectionBean {
            /**
             * section_id : 1
             * section_name : 对外财务报告决策
             * video : [{"video_name":"星巴克成本的构成","VideoId":"066a5dcd13f94f608dfffeabaaafe7cd"},{"video_name":"成本管理与概念","VideoId":"066a5dcd13f94f608dfffeabaaafe7cd"}]
             */

            private int section_id;
            private String section_name;
            private List<VideoBean> video;

            public int getSection_id() {
                return section_id;
            }

            public void setSection_id(int section_id) {
                this.section_id = section_id;
            }

            public String getSection_name() {
                return section_name == null ? "" : section_name;
            }

            public void setSection_name(String section_name) {
                this.section_name = section_name;
            }

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
                 * video_name : 星巴克成本的构成
                 * VideoId : 066a5dcd13f94f608dfffeabaaafe7cd
                 */
                private int id;
                private String video_name;
                private String VideoId;
                private String video_time;

                public int getId() {
                    return id;
                }

                public void setId(int id) {
                    this.id = id;
                }

                public String getVideo_name() {
                    return video_name == null ? "" : video_name;
                }

                public void setVideo_name(String video_name) {
                    this.video_name = video_name;
                }

                public String getVideoId() {
                    return VideoId == null ? "" : VideoId;
                }

                public void setVideoId(String videoId) {
                    VideoId = videoId;
                }

                public String getVideo_time() {
                    return video_time == null ? "" : video_time;
                }

                public void setVideo_time(String video_time) {
                    this.video_time = video_time;
                }
            }
        }
    }
}
