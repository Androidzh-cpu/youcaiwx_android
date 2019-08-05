package com.ucfo.youcai.entity.pay;

/**
 * Author: AND
 * Time: 2019-8-5.  下午 3:36
 * Package: com.ucfo.youcai.entity.pay
 * FileName: OrderFormDetailBean
 * Description:TODO 订单详情
 */
public class OrderFormDetailBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"coupon_num":1,"packages":{"package_id":1,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","name":"中文p1高清网课","view_class":1,"study_date":"2019-08-01 16:00:00","study_days":180,"teacher_id":"4,5","price":"23800.00","teacher_name":"吴宁,姚立","validity":-3}}
     */

    private int code;
    private String msg;
    private DataBean data;


    public static class DataBean {
        /**
         * coupon_num : 1
         * packages : {"package_id":1,"app_img":"http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg","name":"中文p1高清网课","view_class":1,"study_date":"2019-08-01 16:00:00","study_days":180,"teacher_id":"4,5","price":"23800.00","teacher_name":"吴宁,姚立","validity":-3}
         */

        private int coupon_num;
        private PackagesBean packages;

        public static class PackagesBean {
            /**
             * package_id : 1
             * app_img : http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190613/f2b3378f17e99a3ee4e7a1334c1a5aa1.jpeg
             * name : 中文p1高清网课
             * view_class : 1
             * study_date : 2019-08-01 16:00:00
             * study_days : 180
             * teacher_id : 4,5
             * price : 23800.00
             * teacher_name : 吴宁,姚立
             * validity : -3
             */

            private int package_id;
            private String app_img;
            private String name;
            private int view_class;
            private String study_date;
            private int study_days;
            private String teacher_id;
            private String price;
            private String teacher_name;
            private int validity;

        }
    }
}
