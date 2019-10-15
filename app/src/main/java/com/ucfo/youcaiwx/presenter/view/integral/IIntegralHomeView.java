package com.ucfo.youcaiwx.presenter.view.integral;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.integral.IntegralShopHomeBean;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 3:08
 * Package: com.ucfo.youcaiwx.presenter.view.integral
 * FileName: IIntegralHomeView
 * Description:TODO 积分首页v层
 */
public interface IIntegralHomeView extends BaseView {

    /**
     * 我的积分查询
     */
    void inquiryIntegral(String integral);

    /**
     * 积分商城首页
     */
    void inqieryIntegralHome(IntegralShopHomeBean integralShopHomeBean);

}
