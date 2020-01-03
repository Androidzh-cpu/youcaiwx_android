package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2020-1-3.  上午 9:15
 * Package: com.ucfo.youcaiwx.entity.user
 * FileName: CPEApplyInfoBean
 * Description:TODO cpe证明信息
 */
public class CPEApplyInfoBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"time":2020,"list":[{"id":10,"course_id":4,"cpe_integral":null,"end_time":"2020-01-02","endtime":"2020","type":2,"teacher_name":null,"name":null,"app_img":null},{"id":9,"course_id":3,"cpe_integral":10,"end_time":"2020-01-01","endtime":"2020","type":1,"teacher_name":"杨晔","name":"课程3","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191231/cad13ca78e41eb59bf353e1f602a1b73.jpeg"}]},{"time":2019,"list":[{"id":8,"course_id":2,"cpe_integral":30,"end_time":"2019-12-29","endtime":"2019","type":2,"teacher_name":"2","name":"2018(深圳)管理会计实践论坛邀请函","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191230/13b9001a914bfa87a505e70e6ad9b0a6.jpeg"}]},{"time":2018,"list":[{"id":7,"course_id":1,"cpe_integral":20,"end_time":"2018-12-31","endtime":"2018","type":1,"teacher_name":"杨晔","name":"课程1","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191230/ed6afcd83886712a0c4cdfe7d9f6f584.jpeg"}]}]
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
         * time : 2020
         * list : [{"id":10,"course_id":4,"cpe_integral":null,"end_time":"2020-01-02","endtime":"2020","type":2,"teacher_name":null,"name":null,"app_img":null},{"id":9,"course_id":3,"cpe_integral":10,"end_time":"2020-01-01","endtime":"2020","type":1,"teacher_name":"杨晔","name":"课程3","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191231/cad13ca78e41eb59bf353e1f602a1b73.jpeg"}]
         */

        private String time;
        private List<ListBean> list;
        private boolean checked;

        public boolean getChecked() {
            return checked;
        }

        public void setChecked(boolean checked) {
            this.checked = checked;
        }

        public String getTime() {
            return time == null ? "" : time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public List<ListBean> getList() {
            if (list == null) {
                return new ArrayList<>();
            }
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 10
             * course_id : 4
             * cpe_integral : null
             * end_time : 2020-01-02
             * endtime : 2020
             * type : 2
             * teacher_name : null
             * name : null
             * app_img : null
             */
            private String id;
            private String course_id;
            private String cpe_integral;
            private String end_time;
            private String endtime;
            private String type;
            private String teacher_name;
            private String name;
            private String app_img;
            private boolean checked;

            public boolean getChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }

            public String getId() {
                return id == null ? "" : id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getCourse_id() {
                return course_id == null ? "" : course_id;
            }

            public void setCourse_id(String course_id) {
                this.course_id = course_id;
            }

            public String getCpe_integral() {
                return cpe_integral == null ? "" : cpe_integral;
            }

            public void setCpe_integral(String cpe_integral) {
                this.cpe_integral = cpe_integral;
            }

            public String getEnd_time() {
                return end_time == null ? "" : end_time;
            }

            public void setEnd_time(String end_time) {
                this.end_time = end_time;
            }

            public String getEndtime() {
                return endtime == null ? "" : endtime;
            }

            public void setEndtime(String endtime) {
                this.endtime = endtime;
            }

            public String getType() {
                return type == null ? "" : type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getApp_img() {
                return app_img == null ? "" : app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }
        }
    }
}
