package com.ucfo.youcaiwx.adapter.learncenter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.learncenter.UnFinishPlanBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;
import com.ucfo.youcaiwx.widget.customview.NestedListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-24.  下午 3:36
 * FileName: UnFinishLearnPlan2Adapter
 * Description:TODO
 */
public class UnFinishLearnPlan2Adapter extends BaseAdapter<UnFinishPlanBean.DataBean, UnFinishLearnPlan2Adapter.ViewHolder> {
    private List<UnFinishPlanBean.DataBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public UnFinishLearnPlan2Adapter(List<UnFinishPlanBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        final boolean[] expandGroup = {false};
        UnFinishPlanBean.DataBean dataBean = list.get(position);
        String plan_name = dataBean.getPlan_name();
        if (!TextUtils.isEmpty(plan_name)) {
            holder.mTitleItem.setText(dataBean.getPlan_name());
        }
        holder.mBtnToStudy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });
        List<UnFinishPlanBean.DataBean.VideoBean> videoBeans = list.get(position).getVideo();
        List<UnFinishPlanBean.DataBean.VideoBean> videoBeanList = new ArrayList<>();
        videoBeanList.clear();
        //videoBeanList.addAll(list.get(position).getVideo());
        /*if (videoBeanList.size() > 3) {
            for (int i = 3; i < videoBeanList.size(); i++) {
                videoBeanList.remove(i);
            }
        }*/
        if (videoBeans.size() > 3) {
            for (int i = 0; i < videoBeans.size(); i++) {
                if (i < 3) {
                    videoBeanList.add(videoBeans.get(i));
                }
            }
        }
        UnFinishLearnVideoAdapter videoAdapter = new UnFinishLearnVideoAdapter(videoBeanList, context);
        holder.mNestedlistview.setAdapter(videoAdapter);
        holder.mLienarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                videoBeanList.clear();
                //videoBeanList.addAll(list.get(position).getVideo());
                if (expandGroup[0]) {//关闭列表
                    /*for (int i = 3; i < videoBeanList.size(); i++) {
                        videoBeanList.remove(i);
                    }*/
                    for (int i = 0; i < videoBeans.size(); i++) {
                        if (i < 3) {
                            videoBeanList.add(videoBeans.get(i));
                        }
                    }
                    videoAdapter.notifyDataSetChanged();

                    holder.mBtnSpread.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicator_bottom));
                } else {//展开列表
                    videoBeanList.addAll(list.get(position).getVideo());
                    videoAdapter.notifyDataSetChanged();
                    holder.mBtnSpread.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicator_top));
                }
                expandGroup[0] = !expandGroup[0];
            }
        });

        if (expandGroup[0]) {
            holder.mBtnSpread.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicator_top));
        } else {
            holder.mBtnSpread.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.icon_lc_indicator_bottom));
        }
        if (videoBeans.size() > 3) {
            holder.mLine.setVisibility(View.VISIBLE);
            holder.mLienarBtn.setVisibility(View.VISIBLE);
        } else {
            holder.mLine.setVisibility(View.GONE);
            holder.mLienarBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_lc_unfinishvideo, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mTitleItem;
        private RoundTextView mBtnToStudy;
        private NestedListView mNestedlistview;
        private View mLine;
        private ImageView mBtnSpread;
        private LinearLayout mLienarBtn;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mBtnToStudy = (RoundTextView) itemView.findViewById(R.id.btnToStudy);
            mNestedlistview = (NestedListView) itemView.findViewById(R.id.nestedlistview);
            mLine = (View) itemView.findViewById(R.id.line);
            mBtnSpread = (ImageView) itemView.findViewById(R.id.btnSpread);
            mLienarBtn = (LinearLayout) itemView.findViewById(R.id.btn_lienar);
        }
    }

}
