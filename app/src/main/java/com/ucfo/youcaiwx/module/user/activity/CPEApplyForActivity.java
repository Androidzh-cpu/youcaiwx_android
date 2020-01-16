package com.ucfo.youcaiwx.module.user.activity;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.flyco.roundview.RoundTextView;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.request.base.Request;
import com.qw.soul.permission.SoulPermission;
import com.qw.soul.permission.bean.Permission;
import com.qw.soul.permission.callbcak.CheckRequestPermissionListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.user.MineCPEApplyInfoAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.user.CPEApplyInfoBean;
import com.ucfo.youcaiwx.presenter.presenterImpl.user.CPEApplyForPresenter;
import com.ucfo.youcaiwx.presenter.view.user.ICPEApplyForView;
import com.ucfo.youcaiwx.utils.sharedutils.SharedPreferencesUtils;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;
import com.ucfo.youcaiwx.widget.dialog.ImagePreviewDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-12-31 下午 2:50
 * Package: com.ucfo.youcaiwx.module.user.activity
 * FileName: CPEApplyForActivity
 * ORG: www.youcaiwx.com
 * Description:CPE申请玩意
 */
public class CPEApplyForActivity extends BaseActivity implements ICPEApplyForView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.image_icon)
    ImageView imageIcon;
    @BindView(R.id.text_addProveInfo)
    TextView txtAddProveInfo;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_vipNumber)
    TextView txtVipNumber;
    @BindView(R.id.txt_idNumber)
    TextView txtIdNumber;
    @BindView(R.id.btn_proveInfo)
    LinearLayout btnProveInfo;
    @BindView(R.id.expandablelistview)
    ExpandableListView listView;
    @BindView(R.id.txt_point)
    TextView txtPoint;
    @BindView(R.id.btn_next)
    RoundTextView btnNext;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.linear_name_vip)
    LinearLayout linearNameVip;
    @BindView(R.id.linear_idnum)
    LinearLayout linearIdnum;
    private int user_id;
    private CPEApplyForPresenter cpeApplyForPresenter;

    private List<CPEApplyInfoBean.DataBean> list;
    private List<String> checkList;
    private MineCPEApplyInfoAdapter adapter;

    @Override
    protected int setContentView() {
        return R.layout.activity_apply_for;
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
        titlebarMidtitle.setText(getResources().getString(R.string.CPEApplyFor));
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
    }

    @Override
    protected void initData() {
        super.initData();
        list = new ArrayList<>();
        checkList = new ArrayList<>();


        SharedPreferencesUtils sharedPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        user_id = sharedPreferencesUtils.getInt(Constant.USER_ID, 0);

        cpeApplyForPresenter = new CPEApplyForPresenter(this);

        /**
         * 获取学分课程
         */
        cpeApplyForPresenter.initMineCPEList(user_id);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_PROVEINFO:
                //TODO 选取信息
                if (resultCode == Constant.REQUEST_PROVEINFO) {
                    if (data.getExtras() != null) {
                        Bundle bundle = data.getExtras();
                        String editName = bundle.getString(Constant.EDUCATION_EDIT_NAME);
                        String editIDNum = bundle.getString(Constant.EDUCATION_EDIT_IDNUM);
                        String editVIP = bundle.getString(Constant.EDUCATION_EDIT_VIP);

                        txtName.setText(editName);
                        txtIdNumber.setText(editIDNum);
                        txtVipNumber.setText(editVIP);

                        //添加地址隐藏
                        setBtnProveInfo(false);
                    }
                }
                break;
            default:
                break;
        }
    }

    /**
     * 添加地址按钮处理
     */
    private void setBtnProveInfo(boolean flag) {
        if (flag) {
            //添加地址可见
            imageIcon.setVisibility(View.VISIBLE);
            txtAddProveInfo.setVisibility(View.VISIBLE);

            linearIdnum.setVisibility(View.GONE);
            linearNameVip.setVisibility(View.GONE);
        } else {
            //添加地址不可见

            imageIcon.setVisibility(View.GONE);
            txtAddProveInfo.setVisibility(View.GONE);

            linearIdnum.setVisibility(View.VISIBLE);
            linearNameVip.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initMineCPEList(CPEApplyInfoBean bean) {
        if (bean != null) {
            if (bean.getData() != null && bean.getData().size() > 0) {
                List<CPEApplyInfoBean.DataBean> data = bean.getData();
                list.clear();
                list.addAll(data);

                initAdapter();

                loadinglayout.showContent();
            } else {
                loadinglayout.showEmpty();
            }
        } else {
            loadinglayout.showEmpty();
        }
    }

    @Override
    public void createReport(int code, String message, String imageUrl) {
        if (code == 200) {
            if (!TextUtils.isEmpty(imageUrl)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //TODO 6.0以上
                    SoulPermission.getInstance()
                            .checkAndRequestPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new CheckRequestPermissionListener() {
                                @Override
                                public void onPermissionOk(Permission permission) {
                                    previewImage(imageUrl);
                                }

                                @Override
                                public void onPermissionDenied(Permission permission) {
                                }
                            });
                } else {
                    //TODO 6.0以下
                    previewImage(imageUrl);
                }
            } else {
                showToast(getResources().getString(R.string.cpe_tips_notfoundurl));
            }
        } else {
            showToast(message);
        }
    }

    private void previewImage(String imageUrl) {
        new ImagePreviewDialog(this)
                .builder()
                .setImageUrl(imageUrl)
                .setOnSaveClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //自定义路径
                        String storePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + "report";
                        //相机目录
                        //String storePath = Environment.getExternalStorageDirectory() + File.separator + Environment.DIRECTORY_DCIM + File.separator + "Camera" + File.separator;
                        File file = new File(storePath);
                        String finalLocalPath = file.getAbsolutePath();
                        String mDestFileName = System.currentTimeMillis() + ".png";
                        OkGo.<File>get(imageUrl).tag(this).execute(new FileCallback(finalLocalPath, mDestFileName) { //文件下载时指定下载的路径以及下载的文件的名

                            @Override
                            public void onStart(Request<File, ? extends Request> request) {
                                super.onStart(request);
                                showToast(getResources().getString(R.string.cpe_tips_saving));
                            }

                            @Override
                            public void onSuccess(com.lzy.okgo.model.Response<File> response) {
                                if (response.isSuccessful()) {
                                    File file1 = new File(finalLocalPath, mDestFileName);
                                    if (file1.exists()) {
                                        //发送广播通知系统图库刷新数据
                                        Uri uri = Uri.fromFile(file1);
                                        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
                                        showToast(getResources().getString(R.string.cpe_tips_havesaved));
                                    }
                                }
                            }

                            @Override
                            public void onError(com.lzy.okgo.model.Response<File> response) {
                                super.onError(response);
                                showToast(getResources().getString(R.string.cpe_tips_havesaved_error));
                            }
                        });
                    }
                })
                .show();
    }

    /**
     * 啥
     */
    private void initAdapter() {
        if (adapter == null) {
            adapter = new MineCPEApplyInfoAdapter(list, this);

            listView.setAdapter(adapter);
            for (int i = 0; i < adapter.getGroupCount(); i++) {
                listView.expandGroup(i);
            }
        } else {
            adapter.notifychange(list);
        }

        //字列表监听
        listView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                if (list != null && list.size() > 0) {
                    if (list.get(groupPosition).getList() != null && list.get(groupPosition).getList().size() > 0) {
                        List<CPEApplyInfoBean.DataBean.ListBean> beanList = list.get(groupPosition).getList();
                        boolean checked = beanList.get(childPosition).getChecked();
                        beanList.get(childPosition).setChecked(!checked);

                        adapter.notifychange(list);
                    }
                }
                calculateIntegral();
                return false;
            }
        });
        //父列表
        listView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                boolean checked = list.get(groupPosition).getChecked();
                list.get(groupPosition).setChecked(!checked);
                if (list.get(groupPosition).getList() != null && list.get(groupPosition).getList().size() > 0) {
                    List<CPEApplyInfoBean.DataBean.ListBean> beanList = list.get(groupPosition).getList();
                    int size = beanList.size();
                    for (int i = 0; i < size; i++) {
                        beanList.get(i).setChecked(!checked);
                    }
                }
                adapter.notifychange(list);

                calculateIntegral();
                return true;
            }
        });
    }

    /**
     * 计算学分
     */
    private void calculateIntegral() {
        int resultCountIntegral = 0;
        if (list != null && list.size() > 0) {
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (list.get(i).getList() != null && list.get(i).getList().size() > 0) {
                    List<CPEApplyInfoBean.DataBean.ListBean> beanList = list.get(i).getList();
                    int size1 = beanList.size();
                    for (int j = 0; j < size1; j++) {
                        CPEApplyInfoBean.DataBean.ListBean bean = beanList.get(j);
                        String integral = bean.getCpe_integral();
                        boolean checked = bean.getChecked();
                        if (checked) {
                            if (!TextUtils.isEmpty(integral)) {
                                int parseInt = Integer.parseInt(integral);
                                resultCountIntegral += parseInt;
                            }
                        }
                    }
                }
            }
        }

        txtPoint.setText(String.valueOf(resultCountIntegral));
    }

    @Override
    public void showLoading() {

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

    @OnClick({R.id.btn_proveInfo, R.id.btn_next})
    public void onViewClicked(View view) {
        Bundle bundle = new Bundle();
        switch (view.getId()) {
            case R.id.btn_proveInfo:
                //TODO 添加证明信息
                Intent intent = new Intent(this, CPEProveInformationActivity.class);
                //TODO 添加地址
                bundle.putBoolean(Constant.EDUCATION_EDIT, true);
                bundle.putString(Constant.EDUCATION_EDIT_NAME, txtName.getText().toString().trim());
                bundle.putString(Constant.EDUCATION_EDIT_IDNUM, txtIdNumber.getText().toString().trim());
                bundle.putString(Constant.EDUCATION_EDIT_VIP, txtVipNumber.getText().toString().trim());
                intent.putExtras(bundle);
                startActivityForResult(intent, Constant.REQUEST_PROVEINFO);
                break;
            case R.id.btn_next:
                //TODO 下一步
                String userName = txtName.getText().toString().trim();
                String idNumber = txtIdNumber.getText().toString().trim();
                String vipNumber = txtVipNumber.getText().toString().trim();
                //信息验证
                if (TextUtils.isEmpty(userName) || TextUtils.isEmpty(idNumber) || TextUtils.isEmpty(vipNumber)) {
                    showToast(getResources().getString(R.string.cpe_tips_proveinfo));
                    return;
                }
                if (checkList == null) {
                    checkList = new ArrayList<>();
                }
                checkList.clear();
                if (list != null && list.size() > 0) {
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (list.get(i).getList() != null && list.get(i).getList().size() > 0) {
                            List<CPEApplyInfoBean.DataBean.ListBean> beanList = list.get(i).getList();
                            int size1 = beanList.size();
                            for (int j = 0; j < size1; j++) {
                                CPEApplyInfoBean.DataBean.ListBean bean = beanList.get(j);
                                String id = bean.getId();
                                boolean checked = bean.getChecked();
                                if (checked) {
                                    if (!TextUtils.isEmpty(id)) {
                                        checkList.add(id);
                                    }
                                }
                            }
                        }
                    }
                }
                //选中课程验证
                if (checkList.size() == 0) {
                    showToast(getResources().getString(R.string.cpe_tips_prove_course));
                    return;
                }
                //获取选中课程的id
                String string = checkList.toString().trim();
                String substring = string.substring(1, string.length() - 1);
                String replace = substring.replace(" ", "");
                //请求网络,加载证明
                setProcessLoading();
                btnNext.setEnabled(false);
                cpeApplyForPresenter.createReport(user_id, replace, userName, vipNumber, idNumber, 2);
                break;
            default:
                //TODO nothing
                break;
        }
    }
}
