package com.ucfo.youcaiwx.presenter.presenterImpl.user;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.MineCourseBean;
import com.ucfo.youcaiwx.entity.user.MineCourseChildListBean;
import com.ucfo.youcaiwx.entity.user.MineCourseCollectionDirBean;
import com.ucfo.youcaiwx.entity.user.MineQuestionCollectionListBean;
import com.ucfo.youcaiwx.entity.user.ProjectListBean;
import com.ucfo.youcaiwx.presenter.view.user.IMineCollectionView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-6-18.  下午 5:56
 * FileName: MineCollectionPresenter
 * Description:TODO 收藏业务P城
 */
public class MineCollectionPresenter implements IMineCollectionPresenter {
    private IMineCollectionView view;

    public MineCollectionPresenter(IMineCollectionView view) {
        this.view = view;
    }

    //TODO 获取我的课程收藏列表
    @Override
    public void getMineCoursePackageCollectionList(int user_id, int page) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_COURSELIST)
                .params(Constant.USER_ID, user_id)
                .params(Constant.LIMIT, 10)
                .params(Constant.PAGE, page)
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
                                    MineCourseBean mineCourseBean = gson.fromJson(body, MineCourseBean.class);
                                    view.getMineCoursePackageCollectionList(mineCourseBean);
                                } else {
                                    view.getMineCoursePackageCollectionList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCoursePackageCollectionList(null);
                        }
                    }
                });

    }

    //TODO 收藏课程列表
    @Override
    public void getMineCourseCollectionList(int user_id, int package_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_COURSECHILDLIST)
                .params(Constant.USER_ID, user_id)
                .params(Constant.PACKAGE_ID, package_id)
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
                                    MineCourseChildListBean mineCourseBean = gson.fromJson(body, MineCourseChildListBean.class);
                                    view.getMineCourseCollectionChildList(mineCourseBean);
                                } else {
                                    view.getMineCourseCollectionChildList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineCourseCollectionChildList(null);
                        }
                    }
                });

    }

    @Override
    public void getCollectionDirList(int user_id, int package_id, int course_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_COURSEDIRLIST)
                .params(Constant.USER_ID, user_id)
                .params(Constant.PACKAGE_ID, package_id)
                .params(Constant.COURSE_ID, course_id)
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
                                    MineCourseCollectionDirBean bean = gson.fromJson(body, MineCourseCollectionDirBean.class);
                                    view.getCollectionDirList(bean);
                                } else {
                                    view.getCollectionDirList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getCollectionDirList(null);
                        }
                    }
                });

    }

    @Override
    public void getProjectList(int user_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_PROGECTLIST)
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
                                    ProjectListBean mineCourseBean = gson.fromJson(body, ProjectListBean.class);
                                    view.getPorjectList(mineCourseBean);
                                } else {
                                    view.getPorjectList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getPorjectList(null);
                        }
                    }
                });

    }

    //TODO 题库收藏二级列表
    @Override
    public void getMineQuestioinCollectionList(int user_id, int course_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_QUESTIONLIST)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
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
                                    MineQuestionCollectionListBean listBean = gson.fromJson(body, MineQuestionCollectionListBean.class);
                                    view.getMineQuestioinCollectionList(listBean);
                                } else {
                                    view.getMineQuestioinCollectionList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getMineQuestioinCollectionList(null);
                        }
                    }
                });
    }
}
