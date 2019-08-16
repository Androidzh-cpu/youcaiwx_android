package com.ucfo.youcai.presenter.presenterImpl.questionbank;

import com.ucfo.youcai.entity.questionbank.DoProblemsAnswerBean;

import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-16.  下午 5:05
 * Package: com.ucfo.youcai.presenter.presenterImpl.questionbank
 * FileName: IQuestionBankExercisePresenter
 * Detail:TODO 做题业务操作 (拿题,收藏,答疑等等)
 */
public interface IQuestionBankExercisePresenter {
    //TODO 组卷模考获取试卷
    void getGroupExamProblemsData(int course_id, int plate_id, int paper_type, int mock_id, int user_id);

    //TODO 阶段测试获取试卷
    void getStageOfTesting(int course_id, int user_id, int plate_id, int paper_type, int paper_id);

    //TODO 知识点练习获取试卷
    void getKnowledgePractice(int course_id, int user_id, int plate_id, int paper_type, int section_id, int knob_id, String know_id, int num);

    //TODO 系统高频错题
    void getQuestionHightErrors(int course_id, int user_id, int plate_id, int paper_type, int section_id, String know_id);

    //TODO 自助练习
    void getSelfHelpPractice(int course_id, int user_id, int plate_id, int paper_type, int section_id, String know_id, int num);

    //TODO 论述题自测
    void getDissCussData(int course_id, int user_id, int plate_id, int paper_type, int paper_id);

    //TODO 错题中心查看解析
    void getErrorCenterCheckAnalysis(int course_id, int user_id, int section_id, String know_id);

    //TODO 错题中心 重新做题
    void getErrorCenterReform(int course_id, int user_id, int section_id,String knob_id, String know_id);

    //TODO 获取错题解析
    void getErrorAnalysis(int paper_id, int user_id, int type);

    //TODO 获取错题中心解析(错题解析和全部解析)
    void getErrorCenterAnalysis(int user_id, int course_id, int section_id, String know_id, int type, String question_content);

    //TODO 6大板块交卷
    void submitPapers(int course_id, int user_id, int plate_id, int status, int section_id, int knob_id, String know_id, int paper_id, int mock_id, int used_time, int all_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList);

    //TODO 学习中心交卷
    void learnCenterSubmitPapers(int course_id, int user_id, int status, int used_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList, String videoName, String know_id);

    //TODO 答题记录交卷,牛批
    void contimueSubmitPapers(int course_id, int id, int user_id, int plate_id, int status, int section_id, int knob_id, String know_id, int paper_id, int mock_id, int used_time, int all_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList);

    //TODO 错题中心交卷
    void errorCenterSubmit(ArrayList<DoProblemsAnswerBean> optionsAnswerList, int user_id, int course_id);

    //TODO 题目收藏
    void setProblemsCollection(int user_id, int course_id, int question_id, int function);

    //TODO 删除题目
    void deleteProblems(int course_id, int user_id, int question_id);

    //TODO 继续做题获取题目
    void getContinueExamData(int user_id, int id);

    //TODO 答题记录查看论述题试题
    void getContinueDiscussAnalysis(int user_id, int paper_id);

    //TODO 0元体验-获取试题
    void getFreeExperinece(int user_id);

    //TODO 0元体验-获取试题解析
    void getFreeExperineceAnalysis(int user_id, int type, String question_content);

    //TODO 查看我的收藏题目
    void getMineCollectionList(int user_id, int course_id, int section_id, int knob_id, String know_id);

    //TODO 学习计划获取试题
    void getLearnPlanExerciseList(int user_id, String know_id, String video_name);

    //TODO 学习中心获取全部解析
    void getLearnPlanExerciseAnalysis(int user_id, int paper_id);

}
