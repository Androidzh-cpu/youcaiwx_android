package com.ucfo.youcai.common;

import java.io.Serializable;

/**
 * Author:AND
 * Time: 2019-3-11.  上午 11:54
 * Email:2911743255@qq.com
 * ClassName: BaseModel
 * Package: com.ucfo.youcai.common
 * Description:基础模型类
 * Detail:
 */
public class BaseModel<T> implements Serializable {

    private int status_code;
    private String message;
    private T data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public String getMessage() {
        return message == null ? "" : message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}