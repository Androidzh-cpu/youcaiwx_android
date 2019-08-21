package com.ucfo.youcaiwx.presenter.presenterImpl.course;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseDataListBean;
import com.ucfo.youcaiwx.entity.course.CourseSubjectsBean;
import com.ucfo.youcaiwx.presenter.view.course.ICourseListView;

/**
 * Author:29117
 * Time: 2019-4-1.  上午 9:43
 * Email:2911743255@qq.com
 * ClassName: CourseListPresenter
 * Description:课程列表业务
 */
public class CourseListPresenter implements ICourseListPresenter {
    private ICourseListView view;

    public CourseListPresenter(ICourseListView view) {
        this.view = view;
    }

    //获取课程分类列表
    @Override
    public void getCourseSubjects() {
        OkGo.<String>post(ApiStores.COURSE_SUBJECTS)
                .tag(this)
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
                        if (body != null && !body.equals("")) {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                CourseSubjectsBean courseSubjectsBean = gson.fromJson(body, CourseSubjectsBean.class);
                                view.getCourseSubject(courseSubjectsBean);
                            } else {
                                view.getCourseSubject(null);
                            }
                        } else {
                            view.getCourseSubject(null);
                        }
                    }
                });
    }

    @Override
    public void getCourseDataList(int id, int user_id) {
        OkGo.<String>post(ApiStores.COURSE_COURSELIST)
                .tag(this)
                .params(Constant.CLASS_ID, id)
                .params(Constant.USER_ID, user_id)
                .retryCount(1)
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
                        if (body != null && !body.equals("")) {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                CourseDataListBean courseDataListBean = gson.fromJson(body, CourseDataListBean.class);
                                view.getCourseDataList(courseDataListBean);
                            } else {
                                view.getCourseDataList(null);
                            }
                        } else {
                            view.getCourseDataList(null);
                        }
                    }
                });

    }
}
