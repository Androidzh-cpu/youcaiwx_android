package com.ucfo.youcaiwx.presenter.view.pay;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.pay.PayAliPayResponseBean;
import com.ucfo.youcaiwx.entity.pay.PayWeChatResponseBean;

/**
 * Author: AND
 * Time: 2019-9-10.  上午 11:50
 * Package: com.ucfo.youcaiwx.presenter.view.pay
 * FileName: IPayMentView
 * 订单查询结果
 */
public interface IPayMentView extends BaseView {

    void initWeCheatOrderFormDetail(PayWeChatResponseBean data);

    void initAlipayOrderFormDetail(PayAliPayResponseBean data);

    void checkPayResult(int status);

}
