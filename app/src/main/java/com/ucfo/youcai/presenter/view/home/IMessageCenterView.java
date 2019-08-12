package com.ucfo.youcai.presenter.view.home;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.home.MessageCenterHomeBean;
import com.ucfo.youcai.entity.home.MessageCenterNoticeBean;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 1:48
 * Package: com.ucfo.youcai.presenter.view.home
 * FileName: IMessageCenterView
 * Description:TODO 消息中心
 */
public interface IMessageCenterView extends BaseView {

    void getMessageCenterHome(MessageCenterHomeBean data);

    void getMessageCenterNoticeList(MessageCenterNoticeBean data);

}
