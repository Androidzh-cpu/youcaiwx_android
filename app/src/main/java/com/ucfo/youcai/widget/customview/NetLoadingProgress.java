package com.ucfo.youcai.widget.customview;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ucfo.youcai.R;

/**
 * Author:AND
 * Time: 2019-3-7.  上午 11:43
 * Email:2911743255@qq.com
 * ClassName: NetLoadingProgress
 * Package: com.ucfo.youcai.utils.netloding
 * Description:NetLoadingProgress.Builder builder = new NetLoadingProgress.Builder(this);
 * NetLoadingProgress netLoadingProgress = builder.create();
 * netLoadingProgress.show();
 * Detail:
 */
public class NetLoadingProgress extends Dialog {
    public NetLoadingProgress(Context context) {
        super(context);
    }

    public NetLoadingProgress(Context context, int themeResId) {
        super(context, themeResId);
    }

    public static class Builder {
        private Context context;
        private String message;
        private boolean isShowMessage = true;
        private boolean isCancelable = false;//返回键点击消失
        private boolean isCancelOutside = false;//点击外部区域消失

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 设置提示信息
         */
        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        /**
         * 设置是否显示提示信息
         */

        public Builder setShowMessage(boolean isShowMessage) {
            this.isShowMessage = isShowMessage;
            return this;
        }

        /**
         * 设置是否可以按返回键取消
         */
        public Builder setCancelable(boolean isCancelable) {
            this.isCancelable = isCancelable;
            return this;
        }

        /**
         * 设置是否可以取消
         */
        public Builder setCancelOutside(boolean isCancelOutside) {
            this.isCancelOutside = isCancelOutside;
            return this;
        }

        public NetLoadingProgress create() {
            LayoutInflater inflater = LayoutInflater.from(context);
            View view = inflater.inflate(R.layout.net_loading, null);
            NetLoadingProgress netLoadingProgress = new NetLoadingProgress(context, R.style.NetLoadingDialog);
            TextView msgText = (TextView) view.findViewById(R.id.text);
            if (isShowMessage) {
                if (TextUtils.isEmpty(message)) {
                    message = context.getResources().getString(R.string.net_loadingtext);
                }
                msgText.setText(message);
            } else {
                msgText.setVisibility(View.GONE);
            }
            netLoadingProgress.setContentView(view);
            netLoadingProgress.setCancelable(isCancelable);
            netLoadingProgress.setCanceledOnTouchOutside(isCancelOutside);
            return netLoadingProgress;
        }
    }
}
