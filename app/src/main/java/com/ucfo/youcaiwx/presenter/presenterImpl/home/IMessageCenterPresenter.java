package com.ucfo.youcaiwx.presenter.presenterImpl.home;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 1:43
 * FileName: IMessageCenterPresenter
 * Description:TODO 消息中心
 */
public interface IMessageCenterPresenter {

    void getMessageHome(int userId);

    void getNoticeList(int userId, int page, int types);

    void havedReadMessage(int userId, int type, int message_id);

}
