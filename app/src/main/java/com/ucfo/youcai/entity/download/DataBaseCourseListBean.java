package com.ucfo.youcai.entity.download;

import org.litepal.crud.LitePalSupport;

/**
 * Author: AND
 * Time: 2019-7-9.  上午 11:09
 * Package: com.ucfo.youcai.entity.download
 * FileName: DataBaseCourseListBean
 * Description:TODO 课程目录列表DB
 */
public class DataBaseCourseListBean extends LitePalSupport {
    private int id;
    private String courseId;
    private String courseTitle;
    private String teacherName;
    private int courseDownloadNum;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseId() {
        return courseId == null ? "" : courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle == null ? "" : courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public String getTeacherName() {
        return teacherName == null ? "" : teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public int getCourseDownloadNum() {
        return courseDownloadNum;
    }

    public void setCourseDownloadNum(int courseDownloadNum) {
        this.courseDownloadNum = courseDownloadNum;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "DataBaseCourseListBean{" +
                "id=" + id +
                ", courseId='" + courseId + '\'' +
                ", courseTitle='" + courseTitle + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", courseDownloadNum=" + courseDownloadNum +
                ", checked=" + checked +
                '}';
    }
}
