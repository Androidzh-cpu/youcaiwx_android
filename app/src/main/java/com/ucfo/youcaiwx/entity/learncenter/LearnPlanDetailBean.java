package com.ucfo.youcaiwx.entity.learncenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-22.  上午 10:33
 * FileName: LearnPlanDetailBean
 * Description:TODO 学习计划详情尸体 checked
 */
public class LearnPlanDetailBean {
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

        private String advert;
        private List<DateBean> date;

        public String getAdvert() {
            return advert == null ? "" : advert;
        }

        public void setAdvert(String advert) {
            this.advert = advert;
        }

        public List<DateBean> getDate() {
            if (date == null) {
                return new ArrayList<>();
            }
            return date;
        }

        public void setDate(List<DateBean> date) {
            this.date = date;
        }

        public static class DateBean {
            private int sameday;
            private int is_rest;
            private int month;
            private String ymonth;
            private String day;
            private String week;
            private int days;
            private boolean checked;

            public int getSameday() {
                return sameday;
            }

            public void setSameday(int sameday) {
                this.sameday = sameday;
            }

            public int getIs_rest() {
                return is_rest;
            }

            public void setIs_rest(int is_rest) {
                this.is_rest = is_rest;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public String getYmonth() {
                return ymonth == null ? "" : ymonth;
            }

            public void setYmonth(String ymonth) {
                this.ymonth = ymonth;
            }

            public String getDay() {
                return day == null ? "" : day;
            }

            public void setDay(String day) {
                this.day = day;
            }

            public String getWeek() {
                return week == null ? "" : week;
            }

            public void setWeek(String week) {
                this.week = week;
            }

            public int getDays() {
                return days;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public boolean isChecked() {
                return checked;
            }

            public void setChecked(boolean checked) {
                this.checked = checked;
            }
        }
    }
}
