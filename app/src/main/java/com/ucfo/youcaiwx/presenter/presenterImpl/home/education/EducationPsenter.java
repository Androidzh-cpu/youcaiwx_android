package com.ucfo.youcaiwx.presenter.presenterImpl.home.education;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.education.EducationCourseListBean;
import com.ucfo.youcaiwx.entity.home.education.EducationTypeBean;
import com.ucfo.youcaiwx.presenter.view.home.education.IEducationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-12-24.  上午 9:55
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.home.education
 * FileName: EducationPsenter
 * Description:TODO 后续教育啊
 */
public class EducationPsenter implements IEducationPsenter {
    private IEducationView view;

    public EducationPsenter(IEducationView view) {
        this.view = view;
    }

    @Override
    public void initClassification() {
        OkGo.<String>post(ApiStores.EDUCATION_CLASSIFICATION)
                .tag(this)
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
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    if (jsonObject.has(Constant.DATA)) {
                                        EducationTypeBean educationTypeBean = new Gson().fromJson(body, EducationTypeBean.class);
                                        view.initClassification(educationTypeBean);
                                    } else {
                                        view.initClassification(null);
                                    }
                                } else {
                                    view.initClassification(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initClassification(null);
                        }
                    }
                });

    }

    @Override
    public void initCourseListData(String type_id, String sort, String screening, int limit, int page) {
        OkGo.<String>post(ApiStores.EDUCATION_COURSE_LIST)
                .tag(this)
                .params(Constant.TYPE_ID, type_id)
                .params("sort", sort)
                .params("screening", screening)
                .params(Constant.LIMIT, limit)
                .params(Constant.PAGE, page)
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
                        if (body != null) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    //判断最外层是否有data对象
                                    if (jsonObject.has(Constant.DATA)) {
                                        //获取最外层data对象
                                        JSONObject jsonObjectData = jsonObject.optJSONObject(Constant.DATA);
                                        //获取里面的data
                                        Object data = jsonObjectData.get(Constant.DATA);
                                        //判断类型
                                        if (data instanceof JSONArray) {
                                            EducationCourseListBean educationCourseListBean = new Gson().fromJson(body, EducationCourseListBean.class);
                                            view.initCourseList(educationCourseListBean);
                                        } else {
                                            view.initCourseList(null);
                                        }
                                    } else {
                                        view.initCourseList(null);
                                    }
                                } else {
                                    view.initCourseList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.initCourseList(null);
                        }
                    }
                });

    }
}
