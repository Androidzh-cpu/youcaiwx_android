package com.ucfo.youcai.entity.download;

import org.litepal.crud.LitePalSupport;

/**
 * Author: AND
 * Time: 2019-7-9.  上午 11:20
 * Package: com.ucfo.youcai.entity.download
 * FileName: DataBaseVideoListBean
 * Description:TODO 视频目录列表DB
 */
public class DataBaseVideoListBean extends LitePalSupport {
    private int id;
    private String courseId;
    private String sectionId;
    private String videoId;
    private String vid;
    private String videoName;
    private String videoDuration;
    private int status;//0未完成,1完成
    private String saveDir;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return "DataBaseVideoListBean{" +
                "id=" + id +
                ", courseId='" + courseId + '\'' +
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
}
