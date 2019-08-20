package com.ucfo.youcaiwx.base;

/**
 * Author:AND
 * Time: 2019-3-12.  上午 9:15
 * Email:2911743255@qq.com
 * ClassName: BaseView
 * Description:
 * Detail:
 */
public interface BaseView {

    /**
     * 网络加载或耗时加载时界面显示
     */
    void showLoading();

    /**
     * 网络加载或耗时加载完成时界面显示
     */
    void showLoadingFinish();

    /**
     * 显示错误视图
     */
    void showError();

}
