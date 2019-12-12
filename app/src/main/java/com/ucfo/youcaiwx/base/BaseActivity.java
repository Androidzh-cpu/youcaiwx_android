package com.ucfo.youcaiwx.base;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.service.NetworkReceiver;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.netutils.NetTypeCallBack;
import com.ucfo.youcaiwx.utils.netutils.NetworkUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.NetLoadingProgress;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;

/**
 * Author:AND
 * Time: 2019/1/7.  18:39
 * Email:2911743255@qq.com
 * Description:BaseActivity是所有Activity的基类，把一些公共的方法放到里面
 * Detail:其实吧,就是为了偷懒
 */
public abstract class BaseActivity extends AppCompatActivity implements NetTypeCallBack {
    private long lastClick = 0;
    private NetLoadingProgress netLoadingProgress;
    private NetworkReceiver mNetworkReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (setContentView() != 0) {

            setContentView(setContentView());

            StatusBarUtil.immersive(this);

            ActivityUtil.getInstance().addActivity(this);

            //统计应用启动数据在所有的Activity 的onCreate 方法或在应用的BaseActivity的onCreate方法中添加
            PushAgent.getInstance(this).onAppStart();

            initView(savedInstanceState);

            //绑定view之后设置标题栏
            initToolbar();

            //标题栏设置之后可以进行数据操作
            initData();

            setListener();

            processLogic(savedInstanceState);
        } else {
            LogUtils.e("layoutId 为空");
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mNetworkReceiver == null) {
            mNetworkReceiver = new NetworkReceiver();
            mNetworkReceiver.setNetType(this);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        //注册广播
        registerReceiver(mNetworkReceiver, filter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);

        Resources resources = this.getResources();
        Configuration configuration = resources.getConfiguration();
        configuration.fontScale = 1;
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mNetworkReceiver != null) {
            //注销广播
            unregisterReceiver(mNetworkReceiver);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Activity管理器中移除当前页面
        ActivityUtil.getInstance().removeActivity(this);
    }

    /**
     * 设置activity布局
     */
    protected abstract int setContentView();

    /**
     * 抽象 - 初始化方法，可以对页面进行初始化
     */
    protected abstract void initView(Bundle savedInstanceState);

    /**
     * 抽象 - 初始化方法，可以对数据进行处理
     */
    protected void initData() {
    }

    /**
     * 处理业务逻辑，状态恢复等操作
     */
    protected abstract void processLogic(Bundle savedInstanceState);

    /**
     * 使用toolbar的页面进行白底黑字处理
     */
    protected void initToolbar() {
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
    }

    /**
     * 使用toolbar的页面进行白底黑字处理2
     */
    protected void initToolbar(Toolbar toolbar) {
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
    }

    /**
     * 处理点击事件
     */
    protected void setListener() {
    }

    /**
     * 跳转activity 无传参
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 跳转activity
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // 点击手机上的返回键，返回上一层
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 移除Activity
            ActivityUtil.getInstance().removeActivity(this);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void traverse(ViewGroup root) {
        final int childCount = root.getChildCount();
        for (int i = 0; i < childCount; ++i) {
            final View child = root.getChildAt(i);
            if (child instanceof ViewGroup) {
                child.setBackground(null);
                traverse((ViewGroup) child);
            } else {
                if (child != null) {
                    child.setBackground(null);
                }
                if (child instanceof ImageView) {
                    ((ImageView) child).setImageDrawable(null);
                } else if (child instanceof EditText) {
                    ((EditText) child).clearFocus();
                    ((EditText) child).clearAnimation();
                }
            }
        }
    }

    public boolean fastClick(int time) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClick > time) {
            lastClick = currentTime;
            return false;
        }
        return true;
    }

    public  boolean isFastClick(int time) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClick) >= time) {
            flag = false;
        }
        lastClick = currentClickTime;
        return flag;
    }

    /**
     * 耗时操作提示框
     *
     * @param text
     * @param showText
     */
    public void setProcessLoading(String text, boolean showText) {
        if (netLoadingProgress == null) {
            NetLoadingProgress.Builder builder = new NetLoadingProgress.Builder(this)
                    .setMessage(text)
                    .setShowMessage(showText);
            netLoadingProgress = builder.create();
            netLoadingProgress.show();
        }
    }

    public void dismissPorcess() {
        if (netLoadingProgress != null && netLoadingProgress.isShowing()) {
            netLoadingProgress.dismiss();
            netLoadingProgress = null;
        }
    }

    @Override
    public void onNetChange(NetworkUtils.NetworkType type) {
        switch (type) {
            case NETWORK_NO:
                //TODO 网络断了
                if (!NetworkUtils.getMobileDataEnabled()) {
                    ToastUtil.showBottomLongText(this, getResources().getString(R.string.net_loading_no));
                }
                break;
            case NETWORK_WIFI:
                //TODO 已连接至无线网络
                break;
            default:
                //TODO 2G,3G,4G以及未知网络
                break;
        }
    }
}
