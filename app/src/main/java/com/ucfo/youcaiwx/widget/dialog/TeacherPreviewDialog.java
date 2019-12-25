package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.course.CourseIntroductionBean;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Author: AND
 * Time: 2019-12-23.  上午 11:21
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: TeacherPreviewDialog
 * Description:TODO 讲师预览
 */
public class TeacherPreviewDialog {
    private Context context;
    private Dialog dialog;
    private DisplayMetrics display;
    private CourseIntroductionBean.DataBean.TeacehrListBean bean;
    private TextView mtitleTeacher;
    private TextView mNameTeacher;
    private TextView mDetailTeacher;
    private CircleImageView mIconTeacher;

    public TeacherPreviewDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public TeacherPreviewDialog builder() {
        // 获取Dialog布局
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_teacher_detail, null);

        // 定义Dialog布局和参数
        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);

        mIconTeacher = (CircleImageView) contentView.findViewById(R.id.teacher_icon);
        mtitleTeacher = (TextView) contentView.findViewById(R.id.teacher_title);
        mNameTeacher = (TextView) contentView.findViewById(R.id.teacher_name);
        mDetailTeacher = (TextView) contentView.findViewById(R.id.teacher_detail);
        contentView.findViewById(R.id.teacher_back).bringToFront();
        contentView.findViewById(R.id.dialog_close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mIconTeacher.bringToFront();
        return this;
    }

    public TeacherPreviewDialog setEntity(CourseIntroductionBean.DataBean.TeacehrListBean bean) {
        this.bean = bean;
        String teacher_title = bean.getTeacher_title();
        String pictrue = bean.getPictrue();
        String longevity = bean.getLongevity();
        String teacher_name = bean.getTeacher_name();

        RequestOptions requestOptions = new RequestOptions()
                .centerCrop()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror);
        GlideUtils.load(context, pictrue, mIconTeacher, requestOptions);
        mtitleTeacher.setText(teacher_title);
        mDetailTeacher.setText(longevity);
        mNameTeacher.setText(teacher_name);
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
