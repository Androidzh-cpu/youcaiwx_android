package com.ucfo.youcai.presenter.view.home;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.home.HomeBean;

/**
 * Author:AND
 * Time: 2019/3/14.  10:23
 * Email:2911743255@qq.com
 * ClassName: IHomeView
 * Package: com.ucfo.youcai.presenter.presenterImpl.home
 * Description:首页
 * Detail:
 */
public interface IHomeView extends BaseView {

    //首页数据
    void getHomeData(HomeBean homeBean);

}
