package com.ucfo.youcaiwx.entity.integral;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-14.  上午 11:56
 * Package: com.ucfo.youcaiwx.entity.integral
 * FileName: EarnIntegralBean
 * Description:TODO 积分任务清单
 */
public class EarnIntegralBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"daily":[{"id":1,"name":"学习中心签到获得相应的积分","count":1,"score":5,"number":1},{"id":2,"name":"在线学习时间连续30分钟以上","count":1,"score":5,"number":1},{"id":3,"name":"分享App至朋友圈、微信、好友","count":10,"score":2,"number":2},{"id":4,"name":"分享日签获得积分","count":1,"score":5,"number":1},{"id":8,"name":"创建学习计划后完成每日对应的计划","count":1,"score":10,"number":0}],"novice":[{"id":5,"name":"编辑昵称","count":1,"score":20,"number":0},{"id":6,"name":"编辑头像","count":1,"score":30,"number":1},{"id":7,"name":"关注公众号","count":1,"score":50,"number":1}]}
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
        private List<DailyBean> daily;
        private List<NoviceBean> novice;

        public List<DailyBean> getDaily() {
            if (daily == null) {
                return new ArrayList<>();
            }
            return daily;
        }

        public void setDaily(List<DailyBean> daily) {
            this.daily = daily;
        }

        public List<NoviceBean> getNovice() {
            if (novice == null) {
                return new ArrayList<>();
            }
            return novice;
        }

        public void setNovice(List<NoviceBean> novice) {
            this.novice = novice;
        }

        public static class DailyBean {
            /**
             * id : 1
             * name : 学习中心签到获得相应的积分
             * count : 1
             * score : 5
             * number : 1
             */

            private int id;
            private String name;
            private String count;
            private String score;
            private String number;

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

            public String getCount() {
                return count == null ? "" : count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getScore() {
                return score == null ? "" : score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getNumber() {
                return number == null ? "" : number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }

        public static class NoviceBean {
            /**
             * id : 5
             * name : 编辑昵称
             * count : 1
             * score : 20
             * number : 0
             */

            private int id;
            private String name;
            private String count;
            private String score;
            private String number;

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

            public String getCount() {
                return count == null ? "" : count;
            }

            public void setCount(String count) {
                this.count = count;
            }

            public String getScore() {
                return score == null ? "" : score;
            }

            public void setScore(String score) {
                this.score = score;
            }

            public String getNumber() {
                return number == null ? "" : number;
            }

            public void setNumber(String number) {
                this.number = number;
            }
        }
    }
}
