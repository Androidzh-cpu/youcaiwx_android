package com.ucfo.youcaiwx.presenter.view.home;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.home.MessageCenterHomeBean;
import com.ucfo.youcaiwx.entity.home.MessageCenterNoticeBean;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 1:48
 * FileName: IMessageCenterView
 * Description:TODO 消息中心
 */
public interface IMessageCenterView extends BaseView {

    void getMessageCenterHome(MessageCenterHomeBean data);

    void getMessageCenterNoticeList(MessageCenterNoticeBean data);

}
