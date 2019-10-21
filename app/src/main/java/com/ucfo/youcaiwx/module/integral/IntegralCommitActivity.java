package com.ucfo.youcaiwx.module.integral;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.request.RequestOptions;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralAddOrderNumBean;
import com.ucfo.youcaiwx.entity.integral.IntegralOrderExchangeResultBean;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.user.activity.UserAddressActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralExchangePresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeView;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-10-17 下午 5:18
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: IntegralCommitActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 积分订单提交页
 */
public class IntegralCommitActivity extends BaseActivity implements IIntegralExchangeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.text_addAddress)
    TextView textAddAddress;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_phone)
    TextView addressPhone;
    @BindView(R.id.address_content)
    TextView addressContent;
    @BindView(R.id.btn_address)
    LinearLayout btnAddress;
    @BindView(R.id.course_image)
    ImageView courseImage;
    @BindView(R.id.course_title)
    TextView courseTitle;
    @BindView(R.id.text_finalprice)
    TextView textFinalprice;
    @BindView(R.id.checkbox)
    CheckBox checkbox;
    @BindView(R.id.btn_paynotice)
    TextView btnPaynotice;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.text_rmb)
    TextView textRmb;
    @BindView(R.id.item_coupons_money)
    TextView itemCouponsMoney;
    @BindView(R.id.text_discount)
    TextView textDiscount;
    @BindView(R.id.linear_coupon)
    LinearLayout linearCoupon;
    private int userId, finalAddressId = 0;
    private IntegralExchangePresenter integralExchangePresenter;
    private String productId;
    private SharedPreferencesUtils sharedPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_integral_commit;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.integral_commit));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Serializable serializable = bundle.getSerializable(Constant.INTEGRAL_ADDORDERDETAIAL);
            if (serializable != null) {
                IntegralAddOrderNumBean integralAddOrderNumBean = (IntegralAddOrderNumBean) serializable;
                initIntegralOrderDetail(integralAddOrderNumBean);
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        integralExchangePresenter = new IntegralExchangePresenter();
        integralExchangePresenter.setIntegralExchangeView(this);
    }

    /**
     * 初始化订单信息
     */
    private void initIntegralOrderDetail(IntegralAddOrderNumBean bean) {
        IntegralAddOrderNumBean.DataBean data = bean.getData();
        if (data != null) {
            IntegralAddOrderNumBean.DataBean.AddressBean address = data.getAddress();
            if (address != null) {
                //我的收货地址
                if (address.getAddress_id() == 0) {
                    textAddAddress.setVisibility(View.VISIBLE);
                    finalAddressId = 0;
                } else {
                    textAddAddress.setVisibility(View.GONE);
                    String addressAddress = address.getAddress();
                    String telephone = address.getTelephone();
                    String consignee = address.getConsignee();
                    int addressId = address.getAddress_id();
                    if (!TextUtils.isEmpty(addressAddress)) {
                        addressContent.setText(addressAddress);
                    }
                    if (!TextUtils.isEmpty(telephone)) {
                        addressPhone.setText(telephone);
                    }
                    if (!TextUtils.isEmpty(consignee)) {
                        addressName.setText(consignee);
                    }
                    finalAddressId = addressId;
                }
            }

            //TODO 商品信息
            IntegralAddOrderNumBean.DataBean.GoodsBean goodsBean = data.getGoods();
            String type = goodsBean.getType();
            String image = goodsBean.getImage();
            productId = goodsBean.getId();
            if (TextUtils.equals(type, String.valueOf(1))) {
                //TODO 实体商品
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.icon_default)
                        .error(R.mipmap.image_loaderror)
                        .transform(new CenterCrop(), new RoundedCornersTransformation(DensityUtil.dp2px(5), 0))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                GlideUtils.load(this, image, courseImage, requestOptions);

                linearCoupon.setVisibility(View.INVISIBLE);
            } else if (TextUtils.equals(type, String.valueOf(2))) {
                //TODO 课程
                RequestOptions requestOptions = new RequestOptions()
                        .placeholder(R.mipmap.icon_default)
                        .error(R.mipmap.image_loaderror)
                        .transform(new CenterCrop(), new RoundedCornersTransformation(DensityUtil.dp2px(5), 0))
                        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                GlideUtils.load(this, image, courseImage, requestOptions);

                linearCoupon.setVisibility(View.INVISIBLE);
            } else if (TextUtils.equals(type, String.valueOf(3))) {
                //TODO 优惠券
                courseImage.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_integral_coupon));
                linearCoupon.setVisibility(View.VISIBLE);

                String isType = goodsBean.getIs_type();
                String couponPrice = goodsBean.getCoupon_price();
                if (TextUtils.equals(isType, String.valueOf(1))) {//满减
                    float v = Float.parseFloat(couponPrice);
                    int b = Math.round(v);
                    itemCouponsMoney.setText(String.valueOf(b));
                    textRmb.setVisibility(View.VISIBLE);
                    textDiscount.setVisibility(View.GONE);
                } else {//打折劵
                    itemCouponsMoney.setText(String.valueOf(couponPrice));
                    textRmb.setVisibility(View.GONE);
                    textDiscount.setVisibility(View.VISIBLE);
                }
            }

            String integralPrice = goodsBean.getIntegral_price();
            String name = goodsBean.getName();

            textFinalprice.setText(getResources().getString(R.string.integral_holder, integralPrice));
            courseTitle.setText(name);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_ADDRESS:
                //TODO 选取地址
                if (resultCode == Constant.REQUEST_ADDRESS) {
                    if (data.getExtras() != null) {
                        Bundle bundle = data.getExtras();
                        String consignee = bundle.getString(Constant.ADDRESS_NAME);
                        int addressId = bundle.getInt(Constant.ADDRESS_ID);
                        String addressAddress = bundle.getString(Constant.ADDRESS);
                        String telephone = bundle.getString(Constant.TELEPHONE);

                        addressContent.setText(addressAddress);
                        addressPhone.setText(telephone);
                        addressName.setText(consignee);
                        finalAddressId = addressId;

                        textAddAddress.setVisibility(View.GONE);
                    }
                }
                break;
            default:
                break;
        }

    }

    @OnClick({R.id.btn_address, R.id.btn_next, R.id.btn_paynotice})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_address:
                //TODO 添加地址
                Intent addressIntent = new Intent(this, UserAddressActivity.class);
                bundle.putBoolean(Constant.PAY_EDIT, true);
                addressIntent.putExtras(bundle);
                startActivityForResult(addressIntent, Constant.REQUEST_ADDRESS);
                break;
            case R.id.btn_next:
                //TODO 积分支付
                if (!checkbox.isChecked()) {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.pay_readPayNotice));
                    return;
                }
                if (finalAddressId == 0) {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.pay_addAddress));
                    return;
                }
                //积分支付
                integralExchangePresenter.integralExchangePay(userId, finalAddressId, productId);
                break;
            case R.id.btn_paynotice:
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.pay_agreement));
                bundle.putString(Constant.WEB_URL, ApiStores.PAY_AGREEMENT);
                startActivity(WebActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Override
    public void integralAddOrderForm(IntegralAddOrderNumBean dataBean, String desc) {
        //TODO nothing
    }

    @Override
    public void integralOrderFormExchange(IntegralOrderExchangeResultBean data) {
        if (data != null) {
            IntegralOrderExchangeResultBean.DataBean bean = data.getData();
            String integral = bean.getIntegral();
            String order_num = bean.getOrder_num();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.ORDER_NUM, order_num);
            bundle.putString(Constant.COURSE_PRICE, integral);
            startActivity(IntegralPayResultActivity.class, bundle);
        } else {
            ToastUtil.showBottomShortText(this, getResources().getString(R.string.integral_payFailed));
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
    }

    @Override
    public void showError() {

    }
}
