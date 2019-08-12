package com.ucfo.youcai.entity.home;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 1:47
 * Package: com.ucfo.youcai.entity.home
 * FileName: MessageCenterHomeBean
 * Description:TODO 消息中心首页
 */
public class MessageCenterHomeBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"announ":{"title":"呦呵，你个臭傻子","status":1},"notice":{"title":"订单支付成功通知","status":1}}
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
        return msg == null ? "" : msg;
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
         * announ : {"title":"呦呵，你个臭傻子","status":1}
         * notice : {"title":"订单支付成功通知","status":1}
         */

        private AnnounBean announ;
        private NoticeBean notice;

        public AnnounBean getAnnoun() {
            return announ;
        }

        public void setAnnoun(AnnounBean announ) {
            this.announ = announ;
        }

        public NoticeBean getNotice() {
            return notice;
        }

        public void setNotice(NoticeBean notice) {
            this.notice = notice;
        }

        public static class AnnounBean {
            /**
             * title : 呦呵，你个臭傻子
             * status : 1
             */

            private String title;
            private int status;

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }

        public static class NoticeBean {
            /**
             * title : 订单支付成功通知
             * status : 1
             */

            private String title;
            private int status;

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }
        }
    }
}
