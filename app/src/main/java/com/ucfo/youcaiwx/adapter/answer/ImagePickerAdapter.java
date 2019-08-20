package com.ucfo.youcaiwx.adapter.answer;

/**
 * Author:29117
 * Time: 2019-4-18.  上午 11:41
 * Email:2911743255@qq.com
 * ClassName: ImagePickerAdapter
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.ucfo.youcaiwx.R;

import java.util.List;

/**
 * 片选择的Adapter
 */
public class ImagePickerAdapter extends RecyclerView.Adapter<ImagePickerAdapter.SelectedPicViewHolder> {
    private Context context;
    private OnRecyclerViewItemClickListener listener;
    private List<String> list;

    public interface OnRecyclerViewItemClickListener {
        void onItemClick(View view, int position);

        void onItemDeleteClick(View view, int position);
    }

    public void setOnItemClickListener(OnRecyclerViewItemClickListener listener) {
        this.listener = listener;
    }

    public ImagePickerAdapter(Context context, List<String> data) {
        this.context = context;
        this.list = data;
    }

    @Override
    public SelectedPicViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_imageselectr, parent, false);
        SelectedPicViewHolder selectedPicViewHolder = new SelectedPicViewHolder(inflate);
        return selectedPicViewHolder;
    }

    @Override
    public void onBindViewHolder(SelectedPicViewHolder holder, int position) {
        //设置条目的点击事件
        String item = list.get(position);
        if (getItemCount() == 0) {//适配器数据为空,默认显示添加图片按钮
            holder.iv_img.setImageResource(R.mipmap.app_icon);
            holder.iv_delete.setVisibility(View.GONE);
        } else {
            holder.iv_delete.setVisibility(View.VISIBLE);
            Glide.with(context).load(item).error(R.mipmap.banner_default).into(holder.iv_img);
        }
        holder.iv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemDeleteClick(v, position);
            }
        });
        holder.iv_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onItemClick(v, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class SelectedPicViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv_img;
        private ImageView iv_delete;

        public SelectedPicViewHolder(View itemView) {
            super(itemView);
            iv_img = (ImageView) itemView.findViewById(R.id.iv_image);
            iv_delete = (ImageView) itemView.findViewById(R.id.iv_delete);
        }
    }
}