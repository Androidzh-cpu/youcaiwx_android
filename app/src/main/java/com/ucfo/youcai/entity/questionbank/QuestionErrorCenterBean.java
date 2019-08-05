package com.ucfo.youcai.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-10.  下午 5:43
 * Package: com.ucfo.youcai.entity.questionbank
 * FileName: QuestionErrorCenterBean
 * Description:TODO 我的错题列表
 * Detail:TODO
 */
public class QuestionErrorCenterBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"section_id":1,"section_name":"总论211","sectionerrornum":0,"knob":[{"knob_id":1,"knob_name":"大傻","knowerrornum":4},{"knob_id":2,"knob_name":"二傻","knowerrornum":1},{"knob_id":6,"knob_name":"weqw","knowerrornum":0},{"knob_id":7,"knob_name":"weqw","knowerrornum":0},{"knob_id":8,"knob_name":"weqw","knowerrornum":0},{"knob_id":9,"knob_name":"weqw","knowerrornum":0},{"knob_id":10,"knob_name":"weqw","knowerrornum":0},{"knob_id":11,"knob_name":"weqw","knowerrornum":0}]},{"section_id":2,"section_name":"成本管理","sectionerrornum":5,"knob":[{"knob_id":3,"knob_name":"最傻","knowerrornum":0},{"knob_id":13,"knob_name":"第三次 ","knowerrornum":5}]},{"section_id":3,"section_name":"内部控制","sectionerrornum":5,"knob":[]},{"section_id":4,"section_name":"绩效管理","sectionerrornum":5,"knob":[]},{"section_id":12,"section_name":"x'e'x'x 发","sectionerrornum":5,"knob":[]},{"section_id":13,"section_name":"ggg","sectionerrornum":5,"knob":[]},{"section_id":20,"section_name":"道为1","sectionerrornum":5,"knob":[]}]
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
         * section_name : 总论211
         * sectionerrornum : 0
         * knob : [{"knob_id":1,"knob_name":"大傻","knowerrornum":4},{"knob_id":2,"knob_name":"二傻","knowerrornum":1},{"knob_id":6,"knob_name":"weqw","knowerrornum":0},{"knob_id":7,"knob_name":"weqw","knowerrornum":0},{"knob_id":8,"knob_name":"weqw","knowerrornum":0},{"knob_id":9,"knob_name":"weqw","knowerrornum":0},{"knob_id":10,"knob_name":"weqw","knowerrornum":0},{"knob_id":11,"knob_name":"weqw","knowerrornum":0}]
         */

        private int section_id;
        private String section_name;
        private int sectionerrornum;
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

        public int getSectionerrornum() {
            return sectionerrornum;
        }

        public void setSectionerrornum(int sectionerrornum) {
            this.sectionerrornum = sectionerrornum;
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
             * knowerrornum : 4
             */

            private int knob_id;
            private String knob_name;
            private int knowerrornum;

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

            public int getKnowerrornum() {
                return knowerrornum;
            }

            public void setKnowerrornum(int knowerrornum) {
                this.knowerrornum = knowerrornum;
            }
        }
    }
}
