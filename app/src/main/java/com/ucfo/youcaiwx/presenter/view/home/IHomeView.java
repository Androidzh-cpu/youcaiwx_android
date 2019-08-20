package com.ucfo.youcaiwx.presenter.view.home;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.home.HomeBean;

/**
 * Author:AND
 * Time: 2019/3/14.  10:23
 * Email:2911743255@qq.com
 * ClassName: IHomeView
 * Description:首页
 */
public interface IHomeView extends BaseView {

    //首页数据
    void getHomeData(HomeBean homeBean);

}
