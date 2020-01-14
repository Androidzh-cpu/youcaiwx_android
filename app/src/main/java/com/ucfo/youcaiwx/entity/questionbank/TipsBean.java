package com.ucfo.youcaiwx.entity.questionbank;

/**
 * Author: AND
 * Time: 2020-1-14.  下午 4:03
 * Package: com.ucfo.youcaiwx.entity.questionbank
 * FileName: TipsBean
 * Description:TODO 提示框
 */
public class TipsBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"plate_title":"对应的标题","plate_content":"内容后台设置、内容和客服商议","paper_title":"对应的标题","paper_content":"内容后台设置、内容和客服商议"}
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
         * plate_title : 对应的标题
         * plate_content : 内容后台设置、内容和客服商议
         * paper_title : 对应的标题
         * paper_content : 内容后台设置、内容和客服商议
         */

        private String plate_title;
        private String plate_content;
        private String paper_title;
        private String paper_content;

        public String getPlate_title() {
            return plate_title == null ? "" : plate_title;
        }

        public void setPlate_title(String plate_title) {
            this.plate_title = plate_title;
        }

        public String getPlate_content() {
            return plate_content == null ? "" : plate_content;
        }

        public void setPlate_content(String plate_content) {
            this.plate_content = plate_content;
        }

        public String getPaper_title() {
            return paper_title == null ? "" : paper_title;
        }

        public void setPaper_title(String paper_title) {
            this.paper_title = paper_title;
        }

        public String getPaper_content() {
            return paper_content == null ? "" : paper_content;
        }

        public void setPaper_content(String paper_content) {
            this.paper_content = paper_content;
        }
    }
}
