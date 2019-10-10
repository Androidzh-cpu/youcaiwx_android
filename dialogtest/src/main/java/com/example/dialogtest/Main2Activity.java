package com.example.dialogtest;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dialogtest.entiy.User;

import org.litepal.LitePal;

import java.util.List;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    private TextView mTextTest;
    private Button mBtn1;
    private Button mBtn2;
    private Button mBtn3;
    private Button mBtn4;
    private Button mBtn5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        initView();
    }

    private void initView() {
        mTextTest = (TextView) findViewById(R.id.test_text);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mBtn3 = (Button) findViewById(R.id.btn3);
        mBtn3.setOnClickListener(this);
        mBtn4 = (Button) findViewById(R.id.btn4);
        mBtn4.setOnClickListener(this);
        mBtn5 = (Button) findViewById(R.id.btn5);
        mBtn5.setOnClickListener(this);

        check();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1://创建数据库
                // TODO 19/07/08
                SQLiteDatabase db = LitePal.getDatabase();
                boolean open = db.isOpen();
                mTextTest.setText(getResources().getString(R.string.test) + open);

                break;
            case R.id.btn2://增
                // TODO 19/07/08
                for (int i = 0; i < 5; i++) {
                    User movie1 = new User();
                    movie1.setName(getString(R.string.test2) + i);
                    movie1.setPrice(10000 + i);
                    //这一句代码就是将一条记录存储进数据库中
                    movie1.save();
                }
                check();
                break;
            case R.id.btn3://删
                // TODO 19/07/08
                LitePal.deleteAll(User.class);

                check();
                break;
            case R.id.btn4://改
                // TODO 19/07/08
                User user1 = new User();
                user1.setName("修改后的名字");
                user1.updateAll("name like ?", "东成西就");

                check();
                break;
            case R.id.btn5://查
                // TODO 19/07/08
                check();
                break;
            default:
                break;
        }
    }

    public void check() {
        long[] ids = new long[]{101, 99, 97};
        List<User> list = LitePal.findAll(User.class, ids);
        StringBuffer stringBuffer = new StringBuffer();
        for (User user : list) {
            stringBuffer.append(user.getName() + "  " + user.getPrice() + "  id:" + user.getId() + "\n");
        }
        mTextTest.setText(stringBuffer.toString());
    }
}
/**
 * #-------------------------------------------自定义区域----------------------------------------------
 * #---------------------------------1.解析相关实体类---------------------------------
 * FastJson或者Gson解析的实体类
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * #---------------------------------2.与js互相调用的类------------------------
 * -keepattributes *JavascriptInterface*
 * <p>
 * #eg:如果你在WebViewActivity定义一个和Js交互的接口JsCallback，那么可以按照下面的格式写
 * #-keepclassmembers class cn.xx.xx.WebViewActivity$JsCallback {
 * #  public *;
 * #}
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * #---------------------------------3.自定义View的类----------------------------
 * <p>
 * <p>
 * <p>
 * #---------------------------------4.反射相关的类和方法-----------------------
 * 检查一下有没有通过类名反射的类和方法，否则混淆后，将无法操作
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * #---------------------------------5.第三方包-------------------------------
 * #如果报The same input jar is specified twice异常，把-libraryjars注释掉，在AndroidStudio可以不需要，直接对jar里面的类进行混淆操作
 * #-libraryjars libs/xxxxxxx.jar
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * #-------------------------------------------基本不用动区域--------------------------------------------
 * #---------------------------------基本指令区----------------------------------
 * -optimizationpasses 5
 * -dontskipnonpubliclibraryclassmembers
 * -printmapping proguardMapping.txt
 * -optimizations !code/simplification/cast,!field/*,!class/merging/*
 * -keepattributes *Annotation*,InnerClasses
 * -keepattributes Signature
 * -keepattributes SourceFile,LineNumberTable
 * <p>
 * <p>
 * #----------------------------手动启用support keep注解------------------------
 * -dontskipnonpubliclibraryclassmembers
 * -printconfiguration
 * -keep,allowobfuscation @interface android.support.annotation.Keep
 * -keep @android.support.annotation.Keep class *
 * -keepclassmembers class * {
 *
 * @android.support.annotation.Keep *;
 * }
 * <p>
 * <p>
 * <p>
 * #---------------------------------默认保留区---------------------------------
 * -keep public class * extends android.app.Activity
 * -keep public class * extends android.app.Application
 * -keep public class * extends android.support.multidex.MultiDexApplication
 * -keep public class * extends android.app.Service
 * -keep public class * extends android.content.BroadcastReceiver
 * -keep public class * extends android.content.ContentProvider
 * -keep public class * extends android.app.backup.BackupAgentHelper
 * -keep public class * extends android.preference.Preference
 * -keep public class * extends android.view.View
 * -keep public class com.android.vending.licensing.ILicensingService
 * -keep class android.support.** {*;}
 * <p>
 * -keep public class * extends android.view.View{
 * ** get*();
 * void set*(***);
 * public <init>(android.content.Context);
 * public <init>(android.content.Context, android.util.AttributeSet);
 * public <init>(android.content.Context, android.util.AttributeSet, int);
 * }
 * -keepclasseswithmembers class * {
 * public <init>(android.content.Context, android.util.AttributeSet);
 * public <init>(android.content.Context, android.util.AttributeSet, int);
 * }
 * #这个主要是在layout 中写的onclick方法android:onclick="onClick"，不进行混淆
 * -keepclassmembers class * extends android.app.Activity {
 * public void *(android.view.View);
 * }
 * <p>
 * -keepclassmembers class * implements java.io.Serializable {
 * static final long serialVersionUID;
 * private static final java.io.ObjectStreamField[] serialPersistentFields;
 * private void writeObject(java.io.ObjectOutputStream);
 * private void readObject(java.io.ObjectInputStream);
 * java.lang.Object writeReplace();
 * java.lang.Object readResolve();
 * }
 * -keep class **.R$* {
 * ;
 * }
 * <p>
 * -keepclassmembers class * {
 * void *(*Event);
 * }
 * <p>
 * -keepclassmembers enum * {
 * public static **[] values();
 * public static ** valueOf(java.lang.String);
 * }
 * -keep class * implements android.os.Parcelable {
 * public static final android.os.Parcelable$Creator *;
 * }
 * #// natvie 方法不混淆
 * -keepclasseswithmembernames class * {
 * native <methods>;
 * }
 * <p>
 * #保持 Parcelable 不被混淆
 * -keep class * implements android.os.Parcelable {
 * public static final android.os.Parcelable$Creator *;
 * }
 * <p>
 * <p>
 * <p>
 * #---------------------------------webview------------------------------------
 * -keepclassmembers class fqcn.of.javascript.interface.for.Webview { public *; }
 * -keepclassmembers class * extends android.webkit.WebViewClient {
 * public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
 * public boolean *(android.webkit.WebView, java.lang.String);
 * }
 * <p>
 * -keepclassmembers class * extends android.webkit.WebViewClient {
 * public void *(android.webkit.WebView, jav.lang.String);
 * }
 * <p>
 * <p>
 * -------------------------------------------------------------目前支持的开源库------------------------------------------------
 * #================支付宝支付
 * -keep class com.alipay.android.app.IAlixPay{*;}
 * -keep class com.alipay.android.app.IAlixPay$Stub{*;}
 * -keep class com.alipay.android.app.IRemoteServiceCallback{*;}
 * -keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
 * -keep class com.alipay.sdk.app.PayTask{ public *;}
 * -keep class com.alipay.sdk.app.AuthTask{ public *;}
 * -keep public class * extends android.os.IInterface
 * #================微信支付
 * -keep class com.tencent.mm.sdk.openapi.WXMediaMessage {*;}
 * -keep class com.tencent.mm.sdk.openapi.** implements com.tencent.mm.sdk.openapi.WXMediaMessage$IMediaObject {*;}
 * -keep class com.tencent.wxop.** { *; }
 * -dontwarn com.tencent.mm.**
 * -keep class com.tencent.mm.**{*;}
 * <p>
 * -keep class sun.misc.Unsafe { *; }
 * <p>
 * -keep class com.taobao.** {*;}
 * -keep class com.alibaba.** {*;}
 * -keep class com.alipay.** {*;}
 * -dontwarn com.taobao.**
 * -dontwarn com.alibaba.**
 * -dontwarn com.alipay.**
 * <p>
 * -keep class com.ut.** {*;}
 * -dontwarn com.ut.**
 * <p>
 * -keep class com.ta.** {*;}
 * -dontwarn com.ta.**
 * <p>
 * -keep class anet.**{*;}
 * -keep class org.android.spdy.**{*;}
 * -keep class org.android.agoo.**{*;}
 * -dontwarn anet.**
 * -dontwarn org.android.spdy.**
 * -dontwarn org.android.agoo.**
 * <p>
 * -keepclasseswithmembernames class com.xiaomi.**{*;}
 * -keep public class * extends com.xiaomi.mipush.sdk.PushMessageReceiver
 * <p>
 * -dontwarn com.xiaomi.push.service.b
 * <p>
 * -keep class org.apache.http.**
 * -keep interface org.apache.http.**
 * -dontwarn org.apache.**
 * <p>
 * #===================================okhttp3.x
 * -dontwarn com.squareup.okhttp3.**
 * -keep class com.squareup.okhttp3.** { *;}
 * -dontwarn okio.**
 * #===================================sharesdk
 * -keep class cn.sharesdk.**{*;}
 * -keep class com.sina.**{*;}
 * -keep class **.R$* {*;}
 * -keep class **.R{*;}
 * <p>
 * -keep class com.mob.**{*;}
 * -dontwarn com.mob.**
 * -dontwarn cn.sharesdk.**
 * -dontwarn **.R$*
 * <p>
 * #=========================nineoldandroids-2.4.0.jar
 * -keep public class com.nineoldandroids.** {*;}
 * <p>
 * #============================zxing
 * -keep class com.google.zxing.** {*;}
 * -dontwarn com.google.zxing.**
 * #=============================百度定位
 * -keep class com.baidu.** {*;}
 * -keep class vi.com.** {*;}
 * -dontwarn com.baidu.**
 * <p>
 * #=====================================okhttp
 * -dontwarn com.squareup.okhttp.**
 * -keep class com.squareup.okhttp.{*;}
 * #retrofit
 * -dontwarn retrofit.**
 * -keep class retrofit.** { *; }
 * -keepattributes Signature
 * -keepattributes Exceptions
 * -dontwarn okio.**
 * <p>
 * #========================recyclerview-animators
 * -keep class jp.wasabeef.** {*;}
 * -dontwarn jp.wasabeef.*
 * <p>
 * #============================universal-image-loader 混淆
 * -dontwarn com.nostra13.universalimageloader.**
 * -keep class com.nostra13.universalimageloader.** { *; }
 * <p>
 * #===============================ormlite
 * -keep class com.j256.**
 * -keepclassmembers class com.j256.** { *; }
 * -keep enum com.j256.**
 * -keepclassmembers enum com.j256.** { *; }
 * -keep interface com.j256.**
 * -keepclassmembers interface com.j256.** { *; }
 * #umeng
 * #===================================友盟
 * -dontshrink
 * -dontoptimize
 * -dontwarn com.google.android.maps.**
 * -dontwarn android.webkit.WebView
 * -dontwarn com.umeng.**
 * -dontwarn com.tencent.weibo.sdk.**
 * -dontwarn com.facebook.**
 * <p>
 * <p>
 * -keep enum com.facebook.**
 * -keepattributes Exceptions,InnerClasses,Signature
 * -keepattributes *Annotation*
 * -keepattributes SourceFile,LineNumberTable
 * <p>
 * -keep public interface com.facebook.**
 * -keep public interface com.tencent.**
 * -keep public interface com.umeng.socialize.**
 * -keep public interface com.umeng.socialize.sensor.**
 * -keep public interface com.umeng.scrshot.**
 * <p>
 * -keep public class com.umeng.socialize.* {*;}
 * -keep public class javax.**
 * -keep public class android.webkit.**
 * <p>
 * -keep class com.facebook.**
 * -keep class com.umeng.scrshot.**
 * -keep public class com.tencent.** {*;}
 * -keep class com.umeng.socialize.sensor.**
 * <p>
 * -keep class com.tencent.mm.sdk.modelmsg.WXMediaMessage {*;}
 * <p>
 * -keep class com.tencent.mm.sdk.modelmsg.** implements com.tencent.mm.sdk.modelmsg.WXMediaMessage$IMediaObject {*;}
 * <p>
 * -keep class im.yixin.sdk.api.YXMessage {*;}
 * -keep class im.yixin.sdk.api.** implements im.yixin.sdk.api.YXMessage$YXMessageData{*;}
 * <p>
 * -keep public class com.android.ljywp.R$*{
 * public static final int *;
 * }
 * -keepclassmembers class * {
 * public <init> (org.json.JSONObject);
 * }
 * -keepclassmembers enum * {
 * public static **[] values();
 * public static ** valueOf(java.lang.String);
 * }
 * <p>
 * #===============================友盟自动更新
 * -keep public class com.umeng.fb.ui.ThreadView {
 * }
 * -keep public class * extends com.umeng.**
 * # 以下包不进行过滤
 * -keep class com.umeng.** { *; }
 * <p>
 * <p>
 * #========================================ButterKnife 7.0
 * -keep class butterknife.** { *; }
 * -dontwarn butterknife.internal.**
 * -keep class **$$ViewBinder { *; }
 * -keepclasseswithmembernames class * {
 * @butterknife.* <fields>;
 * }
 * -keepclasseswithmembernames class * {
 * @butterknife.* <methods>;
 * }
 * <p>
 * <p>
 * #========================================AndFix
 * -keep class * extends java.lang.annotation.Annotation
 * -keepclasseswithmembernames class * {
 * native <methods>;
 * }
 * <p>
 * #===========================================eventbus 3.0
 * -keepattributes *Annotation*
 * -keepclassmembers class ** {
 * @org.greenrobot.eventbus.Subscribe <methods>;
 * }
 * -keep enum org.greenrobot.eventbus.ThreadMode { *; }
 * -keepclassmembers class * extends org.greenrobot.eventbus.util.ThrowableFailureEvent {
 * <init>(java.lang.Throwable);
 * }
 * -libraryjars libs/pksdk.jar
 * <p>
 * <p>
 * #============================================EventBus
 * -keepclassmembers class ** {
 * public void onEvent*(**);
 * }
 * -keepclassmembers class ** {
 * public void xxxxxx(**);
 * }
 * <p>
 * <p>
 * #==========================================gson
 * -keep class com.google.gson.** {*;}
 * -keep class com.google.**{*;}
 * -keep class sun.misc.Unsafe { *; }
 * -keep class com.google.gson.stream.** { *; }
 * -keep class com.google.gson.examples.android.model.** { *; }
 * <p>
 * -keepclassmembers class * implements java.io.Serializable {
 * static final long serialVersionUID;
 * private static final java.io.ObjectStreamField[] serialPersistentFields;
 * private void writeObject(java.io.ObjectOutputStream);
 * private void readObject(java.io.ObjectInputStream);
 * java.lang.Object writeReplace();
 * java.lang.Object readResolve();
 * }
 * -keep public class * implements java.io.Serializable {*;}
 * <p>
 * <p>
 * #========================================support-v4
 * #https://stackoverflow.com/questions/18978706/obfuscate-android-support-v7-widget-gridlayout-issue
 * -dontwarn android.support.v4.**
 * -keep class android.support.v4.app.** { *; }
 * -keep interface android.support.v4.app.** { *; }
 * -keep class android.support.v4.** { *; }
 * <p>
 * <p>
 * #========================================support-v7
 * -dontwarn android.support.v7.**
 * -keep class android.support.v7.internal.** { *; }
 * -keep interface android.support.v7.internal.** { *; }
 * -keep class android.support.v7.** { *; }
 * <p>
 * #=======================================support design
 * #@link http://stackoverflow.com/a/31028536
 * -dontwarn android.support.design.**
 * -keep class android.support.design.** { *; }
 * -keep interface android.support.design.** { *; }
 * -keep public class android.support.design.R$* { *; }
 * <p>
 * #==============================================picasso
 * -keep class com.squareup.picasso.** {*; }
 * -dontwarn com.squareup.picasso.**
 * <p>
 * #================================================glide
 * -keep public class * implements com.bumptech.glide.module.GlideModule
 * -keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
 * *[] $VALUES;
 * public *;
 * }
 * <p>
 * #========================================greenDao
 * #greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
 * -keep class org.greenrobot.greendao.**{*;}
 * -keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
 * public static java.lang.String TABLENAME;
 * }
 * -keep class **$Properties
 * # If you do not use SQLCipher:
 * -dontwarn org.greenrobot.greendao.database.**
 * <p>
 * <p>
 * #================================volley混淆
 * -keep class com.android.volley.** {*;}
 * -keep class com.android.volley.toolbox.** {*;}
 * -keep class com.android.volley.Response$* { *; }
 * -keep class com.android.volley.Request$* { *; }
 * -keep class com.android.volley.RequestQueue$* { *; }
 * -keep class com.android.volley.toolbox.HurlStack$* { *; }
 * -keep class com.android.volley.toolbox.ImageLoader$* { *; }
 * <p>
 * #=======================================jpush极光推送
 * -dontwarn cn.jpush.**
 * -keep class cn.jpush.** { *; }
 * <p>
 * #==============================================activeandroid
 * -keep class com.activeandroid.** { *; }
 * -dontwarn com.ikoding.app.biz.dataobject.**
 * -keep public class com.ikoding.app.biz.dataobject.** { *;}
 * -keepattributes *Annotation*
 * <p>
 * #==============================================log4j
 * -dontwarn org.apache.log4j.**
 * -keep class  org.apache.log4j.** { *;}
 * #下面几行 是环信即时通信的代码混淆
 * -keep class com.easemob.** {*;}
 * -keep class org.jivesoftware.** {*;}
 * -dontwarn  com.easemob.**
 * <p>
 * #===================================================融云
 * -keepclassmembers class fqcn.of.javascript.interface.for.webview {
 * public *;
 * }
 * <p>
 * -keepattributes Exceptions,InnerClasses
 * <p>
 * -keep class io.rong.** {*;}
 * <p>
 * -keep class * implements io.rong.imlib.model.MessageContent{*;}
 * <p>
 * -keepattributes Signature
 * <p>
 * -keepattributes *Annotation*
 * <p>
 * -keep class sun.misc.Unsafe { *; }
 * <p>
 * -keep class com.google.gson.examples.android.model.** { *; }
 * <p>
 * -keepclassmembers class * extends com.sea_monster.dao.AbstractDao {
 * public static java.lang.String TABLENAME;
 * }
 * -keep class **$Properties
 * -dontwarn org.eclipse.jdt.annotation.**
 * <p>
 * -keep class com.ultrapower.** {*;}
 * #=============================================高徳地图
 * -dontwarn com.amap.api.**
 * -dontwarn com.a.a.**
 * -dontwarn com.autonavi.**
 * -keep class com.amap.api.**  {*;}
 * -keep class com.autonavi.**  {*;}
 * -keep class com.a.a.**  {*;}
 * <p>
 * #===============================retrofit2.x
 * -dontwarn retrofit2.**
 * -keep class retrofit2.** { *; }
 * -keepattributes Signature
 * -keepattributes Exceptions
 * <p>
 * #=====================================Rxjava RxAndroid
 * -dontwarn rx.*
 * -dontwarn sun.misc.**
 * <p>
 * -keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
 * long producerIndex;
 * long consumerIndex;
 * }
 * <p>
 * -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
 * rx.internal.util.atomic.LinkedQueueNode producerNode;
 * }
 * <p>
 * -keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
 * rx.internal.util.atomic.LinkedQueueNode consumerNode;
 * }
 * <p>
 * <p>
 * #=======================================fastJson
 * -dontwarn com.alibaba.fastjson.**
 * -keep class com.alibaba.fastjson.** { *; }
 * <p>
 * #==========================================Okio
 * -dontwarn com.squareup.**
 * -dontwarn okio.**
 * -keep public class org.codehaus.* { *; }
 * -keep public class java.nio.* { *; }
 * # Retrolambda
 * -dontwarn java.lang.invoke.*
 */
