package com.example.dialogtest.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.dialogtest.fragment.FourFragment;
import com.example.dialogtest.fragment.OneFragment;
import com.example.dialogtest.fragment.ThreeFragment;
import com.example.dialogtest.fragment.TwoFragment;

/**
 * Author: AND
 * Time: 2019-9-2.  下午 5:59
 * Package: com.example.dialogtest.adapter
 * FileName: ViewPagerHomeAdapter
 * Description:TODO
 */
public class ViewPagerHomeAdapter extends FragmentPagerAdapter {

    public ViewPagerHomeAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new OneFragment();
            case 1:
                return new TwoFragment();
            case 2:
                return new ThreeFragment();
            case 3:
                return new FourFragment();
            default:
                break;
        }
        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }
}
