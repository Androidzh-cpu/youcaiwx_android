package com.ucfo.youcaiwx.adapter.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.event.EventListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-12-20.  下午 4:58
 * Package: com.ucfo.youcaiwx.adapter.home
 * FileName: EventListAdapter
 * Description:TODO 活动列表
 */
public class EventListAdapter extends BaseAdapter<EventListBean.DataBean, EventListAdapter.ViewHolder> {
    private Context context;
    private List<EventListBean.DataBean> list;

    public EventListAdapter(Context context, List<EventListBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    public void notifyChange(List<EventListBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @SuppressLint("StringFormatInvalid")
    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        EventListBean.DataBean bean = list.get(position);
        String appImg = bean.getApp_img();
        String endTime = bean.getEnd_time();
        String startTime = bean.getStart_time();
        String name = bean.getName();
        String num = bean.getNum();
        String cpe = bean.getCpe();

        if (!TextUtils.isEmpty(name)) {
            holder.mTitleItem.setText(name);
        }
        if (!TextUtils.isEmpty(num)) {
            if (context != null) {
                String string = context.getResources().getString(R.string.event_countNum, num);
                holder.mCountItem.setText(string);
            }
        }
        if (!TextUtils.isEmpty(cpe)) {
            String string = context.getResources().getString(R.string.event_point, cpe);
            holder.mPointItem.setText(string);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, holder.mImageview, requestOptions);

        holder.mTimeItem.setText(String.valueOf(startTime + "-" + endTime));
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_event, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImageview;
        private TextView mTitleItem;
        private TextView mTimeItem;
        private RoundTextView mCountItem;
        private TextView mPointItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mImageview = (ImageView) itemView.findViewById(R.id.imageview);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
            mCountItem = (RoundTextView) itemView.findViewById(R.id.item_count);
            mPointItem = (TextView) itemView.findViewById(R.id.item_point);
        }
    }

}
