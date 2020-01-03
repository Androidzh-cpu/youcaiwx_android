package com.ucfo.youcaiwx.entity.download;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Author: AND
 * Time: 2019-7-9.  上午 10:14
 * FileName: PreparedDownloadInfoBean
 * Description:TODO 准备下载信息
 */
public class PreparedDownloadInfoBean implements Parcelable {
    private String courseId;
    private String courseName;
    private String teacherName;
    private String sectionId;
    private String sectionSort;
    private String sectionName;
    private String videoId;
    private String sort;
    private String vid;
    private String videoName;
    private String videoDuration;


    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public void writeToParcel(Parcel arg0, int flags) {
        // TODO Auto-generated method stub
        // 1.必须按成员变量声明的顺序封装数据，不然会出现获取数据出错
        // 2.序列化对象
        arg0.writeString(courseId);
        arg0.writeString(courseName);
        arg0.writeString(teacherName);
        arg0.writeString(sectionId);
        arg0.writeString(sectionSort);
        arg0.writeString(sectionName);
        arg0.writeString(videoId);
        arg0.writeString(sort);
        arg0.writeString(vid);
        arg0.writeString(videoName);
        arg0.writeString(videoDuration);
    }

    // 1.必须实现Parcelable.Creator接口,否则在获取Person数据的时候，会报错，如下：
    // android.os.BadParcelableException:
    // Parcelable protocol requires a Parcelable.Creator object called  CREATOR on class com.um.demo.Person
    // 2.这个接口实现了从Percel容器读取Person数据，并返回Person对象给逻辑层使用
    // 3.实现Parcelable.Creator接口对象名必须为CREATOR，不如同样会报错上面所提到的错；
    // 4.在读取Parcel容器里的数据事，必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
    // 5.反序列化对象
    public static final Parcelable.Creator<PreparedDownloadInfoBean> CREATOR = new Creator<PreparedDownloadInfoBean>() {
        //实现从source中创建出类的实例的功能
        // TODO Auto-generated method stub
        // 必须按成员变量声明的顺序读取数据，不然会出现获取数据出错
        @Override
        public PreparedDownloadInfoBean createFromParcel(Parcel source) {
            PreparedDownloadInfoBean info = new PreparedDownloadInfoBean();
            info.setCourseId(source.readString());
            info.setCourseName(source.readString());
            info.setTeacherName(source.readString());
            info.setSectionId(source.readString());
            info.setSectionSort(source.readString());
            info.setSectionName(source.readString());
            info.setVideoId(source.readString());
            info.setSort(source.readString());
            info.setVid(source.readString());
            info.setVideoName(source.readString());
            info.setVideoDuration(source.readString());
            return info;
        }

        //创建一个类型为T，长度为size的数组
        @Override
        public PreparedDownloadInfoBean[] newArray(int size) {
            return new PreparedDownloadInfoBean[size];
        }
    };

    public String getVideoDuration() {
        return videoDuration == null ? "" : videoDuration;
    }

    public void setVideoDuration(String videoDuration) {
        this.videoDuration = videoDuration;
    }

    public String getCourseId() {
        return courseId == null ? "" : courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName == null ? "" : courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName == null ? "" : teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
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

    public static Creator<PreparedDownloadInfoBean> getCREATOR() {
        return CREATOR;
    }

    public String getSectionSort() {
        return sectionSort == null ? "" : sectionSort;
    }

    public void setSectionSort(String sectionSort) {
        this.sectionSort = sectionSort;
    }

    public String getSort() {
        return sort == null ? "" : sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "PreparedDownloadInfoBean{" +
                "courseId='" + courseId + '\'' +
                ", courseName='" + courseName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", sectionId='" + sectionId + '\'' +
                ", sectionSort='" + sectionSort + '\'' +
                ", sectionName='" + sectionName + '\'' +
                ", videoId='" + videoId + '\'' +
                ", sort='" + sort + '\'' +
                ", vid='" + vid + '\'' +
                ", videoName='" + videoName + '\'' +
                ", videoDuration='" + videoDuration + '\'' +
                '}';
    }
}
