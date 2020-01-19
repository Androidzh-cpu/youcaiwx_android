package com.ucfo.youcaiwx.adapter.course;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.course.CourseIntroductionBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author:29117
 * Time: 2019-4-4.  下午 4:58
 * Email:2911743255@qq.com
 * ClassName: CourseTeacherAdapter
 */
public class CourseTeacherAdapter extends BaseAdapter<CourseIntroductionBean.DataBean.TeacehrListBean, CourseTeacherAdapter.ViewHolder> {
    private List<CourseIntroductionBean.DataBean.TeacehrListBean> list;
    private Context context;
    private boolean isShowArrows;


    public CourseTeacherAdapter(List<CourseIntroductionBean.DataBean.TeacehrListBean> list, Context context, boolean isShowArrows) {
        this.list = list;
        this.context = context;
        this.isShowArrows = isShowArrows;
    }

    public CourseTeacherAdapter(List<CourseIntroductionBean.DataBean.TeacehrListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getItemCount() {
        if (list != null && list.size() > 0) {
            return list.size();
        } else {
            return 0;
        }
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        CourseIntroductionBean.DataBean.TeacehrListBean bean = list.get(position);
        String longevity = bean.getIntroduce();
        String teacher_name = bean.getTeacher_name();
        String pictrue = bean.getPictrue();//头像
        if (!TextUtils.isEmpty(longevity)) {
            holder.mTeacherdetailTv.setText(longevity);
        }
        if (!TextUtils.isEmpty(teacher_name)) {
            holder.mTeachernameTv.setText(teacher_name);
        }
        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new RoundedCorners(DensityUtil.dp2px(5)))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, pictrue, holder.mTeacherImage, requestOptions);
        if (isShowArrows) {
            holder.mArrowsImage.setVisibility(View.GONE);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_course_teacher, viewGroup, false);
        return new ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {


        private CircleImageView mTeacherImage;
        private TextView mTeachernameTv;
        private TextView mTeacherdetailTv;
        private ImageView mArrowsImage;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mTeacherImage = (CircleImageView) itemView.findViewById(R.id.image_teacher);
            mTeachernameTv = (TextView) itemView.findViewById(R.id.tv_teachername);
            mTeacherdetailTv = (TextView) itemView.findViewById(R.id.tv_teacherdetail);
            mArrowsImage = (ImageView) itemView.findViewById(R.id.image_arrows);
        }
    }

}
