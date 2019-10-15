package com.ucfo.youcaiwx.entity.integral;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-14.  下午 1:52
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: IntegralDetailBean
 * Description:TODO 积分明细
 */
public class IntegralDetailBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":21,"name":"学习中心签到获得相应的积分","detailed":"5","uptime":"2019-10-12 15:06:38","type":1}]
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
         * id : 21
         * name : 学习中心签到获得相应的积分
         * detailed : 5
         * uptime : 2019-10-12 15:06:38
         * type : 1
         */

        private int id;
        private String name;
        private String detailed;
        private String uptime;
        private int type;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDetailed() {
            return detailed == null ? "" : detailed;
        }

        public void setDetailed(String detailed) {
            this.detailed = detailed;
        }

        public String getUptime() {
            return uptime == null ? "" : uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
