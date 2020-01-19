package com.ucfo.youcaiwx.adapter.address;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.address.AddressListBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.baseadapter.OnItemClickListener;

import java.util.List;

/**
 * Author: AND
 * Time: 2019-6-13.  下午 3:51
 * FileName: UserAddressListAdapter
 * Description:TODO 我的地址适配器
 */
public class UserAddressListAdapter extends BaseAdapter<AddressListBean.DataBean, UserAddressListAdapter.ViewHolder> {
    private List<AddressListBean.DataBean> list;
    private Context context;
    private OnItemClickListener onItemClickListener;

    public UserAddressListAdapter(List<AddressListBean.DataBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    public void notifyChange(List<AddressListBean.DataBean> dataBeanList) {
        this.list = dataBeanList;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        AddressListBean.DataBean bean = list.get(position);
        String address = bean.getAddress();
        int isDefault = bean.getIs_default();
        String telephone = bean.getTelephone();
        String consignee = bean.getConsignee();
        switch (isDefault) {
            case 1://默认地址
                holder.mDefaultItem.setVisibility(holder.mDefaultItem.getVisibility() == View.GONE ? View.VISIBLE : View.VISIBLE);
                break;
            case 2:
            default:
                holder.mDefaultItem.setVisibility(holder.mDefaultItem.getVisibility() == View.VISIBLE ? View.GONE : View.GONE);
                break;
        }
        if (!TextUtils.isEmpty(consignee)) {
            holder.mUserNameItem.setText(consignee);
        }
        if (!TextUtils.isEmpty(telephone)) {
            holder.mUserPhoneItem.setText(telephone);
        }
        if (!TextUtils.isEmpty(address)) {
            holder.mAddressItem.setText(address);
        }
        //调用接口,模拟点击
        holder.mEdittorItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用接口里的点击方法,传入布局和索引
                onItemClickListener.onItemClick(holder.itemView, position);
            }
        });

    }
    //定义一个公共方法,当外部类调用时可以通过此方法点击item
    public void setItemClick(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View inflate = layoutInflater.inflate(R.layout.item_useraddress_list, viewGroup, false);
        return new UserAddressListAdapter.ViewHolder(inflate);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mUserNameItem;
        private TextView mUserPhoneItem;
        private RoundTextView mDefaultItem;
        private TextView mAddressItem;
        private TextView mEdittorItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mUserNameItem = (TextView) itemView.findViewById(R.id.item_userName);
            mUserPhoneItem = (TextView) itemView.findViewById(R.id.item_userPhone);
            mDefaultItem = (RoundTextView) itemView.findViewById(R.id.item_default);
            mAddressItem = (TextView) itemView.findViewById(R.id.item_address);
            mEdittorItem = (TextView) itemView.findViewById(R.id.item_edittor);
        }
    }


}
