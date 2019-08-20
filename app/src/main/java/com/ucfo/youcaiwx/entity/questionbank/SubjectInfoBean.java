package com.ucfo.youcaiwx.entity.questionbank;

/**
 * Author:29117
 * Time: 2019-4-26.  下午 4:24
 * Email:2911743255@qq.com
 * ClassName: SubjectInfoBean
 * Description:TODO 题库对应个人信息
 * Detail:TODO
 */
public class SubjectInfoBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"total_num":"181","ranking":1,"accuracy":65,"score":25}
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
        /**
         * total_num : 181
         * ranking : 1
         * accuracy : 65
         * score : 25
         */

        private String total_num;
        private int ranking;
        private float accuracy;
        private float score;

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public int getRanking() {
            return ranking;
        }

        public void setRanking(int ranking) {
            this.ranking = ranking;
        }

        public float getAccuracy() {
            return accuracy;
        }

        public void setAccuracy(float accuracy) {
            this.accuracy = accuracy;
        }

        public float getScore() {
            return score;
        }

        public void setScore(float score) {
            this.score = score;
        }
    }
}
