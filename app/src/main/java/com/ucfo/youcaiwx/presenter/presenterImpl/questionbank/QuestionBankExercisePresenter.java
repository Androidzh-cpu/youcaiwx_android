package com.ucfo.youcaiwx.presenter.presenterImpl.questionbank;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.DoProblemsBean;
import com.ucfo.youcaiwx.entity.questionbank.ErrorCenterSubmitAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.QuestionDeleteBean;
import com.ucfo.youcaiwx.entity.questionbank.SixPlateSubmitAnswerBean;
import com.ucfo.youcaiwx.entity.questionbank.SubmitStatusResultBean;
import com.ucfo.youcaiwx.presenter.view.questionbank.IQuestionBankDoExerciseView;
import com.ucfo.youcaiwx.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-5-16.  下午 4:57
 * FileName: QuestionBankExercisePresenter
 * Description:TODO  题库做题业务
 * Detail:TODO 为方便后续教研部又频繁乱改需求,6大板块获取题目接口一样,但是还是分开写
 */
public class QuestionBankExercisePresenter implements IQuestionBankExercisePresenter {
    private IQuestionBankDoExerciseView view;
    private String EXERCISE_TYPE;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setEXERCISE_TYPE(String EXERCISE_TYPE) {
        this.EXERCISE_TYPE = EXERCISE_TYPE;
    }

    public QuestionBankExercisePresenter(IQuestionBankDoExerciseView view) {
        this.view = view;
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-20 下午 5:33
     * Detail:TODO 组卷模考获取题目
     */
    @Override
    public void getGroupExamProblemsData(int course_id, int plate_id, int paper_type, int mock_id, int user_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.MOCK_ID, mock_id)//组卷ID
                .params(Constant.USER_ID, user_id)//用户
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-22 上午 10:12
     * Detail:TODO 阶段测试获取题目
     */
    @Override
    public void getStageOfTesting(int course_id, int user_id, int plate_id, int paper_type, int paper_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.USER_ID, user_id)//用户
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-22 上午 11:55
     * Detail:TODO 知识点练习
     */
    @Override
    public void getKnowledgePractice(int course_id, int user_id, int plate_id, int paper_type, int section_id, int knob_id, String know_id, int num) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.SECTION_ID, section_id)//章节
                .params(Constant.KNOW_ID, know_id)//知识点
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.NUM, num)//做题数
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:IQuestionBankExercisePresenter
     * Time:2019-5-22 下午 5:46
     * Detail:TODO 系统高频错题
     */
    @Override
    public void getQuestionHightErrors(int course_id, int user_id, int plate_id, int paper_type, int section_id, String know_id, String knob_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.KNOW_ID, know_id)//知识点
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-22 下午 5:51
     * Detail:TODO 自主练习
     */
    @Override
    public void getSelfHelpPractice(int course_id, int user_id, int plate_id, int paper_type, int section_id, String know_id, String knob_id, int num) {
        String know = know_id.replaceAll(" ", "");
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.KNOW_ID, know)//多个知识点  [1,2,3]
                .params(Constant.NUM, num)//做题数
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:IQuestionBankExercisePresenter
     * Time:2019-5-22 下午 5:46
     * Detail:TODO 论述题自测
     */
    @Override
    public void getDissCussData(int course_id, int user_id, int plate_id, int paper_type, int paper_id) {
        OkGo.<String>post(ApiStores.QUESTION_GETPROBLEMSLIS)
                .tag(this)
                .params(Constant.COURSE_ID, course_id)//题库ID
                .params(Constant.PLATE_ID, plate_id)//对应板块id
                .params(Constant.PAPER_TYPE, paper_type)//题目类型
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.USER_ID, user_id)//用户
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-27 下午 1:42
     * Detail:TODO 错题中心: 错题解析和全部解析
     */
    @Override
    public void getErrorCenterAnalysis(int user_id, int course_id, int section_id, String know_id, int type, String question_content) {
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_ANALYSIS)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOW_ID, know_id)
                .params(Constant.TYPE, type)
                .params(Constant.QUESTION_CONTENT, question_content)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-27 下午 1:42
     * Detail:TODO 错题中心查看解析
     */
    @Override
    public void getErrorCenterCheckAnalysis(int course_id, int user_id, int section_id, String know_id) {
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_CHECKANALYSIS)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOW_ID, know_id)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:IQuestionBankExercisePresenter
     * Time:2019-5-28 上午 10:51
     * Detail:TODO 错题中心 重新做题
     */
    @Override
    public void getErrorCenterReform(int course_id, int user_id, int section_id, String knob_id, String know_id) {
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_REFORM)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOW_ID, know_id)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-27 下午 1:42
     * Detail:TODO 错题解析,分别是错题解析和全部解析
     */
    @Override
    public void getErrorAnalysis(int paper_id, int user_id, int type) {
        OkGo.<String>post(ApiStores.QUESTION_GET_AnalysisPROBLEMSLIS)
                .tag(this)
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.TYPE, type)// 1:错题解析2:全部解析
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-23 下午 2:17
     * Detail:TODO 交卷.保存
     */
    @Override
    public void submitPapers(int course_id, int user_id, int plate_id, int status, int section_id,
                             int knob_id, String know_id, int paper_id, int mock_id, int used_time, int all_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList) {
        SixPlateSubmitAnswerBean submitAnswerBean = new SixPlateSubmitAnswerBean();
        submitAnswerBean.setPaper_id(paper_id);//试卷
        submitAnswerBean.setKnob_id(knob_id);//节
        submitAnswerBean.setKnow_id(know_id);//知识点
        submitAnswerBean.setMock_id(mock_id);//组卷
        ArrayList<SixPlateSubmitAnswerBean.QuestionBean> questionBeanList = new ArrayList<>();//题目信息
        for (int i = 0; i < optionsAnswerList.size(); i++) {
            SixPlateSubmitAnswerBean.QuestionBean bean = new SixPlateSubmitAnswerBean.QuestionBean();

            bean.setQuestion_id(optionsAnswerList.get(i).getQuestion_id());
            bean.setTrue_options(optionsAnswerList.get(i).getTrue_options());
            bean.setUser_answer(optionsAnswerList.get(i).getUser_answer());

            questionBeanList.add(bean);
        }
        submitAnswerBean.setQuestion(questionBeanList);

        String json = new Gson().toJson(submitAnswerBean);
        LogUtils.d("submit---json:" + json);

        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //TODO attention:此处paper_type不再是获取题目时选择题和论述题了,变为1是知识点练习模式,2是正常考试模式
        int paper_type;//做题模式1练习模式2考试模式(除了知识点全是考试模式)
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_P)) {
            paper_type = 1;
        } else {
            paper_type = 2;
        }
        OkGo.<String>post(ApiStores.QUESTION_SUBMITPAPERS)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.PLATE_ID, plate_id)//板块
                .params(Constant.PAPER_TYPE, paper_type)//试卷类型
                .params(Constant.STATUS, status)//交卷状态
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.KNOW_ID, know_id)//知识点
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.MOCK_ID, mock_id)//组卷ID
                .params(Constant.USED_TIME, used_time)//做题时长
                .params("all_time", all_time)
                .params(Constant.QUESTION_CONTENT, questionContent)//做题信息
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.submitStatus(null);
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
                                    SubmitStatusResultBean submitStatusResultBean = gson.fromJson(body, SubmitStatusResultBean.class);
                                    view.submitStatus(submitStatusResultBean);
                                } else {
                                    view.submitStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.submitStatus(null);
                        }
                    }
                });
    }

    //TODO 学习中心交卷
    @Override
    public void learnCenterSubmitPapers(int course_id, int user_id, int status, int used_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList, String videoName, String know_id) {
        SixPlateSubmitAnswerBean submitAnswerBean = new SixPlateSubmitAnswerBean();
        submitAnswerBean.setPaper_id(0);//试卷
        submitAnswerBean.setKnob_id(0);//节
        submitAnswerBean.setKnow_id(know_id);//知识点
        submitAnswerBean.setMock_id(0);//组卷
        ArrayList<SixPlateSubmitAnswerBean.QuestionBean> questionBeanList = new ArrayList<>();//题目信息
        for (int i = 0; i < optionsAnswerList.size(); i++) {
            SixPlateSubmitAnswerBean.QuestionBean bean = new SixPlateSubmitAnswerBean.QuestionBean();

            bean.setQuestion_id(optionsAnswerList.get(i).getQuestion_id());
            bean.setTrue_options(optionsAnswerList.get(i).getTrue_options());
            bean.setUser_answer(optionsAnswerList.get(i).getUser_answer());

            questionBeanList.add(bean);
        }
        submitAnswerBean.setQuestion(questionBeanList);

        String json = new Gson().toJson(submitAnswerBean);
        LogUtils.e("交卷json: " + json);
        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.LEARNCENTER_LEARNPLANSUBMIT)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.STATUS, status)//交卷状态
                .params(Constant.USED_TIME, used_time)//做题时长
                .params(Constant.QUESTION_CONTENT, questionContent)//做题信息
                .params(Constant.VIDEO_NAME, videoName)//做题信息
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.submitStatus(null);
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
                                    SubmitStatusResultBean submitStatusResultBean = gson.fromJson(body, SubmitStatusResultBean.class);
                                    view.submitStatus(submitStatusResultBean);
                                } else {
                                    view.submitStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.submitStatus(null);
                        }
                    }
                });

    }

    //TODO 答题记录交卷
    @Override
    public void contimueSubmitPapers(int course_id, int id, int user_id, int plate_id, int status, int section_id, int knob_id, String know_id, int paper_id, int mock_id, int used_time, int all_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList) {
        SixPlateSubmitAnswerBean submitAnswerBean = new SixPlateSubmitAnswerBean();
        submitAnswerBean.setPaper_id(paper_id);//试卷
        submitAnswerBean.setKnob_id(knob_id);//节
        submitAnswerBean.setKnow_id(know_id);//知识点
        submitAnswerBean.setMock_id(mock_id);//组卷
        ArrayList<SixPlateSubmitAnswerBean.QuestionBean> questionBeanList = new ArrayList<>();//题目信息
        for (int i = 0; i < optionsAnswerList.size(); i++) {
            SixPlateSubmitAnswerBean.QuestionBean bean = new SixPlateSubmitAnswerBean.QuestionBean();

            bean.setQuestion_id(optionsAnswerList.get(i).getQuestion_id());
            bean.setTrue_options(optionsAnswerList.get(i).getTrue_options());
            bean.setUser_answer(optionsAnswerList.get(i).getUser_answer());

            questionBeanList.add(bean);
        }
        submitAnswerBean.setQuestion(questionBeanList);

        String json = new Gson().toJson(submitAnswerBean);

        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //TODO attention:此处paper_type不再是获取题目时选择题和论述题了,变为1是知识点练习模式,2是正常考试模式
        int paper_type;//做题模式1练习模式2考试模式(除了知识点全是考试模式)
        if (TextUtils.equals(EXERCISE_TYPE, Constant.EXERCISE_P)) {
            paper_type = 1;
        } else {
            paper_type = 2;
        }
        OkGo.<String>post(ApiStores.QUESTION_CONTINUE_SUBMITPAPERS)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.ID, id)
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.PLATE_ID, plate_id)//板块
                .params(Constant.PAPER_TYPE, paper_type)//试卷类型
                .params(Constant.STATUS, status)//交卷状态
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.KNOW_ID, know_id)//知识点
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.MOCK_ID, mock_id)//组卷ID
                .params(Constant.USED_TIME, used_time)//做题时长
                .params("all_time", all_time)
                .params(Constant.QUESTION_CONTENT, questionContent)//做题信息
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.submitStatus(null);
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
                                    SubmitStatusResultBean submitStatusResultBean = gson.fromJson(body, SubmitStatusResultBean.class);
                                    view.submitStatus(submitStatusResultBean);
                                } else {
                                    view.submitStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.submitStatus(null);
                        }
                    }
                });

    }
/*
    @Override
    public void submitPapers(int course_id, int user_id, int plate_id, int status, int section_id,
                             int knob_id, int know_id, int paper_id, int mock_id, int used_time,int all_time, ArrayList<DoProblemsAnswerBean> optionsAnswerList) {
        ArrayList<ErrorCenterSubmitAnswerBean> answerList = new ArrayList<>();
        int paper_type;//做题模式1练习模式2考试模式(除了知识点全是考试模式)
        for (int i = 0; i < optionsAnswerList.size(); i++) {
            DoProblemsAnswerBean bean = optionsAnswerList.get(i);
            String question_id = bean.getQuestion_id();
            String true_options = bean.getTrue_options();
            String user_answer = bean.getUser_answer();
            answerList.add(new ErrorCenterSubmitAnswerBean(question_id, true_options, user_answer));
        }
        String json = new Gson().toJson(answerList);
        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //TODO 神逻辑,attention:此处paper_type不再是获取题目时选择题和论述题了,变为1是知识点练习模式,2是正常考试模式
        if (EXERCISE_TYPE.equals(Constant.EXERCISE_P)) {
            paper_type = 1;
        } else {
            paper_type = 2;
        }

        OkGo.<String>post(ApiStores.QUESTION_SUBMITPAPERS)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.PLATE_ID, plate_id)//板块
                .params(Constant.PAPER_TYPE, paper_type)//试卷类型
                .params(Constant.STATUS, status)//交卷状态
                .params(Constant.SECTION_ID, section_id)//章
                .params(Constant.KNOB_ID, knob_id)//节
                .params(Constant.KNOW_ID, know_id)//知识点
                .params(Constant.PAPER_ID, paper_id)//试卷ID
                .params(Constant.MOCK_ID, mock_id)//组卷ID
                .params(Constant.USED_TIME, used_time)//做题时长
                .params("all_time", all_time)
                .params(Constant.QUESTION_CONTENT, questionContent)//做题信息
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.submitStatus(null);
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
                                    SubmitStatusResultBean submitStatus = gson.fromJson(body, SubmitStatusResultBean.class);
                                    view.submitStatus(submitStatus);
                                } else {
                                    view.submitStatus(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.submitStatus(null);
                        }
                    }
                });

    }
*/

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-28 上午 11:52
     * Detail:TODO 错题中心交卷
     */
    @Override
    public void errorCenterSubmit(ArrayList<DoProblemsAnswerBean> optionsAnswerList, int user_id, int course_id) {
        ArrayList<ErrorCenterSubmitAnswerBean> answerList = new ArrayList<>();
        for (int i = 0; i < optionsAnswerList.size(); i++) {
            DoProblemsAnswerBean bean = optionsAnswerList.get(i);
            String question_id = bean.getQuestion_id();
            String true_options = bean.getTrue_options();
            String user_answer = bean.getUser_answer();
            answerList.add(new ErrorCenterSubmitAnswerBean(question_id, true_options, user_answer));
        }
        String json = new Gson().toJson(answerList);
        LogUtils.e("ErrorCenterSubmitAnswerBean: " + json);
        String questionContent = "";
        try {
            String str = new String(json.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_SUBMITPAPERS)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.QUESTION_CONTENT, questionContent)//做题信息
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.errorCenterSubmitStatus(0);
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
                                    view.errorCenterSubmitStatus(1);
                                } else {
                                    view.errorCenterSubmitStatus(0);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.errorCenterSubmitStatus(0);
                        }
                    }
                });

    }

    /**
     * Description:IQuestionBankExercisePresenter
     * Time:2019-5-27 上午 10:03
     * Detail:TODO 题目收藏
     */
    @Override
    public void setProblemsCollection(int user_id, int course_id, int question_id, int type) {
        int fianltype = 0;//1收藏操作 2取消收藏操作
        switch (type) {
            case 0://未收藏
                fianltype = 1;
                break;
            case 1://已收藏
                fianltype = 2;
                break;
            default:
                fianltype = 0;
                break;
        }
        OkGo.<String>post(ApiStores.QUESTION_SETPROBLEMSCollection)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.QUESTION_ID, question_id)//题目ID
                .params(Constant.TYPE, fianltype)//操作类型
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.collectionResult(2);
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
                                    int data = jsonObject.optInt("data");
                                    view.collectionResult(data);
                                } else {
                                    view.collectionResult(2);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.collectionResult(2);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-29 上午 10:02
     * Detail:TODO 删除题目
     */
    @Override
    public void deleteProblems(int course_id, int user_id, int question_id) {
        OkGo.<String>post(ApiStores.QUESTION_ERRORCENTER_DELETE)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户ID
                .params(Constant.COURSE_ID, course_id)//专业
                .params(Constant.QUESTION_ID, question_id)//题目ID
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.deleteResult(null);
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
                                    QuestionDeleteBean bean = gson.fromJson(body, QuestionDeleteBean.class);
                                    view.deleteResult(bean);
                                } else {
                                    view.deleteResult(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.deleteResult(null);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-5-29 下午 4:04
     * Detail:TODO 继续做题
     */
    @Override
    public void getContinueExamData(int user_id, int id) {
        OkGo.<String>post(ApiStores.QUESTION_GET_CONTIUEEXAM)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.UPPER_ID, id)//试卷
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    //TODO 答题记录查看论述题试题
    @Override
    public void getContinueDiscussAnalysis(int user_id, int paper_id) {
        OkGo.<String>post(ApiStores.QUESTION_GET_DISCUSS_LOOKEXAM)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.PAPER_ID, paper_id)//试卷ID
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });

    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-6-4 下午 3:16
     * Detail:TODO 0元体验获取试题
     */
    @Override
    public void getFreeExperinece(int user_id) {
        OkGo.<String>post(ApiStores.QUESTION_FREE_EXPERIENCE)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-6-4 下午 3:17
     * Detail:TODO 0元体验获取解析
     */
    @Override
    public void getFreeExperineceAnalysis(int user_id, int type, String question_content) {
        OkGo.<String>post(ApiStores.QUESTION_FREE_EXPERIENCE_ANALYSIS)
                .tag(this)
                .params(Constant.USER_ID, user_id)//用户
                .params(Constant.TYPE, type)// 1:错题解析2:全部解析
                .params(Constant.QUESTION_CONTENT, question_content)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    /**
     * Description:QuestionBankExercisePresenter
     * Time:2019-6-4 下午 3:17
     * Detail:TODO 我的收藏查看试题
     */
    @Override
    public void getMineCollectionList(int user_id, int course_id, int section_id, int knob_id, String know_id) {
        OkGo.<String>post(ApiStores.MINE_COLLECTION_QUESTIONLOOK)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.KNOB_ID, knob_id)
                .params(Constant.KNOW_ID, know_id)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    //TODO 学习中心获取试题
    @Override
    public void getLearnPlanExerciseList(int user_id, String know_id, String video_name) {
        OkGo.<String>post(ApiStores.LEARNCENTER_LEARNPLANTOPIC)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.KNOW_ID, know_id)
                .params(Constant.VIDEO_NAME, video_name)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    //TODO 学习中心获取解析
    @Override
    public void getLearnPlanExerciseAnalysis(int user_id, int paper_id) {
        OkGo.<String>post(ApiStores.LEARNCENTER_LEARNPLANANALYSIS)
                .params(Constant.USER_ID, user_id)
                .params(Constant.PAPER_ID, paper_id)
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
                                    DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                    view.getProblemsList(doProblemsBean);
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }

    /**
     * 我的答疑(查看试题)
     */
    @Override
    public void getQuestionDetailed(int user_id, String question_id) {
        OkGo.<String>post(ApiStores.QUESTION_QUESTIONDETAILED)
                .params(Constant.USER_ID, user_id)
                .params(Constant.QUESTION_ID, question_id)
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
                                    if (jsonObject.has(Constant.DATA)) {
                                        DoProblemsBean doProblemsBean = new Gson().fromJson(body, DoProblemsBean.class);
                                        DoProblemsBean.DataBean data = doProblemsBean.getData();
                                        if (context != null) {
                                            data.setTitle(context.getResources().getString(R.string.question_title_analysis));
                                        }
                                        view.getProblemsList(doProblemsBean);
                                    } else {
                                        view.getProblemsList(null);
                                    }
                                } else {
                                    view.getProblemsList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getProblemsList(null);
                        }
                    }
                });
    }
}
