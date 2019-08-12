package com.ucfo.youcai.adapter.home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.home.MessageCenterNoticeBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-8-9.  下午 2:58
 * Package: com.ucfo.youcai.adapter.home
 * FileName: MessageNoticeAdapter
 * Description:TODO 网校公告适配器
 */
public class MessageNoticeAdapter extends BaseAdapter<MessageCenterNoticeBean.DataBean, MessageNoticeAdapter.ViewHolder> {
    private List<MessageCenterNoticeBean.DataBean> list;
    private Context context;

    public MessageNoticeAdapter(List<MessageCenterNoticeBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        MessageCenterNoticeBean.DataBean bean = list.get(position);
        String create_time = bean.getCreate_time();
        String title = bean.getTitle();
        int status = bean.getStatus();
        String content = bean.getContent();
        if (!TextUtils.isEmpty(title)) {
            holder.mTitleItem.setText(title);
        } else {
            holder.mTitleItem.setText(context.getResources().getString(R.string.holder_nodata));
        }
        if (!TextUtils.isEmpty(content)) {
            holder.mMessageItem.setText(content);
        } else {
            holder.mMessageItem.setText(context.getResources().getString(R.string.holder_nodata));
        }
        if (!TextUtils.isEmpty(create_time)) {
            holder.mTimeItem.setText(create_time);
        }
        if (status == 1) {
            holder.mMessageItem.setTextColor(ContextCompat.getColor(context, R.color.color_666666));
            holder.mTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_333333));
        } else {
            holder.mMessageItem.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
            holder.mTitleItem.setTextColor(ContextCompat.getColor(context, R.color.color_999999));
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.item_message_notice, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTimeItem;
        private TextView mTitleItem;
        private TextView mMessageItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mMessageItem = (TextView) itemView.findViewById(R.id.item_message);
        }
    }

}
