package com.ucfo.youcaiwx.module.home.cpe;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.ucfo.youcaiwx.R;
import com.ucfo.youcaiwx.adapter.home.CPEClassifyListAdapter;
import com.ucfo.youcaiwx.adapter.home.CPECourseListAdapter;
import com.ucfo.youcaiwx.adapter.home.CPEScreenListAdapter;
import com.ucfo.youcaiwx.adapter.home.CPESortListAdapter;
import com.ucfo.youcaiwx.base.BaseActivity;
import com.ucfo.youcaiwx.common.Constant;
import com.ucfo.youcaiwx.entity.home.education.EducationCourseListBean;
import com.ucfo.youcaiwx.entity.home.education.EducationTypeBean;
import com.ucfo.youcaiwx.module.course.player.VideoPlayPageActivity;
import com.ucfo.youcaiwx.presenter.presenterImpl.home.education.EducationPsenter;
import com.ucfo.youcaiwx.presenter.view.home.education.IEducationView;
import com.ucfo.youcaiwx.utils.baseadapter.ItemClickHelper;
import com.ucfo.youcaiwx.utils.systemutils.DensityUtil;
import com.ucfo.youcaiwx.widget.customview.LoadingLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: AND
 * Time: 2019-12-23 下午 4:09
 * Package: com.ucfo.youcaiwx.module.home.cpe
 * FileName: CPECourseActivity
 * ORG: www.youcaiwx.com
 * Description:TODO CPE课程首页
 */
public class CPECourseActivity extends BaseActivity implements IEducationView {
    @BindView(R.id.titlebar_midtitle)
    TextView titlebarMidtitle;
    @BindView(R.id.titlebar_righttitle)
    TextView titlebarRighttitle;
    @BindView(R.id.titlebar_toolbar)
    Toolbar titlebarToolbar;
    @BindView(R.id.showline)
    View showline;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.loadinglayout)
    LoadingLayout loadinglayout;
    @BindView(R.id.txt_classify)
    TextView txtClassify;
    @BindView(R.id.image_classify)
    ImageView imageClassify;
    @BindView(R.id.btn_classify)
    LinearLayout btnClassify;
    @BindView(R.id.txt_sort)
    TextView txtSort;
    @BindView(R.id.image_sort)
    ImageView imageSort;
    @BindView(R.id.btn_sort)
    LinearLayout btnSort;
    @BindView(R.id.txt_screen)
    TextView txtScreen;
    @BindView(R.id.image_screen)
    ImageView imageScreen;
    @BindView(R.id.btn_screen)
    LinearLayout btnScreen;
    @BindView(R.id.refreshlayout)
    SmartRefreshLayout refreshlayout;


    //筛选
    List<EducationTypeBean.DataBean.ScreeningBean> screenList;
    //排序
    List<EducationTypeBean.DataBean.SortBean> sortList;
    //分类
    List<EducationTypeBean.DataBean.TypeBean> typeList;

    private EducationPsenter educationPsenter;

    private List<EducationCourseListBean.DataBeanX.DataBean> list;
    private PopupWindow screenDialog;
    private PopupWindow sortDialog;
    private PopupWindow classifyDialog;

    private CPEScreenListAdapter cpeScreenListAdapter;
    private CPESortListAdapter cpeSortListAdapter;
    private CPEClassifyListAdapter cpeClassifyListAdapter;

    private CPECourseListAdapter cpeCourseListAdapter;

    private int pageIndex = 1;
    private String typeId;
    private String sortId;
    private String screenId;
    private boolean loadFlag = false;

    @Override
    protected void onPause() {
        super.onPause();
        dismiss();
    }

    @Override
    protected int setContentView() {
        return R.layout.activity_cpecourse;
    }

    @Override
    protected void initToolbar() {
        super.initToolbar();
        super.initToolbar();
        setSupportActionBar(titlebarToolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setDisplayShowTitleEnabled(false);
        }
        titlebarMidtitle.setText(getResources().getString(R.string.fue_CPE));
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

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setItemAnimator(new DefaultItemAnimator());
        recyclerview.setNestedScrollingEnabled(false);
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }

    @Override
    protected void initData() {
        super.initData();
        screenList = new ArrayList<>();
        typeList = new ArrayList<>();
        sortList = new ArrayList<>();
        list = new ArrayList<>();
        //注册网络
        educationPsenter = new EducationPsenter(this);

        /**
         * 获取筛选列表
         */
        educationPsenter.initClassification();
        loadinglayout.setRetryListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                educationPsenter.initClassification();
            }
        });

        refreshlayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                loadFlag = false;
                pageIndex = 1;
                loadCourseListData(typeId, sortId, screenId);
            }
        });
        refreshlayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadFlag = false;
                pageIndex += 1;
                loadCourseListData(typeId, sortId, screenId);
            }
        });
    }

    /**
     * 获取响应的课程列表
     *
     * @param type
     * @param sort
     * @param screen
     */
    private void loadCourseListData(String type, String sort, String screen) {
        if (educationPsenter != null) {
            educationPsenter.initCourseListData(type, sort, screen, 10, pageIndex);
        }
    }

    /**
     * 创建popupwindow的视图
     */
    private PopupWindow createScreenListDialog() {
        View view = LayoutInflater.from(this).inflate(R.layout.popuwindow_cpe_listview, null);
        PopupWindow popupWindow = new PopupWindow(view);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);
        popupWindow.setAnimationStyle(R.style.Widget_AppCompat_PopupWindow);
        view.findViewById(R.id.view_holder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return popupWindow;
    }

    /**
     * 干掉所有的弹框
     */
    private void dismiss() {
        if (screenDialog != null && screenDialog.isShowing()) {
            screenDialog.dismiss();
        }
        if (sortDialog != null && sortDialog.isShowing()) {
            sortDialog.dismiss();
        }
        if (classifyDialog != null && classifyDialog.isShowing()) {
            classifyDialog.dismiss();
        }
    }

    @OnClick({R.id.btn_classify, R.id.btn_sort, R.id.btn_screen})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_classify:
                //分类标签
                initClassifyDialog();
                break;
            case R.id.btn_sort:
                //排序
                initSortDialog();
                break;
            case R.id.btn_screen:
                //筛选
                initScreenDialog();
                break;
            default:
                break;
        }
    }

    /**
     * 分类列表
     */
    private void initClassifyDialog() {
        updateBtnClassify(true, typeList);
        if (classifyDialog == null) {
            classifyDialog = createScreenListDialog();
            View view = classifyDialog.getContentView();
            ListView listView = (ListView) view.findViewById(R.id.listView);
            if (cpeClassifyListAdapter == null) {
                cpeClassifyListAdapter = new CPEClassifyListAdapter(typeList, this);
                listView.setAdapter(cpeClassifyListAdapter);
            } else {
                cpeClassifyListAdapter.notifyChange(typeList);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //设置列表选中项
                    for (int i = 0; i < typeList.size(); i++) {
                        EducationTypeBean.DataBean.TypeBean sortBean = typeList.get(i);
                        if (position == i) {
                            sortBean.setChecked(true);
                        } else {
                            sortBean.setChecked(false);
                        }
                    }
                    //刷新列表
                    cpeClassifyListAdapter.notifyChange(typeList);

                    EducationTypeBean.DataBean.TypeBean typeBean = typeList.get(position);
                    //设置下拉列表标题为选中项
                    txtClassify.setText(typeBean.getType_name());
                    txtClassify.setTextColor(ContextCompat.getColor(CPECourseActivity.this, R.color.color_0267FF));
                    //更新选中条件,刷新数据
                    typeId = typeBean.getType_id();
                    loadCourseListData(typeId, sortId, screenId);
                    loadFlag = true;
                    //干掉弹框
                    dismiss();
                }
            });
        }
        int[] a = new int[2];
        btnSort.getLocationInWindow(a);
        classifyDialog.showAsDropDown(btnSort, 0, DensityUtil.dp2px(1));
        classifyDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                updateBtnClassify(false, typeList);
            }
        });

    }

    /**
     * 排序列表
     */
    private void initSortDialog() {
        updateBtnSort(true, sortList);
        if (sortDialog == null) {
            sortDialog = createScreenListDialog();
            View view = sortDialog.getContentView();
            ListView listView = (ListView) view.findViewById(R.id.listView);
            if (cpeSortListAdapter == null) {
                cpeSortListAdapter = new CPESortListAdapter(sortList, this);
                listView.setAdapter(cpeSortListAdapter);
            } else {
                cpeSortListAdapter.notifyChange(sortList);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //设置列表选中项
                    for (int i = 0; i < sortList.size(); i++) {
                        EducationTypeBean.DataBean.SortBean sortBean = sortList.get(i);
                        if (position == i) {
                            sortBean.setChecked(true);
                        } else {
                            sortBean.setChecked(false);
                        }
                    }
                    //刷新列表
                    cpeSortListAdapter.notifyChange(sortList);

                    EducationTypeBean.DataBean.SortBean sortBean = sortList.get(position);
                    //设置下拉列表标题为选中项
                    txtSort.setText(sortBean.getType_name());
                    txtSort.setTextColor(ContextCompat.getColor(CPECourseActivity.this, R.color.color_0267FF));
                    //更新选中条件,刷新数据
                    sortId = sortBean.getType_id();
                    loadCourseListData(typeId, sortId, screenId);
                    loadFlag = true;
                    //干掉弹框
                    dismiss();
                }
            });
        }
        sortDialog.showAsDropDown(btnSort, 0, DensityUtil.dp2px(1));
        sortDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                updateBtnSort(false, sortList);
            }
        });
    }

    /**
     * 筛选列表
     */
    private void initScreenDialog() {
        updateBtnScreen(true, screenList);
        if (screenDialog == null) {
            screenDialog = createScreenListDialog();
            View view = screenDialog.getContentView();
            ListView listView = (ListView) view.findViewById(R.id.listView);
            if (cpeScreenListAdapter == null) {
                cpeScreenListAdapter = new CPEScreenListAdapter(screenList, this);
                listView.setAdapter(cpeScreenListAdapter);
            } else {
                cpeScreenListAdapter.notifyChange(screenList);
            }
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //设置列表选中项
                    for (int i = 0; i < screenList.size(); i++) {
                        EducationTypeBean.DataBean.ScreeningBean screeningBean = screenList.get(i);
                        if (position == i) {
                            screeningBean.setChecked(true);
                        } else {
                            screeningBean.setChecked(false);
                        }
                    }
                    //刷新列表
                    cpeScreenListAdapter.notifyChange(screenList);

                    EducationTypeBean.DataBean.ScreeningBean bean = screenList.get(position);
                    //设置下拉列表标题为选中项
                    txtScreen.setText(bean.getType_name());
                    txtScreen.setTextColor(ContextCompat.getColor(CPECourseActivity.this, R.color.color_0267FF));

                    //更新选中条件,刷新数据
                    screenId = bean.getType_id();
                    loadCourseListData(typeId, sortId, screenId);
                    loadFlag = true;
                    //干掉弹框
                    dismiss();
                }
            });
        }
        screenDialog.showAsDropDown(btnScreen, 0, DensityUtil.dp2px(1));
        screenDialog.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                updateBtnScreen(false, screenList);
            }
        });
    }

    //更新筛选下拉按钮的状态
    private void updateBtnScreen(boolean type, List<EducationTypeBean.DataBean.ScreeningBean> list) {
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean checked = list.get(i).getChecked();
                if (checked) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            //TODO 已有选中状态
            if (type) {
                //打开
                imageScreen.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_selected_icon));
            } else {
                //关闭
                imageScreen.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_selected_icon));
            }
        } else {
            //TODO 未选中
            if (type) {
                //打开
                imageScreen.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_unselected_icon));
            } else {
                //关闭
                imageScreen.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_unselected_icon));
            }
        }

    }

    //更新排序下拉按钮的状态
    private void updateBtnSort(boolean type, List<EducationTypeBean.DataBean.SortBean> list) {
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean checked = list.get(i).getChecked();
                if (checked) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            //TODO 已有选中状态
            if (type) {
                //打开
                imageSort.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_selected_icon));
            } else {
                //关闭
                imageSort.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_selected_icon));
            }
        } else {
            //TODO 未选中
            if (type) {
                //打开
                imageSort.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_unselected_icon));
            } else {
                //关闭
                imageSort.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_unselected_icon));
            }
        }

    }

    //更新分类列表
    private void updateBtnClassify(boolean type, List<EducationTypeBean.DataBean.TypeBean> list) {
        boolean flag = false;
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                boolean checked = list.get(i).getChecked();
                if (checked) {
                    flag = true;
                    break;
                }
            }
        }
        if (flag) {
            //TODO 已有选中状态
            if (type) {
                //打开
                imageClassify.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_selected_icon));
            } else {
                //关闭
                imageClassify.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_selected_icon));
            }
        } else {
            //TODO 未选中
            if (type) {
                //打开
                imageClassify.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_up_unselected_icon));
            } else {
                //关闭
                imageClassify.setImageDrawable(ContextCompat.getDrawable(CPECourseActivity.this, R.mipmap.drop_down_unselected_icon));
            }
        }

    }


    /**
     * 根据条件获取课程的列表
     */
    @Override
    public void initCourseList(EducationCourseListBean bean) {
        if (bean != null) {
            EducationCourseListBean.DataBeanX data = bean.getData();
            if (data.getData() != null && data.getData().size() > 0) {
                List<EducationCourseListBean.DataBeanX.DataBean> beans = data.getData();
                if (pageIndex == 1) {//初次加载或刷新操作
                    list.clear();
                    list.addAll(beans);
                } else {
                    if (beans.size() > 0) {
                        list.addAll(beans);
                    }
                }
                initAdapter();
                if (loadinglayout != null) {
                    loadinglayout.showContent();
                }
            } else {
                if (pageIndex == 1) {
                    if (list != null && list.size() > 0) {
                        if (cpeCourseListAdapter != null) {
                            cpeCourseListAdapter.notifyChange(list);
                        }
                        if (loadinglayout != null) {
                            loadinglayout.showContent();
                        }
                    } else {
                        loadinglayout.showEmpty();
                    }
                } else {//架子啊更多
                    if (list != null && list.size() > 0) {
                        if (cpeCourseListAdapter != null) {
                            cpeCourseListAdapter.notifyChange(list);
                        }
                        refreshlayout.finishRefreshWithNoMoreData();
                        if (loadinglayout != null) {
                            loadinglayout.showContent();
                        }
                    } else {
                        if (loadinglayout != null) {
                            loadinglayout.showEmpty();
                        }
                    }
                }
            }
        } else {
            loadinglayout.showEmpty();
        }

        refreshlayout.finishRefresh();
        refreshlayout.finishLoadMore();
    }

    /**
     * 初始化适配器
     */
    private void initAdapter() {
/*
        for (int i = 0; i < 20; i++) {
            EducationCourseListBean.DataBeanX.DataBean dataBean = new EducationCourseListBean.DataBeanX.DataBean();
            dataBean.setApp_img("https://youcai2020.oss-cn-beijing.aliyuncs.com/style/images/20190812/91ae0fb070e461ab565864f688be94ff.jpeg");
            dataBean.setCpe_integral("1000");
            dataBean.setJoin_num("80");
            dataBean.setName("CMA测试课程");
            dataBean.setPrice("18000");
            dataBean.setTeacher_name("老王");
            list.add(dataBean);
        }
*/

        if (cpeScreenListAdapter == null) {
            cpeCourseListAdapter = new CPECourseListAdapter(this, list);
            recyclerview.setAdapter(cpeCourseListAdapter);
        } else {
            cpeCourseListAdapter.notifyChange(list);
        }
        cpeCourseListAdapter.setOnItemClick(new ItemClickHelper.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Bundle bundle = new Bundle();
                EducationCourseListBean.DataBeanX.DataBean bean = list.get(position);
                String courseIamge = bean.getApp_img();//TODO 课程封面
                String coursePackageId = bean.getPackage_id();//TODO  课程包ID(课程包内含多们课程)
                if (TextUtils.isEmpty(coursePackageId)) {
                    showToast(getResources().getString(R.string.miss_params));
                    return;
                }
                bundle.putString(Constant.COURSE_COVER_IMAGE, courseIamge);//封面
                bundle.putString(Constant.COURSE_SOURCE, Constant.WATCH_EDUCATION_CPE);//播放源
                bundle.putInt(Constant.COURSE_PACKAGE_ID, Integer.parseInt(coursePackageId));//课程包ID
                startActivity(VideoPlayPageActivity.class, bundle);
            }
        });
    }

    /**
     * 获取分类信息
     */
    @Override
    public void initClassification(EducationTypeBean bean) {
        if (bean != null) {
            EducationTypeBean.DataBean data = bean.getData();
            if (data != null) {
                screenList.clear();
                typeList.clear();
                sortList.clear();
                //筛选
                List<EducationTypeBean.DataBean.ScreeningBean> screening = data.getScreening();
                //排序
                List<EducationTypeBean.DataBean.SortBean> sort = data.getSort();
                //分类
                List<EducationTypeBean.DataBean.TypeBean> type = data.getType();
                typeList.addAll(type);
                sortList.addAll(sort);
                screenList.addAll(screening);

                if (typeList != null && typeList.size() > 0) {
                    EducationTypeBean.DataBean.TypeBean typeBean = typeList.get(0);
                    String typeName = typeBean.getType_name();

                    typeId = typeBean.getType_id();
                    //txtClassify.setText(typeName);
                }
                if (sortList != null && sortList.size() > 0) {
                    EducationTypeBean.DataBean.SortBean sortBean = sortList.get(0);
                    String typeName = sortBean.getType_name();

                    sortId = sortBean.getType_id();
                    //txtSort.setText(typeName);
                }
                if (screenList != null && screenList.size() > 0) {
                    EducationTypeBean.DataBean.ScreeningBean screeningBean = screenList.get(0);
                    String typeName = screeningBean.getType_name();

                    screenId = screenList.get(0).getType_id();
                    //txtScreen.setText(typeName);
                }
                loadFlag = true;
                loadCourseListData(typeId, sortId, screenId);
            }
        }
    }

    @Override
    public void showLoading() {
        if (loadFlag) {
            loadinglayout.showLoading();
        }
    }

    @Override
    public void showLoadingFinish() {

    }

    @Override
    public void showError() {
        loadinglayout.showError();
    }
}
