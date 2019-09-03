package com.example.dialogtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dialogtest.R;

/**
 * Author: AND
 * Time: 2019-7-30.  下午 6:02
 * Package: com.example.dialogtest.fragment
 * FileName: ThreeFragment
 * Description:TODO
 * Detail:TODO
 */
public class ThreeFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_three, container, false);
        return rootView;
    }
}
