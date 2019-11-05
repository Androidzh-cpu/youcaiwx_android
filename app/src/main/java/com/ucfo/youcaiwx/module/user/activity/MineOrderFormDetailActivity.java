package com.ucfo.youcaiwx.module.user.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.flyco.roundview.RoundTextView;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.address.StateStatusBean;
import com.ucfo.youcaiwx.entity.user.MineOrderFormDetailBean;
import com.ucfo.youcaiwx.entity.user.MineOrderListBean;
import com.ucfo.youcaiwx.module.pay.PayActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.MineOrderFormPresenter;
import com.ucfo.youcaiwx.presenter.view.user.IMineOrderFromView;
import com.ucfo.youcaiwx.utils.CallUtils;
import com.ucfo.youcaiwx.utils.glideutils.GlideUtils;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.utils.toastutils.ToastUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.customview.NiceImageView;
import com.ucfo.youcaiwx.widget.dialog.InvoiceActiveDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-8-12 下午 3:35
 * FileName: MineOrderFormDetailActivity
 * ORG: www.youcaiwx.com
 * Description:TODO 订单详情
 */
public class MineOrderFormDetailActivity extends BaseActivity implements IMineOrderFromView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.address_name)
    TextView addressName;
    @BindView(R.id.address_phone)
    TextView addressPhone;
    @BindView(R.id.address_content)
    TextView addressContent;
    @BindView(R.id.linear_address)
    RelativeLayout linearAddress;
    @BindView(R.id.item_course_image)
    NiceImageView itemCourseImage;
    @BindView(R.id.item_course_title)
    TextView itemCourseTitle;
    @BindView(R.id.item_course_teacher)
    TextView itemCourseTeacher;
    @BindView(R.id.item_course_time)
    TextView itemCourseTime;
    @BindView(R.id.item_course_effective)
    TextView itemCourseEffective;
    @BindView(R.id.order_coursePrice)
    TextView orderCoursePrice;
    @BindView(R.id.order_preferentialPrice)
    TextView orderPreferentialPrice;
    @BindView(R.id.order_realPrice)
    TextView orderRealPrice;
    @BindView(R.id.order_number)
    TextView orderNumber;
    @BindView(R.id.order_time)
    TextView orderTime;
    @BindView(R.id.order_service)
    TextView orderService;
    @BindView(R.id.order_cancel)
    TextView orderCancel;
    @BindView(R.id.order_success)
    TextView orderSuccess;
    @BindView(R.id.order_invoice)
    TextView orderInvoice;
    @BindView(R.id.order_pay)
    RoundTextView orderPay;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.holder_view)
    View holderView;
    @BindView(R.id.order_edit)
    TextView orderEdit;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.linear_holder)
    LinearLayout linearHolder;
    private Bundle bundle;
    private String order_number;
    private MineOrderFormDetailActivity context;
    private int user_id;
    private MineOrderFormPresenter mineOrderFormPresenter;
    private int order_status;
    private int address_id;
    private String payPrice;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mineOrderFormPresenter.getOrderFormDetail(user_id, order_status, order_number);
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_mine_order_form_detail;
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
        titlebarRighttitle.setVisibility(View.GONE);
        titlebarMidtitle.setText(getResources().getString(R.string.mine_orderDetail));
        showline.setVisibility(View.GONE);
        titlebarToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void initData() {
        super.initData();
        bundle = getIntent().getExtras();
        if (bundle != null) {
            order_number = bundle.getString(Constant.ORDER_NUM, "");
            order_status = bundle.getInt(Constant.STATUS, 0);
        } else {
            loadinglayout.showEmpty();
        }
        context = this;
        user_id = SharedPreferencesUtils.getInstance(context).getInt(Constant.USER_ID, 0);
        mineOrderFormPresenter = new MineOrderFormPresenter(this);

        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mineOrderFormPresenter.getOrderFormDetail(user_id, order_status, order_number);
            }
        });

        fragmentManager = getSupportFragmentManager();
    }

    @OnClick({R.id.order_service, R.id.order_cancel, R.id.order_invoice, R.id.order_pay, R.id.order_edit})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.order_service://打电话
                CallUtils.makeCallWithPermission(this);
                break;
            case R.id.order_cancel://取消
                mineOrderFormPresenter.cancelOrderForm(user_id, order_number);
                break;
            case R.id.order_invoice://发票
                //TODO 发票
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                InvoiceActiveDialog invoiceActiveDialog = new InvoiceActiveDialog();
                invoiceActiveDialog.show(fragmentTransaction, "invoice");
                invoiceActiveDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                    }
                });
                break;
            case R.id.order_pay://去支付
                bundle.putString(Constant.ORDER_NUM, order_number);
                bundle.putFloat(Constant.COURSE_PRICE, Float.parseFloat(payPrice));
                startActivity(PayActivity.class, bundle);
                /*FragmentTransaction fragmentTransaction2 = fragmentManager.beginTransaction();
                fragmentTransaction2.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                PayDialogFragment payDialogFragment = new PayDialogFragment();
                payDialogFragment.show(fragmentTransaction2, "pay");
                payDialogFragment.setOnPayClickListener(new PayDialogFragment.PayClickListener() {
                    @Override
                    public void aliPay() {
                    }

                    @Override
                    public void wechatPay() {
                    }
                });*/
                break;
            case R.id.order_edit://编辑地址
                bundle.putInt(Constant.TYPE, 0);
                bundle.putInt(Constant.ADDRESS_ID, address_id);//TODO 地址ID
                startActivity(EditAddressActivity.class, bundle);
                break;
            default:
                break;
        }
    }

    @Override
    public void getMineOrderFromList(MineOrderListBean data) {
        //TODO nothing
    }

    @Override
    public void cancelOrderForm(StateStatusBean data) {
        if (data != null) {
            if (data.getData() != null) {
                int state = data.getData().getState();
                if (state == 1) {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Success));
                    finish();
                } else {
                    ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
                }
            } else {
                ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
            }
        } else {
            ToastUtil.showBottomShortText(context, getResources().getString(R.string.operation_Error));
        }
    }

    @Override
    public void getOrderFormDetail(MineOrderFormDetailBean result) {
        if (result != null) {
            if (result.getData() != null) {
                initOrderFormDetail(result.getData());
                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    //TODO 订单详情
    private void initOrderFormDetail(MineOrderFormDetailBean.DataBean data) {
        MineOrderFormDetailBean.DataBean.CourseBean courseBean = data.getCourse();
        if (data.getAddress().getAddress_id() != 0) {
            MineOrderFormDetailBean.DataBean.AddressBean address = data.getAddress();
            if (linearAddress != null) {
                linearAddress.setVisibility(View.VISIBLE);
            }
            String address1 = address.getAddress();
            String consignee = address.getConsignee();
            String telephone = address.getTelephone();
            address_id = address.getAddress_id();
            if (!TextUtils.isEmpty(address1)) {
                addressContent.setText(address1);
            }
            if (!TextUtils.isEmpty(consignee)) {
                addressName.setText(consignee);
            }
            if (!TextUtils.isEmpty(telephone)) {
                addressPhone.setText(telephone);
            }
        } else {
            if (linearAddress != null) {
                linearAddress.setVisibility(View.GONE);
            }
        }

        String addTime = courseBean.getAdd_time();//下单时间
        String appImg = courseBean.getApp_img();//图片
        String couponPrice = courseBean.getCoupon_price();//优惠价格
        String price = courseBean.getPrice();//价格
        //实际价格
        payPrice = courseBean.getPay_price();
        String orderNum = courseBean.getOrder_num();//编号
        String teacherName = courseBean.getTeacher_name();//老师名字
        String packageName = courseBean.getPackage_name();//报名
        String studyDays = courseBean.getStudy_days();//购买时效
        String joinNum = courseBean.getJoin_num();
        int payStatus = courseBean.getPay_status();
        if (!TextUtils.isEmpty(packageName)) {
            itemCourseTitle.setText(packageName);
        }
        if (!TextUtils.isEmpty(teacherName)) {
            itemCourseTeacher.setText(teacherName);
        }
        if (!TextUtils.isEmpty(orderNum)) {
            orderNumber.setText(String.valueOf(getResources().getString(R.string.orderForm_number) + " " + orderNum));
        }
        if (!TextUtils.isEmpty(addTime)) {
            orderTime.setText(String.valueOf(getResources().getString(R.string.orderForm_time) + " " + addTime));
        }
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.mipmap.icon_default)
                .error(R.mipmap.image_loaderror)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
        GlideUtils.load(context, appImg, itemCourseImage, requestOptions);
        orderCoursePrice.setText(String.valueOf(getResources().getString(R.string.RMB) + price));
        orderPreferentialPrice.setText(String.valueOf(getResources().getString(R.string.RMB) + couponPrice));
        orderRealPrice.setText(String.valueOf(getResources().getString(R.string.RMB) + payPrice));
        //课程有效期
        itemCourseTime.setText(String.valueOf(getResources().getString(R.string.orderForm_endtime2, studyDays)));
        //学习人数
        itemCourseEffective.setText(String.valueOf(context.getResources().getString(R.string.course_NumOfLearning, String.valueOf(joinNum))));
        /*----------------------------------------------------------------再丑也要看的分割线----------------------------------------------------------------*/
        //TODO 订单状态
        switch (payStatus) {
            case 1:
                //已付款
                orderInvoice.setVisibility(View.GONE);
                orderSuccess.setVisibility(View.VISIBLE);
                orderPay.setVisibility(View.GONE);
                orderCancel.setVisibility(View.GONE);

                orderEdit.setVisibility(View.GONE);
                holderView.setVisibility(View.GONE);
                break;
            case 2:
                //未付款
                orderInvoice.setVisibility(View.GONE);
                orderSuccess.setVisibility(View.GONE);
                orderPay.setVisibility(View.VISIBLE);
                orderCancel.setVisibility(View.VISIBLE);

                orderEdit.setVisibility(View.VISIBLE);
                holderView.setVisibility(View.VISIBLE);
                break;
            case 3:
                //订单已取消
            default:
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
