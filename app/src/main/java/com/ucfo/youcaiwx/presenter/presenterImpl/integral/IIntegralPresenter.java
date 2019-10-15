package com.ucfo.youcaiwx.presenter.presenterImpl.integral;

/**
 * Author: AND
 * Time: 2019-10-11.  下午 2:58
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.integral
 * FileName: IIntegralPresenter
 * Description:TODO 积分业务抽离
 */
public interface IIntegralPresenter {
    /**
     * 我的积分查询
     */
    void inquireIntegral(int userId);

    /**
     * 商品列表(积分首页)
     */
    void inquireIntegralProductHome();

    /**
     * 全部积分商品(不包含优惠券商品)
     */
    void inquireIntegralProductList(int type);

    /**
     * 积分兑换记录
     */
    void inquireIntegralExchangeRecord(int userId);

    /**
     * 积分明细
     */
    void inquireIntegralDetail(int userId);

    /**
     * 赚积分
     */
    void earnIntegral(int user_id);
}
