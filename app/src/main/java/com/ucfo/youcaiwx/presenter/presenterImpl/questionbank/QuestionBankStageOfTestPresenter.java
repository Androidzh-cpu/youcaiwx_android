package com.ucfo.youcaiwx.presenter.presenterImpl.questionbank;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionStageOfTestBean;
import com.ucfo.youcaiwx.entity.questionbank.TipsBean;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankStageView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-5-6.  上午 10:33
 * FileName: QuestionBankStageOfTestPresenter
 * Description:TODO  阶段测试,论述题自测,组卷模考业务
 * Detail:TODO  阶段测试和组卷模考公用一个接口
 */
public class QuestionBankStageOfTestPresenter {
    private IQuestionBankStageView view;

    public QuestionBankStageOfTestPresenter(IQuestionBankStageView view) {
        this.view = view;
    }

    /**
     * 获取提示框信息
     *
     * @param plateid
     */
    public void getDialogTips(int plateid) {
        OkGo.<String>post(ApiStores.QUESTION_GETTRAININGCAMP_TIPS)
                .tag(this)
                .params(Constant.PLATE_ID, plateid)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.getDialogTips(null);
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
                                    TipsBean tipsBean = new Gson().fromJson(body, TipsBean.class);
                                    view.getDialogTips(tipsBean);
                                } else {
                                    view.getDialogTips(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getDialogTips(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankStageOfTestPresenter
     * Time:2019-5-6 上午 10:35
     * Detail:TODO 获取阶段测试,论述题自测,组卷模考
     */
    public void getStageOfTestData(int course_id, int plate_id) {
        if (plate_id == Constant.PLATE_6) {
            //TODO 组卷模考
            OkGo.<String>post(ApiStores.QUESTION_GETGROUPEXAMLIST)
                    .tag(this)
                    .params(Constant.COURSE_ID, course_id)
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
                            if (!body.equals("") && body != null) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(body);
                                    int code = jsonObject.optInt("code");
                                    if (code == 200) {
                                        QuestionStageOfTestBean questionStageOfTestBean = new Gson().fromJson(body, QuestionStageOfTestBean.class);
                                        view.getStageOfTestData(questionStageOfTestBean);
                                    } else {
                                        view.getStageOfTestData(null);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                view.getStageOfTestData(null);
                            }
                        }
                    });
        } else if (plate_id == Constant.PLATE_7) {
            //TODO 冲刺训练营
            OkGo.<String>post(ApiStores.QUESTION_GETTRAININGCAMP_LIST)
                    .tag(this)
                    .params(Constant.COURSE_ID, course_id)
                    .params(Constant.PLATE_ID, plate_id)
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
                            if (!body.equals("") && body != null) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(body);
                                    int code = jsonObject.optInt(Constant.CODE);
                                    if (code == 200) {
                                        QuestionStageOfTestBean questionStageOfTestBean = new Gson().fromJson(body, QuestionStageOfTestBean.class);
                                        view.getStageOfTestData(questionStageOfTestBean);
                                    } else {
                                        view.getStageOfTestData(null);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                view.getStageOfTestData(null);
                            }
                        }
                    });

        } else {
            //TODO 默认板块
            OkGo.<String>post(ApiStores.QUESTION_GETSTAGEOFTESTLIST)
                    .tag(this)
                    .params(Constant.COURSE_ID, course_id)
                    .params(Constant.PLATE_ID, plate_id)
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
                            if (!body.equals("") && body != null) {
                                JSONObject jsonObject = null;
                                try {
                                    jsonObject = new JSONObject(body);
                                    int code = jsonObject.optInt("code");
                                    if (code == 200) {
                                        QuestionStageOfTestBean questionStageOfTestBean = new Gson().fromJson(body, QuestionStageOfTestBean.class);
                                        view.getStageOfTestData(questionStageOfTestBean);
                                    } else {
                                        view.getStageOfTestData(null);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                view.getStageOfTestData(null);
                            }
                        }
                    });
        }
    }
}
