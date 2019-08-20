package com.ucfo.youcaiwx.utils.networkframe;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.ucfo.youcaiwx.common.BaseModel;
import com.ucfo.youcaiwx.common.SimpleModel;
import com.ucfo.youcaiwx.utils.LogUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Headers;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Author:AND
 * Time: 2019/3/14.  13:36
 * Email:2911743255@qq.com
 * ClassName: JsonCallBack
 * Description:
 * Detail:
 */
public abstract class JsonCallBack<T> extends AbsCallback<T> {

    private Gson gson = new Gson();
    private Type type;
    private Class<T> clazz;

    public JsonCallBack() {
    }

    public JsonCallBack(Type type) {
        this.type = type;
    }

    public JsonCallBack(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 该方法是子线程处理，不能做ui相关的工作
     * 主要作用是解析网络返回的 response 对象，生成onSuccess回调中需要的数据对象
     */
    @Override
    public T convertResponse(Response response) throws Throwable {
        if (type == null) {
            if (clazz == null) {
                // 如果没有通过构造函数传进来，就自动解析父类泛型的真实类型
                Type genType = getClass().getGenericSuperclass();
                type = ((ParameterizedType) genType).getActualTypeArguments()[0];
            } else {
                return parseClass(response, clazz);
            }
        }

        if (type instanceof ParameterizedType) {
            return parseParameterizedType(response, (ParameterizedType) type);
        } else if (type instanceof Class) {
            return parseClass(response, (Class<?>) type);
        } else {
            return parseType(response, type);
        }
    }

    private T parseClass(Response response, Class<?> rawType) throws Exception {
        if (rawType == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        if (rawType == String.class) {
            //noinspection unchecked
            return (T) body.string();
        } else if (rawType == JSONObject.class) {
            //noinspection unchecked
            return (T) new JSONObject(body.string());
        } else if (rawType == JSONArray.class) {
            //noinspection unchecked
            return (T) new JSONArray(body.string());
        } else {
            T t = gson.fromJson(jsonReader, rawType);
            response.close();
            return t;
        }
    }

    private T parseParameterizedType(Response response, ParameterizedType type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        Type rawType = type.getRawType();                     // 泛型的实际类型
        Type typeArgument = type.getActualTypeArguments()[0]; // 泛型的参数
        if (rawType != BaseModel.class) {
            // 泛型格式如下： new JsonCallback<外层BaseBean<内层JavaBean>>(this)
            T t = gson.fromJson(jsonReader, type);
            response.close();
            return t;
        } else {
            if (typeArgument == Void.class) {
                // 泛型格式如下： new JsonCallback<BaseModel<Void>>(this)
                SimpleModel simpleModel = gson.fromJson(jsonReader, SimpleModel.class);
                response.close();
                int code = simpleModel.getStatus_code();
                if (code == 401 || code == 505) {
                    throw new IllegalStateException(String.valueOf(code + "," + simpleModel.getMessage()));
                } else {
//                    LogUtils.e("api-->" + response.request().url().toString(),
//                            "code-->" + code,
//                            "method-->" + response.request().method(),
//                            "data-->" + simpleModel.getMessage());

                    //noinspection unchecked
                    return (T) simpleModel.toBaseModel();
                }
            } else {
                // 泛型格式如下： new JsonCallback<BaseModel<内层JavaBean>>(this)
                BaseModel baseModel = gson.fromJson(jsonReader, type);
                response.close();
                int code = baseModel.getStatus_code();
                if (code == 401 || code == 505) {
                    throw new IllegalStateException(String.valueOf(code + "," + baseModel.getMessage()));
                } else {
//                    LogUtils.e("api-->" + response.request().url().toString(),
//                            "code-->" + code,
//                            "method-->" + response.request().method(),
//                            "data-->" + StringUtils.formatJson(gson.toJson(baseModel)));

                    //noinspection unchecked
                    return (T) baseModel;
                }
            }
        }
    }

    private T parseType(Response response, Type type) throws Exception {
        if (type == null) return null;
        ResponseBody body = response.body();
        if (body == null) return null;
        JsonReader jsonReader = new JsonReader(body.charStream());

        // 泛型格式如下： new JsonCallback<任意JavaBean>(this)
        T t = gson.fromJson(jsonReader, type);
        response.close();
        return t;
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        super.onError(response);
        String msg = String.valueOf(response.getException().getMessage());
        if (response.code() == 500) {
            LogUtils.e("服务器异常");
        } else if (msg.startsWith("505") || response.code() == 505) {
            LogUtils.e("请安装最新版APP");
        } else if (response.code() == 404) {
            LogUtils.e("数据找不到了");
        } else if (msg.startsWith("401") || response.code() == 401) {
        } else if (response.code() == 200) {
            LogUtils.e("服务器数据异常");
        } else {
            LogUtils.e("网速过慢，请检查网络是否可用");
        }
    }

    @Override
    public void onSuccess(com.lzy.okgo.model.Response<T> response) {
        Headers headers = response.headers();
        String token = headers.get("Authorization");
    }
}

