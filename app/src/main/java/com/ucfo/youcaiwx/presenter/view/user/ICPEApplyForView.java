package com.ucfo.youcaiwx.presenter.view.user;

import com.ucfo.youcaiwx.base.BaseView;
import com.ucfo.youcaiwx.entity.user.CPEApplyInfoBean;

/**
 * Author: AND
 * Time: 2020-1-2.  下午 5:22
 * Package: com.ucfo.youcaiwx.presenter.view.user
 * FileName: ICPEApplyForView
 * Description:TODO CPE证明申请
 */
public interface ICPEApplyForView extends BaseView {

    void initMineCPEList(CPEApplyInfoBean bean);

    void createReport(int code, String message, String imageUrl);

}
