package com.ucfo.youcaiwx.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.NetLoadingProgress;

/**
 * Author:AND
 * Time: 2019/1/7.  19:12
 * Email:2911743255@qq.com
 * Description:BaseFragment是所有Fragment的基类
 */
public abstract class BaseFragment extends Fragment {
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";
    protected Context context = null;
    protected View rootView;
    protected boolean mIsLoadedData = false;
    //private Unbinder unbinder;
    private NetLoadingProgress netLoadingProgress;
    private long lastClick = 0;

   /* @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.context = activity;
    }*/


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //防止Fragment重叠
        if (savedInstanceState != null) {
            boolean aBoolean = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = null;
            if (fragmentManager != null) {
                fragmentTransaction = fragmentManager.beginTransaction();
            }
            if (aBoolean) {
                if (fragmentTransaction != null) {
                    fragmentTransaction.hide(this);
                }
            } else {
                if (fragmentTransaction != null) {
                    fragmentTransaction.show(this);
                }
            }
            if (fragmentTransaction != null) {
                fragmentTransaction.commit();
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setContentView(), container, false);
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
    public void onDestroyView() {
        super.onDestroyView();
        dismissPorcess();
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

    protected abstract int setContentView();

    protected abstract void initView(View itemView);

    protected abstract void initData();


    /**
     * 跳转activity
     */
    protected void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    /**
     * 跳转activity
     */
    protected void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = null;
        if (getActivity() != null) {
            intent = new Intent(getActivity(), cls);
            if (bundle != null) {
                intent.putExtras(bundle);
            }
        } else {
            if (getContext() != null) {
                intent = new Intent(getContext(), cls);
                if (bundle != null) {
                    intent.putExtras(bundle);
                }
            }
        }
        startActivity(intent);
        //getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    /**
     * 友情提示,吐个丝
     */
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (context != null) {
            ToastUtil.showBottomShortText(context, msg);
        } else {
            if (getContext() != null) {
                ToastUtil.showBottomShortText(getContext(), msg);
            }
        }
    }

    /**
     * 公共base类中弹出进度条
     */
    public void setProcessLoading(String text, boolean showText) {
        if (context != null) {
            NetLoadingProgress.Builder builder = new NetLoadingProgress.Builder(context).setMessage(text).setShowMessage(showText);
            netLoadingProgress = builder.create();
            netLoadingProgress.show();
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

    public boolean isFastClick(int time) {
        boolean flag = true;
        long currentClickTime = System.currentTimeMillis();
        if ((currentClickTime - lastClick) >= time) {
            flag = false;
        }
        lastClick = currentClickTime;
        return flag;
    }
}
