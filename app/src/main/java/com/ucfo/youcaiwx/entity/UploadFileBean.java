package com.ucfo.youcaiwx.entity;

/**
 * Author:29117
 * Time: 2019-4-18.  下午 4:33
 * Email:2911743255@qq.com
 * ClassName: UploadFileBean
 */
public class UploadFileBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"image_url":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190418/3bda84a10d7b23ce978c06b57d4b286a.png"}
     */

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * image_url : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190418/3bda84a10d7b23ce978c06b57d4b286a.png
         */

        private String image_url;

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }
    }
}
