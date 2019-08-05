package com.ucfo.youcai.presenter.view;

import com.ucfo.youcai.base.BaseView;
import com.ucfo.youcai.entity.pay.AivilableCouponBean;
import com.ucfo.youcai.entity.pay.OrderFormDetailBean;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:32
 * Package: com.ucfo.youcai.presenter.view
 * FileName: IPayView
 * Description:TODO 支付
 */
public interface IPayView extends BaseView {

    void getOrderFormDetail(OrderFormDetailBean data);

    void getAivilableCoupon(AivilableCouponBean data);

}
