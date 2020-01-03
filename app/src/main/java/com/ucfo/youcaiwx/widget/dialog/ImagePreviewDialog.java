package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;

/**
 * Author: AND
 * Time: 2020-1-3.  下午 12:56
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: ImagePreviewDialog
 * Description:学分报告图片预览下载框
 */
public class ImagePreviewDialog {

    private Context context;
    private Dialog dialog;
    private ImageView mImageview;
    private TextView mNextBtn;

    public ImagePreviewDialog(Context context) {
        this.context = context;
    }

    public ImagePreviewDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_image, null);

        initView(contentView);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        return this;
    }

    private void initView(@NonNull final View itemView) {
        mImageview = (ImageView) itemView.findViewById(R.id.imageview);
        mNextBtn = (TextView) itemView.findViewById(R.id.btn_next);
    }

    public ImagePreviewDialog setImageUrl(String url) {
        RequestOptions requestOptions = new RequestOptions()
                .fitCenter()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, url, mImageview, requestOptions);
        return this;
    }

    public ImagePreviewDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ImagePreviewDialog setOnSaveClickListener(View.OnClickListener listener) {
        mNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClick(v);
                dialog.dismiss();
            }
        });
        return this;
    }

    public void show() {
        dialog.show();
    }

    public void dismiss() {
        dialog.dismiss();
    }
}
