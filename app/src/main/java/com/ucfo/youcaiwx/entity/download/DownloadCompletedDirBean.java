package com.ucfo.youcaiwx.entity.download;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-10.  下午 1:21
 * FileName: DownloadCompletedDirBean
 * Description:TODO 下载完成章节model
 */
public class DownloadCompletedDirBean {

    private List<DataBean> data;

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
        private String courseId;
        private String sectionId;
        private String sectionName;
        private List<SonBean> son;

        public DataBean(String courseId, String sectionId, String sectionName, List<SonBean> son) {
            this.courseId = courseId;
            this.sectionId = sectionId;
            this.sectionName = sectionName;
            this.son = son;
        }

        public DataBean() {
        }

        public String getCourseId() {
            return courseId == null ? "" : courseId;
        }

        public void setCourseId(String courseId) {
            this.courseId = courseId;
        }

        public String getSectionId() {
            return sectionId == null ? "" : sectionId;
        }

        public void setSectionId(String sectionId) {
            this.sectionId = sectionId;
        }

        public String getSectionName() {
            return sectionName == null ? "" : sectionName;
        }

        public void setSectionName(String sectionName) {
            this.sectionName = sectionName;
        }

        public List<SonBean> getSon() {
            if (son == null) {
                return new ArrayList<>();
            }
            return son;
        }

        public void setSon(List<SonBean> son) {
            this.son = son;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "courseId='" + courseId + '\'' +
                    ", sectionId='" + sectionId + '\'' +
                    ", sectionName='" + sectionName + '\'' +
                    ", son=" + son +
                    '}';
        }

        public static class SonBean {
            private String courseId;
            private String sectionId;
            private String videoId;
            private String vid;
            private String videoName;
            private String videoDuration;
            private int status;//0未完成,1完成
            private String saveDir;
            private boolean checked;

            @Override
            public String toString() {
                return "SonBean{" +
                        "courseId='" + courseId + '\'' +
                        ", sectionId='" + sectionId + '\'' +
                        ", videoId='" + videoId + '\'' +
                        ", vid='" + vid + '\'' +
                        ", videoName='" + videoName + '\'' +
                        ", videoDuration='" + videoDuration + '\'' +
                        ", status=" + status +
                        ", saveDir='" + saveDir + '\'' +
                        ", checked=" + checked +
                        '}';
            }

            public SonBean() {
            }

            public SonBean(String courseId, String sectionId, String videoId, String vid, String videoName, String videoDuration, int status, String saveDir) {
                this.courseId = courseId;
                this.sectionId = sectionId;
                this.videoId = videoId;
                this.vid = vid;
                this.videoName = videoName;
                this.videoDuration = videoDuration;
                this.status = status;
                this.saveDir = saveDir;
            }

            public String getCourseId() {
                return courseId == null ? "" : courseId;
            }

            public void setCourseId(String courseId) {
                this.courseId = courseId;
            }

            public String getSectionId() {
                return sectionId == null ? "" : sectionId;
            }

            public void setSectionId(String sectionId) {
                this.sectionId = sectionId;
            }

            public String getVideoId() {
                return videoId == null ? "" : videoId;
            }

            public void setVideoId(String videoId) {
                this.videoId = videoId;
            }

            public String getVid() {
                return vid == null ? "" : vid;
            }

            public void setVid(String vid) {
                this.vid = vid;
            }

            public String getVideoName() {
                return videoName == null ? "" : videoName;
            }

            public void setVideoName(String videoName) {
                this.videoName = videoName;
            }

            public String getVideoDuration() {
                return videoDuration == null ? "" : videoDuration;
            }

            public void setVideoDuration(String videoDuration) {
                this.videoDuration = videoDuration;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public String getSaveDir() {
                return saveDir == null ? "" : saveDir;
            }

            public void setSaveDir(String saveDir) {
                this.saveDir = saveDir;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }
        }
    }
}
