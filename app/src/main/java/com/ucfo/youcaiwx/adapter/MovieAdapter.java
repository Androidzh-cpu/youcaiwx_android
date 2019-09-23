package com.ucfo.youcaiwx.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.MovieBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author:29117
 * Time: 2019-3-18.  下午 1:39
 * Email:2911743255@qq.com
 * ClassName: MovieAdapter
 * Description:
 * Detail:
 */
public class MovieAdapter extends BaseAdapter<MovieBean.SubjectsBean, MovieAdapter.ViewHolder> {
    private List<MovieBean.SubjectsBean> list;
    private Context context;

    public MovieAdapter(List<MovieBean.SubjectsBean> subjects, Context context) {
        this.list = subjects;
        this.context = context;
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MovieBean.SubjectsBean subjectsBean = list.get(position);
        holder.mMovieTitleItem.setText(subjectsBean.getTitle());
        Glide.with(context)
                .load(subjectsBean.getImages().getSmall())
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .dontAnimate()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .into(holder.mMovieImageItem);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_class_movie, viewGroup, false);
        MovieAdapter.ViewHolder holder = new MovieAdapter.ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mMovieImageItem;
        private TextView mMovieTitleItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mMovieImageItem = (ImageView) itemView.findViewById(R.id.item_movie_image);
            mMovieTitleItem = (TextView) itemView.findViewById(R.id.item_movie_title);
        }
    }
}
