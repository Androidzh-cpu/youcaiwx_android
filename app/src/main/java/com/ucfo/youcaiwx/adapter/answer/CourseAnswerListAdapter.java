package com.ucfo.youcaiwx.adapter.answer;

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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.hitomi.tilibrary.style.index.NumberIndexIndicator;
import com.hitomi.tilibrary.style.progress.ProgressBarIndicator;
import com.hitomi.tilibrary.transfer.TransferConfig;
import com.hitomi.tilibrary.transfer.Transferee;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.answer.AnswerListDataBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.baseadapter.SpacesItemDecoration;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.flowlayout.FlowLayout;
import com.ucfo.youcaiwx.widget.flowlayout.TagAdapter;
import com.ucfo.youcaiwx.widget.flowlayout.TagFlowLayout;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:29117
 * Time: 2019-4-16.  下午 6:15
 * Email:2911743255@qq.com
 * ClassName: CourseAnswerListAdapter
 * Description:TODO 课程答疑适配器
 */
public class CourseAnswerListAdapter extends BaseAdapter<AnswerListDataBean.DataBean, CourseAnswerListAdapter.ViewHolder> {
    private Transferee transferee;
    private List<AnswerListDataBean.DataBean> list;
    private Context context;
    private LinearLayoutManager layoutManager;
    private OnItemViewClickListener onItemClickListener;
    private int type;//0课程播放答疑,1个人中心答疑,2个人中心题库答疑

    public CourseAnswerListAdapter(List<AnswerListDataBean.DataBean> list, Context context, int type) {
        this.list = list;
        this.context = context;
        this.type = type;
        transferee = Transferee.getDefault(context);
    }

    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {

        AnswerListDataBean.DataBean bean = list.get(position);
        String create_time = bean.getCreate_time();//问答创建时间
        String create_times = bean.getCreates_time();//问答创建时间
        String section_name = bean.getSection_name();//问答所属章节
        String username = bean.getUsername();//用户昵称
        String head = bean.getHead();//用户头像
        String video_time = bean.getVideo_time();//视频时间节点
        String quiz = bean.getQuiz();//提问问题
        int reply_status = bean.getReply_status();//问题是否回复

        if (bean.getQuiz_image() != null && bean.getQuiz_image().size() > 0) {//图片集合
            holder.mAnswerImagelistItem.setVisibility(View.VISIBLE);//显示图片集合

            PictureAdapter pictureAdapter = new PictureAdapter(bean.getQuiz_image(), context);//创建图片列表适配器
            layoutManager = new LinearLayoutManager(context);
            layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
            int topBottom = DensityUtil.dip2px(context, 4);
            holder.mAnswerImagelistItem.addItemDecoration(new SpacesItemDecoration(topBottom, 0, Color.TRANSPARENT));
            holder.mAnswerImagelistItem.setLayoutManager(layoutManager);
            holder.mAnswerImagelistItem.setAdapter(pictureAdapter);
            pictureAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int i) {
                    TransferConfig config = TransferConfig.build()//图片预览先关配置
                            .setThumbnailImageList(list.get(position).getQuiz_image())//预览图
                            .setSourceImageList(list.get(position).getQuiz_image())//图片地址
                            .setMissPlaceHolder(R.mipmap.banner_default)
                            .setErrorPlaceHolder(R.mipmap.banner_default)
                            .setProgressIndicator(new ProgressBarIndicator())//加载进度
                            .setIndexIndicator(new NumberIndexIndicator())//指示器
                            .setJustLoadHitImage(true)//是否只加载当前显示在屏幕中的的原图
                            .setOffscreenPageLimit(list.get(position).getQuiz_image().size())
                            .bindRecyclerView(holder.mAnswerImagelistItem, R.id.iv_image);// RecyclerView 来排列显示图片,图片的ID

                    config.setNowThumbnailIndex(i);
                    transferee.apply(config).show();
                }
            });
        } else {
            holder.mAnswerImagelistItem.setVisibility(View.GONE);
        }
        //学员头像
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_headdefault)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, head, holder.mAnswerUsericonItem, requestOptions);
        if (!TextUtils.isEmpty(username)) {//用户昵称
            holder.mAnswerUsernameItem.setText(username);
        }
        switch (type) {//问答创建时间
            case 0:
                //holder.mAnswerCreatetimeItem.setText(create_time);
                holder.mAnswerCreatetimeItem.setText(create_times);
                break;
            case 1:
                holder.mAnswerCreatetimeItem.setText(create_times);
                break;
            case 2:
                holder.mAnswerCreatetimeItem.setText(bean.getCreate_times());
                break;
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
        List<String> know_name = bean.getKnow_name();
        switch (type) {//问答创建时间
            case 0:
            case 1:
                know_name.add(section_name);
                break;
            case 2://题库答疑
                break;
        }
        if (know_name.size() > 0) {//TODO 标签处理
            TagAdapter<String> tagAdapter = new TagAdapter<String>(know_name) {//流式布局适配器
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

        holder.mAnswerCheckdetailItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用接口里的点击方法,传入布局和索引
                onItemClickListener.OnItemClick(holder.itemView, position);
            }
        });
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_answer_list, viewGroup, false);
        return new ViewHolder(inflate);
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
        private TagFlowLayout mFlowlayout;

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
            mFlowlayout = (TagFlowLayout) itemView.findViewById(R.id.flowlayout);
        }
    }
}
