package com.ucfo.youcai.presenter.presenterImpl.questionbank;

/**
 * Author: AND
 * Time: 2019-5-9.  上午 9:48
 * Package: com.ucfo.youcai.presenter.presenterImpl.questionbank
 * FileName: IQuestionBankChapterPresenter
 * Description:TODO  知识点练习和系统高频错题的目录结构一样
 */
public interface IQuestionBankChapterPresenter {
    //TODO 获取知识点章节列表
    void getKnowledgeListData(int course_id, int user_id);

    //TODO 获取知识点列表
    void getKnowledgeChildList(int course_id, int section_id, int knob_id);

    //TODO 获取高频错题列表
    void getHighFrequencyWrongTopic(int course_id);

    //TODO 高频错题知识点列表
    void getHighFrequencyWrongChildList(int course_id, int section_id, int knob_id);

    //TODO 获取错题中心章节列表
    void getErrorCenterSectionList(int course_id, int user_id);

    //TODO 题库收藏知识点列表
    void getQuestionCollectionList(int user_id, int course_id, int section_id, int knob_id);
}
