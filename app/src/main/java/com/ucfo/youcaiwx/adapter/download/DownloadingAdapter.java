package com.ucfo.youcaiwx.adapter.download;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.aliyun.vodplayer.downloader.AliyunDownloadMediaInfo;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.download.AlivcDownloadMediaInfo;

import java.math.BigDecimal;
import java.util.ArrayList;

/**
 * Author: AND
 * Time: 2019-7-4.  上午 9:42
 * FileName: DownloadingAdapter
 * Description:TODO 下载中的适配器
 */
public class DownloadingAdapter extends BaseAdapter {
    private ArrayList<AlivcDownloadMediaInfo> list;
    private Context context;
    private boolean isEditing;

    public DownloadingAdapter(ArrayList<AlivcDownloadMediaInfo> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void setData(ArrayList<AlivcDownloadMediaInfo> arrayList) {
        this.list = arrayList;
        notifyDataSetChanged();
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void notifychange(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // 1.想办法把自己定义的布局转换成一个view对象就可以了
        View view = null;
        ViewHolder holder = null;
        if (convertView != null) {
            view = convertView;
            holder = (ViewHolder) view.getTag();
        } else {
            // 创建新的view对象，可以通过打气筒把一个布局资源转换成一个view对象
            if (view == null) {
                view = LayoutInflater.from(context).inflate(R.layout.item_downloading_list, parent, false);
            }
            holder = new ViewHolder();
            holder.checkBox = (CheckBox) view.findViewById(R.id.download_checkbox);
            holder.downloadTitle = (TextView) view.findViewById(R.id.download_title);
            holder.downloadProgressBar = (ProgressBar) view.findViewById(R.id.download_seekprogress);
            holder.downloadStatus = (TextView) view.findViewById(R.id.download_status);
            holder.downloadHaved = (TextView) view.findViewById(R.id.download_havesize);
            holder.downloadTotalSize = (TextView) view.findViewById(R.id.download_totalsize);
            holder.downloadSpeed = (TextView) view.findViewById(R.id.download_speed);
            holder.downloadBtn = (ImageView) view.findViewById(R.id.download_btn);
            view.setTag(holder);
        }
        //根据 编辑 点击后状太显示
        if (isEditing) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
        AlivcDownloadMediaInfo alivcDownloadMediaInfo = list.get(position);
        AliyunDownloadMediaInfo info = alivcDownloadMediaInfo.getAliyunDownloadMediaInfo();

        String title = info.getTitle();//视频标题
        AliyunDownloadMediaInfo.Status status = info.getStatus();//视频状态
        long totalSize = info.getSize();//视频大小
        int progress = info.getProgress();//视频下载进度
        long haveSize = progress * totalSize / holder.downloadProgressBar.getMax();//已下载大小
        boolean checkedState = alivcDownloadMediaInfo.isCheckedState();//当前下载item是否被选中

        holder.checkBox.setChecked(checkedState);
        holder.downloadTitle.setText(title);
        holder.downloadTotalSize.setText(getFormatSize(totalSize));
        holder.downloadHaved.setText(getFormatSize(haveSize));
        holder.downloadProgressBar.setProgress(progress);
        switch (status) {//视频的各种状态
            case Complete:
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloadCompleted));
                holder.downloadBtn.setVisibility(View.GONE);
                holder.downloadProgressBar.setVisibility(View.GONE);
                break;
            case Error:
                holder.downloadBtn.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_down_start));
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloadError));
                break;
            case Stop:
                holder.downloadBtn.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_down_start));
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloadStop));
                break;
            case Wait:
                holder.downloadBtn.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_down_start));
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloadWait));
                break;
            case Prepare:
                holder.downloadBtn.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_down_start));
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloadPrepared));
                break;
            case Start:
                holder.downloadBtn.setImageDrawable(ContextCompat.getDrawable(context,R.mipmap.icon_down_pause));
                holder.downloadStatus.setText(context.getResources().getString(R.string.download_downloading));
                break;
            default:
                //TODO nothing
                break;
        }
        return view;
    }

    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
//            return size + "Byte";
            return "0K";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }

    protected class ViewHolder {
        public CheckBox checkBox;
        public TextView downloadTitle;
        public ProgressBar downloadProgressBar;
        private TextView downloadStatus;
        private TextView downloadHaved;
        private TextView downloadTotalSize;
        private TextView downloadSpeed;
        private ImageView downloadBtn;
    }

}
