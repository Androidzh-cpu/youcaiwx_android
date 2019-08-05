package com.ucfo.youcai.entity.learncenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-19.  下午 3:12
 * Package: com.ucfo.youcai.entity.learncenter
 * FileName: UnFinishPlanBean
 * Description:TODO 未完成学习计划
 */
public class UnFinishPlanBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : [{"package_id":3,"plan_id":1,"plan_name":"CMA中文Part1学习计划","video":[{"video_name":" 2018年7月考期考后盘点P1-杨晔","id":1},{"video_name":"《管理会计案例与实践》之COSO的内控与风险管理 ","id":2},{"video_name":"《管理会计案例与实践》之500强公司月度分析","id":3},{"video_name":" 2018年11月P2A部分-报表分析-代坤","id":4}]},{"plan_id":2,"plan_name":"CMA中文Part2学习计划","video":[{"video_name":"我的影片","id":5}]}]
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
         * package_id : 3
         * plan_id : 1
         * plan_name : CMA中文Part1学习计划
         * video : [{"video_name":" 2018年7月考期考后盘点P1-杨晔","id":1},{"video_name":"《管理会计案例与实践》之COSO的内控与风险管理 ","id":2},{"video_name":"《管理会计案例与实践》之500强公司月度分析","id":3},{"video_name":" 2018年11月P2A部分-报表分析-代坤","id":4}]
         */

        private int package_id;
        private int plan_id;
        private String plan_name;
        private List<VideoBean> video;

        public int getPackage_id() {
            return package_id;
        }

        public void setPackage_id(int package_id) {
            this.package_id = package_id;
        }

        public int getPlan_id() {
            return plan_id;
        }

        public void setPlan_id(int plan_id) {
            this.plan_id = plan_id;
        }

        public String getPlan_name() {
            return plan_name == null ? "" : plan_name;
        }

        public void setPlan_name(String plan_name) {
            this.plan_name = plan_name;
        }

        public List<VideoBean> getVideo() {
            if (video == null) {
                return new ArrayList<>();
            }
            return video;
        }

        public void setVideo(List<VideoBean> video) {
            this.video = video;
        }

        public static class VideoBean {
            /**
             * video_name :  2018年7月考期考后盘点P1-杨晔
             * id : 1
             */

            private String video_name;
            private int id;

            public String getVideo_name() {
                return video_name == null ? "" : video_name;
            }

            public void setVideo_name(String video_name) {
                this.video_name = video_name;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
