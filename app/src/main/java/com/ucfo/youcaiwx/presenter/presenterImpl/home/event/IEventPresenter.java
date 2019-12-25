package com.ucfo.youcaiwx.presenter.presenterImpl.home.event;

/**
 * Author: AND
 * Time: 2019-12-20.  下午 3:38
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.home.event
 * FileName: IEventPresenter
 * Description:TODO 活动
 */
public interface IEventPresenter {

    /**
     * 活动列表
     */
    void initEventList();

    /**
     * 活动详情
     */
    void initEventDetailed(String user_id, String id);

    /**
     * 活动提交
     */
    void commitEventInformation(String userId, String id, String name, String phone);
}
