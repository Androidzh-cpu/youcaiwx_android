package com.example.dialogtest.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dialogtest.R;

/**
 * Author: AND
 * Time: 2019-7-30.  下午 6:02
 * Package: com.example.dialogtest.fragment
 * FileName: TwoFragment
 * Description:TODO
 */
public class TwoFragment extends Fragment {

    private LottieAnimationView mAnimationview;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_two, container, false);
        initView(rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
    }

    private void initData() {
        mAnimationview.setAnimation("atm_link.json");
        mAnimationview.playAnimation();
    }

    private void initView(@NonNull final View itemView) {
        mAnimationview = (LottieAnimationView) itemView.findViewById(R.id.animationview);
    }
}
