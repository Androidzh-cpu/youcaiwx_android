package com.ucfo.youcaiwx.presenter.presenterImpl.home;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.InformationListBean;
import com.ucfo.youcaiwx.presenter.view.home.IInformationView;

/**
 * Author: AND
 * Time: 2019-9-11.  上午 9:25
 * Package: com.ucfo.youcaiwx.presenter.presenterImpl.home
 * FileName: InformationPresenter
 * Description:TODO 资讯业务
 */
public class InformationPresenter {
    private IInformationView view;

    public InformationPresenter(IInformationView view) {
        this.view = view;
    }

    /**
     * 获取资讯列表
     */
    public void getInformationListData(int page, String parent_id, String type_id) {
        OkGo.<String>post(ApiStores.MESSAGE_INFORMATIONLIST)
                .params(Constant.LIMIT, 10)
                .params(Constant.PAGE, page)
                .params("parent_id", parent_id)
                .params("type_id", type_id)
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
                                InformationListBean listBean = gson.fromJson(body, InformationListBean.class);
                                view.infomationList(listBean);
                            } else {
                                view.infomationList(null);
                            }
                        } else {
                            view.infomationList(null);
                        }
                    }
                });

    }
}
