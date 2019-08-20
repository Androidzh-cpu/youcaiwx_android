package com.ucfo.youcaiwx.entity.questionbank;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-8.  下午 1:58
 * FileName: QuestionKnowLedgeChildListBean
 * Description:TODO  知识点三级列表
 * Detail:TODO
 */
public class QuestionKnowLedgeChildListBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":1,"know_name":"总论"},{"id":2,"know_name":"成本度量和计量基本概念"},{"id":3,"know_name":"案例-税收成本和会计成本的区别"}]
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
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 1
         * know_name : 总论
         */

        private String id;
        private String know_name;
        private boolean isChecked;//添加CheckBox的选中状态

        public boolean getChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getKnow_name() {
            return know_name == null ? "" : know_name;
        }

        public void setKnow_name(String know_name) {
            this.know_name = know_name;
        }
    }
}
