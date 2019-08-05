package com.ucfo.youcai.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.ucfo.youcai.R;
import com.ucfo.youcai.common.Constant;

/**
 * Author:29117
 * Time: 2019-3-19.  上午 10:20
 * Email:2911743255@qq.com
 * ClassName: CallUtils
 * Package: com.ucfo.youcai.utils
 * Description:TODO  呼叫联系人
 * Detail:
 */
public class CallUtils {
    public static void makeCall(Context context, String phoneNum) {
        try {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            Uri data = Uri.parse("tel:" + phoneNum);
            intent.setData(data);
            if (!(context instanceof Activity)) {
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            }
            context.startActivity(intent);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }


    public static void makeCallWithPermission(AppCompatActivity context) {
        SoulPermission.getInstance()
                .checkAndRequestPermission(Manifest.permission.CALL_PHONE, new CheckRequestPermissionListener() {
                    @Override
                    public void onPermissionOk(Permission permission) {

                        try {
                            Intent intent = new Intent(Intent.ACTION_DIAL);
                            Uri data = Uri.parse("tel:" + Constant.SERVICE_NUM);
                            intent.setData(data);
                            if (!(context instanceof Activity)) {
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            }
                            context.startActivity(intent);
                        } catch (SecurityException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPermissionDenied(Permission permission) {
                        if (permission.shouldRationale()) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.WhiteDialogStyle);
                            builder.setTitle(context.getResources().getString(R.string.explication));
                            builder.setMessage(context.getResources().getString(R.string.permission_call));
                            builder.setCancelable(false);
                            builder.setPositiveButton(context.getResources().getString(R.string.donner), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    //用户确定以后，重新执行请求原始流程
                                    SoulPermission.getInstance().goApplicationSettings();
                                }
                            });
                            builder.create();
                            builder.show();
                        } else {
                            Toast.makeText(context, context.getResources().getString(R.string.permission_explication), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
