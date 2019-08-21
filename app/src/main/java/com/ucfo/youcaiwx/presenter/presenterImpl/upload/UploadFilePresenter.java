package com.ucfo.youcaiwx.presenter.presenterImpl.upload;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.PostRequest;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.UploadFileBean;
import com.ucfo.youcaiwx.utils.LogUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Author:29117
 * Time: 2019-4-18.  下午 4:12
 * Email:2911743255@qq.com
 * ClassName: UploadFilePresenter
 * Description:TODO 上传文件,上传成功后生成一个图片链接
 */
public class UploadFilePresenter {
    private IUploadFileView view;

    public UploadFilePresenter(IUploadFileView view) {
        this.view = view;
    }

    /*public void upLoadFile(File file) {
        OkGo.<String>post(ApiStores.FILE_UPLOAD)
                .tag(this)
                .params("image", file)
                .isMultipart(true)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        view.startUploadFile();
                    }

                    @Override
                    public void onError(Response<String> response) {
                        super.onError(response);
                        view.errorUploadFile();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        view.errorUploadFile();
                    }

                    @Override
                    public void uploadProgress(Progress progress) {
                        super.uploadProgress(progress);
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (body != null && !body.equals(equals(""))) {
                            if (response.code() == 200) {
                                Gson gson = new Gson();
                                try {
                                    JSONObject object = new JSONObject(response.body());
                                    int code = object.optInt(Constant.CODE);//状态码
                                    if (code == 200) {
                                        UploadFileBean fileBean = gson.fromJson(response.body(), UploadFileBean.class);
                                        view.resultUploadFile(fileBean);
                                    } else {
                                        UploadFileBean bean = new UploadFileBean();
                                        String message = object.optString("msg");
                                        bean.setCode(code);
                                        bean.setMsg(message);
                                        view.resultUploadFile(bean);
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                view.resultUploadFile(null);
                            }
                        } else {
                            view.resultUploadFile(null);
                        }
                    }
                });

    }*/

    /**
     * Description:UploadFilePresenter
     * Time:2019-4-18   下午 4:14
     * Detail: TODO 上传文件
     */
    public void upLoadFile(File file) {

        PostRequest<String> postRequest = OkGo.<String>post(ApiStores.FILE_UPLOAD).tag(this).isMultipart(true);
        postRequest.params("image", file);
        postRequest.execute(new StringCallback() {
            @Override
            public void onStart(Request<String, ? extends Request> request) {
                super.onStart(request);
                view.startUploadFile();
            }

            @Override
            public void onError(Response<String> response) {
                super.onError(response);
                view.errorUploadFile();
            }

            @Override
            public void onFinish() {
                super.onFinish();
                view.errorUploadFile();
            }

            @Override
            public void uploadProgress(Progress progress) {
                super.uploadProgress(progress);
                LogUtils.e("uploadProgress---------------------" + progress);
            }

            @Override
            public void onSuccess(Response<String> response) {
                String body = response.body();
                if (body != null && !body.equals(equals(""))) {
                    if (response.code() == 200) {
                        Gson gson = new Gson();
                        try {
                            JSONObject object = new JSONObject(response.body());
                            int code = object.optInt(Constant.CODE);//状态码
                            if (code == 200) {
                                UploadFileBean fileBean = gson.fromJson(response.body(), UploadFileBean.class);
                                view.resultUploadFile(fileBean);
                            } else {
                                UploadFileBean bean = new UploadFileBean();
                                String message = object.optString("msg");
                                bean.setCode(code);
                                bean.setMsg(message);
                                view.resultUploadFile(bean);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        view.resultUploadFile(null);
                    }
                } else {
                    view.resultUploadFile(null);
                }
            }
        });
    }
}
