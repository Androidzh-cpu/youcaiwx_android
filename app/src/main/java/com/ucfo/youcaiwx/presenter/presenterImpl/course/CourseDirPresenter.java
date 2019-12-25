package com.ucfo.youcaiwx.presenter.presenterImpl.course;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.course.CourseDirBean;
import com.ucfo.youcaiwx.presenter.view.course.ICourseDirView;

/**
 * Author:29117
 * Time: 2019-4-16.  上午 9:56
 * Email:2911743255@qq.com
 * ClassName: CourseDirPresenter
 */
public class CourseDirPresenter {
    private ICourseDirView view;

    public CourseDirPresenter(ICourseDirView view) {
        this.view = view;
    }


    /**
     * Description:CourseDirPresenter
     * Time:2019-4-16   上午 10:00
     * Detail: 获取课程目录
     */
    public void getCourseDirData(int package_id, int user_id, String course_Source) {
        String url = "";
        if (TextUtils.isEmpty(course_Source)) {
            //我的课程可能为空,下载列表也可能为空
            url = ApiStores.COURSE_GETCOURSEDIR;
        } else {
            //后续教育系列
            if (TextUtils.equals(course_Source, Constant.WATCH_EDUCATION_CPE)) {
                url = ApiStores.EDUCATION_COURSEDIRECTORY;
            } else {
                //非后续教育
                url = ApiStores.COURSE_GETCOURSEDIR;
            }
        }
        OkGo.<String>post(url)
                .tag(this)
                .params(Constant.PACKAGE_ID, package_id)
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
                        if (body != null && !body.equals("")) {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                CourseDirBean courseDirBean = gson.fromJson(body, CourseDirBean.class);
                                view.getCourseDirData(courseDirBean);
                            } else {
                                view.getCourseDirData(null);
                            }
                        } else {
                            view.getCourseDirData(null);
                        }
                    }
                });
    }

}
