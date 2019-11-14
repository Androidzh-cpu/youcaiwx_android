package com.ucfo.youcaiwx.presenter.view.complain;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.complain.ComplainTypeBean;

/**
 * Author: AND
 * Time: 2019-11-13.  下午 4:18
 * Package: com.ucfo.youcaiwx.presenter.view.complain
 * FileName: IComplainView
 * Description:TODO 投诉
 */
public interface IComplainView extends BaseView {

    void initComplainType(ComplainTypeBean dataBean);

    void complainResult(int status);
}
