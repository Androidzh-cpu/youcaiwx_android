package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.CPEApplyInfoBean;
import com.ucfo.youcaiwx.presenter.view.user.ICPEApplyForView;

import org.json.JSONArray;
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
                                    Object data = jsonObject.get(Constant.DATA);
                                    if (data instanceof JSONArray) {
                                        Gson gson = new Gson();
                                        CPEApplyInfoBean bean = gson.fromJson(body, CPEApplyInfoBean.class);
                                        view.initMineCPEList(bean);
                                    } else {
                                        view.initMineCPEList(null);
                                    }
                                } else {
                                    view.initMineCPEList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initMineCPEList(null);
                        }
                    }
                });
    }

    /**
     * 生成学分报告
     *
     * @param user_id
     */
    public void createReport(int user_id, String id, String user_name, String membership, String identity, int source) {
        OkGo.<String>post(ApiStores.EDUCATION_CREATE_REPORT)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.ID, id)
                .params("user_name", user_name)
                .params("membership", membership)
                .params("identity", identity)
                .params("source", source)
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
                                String message = jsonObject.optString("message");
                                if (code == 200) {
                                    JSONObject data = jsonObject.optJSONObject(Constant.DATA);
                                    String imgHref = data.optString("img_href");
                                    view.createReport(code, message, imgHref);
                                } else {
                                    view.createReport(-1, message, null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

}
