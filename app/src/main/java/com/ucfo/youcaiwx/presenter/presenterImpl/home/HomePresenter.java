package com.ucfo.youcaiwx.presenter.presenterImpl.home;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.entity.home.HomeBean;
import com.ucfo.youcaiwx.presenter.view.home.IHomeView;
import com.ucfo.youcaiwx.utils.LogUtils;

/**
 * Author:AND
 * Time: 2019/3/14.  10:02
 * Email:2911743255@qq.com
 * ClassName: HomePresenter
 * Description:首页功能模块
 * Detail:
 */
public class HomePresenter implements IHomePresenter {

    private IHomeView homeView;

    public HomePresenter(IHomeView view) {
        this.homeView = view;
    }

    /**
     * Description:HomePresenter
     * Time:2019/3/14   10:27
     * Detail:获取首页数据
     */
    @Override
    public void getHomeData(String cachKey) {
        OkGo.<String>post(ApiStores.HOME_API)
                .tag(this)
                .cacheKey("home_" + cachKey)//设置当前请求的缓存KEY
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .retryCount(1)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        homeView.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        homeView.showError();
                        homeView.getHomeData(null);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        homeView.showLoadingFinish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals("")) {
                            int code = response.code();
                            if (code == 200) {
                                Gson gson = new Gson();
                                try {
                                    HomeBean homeBean = gson.fromJson(body, HomeBean.class);
                                    homeView.getHomeData(homeBean);
                                } catch (Throwable throwable) {
                                    LogUtils.e(throwable.getMessage());
                                    homeView.getHomeData(null);
                                }
                            } else {
                                homeView.getHomeData(null);
                            }
                        } else {
                            homeView.getHomeData(null);
                        }
                    }
                });
    }
}
