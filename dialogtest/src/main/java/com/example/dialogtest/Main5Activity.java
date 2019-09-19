package com.example.dialogtest;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.internal.BottomNavigationMenuView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.example.dialogtest.fragment.FourFragment;
import com.example.dialogtest.fragment.OneFragment;
import com.example.dialogtest.fragment.ThreeFragment;
import com.example.dialogtest.fragment.TwoFragment;
import com.jaeger.library.StatusBarUtil;

import java.lang.reflect.Field;

public class Main5Activity extends AppCompatActivity {

    private BottomNavigationView mBottomNavigationView;
    private ViewPager mViewpager;
    private FrameLayout mFramelayout;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    private OneFragment oneFragment;
    private TwoFragment twoFragment;
    private ThreeFragment threeFragment;
    private FourFragment fourFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        initView();

        initData();

        StatusBarUtil.setLightMode(this);
    }

    private void initData() {
        supportFragmentManager = getSupportFragmentManager();
        fragmentTransaction = supportFragmentManager.beginTransaction();

        disableShiftMode(mBottomNavigationView);
        mBottomNavigationView.setItemIconTintList(null);


        initSelectTab(0);

        mBottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                fragmentTransaction = supportFragmentManager.beginTransaction();
                hideAllFragment(fragmentTransaction);
                switch (item.getItemId()) {
                    case R.id.action_home:
                        if (oneFragment == null) {
                            oneFragment = new OneFragment();
                            fragmentTransaction.add(R.id.framelayout, oneFragment);
                        } else {
                            fragmentTransaction.show(oneFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    case R.id.action_learncenter:
                        if (twoFragment == null) {
                            twoFragment = new TwoFragment();
                            fragmentTransaction.add(R.id.framelayout, twoFragment);
                        } else {
                            fragmentTransaction.show(twoFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    case R.id.action_questionbank:
                        if (threeFragment == null) {
                            threeFragment = new ThreeFragment();
                            fragmentTransaction.add(R.id.framelayout, threeFragment);
                        } else {
                            fragmentTransaction.show(threeFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    case R.id.action_mine:
                        if (fourFragment == null) {
                            fourFragment = new FourFragment();
                            fragmentTransaction.add(R.id.framelayout, fourFragment);
                        } else {
                            fragmentTransaction.show(fourFragment);
                        }
                        fragmentTransaction.commit();
                        return true;
                    default:
                        break;
                }
                return false;
            }
        });
    }

    private void initSelectTab(int position) {
        mBottomNavigationView.getMenu().getItem(position).setChecked(true);
        switch (position) {
            case 0:
                if (oneFragment == null) {
                    oneFragment = new OneFragment();
                    fragmentTransaction.add(R.id.framelayout, oneFragment, "home");
                } else {
                    fragmentTransaction.show(oneFragment);
                }
                fragmentTransaction.commit();
                break;
            case 1:
                if (twoFragment == null) {
                    twoFragment = new TwoFragment();
                    fragmentTransaction.add(R.id.framelayout, twoFragment, "learn");
                } else {
                    fragmentTransaction.show(twoFragment);
                }
                fragmentTransaction.commit();
                break;
            case 2:
                if (threeFragment == null) {
                    threeFragment = new ThreeFragment();
                    fragmentTransaction.add(R.id.framelayout, threeFragment, "question");
                } else {
                    fragmentTransaction.show(threeFragment);
                }
                fragmentTransaction.commit();
                break;
            case 3:
                if (fourFragment == null) {
                    fourFragment = new FourFragment();
                    fragmentTransaction.add(R.id.framelayout, fourFragment, "mine");
                } else {
                    fragmentTransaction.show(fourFragment);
                }
                fragmentTransaction.commit();
                break;
            default:
                break;
        }

    }

    //TODO 隐藏所有Fragment
    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (oneFragment != null) {
            fragmentTransaction.hide(oneFragment);
        }
        if (twoFragment != null) {
            fragmentTransaction.hide(twoFragment);
        }
        if (threeFragment != null) {
            fragmentTransaction.hide(threeFragment);
        }
        if (fourFragment != null) {
            fragmentTransaction.hide(fourFragment);
        }
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

    private void initView() {
        mBottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        mFramelayout = (FrameLayout) findViewById(R.id.framelayout);
    }

}
