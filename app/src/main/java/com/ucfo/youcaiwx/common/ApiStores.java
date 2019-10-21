package com.ucfo.youcaiwx.common;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:12
 * Email:2911743255@qq.com
 * ClassName: ApiStores
 * Detail:TODO 公共类的url，网络地址
 * 瓦力打包命令 发布前一定要切换到正式版本  gradlew clean assembleReleaseChannels
 */
public class ApiStores {

    public static final String BASE_URL = "http://ycapi.youcaiwx.com/apps/";  //开发内网测试环境
    public static final String SOCKET = "ws://ycapi.youcaiwx.com:2346";//socket地址
    public static final String FILE_UPLOAD = "http://ycapi.youcaiwx.com/upload/Index/uploadImage";//上传文件
    public static final String VERSION_UPDATE = "http://ycapi.youcaiwx.com/apps/Version/upAndroid";//版本更新
    //public static final String TEMPORARYLIVE = "https://w.url.cn/s/AkkfsXi";//临时直播地址
    //public static final String TEMPORARYLIVE = "http://www.youcaiwx.com/html/DirectSeeding/Index/index.html";//临时直播地址
    //public static final String TEMPORARYLIVE = "http://m.youcaiwx.com/zhibo";//临时直播地址
    public static final String TEMPORARYLIVE = "http://ycfx.youcaiwx.com/static/Video.html?user_id=888888";//临时直播地址
    public static final String TEMPORARYNEWS = "http://m.ucwx.com.cn/news";//临时资讯地址
    public static final String TEMPORARFACE = "http://m.ucfo.com.cn/mskc/";//临时面授地址
    public static final String TEMPORARACTIVE = "http://m.ucfo.com.cn/yqh-wap/20190921/";//临时活动地址
    public static final String APP_DOWNLOAD_URL = "http://www.youcaiwx.com/html/activity/activity.html";//App应用商店下载页
    public static final String LOGO = "http://www.youcaiwx.com/Public/Uploads/newtopicpics/2017-12-26/5a41b418a2e32.png";
    public static final String ACTIVEEVENT = "http://ycapi.youcaiwx.com/web/Register/thickness";


    //-----------------------------------------TODO 首页--------------------------------------------------------------//
    public static final String HOME_API = BASE_URL + "First/newInformation";//首页
    public static final String DOUBAN_API = "https://api.douban.com/v2/movie/top250";//豆瓣TOP250好评
    public static final String MESSAGE_CENTER_HOME = BASE_URL + "Message/indexMessage";//消息首页
    public static final String MESSAGE_CENTER_NOTICE = BASE_URL + "Message/systeMessage";//网校公告列表
    public static final String MESSAGE_CENTER_HAVEDREAD = BASE_URL + "Message/read";//消息已读
    //资讯列表
    public static final String MESSAGE_INFORMATIONLIST = BASE_URL + "news/newsList";

    //-----------------------------------------TODO 登录注册业务------------------------------------------------------------------------------//
    public static final String LOGIN_ACCOUMENT = BASE_URL + "Login/accountLogin";//账号登录
    public static final String LOGIN_EXITLOGIN = BASE_URL + "Login/outlogin";//账号退出登录
    public static final String LOGIN_SMSLOGIN = BASE_URL + "Login/quickLogin";//短信登录
    public static final String LOGIN_WECHEATLOGIN = BASE_URL + "Login/wxlogin";//微信登录
    public static final String LOGIN_WECHEATLOGIN_COMPLETED = BASE_URL + "Login/wxCominformation";//微信登录完善信息
    public static final String REGISTER_ACCOUMENT = BASE_URL + "Register/webReg";//账号注册
    public static final String RESET_PASSWORD = BASE_URL + "Login/resetPaw";//账号密码重置
    public static final String REGISTER_GETSMSCODE = BASE_URL + "Register/getSmsCode";//注册获取短信验证码
    public static final String REGISTER_GETVOICECODE = BASE_URL + "Register/voice";//注册获取语音验证码
    public static final String FORGET_GETSMSCODE = BASE_URL + "Login/forgetPaw";//忘记密码获取验证码
    public static final String USER_REGISTER_ARGUMENT = "http://www.youcaiwx.com/html/app/register.html";//注册协议

    //-----------------------------------------TODO 课程业务------------------------------------------------------------------------------//
    public static final String COURSE_SUBJECTS = BASE_URL + "Course/subjects";//课程列表-条件筛选科目
    public static final String COURSE_COURSELIST = BASE_URL + "Course/courseList";//外层课程列表
    public static final String COURSE_INTORDUCTION = BASE_URL + "Course/courseIntroduction";//课程简介
    public static final String COURSE_GETVIDEO_CREDENTIALS = BASE_URL + "Course/videoCredentials";//获取视频凭证
    public static final String COURSE_GETVIDEO_STS = BASE_URL + "Course/getToken";//获取下载STS
    public static final String COURSE_GETCOURSEDIR = BASE_URL + "Course/courseoutline";//获取视频目录
    public static final String COURSE_VIDEOCOLLECT = BASE_URL + "Course/collection";//视频收藏
    //-----------------------------------------TODO 课程问答业务------------------------------------------------------------------------------//
    public static final String ANSWER_GETANSWERLIST = BASE_URL + "Course/answerList";//获取问答列表
    public static final String ANSWER_GETANSWERDETAIL = BASE_URL + "Course/answerDetails";//获取问答详情
    public static final String ANSWER_ASKQUESTION = BASE_URL + "Course/answerSub";//提问问题

    //-----------------------------------------TODO 题库做题考试------------------------------------------------------------------------------//
    public static final String QUESTION_GETPROJECT = BASE_URL + "Question/getProject";//获取题库展示专业
    public static final String QUESTION_GETPROJECTINFO = BASE_URL + "Question/questionIndex";//获取用户对应题库信息
    public static final String QUESTION_GETQUSTIONONRECORD = BASE_URL + "Question/questionRecord";//获取用户答题记录
    public static final String QUESTION_AbilityTOAssess = "http://ycapi.youcaiwx.com/home/index.html#/capacity-assessment-app";//用户能力评估
    //http://ycapi.youcaiwx.com/home/index.html#/capacity-assessment-app?course_id=1&user_id=67
    //public static final String QUESTION_AbilityTOAssess = "http://192.168.3.23:8080/#/capacity-assessment-app";//用户能力评估
    public static final String QUESTION_GETSTAGEOFTESTLIST = BASE_URL + "Question/getCourse";//阶段测试列表
    public static final String QUESTION_GETGROUPEXAMLIST = BASE_URL + "Question/volumeList";//组卷模考列表
    public static final String QUESTION_GETKnowledgePractice = BASE_URL + "Question/getSection";//知识点章节列表
    public static final String QUESTION_GETKnowledgeChildList = BASE_URL + "Question/getKnow";//知识点列表
    public static final String QUESTION_GETERRORCENTERChildList = BASE_URL + "Question/errorqueKnow";//做题中心知识点列表
    public static final String QUESTION_GETHIGHTErrorsection = BASE_URL + "Question/getErrorsection";//系统高频错题列表
    public static final String QUESTION_HIGHTERRORKNOWLEDGE = BASE_URL + "Question/geterrorKnow";//系统高频错题知识点列表
    public static final String QUESTION_GETERRORCENTER = BASE_URL + "Question/wrongIndex";//错题中心

    //TODO 题库6大板块
    public static final String QUESTION_GETPROBLEMSLIS = BASE_URL + "Question/topicList";//6大板块获取题库
    public static final String QUESTION_GET_AnalysisPROBLEMSLIS = BASE_URL + "Question/questionParsing";//6大板块获取错题解析题库
    public static final String QUESTION_SUBMITPAPERS = BASE_URL + "Question/getPapers";//6大板块交卷
    public static final String QUESTION_CONTINUE_SUBMITPAPERS = BASE_URL + "Question/getdtPapers";//继续做题交卷
    public static final String QUESTION_GETRESULTREPORT = BASE_URL + "Question/resultsStati";//6大板块成绩统计
    public static final String QUESTION_SETPROBLEMSCollection = BASE_URL + "Question/questionCollection";//题目收藏功能
    public static final String QUESTION_GET_DISCUSS_LOOKEXAM = BASE_URL + "Question/checkItem";//答题记录-查看试题(论述题)
    public static final String QUESTION_GET_CONTIUEEXAM = BASE_URL + "Question/continueQuestion";//答题记录-继续做题
    public static final String QUESTION_FREE_EXPERIENCE = BASE_URL + "Question/zExperience";//0元体验-做题
    public static final String QUESTION_FREE_EXPERIENCE_RESULTREPORT = BASE_URL + "Question/experienceStati";//0元体验-成绩统计
    public static final String QUESTION_FREE_EXPERIENCE_ANALYSIS = BASE_URL + "Question/experienceParsing";//0元体验-错题解析
    //TODO 错题中心单独摘出来
    public static final String QUESTION_ERRORCENTER_CHECKANALYSIS = BASE_URL + "Question/wrongCheck";//错题中心查看解析
    public static final String QUESTION_ERRORCENTER_REFORM = BASE_URL + "Question/wrongRedo";//错题中心重新做题
    public static final String QUESTION_ERRORCENTER_SUBMITPAPERS = BASE_URL + "Question/errorCenter";//错题中心交卷
    public static final String QUESTION_ERRORCENTER_GETRESULTREPORT = BASE_URL + "Question/errorStati";//错题中心成绩统计
    public static final String QUESTION_ERRORCENTER_ANALYSIS = BASE_URL + "Question/errorParsing";//错题中心(错题解析和全部解析)
    public static final String QUESTION_ERRORCENTER_DELETE = BASE_URL + "Question/delmisquestion";//错题中心:查看解析删除错题
    public static final String QUESTION_QUESTIONDETAILED = BASE_URL + "Question/lastQuestion";//我的问题查看试题
    //-----------------------------------------TODO 题库答疑------------------------------------------------------------------------------//
    public static final String ANSWERQUESTION_ANSWER_LIST = BASE_URL + "Question/questionallAnswer";//题库答疑列表
    public static final String ANSWERQUESTION_ANSWER_DETAIL = BASE_URL + "Question/questionDetails";//题库答疑详情
    public static final String ANSWERQUESTION_ANSWER_KNOWLIST = BASE_URL + "Question/getAnswerknow";//获取提问问题界面知识点列表
    public static final String ANSWERQUESTION_ANSWER_SUBMIT = BASE_URL + "Question/questionSub";//提问页提交问题
    //-----------------------------------------TODO 个人中心------------------------------------------------------------------------------//
    public static final String USER_ABOUT_YOUCAI = "http://www.youcaiwx.com/html/app/aboutyoucai.html";//关于SB
    public static final String USER_GETUSERINFO = BASE_URL + "Personal/getPersonal";//个人中心基本信息
    public static final String USER_RETOUCHE_USERINFO = BASE_URL + "Personal/savePersonal";//修改个人基本信息
    public static final String USER_RETOUCHE_PASSWORD = BASE_URL + "Personal/resetPaw";//修改密码
    public static final String USER_ADDRESS_LIST = BASE_URL + "Personal/recAddress";//地址列表
    public static final String USER_ADDADDRESS = BASE_URL + "Personal/addAddress";//添加地址
    public static final String USER_GETADDRESS_DETAIL = BASE_URL + "Personal/viewList";//地址详情
    public static final String USER_ADDRESS_DELETE = BASE_URL + "Personal/delAddress";//删除地址
    public static final String USER_ADDRESS_EDIT = BASE_URL + "Personal/editAddress";//修改地址
    public static final String USER_FEEDBACK = BASE_URL + "Personal/idea";//意见反馈
    public static final String USER_WATCHRECORD = BASE_URL + "Personal/watchRecords";//观看记录
    public static final String MINE_COURSE = BASE_URL + "Personal/myPackage";//我的课程
    public static final String MINE_COLLECTION_COURSECHILDLIST = BASE_URL + "Personal/myCollcourse";//课程套餐课程目录
    public static final String MINE_COLLECTION_COURSELIST = BASE_URL + "Personal/myCollpackage";//课程套餐收藏列表
    public static final String MINE_COLLECTION_COURSEDIRLIST = BASE_URL + "Personal/myCollvideo";//课程收藏播放目录
    public static final String MINE_COLLECTION_PROGECTLIST = BASE_URL + "Personal/getProject";//专业标签列表
    public static final String MINE_COLLECTION_QUESTIONLIST = BASE_URL + "Personal/myCollquestion";//题库收藏章节
    public static final String MINE_COLLECTION_QUESTIONCHILDLIST = BASE_URL + "Personal/myCollquestionknob";//题库收藏知识点列表
    public static final String MINE_COLLECTION_QUESTIONLOOK = BASE_URL + "Personal/myCollcsee";//题库收藏查看试题
    public static final String MINE_QUESTION_ANSWER = BASE_URL + "Personal/questionAnswer";//个人中心题库答疑
    public static final String MINE_COURSE_ANSWER = BASE_URL + "Personal/courseAnswer";//个人中心课程答疑
    //-----------------------------------------再丑也要看的分割线------------------------------------------------------------------------------//
    public static final String MINE_ORDERFORM_LIST = BASE_URL + "Personal/myOrder";//我的订单列表
    public static final String MINE_ORDERFORM_CANCEL = BASE_URL + "Personal/cancelOrder";//取消订单
    public static final String MINE_ORDERFORM_HASBEENDETAIL = BASE_URL + "Personal/alreadyOrderlist";//完成,取消订单详情
    public static final String MINE_ORDERFORM_NOTPAYDETAIL = BASE_URL + "Personal/stayOrderlist";//待支付订单详情
    public static final String MINE_COUPONS = BASE_URL + "Orders/coupons";//我的优惠券
    //-----------------------------------------TODO 学习中心------------------------------------------------------------------------------//
    public static final String LEARNCENTER_HOME = BASE_URL + "Plan/learnIndex";//学习中心首页
    public static final String LEARNCENTER_CHECKCOURSE = BASE_URL + "Plan/courseList";//添加学习计划选择课程
    public static final String LEARNCENTER_CHECKTIME = BASE_URL + "Plan/testTime";//添加学习计划选择时间
    public static final String LEARNCENTER_ADDLEARNPLAN = BASE_URL + "Plan/addStudy";//添加学习计划
    public static final String LEARNCENTER_UNFINISHPLAN = BASE_URL + "Plan/hangAir";//未完成学习计划
    public static final String LEARNCENTER_LEARNPLANDETAIL = BASE_URL + "Plan/studyContent";//学习计划详情
    public static final String LEARNCENTER_LEARNPLANDETAILVIDEO = BASE_URL + "Plan/getVideo";//学习计划详情视频列表
    public static final String LEARNCENTER_LEARNPLANTOPIC = BASE_URL + "Plan/withTopic";//学习计划习题获取题目
    public static final String LEARNCENTER_LEARNPLANSUBMIT = BASE_URL + "Plan/getPaper";//学习计划习题交卷
    public static final String LEARNCENTER_LEARNPLANANALYSIS = BASE_URL + "Plan/questionParsing";//学习计划试题解析
    public static final String LEARNCENTER_CLOCKIN = BASE_URL + "Plan/learnClock";//学习打卡
    public static final String LEARNCENTER_EXITPLAN = BASE_URL + "Plan/outPlan";//退出学习计划

    public static final String PAY_AGREEMENT = "http://www.youcaiwx.com/html/app/buy.html";//支付协议

    //------------------------------------------TODO 支付---------------------------------------------------------------------------------//
    public static final String PAY_GET_ORDERFORMDETAIL = BASE_URL + "Orders/showOrder";//订单详情
    public static final String PAY_GET_AVAILABLECOUPON = BASE_URL + "Orders/availableCoupon";//可用优惠券
    public static final String PAY_ADDORDERFORM = BASE_URL + "Orders/addOrder";//添加订单

    //支付页获取支付返回的平台订单编号
    public static final String PAY_GETPAYORDERNUMBER = BASE_URL + "";

    //------------------------------------------TODO 积分---------------------------------------------------------------------------------//
    //作任务获取积分
    public static final String INTEGRAL_EARNINTEGRAL = BASE_URL + "Integral/integralAdd";
    //查询我的积分
    public static final String INTEGRAL_MINEINTEGRAL = BASE_URL + "Integral/myIntegral";
    //积分商城首页
    public static final String INTEGRAL_HOME = BASE_URL + "Integral/goodsIndex";
    //商品兑换列表
    public static final String INTEGRAL_PRODUCTLIST = BASE_URL + "Integral/goodsList";
    //赚取积分
    public static final String INTEGRAL_EARNPOINT = BASE_URL + "Integral/earnPoints";
    //积分兑换记录
    public static final String INTEGRAL_EXCHANGERECORD = BASE_URL + "Integral/exchange";
    //积分明细
    public static final String INTEGRAL_DETAIL = BASE_URL + "Integral/couponDetailed";
    //积分商品详情
    public static final String INTEGRAL_PRODUCT_DETAIL = BASE_URL + "Integral/goodsExchange";
    //积分订单详情
    public static final String INTEGRAL_ADDORDER = BASE_URL + "Integral/ordersAdd";
    //订单兑换
    public static final String INTEGRAL_ORDEREXCHANGE = BASE_URL + "Integral/ordersExchange";
}
