package com.ucfo.youcaiwx.entity.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-9-11.  上午 9:34
 * Package: com.ucfo.youcaiwx.entity.home
 * FileName: InformationListBean
 * Description:TODO 资讯详情
 */
public class InformationListBean {

    /**
     * code : 200
     * msg : 操作成功
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
        private String news_id;
        private String title;
        private String source;
        private String image;
        private String create_time;
        private String jumphref;
        private String author;
        private String url;

        public String getNews_id() {
            return news_id == null ? "" : news_id;
        }

        public void setNews_id(String news_id) {
            this.news_id = news_id;
        }

        public String getTitle() {
            return title == null ? "" : title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getSource() {
            return source == null ? "" : source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getImage() {
            return image == null ? "" : image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getCreate_time() {
            return create_time == null ? "" : create_time;
        }

        public void setCreate_time(String create_time) {
            this.create_time = create_time;
        }

        public String getJumphref() {
            return jumphref == null ? "" : jumphref;
        }

        public void setJumphref(String jumphref) {
            this.jumphref = jumphref;
        }

        public String getAuthor() {
            return author == null ? "" : author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
