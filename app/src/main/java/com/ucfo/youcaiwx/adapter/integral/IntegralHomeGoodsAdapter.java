package com.ucfo.youcaiwx.adapter.integral;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.entity.integral.IntegralShopHomeBean;
import com.ucfo.youcaiwx.utils.baseadapter.BaseAdapter;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-10-14.  下午 5:24
 * Package: com.ucfo.youcaiwx.adapter.integral
 * FileName: IntegralHomeGoodsAdapter
 * Description:TODO 积分首页商品列表
 */
public class IntegralHomeGoodsAdapter extends BaseAdapter<IntegralShopHomeBean.DataBean.ShopBean, IntegralHomeGoodsAdapter.ViewHolder> {
    private Context context;
    private List<IntegralShopHomeBean.DataBean.ShopBean> list;

    public IntegralHomeGoodsAdapter(Context context, List<IntegralShopHomeBean.DataBean.ShopBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    protected void onBindDataViewHolder(ViewHolder holder, int position) {
        IntegralShopHomeBean.DataBean.ShopBean shopBean = list.get(position);
        String image = shopBean.getImage();
        String integral_price = shopBean.getIntegral_price();
        String name = shopBean.getName();
        if (!TextUtils.isEmpty(integral_price)) {
            holder.mGoodsIntegralItem.setText(context.getResources().getString(R.string.integral_holder, integral_price));
        }
        if (!TextUtils.isEmpty(name)) {
            holder.mGoodsTitleItem.setText(name);
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new CenterCrop(), new RoundedCornersTransformation(DensityUtil.dp2px(5), 0));
        GlideUtils.load(context, image, holder.mGoodsIamgeItem, requestOptions);
    }

    @Override
    public ViewHolder onCreateDataViewHolder(ViewGroup viewGroup, int itemType) {
        LayoutInflater layoutInflater = LayoutInflater.from(viewGroup.getContext());
        View view = layoutInflater.inflate(R.layout.item_integral_goods, viewGroup, false);
        return new ViewHolder(view);
    }

    protected class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mGoodsIamgeItem;
        private TextView mGoodsTitleItem, mGoodsIntegralItem;

        public ViewHolder(View view) {
            super(view);
            initView(view);
        }

        private void initView(@NonNull final View itemView) {
            mGoodsIamgeItem = (ImageView) itemView.findViewById(R.id.item_goods_iamge);
            mGoodsTitleItem = (TextView) itemView.findViewById(R.id.item_goods_title);
            mGoodsIntegralItem = (TextView) itemView.findViewById(R.id.item_goods_integral);
        }
    }

}
