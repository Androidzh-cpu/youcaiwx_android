package com.ucfo.youcaiwx.entity.complain;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-11-13.  下午 4:25
 * Package: com.ucfo.youcaiwx.entity.complain
 * FileName: ComplainTypeBean
 * Description:TODO 投诉类别
 */
public class ComplainTypeBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":1,"complain_name":"错别字"},{"id":2,"complain_name":"推诿，敷衍了事"},{"id":3,"complain_name":"答非所问"},{"id":4,"complain_name":"辱骂学员"},{"id":5,"complain_name":"专业性错误或不严谨"},{"id":6,"complain_name":"其他"}]
     */

    private int code;
    private String msg;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * complain_name : 错别字
         */

        private String id;
        private String complain_name;
        private int state;

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getComplain_name() {
            return complain_name == null ? "" : complain_name;
        }

        public void setComplain_name(String complain_name) {
            this.complain_name = complain_name;
        }
    }
}
