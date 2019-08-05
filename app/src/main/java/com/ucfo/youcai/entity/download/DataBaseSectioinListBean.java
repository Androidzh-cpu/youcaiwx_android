package com.ucfo.youcai.entity.download;

import org.litepal.crud.LitePalSupport;

/**
 * Author: AND
 * Time: 2019-7-9.  上午 11:13
 * Package: com.ucfo.youcai.entity.download
 * FileName: DataBaseSectioinListBean
 * Description:TODO 章节目录列表DB
 */
public class DataBaseSectioinListBean extends LitePalSupport {
    private int id;
    private String courseId;
    private String sectionId;
    private String sectionName;
    private boolean checked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCourseId() {
        return courseId == null ? "" : courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return "DataBaseSectioinListBean{" +
                "id=" + id +
                ", courseId='" + courseId + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", checked=" + checked +
                '}';
    }
}
