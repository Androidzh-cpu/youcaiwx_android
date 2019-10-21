package com.ucfo.youcaiwx.module.integral;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.integral.IntegralAddOrderNumBean;
import com.ucfo.youcaiwx.entity.integral.IntegralOrderExchangeResultBean;
import com.ucfo.youcaiwx.entity.integral.IntegralProductDetailBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.integral.IntegralExchangePresenter;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeDetailView;
import com.ucfo.youcaiwx.presenter.view.integral.IIntegralExchangeView;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Author: AND
 * Time: 2019-10-16 下午 1:57
 * Package: com.ucfo.youcaiwx.module.integral
 * FileName: CommodityExchangeActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 积分商品兑换
 */
public class CommodityExchangeActivity extends BaseActivity implements IIntegralExchangeDetailView, IIntegralExchangeView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.image_product)
    ImageView imageProduct;
    @BindView(R.id.tv_product_name)
    TextView tvProductName;
    @BindView(R.id.tv_integralprice)
    TextView tvIntegralprice;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
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
    @BindView(R.id.item_coupons_des)
    TextView itemCouponsDes;
    @BindView(R.id.item_coupons_validity)
    TextView itemCouponsValidity;
    @BindView(R.id.item_coupons_range)
    RoundTextView itemCouponsRange;
    @BindView(R.id.linear_coupon)
    LinearLayout linearCoupon;
    private IntegralExchangePresenter integralExchangePresenter;
    private SharedPreferencesUtils sharedPreferencesUtils;
    private int user_id;
    private Bundle bundle;
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        inquryProductDetailed();
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
        titlebarMidtitle.setText(getResources().getString(R.string.integral_exchangegoods));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_commodity_exchange;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        loadinglayout.showContent();
    }

    @Override
    protected void initData() {
        super.initData();
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
        bundle = getIntent().getExtras();
        if (bundle != null) {
            productId = bundle.getString(Constant.PRODUCT_ID);
        }

        integralExchangePresenter = new IntegralExchangePresenter();
        integralExchangePresenter.setIntegralExchangeDetailView(this);
        integralExchangePresenter.setIntegralExchangeView(this);
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inquryProductDetailed();
            }
        });
    }

    /**
     * 查询商品详情
     */
    private void inquryProductDetailed() {
        if (integralExchangePresenter != null) {
            integralExchangePresenter.inquireProductDetail(user_id, productId);
        }
    }

    @Override
    public void inquiryProductDetail(IntegralProductDetailBean data) {
        if (data != null) {

            initProductDetail(data);

            loadinglayout.showContent();
        } else {
            loadinglayout.showEmpty();
        }
    }

    /**
     * 设置商品详情
     */
    private void initProductDetail(IntegralProductDetailBean data) {
        IntegralProductDetailBean.DataBean bean = data.getData();

        //TODO 1实体  2课程  3优惠卷
        String type = bean.getType();
        if (TextUtils.equals(type, String.valueOf(1))) {
            linearCoupon.setVisibility(GONE);
            //TODO 实体商品
            String image = bean.getImage();
            String integralPrice = bean.getIntegral_price();
            String name = bean.getName();
            String introduce = bean.getIntroduce();

            if (!TextUtils.isEmpty(name)) {
                tvProductName.setText(name);

            }
            if (!TextUtils.isEmpty(introduce)) {
                tvIntroduce.setText(introduce);
            }
            if (!TextUtils.isEmpty(integralPrice)) {
                tvIntegralprice.setText(getResources().getString(R.string.integral_holder, integralPrice));
            }
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror);
            GlideUtils.load(this, image, imageProduct, requestOptions);
        } else if (TextUtils.equals(type, String.valueOf(2))) {
            linearCoupon.setVisibility(GONE);
            //TODO 课程
            String image = bean.getImage();
            String integralPrice = bean.getIntegral_price();
            String name = bean.getName();
            String introduce = bean.getIntroduce();

            if (!TextUtils.isEmpty(name)) {
                tvProductName.setText(name);

            }
            if (!TextUtils.isEmpty(introduce)) {
                tvIntroduce.setText(introduce);
            }
            if (!TextUtils.isEmpty(integralPrice)) {
                tvIntegralprice.setText(getResources().getString(R.string.integral_holder, integralPrice));
            }
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.mipmap.icon_default)
                    .error(R.mipmap.image_loaderror);
            GlideUtils.load(this, image, imageProduct, requestOptions);
        } else if (TextUtils.equals(type, String.valueOf(3))) {
            imageProduct.setVisibility(GONE);
            linearCoupon.setVisibility(VISIBLE);
            linearCoupon.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_usable_coupon));
            //TODO 优惠券
            String name = bean.getName();//优惠券名
            String couponPrice = bean.getCoupon_price();//优惠金额
            String range = bean.getRange();//适用范围1课程2直播3全部
            String endTime = bean.getEnd_time();//到期时间
            String isType = bean.getIs_type();//1:满减 2:打折
            String integralPrice = bean.getIntegral_price();
            String introduce = bean.getIntroduce();

            if (!TextUtils.isEmpty(couponPrice)) {
                if (TextUtils.equals(isType, String.valueOf(1))) {//满减
                    float v = Float.parseFloat(couponPrice);
                    int b = Math.round(v);
                    itemCouponsMoney.setText(String.valueOf(b));
                    textRmb.setVisibility(VISIBLE);
                    textDiscount.setVisibility(GONE);
                } else {//打折劵
                    itemCouponsMoney.setText(String.valueOf(couponPrice));
                    textRmb.setVisibility(View.GONE);
                    textDiscount.setVisibility(VISIBLE);
                }
            } else {
                itemCouponsMoney.setText(String.valueOf(0));
            }

            if (!TextUtils.isEmpty(name)) {
                itemCouponsDes.setText(name);
                tvProductName.setText(name);
            }
            if (!TextUtils.isEmpty(endTime)) {
                itemCouponsValidity.setText(getResources().getString(R.string.coupon_ValidityEndTime, endTime));
            }
            if (!TextUtils.isEmpty(range)) {
                int parseInt = Integer.parseInt(range);
                switch (parseInt) {
                    case 1://课程
                        itemCouponsRange.setText(getResources().getString(R.string.coupon_course));
                        break;
                    case 2://直播
                        itemCouponsRange.setText(getResources().getString(R.string.coupon_live));
                        break;
                    case 3://全部
                        itemCouponsRange.setText(getResources().getString(R.string.coupon_all));
                        break;
                    default:
                        break;
                }
            }
            if (!TextUtils.isEmpty(integralPrice)) {
                tvIntegralprice.setText(getResources().getString(R.string.integral_holder, integralPrice));
            }
            if (!TextUtils.isEmpty(introduce)) {
                tvIntroduce.setText(introduce);
            }
        }

        String numberStatus = bean.getNumber_status();
        if (TextUtils.equals(numberStatus, String.valueOf(1))) {
            //TODO 可以兑换
            btnNext.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_btnbackprimary));
            btnNext.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            btnNext.setText(getResources().getString(R.string.integral_button_gotoExchange));
            btnNext.setEnabled(true);
        } else if (TextUtils.equals(numberStatus, String.valueOf(2))) {
            //TODO 库存不足
            btnNext.setBackground(ContextCompat.getDrawable(this, R.mipmap.icon_btnbackgray));
            btnNext.setTextColor(ContextCompat.getColor(this, R.color.colorWhite));
            btnNext.setText(getResources().getString(R.string.integral_button_integralLack));
            btnNext.setEnabled(false);
        }
    }

    @Override
    public void showLoading() {
        setProcessLoading(null, true);
    }

    @Override
    public void showLoadingFinish() {
        dismissPorcess();
        btnNext.setEnabled(true);
    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }

    @OnClick(R.id.btn_next)
    public void onViewClicked() {
        //添加积分订单
        integralExchangePresenter.integralAddOrderNumber(user_id, productId);
        btnNext.setEnabled(false);
    }

    @Override
    public void integralAddOrderForm(IntegralAddOrderNumBean dataBean, String desc) {
        if (dataBean != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.INTEGRAL_ADDORDERDETAIAL, dataBean);
            startActivity(IntegralCommitActivity.class, bundle);
        } else {
            ToastUtil.showBottomShortText(this, desc);
        }
    }

    @Override
    public void integralOrderFormExchange(IntegralOrderExchangeResultBean data) {
        //TODO nothing
    }
}
