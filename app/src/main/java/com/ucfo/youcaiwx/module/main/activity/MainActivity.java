package com.ucfo.youcaiwx.module.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.hubert.guide.NewbieGuide;
import com.app.hubert.guide.core.Controller;
import com.app.hubert.guide.listener.OnLayoutInflatedListener;
import com.app.hubert.guide.model.GuidePage;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.CallUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.update.UpdateCustomParser;
import com.ucfo.youcaiwx.module.main.fragment.ClassFragment;
import com.ucfo.youcaiwx.module.main.fragment.HomeFragment;
import com.ucfo.youcaiwx.module.main.fragment.LearnCenterFragment;
import com.ucfo.youcaiwx.module.main.fragment.MineFragment;
import com.ucfo.youcaiwx.module.main.fragment.QuestionBankFragment;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xuexiang.xupdate.XUpdate;

import java.lang.reflect.Field;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Author: AND
 * Time: 2019-9-3 下午 1:35
 * Package: com.ucfo.youcaiwx.view.main.activity
 * FileName: MainActivity
 * ORG: www.youcaiwx.com
 * Description:主页
 */
public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.bottom_navigation)
    BottomNavigationView bottomNavigation;
    private int indexTab = 0;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager supportFragmentManager;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private LearnCenterFragment learnCenterFragment;
    private QuestionBankFragment questionBankFragment;
    private MineFragment mineFragment;
    private MainActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.immersive(this);
        StatusbarUI.setStatusBarUIMode(this, Color.TRANSPARENT, true);

        context = MainActivity.this;
        ButterKnife.bind(this);
        ActivityUtil.getInstance().addActivity(this);

        //统计应用启动数据在所有的Activity 的onCreate 方法或在应用的BaseActivity的onCreate方法中添加
        PushAgent.getInstance(this).onAppStart();

        initView();

        checkPermission();

        updateApp();
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        int anInt = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        if (anInt != 0) {
            //该用户本次启动后的异常日志用户ID
            CrashReport.setUserId(String.valueOf(anInt));
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    //BottomNavigationViewHelper.java
    @SuppressLint("RestrictedApi")
    public static void disableShiftMode(BottomNavigationView view) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) view.getChildAt(0);
        try {
            Field shiftingMode = menuView.getClass().getDeclaredField("mShiftingMode");
            shiftingMode.setAccessible(true);
            shiftingMode.setBoolean(menuView, false);
            shiftingMode.setAccessible(false);
            for (int i = 0; i < menuView.getChildCount(); i++) {
                BottomNavigationItemView item = (BottomNavigationItemView) menuView.getChildAt(i);
                item.setShiftingMode(false);
                item.setChecked(item.getItemData().isChecked());
            }
        } catch (NoSuchFieldException e) {
            Log.e("BNVHelper", "Unable to get shift mode field", e);
        } catch (IllegalAccessException e) {
            Log.e("BNVHelper", "Unable to change value of shift mode", e);
        }
    }

    /**
     * 新手引导
     */
    public void initNewGuide() {
        NewbieGuide.with(this)
                .setLabel("guide_home")
                .alwaysShow(false)//总是显示，调试时可以打开
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.newbieguide)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView constraintLayout = view.findViewById(R.id.image_view);
                                TextView button = view.findViewById(R.id.button_back);
                                button.setVisibility(View.VISIBLE);
                                button.bringToFront();
                                view.findViewById(R.id.button_back).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        controller.remove();
                                    }
                                });
                                constraintLayout.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_newguide_01));
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.newbieguide)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView constraintLayout = view.findViewById(R.id.image_view);
                                constraintLayout.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_newguide_02));
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.newbieguide)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView constraintLayout = view.findViewById(R.id.image_view);
                                constraintLayout.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_newguide_03));
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.newbieguide)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView constraintLayout = view.findViewById(R.id.image_view);
                                constraintLayout.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_newguide_04));
                            }
                        })
                )
                .addGuidePage(GuidePage.newInstance()
                        .setEverywhereCancelable(true)
                        .setLayoutRes(R.layout.newbieguide)
                        .setOnLayoutInflatedListener(new OnLayoutInflatedListener() {
                            @Override
                            public void onLayoutInflated(View view, Controller controller) {
                                ImageView constraintLayout = view.findViewById(R.id.image_view);
                                constraintLayout.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.icon_newguide_05));
                            }
                        })
                )
                .show();
    }

    private void updateApp() {
        XUpdate.newBuild(this)
                .themeColor(ContextCompat.getColor(this, R.color.color_FF7C28))
                .topResId(R.mipmap.update_background)
                .updateUrl(ApiStores.VERSION_UPDATE)
                .updateParser(new UpdateCustomParser())
                .update();
    }

    @SuppressLint("CommitTransaction")
    private void initView() {
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);

        supportFragmentManager = getSupportFragmentManager();

        disableShiftMode(bottomNavigation);
        bottomNavigation.setItemIconTintList(null);

        initSelectTab(indexTab);

        bottomNavigation.setOnNavigationItemSelectedListener(this::onNavigationItemSelected);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        //TODO  接收其他页面传入的索引,进入指定的页面
        if (intent != null) {
            int intExtrai = intent.getIntExtra(Constant.INDEX, 0);
            initSelectTab(intExtrai);
        }
/*      跳转至指定页面代码
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra(Constant.INDEX, 1);
        startActivity(intent);
*/
// Unable to get provider com.tencent.bugly.beta.utils.BuglyFileProvider: java.lang.ClassNotFoundException: Didn't find class "com.tencent.bugly.beta.utils.BuglyFileProvider" on path: DexPathList[[zip file "/data/app/com.example.lebaobeiimprinter-1.apk"],
// nativeLibraryDirectories=[/data/app-lib/com.example.lebaobeiimprinter-1, /vendor/lib, /system/lib]]
    }

    /**
     * 选中指定页面
     */
    private void initSelectTab(int index) {
        if (index < 0 || index > 3) {
            index = 0;
        }
        fragmentTransaction = supportFragmentManager.beginTransaction();
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        hideAllFragment(fragmentTransaction);
        bottomNavigation.getMenu().getItem(index).setChecked(true);

        indexTab = index;
        switch (index) {
            case 0:
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    fragmentTransaction.add(R.id.frame_layout, homeFragment, HomeFragment.TAG);
                } else {
                    fragmentTransaction.show(homeFragment);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                if (learnCenterFragment == null) {
                    learnCenterFragment = new LearnCenterFragment();
                    fragmentTransaction.add(R.id.frame_layout, learnCenterFragment, LearnCenterFragment.TAG);
                } else {
                    fragmentTransaction.show(learnCenterFragment);
                }
                fragmentTransaction.commit();
                break;
            case 2:
                if (questionBankFragment == null) {
                    questionBankFragment = new QuestionBankFragment();
                    fragmentTransaction.add(R.id.frame_layout, questionBankFragment, QuestionBankFragment.TAG);
                } else {
                    fragmentTransaction.show(questionBankFragment);
                }
                fragmentTransaction.commit();
                break;
            case 3:
                if (mineFragment == null) {
                    mineFragment = new MineFragment();
                    fragmentTransaction.add(R.id.frame_layout, mineFragment, MineFragment.TAG);
                } else {
                    fragmentTransaction.show(mineFragment);
                }
                fragmentTransaction.commit();
                break;
            default:
                break;
        }
    }

    /**
     * 检查权限
     */
    private void checkPermission() {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE),
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                    }
                });
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (homeFragment != null) {
            fragmentTransaction.hide(homeFragment);
        }
        if (classFragment != null) {
            fragmentTransaction.hide(classFragment);
        }
        if (learnCenterFragment != null) {
            fragmentTransaction.hide(learnCenterFragment);
        }
        if (questionBankFragment != null) {
            fragmentTransaction.hide(questionBankFragment);
        }
        if (mineFragment != null) {
            fragmentTransaction.hide(mineFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            /*Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);*/
            exitApplication();
        }
        return false;
    }

    /**
     * 客服
     */
    public void makeCall() {
        SoulPermission.getInstance()
                .checkAndRequestPermission(Manifest.permission.CALL_PHONE, new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {
                        CallUtils.makeCall(MainActivity.this, Constant.SERVICE_NUM);
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        if (permission.shouldRationale()) {
                            new android.support.v7.app.AlertDialog.Builder(MainActivity.this)
                                    .setTitle(getResources().getString(R.string.explication))
                                    .setMessage(getResources().getString(R.string.permission_call))
                                    .setPositiveButton(getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            //用户确定以后，重新执行请求原始流程
                                            SoulPermission.getInstance().goApplicationSettings();
                                        }
                                    }).create().show();
                        } else {
                            Toast.makeText(MainActivity.this, getResources().getString(R.string.permission_explication), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /**
     * 退出程序
     */
    private void exitApplication() {
        new AlertDialog(this).builder()
                .setMsg(getResources().getString(R.string.exit_confirm))
                .setCancelable(false)
                .setCanceledOnTouchOutside(false)
                .setNegativeButton(getResources().getString(R.string.cancel), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton(getResources().getString(R.string.confirm), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActivityUtil.getInstance().finishAllActivity();
                    }
                }).show();

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_home:
                indexTab = 0;
                initSelectTab(indexTab);
                return true;
            case R.id.action_learncenter:
                indexTab = 1;
                initSelectTab(indexTab);
                return true;
            case R.id.action_questionbank:
                indexTab = 2;
                initSelectTab(indexTab);
                return true;
            case R.id.action_mine:
                indexTab = 3;
                initSelectTab(indexTab);
                return true;
            default:
                break;
        }
        return false;
    }
}
