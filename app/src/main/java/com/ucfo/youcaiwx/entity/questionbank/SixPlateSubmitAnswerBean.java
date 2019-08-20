package com.ucfo.youcaiwx.entity.questionbank;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-30.  上午 9:44
 * FileName: SixPlateSubmitAnswerBean
 * Description:TODO 6大板块交卷所需答案
 */
public class SixPlateSubmitAnswerBean {
    private int paper_id;//试卷ID
    private int knob_id;//节
    private String know_id;//知识点
    private int mock_id;//组卷
    private List<QuestionBean> question;//题目信息

    public int getPaper_id() {
        return paper_id;
    }

    public void setPaper_id(int paper_id) {
        this.paper_id = paper_id;
    }

    public int getKnob_id() {
        return knob_id;
    }

    public void setKnob_id(int knob_id) {
        this.knob_id = knob_id;
    }

    public String getKnow_id() {
        return know_id == null ? "" : know_id;
    }

    public void setKnow_id(String know_id) {
        this.know_id = know_id;
    }

    public int getMock_id() {
        return mock_id;
    }

    public void setMock_id(int mock_id) {
        this.mock_id = mock_id;
    }

    public List<QuestionBean> getQuestion() {
        if (question == null) {
            return new ArrayList<>();
        }
        return question;
    }

    public void setQuestion(List<QuestionBean> question) {
        this.question = question;
    }

    public static class QuestionBean {
        /**
         * question_id : 53
         * true_options : D
         * user_answer : A
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
