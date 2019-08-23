package com.ucfo.youcaiwx.common;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:38
 * Email:2911743255@qq.com
 * ClassName: Constant
 * Description:TODO 字段常量
 */
public class Constant {
    //---------------------------------------------TODO 基础变量-----------------------------------------------//
    //是否显示打印日志
    public static final boolean ISTEST_ENVIRONMENT = true;
    //客服电话
    public static final String SERVICE_NUM = "4006665318";
    public static final String UTF_8 = "UTF-8";
    public static final String CODE = "code";
    public static final String MSG = "msg";
    public static final String INDEX = "index";
    public static final String TYPE = "type";
    public static final String CONTENT = "content";
    public static final String VALUE = "value";
    public static final String VERSION_CODE = "VersionCode";
    public static final String versioncode = "versioncode";
    public static final String APP_KEY = "AppKey";
    //TODO 微信APPID
    public static final String WEIXIN_KEY = "wx55d839e60fb4b35f";
    //TODO 微信secret
    public static final String WEIXIN_SECRET = "b18dd689f27360c730a2280b3b7132fd";
    //TODO 微信登录参数
    public static final String WEIXIN_PARAMS1 = "snsapi_userinfo";
    //TODO 微信登录参数
    public static final String WEIXIN_PARAMS2 = "wechat_youcailogin";
    //AES加密key
    public static final String AES_KEY = "1234567812345678";
    //AES加密向量
    public static final String AES_IV = "1234567812345678";
    //TODO 阿里云视频安全下载所需安全文件的离线解密私钥
    public static final String ALIYUN_DECRYPT = "CMAyoucai2020999999";
    //友盟平台唯一标识
    public static final String UMENG_APPKEY = "5d521d4e3fc195b523000353";
    //友盟平台客户端推送密钥
    public static final String UMENG_MESSAGE_SCRECT = "d9a3baa0dff24082751e60940cdb94f3";
    //友盟平台客户端渠道
    public static final String UMENG_CHANNEL = "umeng";
    //友盟平台自定义packagename
    public static final String UMENG_PACKAGE_NAME = "com.ucfo.youcaiwx";
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
    //H5链接
    public static final String WEB_URL = "url";
    //H5标题
    public static final String WEB_TITLE = "web_title";
    public static final String DATA = "data";
    /*******************************************************TODO 再丑也要注意的分割线*******************************************************/
    //压缩图片地址
    public static final String LUBAN_PATH = "/youcai/image/";
    //离线缓存地址        TODO 地址莫篡改,统一都在跟目录的youcai文件夹下处理本应用的数据
    public static final String CACHE_PATH = "/youcai/Download/";
    //pdf文件存放地址
    public static final String PDF_PATH = "/youcai/pdf/";
    //加密文件存放地址
    public static final String ENCRYPTED_PATH = "/aliyun/encryptedApp.dat";
    /*******************************************************TODO 再丑也要注意的分割线*******************************************************/
    //---------------------------------------------登录-----------------------------------------------//
    public static final String FIRST_LOGIN = "first_login";//第一次登陆
    public static final String LOGIN_STATUS = "is_login";//用户是否登录
    public static final String STATE = "state";//首页底部导航栏标签
    public static final String HOME_CACHE = "home_cache";//首页底部导航栏标签
    public static final String TYPE_FORGET = "type_forget";//忘记密码
    public static final String TYPE_COMPLET = "type_complet";//完善信息
    public static final String USER_ID = "user_id";//用户ID
    public static final String ID = "id";
    public static final String UPPER_ID = "Id";
    public static final String USER_STATUS = "status";//用户状态
    public static final String MOBILE = "mobile";//手机号
    public static final String PASSWORD = "password";//密码
    public static final String DEVICES = "devices";//设备ID
    public static final String DEVICES_TOKEN = "user_devices";//友盟设备ID
    public static final String UNIONID = "unionId";//微信唯一ID
    public static final String OPENID = "openid";//openid
    public static final String PASSWORD_CONFIRM = "pass";//确认密码
    public static final String SMS_CODE = "mobilecode";//短信验证码
    public static final String SMS_STATE = "state";//短信验证码  	TODO 1注册2登陆

    //---------------------------------------------TODO 课程-----------------------------------------------//
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
    //免费试看时间
    public static final int FREE_TIME = 3 * 60;
    //视频播放源
    public static final String LOCAL_CACHE = "localCache";//本地缓存
    public static final String LOCAL_PLAYURL = "localPlayUrl";//本地视频播放地址
    public static final String COLLECTION = "collection";//收藏
    public static final String WATCH_RECORD = "watch_record";//观看记录
    public static final String WATCH_LEARNPLAN = "watch_learnPlan";//学习计划
    //WIFI观看处理
    public static final String DOWNLOAD_WIFI = "download_wifi";
    public static final String LOOK_WIFI = "download_look";
    //--------------------------------------------------------题库----------------------------------------------------------//
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
    public static final String TITLE = "title";//标题
    public static final String URL = "url";//链接
    public static final String OVER = "over";
    public static final String ANALYSIS_TYPE = "analysisType";//解析类型
    public static final String ANSEWR_ID = "answer_id";//题目ID
    public static final String BroadcastReceiver_TONEXT = "com.leyikao.jumptonext";//广播跳转下一页
    public static final String BroadcastReceiver_TOPAGE = "com.leyikao.jumptopage";//广播跳转指定页面

    //---------------------------------------------TODO int related to questionbank----------------------------------------//
    public static final String PAPER_TYPE = "paper_type";//TODO 题库类型,1单选2论述题
    public static final String EXERCISE_TYPE = "exercise_type";//TODO 做题模式
    public static final String EXERCISE_E = "exam";//TODO E:正常考试模式
    public static final String EXERCISE_P = "practice";//TODO P:知识点练习模式
    public static final String EXERCISE_A = "analysis";//TODO A:解析模式(严格来说,弄混了,这个其实是选择题的解析模式)
    public static final String EXERCISE_D = "discuss";//TODO D:论述题模式 (论述题模式的话就是diss_analysis)
    public static final String CONTINUE_PLATE = "continue_plate";//TODO 继续做题(创造101)
    public static final String PLATE_ID = "plate_id";//6大板块id  TODO 1知识点练习,2阶段测试,3论述题自测,4错题智能练习,5自主练习,6组卷模考
    //板块1-6为后台规定
    public static final int PLATE_1 = 1;//TODO 1: 知识点练习
    public static final int PLATE_2 = 2;//TODO 2: 阶段测试
    public static final int PLATE_3 = 3;//TODO 3: 论述题自测
    public static final int PLATE_4 = 4;//TODO 4: 错题智能练习,高频错题
    public static final int PLATE_5 = 5;//TODO 5: 自助练习
    public static final int PLATE_6 = 6;//TODO 6: 组卷模考
    //这些板块用于区分庞杂的题库模块
    public static final int PLATE_0 = 0;//TODO 0: 0元体验课
    public static final int PLATE_7 = 7;//TODO 7: 错题中心   或者查看解析
    public static final int PLATE_8 = 8;//TODO 8: 错题中心的重新做题
    public static final int PLATE_9 = 9;//TODO 9: 6大板块错题解析
    public static final int PLATE_10 = 10;//TODO 10: 错题中心错题解析
    public static final int PLATE_11 = 11;//TODO 11: 6大板块继续做题
    public static final int PLATE_12 = 12;//TODO 12: 答题记录查看论述题解析
    public static final int PLATE_13 = 13;//TODO 13: 我的收藏查看试题,有论述也有选择
    public static final int PLATE_14 = 14;//TODO 14:学习计划获取题目
    public static final int PLATE_15 = 15;//TODO 15:学习计划解析


    //---------------------------------------------个人中心-----------------------------------------------//
    public static final String NICKNAME = "nickname";
    public static final String IS_DEFAULT = "is_default";
    public static final String TELEPHONE = "telephone";
    public static final String ADDRESS = "address";
    public static final String ADDRESS_NAME = "consignee";
    public static final String ADDRESS_ID = "address_id";
    public static final String ORDER_NUM = "order_num";
    public static final String TEACHER_NAME = "teacher_name";
    public static final String MINE_ANSWER = "mine_answer";
    public static final String DOWNLOADVID_LIST = "downloadvidList";//下载的vid列表
    public static final String DOWNLOADINFO_LIST = "downloadinfoList";//下载的列表
    public static final String LOCAL_STAIRLIST = "local_list";//本地视频一级存储列表
    //---------------------------------------------支付-----------------------------------------------//
    public static final String PAY_EDIT = "pay_edit";
    public static final String PAY_COUPONPRICE = "pay_couponPrice";
    public static final String PAY_COUPONID = "pay_couponId";
    //---------------------------------------------消息中心-----------------------------------------------//
    public static final String MESSAGE_ID = "message_id";


}
