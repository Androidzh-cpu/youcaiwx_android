package com.ucfo.youcaiwx.presenter.view.home.event;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.home.event.EventDetailedBean;
import com.ucfo.youcaiwx.entity.home.event.EventListBean;

/**
 * Author: AND
 * Time: 2019-12-20.  下午 3:28
 * Package: com.ucfo.youcaiwx.presenter.view.home.event
 * FileName: IEventView
 * Description:TODO 活动view
 */
public interface IEventView extends BaseView {

    /**
     * 获取活动首页列表
     *
     * @param bean
     */
    void initEventList(EventListBean bean);

    /**
     * 活动详情
     *
     * @param bean
     */
    void initEventDetailed(EventDetailedBean bean);

    /**
     * 活动报名结果
     *
     * @param status
     */
    void eventApplyResult(int status,String msg);

}
