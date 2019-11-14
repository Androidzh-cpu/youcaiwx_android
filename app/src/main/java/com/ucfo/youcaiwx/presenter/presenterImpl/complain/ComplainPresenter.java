package com.ucfo.youcaiwx.presenter.presenterImpl.complain;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.complain.ComplainTypeBean;
import com.ucfo.youcaiwx.presenter.view.complain.IComplainView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-11-13.  下午 4:21
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.complain
 * FileName: ComplainPresenter
 * Description:TODO 投诉业务
 */
public class ComplainPresenter implements IComplainPresenter {
    private IComplainView view;

    public ComplainPresenter(IComplainView view) {
        this.view = view;
    }

    @Override
    public void initComplainType() {
        OkGo.<String>post(ApiStores.COMPLAIN_TYPE)
                .execute(new StringCallback() {
                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.initComplainType(null);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    if (jsonObject.has(Constant.DATA)) {
                                        Object data = jsonObject.get(Constant.DATA);
                                        if (data instanceof JSONArray) {
                                            Gson gson = new Gson();
                                            ComplainTypeBean complainTypeBean = gson.fromJson(body, ComplainTypeBean.class);
                                            view.initComplainType(complainTypeBean);
                                        } else {
                                            view.initComplainType(null);
                                        }
                                    } else {
                                        view.initComplainType(null);
                                    }
                                } else {
                                    view.initComplainType(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initComplainType(null);
                        }
                    }
                });

    }

    @Override
    public void initComplain(String type, String answer_id, String complainid, String useid, String content) {
        OkGo.<String>post(ApiStores.COMPLAIN_SUBMIT)
                .params(Constant.USER_ID, useid)
                .params(Constant.ANSWER_TYPE, type)
                .params(Constant.ANSWER_ID, answer_id)
                .params(Constant.CONTENT, content)
                .params("complain_id", complainid)
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
                        view.complainResult(-1);
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.showLoadingFinish();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    view.complainResult(1);
                                } else {
                                    view.complainResult(-1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.complainResult(-1);
                        }
                    }
                });

    }
}
