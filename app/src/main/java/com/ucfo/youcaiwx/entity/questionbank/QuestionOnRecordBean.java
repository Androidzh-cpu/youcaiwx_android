package com.ucfo.youcaiwx.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-30.  上午 10:15
 * Email:2911743255@qq.com
 * ClassName: QuestionOnRecordBean
 * Description:TODO  答题记录列表实体类
 */
public class QuestionOnRecordBean {

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
         * id : 529
         * paper_name : 总论
         * status : 1
         * create_time : 2019-05-30 10:15:37
         * question_num : 5
         * paper_status : 1
         * course_id : 1
         * plate_id : 4
         * section_id : 1
         * question_content : {"knob_id":2,"know_id":1,"mock_id":0,"paper_id":0,"question":[{"question_id":"63","true_options":"D","user_answer":"C"}]}
         * knob_id : 2
         * know_id : 1
         * paper_id : 0
         * mock_id : 0
         * create_times : 2分钟前
         * state : 1
         */

        private int id;
        private String paper_name;
        private String status;
        private String create_time;
        private String question_num;
        private String paper_status;
        private int course_id;
        private int plate_id;
        private int section_id;
        private String paper_type;
        private String question_content;
        private String knob_id;
        private String know_id;
        private String paper_id;
        private String mock_id;
        private String create_times;
        private String state;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getPaper_name() {
            return paper_name == null ? "" : paper_name;
        }

        public void setPaper_name(String paper_name) {
            this.paper_name = paper_name;
        }

        public String getStatus() {
            return status == null ? "" : status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getQuestion_num() {
            return question_num == null ? "" : question_num;
        }

        public void setQuestion_num(String question_num) {
            this.question_num = question_num;
        }

        public String getPaper_status() {
            return paper_status == null ? "" : paper_status;
        }

        public void setPaper_status(String paper_status) {
            this.paper_status = paper_status;
        }

        public int getCourse_id() {
            return course_id;
        }

        public void setCourse_id(int course_id) {
            this.course_id = course_id;
        }

        public int getPlate_id() {
            return plate_id;
        }

        public void setPlate_id(int plate_id) {
            this.plate_id = plate_id;
        }

        public int getSection_id() {
            return section_id;
        }

        public void setSection_id(int section_id) {
            this.section_id = section_id;
        }

        public String getPaper_type() {
            return paper_type == null ? "" : paper_type;
        }

        public void setPaper_type(String paper_type) {
            this.paper_type = paper_type;
        }

        public String getQuestion_content() {
            return question_content == null ? "" : question_content;
        }

        public void setQuestion_content(String question_content) {
            this.question_content = question_content;
        }

        public String getKnob_id() {
            return knob_id == null ? "" : knob_id;
        }

        public void setKnob_id(String knob_id) {
            this.knob_id = knob_id;
        }

        public String getKnow_id() {
            return know_id == null ? "" : know_id;
        }

        public void setKnow_id(String know_id) {
            this.know_id = know_id;
        }

        public String getPaper_id() {
            return paper_id == null ? "" : paper_id;
        }

        public void setPaper_id(String paper_id) {
            this.paper_id = paper_id;
        }

        public String getMock_id() {
            return mock_id == null ? "" : mock_id;
        }

        public void setMock_id(String mock_id) {
            this.mock_id = mock_id;
        }

        public String getCreate_times() {
            return create_times == null ? "" : create_times;
        }

        public void setCreate_times(String create_times) {
            this.create_times = create_times;
        }

        public String getState() {
            return state == null ? "" : state;
        }

        public void setState(String state) {
            this.state = state;
        }
    }
}
