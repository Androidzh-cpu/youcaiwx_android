package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.user.MineCPEWatchRecordBean;
import com.ucfo.youcaiwx.entity.user.MineWatchRecordBean;

/**
 * Author: AND
 * Time: 2019-12-30.  上午 11:07
 * Package: com.ucfo.youcaiwx.presenter.view.user
 * FileName: IWatchTheRecordView
 * Description:TODO 观看记录
 */
public interface IWatchTheRecordView extends BaseView {
    /**
     * 我的观看记录
     *
     * @param data
     */
    void getMineWatchRecord(MineWatchRecordBean data);

    /**
     * 我的CPE观看记录
     *
     * @param data
     */
    void getMineCPEWatchRecord(MineCPEWatchRecordBean data);

}
