package com.ucfo.youcaiwx.entity.home.event;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-23.  上午 10:29
 * Package: com.ucfo.youcaiwx.entity.home.event
 * FileName: EventDetailedBean
 * Description:TODO 活动详情
 */
public class EventDetailedBean {


    /**
     * code : 200
     * msg : 操作成功
     * data : {"id":1,"name":"活动1","app_img":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191223/7c70019340e7affd3ff1f4d7499e74d0.jpeg","href_type":1,"jump":"","start_time":"2019-12-23 00:00","end_time":"2019-12-23 00:00","activity_address":"北京海淀区","cpe":12,"people_num":123,"content":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191223/4c75d78c28d7327b56f7582088b49218.jpeg","teacher_id":"1,2","endtime":"2019-12-23 00:00:00","num":0,"teacehr_list":[{"id":2,"teacher_name":"代坤","longevity":"CMA管理会计案例大赛历任评委，拥有CMA，CICPA，CTA证书，\u201c211\u201d重点大学会计学硕士。 15年央企、国企及跨国企业财务管理实务背景，公开出版多本书籍，实践与理论完美兼备。从教CMA课程6年，执教班级超过60期，培训学员超2000人，班级学员考试通过率超过90%。中英授课，案例教学，全面细致的教学风格、认真负责的教学态度在学员群体中拥有卓越的口碑与超高的满意率。","introduce":"优财CMA独家签约讲师，沪上CMA明星讲师","pictrue":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191030/bf62054ded13012db269330717b686df.jpeg","en_name":null,"teacher_title":"优财独家签约王牌讲师"},{"id":1,"teacher_name":"杨晔","longevity":"CMA；CPA；CGA；CGMA；加拿大麦基尔大学MBA学位。 曾任职于加拿大皇家银行、新东方等知名企业，拥有国际化的视野和背景，多年企业实践管理与管理会计知识完美结合，真正将实务与考点融合，广受学员的欢迎。在财务培训方面, 杨晔先生多次为国内大型制造型和服务型企业进行管理会计, 财务报告和国际税法方面的培训，其培训内容充实全面，受到业界的一致好评。","introduce":"国内CMA案例教学专家，优财CMA研究院院长，北美会计、税务和财务实务服务专业人士","pictrue":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191030/67abd119d1889eba4ca7f19660124fd5.jpeg","en_name":null,"teacher_title":"优财独家签约王牌讲师"}],"time_type":2,"user_type":1}
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
         * id : 1
         * name : 活动1
         * app_img : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191223/7c70019340e7affd3ff1f4d7499e74d0.jpeg
         * href_type : 1
         * jump :
         * start_time : 2019-12-23 00:00
         * end_time : 2019-12-23 00:00
         * activity_address : 北京海淀区
         * cpe : 12
         * people_num : 123
         * content : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191223/4c75d78c28d7327b56f7582088b49218.jpeg
         * teacher_id : 1,2
         * endtime : 2019-12-23 00:00:00
         * num : 0
         * teacehr_list : [{"id":2,"teacher_name":"代坤","longevity":"CMA管理会计案例大赛历任评委，拥有CMA，CICPA，CTA证书，\u201c211\u201d重点大学会计学硕士。 15年央企、国企及跨国企业财务管理实务背景，公开出版多本书籍，实践与理论完美兼备。从教CMA课程6年，执教班级超过60期，培训学员超2000人，班级学员考试通过率超过90%。中英授课，案例教学，全面细致的教学风格、认真负责的教学态度在学员群体中拥有卓越的口碑与超高的满意率。","introduce":"优财CMA独家签约讲师，沪上CMA明星讲师","pictrue":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191030/bf62054ded13012db269330717b686df.jpeg","en_name":null,"teacher_title":"优财独家签约王牌讲师"},{"id":1,"teacher_name":"杨晔","longevity":"CMA；CPA；CGA；CGMA；加拿大麦基尔大学MBA学位。 曾任职于加拿大皇家银行、新东方等知名企业，拥有国际化的视野和背景，多年企业实践管理与管理会计知识完美结合，真正将实务与考点融合，广受学员的欢迎。在财务培训方面, 杨晔先生多次为国内大型制造型和服务型企业进行管理会计, 财务报告和国际税法方面的培训，其培训内容充实全面，受到业界的一致好评。","introduce":"国内CMA案例教学专家，优财CMA研究院院长，北美会计、税务和财务实务服务专业人士","pictrue":"https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191030/67abd119d1889eba4ca7f19660124fd5.jpeg","en_name":null,"teacher_title":"优财独家签约王牌讲师"}]
         * time_type : 2
         * user_type : 1
         */

        private String id;
        private String name;
        private String app_img;
        private String href_type;
        private String jump;
        private String start_time;
        private String end_time;
        private String activity_address;
        private String cpe;
        private String people_num;
        private String content;
        private String teacher_id;
        private String endtime;
        private String num;
        private String time_type;
        private String user_type;
        private List<TeacehrListBean> teacehr_list;

        public String getId() {
            return id == null ? "" : id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getApp_img() {
            return app_img == null ? "" : app_img;
        }

        public void setApp_img(String app_img) {
            this.app_img = app_img;
        }

        public String getHref_type() {
            return href_type == null ? "" : href_type;
        }

        public void setHref_type(String href_type) {
            this.href_type = href_type;
        }

        public String getJump() {
            return jump == null ? "" : jump;
        }

        public void setJump(String jump) {
            this.jump = jump;
        }

        public String getStart_time() {
            return start_time == null ? "" : start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getEnd_time() {
            return end_time == null ? "" : end_time;
        }

        public void setEnd_time(String end_time) {
            this.end_time = end_time;
        }

        public String getActivity_address() {
            return activity_address == null ? "" : activity_address;
        }

        public void setActivity_address(String activity_address) {
            this.activity_address = activity_address;
        }

        public String getCpe() {
            return cpe == null ? "" : cpe;
        }

        public void setCpe(String cpe) {
            this.cpe = cpe;
        }

        public String getPeople_num() {
            return people_num == null ? "" : people_num;
        }

        public void setPeople_num(String people_num) {
            this.people_num = people_num;
        }

        public String getContent() {
            return content == null ? "" : content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getTeacher_id() {
            return teacher_id == null ? "" : teacher_id;
        }

        public void setTeacher_id(String teacher_id) {
            this.teacher_id = teacher_id;
        }

        public String getEndtime() {
            return endtime == null ? "" : endtime;
        }

        public void setEndtime(String endtime) {
            this.endtime = endtime;
        }

        public String getNum() {
            return num == null ? "" : num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getTime_type() {
            return time_type == null ? "" : time_type;
        }

        public void setTime_type(String time_type) {
            this.time_type = time_type;
        }

        public String getUser_type() {
            return user_type == null ? "" : user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public List<TeacehrListBean> getTeacehr_list() {
            if (teacehr_list == null) {
                return new ArrayList<>();
            }
            return teacehr_list;
        }

        public void setTeacehr_list(List<TeacehrListBean> teacehr_list) {
            this.teacehr_list = teacehr_list;
        }

        public static class TeacehrListBean {
            /**
             * id : 2
             * teacher_name : 代坤
             * longevity : CMA管理会计案例大赛历任评委，拥有CMA，CICPA，CTA证书，“211”重点大学会计学硕士。 15年央企、国企及跨国企业财务管理实务背景，公开出版多本书籍，实践与理论完美兼备。从教CMA课程6年，执教班级超过60期，培训学员超2000人，班级学员考试通过率超过90%。中英授课，案例教学，全面细致的教学风格、认真负责的教学态度在学员群体中拥有卓越的口碑与超高的满意率。
             * introduce : 优财CMA独家签约讲师，沪上CMA明星讲师
             * pictrue : https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191030/bf62054ded13012db269330717b686df.jpeg
             * en_name : null
             * teacher_title : 优财独家签约王牌讲师
             */

            private int id;
            private String teacher_name;
            private String longevity;
            private String introduce;
            private String pictrue;
            private String en_name;
            private String teacher_title;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTeacher_name() {
                return teacher_name == null ? "" : teacher_name;
            }

            public void setTeacher_name(String teacher_name) {
                this.teacher_name = teacher_name;
            }

            public String getLongevity() {
                return longevity == null ? "" : longevity;
            }

            public void setLongevity(String longevity) {
                this.longevity = longevity;
            }

            public String getIntroduce() {
                return introduce == null ? "" : introduce;
            }

            public void setIntroduce(String introduce) {
                this.introduce = introduce;
            }

            public String getPictrue() {
                return pictrue == null ? "" : pictrue;
            }

            public void setPictrue(String pictrue) {
                this.pictrue = pictrue;
            }

            public String getEn_name() {
                return en_name == null ? "" : en_name;
            }

            public void setEn_name(String en_name) {
                this.en_name = en_name;
            }

            public String getTeacher_title() {
                return teacher_title == null ? "" : teacher_title;
            }

            public void setTeacher_title(String teacher_title) {
                this.teacher_title = teacher_title;
            }
        }
    }
}
