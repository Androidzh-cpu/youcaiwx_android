package com.ucfo.youcai.utils.baseadapter;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * Author:29117
 * Time: 2019-3-18.  上午 11:02
 * Email:2911743255@qq.com
 * ClassName: SpacesItemDecoration
 * Package: com.ucfo.youcai.utils.baseadapter
 * Description:TODO 二种分割线
 * Detail:
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {

    private SpacesItemDecorationEntrust mEntrust;
    private int mColor;
    private int leftRight;
    private int topBottom;


    public SpacesItemDecoration(int leftRight, int topBottom) {
        this.leftRight = leftRight;
        this.topBottom = topBottom;
    }

    public SpacesItemDecoration(int leftRight, int topBottom, int mColor) {
        this(leftRight, topBottom);
        this.mColor = mColor;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mEntrust == null) {
            mEntrust = getEntrust(parent.getLayoutManager());
        }
        mEntrust.onDraw(c, parent, state);
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        if (mEntrust == null) {
            mEntrust = getEntrust(parent.getLayoutManager());
        }
        mEntrust.getItemOffsets(outRect, view, parent, state);
    }

    private SpacesItemDecorationEntrust getEntrust(RecyclerView.LayoutManager manager) {
        SpacesItemDecorationEntrust entrust = null;
        //要注意这边的GridLayoutManager是继承LinearLayoutManager，所以要先判断GridLayoutManager
        if (manager instanceof GridLayoutManager) {
            entrust = new GridEntrust(leftRight, topBottom, mColor);
        } else if (manager instanceof StaggeredGridLayoutManager) {
            entrust = new StaggeredGridEntrust(leftRight, topBottom, mColor);
        } else {//其他的都当做Linear来进行计算
            entrust = new LinearEntrust(leftRight, topBottom, mColor);
        }
        return entrust;
    }

    public class GridEntrust extends SpacesItemDecorationEntrust {


        public GridEntrust(int leftRight, int topBottom, int mColor) {
            super(leftRight, topBottom, mColor);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            final GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            final GridLayoutManager.SpanSizeLookup lookup = layoutManager.getSpanSizeLookup();

            if (mDivider == null || layoutManager.getChildCount() == 0) {
                return;
            }
            //判断总的数量是否可以整除
            int spanCount = layoutManager.getSpanCount();
            int left, right, top, bottom;
            final int childCount = parent.getChildCount();
            if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    //将带有颜色的分割线处于中间位置
                    final float centerLeft = ((float) (layoutManager.getLeftDecorationWidth(child) + layoutManager.getRightDecorationWidth(child))
                            * spanCount / (spanCount + 1) + 1 - leftRight) / 2;
                    final float centerTop = (layoutManager.getBottomDecorationHeight(child) + 1 - topBottom) / 2;
                    //得到它在总数里面的位置
                    final int position = parent.getChildAdapterPosition(child);
                    //获取它所占有的比重
                    final int spanSize = lookup.getSpanSize(position);
                    //获取每排的位置
                    final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                    //判断是否为第一排
                    boolean isFirst = layoutManager.getSpanSizeLookup().getSpanGroupIndex(position, spanCount) == 0;
                    //画上边的，第一排不需要上边的,只需要在最左边的那项的时候画一次就好
                    if (!isFirst && spanIndex == 0) {
                        left = layoutManager.getLeftDecorationWidth(child);
                        right = parent.getWidth() - layoutManager.getLeftDecorationWidth(child);
                        top = (int) (child.getTop() - centerTop) - topBottom;
                        bottom = top + topBottom;
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
                    }
                    //最右边的一排不需要右边的
                    boolean isRight = spanIndex + spanSize == spanCount;
                    if (!isRight) {
                        //计算右边的
                        left = (int) (child.getRight() + centerLeft);
                        right = left + leftRight;
                        top = child.getTop();
                        if (!isFirst) {
                            top -= centerTop;
                        }
                        bottom = (int) (child.getBottom() + centerTop);
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
                    }
                }
            } else {
                for (int i = 0; i < childCount; i++) {
                    final View child = parent.getChildAt(i);
                    //将带有颜色的分割线处于中间位置
                    final float centerLeft = (layoutManager.getRightDecorationWidth(child) + 1 - leftRight) / 2;
                    final float centerTop = ((float) (layoutManager.getTopDecorationHeight(child) + layoutManager.getBottomDecorationHeight(child))
                            * spanCount / (spanCount + 1) - topBottom) / 2;
                    //得到它在总数里面的位置
                    final int position = parent.getChildAdapterPosition(child);
                    //获取它所占有的比重
                    final int spanSize = lookup.getSpanSize(position);
                    //获取每排的位置
                    final int spanIndex = lookup.getSpanIndex(position, layoutManager.getSpanCount());
                    //判断是否为第一列
                    boolean isFirst = layoutManager.getSpanSizeLookup().getSpanGroupIndex(position, spanCount) == 0;
                    //画左边的，第一排不需要左边的,只需要在最上边的那项的时候画一次就好
                    if (!isFirst && spanIndex == 0) {
                        left = (int) (child.getLeft() - centerLeft) - leftRight;
                        right = left + leftRight;
                        top = layoutManager.getRightDecorationWidth(child);
                        bottom = parent.getHeight() - layoutManager.getTopDecorationHeight(child);
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
                    }
                    //最下的一排不需要下边的
                    boolean isRight = spanIndex + spanSize == spanCount;
                    if (!isRight) {
                        //计算右边的
                        left = child.getLeft();
                        if (!isFirst) {
                            left -= centerLeft;
                        }
                        right = (int) (child.getRight() + centerTop);
                        top = (int) (child.getBottom() + centerLeft);
                        bottom = top + leftRight;
                        mDivider.setBounds(left, top, right, bottom);
                        mDivider.draw(c);
                    }
                }
            }
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            GridLayoutManager layoutManager = (GridLayoutManager) parent.getLayoutManager();
            final GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
            final int childPosition = parent.getChildAdapterPosition(view);
            final int spanCount = layoutManager.getSpanCount();

            if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
                //判断是否在第一排
                if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {//第一排的需要上面
                    outRect.top = topBottom;
                }
                outRect.bottom = topBottom;
                //这里忽略和合并项的问题，只考虑占满和单一的问题
                if (lp.getSpanSize() == spanCount) {//占满
                    outRect.left = leftRight;
                    outRect.right = leftRight;
                } else {
                    outRect.left = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * leftRight);
                    outRect.right = (int) (((float) leftRight * (spanCount + 1) / spanCount) - outRect.left);
                }
            } else {
                if (layoutManager.getSpanSizeLookup().getSpanGroupIndex(childPosition, spanCount) == 0) {//第一排的需要left
                    outRect.left = leftRight;
                }
                outRect.right = leftRight;
                //这里忽略和合并项的问题，只考虑占满和单一的问题
                if (lp.getSpanSize() == spanCount) {//占满
                    outRect.top = topBottom;
                    outRect.bottom = topBottom;
                } else {
                    outRect.top = (int) (((float) (spanCount - lp.getSpanIndex())) / spanCount * topBottom);
                    outRect.bottom = (int) (((float) topBottom * (spanCount + 1) / spanCount) - outRect.top);
                }
            }
        }


    }

    public class LinearEntrust extends SpacesItemDecorationEntrust {
        public LinearEntrust(int leftRight, int topBottom, int mColor) {
            super(leftRight, topBottom, mColor);
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            //没有子view或者没有没有颜色直接return
            if (mDivider == null || layoutManager.getChildCount() == 0) {
                return;
            }
            int left;
            int right;
            int top;
            int bottom;
            final int childCount = parent.getChildCount();
            if (layoutManager.getOrientation() == GridLayoutManager.VERTICAL) {
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    //将有颜色的分割线处于中间位置
                    final float center = (layoutManager.getTopDecorationHeight(child) + 1 - topBottom) / 2;
                    //计算下边的
                    left = layoutManager.getLeftDecorationWidth(child);
                    right = parent.getWidth() - layoutManager.getLeftDecorationWidth(child);
                    top = (int) (child.getBottom() + center);
                    bottom = top + topBottom;
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            } else {
                for (int i = 0; i < childCount - 1; i++) {
                    final View child = parent.getChildAt(i);
                    final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    //将有颜色的分割线处于中间位置
                    final float center = (layoutManager.getLeftDecorationWidth(child) + 1 - leftRight) / 2;
                    //计算右边的
                    left = (int) (child.getRight() + center);
                    right = left + leftRight;
                    top = layoutManager.getTopDecorationHeight(child);
                    bottom = parent.getHeight() - layoutManager.getTopDecorationHeight(child);
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(c);
                }
            }

        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            LinearLayoutManager layoutManager = (LinearLayoutManager) parent.getLayoutManager();
            //竖直方向的
            if (layoutManager.getOrientation() == LinearLayoutManager.VERTICAL) {
                //最后一项需要 bottom
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.bottom = topBottom;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.right = leftRight;
            } else {
                //最后一项需要right
                if (parent.getChildAdapterPosition(view) == layoutManager.getItemCount() - 1) {
                    outRect.right = leftRight;
                }
                outRect.top = topBottom;
                outRect.left = leftRight;
                outRect.bottom = topBottom;
            }
        }


    }

}
