package com.ucfo.youcaiwx.adapter.answer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

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
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        String imageUrl = list.get(position);
        Glide.with(context)
                .load(imageUrl)
                .asBitmap()
                .centerCrop()
                .placeholder(R.mipmap.banner_default)
                .error(R.mipmap.image_loaderror)

                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(holder.iv_image);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_image;

        public ViewHolder(View view) {
            super(view);
            iv_image = (ImageView) view.findViewById(R.id.iv_image);
        }
    }

}