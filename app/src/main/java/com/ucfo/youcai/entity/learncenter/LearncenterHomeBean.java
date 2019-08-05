package com.ucfo.youcai.entity.learncenter;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-18.  上午 10:26
 * Package: com.ucfo.youcai.entity.learncenter
 * FileName: LearncenterHomeBean
 * Description:TODO 学习中心首页
 */
public class LearncenterHomeBean {

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
         * addlearn : 2
         * state : 2
         * user : {"username":"Wang","head":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/0ec7f3a1e2c520c8cf2ab5d24a626dc1.png","card":0}
         * plan : [{"plan_id":2,"plan_name":"60天学习计划（中等）","course_id":1,"plan_num":1},{"plan_id":3,"plan_name":"30天学习计划","course_id":2,"plan_num":1},{"plan_id":5,"plan_name":"20天学习计划2","course_id":8,"plan_num":0},{"plan_id":8,"plan_name":"户籍","course_id":6,"plan_num":0}]
         * learnList : [{"join_id":1,"test_time":"2019-08-20","schedule":"1","join_days":1,"plan_days":30,"plan_name":"30天学习计划","create_time":"2019-09-16 16:55:52","number":33},{"join_id":2,"test_time":"2019-08-01","schedule":"0","join_days":2,"plan_days":60,"plan_name":"60天学习计划（中等）","create_time":"2019-07-16 14:13:25","number":14}]
         * news : {"id":6,"title":"这个是学习公告","source":"这个是学习公告","image":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190718/541abb7c1877625d17428fd3c62d5073.jpeg","jumphref":"http://pic37.nipic.com/20140105/15166348_202320428000_2.jpg","create_time":"2019-07-18 11:07:43","creates_time":"2019-07-18"}
         */

        private int addlearn;
        private int state;
        private UserBean user;
        private NewsBean news;
        private List<PlanBean> plan;
        private List<LearnListBean> learnList;

        public int getAddlearn() {
            return addlearn;
        }

        public void setAddlearn(int addlearn) {
            this.addlearn = addlearn;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public NewsBean getNews() {
            return news;
        }

        public void setNews(NewsBean news) {
            this.news = news;
        }

        public List<PlanBean> getPlan() {
            if (plan == null) {
                return new ArrayList<>();
            }
            return plan;
        }

        public void setPlan(List<PlanBean> plan) {
            this.plan = plan;
        }

        public List<LearnListBean> getLearnList() {
            if (learnList == null) {
                return new ArrayList<>();
            }
            return learnList;
        }

        public void setLearnList(List<LearnListBean> learnList) {
            this.learnList = learnList;
        }
        //TODO 用户信息

        public static class UserBean {
            /**
             * username : Wang
             * head : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/0ec7f3a1e2c520c8cf2ab5d24a626dc1.png
             * card : 0
             */

            private String username;
            private String head;
            private int card;

            public String getUsername() {
                return username == null ? "" : username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getHead() {
                return head == null ? "" : head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public int getCard() {
                return card;
            }

            public void setCard(int card) {
                this.card = card;
            }
        }

        //TODO 学习公告
        public static class NewsBean {
            /**
             * id : 6
             * title : 这个是学习公告
             * source : 这个是学习公告
             * image : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190718/541abb7c1877625d17428fd3c62d5073.jpeg
             * jumphref : http://pic37.nipic.com/20140105/15166348_202320428000_2.jpg
             * create_time : 2019-07-18 11:07:43
             * creates_time : 2019-07-18
             */

            private int id;
            private String title;
            private String source;
            private String image;
            private String jumphref;
            private String create_time;
            private String creates_time;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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

            public String getJumphref() {
                return jumphref == null ? "" : jumphref;
            }

            public void setJumphref(String jumphref) {
                this.jumphref = jumphref;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public String getCreates_time() {
                return creates_time == null ? "" : creates_time;
            }

            public void setCreates_time(String creates_time) {
                this.creates_time = creates_time;
            }
        }

        //TODO 学习计划列表
        public static class PlanBean {
            /**
             * plan_id : 2
             * plan_name : 60天学习计划（中等）
             * course_id : 1
             * plan_num : 1
             */

            private int plan_id;
            private String plan_name;
            private int course_id;
            private int plan_num;
            private int is_exper;

            public int getIs_exper() {
                return is_exper;
            }

            public void setIs_exper(int is_exper) {
                this.is_exper = is_exper;
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

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }

            public int getPlan_num() {
                return plan_num;
            }

            public void setPlan_num(int plan_num) {
                this.plan_num = plan_num;
            }
        }

        //TODO 学习计划详情
        public static class LearnListBean {
            /**
             * join_id : 1
             * test_time : 2019-08-20
             * schedule : 1
             * join_days : 1
             * plan_days : 30
             * plan_name : 30天学习计划
             * create_time : 2019-09-16 16:55:52
             * number : 33
             */

            private int join_id;
            private int plan_id;
            private int course_id;
            private String test_time;
            private String schedule;
            private int join_days;
            private int plan_days;
            private String plan_name;
            private String create_time;
            private int number;
            private int is_exper;

            public int getJoin_id() {
                return join_id;
            }

            public void setJoin_id(int join_id) {
                this.join_id = join_id;
            }

            public String getTest_time() {
                return test_time == null ? "" : test_time;
            }

            public void setTest_time(String test_time) {
                this.test_time = test_time;
            }

            public String getSchedule() {
                return schedule == null ? "" : schedule;
            }

            public void setSchedule(String schedule) {
                this.schedule = schedule;
            }

            public int getJoin_days() {
                return join_days;
            }

            public void setJoin_days(int join_days) {
                this.join_days = join_days;
            }

            public int getPlan_days() {
                return plan_days;
            }

            public void setPlan_days(int plan_days) {
                this.plan_days = plan_days;
            }

            public String getPlan_name() {
                return plan_name == null ? "" : plan_name;
            }

            public void setPlan_name(String plan_name) {
                this.plan_name = plan_name;
            }

            public String getCreate_time() {
                return create_time == null ? "" : create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }

            public int getNumber() {
                return number;
            }

            public void setNumber(int number) {
                this.number = number;
            }

            public int getIs_exper() {
                return is_exper;
            }

            public void setIs_exper(int is_exper) {
                this.is_exper = is_exper;
            }

            public int getPlan_id() {
                return plan_id;
            }

            public void setPlan_id(int plan_id) {
                this.plan_id = plan_id;
            }

            public int getCourse_id() {
                return course_id;
            }

            public void setCourse_id(int course_id) {
                this.course_id = course_id;
            }
        }
    }
}
