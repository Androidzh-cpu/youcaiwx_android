package com.ucfo.youcaiwx.entity.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 2:34
 * FileName: MessageCenterNoticeBean
 * Description:TODO 消息列表
 */
public class MessageCenterNoticeBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"message_id":2,"defaultTitle":"你是一个小傻子","content":"哈哈哈哈哈哈哈哈","create_time":"2019-08-09 08:53:24","type":1,"types":4,"status":1},{"message_id":1,"defaultTitle":"12312","content":"3131231313","create_time":"2019-08-07 10:28:24","type":1,"types":4,"status":1}]
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
         * message_id : 2
         * defaultTitle : 你是一个小傻子
         * content : 哈哈哈哈哈哈哈哈
         * create_time : 2019-08-09 08:53:24
         * type : 1
         * types : 4
         * status : 1
         */

        private int message_id;
        private int content_id;
        private String title;
        private String content;
        private String create_time;
        private String href;
        private String from;
        private String order_num;
        private String pay_price;
        private String add_time;
        private String package_name;
        private int type;
        private int status;

        public int getMessage_id() {
            return message_id;
        }

        public void setMessage_id(int message_id) {
            this.message_id = message_id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getHref() {
            return href == null ? "" : href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getFrom() {
            return from == null ? "" : from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getOrder_num() {
            return order_num == null ? "" : order_num;
        }

        public void setOrder_num(String order_num) {
            this.order_num = order_num;
        }

        public String getPay_price() {
            return pay_price == null ? "" : pay_price;
        }

        public void setPay_price(String pay_price) {
            this.pay_price = pay_price;
        }

        public String getAdd_time() {
            return add_time == null ? "" : add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }

        public String getPackage_name() {
            return package_name == null ? "" : package_name;
        }

        public void setPackage_name(String package_name) {
            this.package_name = package_name;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getContent_id() {
            return content_id;
        }

        public void setContent_id(int content_id) {
            this.content_id = content_id;
        }
    }
}
