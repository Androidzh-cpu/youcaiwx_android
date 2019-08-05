package com.ucfo.youcai.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucfo.youcai.widget.customview.NetLoadingProgress;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Author:AND
 * Time: 2019/1/7.  19:12
 * Email:2911743255@qq.com
 * Description:BaseFragment是所有Fragment的基类
 * Detail:
 */
public abstract class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Context context = null;
    protected View rootView;
    protected boolean mIsLoadedData = false;
    private Unbinder unbinder;
    private NetLoadingProgress netLoadingProgress;
    private long lastClick = 0;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止Fragment重叠
        if (savedInstanceState != null) {
            boolean aBoolean = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (aBoolean) {
                fragmentTransaction.hide(this);
            } else {
                fragmentTransaction.show(this);
            }
            fragmentTransaction.commit();
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentView(), container, false);
            unbinder = ButterKnife.bind(this, rootView);
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 初始化方法
        initView(view);

        initData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(true);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (getUserVisibleHint()) {
            handleOnVisibilityChangedToUser(false);
        }
    }

    @Override

    public void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isResumed()) {
            handleOnVisibilityChangedToUser(isVisibleToUser);
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private void handleOnVisibilityChangedToUser(boolean isVisibleToUser) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true;
                onLazyLoadOnce();
            }
            onVisibleToUser();
        } else {
            // 对用户不可见
            onInvisibleToUser();
        }
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    protected void onLazyLoadOnce() {
    }

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    protected void onVisibleToUser() {
    }

    /**
     * 对用户不可见时触发该方法
     */
    protected void onInvisibleToUser() {
    }

    protected abstract void initView(View view);

    protected abstract void initData();

    protected abstract int setContentView();

    /**
     * 跳转activity
     *
     * @param cls
     * @param bundle
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(getActivity(), cls);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
        //overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 公共base类中弹出进度条
     */
    public void setProcessLoading(String text, boolean showText) {
        NetLoadingProgress.Builder builder = new NetLoadingProgress.Builder(context).setMessage(text).setShowMessage(showText);
        netLoadingProgress = builder.create();
        netLoadingProgress.show();
    }

    /**
     * 关闭显示中的进度条
     */
    public void dismissPorcessDelay(long delaytime) {
        if (netLoadingProgress != null && netLoadingProgress.isShowing()) {
            TimerTask task = new TimerTask() {
                @Override
                public void run() {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            netLoadingProgress.dismiss();
                        }
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(task, delaytime);//n秒后执行
        }
    }

    /**
     * 关闭显示中的进度条
     */
    public void dismissPorcess() {
        if (netLoadingProgress != null && netLoadingProgress.isShowing()) {
            netLoadingProgress.dismiss();
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
}
