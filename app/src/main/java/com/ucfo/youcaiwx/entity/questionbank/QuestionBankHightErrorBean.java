package com.ucfo.youcaiwx.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-9.  上午 9:55
 * FileName: QuestionBankHightErrorBean
 * Description:TODO 系统高频错题
 */
public class QuestionBankHightErrorBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"section_id":1,"section_name":"总论","sectionnum":1,"knob":[{"knob_id":1,"knob_name":"大傻","knownum":0},{"knob_id":2,"knob_name":"二傻","knownum":1},{"knob_id":6,"knob_name":"weqw","knownum":0},{"knob_id":7,"knob_name":"weqw","knownum":0},{"knob_id":8,"knob_name":"weqw","knownum":0}]},{"section_id":2,"section_name":"成本管理","sectionnum":0,"knob":[{"knob_id":3,"knob_name":"最傻","knownum":0}]},{"section_id":3,"section_name":"内部控制","sectionnum":0,"knob":[]},{"section_id":4,"section_name":"绩效管理","sectionnum":0,"knob":[]}]
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
         * section_id : 1
         * section_name : 总论
         * sectionnum : 1
         * knob : [{"knob_id":1,"knob_name":"大傻","knownum":0},{"knob_id":2,"knob_name":"二傻","knownum":1},{"knob_id":6,"knob_name":"weqw","knownum":0},{"knob_id":7,"knob_name":"weqw","knownum":0},{"knob_id":8,"knob_name":"weqw","knownum":0}]
         */

        private int section_id;
        private String section_name;
        private int sectionnum;
        private List<KnobBean> knob;

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

        public int getSectionnum() {
            return sectionnum;
        }

        public void setSectionnum(int sectionnum) {
            this.sectionnum = sectionnum;
        }

        public List<KnobBean> getKnob() {
            if (knob == null) {
                return new ArrayList<>();
            }
            return knob;
        }

        public void setKnob(List<KnobBean> knob) {
            this.knob = knob;
        }

        public static class KnobBean {
            /**
             * knob_id : 1
             * knob_name : 大傻
             * knownum : 0
             */

            private int knob_id;
            private String knob_name;
            private int knownum;

            public int getKnob_id() {
                return knob_id;
            }

            public void setKnob_id(int knob_id) {
                this.knob_id = knob_id;
            }

            public String getKnob_name() {
                return knob_name == null ? "" : knob_name;
            }

            public void setKnob_name(String knob_name) {
                this.knob_name = knob_name;
            }

            public int getKnownum() {
                return knownum;
            }

            public void setKnownum(int knownum) {
                this.knownum = knownum;
            }
        }
    }
}
