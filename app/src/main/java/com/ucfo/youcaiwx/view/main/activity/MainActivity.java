package com.ucfo.youcaiwx.view.main.activity;

import android.Manifest;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.bean.Permissions;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.qw.soul.permission.callbcak.CheckRequestPermissionsListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.ActivityUtil;
import com.ucfo.youcaiwx.utils.CallUtils;
import com.ucfo.youcaiwx.utils.systemutils.StatusBarUtil;
import com.ucfo.youcaiwx.utils.systemutils.StatusbarUI;
import com.ucfo.youcaiwx.utils.update.UpdateCustomParser;
import com.ucfo.youcaiwx.view.main.fragment.ClassFragment;
import com.ucfo.youcaiwx.view.main.fragment.HomeFragment;
import com.ucfo.youcaiwx.view.main.fragment.LearnCenterFragment;
import com.ucfo.youcaiwx.view.main.fragment.MineFragment;
import com.ucfo.youcaiwx.view.main.fragment.QuestionBankFragment;
import com.ucfo.youcaiwx.widget.dialog.AlertDialog;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.xuexiang.xupdate.XUpdate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_REQUESTCODE = 1;
    //申请权限组
    final String[] permissions = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.CAMERA,
            Manifest.permission.READ_PHONE_STATE};
    @BindView(R.id.frame_layout)
    FrameLayout frameLayout;
    @BindView(R.id.tab_home)
    RadioButton tabHome;
    @BindView(R.id.tab_learncenter)
    RadioButton tabLearncenter;
    @BindView(R.id.tab_questionbank)
    RadioButton tabQuestionbank;
    @BindView(R.id.tab_mine)
    RadioButton tabMine;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    private int state = 1;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager supportFragmentManager;
    private HomeFragment homeFragment;
    private ClassFragment classFragment;
    private LearnCenterFragment learnCenterFragment;
    private QuestionBankFragment questionBankFragment;
    private MineFragment mineFragment;
    private MainActivity context;
    private long lastClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StatusBarUtil.immersive(this);

        StatusbarUI.setStatusBarUIMode(MainActivity.this, Color.TRANSPARENT, true);

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
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    private void updateApp() {
        XUpdate.newBuild(this)
                .themeColor(ContextCompat.getColor(this, R.color.color_FF7C28))
                .topResId(R.mipmap.update_background)
                .updateUrl(ApiStores.VERSION_UPDATE)
                .updateParser(new UpdateCustomParser())
                .update();
    }

    private void initView() {
        //TODO  接收其他页面传入的索引,进入指定的页面
        if (getIntent().getStringExtra(Constant.STATE) != null) {
            state = Integer.parseInt(getIntent().getStringExtra(Constant.STATE));
        }
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();

        homeFragment = new HomeFragment();
        fragmentTransaction.add(R.id.frame_layout, homeFragment, HomeFragment.TAG);
        fragmentTransaction.commit();
        tabHome.setChecked(true);
        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                fragmentTransaction = supportFragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                switch (checkedId) {
                    case R.id.tab_home://TODO 首页
                        state = 1;
                        tabHome.setChecked(true);
                        if (homeFragment == null) {
                            homeFragment = new HomeFragment();
                            fragmentTransaction.add(R.id.frame_layout, homeFragment);
                        } else {
                            fragmentTransaction.show(homeFragment);
                        }
                        break;
                    case R.id.tab_learncenter:
                        state = 2;
                        tabLearncenter.setChecked(true);
                        if (learnCenterFragment == null) {
                            learnCenterFragment = new LearnCenterFragment();
                            fragmentTransaction.add(R.id.frame_layout, learnCenterFragment);
                        } else {
                            fragmentTransaction.show(learnCenterFragment);
                        }
                        break;
                    case R.id.tab_questionbank:
                        state = 3;
                        tabQuestionbank.setChecked(true);
                        if (questionBankFragment == null) {
                            questionBankFragment = new QuestionBankFragment();
                            fragmentTransaction.add(R.id.frame_layout, questionBankFragment);
                        } else {
                            fragmentTransaction.show(questionBankFragment);
                        }
                        break;
                    case R.id.tab_mine:
                        state = 4;
                        tabMine.setChecked(true);
                        if (mineFragment == null) {
                            mineFragment = new MineFragment();
                            fragmentTransaction.add(R.id.frame_layout, mineFragment);
                        } else {
                            fragmentTransaction.show(mineFragment);
                        }
                        break;
                    default:
                        break;
                }
                fragmentTransaction.commit();
            }
        });
    }

    //TODO 检查权限
    private void checkPermission() {
        SoulPermission.getInstance().checkAndRequestPermissions(
                Permissions.build(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE),
                new CheckRequestPermissionsListener() {
                    @Override
                    public void onAllPermissionOk(Permission[] allPermissions) {
                    }

                    @Override
                    public void onPermissionDenied(Permission[] refusedPermissions) {
                    }
                });
    }

    //TODO 隐藏所有Fragment
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
                        finish();
                    }
                }).show();

    }

}
