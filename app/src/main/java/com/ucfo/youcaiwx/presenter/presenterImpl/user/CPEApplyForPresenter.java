package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.presenter.view.user.ICPEApplyForView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2020-1-2.  下午 5:24
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.user
 * FileName: CPEApplyForPresenter
 * Description:TODO CPE证明申请
 */
public class CPEApplyForPresenter {
    private ICPEApplyForView view;

    public CPEApplyForPresenter(ICPEApplyForView view) {
        this.view = view;
    }

    /**
     * 获取我的CPE课程列表
     *
     * @param user_id
     */
    public void initMineCPEList(int user_id) {
        OkGo.<String>post(ApiStores.EDUCATION_APPLYFOR)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.showError();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    Gson gson = new Gson();
                                } else {
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                        }
                    }
                });

    }
}
