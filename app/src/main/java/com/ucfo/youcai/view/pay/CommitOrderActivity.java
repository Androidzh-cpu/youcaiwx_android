package com.ucfo.youcai.view.pay;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.ucfo.youcai.R;
import com.ucfo.youcai.base.BaseActivity;
import com.ucfo.youcai.common.ApiStores;
import com.ucfo.youcai.common.Constant;
import com.ucfo.youcai.entity.pay.CommitOrderFormBean;
import com.ucfo.youcai.entity.pay.OrderFormDetailBean;
import com.ucfo.youcai.presenter.presenterImpl.pay.PayPresenter;
import com.ucfo.youcai.presenter.view.IPayView;
import com.ucfo.youcai.utils.glideutils.GlideRoundTransform;
import com.ucfo.youcai.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcai.utils.toastutils.ToastUtil;
import com.ucfo.youcai.view.main.activity.WebActivity;
import com.ucfo.youcai.view.user.activity.MineCouponsActivity;
import com.ucfo.youcai.view.user.activity.UserAddressActivity;
import com.ucfo.youcai.widget.customview.LoadingLayout;
import com.ucfo.youcai.widget.dialog.InvoiceActiveDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-7-31 下午 6:05
 * Package: com.ucfo.youcai.view.pay
 * FileName: CommitOrderActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 提交订单
 */
public class CommitOrderActivity extends BaseActivity implements IPayView {

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
    @BindView(R.id.course_teacher)
    TextView courseTeacher;
    @BindView(R.id.course_endtime)
    TextView courseEndtime;
    @BindView(R.id.coupon_count)
    TextView couponCount;
    @BindView(R.id.coupon_des)
    TextView couponDes;
    @BindView(R.id.btn_coupons)
    LinearLayout btnCoupons;
    @BindView(R.id.invoice_content)
    TextView invoiceContent;
    @BindView(R.id.btn_invoice)
    LinearLayout btnInvoice;
    @BindView(R.id.text_price)
    TextView textPrice;
    @BindView(R.id.text_discountsprice)
    TextView textDiscountsprice;
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
    private CommitOrderActivity context;
    private SharedPreferencesUtils sharedPreferencesUtils;
    //TODO orderNumType: 订单类型1直播订单、2课程订单
    private int userId, courserPackageId, finalCouponId = 0, finalAddressId = 0, orderNumType = 2;
    private Bundle bundle;
    private PayPresenter payPresenter;
    private float discountsprice = 0, finalPayPrice = 0, originalPayPace = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_commit_order;
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

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
        titlebarMidtitle.setText(getResources().getString(R.string.pay_commitOrder));
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        super.initView(savedInstanceState);
        context = this;
        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(context);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            courserPackageId = bundle.getInt(Constant.COURSE_PACKAGE_ID, 0);
        } else {
            loadinglayout.showEmpty();
        }
        payPresenter = new PayPresenter(this);

        payPresenter.getOrderFormDetail(userId, courserPackageId);
        //支付协议
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnNext.setBackground(ContextCompat.getDrawable(context, R.mipmap.icon_btnbackprimary));
                } else {
                    btnNext.setBackground(ContextCompat.getDrawable(context, R.mipmap.icon_btnbackgray));
                }
            }
        });
    }

    @OnClick({R.id.btn_address, R.id.btn_coupons, R.id.btn_invoice, R.id.btn_paynotice, R.id.btn_next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_address:
                Intent addressIntent = new Intent(this, UserAddressActivity.class);
                //TODO 添加地址
                bundle.putBoolean(Constant.PAY_EDIT, true);
                addressIntent.putExtras(bundle);
                startActivityForResult(addressIntent, 10000);
                break;
            case R.id.btn_coupons:
                Intent couponIntent = new Intent(this, MineCouponsActivity.class);
                //TODO 优惠券
                bundle.putBoolean(Constant.PAY_EDIT, true);
                bundle.putInt(Constant.COURSE_PACKAGE_ID, courserPackageId);
                couponIntent.putExtras(bundle);
                startActivityForResult(couponIntent, 10001);
                break;
            case R.id.btn_invoice:
                //TODO 发票
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                InvoiceActiveDialog invoiceActiveDialog = new InvoiceActiveDialog();
                invoiceActiveDialog.show(fragmentTransaction, "invoice");
                break;
            case R.id.btn_paynotice:
                //TODO 支付须知
                bundle.putString(Constant.WEB_TITLE, getResources().getString(R.string.pay_agreement));
                bundle.putString(Constant.WEB_URL, ApiStores.PAY_AGREEMENT);
                startActivity(WebActivity.class, bundle);
                break;
            case R.id.btn_next:
                //TODO 提交订单
                if (checkbox.isChecked()) {
                    if (finalAddressId == 0) {
                        ToastUtil.showBottomShortText(this, "请添加地址");
                    } else {
                        payPresenter.commitOrderForm(userId, courserPackageId, orderNumType, finalAddressId, finalCouponId);
                    }
                } else {
                    ToastUtil.showBottomShortText(this, "请同意支付协议");
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 10000:
                //TODO 选取地址
                if (resultCode == 10000) {
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
                    }
                }
                break;
            case 10001:
                //TODO 选取优惠券
                if (resultCode == 10001) {
                    if (data.getExtras() != null) {
                        Bundle bundle = data.getExtras();
                        initCoupon(bundle);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 使用优惠券
     */
    private void initCoupon(Bundle bundle) {
        int type = bundle.getInt(Constant.TYPE);
        String couponPrice = bundle.getString(Constant.PAY_COUPONPRICE);
        finalCouponId = bundle.getInt(Constant.PAY_COUPONID);
        if (type == 1) {
            //满减优惠券
            float floatCouponPrice = Float.parseFloat(couponPrice);
            int intCouponPrice = Math.round(floatCouponPrice);

            discountsprice = intCouponPrice;
            finalPayPrice = originalPayPace - discountsprice;

            couponCount.setText(String.valueOf("-" + getResources().getString(R.string.RMB) + intCouponPrice));
            couponDes.setVisibility(View.GONE);
            textDiscountsprice.setText(String.valueOf("-" + getResources().getString(R.string.RMB) + intCouponPrice));
            textFinalprice.setText(String.valueOf(getResources().getString(R.string.RMB) + String.valueOf(Math.round(finalPayPrice))));
        } else {
            //打折优惠券
            float floatCouponPrice = Float.parseFloat(couponPrice) / 10;
            finalPayPrice = Math.round(originalPayPace * floatCouponPrice);
            discountsprice = originalPayPace - finalPayPrice;

            couponCount.setText(String.valueOf("-" + getResources().getString(R.string.RMB) + Math.round(discountsprice)));
            couponDes.setVisibility(View.GONE);
            textDiscountsprice.setText(String.valueOf("-" + getResources().getString(R.string.RMB) + Math.round(discountsprice)));
            textFinalprice.setText(String.valueOf(getResources().getString(R.string.RMB) + String.valueOf(Math.round(finalPayPrice))));
        }
    }

    /**
     * 订单详情
     */
    @Override
    public void getOrderFormDetail(OrderFormDetailBean data) {
        if (data != null) {
            if (data.getData() != null) {
                OrderFormDetailBean.DataBean dataBean = data.getData();

                initOrderFormData(dataBean);

                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    /**
     * 提交订单
     */
    @Override
    public void commitOrderForm(CommitOrderFormBean bean) {
        if (bean != null) {
            if (bean.getData() != null) {
                CommitOrderFormBean.DataBean dataBean = bean.getData();
                String orderNum = dataBean.getOrder_num();
                float payPrice = dataBean.getPay_price();

                Bundle bundle = new Bundle();
                bundle.putString(Constant.ORDER_NUM, orderNum);
                bundle.putFloat(Constant.COURSE_PRICE, payPrice);
                startActivity(PayActivity.class, bundle);
                finish();
            } else {
                ToastUtil.showBottomLongText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomLongText(context, getResources().getString(R.string.operation_Error));
        }
    }

    /**
     * 订单详情信息
     */
    private void initOrderFormData(OrderFormDetailBean.DataBean dataBean) {
        OrderFormDetailBean.DataBean.AddressBean address = dataBean.getAddress();
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

        //可用优惠券数量
        if (dataBean.getCoupon_num() == 0) {
            couponDes.setText(getResources().getString(R.string.pay_NoCouponsAvailable));
            couponDes.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
        } else {
            couponDes.setText(getResources().getString(R.string.pay_CouponAvailable));
            couponCount.setText(String.valueOf(dataBean.getCoupon_num() + getResources().getString(R.string.mine_zhang)));
        }
        //TODO 订单详情信息
        OrderFormDetailBean.DataBean.PackagesBean beanPackages = dataBean.getPackages();
        String name = beanPackages.getName();
        String teacherName = beanPackages.getTeacher_name();
        int studyDays = beanPackages.getStudy_days();
        String price = beanPackages.getPrice();
        //封面
        String appImg = beanPackages.getApp_img();
        Glide.with(context).load(appImg).asBitmap().placeholder(R.mipmap.banner_default)
                .transform(new CenterCrop(context), new GlideRoundTransform(context, 4)).error(R.mipmap.image_loaderror).dontAnimate().skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.ALL).priority(Priority.HIGH).into(courseImage);
        //课程名
        if (!TextUtils.isEmpty(name)) {
            courseTitle.setText(name);
        }
        //讲师名
        if (!TextUtils.isEmpty(teacherName)) {
            courseTeacher.setText(teacherName);
        }
        //结束时间
        courseEndtime.setText(getResources().getString(R.string.orderForm_endtime, String.valueOf(studyDays)));
        //原价
        float floatPrice = Float.parseFloat(price);
        originalPayPace = floatPrice;
        finalPayPrice = floatPrice;
        textPrice.setText(String.valueOf(getResources().getString(R.string.RMB) + String.valueOf(Math.round(originalPayPace))));
        //最终支付价格
        textFinalprice.setText(String.valueOf(getResources().getString(R.string.RMB) + String.valueOf(Math.round(floatPrice))));
        //优惠价格
        discountsprice = 0;
        textDiscountsprice.setText(String.valueOf(Math.round(discountsprice)));
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
        loadinglayout.showError();
    }
}
