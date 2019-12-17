package com.ucfo.youcaiwx.adapter.answer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 6:09
 * Email:2911743255@qq.com
 * ClassName: PictureAdapter
 * Description:TODO  答疑列表的图片集合,可点击预览
 */
public class PictureAdapter extends BaseAdapter<String, PictureAdapter.ViewHolder> {

    private List<String> list;
    private Context context;

    public PictureAdapter(List<String> url, Context context) {
        this.list = url;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_answer_pictureitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        String imageUrl = list.get(position);

        if (Constant.ISTEST_ENVIRONMENT) {
            //实在是TNM丑了,线上的爱咋咋地吧
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new CenterCrop(), new RoundedCorners(DensityUtil.dp2px(2)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, imageUrl, holder.iv_image, requestOptions);
        } else {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror)
                    .transform(new RoundedCorners(DensityUtil.dp2px(2)))
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
            GlideUtils.load(context, imageUrl, holder.iv_image, requestOptions);
        }
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_image;

        public ViewHolder(View view) {
            super(view);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }

}