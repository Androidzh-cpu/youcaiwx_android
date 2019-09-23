package com.ucfo.youcaiwx.adapter.home;

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
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.HomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author:AND
 * Time: 2019/3/14.  15:37
 * Email:2911743255@qq.com
 * ClassName: HomeLiveAdapter
 * Description:首页直播推荐
 * Detail:
 */
public class HomeLiveAdapter extends BaseAdapter<HomeBean.DataBean.BroadcastBean, HomeLiveAdapter.ViewHolder> {
    private Context context;
    private List<HomeBean.DataBean.BroadcastBean> list;

    public HomeLiveAdapter(List<HomeBean.DataBean.BroadcastBean> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        HomeBean.DataBean.BroadcastBean bean = list.get(position);
        String app_image = bean.getApp_image();
        String price = bean.getPrice();
        String teacher_name = bean.getTeacher_name();
        String title = bean.getTitle();
        if (!TextUtils.isEmpty(app_image)) {
            RequestOptions requestOptions = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, app_image, holder.mLiveImageItem, requestOptions);
        }
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mLiveAuthorItem.setText("讲师: " + teacher_name);
        }
        if (!TextUtils.isEmpty(title)) {
            holder.mLiveTitleItem.setText(title);
        }
        if (!TextUtils.isEmpty(price)) {
            holder.mLivePriceItem.setText("￥" + price);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_home_live, viewGroup, false);
        ViewHolder holder = new ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mLiveImageItem;
        private TextView mLiveTitleItem;
        private TextView mLiveAuthorItem;
        private TextView mLivePriceItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mLiveImageItem = (ImageView) itemView.findViewById(R.id.item_live_image);
            mLiveTitleItem = (TextView) itemView.findViewById(R.id.item_live_title);
            mLiveAuthorItem = (TextView) itemView.findViewById(R.id.item_live_author);
            mLivePriceItem = (TextView) itemView.findViewById(R.id.item_live_price);
        }
    }
}
