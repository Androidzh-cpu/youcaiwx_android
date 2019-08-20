package com.ucfo.youcaiwx.entity.course;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-16.  上午 9:52
 * Email:2911743255@qq.com
 * ClassName: CourseDirBea
 * Description:TODO 课程目录
 */
public class CourseDirBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"name":"中文part1","course_id":"1","teacher_id":"1","teacher_name":"王强","section":[{"course_id":1,"section_id":1,"section_name":"总论","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":2,"section_name":"中文p1 第一章","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":3,"section_name":"中文p1 第2章","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":4,"section_name":"中文p1 第3章","video":[{"id":4,"video_name":"中文p2 视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":4,"course_id":1,"is_shoucang":2},{"id":5,"video_name":"中文p2视频2","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":4,"course_id":1,"is_shoucang":2},{"id":6,"video_name":"中文p2视频3","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":4,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":5,"section_name":"中文p1 第4章","video":[{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":4,"video_name":"中文p2 视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":5,"video_name":"中文p2视频2","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":5,"course_id":1,"is_shoucang":2}]}]},{"name":"中文part2","course_id":"2","teacher_id":"1","teacher_name":"王强","section":[{"course_id":2,"section_id":6,"section_name":"中文p2 第1章"},{"course_id":2,"section_id":7,"section_name":"中文p2 第2章"},{"course_id":2,"section_id":8,"section_name":"中文p2 第3章"},{"course_id":2,"section_id":9,"section_name":"中文p2 第3章"}]},{"name":"中文part3","course_id":"3","teacher_id":"1","teacher_name":"王强","section":[{"course_id":3,"section_id":10,"section_name":"中文p3章节1"}]}]
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }


    public static class DataBean {
        /**
         * name : 中文part1
         * course_id : 1
         * teacher_id : 1
         * "is_zhengke": "2",
         * teacher_name : 王强
         * section : [{"course_id":1,"section_id":1,"section_name":"总论","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":2,"section_name":"中文p1 第一章","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":2,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":3,"section_name":"中文p1 第2章","video":[{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":3,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":4,"section_name":"中文p1 第3章","video":[{"id":4,"video_name":"中文p2 视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":4,"course_id":1,"is_shoucang":2},{"id":5,"video_name":"中文p2视频2","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":4,"course_id":1,"is_shoucang":2},{"id":6,"video_name":"中文p2视频3","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":4,"course_id":1,"is_shoucang":2}]},{"course_id":1,"section_id":5,"section_name":"中文p1 第4章","video":[{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":4,"video_name":"中文p2 视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":5,"course_id":1,"is_shoucang":2},{"id":5,"video_name":"中文p2视频2","video_time":"00:00:11","VideoId":"7222710305204193be1c0ebb0c1fc1e0","section_id":5,"course_id":1,"is_shoucang":2}]}]
         */

        private String name;
        private String course_id;
        private String teacher_id;
        private String teacher_name;
        private String is_zhengke;
        private List<SectionBean> section;

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCourse_id() {
            return course_id == null ? "" : course_id;
        }

        public void setCourse_id(String course_id) {
            this.course_id = course_id;
        }

        public String getTeacher_id() {
            return teacher_id == null ? "" : teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getTeacher_name() {
            return teacher_name == null ? "" : teacher_name;
        }

        public void setTeacher_name(String teacher_name) {
            this.teacher_name = teacher_name;
        }

        public String getIs_zhengke() {
            return is_zhengke == null ? "" : is_zhengke;
        }

        public void setIs_zhengke(String is_zhengke) {
            this.is_zhengke = is_zhengke;
        }

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
             * course_id : 1
             * section_id : 1
             * section_name : 总论
             * video : [{"id":1,"video_name":"中文p1视频1","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":2,"video_name":"中文p1 视频2 ","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2},{"id":3,"video_name":"中文p1视频3","video_time":"00:00:12","VideoId":"d1a5604c959545bf949b9a2ad8b25b57","section_id":1,"course_id":1,"is_shoucang":2}]
             */

            private int course_id;
            private int section_id;
            private String section_name;
            private List<VideoBean> video;

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

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
                 * id : 1
                 * video_name : 中文p1视频1
                 * video_time : 00:00:12
                 * VideoId : d1a5604c959545bf949b9a2ad8b25b57
                 * section_id : 1
                 * course_id : 1
                 * is_shoucang : 2
                 */

                private int id;
                private String video_name;
                private String video_time;
                private String VideoId;
                private int section_id;
                private int course_id;
                private int is_shoucang;
                private boolean checked;

                public boolean getChecked() {
                    return checked;
                }

                public void setChecked(boolean checked) {
                    this.checked = checked;
                }

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

                public int getSection_id() {
                    return section_id;
                }

                public void setSection_id(int section_id) {
                    this.section_id = section_id;
                }

                public int getCourse_id() {
                    return course_id;
                }

                public void setCourse_id(int course_id) {
                    this.course_id = course_id;
                }

                public int getIs_shoucang() {
                    return is_shoucang;
                }

                public void setIs_shoucang(int is_shoucang) {
                    this.is_shoucang = is_shoucang;
                }
            }
        }
    }
}
