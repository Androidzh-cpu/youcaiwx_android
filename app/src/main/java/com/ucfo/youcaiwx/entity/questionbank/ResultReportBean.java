package com.ucfo.youcaiwx.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-24.  下午 2:51
 * FileName: ResultReportBean
 * Description:TODO 成绩统计实体类
 */
public class ResultReportBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"paper_name":"阶段测试","accuracy":"0.05","used_time":5,"true_num":1,"false_num":2,"not_num":1,"as_num":3,"question_num":20,"question_content":[{"question_id":"64","true_options":"C","user_answer":"B"},{"question_id":"69","true_options":"D","user_answer":"D"},{"question_id":"73","true_options":"D","user_answer":"C"},{"question_id":"74","true_options":"C","user_answer":""}]}
     */

    private int code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * paper_name : 阶段测试
         * accuracy : 0.05
         * used_time : 5
         * true_num : 1
         * false_num : 2
         * not_num : 1
         * as_num : 3
         * question_num : 20
         * question_content : [{"question_id":"64","true_options":"C","user_answer":"B"},{"question_id":"69","true_options":"D","user_answer":"D"},{"question_id":"73","true_options":"D","user_answer":"C"},{"question_id":"74","true_options":"C","user_answer":""}]
         */

        private String paper_name;
        private String accuracy;
        private int used_time;
        private int true_num;
        private int false_num;
        private int not_num;
        private int as_num;
        private int question_num;
        private List<QuestionContentBean> question_content;

        public String getPaper_name() {
            return paper_name == null ? "" : paper_name;
        }

        public void setPaper_name(String paper_name) {
            this.paper_name = paper_name;
        }

        public String getAccuracy() {
            return accuracy == null ? "" : accuracy;
        }

        public void setAccuracy(String accuracy) {
            this.accuracy = accuracy;
        }

        public int getUsed_time() {
            return used_time;
        }

        public void setUsed_time(int used_time) {
            this.used_time = used_time;
        }

        public int getTrue_num() {
            return true_num;
        }

        public void setTrue_num(int true_num) {
            this.true_num = true_num;
        }

        public int getFalse_num() {
            return false_num;
        }

        public void setFalse_num(int false_num) {
            this.false_num = false_num;
        }

        public int getNot_num() {
            return not_num;
        }

        public void setNot_num(int not_num) {
            this.not_num = not_num;
        }

        public int getAs_num() {
            return as_num;
        }

        public void setAs_num(int as_num) {
            this.as_num = as_num;
        }

        public int getQuestion_num() {
            return question_num;
        }

        public void setQuestion_num(int question_num) {
            this.question_num = question_num;
        }

        public List<QuestionContentBean> getQuestion_content() {
            if (question_content == null) {
                return new ArrayList<>();
            }
            return question_content;
        }

        public void setQuestion_content(List<QuestionContentBean> question_content) {
            this.question_content = question_content;
        }

        public static class QuestionContentBean {
            /**
             * question_id : 64
             * true_options : C
             * user_answer : B
             */

            private String question_id;
            private String true_options;
            private String user_answer;

            public String getQuestion_id() {
                return question_id == null ? "" : question_id;
            }

            public void setQuestion_id(String question_id) {
                this.question_id = question_id;
            }

            public String getTrue_options() {
                return true_options == null ? "" : true_options;
            }

            public void setTrue_options(String true_options) {
                this.true_options = true_options;
            }

            public String getUser_answer() {
                return user_answer == null ? "" : user_answer;
            }

            public void setUser_answer(String user_answer) {
                this.user_answer = user_answer;
            }
        }
    }
}
