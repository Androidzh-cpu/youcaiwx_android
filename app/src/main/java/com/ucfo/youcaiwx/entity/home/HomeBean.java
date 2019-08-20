package com.ucfo.youcaiwx.entity.home;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:AND
 * Time: 2019/3/14.  9:49
 * Email:2911743255@qq.com
 * ClassName: HomeBean
 * Description:
 * Detail:TODO 首页
 */
public class HomeBean {


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
        private List<ListpicBean> listpic;
        private List<HotspotBean> hotspot;
        private List<BroadcastBean> broadcast;
        private List<CurriculumBean> curriculum;
        private List<InformationBean> information;

        public List<ListpicBean> getListpic() {
            if (listpic == null) {
                return new ArrayList<>();
            }
            return listpic;
        }

        public void setListpic(List<ListpicBean> listpic) {
            this.listpic = listpic;
        }

        public List<HotspotBean> getHotspot() {
            if (hotspot == null) {
                return new ArrayList<>();
            }
            return hotspot;
        }

        public void setHotspot(List<HotspotBean> hotspot) {
            this.hotspot = hotspot;
        }

        public List<BroadcastBean> getBroadcast() {
            if (broadcast == null) {
                return new ArrayList<>();
            }
            return broadcast;
        }

        public void setBroadcast(List<BroadcastBean> broadcast) {
            this.broadcast = broadcast;
        }

        public List<CurriculumBean> getCurriculum() {
            if (curriculum == null) {
                return new ArrayList<>();
            }
            return curriculum;
        }

        public void setCurriculum(List<CurriculumBean> curriculum) {
            this.curriculum = curriculum;
        }

        public List<InformationBean> getInformation() {
            if (information == null) {
                return new ArrayList<>();
            }
            return information;
        }

        public void setInformation(List<InformationBean> information) {
            this.information = information;
        }

        public static class ListpicBean {
            /**
             * image_href : http://pic.90sjimg.com/design/00/15/64/16/56160fa850f20.jpg
             * jump_href : http://pic.90sjimg.com/design/00/15/64/16/56160fa850f20.jpg
             */

            private String image_href;
            private String jump_href;

            public String getImage_href() {
                return image_href == null ? "" : image_href;
            }

            public void setImage_href(String image_href) {
                this.image_href = image_href;
            }

            public String getJump_href() {
                return jump_href == null ? "" : jump_href;
            }

            public void setJump_href(String jump_href) {
                this.jump_href = jump_href;
            }
        }

        public static class HotspotBean {
            /**
             * title : 3.5 大事件
             * jumphref : http://pic34.photophoto.cn/20150110/0020033068599226_b.jpg
             */

            private String title;
            private String jumphref;

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getJumphref() {
                return jumphref == null ? "" : jumphref;
            }

            public void setJumphref(String jumphref) {
                this.jumphref = jumphref;
            }
        }

        public static class BroadcastBean {
            /**
             * title : 翻转课堂
             * price : 0.00
             * app_image : http://pic34.photophoto.cn/20150110/0020033068599226_b.jpg
             * teacher_id : 1
             * teacher_name : 王强
             */

            private String title;
            private String price;
            private String app_image;
            private String teacher_id;
            private String teacher_name;
            private String jumplink;

            public String getJumplink() {
                return jumplink == null ? "" : jumplink;
            }

            public void setJumplink(String jumplink) {
                this.jumplink = jumplink;
            }

            public String getTitle() {
                return title == null ? "" : title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getApp_image() {
                return app_image == null ? "" : app_image;
            }

            public void setApp_image(String app_image) {
                this.app_image = app_image;
            }

            public String getTeacher_id() {
                return teacher_id == null ? "" : teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }
        }

        public static class CurriculumBean {
            /**
             * name : 中文p1课程包
             * teacher_id : 1,2
             * app_img :
             * billing_status : 2
             * price :
             * join_num : 985
             * teacher_name : 王强、王强、金龟
             */

            private String name;
            private String package_id;
            private String teacher_id;
            private String app_img;
            private String billing_status;
            private String price;
            private String join_num;
            private String teacher_name;

            public String getName() {
                return name == null ? "" : name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getPackage_id() {
                return package_id == null ? "" : package_id;
            }

            public void setPackage_id(String package_id) {
                this.package_id = package_id;
            }

            public String getTeacher_id() {
                return teacher_id == null ? "" : teacher_id;
            }

            public void setTeacher_id(String teacher_id) {
                this.teacher_id = teacher_id;
            }

            public String getApp_img() {
                return app_img == null ? "" : app_img;
            }

            public void setApp_img(String app_img) {
                this.app_img = app_img;
            }

            public String getBilling_status() {
                return billing_status == null ? "" : billing_status;
            }

            public void setBilling_status(String billing_status) {
                this.billing_status = billing_status;
            }

            public String getPrice() {
                return price == null ? "" : price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getJoin_num() {
                return join_num == null ? "" : join_num;
            }

            public void setJoin_num(String join_num) {
                this.join_num = join_num;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }
        }

        public static class InformationBean {
            /**
             * title : 3.5 大事件
             * source : 优财网校
             * jumphref : http://pic34.photophoto.cn/20150110/0020033068599226_b.jpg
             * imageurl : http://pic34.photophoto.cn/20150110/0020033068599226_b.jpg
             * create_time : 2019-03-14 09:12:18
             */

            private String title;
            private String source;
            private String jumphref;
            private String image;
            private String create_time;

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

            public String getJumphref() {
                return jumphref == null ? "" : jumphref;
            }

            public void setJumphref(String jumphref) {
                this.jumphref = jumphref;
            }

            public String getImageurl() {
                return image == null ? "" : image;
            }

            public void setImageurl(String imageurl) {
                this.image = imageurl;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}
