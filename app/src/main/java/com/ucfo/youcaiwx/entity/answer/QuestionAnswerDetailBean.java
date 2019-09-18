package com.ucfo.youcaiwx.entity.answer;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-5-31.  上午 11:26
 * FileName: QuestionAnswerDetailBean
 * Description:TODO 题库答疑详情
 */
public class QuestionAnswerDetailBean {
    /**
     * code : 200
     * msg : 操作成功
     * data : {"question_keyword":"朱诺公司是生产手机和笔记本电脑的一家企业，其高层管理正在对公司业务进行SWOT（优势、劣势、机会和威胁）分析。公司的副总裁安德鲁·哈德逊将公司尖端研发部门列为优势之一，因为它可以帮助设计高质量产品。然而， 营销经理梅兰妮·哈里斯认为研发部门是一个劣势而不是优势。下列哪一个选项支持梅兰妮的观点? ","data":{"Id":8,"username":"旧城凉","quiz":"Hahahhaha","head":"https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190530/bc3c00dd220a8c99862aa05fe49c431c.jpeg"],"create_times":"19小时前","know_name":["总论","成本度量和计量基本概念","案例-税收成本和会计成本的区别","案例-成本和意识","案例-复杂性（1）","风险管理流程-风险监控","风险管理流程-风险评估-分析方法"]},"reply":{"reply_image":[],"reply_quiz":null,"reply_user_name":null,"head_img":null,"reply_times":"一个月前"}}
     */

    private int code;
    private String msg;
    private DataBeanX data;

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

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX {
        /**
         * question_keyword : 朱诺公司是生产手机和笔记本电脑的一家企业，其高层管理正在对公司业务进行SWOT（优势、劣势、机会和威胁）分析。公司的副总裁安德鲁·哈德逊将公司尖端研发部门列为优势之一，因为它可以帮助设计高质量产品。然而， 营销经理梅兰妮·哈里斯认为研发部门是一个劣势而不是优势。下列哪一个选项支持梅兰妮的观点?
         * data : {"Id":8,"username":"旧城凉","quiz":"Hahahhaha","head":"https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132","quiz_image":["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190530/bc3c00dd220a8c99862aa05fe49c431c.jpeg"],"create_times":"19小时前","know_name":["总论","成本度量和计量基本概念","案例-税收成本和会计成本的区别","案例-成本和意识","案例-复杂性（1）","风险管理流程-风险监控","风险管理流程-风险评估-分析方法"]}
         * reply : {"reply_image":[],"reply_quiz":null,"reply_user_name":null,"head_img":null,"reply_times":"一个月前"}
         */

        private String question_keyword;
        private DataBean data;
        private ReplyBean reply;

        public String getQuestion_keyword() {
            return question_keyword == null ? "" : question_keyword;
        }

        public void setQuestion_keyword(String question_keyword) {
            this.question_keyword = question_keyword;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public ReplyBean getReply() {
            return reply;
        }

        public void setReply(ReplyBean reply) {
            this.reply = reply;
        }

        public static class DataBean {
            /**
             * Id : 8
             * username : 旧城凉
             * quiz : Hahahhaha
             * head : https://thirdwx.qlogo.cn/mmopen/vi_32/ibjTF6xFeAfEHaDnMjviaaqV3HHeybbV3uJpibcKtD1iaMdFvKkQRPtzA0K2m0NcF2YgOgovY3pWJLULItgRU3u0lQ/132
             * quiz_image : ["http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190530/bc3c00dd220a8c99862aa05fe49c431c.jpeg"]
             * create_times : 19小时前
             * know_name : ["总论","成本度量和计量基本概念","案例-税收成本和会计成本的区别","案例-成本和意识","案例-复杂性（1）","风险管理流程-风险监控","风险管理流程-风险评估-分析方法"]
             */

            private String Id;
            private String username;
            private String quiz;
            private String head;
            private String create_times;
            private List<String> quiz_image;
            private List<String> know_name;

            public String getId() {
                return Id == null ? "" : Id;
            }

            public void setId(String id) {
                Id = id;
            }

            public String getUsername() {
                return username == null ? "" : username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getQuiz() {
                return quiz == null ? "" : quiz;
            }

            public void setQuiz(String quiz) {
                this.quiz = quiz;
            }

            public String getHead() {
                return head == null ? "" : head;
            }

            public void setHead(String head) {
                this.head = head;
            }

            public String getCreate_times() {
                return create_times == null ? "" : create_times;
            }

            public void setCreate_times(String create_times) {
                this.create_times = create_times;
            }

            public List<String> getQuiz_image() {
                if (quiz_image == null) {
                    return new ArrayList<>();
                }
                return quiz_image;
            }

            public void setQuiz_image(List<String> quiz_image) {
                this.quiz_image = quiz_image;
            }

            public List<String> getKnow_name() {
                if (know_name == null) {
                    return new ArrayList<>();
                }
                return know_name;
            }

            public void setKnow_name(List<String> know_name) {
                this.know_name = know_name;
            }
        }

        public static class ReplyBean {
            /**
             * reply_image : []
             * reply_quiz : null
             * reply_user_name : null
             * head_img : null
             * reply_times : 一个月前
             */

            private String reply_quiz;
            private String reply_user_name;
            private String head_img;
            private String reply_times;
            private List<String> reply_image;

            public String getReply_quiz() {
                return reply_quiz == null ? "" : reply_quiz;
            }

            public void setReply_quiz(String reply_quiz) {
                this.reply_quiz = reply_quiz;
            }

            public String getReply_user_name() {
                return reply_user_name == null ? "" : reply_user_name;
            }

            public void setReply_user_name(String reply_user_name) {
                this.reply_user_name = reply_user_name;
            }

            public String getHead_img() {
                return head_img == null ? "" : head_img;
            }

            public void setHead_img(String head_img) {
                this.head_img = head_img;
            }

            public String getReply_times() {
                return reply_times == null ? "" : reply_times;
            }

            public void setReply_times(String reply_times) {
                this.reply_times = reply_times;
            }

            public List<String> getReply_image() {
                if (reply_image == null) {
                    return new ArrayList<>();
                }
                return reply_image;
            }

            public void setReply_image(List<String> reply_image) {
                this.reply_image = reply_image;
            }
        }
    }
}
