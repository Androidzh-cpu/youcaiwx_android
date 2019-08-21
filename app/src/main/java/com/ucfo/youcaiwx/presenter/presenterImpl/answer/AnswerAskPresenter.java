package com.ucfo.youcaiwx.presenter.presenterImpl.answer;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.answer.AnswerKnowListBean;
import com.ucfo.youcaiwx.presenter.view.answer.IAnswerAskQuestionView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Author:29117
 * Time: 2019-4-19.  上午 9:11
 * Email:2911743255@qq.com
 * ClassName: AnswerAskPresenter
 * Description:TODO 课程答疑提问问题
 */
public class AnswerAskPresenter {
    private IAnswerAskQuestionView view;

    public AnswerAskPresenter(IAnswerAskQuestionView view) {
        this.view = view;
    }

    /**
     * Description:AnswerAskPresenter
     * Time:2019-4-19   上午 9:21
     * Detail: 提问问题
     */
    public void userAskQuestion(int user_id, int video_id, int section_id, int course_id, int package_id, String quiz, int video_time, ArrayList<String> imageList) {
        String questionImage = "";
        if (imageList != null && imageList.size() > 0) {//有图片切不止一张
            for (String item : imageList) {
                // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                questionImage += item + ",";
            }
            // 去掉最后一个逗号
            questionImage = questionImage.substring(0, questionImage.length() - 1);
        }
        OkGo.<String>post(ApiStores.ANSWER_ASKQUESTION)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.VIDEO_ID, video_id)
                .params(Constant.SECTION_ID, section_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.PACKAGE_ID, package_id)
                .params("quiz", quiz.trim())
                .params(Constant.VIDEO_TIME, video_time)
                .params("quiz_image", questionImage.trim())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.askQuestionResult(-1);
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
                            try {
                                JSONObject object = new JSONObject(response.body());
                                int code = object.optInt(Constant.CODE);//状态码
                                if (code == 200) {
                                    view.askQuestionResult(code);
                                } else {
                                    view.askQuestionResult(-1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.askQuestionResult(-1);
                        }
                    }
                });

    }

    /**
     * Description:AnswerAskPresenter
     * Time:2019-6-3 上午 10:47
     * Detail:TODO 题库答疑提问问题
     */
    public void qustionAskQuestion(int user_id, int course_id, int question_id, String content, ArrayList<String> imageList) {
        String questionImage = "";
        if (imageList != null && imageList.size() > 0) {//有图片切不止一张
            for (String item : imageList) {
                // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                questionImage += item + ",";
            }
            // 去掉最后一个逗号
            questionImage = questionImage.substring(0, questionImage.length() - 1);
        }
        String questionContent = null;
        try {
            String str = new String(content.getBytes(), Constant.UTF_8);
            questionContent = URLEncoder.encode(str, Constant.UTF_8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OkGo.<String>post(ApiStores.ANSWERQUESTION_ANSWER_SUBMIT)
                .tag(this)
                .params(Constant.USER_ID, user_id)
                .params(Constant.COURSE_ID, course_id)
                .params(Constant.QUESTION_ID, question_id)
                .params("quiz", questionContent)
                .params("quiz_image", questionImage.trim())
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.showLoading();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.askQuestionResult(-1);
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
                            try {
                                JSONObject object = new JSONObject(body);
                                int code = object.optInt(Constant.CODE);//状态码
                                if (code == 200) {
                                    view.askQuestionResult(code);
                                } else {
                                    view.askQuestionResult(-1);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.askQuestionResult(-1);
                        }
                    }
                });

    }

    /**
     * Description:AnswerAskPresenter
     * Time:2019-6-3 下午 3:13
     * Detail:TODO 获取答疑知识点
     */
    public void getKnowList(int question_id) {
        OkGo.<String>post(ApiStores.ANSWERQUESTION_ANSWER_KNOWLIST)
                .tag(this)
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
                        view.getKnowList(null);
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
                            try {
                                JSONObject object = new JSONObject(response.body());
                                int code = object.optInt(Constant.CODE);//状态码
                                if (code == 200) {
                                    AnswerKnowListBean bean = new Gson().fromJson(body, AnswerKnowListBean.class);
                                    view.getKnowList(bean);
                                } else {
                                    view.getKnowList(null);
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            view.getKnowList(null);
                        }
                    }
                });

    }


    /*public static void main(String[] args) {
        ArrayList<String> imageList = new ArrayList<String>();
        imageList.add("https://blog.csdn.net/opengl_es/article/details/42519579");
        imageList.add("https://blog.csdn.net/opengl_es/article/details/42519579");
        imageList.add("https://blog.csdn.net/opengl_es/article/details/42519579");
        imageList.add("https://blog.csdn.net/opengl_es/article/details/42519579");
        String result = "";
        if (imageList != null && imageList.size() > 0) {
            for (String item : imageList) {
                // 把列表中的每条数据用逗号分割开来，然后拼接成字符串
                result += item + ",";
            }
            // 去掉最后一个逗号
            result = result.substring(0, result.length() - 1);
        }
        int fileUploadFlag = 0;
        for (int i = 0; i < 3; i++) {
            fileUploadFlag++;
        }
        System.out.print("图片地址集合:" + fileUploadFlag);
    }*/

}
