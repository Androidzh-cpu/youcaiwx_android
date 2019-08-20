package com.ucfo.youcaiwx.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-7.  下午 1:57
 * FileName: QuestionKnowledgeListBean
 * Description:TODO 知识点练习耳机二级列表
 * Detail:TODO
 */
public class QuestionKnowledgeListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"section_id":1,"section_name":"总论","correct":"100.00","have":"0.48","knob":[{"id":1,"knob_name":"大傻","knobcorrect":"100.00","knobhave":"3.57"},{"id":2,"knob_name":"二傻","knobcorrect":0,"knobhave":"0.00"},{"id":6,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"},{"id":7,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"},{"id":8,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"}]},{"section_id":2,"section_name":"成本管理","correct":0,"have":"0.00","knob":[{"id":3,"knob_name":"最傻","knobcorrect":0,"knobhave":"0.00"}]},{"section_id":3,"section_name":"内部控制","correct":0,"have":"0.00","knob":[]},{"section_id":4,"section_name":"绩效管理","correct":0,"have":"0.00","knob":[]}]
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
         * correct : 100.00
         * have : 0.48
         * knob : [{"id":1,"knob_name":"大傻","knobcorrect":"100.00","knobhave":"3.57"},{"id":2,"knob_name":"二傻","knobcorrect":0,"knobhave":"0.00"},{"id":6,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"},{"id":7,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"},{"id":8,"knob_name":"weqw","knobcorrect":0,"knobhave":"0.00"}]
         */

        private int section_id;
        private String section_name;
        private String correct;
        private String have;
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

        public String getCorrect() {
            return correct == null ? "" : correct;
        }

        public void setCorrect(String correct) {
            this.correct = correct;
        }

        public String getHave() {
            return have == null ? "" : have;
        }

        public void setHave(String have) {
            this.have = have;
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
             * id : 1
             * knob_name : 大傻
             * knobcorrect : 100.00
             * knobhave : 3.57
             */

            private int knob_id;
            private String knob_name;
            private String knobcorrect;
            private String knobhave;

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

            public String getKnobcorrect() {
                return knobcorrect == null ? "" : knobcorrect;
            }

            public void setKnobcorrect(String knobcorrect) {
                this.knobcorrect = knobcorrect;
            }

            public String getKnobhave() {
                return knobhave == null ? "" : knobhave;
            }

            public void setKnobhave(String knobhave) {
                this.knobhave = knobhave;
            }
        }
    }
}
