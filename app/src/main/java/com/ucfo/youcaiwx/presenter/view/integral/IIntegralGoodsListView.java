package com.ucfo.youcaiwx.presenter.view.integral;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.integral.IntegralCouponsListBean;
import com.ucfo.youcaiwx.entity.integral.IntegralProductListBean;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 5:50
 * Package: com.ucfo.youcaiwx.presenter.view.integral
 * FileName: IIntegralGoodsListView
 * Description:TODO 积分兑换商品列表(优惠券和普通商品)
 */
public interface IIntegralGoodsListView extends BaseView {

    /**
     * 积分兑换优惠券列表
     */
    void inqueryIntegralCouponList(IntegralCouponsListBean bean);

    /**
     * 积分兑换商品列表
     */
    void inqueryIntegralProductList(IntegralProductListBean bean);
}
