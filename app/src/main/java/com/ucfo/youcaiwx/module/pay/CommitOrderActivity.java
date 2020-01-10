package com.ucfo.youcaiwx.module.pay;

import android.content.DialogInterface;
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

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cache.CacheMode;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;
import com.tencent.bugly.crashreport.CrashReport;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.ApiStores;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.AddressListBean;
import com.ucfo.youcaiwx.entity.pay.CommitOrderFormBean;
import com.ucfo.youcaiwx.entity.pay.InvoiceInfoBean;
import com.ucfo.youcaiwx.entity.pay.OrderFormDetailBean;
import com.ucfo.youcaiwx.module.main.activity.WebActivity;
import com.ucfo.youcaiwx.module.user.activity.MineAddressActivity;
import com.ucfo.youcaiwx.module.user.activity.MineCouponsActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.pay.PayPresenter;
import com.ucfo.youcaiwx.presenter.view.pay.IPayView;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.InvoiceActiveDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/**
 * Author: AND
 * Time: 2019-7-31 下午 6:05
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
    private SharedPreferencesUtils sharedPreferencesUtils;
    //TODO orderNumType(实际参数名叫is_live): 订单类型 1.直播订单 2.课程订单 3.积分订单 4.图书订单 5.后续教育
    private int userId, courserPackageId, finalCouponId = 0, finalAddressId = 0, orderNumType = 0;
    private Bundle bundle;
    private PayPresenter payPresenter;
    private float discountsprice = 0, finalPayPrice = 0, originalPayPace = 0;
    private InvoiceInfoBean invoiceInfoBean;

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
        ButterKnife.bind(this);

        sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        userId = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        // 上报后的Crash会显示该标签
        CrashReport.setUserSceneTag(this, Constant.BUGLY_TAG_ORDER);
    }

    @Override
    protected void initData() {
        super.initData();
        payPresenter = new PayPresenter(this);

        bundle = getIntent().getExtras();
        if (bundle != null) {
            courserPackageId = bundle.getInt(Constant.COURSE_PACKAGE_ID, 0);
            orderNumType = bundle.getInt(Constant.PAY_ORDERTYPE, 0);

            payPresenter.getOrderFormDetail(userId, courserPackageId, orderNumType);
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
        }
        //支付协议
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    btnNext.setBackground(ContextCompat.getDrawable(CommitOrderActivity.this, R.mipmap.icon_btnbackprimary));
                } else {
                    btnNext.setBackground(ContextCompat.getDrawable(CommitOrderActivity.this, R.mipmap.icon_btnbackgray));
                }
            }
        });
    }

    @OnClick({R.id.btn_address, R.id.btn_coupons, R.id.btn_invoice, R.id.btn_paynotice, R.id.btn_next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_address:
                Intent addressIntent = new Intent(this, MineAddressActivity.class);
                //TODO 添加地址
                bundle.putBoolean(Constant.PAY_EDIT, true);
                addressIntent.putExtras(bundle);
                startActivityForResult(addressIntent, Constant.REQUEST_ADDRESS);
                break;
            case R.id.btn_coupons:
                Intent couponIntent = new Intent(this, MineCouponsActivity.class);
                //TODO 优惠券
                bundle.putBoolean(Constant.PAY_EDIT, true);
                bundle.putInt(Constant.COURSE_PACKAGE_ID, courserPackageId);
                couponIntent.putExtras(bundle);
                startActivityForResult(couponIntent, Constant.REQUEST_COUPON);
                break;
            case R.id.btn_invoice:
                //TODO 发票
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                InvoiceActiveDialog invoiceActiveDialog = new InvoiceActiveDialog();
                invoiceActiveDialog.show(fragmentTransaction, "invoice");
                invoiceActiveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        invoiceInfoBean = invoiceActiveDialog.getInvoiceInfoBean();
                        if (invoiceInfoBean != null) {
                            String companyName = invoiceInfoBean.getCompanyName();
                            String personName = invoiceInfoBean.getPersonName();
                            String specialName = invoiceInfoBean.getSpecialName();
                            if (!TextUtils.isEmpty(personName)) {
                                invoiceContent.setText(personName);
                            }
                            if (!TextUtils.isEmpty(companyName)) {
                                invoiceContent.setText(companyName);
                            }
                            if (!TextUtils.isEmpty(specialName)) {
                                invoiceContent.setText(specialName);
                            }
                        } else {
                            invoiceContent.setText(getResources().getString(R.string.noInvoice));
                        }
                    }
                });
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
                        ToastUtil.showBottomShortText(this, getResources().getString(R.string.pay_addAddress));
                    } else {
                        if (orderNumType == 0) {
                            ToastUtil.showBottomShortText(this, getResources().getString(R.string.miss_params));
                            return;
                        }
                        if (payPresenter != null) {
                            payPresenter.commitOrderForm(userId, courserPackageId, orderNumType, finalAddressId, finalCouponId, invoiceInfoBean);
                        }
                    }
                } else {
                    ToastUtil.showBottomShortText(this, getResources().getString(R.string.pay_readPayNotice));
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

                        //添加地址隐藏
                        textAddAddress.setVisibility(View.GONE);
                    }
                } else {
                    initMineAddress();
                }
                break;
            case Constant.REQUEST_COUPON:
                //TODO 选取优惠券
                if (resultCode == Constant.REQUEST_COUPON) {
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

    //地址重置
    private void addressClear() {
        addressContent.setText("");
        addressPhone.setText("");
        addressName.setText("");
        finalAddressId = 0;
        textAddAddress.setVisibility(View.VISIBLE);
    }

    //查询是否有可用地址
    private void initMineAddress() {
        OkGo.<String>post(ApiStores.USER_ADDRESS_LIST)
                .params(Constant.USER_ID, userId)
                .cacheMode(CacheMode.FIRST_CACHE_THEN_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onStart(Request<String, ? extends Request> request) {
                        super.onStart(request);
                        showLoading();
                    }

                    @Override
                    public void onFinish() {
                        super.onFinish();
                        dismissPorcess();
                    }

                    @Override
                    public void onSuccess(Response<String> response) {
                        String body = response.body();
                        if (!body.equals("")) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = new JSONObject(body);
                                int code = jsonObject.optInt(Constant.CODE);
                                if (code == 200) {
                                    AddressListBean addressListBean = new Gson().fromJson(body, AddressListBean.class);
                                    List<AddressListBean.DataBean> data = addressListBean.getData();
                                    if (data != null && data.size() > 0) {
                                    } else {
                                        addressClear();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    /**
     * 使用优惠券
     */
    private void initCoupon(Bundle bundle) {
        int type = bundle.getInt(Constant.TYPE);
        //优惠券满减金额
        String couponPrice = bundle.getString(Constant.PAY_COUPONPRICE);
        finalCouponId = bundle.getInt(Constant.PAY_COUPONID);
        if (type == 1) {
            //满减优惠券

            //满减力度
            float floatCouponPrice = Float.parseFloat(couponPrice);
            //优惠额度
            discountsprice = floatCouponPrice;
            //最终支付价格
            finalPayPrice = originalPayPace - discountsprice;

            //优惠券显示金额
            couponCount.setText(String.format("-%s%s", getResources().getString(R.string.RMB), calculate(floatCouponPrice)));
            couponDes.setVisibility(View.GONE);
            //优惠金额
            textDiscountsprice.setText(String.format("-%s%s", getResources().getString(R.string.RMB), calculate(floatCouponPrice)));
            //最终支付金额
            textFinalprice.setText(String.format("%s%s", getResources().getString(R.string.RMB), calculate(finalPayPrice)));
        } else {
            //打折优惠券

            //折扣力度
            float floatCoupon = Float.parseFloat(couponPrice) / 10;
            finalPayPrice = originalPayPace * floatCoupon;
            discountsprice = originalPayPace - finalPayPrice;

            //优惠卷优惠金额
            couponCount.setText(String.format("-%s%s", getResources().getString(R.string.RMB), calculate(discountsprice)));
            couponDes.setVisibility(View.GONE);
            //优惠金额
            textDiscountsprice.setText((String.format("-%s%s", getResources().getString(R.string.RMB), calculate(discountsprice))));
            //最终支付价格
            textFinalprice.setText(String.format("%s%s", getResources().getString(R.string.RMB), calculate(finalPayPrice)));
        }
    }

    private String calculate(float price) {
        //构造方法的字符格式这里如果小数不足2位,会以0补足.
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        //format 返回的是字符串
        return decimalFormat.format(price);
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

                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (loadinglayout != null) {
                    loadinglayout.showEmpty();
                }
            }
        } else {
            if (loadinglayout != null) {
                loadinglayout.showEmpty();
            }
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

                if (TextUtils.isEmpty(orderNum)) {
                    ToastUtil.showBottomLongText(this, getResources().getString(R.string.pay_miss_orderinfo));
                    return;
                }
                Bundle bundle = new Bundle();
                bundle.putString(Constant.ORDER_NUM, orderNum);
                bundle.putFloat(Constant.COURSE_PRICE, payPrice);
                startActivity(PayActivity.class, bundle);
                finish();
            } else {
                ToastUtil.showBottomLongText(this, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomLongText(this, getResources().getString(R.string.operation_Error));
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
        //TODO 订单详情信息
        OrderFormDetailBean.DataBean.PackagesBean beanPackages = dataBean.getPackages();
        String name = beanPackages.getName();
        String teacherName = beanPackages.getTeacher_name();
        String validity = beanPackages.getValidity();
        String price = beanPackages.getPrice();
        String couponNum = beanPackages.getCoupon_num();
        //可用优惠券数量
        if (!TextUtils.isEmpty(couponNum)) {
            int parseInt = Integer.parseInt(couponNum);
            if (parseInt == 0) {
                //描述文字
                couponDes.setText(getResources().getString(R.string.pay_NoCouponsAvailable));
                //可用张数
                couponDes.setTextColor(ContextCompat.getColor(this, R.color.color_999999));
            } else {
                //描述文字
                couponDes.setText(getResources().getString(R.string.pay_CouponAvailable));
                //可用张数
                couponCount.setText(String.format("%s%s", couponNum, getResources().getString(R.string.mine_zhang)));
            }

        }
        //封面
        String appImg = beanPackages.getApp_img();
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .transform(new RoundedCornersTransformation(DensityUtil.dp2px(5), 0))
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(this, appImg, courseImage, requestOptions);
        //课程名
        if (!TextUtils.isEmpty(name)) {
            courseTitle.setText(name);
        }
        //讲师名
        if (!TextUtils.isEmpty(teacherName)) {
            courseTeacher.setText(teacherName);
        }
        //课程有效期
        courseEndtime.setText(getResources().getString(R.string.orderForm_endtime, validity));

        if (!TextUtils.isEmpty(price)) {
            //课程包价格
            float floatPrice = Float.parseFloat(price);
            //原价格
            originalPayPace = floatPrice;
            //最终支付价格
            finalPayPrice = floatPrice;

            //原价
            textPrice.setText(String.format("%s%s", getResources().getString(R.string.RMB), calculate(originalPayPace)));
            //最终支付价格
            textFinalprice.setText(String.format("%s%s", getResources().getString(R.string.RMB), calculate(finalPayPrice)));
            //优惠价格
            discountsprice = 0;
            textDiscountsprice.setText(String.valueOf(Math.round(discountsprice)));
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
        if (loadinglayout != null) {
            loadinglayout.showError();
        }
    }
}
