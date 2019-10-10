package com.ucfo.youcaiwx.presenter.presenterImpl.integral;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;

/**
 * Author: AND
 * Time: 2019-10-10.  下午 4:23
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.integral
 * FileName: EarnIntegralPresenter
 * Description:TODO 任务获取积分
 */
public class EarnIntegralPresenter {
    private static EarnIntegralPresenter instance;

    private EarnIntegralPresenter() {
    }

    public static EarnIntegralPresenter getInstance() {
        if (instance == null) {
            instance = new EarnIntegralPresenter();
        }
        return instance;
    }

    /**
     * 做任务获取积分
     */
    public void earnIntegralForTask(int shareType, int userId) {
        if (userId == 0) {
            return;
        }
        OkGo.<String>post(ApiStores.INTEGRAL_EARNINTEGREAL)
                .params(Constant.USER_ID, userId)
                .params(Constant.TYPE_ID, shareType)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });

    }
}
