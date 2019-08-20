package com.ucfo.youcaiwx.presenter.view;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.pay.CommitOrderFormBean;
import com.ucfo.youcaiwx.entity.pay.OrderFormDetailBean;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:32
 * FileName: IPayView
 * Description:TODO 支付
 */
public interface IPayView extends BaseView {

    void getOrderFormDetail(OrderFormDetailBean data);

    void commitOrderForm(CommitOrderFormBean bean);

}
