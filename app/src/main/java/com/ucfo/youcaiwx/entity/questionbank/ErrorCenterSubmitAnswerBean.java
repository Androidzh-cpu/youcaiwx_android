package com.ucfo.youcaiwx.entity.questionbank;

/**
 * Author: AND
 * Time: 2019-5-23.  下午 4:58
 * FileName: ErrorCenterSubmitAnswerBean
 * Description:TODO 错题中心交卷JSON串
 */
public class ErrorCenterSubmitAnswerBean {
    private String question_id;// 题目所属ID
    private String true_options;// 正确答案
    private String user_answer;// 用户答案

    public ErrorCenterSubmitAnswerBean(String question_id, String true_options, String user_answer) {
        this.question_id = question_id;
        this.true_options = true_options;
        this.user_answer = user_answer;
    }

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
