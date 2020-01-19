package com.ucfo.youcaiwx.adapter.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.home.MessageCenterNoticeBean;
import com.ucfo.youcaiwx.utils.LogUtils;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Author: AND
 * Time: 2019-8-12.  上午 9:42
 * FileName: MessageNotificationAdapter
 * Description:系统消息适配
 */
public class MessageNotificationAdapter extends RecyclerView.Adapter {
    private List<MessageCenterNoticeBean.DataBean> list;
    private Context context;
    //课程提醒  暂时预留
    public static final int TYPE_COURSE = 1;
    //订单支付
    public static final int TYPE_ORDERFORM = 2;
    //网校公告和链接消息
    public static final int TYPE_WEB = 3;
    //题库答疑
    public static final int TYPE_ANSWER = 4;

    //item_messagenotification_course   课程提醒
    //item_messagenotification_orderform   订单支付
    //item_messagenotification_web   网页链接
    //item_messagenotification_answer   答疑
    public MessageNotificationAdapter(List<MessageCenterNoticeBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        int type = list.get(position).getType();
        if (type == 1 || type == 2) {
            //答疑
            return TYPE_ANSWER;
        } else if (type == 3) {
            //订单
            return TYPE_ORDERFORM;
        } else if (type == 4 || type == 5) {
            //h5消息
            return TYPE_WEB;
        }
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType) {
            case TYPE_COURSE:
                //课程提醒
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagenotification_course, parent, false);
                holder = new CourseViewHolder(view);
                break;
            case TYPE_ANSWER:
                //答疑类型
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagenotification_answer, parent, false);
                holder = new AnswerViewHolder(view);
                break;
            case TYPE_ORDERFORM:
                //订单类型
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagenotification_orderform, parent, false);
                holder = new OrderFormViewHolder(view);
                break;
            case TYPE_WEB:
                //H5类型
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_messagenotification_web, parent, false);
                holder = new WebViewHolder(view);
                break;
            default:
                break;
        }
        return holder;
    }

    private OnItemClickListener onItemClickListener;

    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public String formatDate(String serverTime) {
        String format = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CHINESE);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        Date date = null;
        try {
            date = sdf.parse(serverTime);
        } catch (Exception e) {
            LogUtils.e(e.toString());
        }
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        return formatter.format(date);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MessageCenterNoticeBean.DataBean bean = list.get(position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onItemClick(v, position);
            }
        });


        if (holder instanceof CourseViewHolder) {

        }
        if (holder instanceof AnswerViewHolder) {
            String content = bean.getContent();
            String createTime = bean.getCreate_time();
            String from = bean.getFrom();
            if (!TextUtils.isEmpty(createTime)) {
                ((AnswerViewHolder) holder).mCreatetimeItem.setText(createTime);
                String formatDate = formatDate(createTime);
                ((AnswerViewHolder) holder).mTimeItem.setText(formatDate);
            }
            if (!TextUtils.isEmpty(from)) {
                ((AnswerViewHolder) holder).mFromItem.setText(from);
            }
            if (!TextUtils.isEmpty(content)) {
                ((AnswerViewHolder) holder).mMessageItem.setText(content);
            }
        }
        if (holder instanceof OrderFormViewHolder) {
            String content = bean.getContent();
            String createTime = bean.getCreate_time();
            String orderNum = bean.getOrder_num();
            String payPrice = bean.getPay_price();
            String addTime = bean.getAdd_time();
            String packageName = bean.getPackage_name();
            if (!TextUtils.isEmpty(createTime)) {
                ((OrderFormViewHolder) holder).mCreatetimeItem.setText(createTime);
                String formatDate = formatDate(createTime);
                ((OrderFormViewHolder) holder).mTimeItem.setText(formatDate);
            }
            if (!TextUtils.isEmpty(content)) {
                ((OrderFormViewHolder) holder).mTitleItem.setText(content);
            }
            if (!TextUtils.isEmpty(orderNum)) {
                ((OrderFormViewHolder) holder).mOrderNumItem.setText(orderNum);
            }
            if (!TextUtils.isEmpty(payPrice)) {
                ((OrderFormViewHolder) holder).mOrderPriceItem.setText(payPrice);
            }
            if (!TextUtils.isEmpty(addTime)) {
                ((OrderFormViewHolder) holder).mOrderTimeItem.setText(addTime);
            }
            if (!TextUtils.isEmpty(packageName)) {
                ((OrderFormViewHolder) holder).mOrderNameItem.setText(packageName);
            }
        }
        if (holder instanceof WebViewHolder) {
            String createTime = bean.getCreate_time();
            String title = bean.getTitle();
            String content = bean.getContent();
            if (!TextUtils.isEmpty(createTime)) {
                ((WebViewHolder) holder).mCreatetimeItem.setText(createTime);
                String formatDate = formatDate(createTime);
                ((WebViewHolder) holder).mTimeItem.setText(formatDate);

            }
            if (!TextUtils.isEmpty(title)) {
                ((WebViewHolder) holder).mTitleItem.setText(title);
            }
            if (!TextUtils.isEmpty(content)) {
                ((WebViewHolder) holder).mMessageItem.setText(content);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    private class CourseViewHolder extends RecyclerView.ViewHolder {

        public CourseViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class AnswerViewHolder extends RecyclerView.ViewHolder {

        private RoundTextView mCreatetimeItem;
        private TextView mTitleItem;
        private TextView mTimeItem;
        private TextView mFromItem;
        private TextView mMessageItem;

        public AnswerViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(@NonNull final View itemView) {
            mCreatetimeItem = (RoundTextView) itemView.findViewById(R.id.item_createtime);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
            mFromItem = (TextView) itemView.findViewById(R.id.item_from);
            mMessageItem = (TextView) itemView.findViewById(R.id.item_message);
        }
    }

    private class WebViewHolder extends RecyclerView.ViewHolder {
        private RoundTextView mCreatetimeItem;
        private TextView mTitleItem;
        private TextView mTimeItem;
        private TextView mMessageItem;

        public WebViewHolder(View itemView) {
            super(itemView);
            mCreatetimeItem = (RoundTextView) itemView.findViewById(R.id.item_createtime);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
            mMessageItem = (TextView) itemView.findViewById(R.id.item_message);
        }
    }

    private class OrderFormViewHolder extends RecyclerView.ViewHolder {
        private RoundTextView mCreatetimeItem;
        private TextView mTitleItem;
        private TextView mTimeItem;
        private TextView mOrderNumItem;
        private TextView mOrderPriceItem;
        private TextView mOrderTimeItem;
        private TextView mOrderNameItem;

        public OrderFormViewHolder(View itemView) {
            super(itemView);
            initView(itemView);
        }

        private void initView(@NonNull final View itemView) {
            mCreatetimeItem = (RoundTextView) itemView.findViewById(R.id.item_createtime);
            mTitleItem = (TextView) itemView.findViewById(R.id.item_title);
            mTimeItem = (TextView) itemView.findViewById(R.id.item_time);
            mOrderNumItem = (TextView) itemView.findViewById(R.id.item_orderNum);
            mOrderPriceItem = (TextView) itemView.findViewById(R.id.item_orderPrice);
            mOrderTimeItem = (TextView) itemView.findViewById(R.id.item_orderTime);
            mOrderNameItem = (TextView) itemView.findViewById(R.id.item_orderName);
        }
    }
}
