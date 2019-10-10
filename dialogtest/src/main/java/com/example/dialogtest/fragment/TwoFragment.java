package com.example.dialogtest.fragment;

import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.airbnb.lottie.LottieAnimationView;
import com.example.dialogtest.R;

import java.lang.reflect.Field;

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
        mAnimationview.setAnimation("data.json");
        mAnimationview.playAnimation();
    }

    private void initView(@NonNull final View itemView) {
        mAnimationview = (LottieAnimationView) itemView.findViewById(R.id.animationview);
    }

    public void setTypeface() {
        AssetManager assets = getActivity().getAssets();
        Typeface typeFace = Typeface.createFromAsset(assets, "fonts/Microsoft YaHei.TTF");
        try {
            Field field = Typeface.class.getDeclaredField("SERIF");
            field.setAccessible(true);
            field.set(null, typeFace);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
