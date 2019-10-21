package com.ucfo.youcaiwx.entity.course;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-10-21.  下午 2:36
 * Package: com.ucfo.youcaiwx.entity.course
 * FileName: TestCourseAnswerDetailBean
 * Description:TODO 测试的,没啥用
 */
public class TestCourseAnswerDetailBean {

    /**
     * code : 200
     * msg : 操作成功
     * data : {"Title":{"title":"3-1-01成本和差异核算内容","video_time":199,"VideoId":"073db65988d747878b3d151a4f99f778","user_id":5,"package_id":5,"course_id":1,"section_id":5,"video_id":68,"is_purchase":1},"data":{"id":"1","video_time":"199","quiz":"提问题问提问问题","username":"王境泽","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/cab650f9c71dee1571e112eeabcca7f2.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/9813ebc9fd1a12718cc0a80f9a60680a.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/3de6673f98c972e908950e4f5a69fb51.jpeg"],"head":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/c24431814ce23198ede8889831b68f2d.jpeg","section_name":"第三章  绩效管理 ","creates_time":"4天前"},"reply":{"reply_quiz":"是的你好回家就离开家颗粒剂离开家卢克就看拉近了看就开了大撒尽量快点撒就","reply_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/a46c558e4cf9d6b09beb7507fcc80b9f.jpeg"],"head_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/63c285df69253f2854101c0775cc1e2d.jpeg","reply_user_name":"超管","repls_time":"4天前"}}
     */

    private int code;
    private String msg;
    private DataBeanX data;

    public static class DataBeanX {
        /**
         * Title : {"title":"3-1-01成本和差异核算内容","video_time":199,"VideoId":"073db65988d747878b3d151a4f99f778","user_id":5,"package_id":5,"course_id":1,"section_id":5,"video_id":68,"is_purchase":1}
         * data : {"id":"1","video_time":"199","quiz":"提问题问提问问题","username":"王境泽","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/cab650f9c71dee1571e112eeabcca7f2.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/9813ebc9fd1a12718cc0a80f9a60680a.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/3de6673f98c972e908950e4f5a69fb51.jpeg"],"head":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/c24431814ce23198ede8889831b68f2d.jpeg","section_name":"第三章  绩效管理 ","creates_time":"4天前"}
         * reply : {"reply_quiz":"是的你好回家就离开家颗粒剂离开家卢克就看拉近了看就开了大撒尽量快点撒就","reply_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/a46c558e4cf9d6b09beb7507fcc80b9f.jpeg"],"head_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/63c285df69253f2854101c0775cc1e2d.jpeg","reply_user_name":"超管","repls_time":"4天前"}
         */

        private TitleBean Title;
        private DataBean data;
        private ReplyBean reply;


        public static class TitleBean {
            /**
             * title : 3-1-01成本和差异核算内容
             * video_time : 199
             * VideoId : 073db65988d747878b3d151a4f99f778
             * user_id : 5
             * package_id : 5
             * course_id : 1
             * section_id : 5
             * video_id : 68
             * is_purchase : 1
             */

            private String title;
            private int video_time;
            private String VideoId;
            private int user_id;
            private int package_id;
            private int course_id;
            private int section_id;
            private int video_id;
            private int is_purchase;


        }

        public static class DataBean {
            /**
             * id : 1
             * video_time : 199
             * quiz : 提问题问提问问题
             * username : 王境泽
             * quiz_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/cab650f9c71dee1571e112eeabcca7f2.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/9813ebc9fd1a12718cc0a80f9a60680a.jpeg","http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/3de6673f98c972e908950e4f5a69fb51.jpeg"]
             * head : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/c24431814ce23198ede8889831b68f2d.jpeg
             * section_name : 第三章  绩效管理
             * creates_time : 4天前
             */

            private String id;
            private String video_time;
            private String quiz;
            private String username;
            private String head;
            private String section_name;
            private String creates_time;
            private List<String> quiz_image;

        }

        public static class ReplyBean {
            /**
             * reply_quiz : 是的你好回家就离开家颗粒剂离开家卢克就看拉近了看就开了大撒尽量快点撒就
             * reply_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20191017/a46c558e4cf9d6b09beb7507fcc80b9f.jpeg"]
             * head_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190612/63c285df69253f2854101c0775cc1e2d.jpeg
             * reply_user_name : 超管
             * repls_time : 4天前
             */

            private String reply_quiz;
            private String head_img;
            private String reply_user_name;
            private String repls_time;
            private List<String> reply_image;

        }
    }
}
