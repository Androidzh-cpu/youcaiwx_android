package com.ucfo.youcaiwx.entity.home.education;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-24.  上午 9:49
 * Package: com.ucfo.youcaiwx.entity.home.education
 * FileName: EducationTypeBean
 * Description:TODO 后续教育分类
 */
public class EducationTypeBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"type":[{"type_id":0,"type_name":"全部"},{"type_id":1,"type_name":"测试1"},{"type_id":2,"type_name":"测试2"},{"type_id":3,"type_name":"测试3"}],"sort":[{"type_id":0,"type_name":"默认"},{"type_id":1,"type_name":"最新"},{"type_id":2,"type_name":"人气(从高到低)"},{"type_id":3,"type_name":"人气(从低到高)"},{"type_id":4,"type_name":"价格(从高到低)"},{"type_id":5,"type_name":"价格(从低到高)"}],"screening":[{"type_id":0,"type_name":"不限"},{"type_id":1,"type_name":"收费"},{"type_id":2,"type_name":"免费"}]}
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
        private List<TypeBean> type;
        private List<SortBean> sort;
        private List<ScreeningBean> screening;

        public List<TypeBean> getType() {
            if (type == null) {
                return new ArrayList<>();
            }
            return type;
        }

        public void setType(List<TypeBean> type) {
            this.type = type;
        }

        public List<SortBean> getSort() {
            if (sort == null) {
                return new ArrayList<>();
            }
            return sort;
        }

        public void setSort(List<SortBean> sort) {
            this.sort = sort;
        }

        public List<ScreeningBean> getScreening() {
            if (screening == null) {
                return new ArrayList<>();
            }
            return screening;
        }

        public void setScreening(List<ScreeningBean> screening) {
            this.screening = screening;
        }

        public static class TypeBean {
            /**
             * type_id : 0
             * type_name : 全部
             */

            private String type_id;
            private String type_name;
            private boolean isChecked;

            public boolean getChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getType_id() {
                return type_id == null ? "" : type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name == null ? "" : type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }

        public static class SortBean {
            /**
             * type_id : 0
             * type_name : 默认
             */

            private String type_id;
            private String type_name;
            private boolean isChecked;

            public boolean getChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getType_id() {
                return type_id == null ? "" : type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name == null ? "" : type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }

        public static class ScreeningBean {
            /**
             * type_id : 0
             * type_name : 不限
             */

            private String type_id;
            private String type_name;
            private boolean isChecked;

            public boolean getChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getType_id() {
                return type_id == null ? "" : type_id;
            }

            public void setType_id(String type_id) {
                this.type_id = type_id;
            }

            public String getType_name() {
                return type_name == null ? "" : type_name;
            }

            public void setType_name(String type_name) {
                this.type_name = type_name;
            }
        }
    }
}
