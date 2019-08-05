package com.ucfo.youcai.entity.course;

/**
 * Author:29117
 * Time: 2019-4-23.  下午 2:23
 * Email:2911743255@qq.com
 * ClassName: CourseSocketBean
 * Package: com.ucfo.youcai.entity.course
 * Description:TODO  观课socket数据
 */
public class CourseSocketBean {

    private int user_id;//用户id
    private int package_id;//课程包ID
    private int course_id;//课程ID
    private int section_id;//章ID
    private int video_id;//视频ID
    private int watch_time;//视频节点
    private int video_type;//视频类型  1视频,2直播
    private int status;//播放类型  1课程播放
    private int plan_id;//学习计划ID
    private int days;//第几天学习计划

    public int getPlan_id() {
        return plan_id;
    }

    public void setPlan_id(int plan_id) {
        this.plan_id = plan_id;
    }

    public int getDays() {
        return days;
    }

    public void setDays(int days) {
        this.days = days;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public int getPackage_id() {
        return package_id;
    }

    public void setPackage_id(int package_id) {
        this.package_id = package_id;
    }

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

    public int getVideo_id() {
        return video_id;
    }

    public void setVideo_id(int video_id) {
        this.video_id = video_id;
    }

    public int getWatch_time() {
        return watch_time;
    }

    public void setWatch_time(int watch_time) {
        this.watch_time = watch_time;
    }

    public int getVideo_type() {
        return video_type;
    }

    public void setVideo_type(int video_type) {
        this.video_type = video_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
