package com.example.dialogtest.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.dialogtest.R;
import com.example.dialogtest.utils.GlideCircleTransform;
import com.warkiz.widget.IndicatorSeekBar;

/**
 * Author: AND
 * Time: 2019-7-30.  下午 6:01
 * Package: com.example.dialogtest.fragment
 * FileName: OneFragment
 * Description:TODO
 */
public class OneFragment extends Fragment {
    private IndicatorSeekBar mSeekbar;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_one, container, false);
        initView(rootView);
        return rootView;
    }

    @SuppressLint("ResourceType")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String url = "http://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190826/26fd3f42d10d4d9705d9d4ad63a63276.jpeg";
        SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                mSeekbar.setThumbDrawable(resource);
            }
        };
        Glide.with(getActivity()).load(url).placeholder(R.mipmap.ic_launcher_round)
                .transform(new GlideCircleTransform(getActivity(), 3, ContextCompat.getColor(getActivity(), R.color.color_FAA827)))
                .skipMemoryCache(false).priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(simpleTarget);
    }

    private void initView(@NonNull final View itemView) {
        mSeekbar = (IndicatorSeekBar) itemView.findViewById(R.id.seekbar);
    }

}
