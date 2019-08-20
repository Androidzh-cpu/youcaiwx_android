package com.ucfo.youcaiwx.utils.baseadapter;

import android.view.View;

/**
 * 类名称：
 * 作者：anjun
 * 时间：2017/05/17
 * 功能：item的点击帮助类
 */

public class ItemClickHelper {
    /**
     * Item 点击事件
     */
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    /**
     * Item 长按事件
     */
    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int position);
    }

    public interface ProductOnClickListener {
        void onItmeClick(View v, int position, String prodId, String prodName, String imgUrl, String
                price, String promotionId, int promotionType);
    }
}
