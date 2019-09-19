package com.ucfo.youcaiwx.module.course.player.widget;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.aliyun.vodplayer.media.IAliyunVodPlayer;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.LinkedList;
import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-11.  下午 3:56
 * Email:2911743255@qq.com
 * ClassName: QualityListviewWindow
 * Description:TODO  播放器清晰度选择
 */
public class QualityListviewWindow {

    private final QualityAdapter mAdapter;

    //adapter的数据源
    private List<String> mQualityItems;
    //是否是mts源
    private boolean isMtsSource = false;
    //当前播放的清晰度，高亮显示
    private String currentQuality;
    //清晰度项的点击事件
    private OnQualityClickListener mOnQualityClickListener;

    private Activity context;
    private PopupWindow popupWindow;
    private ListView listView;


    public QualityListviewWindow(Activity context) {
        this.context = context;

        RelativeLayout view = new RelativeLayout(context);

        listView = new ListView(context);
        listView.setPadding(0, DensityUtil.dp2px(3), 0, DensityUtil.dp2px(3));
        view.addView(listView, new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));

        mAdapter = new QualityAdapter();

        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        //不显示滚动条，保证全部被显示
        listView.setVerticalScrollBarEnabled(false);
        listView.setHorizontalScrollBarEnabled(false);

        listView.setAdapter(mAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //回调监听
                if (mOnQualityClickListener != null && mQualityItems != null) {
                    mOnQualityClickListener.onQualityClick(mQualityItems.get(position));
                    listView.invalidate();
                }
                dismiss();
            }
        });
        popupWindow = new PopupWindow(view);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.argb(178, 0, 0, 0)));
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

    }

    private List<String> sortQuality(List<String> qualities) {
        //MTS的源不需要排序
        if (isMtsSource) {
            return qualities;
        }
        String ld = null, sd = null, hd = null, fd = null, k2 = null, k4 = null, od = null;
        for (String quality : qualities) {
            if (IAliyunVodPlayer.QualityValue.QUALITY_FLUENT.equals(quality)) {
                fd = IAliyunVodPlayer.QualityValue.QUALITY_FLUENT;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_LOW.equals(quality)) {
                ld = IAliyunVodPlayer.QualityValue.QUALITY_LOW;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_STAND.equals(quality)) {
                sd = IAliyunVodPlayer.QualityValue.QUALITY_STAND;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_HIGH.equals(quality)) {
                hd = IAliyunVodPlayer.QualityValue.QUALITY_HIGH;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_2K.equals(quality)) {
                k2 = IAliyunVodPlayer.QualityValue.QUALITY_2K;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_4K.equals(quality)) {
                k4 = IAliyunVodPlayer.QualityValue.QUALITY_4K;
            } else if (IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL.equals(quality)) {
                od = IAliyunVodPlayer.QualityValue.QUALITY_ORIGINAL;
            }
        }

        //清晰度按照fd,ld,sd,hd,2k,4k,od排序
        List<String> sortedQuality = new LinkedList<String>();
        if (!TextUtils.isEmpty(fd)) {
            sortedQuality.add(fd);
        }

        if (!TextUtils.isEmpty(ld)) {
            sortedQuality.add(ld);
        }
        if (!TextUtils.isEmpty(sd)) {
            sortedQuality.add(sd);
        }
        if (!TextUtils.isEmpty(hd)) {
            sortedQuality.add(hd);
        }

        if (!TextUtils.isEmpty(k2)) {
            sortedQuality.add(k2);
        }
        if (!TextUtils.isEmpty(k4)) {
            sortedQuality.add(k4);
        }
        if (!TextUtils.isEmpty(od)) {
            sortedQuality.add(od);
        }
        return sortedQuality;
    }

    /**
     * 设置清晰度
     * 所有支持的清晰度
     * 当前的清晰度
     */
    public void setQuality(List<String> qualities, String mcurrentQuality) {
        //排序之后显示出来
        mQualityItems = sortQuality(qualities);
        currentQuality = mcurrentQuality;
        if (mAdapter != null) {
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 设置清晰度点击监听
     */
    public void setOnQualityClickListener(OnQualityClickListener l) {
        mOnQualityClickListener = l;
    }


    public interface OnQualityClickListener {
        /**
         * 清晰度点击事件
         * 点中的清晰度
         */
        void onQualityClick(String quality);
    }

    public void showAsDropDown(View parent) {

        popupWindow.setWidth(DensityUtil.dip2px(context, 50));
        popupWindow.setHeight(DensityUtil.dip2px(context, 28 * mQualityItems.size()));

        popupWindow.showAsDropDown(parent, 0, 0);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    public void dismiss() {
        popupWindow.dismiss();
    }


    private final class QualityAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mQualityItems != null) {
                return mQualityItems.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            return mQualityItems.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            RelativeLayout layoutView = new RelativeLayout(context);
            if (mQualityItems != null) {
                String quality = mQualityItems.get(position);

                TextView textView = new TextView(context);
                textView.setTextSize(15);
                textView.setText(QualityItem.getItem(context, quality, isMtsSource).getName());
                textView.setTag(position);

                //默认白色，当前清晰度为主题色。
                if (quality.equals(currentQuality)) {
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

