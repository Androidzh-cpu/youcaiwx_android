package com.ucfo.youcai.adapter.answer;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.style.progress.ProgressPieIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcai.R;
import com.ucfo.youcai.entity.answer.QuestionAnswerListBean;
import com.ucfo.youcai.utils.baseadapter.BaseAdapter;
import com.ucfo.youcai.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcai.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcai.utils.systemutils.DensityUtil;
import com.ucfo.youcai.widget.flowlayout.FlowLayout;
import com.ucfo.youcai.widget.flowlayout.TagAdapter;
import com.ucfo.youcai.widget.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-5-30.  下午 3:52
 * Package: com.ucfo.youcai.adapter.answer
 * FileName: QuestionAnswerListAdapter
 * Description:TODO 题库答疑列表适配器
 */
public class QuestionAnswerListAdapter extends BaseAdapter<QuestionAnswerListBean.DataBean, QuestionAnswerListAdapter.ViewHolder> {
    private final Transferee transferee;
    private ArrayList<QuestionAnswerListBean.DataBean> list;
    private Context context;
    private LinearLayoutManager layoutManager;
    private TransferConfig config;
    private OnItemViewClickListener onItemClickListener;

    public QuestionAnswerListAdapter(ArrayList<QuestionAnswerListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;

        transferee = Transferee.getDefault(context);//预览图配置
    }

    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        QuestionAnswerListBean.DataBean bean = list.get(position);
        String create_time = bean.getCreate_times();//问答创建时间
        String username = bean.getUsername();//用户昵称
        String head = bean.getHead();//用户头像
        String quiz = bean.getQuiz();//提问问题
        int reply_status = bean.getReply_status();//问题是否回复
        List<String> quiz_image = bean.getQuiz_image();
        List<String> know_name = bean.getKnow_name();//标签集合

        if (quiz_image.size() > 0) {//图片集合
            holder.mAnswerImagelistItem.setVisibility(View.VISIBLE);//显示图片集合

            PictureAdapter pictureAdapter = new PictureAdapter(quiz_image, context);//创建图片列表适配器
            layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            int leftright = DensityUtil.dip2px(context, 4);
            holder.mAnswerImagelistItem.addItemDecoration(new SpacesItemDecoration(leftright, 0, Color.TRANSPARENT));
            holder.mAnswerImagelistItem.setLayoutManager(layoutManager);
            holder.mAnswerImagelistItem.setAdapter(pictureAdapter);
            TransferConfig config = TransferConfig.build()//图片预览先关配置
                    .setThumbnailImageList(list.get(position).getQuiz_image())//预览图
                    .setSourceImageList(list.get(position).getQuiz_image())//图片地址
                    .setMissPlaceHolder(R.mipmap.banner_default)
                    .setErrorPlaceHolder(R.mipmap.banner_default)
                    .setProgressIndicator(new ProgressBarIndicator())//加载进度
                    .setIndexIndicator(new NumberIndexIndicator())//指示器
                    .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                    .bindRecyclerView(holder.mAnswerImagelistItem, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID
            pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    config.setNowThumbnailIndex(i);
                    transferee.apply(config).show();
                }
            });
        } else {
            holder.mAnswerImagelistItem.setVisibility(View.GONE);
        }
        //学员头像
        Glide.with(context).load(head).error(R.mipmap.banner_default).into(holder.mAnswerUsericonItem);
        TransferConfig config = TransferConfig.build()
                .setMissPlaceHolder(R.mipmap.banner_default)
                .setErrorPlaceHolder(R.mipmap.banner_default)
                .setProgressIndicator(new ProgressPieIndicator())
                .setIndexIndicator(new NumberIndexIndicator())
                .setJustLoadHitImage(true)
                .bindImageView(holder.mAnswerUsericonItem, head);
        holder.mAnswerUsericonItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transferee.apply(config).show();
            }
        });

        if (!TextUtils.isEmpty(username)) {//用户昵称
            holder.mAnswerUsernameItem.setText(username);
        }
        if (!TextUtils.isEmpty(create_time)) {//问答创建时间
            holder.mAnswerCreatetimeItem.setText(create_time);
        }
        if (!TextUtils.isEmpty(quiz)) {//提问问题
            holder.mAnswerContentItem.setText(quiz);
        }
        switch (reply_status) {
            case 1://1回复
                holder.mAnswerReplystatusItem.setVisibility(View.VISIBLE);
                break;
            case 2://2未回复
                holder.mAnswerReplystatusItem.setVisibility(View.GONE);
                break;
        }
        //调用接口,模拟点击
        holder.mAnswerCheckdetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用接口里的点击方法,传入布局和索引
                onItemClickListener.OnItemClick(holder.itemView, position);
            }
        });
        if (know_name.size() > 0) {
            //流式布局适配器
            TagAdapter<String> tagAdapter = new TagAdapter<String>(know_name) {
                @Override
                public View getView(FlowLayout parent, int j, String string) {
                    TextView textView = (TextView) LayoutInflater.from(context).inflate(R.layout.item_tagflowlayout, holder.flowLayout, false);
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
            holder.flowLayout.setAdapter(tagAdapter);//流式布局添加适配器
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_answerquestion_list, viewGroup, false);
        QuestionAnswerListAdapter.ViewHolder viewHolder = new QuestionAnswerListAdapter.ViewHolder(inflate);
        return viewHolder;
    }

    public interface OnItemViewClickListener {
        void OnItemClick(View view, int position);
    }

    //定义一个公共方法,当外部类调用时可以通过此方法点击item
    public void setItemClick(OnItemViewClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private CircleImageView mAnswerUsericonItem;
        private TextView mAnswerUsernameItem;
        private TextView mAnswerCreatetimeItem;
        private TextView mAnswerContentItem;
        private RecyclerView mAnswerImagelistItem;
        private RoundTextView mAnswerCheckdetailItem;
        private TextView mAnswerReplystatusItem;
        private TagFlowLayout flowLayout;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mAnswerUsericonItem = (CircleImageView) itemView.findViewById(R.id.item_answer_usericon);
            mAnswerUsernameItem = (TextView) itemView.findViewById(R.id.item_answer_username);
            mAnswerCreatetimeItem = (TextView) itemView.findViewById(R.id.item_answer_createtime);
            mAnswerContentItem = (TextView) itemView.findViewById(R.id.item_answer_content);
            mAnswerImagelistItem = (RecyclerView) itemView.findViewById(R.id.item_answer_imagelist);
            mAnswerCheckdetailItem = (RoundTextView) itemView.findViewById(R.id.item_answer_checkdetail);
            mAnswerReplystatusItem = (TextView) itemView.findViewById(R.id.item_answer_replystatus);
            flowLayout = (TagFlowLayout) itemView.findViewById(R.id.flowlayout);
        }
    }

}

