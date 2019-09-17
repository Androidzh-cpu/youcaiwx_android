package com.example.dialogtest.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.dialogtest.R;
import com.warkiz.widget.IndicatorSeekBar;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-7-30.  下午 6:01
 * Package: com.example.dialogtest.fragment
 * FileName: OneFragment
 * Description:TODO
 */
public class OneFragment extends Fragment implements View.OnClickListener {
    private IndicatorSeekBar mSeekbar;
    private ImageView imageView;
    private Button mBtn1;
    private String url = "http://guolin.tech/book.png";

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
        /*SimpleTarget<GlideDrawable> simpleTarget = new SimpleTarget<GlideDrawable>() {
            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation glideAnimation) {
                mSeekbar.setThumbDrawable(resource);
            }
        };
        Glide.with(getActivity()).load(url).placeholder(R.mipmap.ic_launcher_round)
                .transform(new GlideCircleTransform(getActivity(), 3, ContextCompat.getColor(getActivity(), R.color.color_FAA827)))
                .skipMemoryCache(false).priority(Priority.HIGH)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE).into(simpleTarget);*/

    }

    private void initView(@NonNull final View itemView) {
        mSeekbar = (IndicatorSeekBar) itemView.findViewById(R.id.seekbar);
        imageView = (ImageView) itemView.findViewById(R.id.imageview);
        mBtn1 = (Button) itemView.findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn1:
                // TODO 19/09/12
                RequestOptions options = new RequestOptions();
                options.placeholder(R.mipmap.ic_launcher);
                options.error(R.mipmap.ic_launcher);
                //options.transform(new CropCircleWithBorderTransformation());
                options.transform(new RoundedCornersTransformation(20, 0));
                Glide.with(this)
                        .asBitmap()
                        .load(url)
                        .apply(options)
                        .into(imageView);
                Glide
                        .with(this)
                        .load(url)
                        .centerCrop()
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageView);

                break;
            default:
                break;
        }
    }
}
