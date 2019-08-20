package com.ucfo.youcaiwx.utils.netutils;

import android.support.annotation.NonNull;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Progress;
import com.xuexiang.xupdate.proxy.IUpdateHttpService;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;

/**
 * Author: AND
 * Time: 2019-8-20.  下午 4:37
 * Package: com.ucfo.youcaiwx.utils.netutils
 * FileName: OKHttpUpdateHttpService
 */
public class OKHttpUpdateHttpService implements IUpdateHttpService {
    public OKHttpUpdateHttpService() {
    }

    @Override
    public void asyncGet(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        OkGo.<String>get(url).params(transform(params)).execute(new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                Throwable exception = response.getException();
                callBack.onError(exception);
            }
        });
    }

    @Override
    public void asyncPost(@NonNull String url, @NonNull Map<String, Object> params, final @NonNull Callback callBack) {
        //这里默认post的是Form格式，使用json格式的请修改 post -> postString
        OkGo.<String>post(url).params(transform(params)).execute(new com.lzy.okgo.callback.StringCallback() {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                callBack.onSuccess(response.body());
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<String> response) {
                super.onError(response);
                callBack.onError(response.getException());
            }
        });
    }

    @Override
    public void download(@NonNull String url, @NonNull String path, @NonNull String fileName, final @NonNull DownloadCallback callback) {
        OkGo.<File>get(url).execute(new com.lzy.okgo.callback.FileCallback(path, fileName) {
            @Override
            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                File body = response.body();
                callback.onSuccess(body);
            }

            @Override
            public void onStart(com.lzy.okgo.request.base.Request<File, ? extends com.lzy.okgo.request.base.Request> request) {
                super.onStart(request);
                callback.onStart();
            }

            @Override
            public void onError(com.lzy.okgo.model.Response<File> response) {
                super.onError(response);
                callback.onError(response.getException());
            }

            @Override
            public void downloadProgress(Progress progress) {
                super.downloadProgress(progress);
                callback.onProgress(progress.fraction, progress.totalSize);
            }
        });
    }

    @Override
    public void cancelDownload(@NonNull String url) {

    }

    private Map<String, String> transform(Map<String, Object> params) {
        Map<String, String> map = new TreeMap<>();
        for (Map.Entry<String, Object> entry : params.entrySet()) {
            map.put(entry.getKey(), entry.getValue().toString());
        }
        return map;
    }
}
