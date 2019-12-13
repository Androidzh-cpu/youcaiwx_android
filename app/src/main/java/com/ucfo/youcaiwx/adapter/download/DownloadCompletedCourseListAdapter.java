package com.ucfo.youcaiwx.adapter.download;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.download.DataBaseCourseListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-7-10.  上午 9:27
 * FileName: DownloadCompletedCourseListAdapter
 * Description:TODO 下载完成的课程列表
 */
public class DownloadCompletedCourseListAdapter extends BaseAdapter<DataBaseCourseListBean, DownloadCompletedCourseListAdapter.ViewHolder> {

    private List<DataBaseCourseListBean> list;
    private Context context;
    private boolean isEditing;

    public DownloadCompletedCourseListAdapter(List<DataBaseCourseListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifychange(boolean isEditing) {
        this.isEditing = isEditing;
        notifyDataSetChanged();
    }

    public void notifyChange(List<DataBaseCourseListBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        DataBaseCourseListBean bean = list.get(position);
        String courseTitle = bean.getCourseTitle();
        int courseDownloadNum = bean.getCourseDownloadNum();

        if (!TextUtils.isEmpty(courseTitle)) {
            holder.mCourseTitleItem.setText(courseTitle);
        }
        String string = context.getResources().getString(R.string.download_downloadNum, String.valueOf(courseDownloadNum));
        holder.mCourseTeacherItem.setText(string);

        holder.checkBox.setChecked(list.get(position).isChecked());
        //根据 编辑 点击后状太显示
        if (isEditing) {
            holder.checkBox.setVisibility(View.VISIBLE);
        } else {
            holder.checkBox.setVisibility(View.GONE);
        }
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View inflate = layoutInflater.inflate(R.layout.item_collection_courselist, viewGroup, false);
        DownloadCompletedCourseListAdapter.ViewHolder holder = new DownloadCompletedCourseListAdapter.ViewHolder(inflate);
        return holder;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mCourseTitleItem;
        private TextView mCourseTeacherItem;
        private CheckBox checkBox;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mCourseTitleItem = (TextView) itemView.findViewById(R.id.item_course_title);
            mCourseTeacherItem = (TextView) itemView.findViewById(R.id.item_course_teacher);
            checkBox = (CheckBox) itemView.findViewById(R.id.download_checkbox);
        }
    }
}
