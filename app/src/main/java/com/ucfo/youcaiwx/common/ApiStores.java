package com.ucfo.youcaiwx.common;

/**
 * Author:AND
 * Time: 2019-3-5.  下午 5:12
 * Email:2911743255@qq.com
 * ClassName: ApiStores
 * Detail:TODO 公共类的url，网络地址
 * 瓦力打包命令 发布前一定要切换到正式版本  gradlew clean assembleReleaseChannels
 * 同样的经历,我才刚经历不久
 */
public class ApiStores {

    private ApiStores() {
    }

    //线上正式环境
    //private static final String ROOT_URL = "https://api.youcaiwx.cn/";
    //线下测试环境
    private static final String ROOT_URL = "https://dest.youcaiwx.cn/";

    private static final String BASE_URL = ROOT_URL + "apps/";
    private static final String BASE_URL_UPLOAD = ROOT_URL + "upload/";

    //socket地址
    //public static final String SOCKET = "ws://ycapi.youcaiwx.com:2346";
    //public static final String SOCKET = "ws://47.93.190.198:2346";
    //public static final String SOCKET = "ws://api.youcaiwx.cn:2346";
    public static final String SOCKET = "ws://59.110.223.165:2346";


    //上传文件
    public static final String FILE_UPLOAD = ROOT_URL + "upload/Index/uploadImage";
    //app版本更新
    public static final String VERSION_UPDATE = BASE_URL + "Version/upAndroid";

    //TODO 首页各种临时地址
    public static final String TEMPORARYLIVE = "http://ycfx.youcaiwx.com/static/Video.html?user_id=888888";//临时直播地址
    public static final String TEMPORARFACE = "http://m.ucfo.com.cn/mskc/";//临时面授地址
    public static final String TEMPORARACTIVE = "http://m.ucfo.com.cn/yqh-wap/20190921/";//临时活动地址
    public static final String DOUBAN_API = "https://api.douban.com/v2/movie/top250";//豆瓣TOP250好评


    //-----------------------------------------TODO 协议和地址 start--------------------------------------------------------------//
    //App应用商店下载页
    public static final String APP_DOWNLOAD_URL = "https://www.youcaiwx.cn/Activity/download.html";
    //课程分享页
    public static final String COURSER_SHARE = "https://www.youcaiwx.cn/Video/index.html";
    //App logo
    public static final String LOGO = "https://api.youcaiwx.cn/logo/ycwx.png";


    //注册协议==用户协议
    public static final String USER_REGISTER_ARGUMENT = "https://www.youcaiwx.cn/Agreement/register.html";
    //隐私协议
    public static final String PRIVACY_AGREEMENT = "https://www.youcaiwx.cn/Agreement/privacy.html";
    //支付协议
    public static final String PAY_AGREEMENT = "https://www.youcaiwx.cn/Agreement/buy.html";
    //关于SB
    public static final String USER_ABOUT_YOUCAI = "https://www.youcaiwx.cn/About/aboutyoucai.html";
    //public static final String USER_ABOUT_YOUCAI = "https://www.youcaiwx.cn/action/index.html";
    //用户能力评估
    public static final String QUESTION_ABILITYTO_ASSESS = "https://www.youcaiwx.cn/capacity-assessment-app";
    //后续教育分享地址
    public static final String EDUCATION_COURSE_SHARE = "https://www.youcaiwx.cn/education-preview-sign";

    //-----------------------------------------TODO 协议 end--------------------------------------------------------------//


    //-----------------------------------------TODO 首页--------------------------------------------------------------//
    //首页
    public static final String HOME_API = BASE_URL + "First/newInformation";
    //消息首页
    public static final String MESSAGE_CENTER_HOME = BASE_URL + "Message/indexMessage";
    //网校公告列表
    public static final String MESSAGE_CENTER_NOTICE = BASE_URL + "Message/systeMessage";
    //消息已读
    public static final String MESSAGE_CENTER_HAVEDREAD = BASE_URL + "Message/read";
    //资讯列表
    public static final String MESSAGE_INFORMATIONLIST = BASE_URL + "news/newsList";
    //App学习中心 获取图片活动
    public static final String ACTIVEEVENT = ROOT_URL + "web/Register/thickness";
    //首页活动列表
    public static final String HOME_EVENTLIST = BASE_URL + "Education/previewCourse";
    //首页活动详情
    public static final String HOME_EVENTDETAILED = BASE_URL + "Education/previewDetails";
    //提交活动报名信息
    public static final String HOME_EVENT_COMMIT = BASE_URL + "Education/activityUser";

    //-----------------------------------------TODO 后续教育 start------------------------------------------------------------------------------//

    public static final String EDUCATION_CLASSIFICATION = BASE_URL + "Education/deuType";
    public static final String EDUCATION_COURSE_LIST = BASE_URL + "Education/listPackage";
    //课程简介
    public static final String EDUCATION_COURSE_INTORDUCTION = BASE_URL + "Education/courseIntroduction";
    //课程目录
    public static final String EDUCATION_COURSEDIRECTORY = BASE_URL + "Education/courseoutline";
    //获取视频凭证
    public static final String EDUCATION_COURSE_GETVIDEO_CREDENTIALS = BASE_URL + "Education/videoCredentials";
    //检查是否签到
    public static final String EDUCATION_COURSE_WETHERSIGNIN = BASE_URL + "Education/signQuery";
    //签到
    public static final String EDUCATION_COURSE_SIGNIN = BASE_URL + "Education/sign";
    //后续教育记录
    public static final String EDUCATION_COURSE_RECORD = BASE_URL + "Education/record";
    //后续教育课程
    public static final String EDUCATION_COURSE_MINECOURSE = BASE_URL + "Education/myCPEcourse";
    //观看记录
    public static final String EDUCATION_COURSE_WATCHRECORD = BASE_URL + "Education/watchRecords";
    //CPE 证明申请
    public static final String EDUCATION_APPLYFOR = BASE_URL + "Education/userIntegral";
    //生成学分报告
    public static final String EDUCATION_CREATE_REPORT = BASE_URL_UPLOAD + "Edureport/generateImg";
    //我的活动
    public static final String EDUCATION_MINE_EVENT = BASE_URL + "Personal/myPreview";

    //-----------------------------------------TODO 后续教育 end------------------------------------------------------------------------------//

    //-----------------------------------------TODO 登录注册 start------------------------------------------------------------------------------//
    //账号登录
    public static final String LOGIN_ACCOUMENT = BASE_URL + "Login/accountLogin";
    //账号退出登录
    public static final String LOGIN_EXITLOGIN = BASE_URL + "Login/outlogin";
    //短信登录
    public static final String LOGIN_SMSLOGIN = BASE_URL + "Login/quickLogin";
    //微信登录
    public static final String LOGIN_WECHEATLOGIN = BASE_URL + "Login/wxlogin";
    //微信登录完善信息
    public static final String LOGIN_WECHEATLOGIN_COMPLETED = BASE_URL + "Login/wxCominformation";
    //账号注册
    public static final String REGISTER_ACCOUMENT = BASE_URL + "Register/webReg";
    //账号密码重置
    public static final String RESET_PASSWORD = BASE_URL + "Login/resetPaw";
    //注册获取短信验证码
    public static final String REGISTER_GETSMSCODE = BASE_URL + "Register/getSmsCode";
    //注册获取语音验证码
    public static final String REGISTER_GETVOICECODE = BASE_URL + "Register/voice";
    //忘记密码获取验证码
    public static final String FORGET_GETSMSCODE = BASE_URL + "Login/forgetPaw";
    //-----------------------------------------TODO 登录注册 end------------------------------------------------------------------------------//


    //-----------------------------------------TODO 课程 start------------------------------------------------------------------------------//
    //课程列表-条件筛选科目
    public static final String COURSE_SUBJECTS = BASE_URL + "Course/subjects";
    //外层课程列表
    public static final String COURSE_COURSELIST = BASE_URL + "Course/courseList";
    //课程简介
    public static final String COURSE_INTORDUCTION = BASE_URL + "Course/courseIntroduction";
    //获取视频凭证
    public static final String COURSE_GETVIDEO_CREDENTIALS = BASE_URL + "Course/videoCredentials";
    //获取下载STS
    public static final String COURSE_GETVIDEO_STS = BASE_URL + "Course/getToken";
    //获取视频目录
    public static final String COURSE_GETCOURSEDIR = BASE_URL + "Course/courseoutline";
    //视频收藏
    public static final String COURSE_VIDEOCOLLECT = BASE_URL + "Course/collection";
    //视频观看记录记录
    public static final String COURSE_SOCKET = BASE_URL + "Course/firstSocket";
    //-----------------------------------------TODO 课程 end------------------------------------------------------------------------------//


    //-----------------------------------------TODO 课程问答业务 start------------------------------------------------------------------------------//
    //获取问答列表
    public static final String ANSWER_GETANSWERLIST = BASE_URL + "Course/answerList";
    //获取问答详情
    public static final String ANSWER_GETANSWERDETAIL = BASE_URL + "Course/answerDetails";
    //提问问题
    public static final String ANSWER_ASKQUESTION = BASE_URL + "Course/answerSub";
    //-----------------------------------------TODO 课程问答业务 end------------------------------------------------------------------------------//


    //-----------------------------------------TODO 投诉业务 start------------------------------------------------------------------------------//
    //投诉类别
    public static final String COMPLAIN_TYPE = BASE_URL + "Course/complainType";
    //投诉提交
    public static final String COMPLAIN_SUBMIT = BASE_URL + "Course/complainSub";
    //-----------------------------------------TODO 投诉业务 end------------------------------------------------------------------------------//


    //-----------------------------------------TODO 题库做题考试-------------------------------------------------------------------//
    //获取题库展示专业
    public static final String QUESTION_GETPROJECT = BASE_URL + "Question/getProject";
    //获取用户对应题库信息
    public static final String QUESTION_GETPROJECTINFO = BASE_URL + "Question/questionIndex";
    //获取用户答题记录
    public static final String QUESTION_GETQUSTIONONRECORD = BASE_URL + "Question/questionRecord";
    //阶段测试列表
    public static final String QUESTION_GETSTAGEOFTESTLIST = BASE_URL + "Question/getCourse";
    //冲刺训练营
    public static final String QUESTION_GETTRAININGCAMP_LIST = BASE_URL + "Question/sprintCourse";
    //训练营提示框
    public static final String QUESTION_GETTRAININGCAMP_TIPS = BASE_URL + "Question/tips";
    //组卷模考列表
    public static final String QUESTION_GETGROUPEXAMLIST = BASE_URL + "Question/volumeList";
    //知识点章节列表
    public static final String QUESTION_GET_KNOWLEDGE_PRACTICE = BASE_URL + "Question/getSection";
    //知识点列表
    public static final String QUESTION_GET_KNOWLEDGE_CHILD_LIST = BASE_URL + "Question/getKnow";
    //做题中心知识点列表
    public static final String QUESTION_GETERRORCENTER_CHILD_LIST = BASE_URL + "Question/errorqueKnow";
    //系统高频错题列表
    public static final String QUESTION_GETHIGHTErrorsection = BASE_URL + "Question/getErrorsection";
    //系统高频错题知识点列表
    public static final String QUESTION_HIGHTERRORKNOWLEDGE = BASE_URL + "Question/geterrorKnow";
    //错题中心
    public static final String QUESTION_GETERRORCENTER = BASE_URL + "Question/wrongIndex";

    //TODO 题库6大板块
    //6大板块获取题库
    public static final String QUESTION_GETPROBLEMSLIS = BASE_URL + "Question/topicList";
    //6大板块获取错题解析题库
    public static final String QUESTION_GET_AnalysisPROBLEMSLIS = BASE_URL + "Question/questionParsing";
    //6大板块交卷
    public static final String QUESTION_SUBMITPAPERS = BASE_URL + "Question/getPapers";
    //继续做题交卷
    public static final String QUESTION_CONTINUE_SUBMITPAPERS = BASE_URL + "Question/getdtPapers";
    //6大板块成绩统计
    public static final String QUESTION_GETRESULTREPORT = BASE_URL + "Question/resultsStati";
    //题目收藏功能
    public static final String QUESTION_SETPROBLEMSCollection = BASE_URL + "Question/questionCollection";
    //答题记录-查看试题(论述题)
    public static final String QUESTION_GET_DISCUSS_LOOKEXAM = BASE_URL + "Question/checkItem";
    //答题记录-继续做题
    public static final String QUESTION_GET_CONTIUEEXAM = BASE_URL + "Question/continueQuestion";
    //0元体验-做题
    public static final String QUESTION_FREE_EXPERIENCE = BASE_URL + "Question/zExperience";
    //0元体验-成绩统计
    public static final String QUESTION_FREE_EXPERIENCE_RESULTREPORT = BASE_URL + "Question/experienceStati";
    //0元体验-错题解析
    public static final String QUESTION_FREE_EXPERIENCE_ANALYSIS = BASE_URL + "Question/experienceParsing";

    //TODO 错题中心单独摘出来
    //错题中心查看解析
    public static final String QUESTION_ERRORCENTER_CHECKANALYSIS = BASE_URL + "Question/wrongCheck";
    //错题中心重新做题
    public static final String QUESTION_ERRORCENTER_REFORM = BASE_URL + "Question/wrongRedo";
    //错题中心交卷
    public static final String QUESTION_ERRORCENTER_SUBMITPAPERS = BASE_URL + "Question/errorCenter";
    //错题中心成绩统计
    public static final String QUESTION_ERRORCENTER_GETRESULTREPORT = BASE_URL + "Question/errorStati";
    //错题中心(错题解析和全部解析)
    public static final String QUESTION_ERRORCENTER_ANALYSIS = BASE_URL + "Question/errorParsing";
    //错题中心:查看解析删除错题
    public static final String QUESTION_ERRORCENTER_DELETE = BASE_URL + "Question/delmisquestion";
    //我的问题查看试题
    public static final String QUESTION_QUESTIONDETAILED = BASE_URL + "Question/lastQuestion";
    //-----------------------------------------TODO 题库做题考试  end-------------------------------------------------------------------//


    //-----------------------------------------TODO 题库答疑 start----------------------------------------------------------------------//
    //题库答疑列表
    public static final String ANSWERQUESTION_ANSWER_LIST = BASE_URL + "Question/questionallAnswer";
    //题库答疑详情
    public static final String ANSWERQUESTION_ANSWER_DETAIL = BASE_URL + "Question/questionDetails";
    //获取提问问题界面知识点列表
    public static final String ANSWERQUESTION_ANSWER_KNOWLIST = BASE_URL + "Question/getAnswerknow";
    //提问页提交问题
    public static final String ANSWERQUESTION_ANSWER_SUBMIT = BASE_URL + "Question/questionSub";
    //追问
    public static final String TRACE_QUESTION = BASE_URL + "Course/answerClose";
    //-----------------------------------------TODO 题库答疑 start----------------------------------------------------------------------//


    //-----------------------------------------TODO 个人中心 start ----------------------------------------------------------------------//
    //个人中心基本信息
    public static final String USER_GETUSERINFO = BASE_URL + "Personal/getPersonal";
    //修改个人基本信息
    public static final String USER_RETOUCHE_USERINFO = BASE_URL + "Personal/savePersonal";
    //修改密码
    public static final String USER_RETOUCHE_PASSWORD = BASE_URL + "Personal/resetPaw";
    //地址列表
    public static final String USER_ADDRESS_LIST = BASE_URL + "Personal/recAddress";
    //添加地址
    public static final String USER_ADDADDRESS = BASE_URL + "Personal/addAddress";
    //地址详情
    public static final String USER_GETADDRESS_DETAIL = BASE_URL + "Personal/viewList";
    //删除地址
    public static final String USER_ADDRESS_DELETE = BASE_URL + "Personal/delAddress";
    //修改地址
    public static final String USER_ADDRESS_EDIT = BASE_URL + "Personal/editAddress";
    //意见反馈
    public static final String USER_FEEDBACK = BASE_URL + "Personal/idea";
    //观看记录
    public static final String USER_WATCHRECORD = BASE_URL + "Personal/watchRecords";
    //我的课程
    public static final String MINE_COURSE = BASE_URL + "Personal/myPackage";
    //课程套餐课程目录
    public static final String MINE_COLLECTION_COURSECHILDLIST = BASE_URL + "Personal/myCollcourse";
    //课程套餐收藏列表
    public static final String MINE_COLLECTION_COURSELIST = BASE_URL + "Personal/myCollpackage";
    //课程收藏播放目录
    public static final String MINE_COLLECTION_COURSEDIRLIST = BASE_URL + "Personal/myCollvideo";
    //专业标签列表
    public static final String MINE_COLLECTION_PROGECTLIST = BASE_URL + "Personal/getProject";
    //题库收藏章节
    public static final String MINE_COLLECTION_QUESTIONLIST = BASE_URL + "Personal/myCollquestion";
    //题库收藏知识点列表
    public static final String MINE_COLLECTION_QUESTIONCHILDLIST = BASE_URL + "Personal/myCollquestionknob";
    //题库收藏查看试题
    public static final String MINE_COLLECTION_QUESTIONLOOK = BASE_URL + "Personal/myCollcsee";
    //个人中心题库答疑
    public static final String MINE_QUESTION_ANSWER = BASE_URL + "Personal/questionAnswer";
    //个人中心课程答疑
    public static final String MINE_COURSE_ANSWER = BASE_URL + "Personal/courseAnswer";

    //-----------------------------------------再丑也要看的分割线------------------------------------------------------------------------------//
    //我的订单列表
    public static final String MINE_ORDERFORM_LIST = BASE_URL + "Personal/myOrder";
    //取消订单
    public static final String MINE_ORDERFORM_CANCEL = BASE_URL + "Personal/cancelOrder";
    //完成,取消订单详情
    public static final String MINE_ORDERFORM_HASBEENDETAIL = BASE_URL + "Personal/alreadyOrderlist";
    //我的优惠券
    public static final String MINE_COUPONS = BASE_URL + "Orders/coupons";
    //-----------------------------------------TODO 个人中心 end ----------------------------------------------------------------------//


    //-----------------------------------------TODO 学习中心 start --------------------------------------------------------------------//
    //学习中心首页
    public static final String LEARNCENTER_HOME = BASE_URL + "Plan/learnIndex";
    //添加学习计划选择课程
    public static final String LEARNCENTER_CHECKCOURSE = BASE_URL + "Plan/courseList";
    //添加学习计划选择时间
    public static final String LEARNCENTER_CHECKTIME = BASE_URL + "Plan/testTime";
    //添加学习计划
    public static final String LEARNCENTER_ADDLEARNPLAN = BASE_URL + "Plan/addStudy";
    //未完成学习计划
    public static final String LEARNCENTER_UNFINISHPLAN = BASE_URL + "Plan/hangAir";
    //学习计划详情
    public static final String LEARNCENTER_LEARNPLANDETAIL = BASE_URL + "Plan/studyContent";
    //学习计划详情视频列表
    public static final String LEARNCENTER_LEARNPLANDETAILVIDEO = BASE_URL + "Plan/getVideo";
    //学习计划习题获取题目
    public static final String LEARNCENTER_LEARNPLANTOPIC = BASE_URL + "Plan/withTopic";
    //学习计划习题交卷
    public static final String LEARNCENTER_LEARNPLANSUBMIT = BASE_URL + "Plan/getPaper";
    //学习计划试题解析
    public static final String LEARNCENTER_LEARNPLANANALYSIS = BASE_URL + "Plan/questionParsing";
    //学习打卡
    public static final String LEARNCENTER_CLOCKIN = BASE_URL + "Plan/learnClock";
    //退出学习计划
    public static final String LEARNCENTER_EXITPLAN = BASE_URL + "Plan/outPlan";
    //-----------------------------------------TODO 学习中心 end --------------------------------------------------------------------//


    //------------------------------------------TODO 支付 start------------------------------------------------------------------------------//
    //订单详情
    public static final String PAY_GET_ORDERFORMDETAIL = BASE_URL + "Orders/showOrder";
    //可用优惠券
    public static final String PAY_GET_AVAILABLECOUPON = BASE_URL + "Orders/availableCoupon";
    //添加订单
    public static final String PAY_ADDORDERFORM = BASE_URL + "Orders/addOrder";

    //根据订单号获取微信支付参数
    public static final String PAY_GET_WECHAT_PARAMAS = BASE_URL + "Pay/weiOrder";
    //支付完成后后台查询最终支付状态
    public static final String PAY_CHECK_PAY_RESULT = BASE_URL + "Pay/getGoods";
    //京东支付
    public static final String PAY_JINGDONG = ROOT_URL + "demo/action/ClientOrder.php?list=";
    //京东支付回调地址
    public static final String PAY_JINGDONG_NOTIFY = ROOT_URL + "Jdpay/Asynnotifyaction/execute";
    //京东支付回调地址
    public static final String PAY_JINGDONG_CALLBACK = ROOT_URL + "demo/action/CallBack.php";
    //------------------------------------------TODO 支付 end------------------------------------------------------------------------------//


    //------------------------------------------TODO 积分 start-------------------------------------------------------------------//
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
    //------------------------------------------TODO 积分 end-------------------------------------------------------------------//

    /**
     *
     👂👌
     👂─👌
     👂──👌
     👂───👌
     👂────👌
     👂─────👌
     👂──────👌
     👂───────👌
     👂────────👌
     👂─────────👌
     👂──────────👌
     👂───────────👌
     👂────────────👌
     👂─────────────👌
     👂──────────────👌
     大家不要碰我，我正在拿金箍棒

     👂👌👈，不用谢我，帮你摁进去了
     */
/*
                          _ooOoo_
                        o8888888o
                        88" . "88
                        (| -_- |)
                         O\ = /O
                     ____/`---'\____
                   .   ' \\| |// `.
                    / \\||| : |||// \
                  / _||||| -:- |||||- \
                    | | \\\ - /// | |
                  | \_| ''\---/'' | |
                   \ .-\__ `-` ___/-. /
                ______`. .' /--.--\ `. . __
             ."" '< `.___\_<|>_/___.' >'"".
            | | : `- \`.;`\ _ /`;.`/ - ` : | |
              \ \ `-. \_ __\ /__ _/ .-` / /
      ======`-.____`-.___\_____/___.-`____.-'======
                         `=---='
               佛祖保佑             永无BUG
*/
}
