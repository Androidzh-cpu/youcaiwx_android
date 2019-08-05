package com.ucfo.youcai.entity.learncenter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  上午 11:20
 * Package: com.ucfo.youcai.entity.learncenter
 * FileName: AddLearnPlanCheckTimeBean
 * Description:TODO 添加学习计划-选择时间
 */
public class AddLearnPlanCheckTimeBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : [{"test_time":"2019-11-11"},{"test_time":"2020-04-06"}]
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
         * test_time : 2019-11-11
         */

        private String test_time;

        public String getTest_time() {
            return test_time;
        }

        public void setTest_time(String test_time) {
            this.test_time = test_time;
        }
    }
}
