package com.ucfo.youcai.common;

/**
 * Author:AND
 * Time: 2019-3-11.  下午 2:26
 * Email:2911743255@qq.com
 * ClassName: SimpleModel
 * Package: com.ucfo.youcai.common
 * Description:
 * Detail:
 */
public class SimpleModel {


    private int status_code;
    private String message;

    public BaseModel toBaseModel() {
        BaseModel baseModel = new BaseModel();
        baseModel.setStatus_code(status_code);
        baseModel.setMessage(message);
        return baseModel;
    }

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

    @Override
    public String toString() {
        return "SimpleModel{" +
                "status_code=" + status_code +
                ", message='" + message + '\'' +
                '}';
    }
}
