package com.ucfo.youcaiwx.entity.questionbank;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 10:55
 * FileName: QuestionStageOfTestBean
 * Description:TODO
 * Detail:TODO
 */
public class QuestionStageOfTestBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"section_name":"总论","difficulty":"A"},{"section_name":"成本管理","difficulty":"B"},{"section_name":"内部控制","difficulty":"B"},{"section_name":"绩效管理","difficulty":"A"}]
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
         * section_name : 总论
         * difficulty : A
         */

        int mock_id;
        int paper_id;
        private String section_name;
        private String paper_name;
        private String difficulty;

        public int getPaper_id() {
            return paper_id;
        }

        public void setPaper_id(int paper_id) {
            this.paper_id = paper_id;
        }

        public String getPaper_name() {
            return paper_name == null ? "" : paper_name;
        }

        public void setPaper_name(String paper_name) {
            this.paper_name = paper_name;
        }

        public int getMock_id() {
            return mock_id;
        }

        public void setMock_id(int mock_id) {
            this.mock_id = mock_id;
        }

        public String getSection_name() {
            return section_name == null ? "" : section_name;
        }

        public void setSection_name(String section_name) {
            this.section_name = section_name;
        }

        public String getDifficulty() {
            return difficulty == null ? "" : difficulty;
        }

        public void setDifficulty(String difficulty) {
            this.difficulty = difficulty;
        }
    }
}