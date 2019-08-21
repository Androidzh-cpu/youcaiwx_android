package com.ucfo.youcaiwx.presenter.presenterImpl.questionbank;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.QuestionBankHightErrorBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionErrorCenterBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowLedgeChildListBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionKnowledgeListBean;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankKonwledgeView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Author: AND
 * Time: 2019-5-7.  下午 2:02
 * FileName: QuestionBankKnowledgePresenter
 * Description:TODO  知识点练习,系统高频错题业务
 */
public class QuestionBankKnowledgePresenter implements IQuestionBankChapterPresenter {
    private IQuestionBankKonwledgeView view;

    public QuestionBankKnowledgePresenter(IQuestionBankKonwledgeView view) {
        this.view = view;
    }

    /**
     * Description:QuestionBankKnowledgePresenter
     * Time:2019-5-7 下午 2:02
     * Detail:TODO 获取知识点列表
     */
    @Override
    public void getKnowledgeListData(int course_id, int user_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETKnowledgePractice)
                .tag(this)
                .params(Constant.USER_ID, user_id)
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
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt("code");
                                if (code == 200) {
                                    QuestionKnowledgeListBean questionKnowledgeListBean = new Gson().fromJson(body, QuestionKnowledgeListBean.class);
                                    view.getKnowledgeList(questionKnowledgeListBean);
                                } else {
                                    view.getKnowledgeList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowledgeList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankKnowledgePresenter
     * Time:2019-5-8 下午 1:54
     * Detail:TODO 根据章节获取对应点的知识点
     */
    @Override
    public void getKnowledgeChildList(int course_id, int section_id, int knob_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETKnowledgeChildList)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOB_ID, knob_id)
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
                                    QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean = new Gson().fromJson(body, QuestionKnowLedgeChildListBean.class);
                                    view.getKnowledgeChildList(questionKnowLedgeChildListBean);
                                } else {
                                    view.getKnowledgeChildList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowledgeChildList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankKnowledgePresenter
     * Time:2019-5-9 上午 9:52
     * Detail:TODO 高频错题列表
     */
    @Override
    public void getHighFrequencyWrongTopic(int course_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETHIGHTErrorsection)
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
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    QuestionBankHightErrorBean questionBankHightErrorBean = new Gson().fromJson(body, QuestionBankHightErrorBean.class);
                                    view.getHighFrequencyWrongTopic(questionBankHightErrorBean);
                                } else {
                                    view.getHighFrequencyWrongTopic(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getHighFrequencyWrongTopic(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankKnowledgePresenter
     * Time:2019-5-10 下午 5:41
     * Detail:TODO 高频错题知识点列表
     */
    @Override
    public void getHighFrequencyWrongChildList(int course_id, int section_id, int knob_id) {
        OkGo.<String>post(ApiStores.QUESTION_HIGHTERRORKNOWLEDGE)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOB_ID, knob_id)
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
                                    QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean = new Gson().fromJson(body, QuestionKnowLedgeChildListBean.class);
                                    view.getKnowledgeChildList(questionKnowLedgeChildListBean);
                                } else {
                                    view.getKnowledgeChildList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowledgeChildList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankKnowledgePresenter
     * Time:2019-5-10 下午 5:41
     * Detail:TODO 获取错题中心二级列表
     */
    @Override
    public void getErrorCenterSectionList(int course_id, int user_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETERRORCENTER)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.USER_ID, user_id)
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
                                    QuestionErrorCenterBean bean = new Gson().fromJson(body, QuestionErrorCenterBean.class);
                                    view.getErrorCenterSectionList(bean);

                                } else {
                                    view.getErrorCenterSectionList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getErrorCenterSectionList(null);
                        }
                    }
                });

    }

    /**
     * 错题中心知识点列表
     */
    @Override
    public void getErrorCenterKnowList(int course_id, int user_id, int section_id, int knob_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETERRORCENTERChildList)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.USER_ID, user_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOB_ID, knob_id)
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
                                    QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean = new Gson().fromJson(body, QuestionKnowLedgeChildListBean.class);
                                    view.getKnowledgeChildList(questionKnowLedgeChildListBean);
                                } else {
                                    view.getKnowledgeChildList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowledgeChildList(null);
                        }
                    }
                });

    }

    /**
     * Description:IQuestionBankChapterPresenter
     * Time:2019-5-10 下午 5:39
     * Detail:TODO 题库收藏三级列表
     */
    @Override
    public void getQuestionCollectionList(int user_id, int course_id, int section_id, int knob_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_QUESTIONCHILDLIST)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOB_ID, knob_id)
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
                                    QuestionKnowLedgeChildListBean questionKnowLedgeChildListBean = new Gson().fromJson(body, QuestionKnowLedgeChildListBean.class);
                                    view.getKnowledgeChildList(questionKnowLedgeChildListBean);
                                } else {
                                    view.getKnowledgeChildList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowledgeChildList(null);
                        }
                    }
                });

    }
}
