package com.ucfo.youcaiwx.widget.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;

/**
 * Author: AND
 * Time: 2019-8-23.  上午 10:09
 * Package: com.ucfo.youcaiwx.widget.dialog
 * FileName: ActiveEventDialog
 * Description:TODO 活动页弹窗
 */
public class ActiveEventDialog {

    private Context context;
    private Dialog dialog;
    private ImageView imageview;
    private DisplayMetrics display;

    public ActiveEventDialog(Context context) {
        this.context = context;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        if (windowManager != null) {
            display = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(display);
        }
    }

    public ActiveEventDialog builder() {
        View contentView = LayoutInflater.from(context).inflate(R.layout.dialog_active_event, null);
        imageview = (ImageView) contentView.findViewById(R.id.image_view);

        dialog = new Dialog(context, R.style.AlertDialogStyle);
        dialog.setContentView(contentView);
        ViewGroup.LayoutParams layoutParams = contentView.getLayoutParams();
        layoutParams.width = context.getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(layoutParams);
        return this;
    }

    public ActiveEventDialog setCancelable(boolean cancel) {
        dialog.setCancelable(cancel);
        return this;
    }

    public ActiveEventDialog setCanceledOnTouchOutside(boolean cancel) {
        dialog.setCanceledOnTouchOutside(cancel);
        return this;
    }

    public ActiveEventDialog setImageUrl(String imageUrl) {
        RequestOptions requestOptions = new RequestOptions()
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, imageUrl, imageview, requestOptions);
        return this;
    }

    public ActiveEventDialog setNegativeButton(final View.OnClickListener listener) {
        imageview.setOnClickListener(new View.OnClickListener() {
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
