package com.ucfo.youcaiwx.entity.user;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-19.  下午 2:56
 * FileName: MineQuestionCollectionListBean
 * Description:TODO 题库收藏二级列表
 */
public class MineQuestionCollectionListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"section_id":1,"section_name":"对外财务报告决策","knob":[{"knob_id":2,"knob_name":"确认，计量，计价和披露","knobcoun":1}],"sectioncoun":1},{"section_id":4,"section_name":"成本管理","knob":[{"knob_id":8,"knob_name":"4.3 间接成本","knobcoun":1}],"sectioncoun":1}]
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
         * section_id : 1
         * section_name : 对外财务报告决策
         * knob : [{"knob_id":2,"knob_name":"确认，计量，计价和披露","knobcoun":1}]
         * sectioncoun : 1
         */

        private int section_id;
        private String section_name;
        private int sectioncoun;
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

        public int getSectioncoun() {
            return sectioncoun;
        }

        public void setSectioncoun(int sectioncoun) {
            this.sectioncoun = sectioncoun;
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
             * knob_id : 2
             * knob_name : 确认，计量，计价和披露
             * knobcoun : 1
             */

            private int knob_id;
            private String knob_name;
            private int knobcoun;

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

            public int getKnobcoun() {
                return knobcoun;
            }

            public void setKnobcoun(int knobcoun) {
                this.knobcoun = knobcoun;
            }
        }
    }
}
