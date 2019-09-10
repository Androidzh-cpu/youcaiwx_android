####################################################################################################
#
# 基本混淆指令
#
####################################################################################################
# 代码混淆压缩比，在 0~7 之间
-optimizationpasses 5
# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames
# 指定不忽略非公共库的类和类成员
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose
# 不做预校验，preverify是proguard的四个步骤之一，Android不需要preverify，去掉这一步能够加快混淆速度
-dontpreverify
# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses
# 避免混淆泛型
-keepattributes Signature
# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable
# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是 Google 推荐的算法，一般不做修改
-optimizations !code/simplification/arithmetic,!code/simplification/cast,!field/*,!class/merging/*
# 输出所有找不到引用和一些其它错误的警告，但是继续执行处理过程。不处理警告有些危险，所以在清楚配置的具体作用的时候再使用
-ignorewarnings
####################################################################################################
#
# 一些不是很常用但比较实用的混淆命令
#
####################################################################################################

# 所有重新命名的包都重新打包，并把所有的类移动到所给定的包下面。如果没有指定 packagename，那么所有的类都会被移动到根目录下
# 如果需要从目录中读取资源文件，移动包的位置可能会导致异常，谨慎使用
# you.package.path 请改成你自己的项目路径
#-flatternpackagehierarchy you.package.path

# 所有重新命名过的类都重新打包，并把他们移动到指定的packagename目录下。如果没有指定 packagename，同样把他们放到根目录下面。
# 这项配置会覆盖-flatternpackagehierarchy的配置。它可以代码体积更小，并且更加难以理解。
# you.package.path 请改成你自己的项目路径
#-repackageclasses

#---------------------------------------------------TODO 丧心病狂的混淆操作 start------------------------------------------------------#

#TODO 把 proguard-dictionary.txt 文件放到 App Module 根目录下，将下面参数加入到 proguard-rules.pro 里
# 指定一个文本文件用来生成混淆后的名字。默认情况下，混淆后的名字一般为 a、b、c 这种。
# 通过使用配置的字典文件，可以使用一些非英文字符做为类名。成员变量名、方法名。字典文件中的空格，标点符号，重复的词，还有以'#'开头的行都会被忽略。
# 需要注意的是添加了字典并不会显著提高混淆的效果，只不过是更不利与人类的阅读。正常的编译器会自动处理他们，并且输出出来的jar包也可以轻易的换个字典再重新混淆一次。
# 最有用的做法一般是选择已经在类文件中存在的字符串做字典，这样可以稍微压缩包的体积。
# 查找了字典文件的格式：一行一个单词，空行忽略，重复忽略
-obfuscationdictionary proguard-dictionary.txt
# 指定一个混淆类名的字典，字典格式与 -obfuscationdictionary 相同
-classobfuscationdictionary proguard-dictionary.txt
# 指定一个混淆包名的字典，字典格式与 -obfuscationdictionary 相同
-packageobfuscationdictionary proguard-dictionary.txt

#---------------------------------------------------TODO 丧心病狂的混淆操作 END------------------------------------------------------#

# 混淆的时候大量使用重载，多个方法名使用同一个混淆名，但是他们的方法签名不同。这可以使包的体积减小一部分，也可以加大理解的难度。仅在混淆阶段有效。
# 这个参数在 JDK 版本上有一定的限制，可能会导致一些未知的错误，谨慎使用
#-overloadaggressively

# 方法同名混淆后亦同名，方法不同名混淆后亦不同名。不使用该选项时，类成员可被映射到相同的名称。因此该选项会增加些许输出文件的大小。
#-useuniqueclassmembernames

# 指定在混淆的时候不使用大小写混用的类名。默认情况下，混淆后的类名可能同时包含大写字母和小写字母。
# 这样生成jar包并没有什么问题。只有在大小写不敏感的系统（例如windows）上解压时，才会涉及到这个问题。
# 因为大小写不区分，可能会导致部分文件在解压的时候相互覆盖。如果有在windows系统上解压输出包的需求的话，可以加上这个配置。
#-dontusemixedcaseclassnames

####################################################################################################
#
# 混淆日志
# 记录混淆生成的日志数据，build 生成在项目根目录
#
####################################################################################################

# APK 包内所有 class 的内部结构
-dump proguard/class_files.txt
# 未混淆的类和成员
-printseeds proguard/seeds.txt
# 列出从 APK 中删除的代码
-printusage proguard/unused.txt
# 混淆前后的映射，这个文件在追踪异常的时候是有用的
-printmapping proguard/mapping.txt

####################################################################################################
#
# TODO 安利一下:  Android 开发不需要混淆的部份
#
####################################################################################################
# Android 四大组件相关
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService
# Fragment
-keep public class * extends android.support.v4.app.Fragment
-keep public class * extends android.app.Fragment
# 保留support下的所有类及其内部类
-keep class android.support.** { *; }
-keep interface android.support.** { *; }
-dontwarn android.support.**
# 保留 R 下面的资源
-keep class **.R$* {*;}
-keepclassmembers class **.R$* {
    public static <fields>;
}

# 保留本地 native 方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在 Activity 中的方法参数是 view 的方法，
# 这样以来我们在 layout 中写的 onClick 就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# 保留自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留 Parcelable 序列化类不被混淆
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}

# 保留 Serializable 序列化的类不被混淆
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的 onXXEvent 的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
}

# WebView，没有使用 WebView 请注释掉
-keepclassmembers class fqcn.of.javascript.interface.for.webview {
   public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

# 不混淆使用了 @Keep 注解相关的类
-keep class android.support.annotation.Keep
-keep @android.support.annotation.Keep class * {*;}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <methods>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <fields>;
}
-keepclasseswithmembers class * {
    @android.support.annotation.Keep <init>(...);
}

####################################################################################################
#
# 第三方依赖库
#
####################################################################################################

#okhttp
-keep class okhttp3.**{*;}

#okio
-keep class okio.**{*;}

# OkHttp3
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions

-dontwarn javax.annotation.**
-dontwarn javax.inject.**



# Butterknife
-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }

# Gson
#TODO 修改后的gson混淆规则(Google官方给的示例代码一定要看好包名的区别)
-keep class sun.misc.Unsafe { *; }
-keep class com.google.**{*;}
-keep class com.ucfo.youcaiwx.entity.**{ *; }
-keep interface com.google.gson.examples.models.** { *; }
-keep class com.google.gson.examples.upgrade.internal.VersionInfo {*;}


# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep public enum com.bumptech.glide.load.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
#-keepresourcexmlelements manifest/application/meta-data@value=GlideModule

# Rxjava and RxAndroid
-dontwarn org.mockito.**
-dontwarn org.junit.**
-dontwarn org.robolectric.**

-keep class io.reactivex.** { *; }
-keep interface io.reactivex.** { *; }

-keep class com.squareup.okhttp.** { *; }
-dontwarn okio.**
-keep interface com.squareup.okhttp.** { *; }
-dontwarn com.squareup.okhttp.**

-dontwarn io.reactivex.**
-dontwarn retrofit.**
-keep class retrofit.** { *; }
-keepclasseswithmembers class * {
    @retrofit.http.* <methods>;
}

-keep class sun.misc.Unsafe { *; }

-dontwarn java.lang.invoke.*

-keep class io.reactivex.schedulers.Schedulers {
    public static <methods>;
}
-keep class io.reactivex.schedulers.ImmediateScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.TestScheduler {
    public <methods>;
}
-keep class io.reactivex.schedulers.Schedulers {
    public static ** test();
}
-keepclassmembers class io.reactivex.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    long producerNode;
    long consumerNode;
}

-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class io.reactivex.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    io.reactivex.internal.util.atomic.LinkedQueueNode consumerNode;
}

-dontwarn io.reactivex.internal.util.unsafe.**

# Annotation
-keep class android.support.annotation.** { *; }
-keep interface android.support.annotation.** { *; }

# RxPermissions
-keep class com.tbruyelle.rxpermissions2.** { *; }
-keep interface com.tbruyelle.rxpermissions2.** { *; }

# RxCache
-dontwarn io.rx_cache2.internal.**
-keep class io.rx_cache2.internal.Record { *; }
-keep class io.rx_cache2.Source { *; }
-keep class io.victoralbertos.jolyglot.** { *; }
-keep interface io.victoralbertos.jolyglot.** { *; }

# Marshmallow removed Notification.setLatestEventInfo()
-dontwarn android.app.Notification

####################################################################################################
#
# 本项目混淆配置
#
####################################################################################################

# JavaBean 实体类不能混淆，一般会将实体类统一放到一个包下，you.package.path 请改成你自己的项目路径
-keep public class com.ucfo.youcaiwx.entity.** {
    public void set*(***);
    public *** get*();
    public *** is*();
}

# 网页中的 JavaScript 进行交互，you.package.path 请改成你自己的项目路径
#-keepclassmembers class you.package.path.JSInterface {
#    <methods>;
#}

# 需要通过反射来调用的类，没有可忽略，you.package.path 请改成你自己的项目路径
#-keep class you.package.path.** { *; }

#删除log代码-----begin-----  https://www.guardsquare.com/en/products/proguard/manual/examples#logging
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
   }
#删除log代码-----end-----


# TODO 其他的相关配置

#okgo相关
-keep class okhttp3.**{*;}
#okio
-keep class okio.**{*;}

# banner 的混淆代码
-keep class com.youth.banner.** {*;}

#TODO 以下是X5webview的混淆策略
# ------------------ TODO  start ---------------- #
#-optimizationpasses 7
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-dontoptimize
-dontusemixedcaseclassnames
-verbose
-dontskipnonpubliclibraryclasses
-dontskipnonpubliclibraryclassmembers
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**
#-overloadaggressively

# ------------------ Keep LineNumbers and properties ---------------- #
-keepattributes Exceptions,InnerClasses,Signature,Deprecated,SourceFile,LineNumberTable,*Annotation*,EnclosingMethod
# --------------------------------------------------------------------------
# Addidional for x5.sdk classes for apps
-keep class com.tencent.smtt.export.external.**{
    *;
}
-keep class com.tencent.tbs.video.interfaces.IUserStateChangedListener {
	*;
}
-keep class com.tencent.smtt.sdk.CacheManager {
	public *;
}
-keep class com.tencent.smtt.sdk.CookieManager {
	public *;
}
-keep class com.tencent.smtt.sdk.WebHistoryItem {
	public *;
}
-keep class com.tencent.smtt.sdk.WebViewDatabase {
	public *;
}
-keep class com.tencent.smtt.sdk.WebBackForwardList {
	public *;
}
-keep public class com.tencent.smtt.sdk.WebView {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebView$HitTestResult {
	public static final <fields>;
	public java.lang.String getExtra();
	public int getType();
}
-keep public class com.tencent.smtt.sdk.WebView$WebViewTransport {
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebView$PictureListener {
	public <fields>;
	public <methods>;
}
-keepattributes InnerClasses
-keep public enum com.tencent.smtt.sdk.WebSettings$** {
    *;
}
-keep public enum com.tencent.smtt.sdk.QbSdk$** {
    *;
}
-keep public class com.tencent.smtt.sdk.WebSettings {
    public *;
}
-keepattributes Signature
-keep public class com.tencent.smtt.sdk.ValueCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebViewClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebChromeClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebChromeClient$FileChooserParams {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.sdk.SystemWebChromeClient{
	public *;
}
# 1. extension interfaces should be apparent
-keep public class com.tencent.smtt.export.external.extension.interfaces.* {
	public protected *;
}
# 2. interfaces should be apparent
-keep public class com.tencent.smtt.export.external.interfaces.* {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.WebViewCallbackClient {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.WebStorage$QuotaUpdater {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebIconDatabase {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.WebStorage {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.DownloadListener {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.QbSdk {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.QbSdk$PreInitCallback {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.Tbs* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.LogFileUtils {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.TbsLog {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.TbsLogClient {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.CookieSyncManager {
	public <fields>;
	public <methods>;
}
# Added for game demos
-keep public class com.tencent.smtt.sdk.TBSGamePlayer {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerClient* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerClientExtension {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGamePlayerService* {
	public <fields>;
	public <methods>;
}
-keep public class com.tencent.smtt.utils.Apn {
	public <fields>;
	public <methods>;
}
-keep class com.tencent.smtt.** {
	*;
}
# end

-keep public class com.tencent.smtt.export.external.extension.proxy.ProxyWebViewClientExtension {
	public <fields>;
	public <methods>;
}
-keep class MTT.ThirdAppInfoNew {
	*;
}
-keep class com.tencent.mtt.MttTraceEvent {
	*;
}
# Game related
-keep public class com.tencent.smtt.gamesdk.* {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.TBSGameBooter {
        public <fields>;
        public <methods>;
}
-keep public class com.tencent.smtt.sdk.TBSGameBaseActivity {
	public protected *;
}
-keep public class com.tencent.smtt.sdk.TBSGameBaseActivityProxy {
	public protected *;
}
-keep public class com.tencent.smtt.gamesdk.internal.TBSGameServiceClient {
	public *;
}
#---------------------------------------------------------------------------
#------------------  下方是android平台自带的排除项，这里不要动         ----------------

-keep public class * extends android.app.Activity{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Application{
	public <fields>;
	public <methods>;
}
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
	public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepattributes *Annotation*
-keepclasseswithmembernames class *{
	native <methods>;
}
-keep class * implements android.os.Parcelable {
  public static final android.os.Parcelable$Creator *;
}
#------------------  下方是共性的排除项目         ----------------
# 方法名中含有“JNI”字符的，认定是Java Native Interface方法，自动排除
# 方法名中含有“JRI”字符的，认定是Java Reflection Interface方法，自动排除
-keepclasseswithmembers class * {
    ... *JNI*(...);
}
-keepclasseswithmembernames class * {
	... *JRI*(...);
}
-keep class **JNI* {*;}

# ------------------ TODO  end  ---------------- #


#----------------------------------------------TODO SmartRefreshLayout start------------------------------------------------#
#app混淆标记
-keep class com.github.mmin18.** {*;}

-dontwarn android.support.v8.renderscript.*
-keepclassmembers class android.support.v8.renderscript.RenderScript {
  native *** rsn*(...);
  native *** n*(...);
}

-keep class com.wang.avi.** { *; }
-keep class com.wang.avi.indicators.** { *; }

-keep class com.scwang.refreshlayout.activity.practice.BannerPracticeActivity$Movie {*;}
#----------------------------------------------TODO  SmartRefreshLayout end------------------------------------------------#

#----------------------------------------------TODO  友盟 start------------------------------------------------#
-dontwarn com.umeng.**
-dontwarn com.taobao.**
-dontwarn anet.channel.**
-dontwarn anetwork.channel.**
-dontwarn org.android.**
-dontwarn org.apache.thrift.**
-dontwarn com.xiaomi.**
-dontwarn com.huawei.**
-dontwarn com.meizu.**
-keepattributes *Annotation*
-keep class com.taobao.** {*;}
-keep class org.android.** {*;}
-keep class anet.channel.** {*;}
-keep class com.umeng.** {*;}
-keep class com.xiaomi.** {*;}
-keep class com.huawei.** {*;}
-keep class com.meizu.** {*;}
-keep class org.apache.thrift.** {*;}

-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

#-keep public class **.R$*{
#   public static final int *;
#}
#统计服务
-keep public class com.ucfo.youcaiwx.**.R$*{
   public static final int *;
}
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
#----------------------------------------------TODO  友盟 end------------------------------------------------#
#----------------------------------------------TODO 微信------------------------------------------------#
-keep class com.tencent.mm.opensdk.** {
    *;
}
-keep class com.tencent.wxop.** {
    *;
}
-keep class com.tencent.mm.sdk.** {
    *;
}
#----------------------------------------------TODO 阿里云视频------------------------------------------------#
-keep class com.alivc.player.**{*;}
-keep class com.aliyun.clientinforeport.**{*;}
-keep class com.aliyun.vodplayer.**{*;}
-dontwarn com.alivc.player.**
#----------------------------------------------TODO 支付宝支付------------------------------------------------#
-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}
-keep class com.alipay.sdk.app.H5PayCallback {
    <fields>;
    <methods>;
}
-keep class com.alipay.android.phone.mrpc.core.** { *; }
-keep class com.alipay.apmobilesecuritysdk.** { *; }
-keep class com.alipay.mobile.framework.service.annotation.** { *; }
-keep class com.alipay.mobilesecuritysdk.face.** { *; }
-keep class com.alipay.tscenter.biz.rpc.** { *; }
-keep class org.json.alipay.** { *; }
-keep class com.alipay.tscenter.** { *; }
-keep class com.ta.utdid2.** { *;}
-keep class com.ut.device.** { *;}
#----------------------------------------------TODO PDF文件------------------------------------------------#
-keep class com.shockwave.**
#----------------------------------------------TODO 照片选择------------------------------------------------#
-dontwarn com.bumptech.glide.**
#----------------------------------------------TODO 数据库------------------------------------------------#
-keep class org.litepal.** {
    *;
}
-keep class * extends org.litepal.crud.DataSupport {
    *;
}
-keep class * extends org.litepal.crud.LitePalSupport {
    *;
}
#----------------------------------------------TODO 版本更新------------------------------------------------#
-keep class com.xuexiang.xupdate.entity.** { *; }
#----------------------------------------------TODO bugly------------------------------------------------#
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
