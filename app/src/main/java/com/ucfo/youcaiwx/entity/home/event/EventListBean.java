package com.ucfo.youcaiwx.entity.home.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-20.  下午 3:31
 * Package: com.ucfo.youcaiwx.entity.home.event
 * FileName: EventListBean
 * Description:TODO 首页活动列表
 */
public class EventListBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : [{"id":2,"name":"预售课程2","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191220/a868b842bf1d308738f821020a89789f.jpeg","href_type":1,"jump":"","start_time":"2019-12-19 16:00","end_time":"2019-12-19 16:00","activity_address":"北京海淀五道口","cpe":30,"num":0},{"id":1,"name":"预售课程1","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191218/fe7a24adc255df135997fbddd8f4e0e4.jpeg","href_type":1,"jump":"","start_time":"2019-12-18 00:00","end_time":"2019-12-26 00:00","activity_address":"北京","cpe":50,"num":1}]
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
         * id : 2
         * name : 预售课程2
         * app_img : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191220/a868b842bf1d308738f821020a89789f.jpeg
         * href_type : 1
         * jump :
         * start_time : 2019-12-19 16:00
         * end_time : 2019-12-19 16:00
         * activity_address : 北京海淀五道口
         * cpe : 30
         * num : 0
         */

        private String id;
        private String name;
        private String app_img;
        private String href_type;
        private String jump;
        private String start_time;
        private String end_time;
        private String activity_address;
        private String cpe;
        private String num;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getHref_type() {
            return href_type == null ? "" : href_type;
        }

        public void setHref_type(String href_type) {
            this.href_type = href_type;
        }

        public String getJump() {
            return jump == null ? "" : jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }

        public String getStart_time() {
            return start_time == null ? "" : start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getActivity_address() {
            return activity_address == null ? "" : activity_address;
        }

        public void setActivity_address(String activity_address) {
            this.activity_address = activity_address;
        }

        public String getCpe() {
            return cpe == null ? "" : cpe;
        }

        public void setCpe(String cpe) {
            this.cpe = cpe;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }
    }
}
