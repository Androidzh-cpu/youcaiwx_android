package com.ucfo.youcaiwx.adapter.learncenter;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-20.  下午 6:58
 * FileName: UnFinishLearnVideoAdapter
 * Description:TODO 未完成学习计划视频适配器
 */
public class UnFinishLearnVideoAdapter extends BaseAdapter {
    private List<UnFinishPlanBean.DataBean.VideoBean> list;
    private Context context;

    public UnFinishLearnVideoAdapter(List<UnFinishPlanBean.DataBean.VideoBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        UnFinishPlanBean.DataBean.VideoBean bean = list.get(position);
        String videoName = bean.getVideo_name();
        TextView textView = new TextView(parent.getContext());
        textView.setGravity(Gravity.START | Gravity.CENTER_VERTICAL);
        textView.setLayoutParams(new GridView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(context, 48)));
        textView.setText(videoName);
        return textView;
    }
}
