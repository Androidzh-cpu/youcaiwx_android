package com.ucfo.youcaiwx.adapter.answer;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.answer.AnsweringQuestionDetailsBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.widget.flowlayout.FlowLayout;
import com.ucfo.youcaiwx.widget.flowlayout.TagAdapter;
import com.ucfo.youcaiwx.widget.flowlayout.TagFlowLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-11-13.  上午 9:57
 * Package: com.ucfo.youcaiwx.adapter.answer
 * FileName: AnsweringQuestionDetailAdapter
 * Description:TODO 题库答疑详情(追问)
 */
public class AnsweringQuestionDetailAdapter extends BaseAdapter<AnsweringQuestionDetailsBean.DataBean.ReplyBean, AnsweringQuestionDetailAdapter.ViewHolder> {
    private Transferee transferee;
    private List<AnsweringQuestionDetailsBean.DataBean.ReplyBean> list;
    private Context context;
    private OnItemViewClickListener onItemClickListener;

    public AnsweringQuestionDetailAdapter(List<AnsweringQuestionDetailsBean.DataBean.ReplyBean> list, Context context, Transferee transferee1) {
        this.list = list;
        this.context = context;
        this.transferee = transferee1;
    }

    public void notifyChange(List<AnsweringQuestionDetailsBean.DataBean.ReplyBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        AnsweringQuestionDetailsBean.DataBean.ReplyBean bean = list.get(position);
        String userSelf = bean.getUser_self();
        //老师标识 1:不是老师 2:老师
        String isTeacher = bean.getIs_teacher();
        //回复状态 1:已回复  2:否
        String replyStatus = bean.getReply_status();
        //创建时间
        String createsTime = bean.getCreates_time();
        //昵称
        String username = bean.getUsername();
        //问题内容
        String quiz = bean.getQuiz();
        //用户头像
        String headImage = bean.getHead_image();
        //标签
        List<String> know = bean.getKnow();
        //图片
        List<String> beanQuizImage = bean.getQuiz_image();
        //是否投诉1投诉2未投诉
        String isComplain = bean.getIs_complain();
        //TODO 是否可以投诉
        if (TextUtils.equals(String.valueOf("2"), isTeacher)) {
            //老师
            holder.mTeacherTab.setVisibility(View.VISIBLE);
            //其他人的答疑不显示回复按钮
            if (TextUtils.equals(String.valueOf("1"), userSelf)) {
                if (TextUtils.equals(String.valueOf("1"), isComplain)) {
                    holder.mComplainTxt.setVisibility(View.GONE);
                } else {
                    holder.mComplainTxt.setVisibility(View.VISIBLE);
                }
            } else {
                holder.mComplainTxt.setVisibility(View.GONE);
            }
        } else {
            //呦呵,用户啊
            holder.mTeacherTab.setVisibility(View.GONE);
            holder.mComplainTxt.setVisibility(View.GONE);
            holder.mReplyStatusTxt.setVisibility(View.VISIBLE);
            //TODO 回复状态
            if (TextUtils.equals(String.valueOf("1"), replyStatus)) {
                holder.mReplyStatusTxt.setText(context.getResources().getString(R.string.answer_teacher_reply));
            } else {
                holder.mReplyStatusTxt.setText("");
            }
        }
        //TODO 图片内容
        if (beanQuizImage.isEmpty()) {
            holder.mImageRecyclerview.setVisibility(View.GONE);
        } else {
            holder.mImageRecyclerview.setVisibility(View.VISIBLE);
            LinearLayoutManager layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            holder.mImageRecyclerview.setLayoutManager(layoutManager);

            PictureAdapter pictureAdapter = new PictureAdapter(beanQuizImage, context);//创建图片列表适配器
            TransferConfig config = TransferConfig.build()//图片预览先关配置
                    .setThumbnailImageList(beanQuizImage)//预览图
                    .setSourceImageList(beanQuizImage)//图片地址
                    .setProgressIndicator(new ProgressBarIndicator())//加载进度
                    .setIndexIndicator(new NumberIndexIndicator())//指示器
                    .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                    .bindRecyclerView(holder.mImageRecyclerview, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
            holder.mImageRecyclerview.setAdapter(pictureAdapter);
            pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    config.setNowThumbnailIndex(position);
                    transferee.apply(config).show();
                }
            });
        }
        //TODO 知识点标签
        if (position == 0) {
            holder.mFlowlayout.setVisibility(View.VISIBLE);
            if (!know.isEmpty()) {
                //holder.mSectionNameTab.setText(sectionName);
                TagAdapter<String> tagAdapter = new TagAdapter<String>(know) {
                    @Override
                    public View getView(FlowLayout parent, int j, String string) {
                        TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tagflowlayout, holder.mFlowlayout, false);
                        textView.setText(string);
                        int i = j % 3;
                        switch (i) {
                            case 0://余数为0
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_blue));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_0267FF));
                                break;
                            case 1://余数为1
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_red));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_E84342));
                                break;
                            case 2://余数为2
                                textView.setBackground(ContextCompat.getDrawable(context, R.drawable.shape_rectangle_corners_orange));
                                textView.setTextColor(ContextCompat.getColor(context, R.color.color_F99111));
                                break;
                            default:
                                break;
                        }
                        return textView;
                    }
                };
                holder.mFlowlayout.setAdapter(tagAdapter);//流式布局添加适配器
            }
        } else {
            holder.mFlowlayout.setVisibility(View.GONE);
        }
        //TODO 提问问题
        if (!TextUtils.isEmpty(quiz)) {
            holder.mQuestionContentTxt.setText(quiz);
        }
        //TODO 创建时间
        if (!TextUtils.isEmpty(createsTime)) {
            holder.mCreateTimeTxt.setText(createsTime);
        }
        //TODO 昵称
        if (!TextUtils.isEmpty(username)) {
            holder.mNickNameTxt.setText(username);
        }
        //TODO 头像
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_account_btn)
                .error(R.mipmap.icon_account_btn)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, headImage, holder.mUserIconImg, requestOptions);
        if (!TextUtils.isEmpty(headImage)) {
            TransferConfig headConfig = TransferConfig.build()
                    .setMissPlaceHolder(R.mipmap.icon_account_btn)
                    .setErrorPlaceHolder(R.mipmap.icon_account_btn)
                    .setProgressIndicator(new ProgressPieIndicator())
                    .setIndexIndicator(new NumberIndexIndicator())
                    .setJustLoadHitImage(true)
                    .bindImageView(holder.mUserIconImg, headImage);
            holder.mUserIconImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    transferee.apply(headConfig).show();
                }
            });
        }
        holder.mComplainTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.OnItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_question_trace, viewGroup, false);
        return new ViewHolder(inflate);
    }

    public interface OnItemViewClickListener {
        void OnItemClick(View view, int position);
    }

    //定义一个公共方法,当外部类调用时可以通过此方法点击item
    public void setComplainClick(OnItemViewClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }


    protected class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mUserIconImg;
        private TextView mNickNameTxt;
        private TextView mCreateTimeTxt;
        private RoundTextView mTeacherTab;
        private TextView mReplyStatusTxt;
        private TextView mComplainTxt;
        private LinearLayout mUserLinearAnswer;
        private TextView mQuestionContentTxt;
        private RecyclerView mImageRecyclerview;
        private TagFlowLayout mFlowlayout;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {

            mUserIconImg = (CircleImageView) itemView.findViewById(R.id.img_userIcon);
            mNickNameTxt = (TextView) itemView.findViewById(R.id.txt_nickName);
            mCreateTimeTxt = (TextView) itemView.findViewById(R.id.txt_createTime);
            mTeacherTab = (RoundTextView) itemView.findViewById(R.id.tab_teacher);
            mReplyStatusTxt = (TextView) itemView.findViewById(R.id.txt_replyStatus);
            mComplainTxt = (TextView) itemView.findViewById(R.id.txt_complain);
            mUserLinearAnswer = (LinearLayout) itemView.findViewById(R.id.answer_user_linear);
            mQuestionContentTxt = (TextView) itemView.findViewById(R.id.txt_questionContent);
            mImageRecyclerview = (RecyclerView) itemView.findViewById(R.id.recyclerview_image);
            mFlowlayout = (TagFlowLayout) itemView.findViewById(R.id.flowlayout);
        }
    }
}
