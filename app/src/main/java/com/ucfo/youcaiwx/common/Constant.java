package com.ucfo.youcaiwx.common;

import com.ucfo.youcaiwx.BuildConfig;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:38
 * Email:2911743255@qq.com
 * ClassName: Constant
 * Description:恒量
 */
public class Constant {

    private Constant() {
    }

    //---------------------------------------------TODO common  start-----------------------------------------------//
    //App测试模式控制字段
    public static final boolean ISTEST_ENVIRONMENT = BuildConfig.DEBUG;
    //BuildConfig.DEBUG

    //客服电话
    public static final String SERVICE_NUM = "4006665318";
    //TODO 微信APPID
    public static final String WEIXIN_KEY = "wx55d839e60fb4b35f";
    //TODO 微信secret
    //public static final String WEIXIN_SECRET = "b18dd689f27360c730a2280b3b7132fd";
    public static final String WEIXIN_SECRET = "c56a1337d1efc66663e377ea0da7e7fe";
    //TODO 微信登录参数
    public static final String WEIXIN_PARAMS1 = "snsapi_userinfo";
    //TODO 微信登录参数
    public static final String WEIXIN_PARAMS2 = "wechat_youcailogin";

    //TODO buglyAppID
    public static final String BUGLY_ID = "507d69a881";
    //TODO bugly标签_视频播放标签
    public static final int BUGLY_TAG_VIDEO = 127217;
    //TODO bugly标签_习题练习标签
    public static final int BUGLY_TAG_EXERCISE = 127218;
    //TODO bugly标签_提交订单标签
    public static final int BUGLY_TAG_ORDER = 127220;
    //TODO bugly标签_订单支付标签
    public static final int BUGLY_TAG_PAY = 127221;
    //TODO bugly标签_离线瞎子啊标签
    public static final int BUGLY_TAG_CACHE = 137850;

    //TODO 阿里云视频安全下载所需安全文件的离线解密私钥
    public static final String ALIYUN_DECRYPT = "CMAyoucai2020999999";

    //TODO 友盟平台唯一标识
    public static final String UMENG_APPKEY = "5d521d4e3fc195b523000353";
    //TODO 友盟平台客户端推送密钥
    public static final String UMENG_MESSAGE_SCRECT = "d9a3baa0dff24082751e60940cdb94f3";
    //友盟平台客户端渠道(bugly也是)
    public static final String UMENG_CHANNEL = "YingYongBao";
    //TODO 友盟平台自定义packagename
    public static final String UMENG_PACKAGE_NAME = "com.ucfo.youcaiwx";

    //TODO AES加密key
    public static final String AES_KEY = "1234567812345678";
    //TODO AES加密向量
    public static final String AES_IV = "1234567812345678";

    //通知栏消息: 资讯消息
    public static final String UMENG_MESSAGE_INFORMATION = "newsMessage";
    //通知栏消息: 直播消息
    public static final String UMENG_MESSAGE_LIVE = "liveMessage";
    //通知栏消息: 消息公告
    public static final String UMENG_MESSAGE_NOTICE = "systemMessage";
    //通知栏消息: 订单消息
    public static final String UMENG_MESSAGE_ORDERFORM = "orderMessage";
    //通知栏消息: 课程答疑
    public static final String UMENG_MESSAGE_COURSEANSWER = "courseMessage";
    //通知栏消息: 题库答疑
    public static final String UMENG_MESSAGE_QUESTIONANSWER = "queMessage";
    //强制下线
    public static final String UMENG_MESSAGE_FORCE = "freezeMessage";

    //H5链接
    public static final String WEB_URL = "web_link";
    //H5标题
    public static final String WEB_TITLE = "web_title";
    public static final String DATA = "data";
    public static final String MESSAGE = "msg";

    public static final String UTF_8 = "UTF-8";
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String INDEX = "index";
    public static final String TYPE = "type";
    public static final String TYPE_ID = "type_id";
    public static final String CONTENT = "content";
    public static final String VALUE = "value";
    public static final String VERSION_CODE = "VersionCode";
    public static final String versioncode = "versioncode";
    public static final String APP_KEY = "AppKey";

    //提问问题最大图片选择数量
    public static final int MAX_IMAGECOUNT = 3;
    //输入框最小输入字数
    public static final int QUESTION_MINICOUNT = 5;
    //提问问题最大输入字数
    public static final int QUESTION_MAX_EDITTEXT = 200;
    //投诉最大字数
    public static final int COMPLAIN_MAX_COOUNT = 250;
    //投诉类型最大选择数量
    public static final int COMPLAIN_MAX_SELECT = 1;
    //---------------------------------------------TODO common  end-----------------------------------------------//


    /*******************************************************TODO 再丑也要注意的分割线*******************************************************/
    public static final String ROOT_DIR = "/youcai/";
    //压缩图片存储地址
    public static final String LUBAN_PATH = ROOT_DIR + "image/";
    //离线缓存地址
    public static final String CACHE_PATH = ROOT_DIR + "Download/";
    //pdf文件存放地址(API19以下存储位置)
    public static final String PDF_PATH = ROOT_DIR + "pdf/";
    //阿里加密文件存放地址
    public static final String ENCRYPTED_PATH = "/aliyun/encryptedApp.dat";
    //阿里播放器边下边缓存地址
    public static final String PLAYER_CACHE_PATH = ROOT_DIR + "aliyunVodPlayer_cache";
    //fileprovider
    public static final String AUTHORITY = "PhotoProvider";
    /*******************************************************TODO 再丑也要注意的分割线*******************************************************/

    //---------------------------------------------TODO 登录注册  start-----------------------------------------------//
    //第一次登陆
    public static final String FIRST_LOGIN = "first_login";
    //是否阅读用户协议
    public static final String WHETHER_READ_AGREEMENT = "ReadAgreement";
    //用户是否登录
    public static final String LOGIN_STATUS = "is_login";
    public static final String STATE = "state";
    public static final String HOME_CACHE = "home_cache";//首页底部导航栏标签
    public static final String TYPE_FORGET = "type_forget";//忘记密码
    public static final String TYPE_COMPLET = "type_complet";//完善信息
    public static final String USER_ID = "user_id";//用户ID
    public static final String USER_NAME = "username";//用户昵称
    public static final String ID = "id";
    public static final String UPPER_ID = "Id";
    public static final String USER_STATUS = "status";//用户状态
    public static final String MOBILE = "mobile";//手机号
    public static final String PASSWORD = "password";//密码
    public static final String DEVICES = "devices";//设备ID
    public static final String SEX = "sex";//性别
    public static final String HEAD = "head";//头像
    public static final String DEVICES_TOKEN = "user_devices";//友盟设备ID
    public static final String UNIONID = "unionId";//微信唯一ID
    public static final String OPENID = "openid";//openid
    public static final String PASSWORD_CONFIRM = "pass";//确认密码
    public static final String SMS_CODE = "mobilecode";//短信验证码
    public static final String SMS_STATE = "state";//短信验证码  	TODO 1注册2登陆
    //短信倒计时时间
    public static final int SMS_SECOND = 60 * 1000;
    //---------------------------------------------TODO 登录注册  end-----------------------------------------------//


    //---------------------------------------------TODO 课程  start-----------------------------------------------//
    //免费试看时间(单位: 秒)
    public static final int FREE_TIME = 3 * 60;
    //socket消息发送间隔时间(单位: 毫秒)
    public static final int SOCKET_TIME = 30 * 1000;
    //播放器菜单隐藏时间(单位 : 毫秒)
    public static final int DELAY_TIME = 5 * 1000;
    //累计学习指定时间领取积分(单位: 秒)
    public static final int INTEGRAL_TIME = 30 * 60;


    //课程已购买状态码  1: 已购买 2: 未购买
    public static final int HAVED_BUY = 1;
    //课程是否是正课状态码  1: 正课 2: 非正课
    public static final int COURSE_UNCON = 1;


    //当日看课累计时间字段
    public static final String INTEGRAL_COUNTER = "integral_counter";

    public static final String CLASS_ID = "class_id";//课程筛选分类id
    public static final String COURSE_PACKAGE_ID = "course_PackageId";//课程包的ID
    public static final String COURSE_COVER_IMAGE = "course_coverimage";//课程包封面
    public static final String COURSE_UN_CON = "course_un_con";//课程包正课区分  是否是正课1正课2非正课
    public static final String COURSE_SOURCE = "course_source";//进入课程播放页的来源
    public static final String COURSE_BUY_STATE = "course_buy_state";//课程包用户是否购买
    public static final String COURSE_VIDEOID = "VideoId";//阿里播放视频的vid
    public static final String COURSE_PRICE = "price";//课程包价格

    public static final String TYPE_COURSE_ASK = "type_courseask";//课程提问
    public static final String TYPE_QUESTION_ASK = "type_questionask";//题库提问
    //课程答疑,题库答疑追问
    public static final String TYPE_TRACE = "type_trace";
    public static final String ANSWER_TYPE = "answer_type";//追问类型
    public static final String ANSWER_TYPE_COURSE = "1";//课程追问
    public static final String ANSWER_TYPE_QUESTION = "2";//题库追问

    public static final String TYPE_FEEDBACK = "type_feedback";//意见反馈
    public static final String PACKAGE_ID = "package_id";//课程包ID
    public static final String IS_LIVE = "is_live";//直播
    public static final String SECTION_ID = "section_id";//章ID
    public static final String SECTION_NAME = "section_name";//章名称
    public static final String COURSE_ID = "course_id";//课程ID
    public static final String VIDEO_ID = "video_id";//小节视频ID
    public static final String ANSWER_ID = "answer_id";//问答列表ID
    public static final String VIDEO_TIME = "video_time";//视频提问时间
    public static final String VIDEO_NAME = "video_name";//视频标题
    public static final String VIDEO_TITLE = "video_title";//视频标题
    public static final String PLAN_ID = "plan_id";//学习计划ID
    public static final String DAYS = "days";//学习计划天数
    //视频播放源
    public static final String LOCAL_CACHE = "localCache";//本地缓存
    public static final String LOCAL_PLAYURL = "localPlayUrl";//本地视频播放地址
    public static final String COLLECTION = "collection";//收藏
    public static final String WATCH_RECORD = "watch_record";//观看记录
    public static final String WATCH_LEARNPLAN = "watch_learnPlan";//学习计划
    public static final String WATCH_ANSWERDETAILED = "watch_answerDetailed";//课程答疑查看视频
    //WIFI观看处理
    public static final String DOWNLOAD_WIFI = "download_wifi";
    public static final String LOOK_WIFI = "download_look";
    //---------------------------------------------TODO 课程  start-----------------------------------------------//


    //--------------------------------------------------------TODO 题库  start----------------------------------------------------------//
    public static final String SUBJECT_ID = "subject_id";//选中题库的ID
    public static final String PAGE = "page";//分页加载
    public static final String LIMIT = "limit";//分页加载
    public static final String KNOB_ID = "knob_id";//用于获取三级列表的所谓的节的ID
    public static final String KNOW_ID = "know_id";//知识点三级列表的ID
    public static final String MOCK_ID = "mock_id";//组卷模考的试卷ID
    public static final String PAPER_ID = "paper_id";//阶段测试试卷ID
    public static final String NUMBER = "number";//做题数
    public static final String STATUS = "status";//做题状态
    public static final String NUM = "num";//获取题目数
    public static final String USED_TIME = "used_time";//用时时间
    public static final String QUESTION_CONTENT = "question_content";//题目json信息
    public static final String QUESTION_ID = "question_id";//题目ID
    public static final String TITLE = "defaultTitle";//标题
    public static final String URL = "defaultUrl";//链接
    public static final String OVER = "over";
    public static final String ANALYSIS_TYPE = "analysisType";//解析类型
    public static final String ANSEWR_ID = "answer_id";//题目ID
    public static final String BroadcastReceiver_TONEXT = "com.leyikao.jumptonext";//广播跳转下一页
    public static final String BroadcastReceiver_TOPAGE = "com.leyikao.jumptopage";//广播跳转指定页面
    //--------------------------------------------------------TODO 题库  end----------------------------------------------------------//


    //---------------------------------------------TODO int related to questionbank start----------------------------------------//
    //TODO 题库类型,1单选2论述题
    public static final String PAPER_TYPE = "paper_type";
    //TODO 做题模式
    public static final String EXERCISE_TYPE = "exercise_type";
    //TODO E:正常考试模式
    public static final String EXERCISE_E = "exam";
    //TODO P:知识点练习模式
    public static final String EXERCISE_P = "practice";
    //TODO A:解析模式(严格来说,弄混了,这个其实是选择题的解析模式)
    public static final String EXERCISE_A = "analysis";
    //TODO D:论述题模式 (论述题模式的话就是diss_analysis)
    public static final String EXERCISE_D = "discuss";
    //TODO 继续做题(创造101)
    public static final String CONTINUE_PLATE = "continue_plate";

    //6大板块id  TODO 1知识点练习,2阶段测试,3论述题自测,4错题智能练习,5自主练习,6组卷模考
    public static final String PLATE_ID = "plate_id";
    //板块1-6为后台规定
    //TODO 1: 知识点练习
    public static final int PLATE_1 = 1;
    //TODO 2: 阶段测试
    public static final int PLATE_2 = 2;
    //TODO 3: 论述题自测
    public static final int PLATE_3 = 3;
    //TODO 4: 错题智能练习,高频错题
    public static final int PLATE_4 = 4;
    //TODO 5: 自助练习
    public static final int PLATE_5 = 5;
    //TODO 6: 组卷模考
    public static final int PLATE_6 = 6;

    //custom
    //TODO 0: 0元体验课
    public static final int PLATE_0 = 0;
    //TODO 7: 错题中心   或者查看解析
    public static final int PLATE_7 = 7;
    //TODO 8: 错题中心重新做题
    public static final int PLATE_8 = 8;
    //TODO 9: 6大板块错题解析
    public static final int PLATE_9 = 9;
    //TODO 10: 错题中心错题解析
    public static final int PLATE_10 = 10;
    //TODO 11: 6大板块继续做题
    public static final int PLATE_11 = 11;
    //TODO 12: 答题记录查看论述题解析
    public static final int PLATE_12 = 12;
    //TODO 13: 我的收藏查看试题,有论述也有选择
    public static final int PLATE_13 = 13;
    //TODO 14:学习计划获取题目
    public static final int PLATE_14 = 14;
    //TODO 15:学习计划解析
    public static final int PLATE_15 = 15;
    //TODO 16:试题详情
    public static final int PLATE_16 = 16;
    //---------------------------------------------TODO int related to questionbank  end----------------------------------------//


    //---------------------------------------------TODO 个人中心 start-----------------------------------------------//
    public static final String NICKNAME = "nickname";
    public static final String IS_DEFAULT = "is_default";
    public static final String TELEPHONE = "telephone";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_NAME = "consignee";
    public static final String ADDRESS_ID = "address_id";
    public static final String ORDER_NUM = "order_num";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String MINE_ANSWER = "mine_answer";//我的答疑
    public static final String MESSAGE_ANSWER = "message_answer";//消息中心
    public static final String QUESTION_ANSWER = "question_answer";//题库答疑列表

    public static final String DOWNLOADVID_LIST = "downloadvidList";//下载的vid列表
    public static final String DOWNLOADINFO_LIST = "downloadinfoList";//下载的列表
    public static final String LOCAL_STAIRLIST = "local_list";//本地视频一级存储列表
    //---------------------------------------------TODO 个人中心 end-----------------------------------------------//


    //---------------------------------------------TODO 支付 start-----------------------------------------------//
    public static final String PAY_EDIT = "pay_edit";
    public static final String PAY_COUPONPRICE = "pay_couponPrice";
    public static final String PAY_COUPONID = "pay_couponId";
    //地址选择请求码
    public static final int REQUEST_ADDRESS = 10000;
    //优惠券选择请求码
    public static final int REQUEST_COUPON = 10001;
    //---------------------------------------------TODO 支付 end-----------------------------------------------//


    //---------------------------------------------TODO 消息中心 start-----------------------------------------------//
    public static final String MESSAGE_ID = "message_id";
    //---------------------------------------------TODO 消息中心 end-----------------------------------------------//


    //---------------------------------------------TODO 积分 start-----------------------------------------------//
    public static final String INTEGRAL = "integral";
    public static final String PRODUCT_ID = "good_id";
    //App下载页分享
    public static final int INTEGRAL_SHARE = 3;
    //App日签分享
    public static final int INTEGRAL_SIGN = 4;
    //在线学习30分钟
    public static final int INTEGRAL_STUDY = 2;
    //商品列表
    public static final String INTEGRAL_TYPE_PRODUCT = "product";
    //优惠券列表
    public static final String INTEGRAL_TYPE_COUPON = "coupon";
    //积分订单下单完成数据
    public static final String INTEGRAL_ADDORDERDETAIAL = "integral_orderDetail";
    //---------------------------------------------TODO 积分 end -----------------------------------------------//
}
