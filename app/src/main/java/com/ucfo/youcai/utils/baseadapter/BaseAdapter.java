package com.ucfo.youcai.utils.baseadapter;

import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: anjun
 * Created on: 2017/1/5
 * recycleview的base类、
 */

public abstract class BaseAdapter<T, V extends ViewHolder> extends Adapter<V> {
    private List<T> mData;

    // 点击监听
    private ItemClickHelper.OnItemClickListener mOnItemClickListener;
    // 长按监听
    private ItemClickHelper.OnItemLongClickListener mOnItemLongClickListener;

    public BaseAdapter() {
        this(null);
    }

    public BaseAdapter(List<T> data) {
        this.mData = new ArrayList();
        if (null != data) {
            mData.addAll(data);
        }
    }

    @Override
    public V onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        V viewHolder = onCreateDataViewHolder(viewGroup, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(V holder, final int position) {
        // 如果点击监听不为null 就设置点击事件
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mOnItemClickListener) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            }
        });

        // 如果长按监听不为null 就设置长按事件
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (null != mOnItemLongClickListener) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
                return false;
            }
        });

        onBindDataViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return null == mData ? 0 : mData.size();
    }

    @Override
    public void onViewAttachedToWindow(V holder) {
        super.onViewAttachedToWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onViewDetachedFromWindow(V holder) {
        super.onViewDetachedFromWindow(holder);
    }

    /**
     * 绑定ViewHolder，由于每个适配器不一定相同，所以由子类去实现
     *
     * @param holder
     * @param position
     */
    protected abstract void onBindDataViewHolder(V holder, int position);

    /**
     * 创建ViewHolder，由于每个适配器不一定相同，所以由子类去实现
     *
     * @param viewGroup
     * @param itemType
     * @return
     */
    public abstract V onCreateDataViewHolder(ViewGroup viewGroup, int
            itemType);

    /**
     * 根据position 获取具体条目
     *
     * @param position 索引位置
     * @return
     */
    public T getItem(int position) {
        return mData.get(position);
    }

    /**
     * 追加数据
     *
     * @param list
     */
    public void appendData(List<T> list) {
        if (null != list) {
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    public void clearDataAndSetData(List<T> list) {
        if (null != list) {
            mData.clear();
            mData.addAll(list);
            notifyDataSetChanged();
        }
    }

    /**
     * 添加一条数据
     *
     * @param position 要添加的位置，如果要添加到最后，请传小于0的值
     * @param data     要添加的数据
     */
    public void appendData(int position, T data) {
        int tempPos = position >= 0 ? position : mData.size();
        mData.add(position, data);
        notifyItemInserted(tempPos);
    }

    /**
     * 移除某一条数据
     *
     * @param position 位置
     */
    public void removeData(int position) {
        if (null != mData) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    /**
     * 移除某一部分数据
     *
     * @param data 数据集合
     */
    public void removeData(List<T> data) {
        if (null != mData) {
            mData.removeAll(data);
            notifyDataSetChanged();
        }
    }

    public List<T> getData() {
        return mData;
    }

    public void setData(List<T> data) {
        this.mData = data;
    }

    /**
     * 设置点击事件
     *
     * @param onItemClick
     */
    public void setOnItemClick(ItemClickHelper.OnItemClickListener onItemClick) {
        this.mOnItemClickListener = onItemClick;
    }

    /**
     * 设置长按事件
     *
     * @param onItemLongClick
     */

    public void setOnItemLongClick(ItemClickHelper.OnItemLongClickListener onItemLongClick) {
        this.mOnItemLongClickListener = onItemLongClick;
    }


}
