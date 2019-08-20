package com.ucfo.youcaiwx.view.course.player.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-12.  上午 9:58
 * Email:2911743255@qq.com
 * ClassName: SpeedListviewWindow
 */
public class SpeedListviewWindow {

    private final SpeedAdapter mAdapter;

    //adapter的数据源
    private List<Float> mSpeedItems;
    //当前播放的倍速，高亮显示
    private float currentSpeed;
    //倍速项的点击事件
    private OnSpeedClickListener onSpeedClickListener;

    private Activity context;
    private PopupWindow popupWindow;
    private ListView listView;


    public SpeedListviewWindow(Activity context) {
        this.context = context;
        mSpeedItems = new ArrayList<>();
        mSpeedItems.add(0.5f);
        mSpeedItems.add(1.0f);
        mSpeedItems.add(1.5f);
        mSpeedItems.add(2.0f);

        RelativeLayout view = new RelativeLayout(context);

        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dp2px(3), 0, DensityUtil.dp2px(3));
        view.addView(listView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        mAdapter = new SpeedAdapter();

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //不显示滚动条，保证全部被显示
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //回调监听
                if (onSpeedClickListener != null && mSpeedItems != null) {
                    onSpeedClickListener.onSpeedClick(mSpeedItems.get(position));
                    listView.invalidate();
                }
                dismiss();
            }
        });
        popupWindow = new PopupWindow(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(178, 0, 0, 0)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    /**
     * 设置清晰度
     * 所有支持的清晰度
     * 当前的清晰度
     */
    public void setCurrentSpeed(float mcurrentSpeed) {
        currentSpeed = mcurrentSpeed;
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置清晰度点击监听
     */
    public void setOnSpeedClickListener(OnSpeedClickListener l) {
        onSpeedClickListener = l;
    }


    public interface OnSpeedClickListener {
        /**
         * 清晰度点击事件
         * 点中的清晰度
         */
        void onSpeedClick(float quality);
    }

    public void showAsDropDown(View parent) {

        popupWindow.setWidth(DensityUtil.dip2px(context, 50));
        popupWindow.setHeight(DensityUtil.dip2px(context, 28 * mSpeedItems.size()));

        popupWindow.showAsDropDown(parent, 0, 10);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();

        //int[] location = new int[2];
        //parent.getLocationOnScreen(location);
        //popupWindow.showAtLocation(parent, Gravity.NO_GRAVITY, location[0], location[1] - popupWindow.getHeight());

    }

    public void dismiss() {
        popupWindow.dismiss();
    }


    private final class SpeedAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mSpeedItems != null) {
                return mSpeedItems.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mSpeedItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layoutView = new RelativeLayout(context);
            if (mSpeedItems != null) {
                float speed = mSpeedItems.get(position);

                TextView textView = new TextView(context);
                textView.setTextSize(15);
                textView.setText(String.valueOf(speed + "X"));
                textView.setTag(position);

                //默认白色，当前清晰度为主题色。
                if (speed == currentSpeed) {
                    textView.setTextColor(ContextCompat.getColor(context,R.color.alivc_blue));
                } else {
                    textView.setTextColor(ContextCompat.getColor(context,R.color.colorWhite));
                }

                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.CENTER_IN_PARENT);
                layoutView.addView(textView, params);
                layoutView.setMinimumHeight(DensityUtil.dp2px(26));
            }
            return layoutView;
        }

    }


}
