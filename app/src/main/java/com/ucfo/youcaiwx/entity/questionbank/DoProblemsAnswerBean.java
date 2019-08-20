package com.ucfo.youcaiwx.entity.questionbank;

/**
 * Author: AND
 * Time: 2019-5-20.  下午 6:40
 * FileName: DoProblemsAnswerBean
 * Description:TODO  做题页答题卡
 */
public class DoProblemsAnswerBean {

    private String question_id;// 题目所属ID
    private String true_options;// 正确答案
    private String user_answer;// 用户答案
    private int position;// 题所在位置  索引值

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

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
