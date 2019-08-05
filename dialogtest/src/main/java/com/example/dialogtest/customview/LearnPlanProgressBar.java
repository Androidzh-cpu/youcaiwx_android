package com.example.dialogtest.customview;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dialogtest.R;

/**
 * Author: AND
 * Time: 2019-7-18.  下午 6:01
 * Package: com.example.dialogtest.customview
 * FileName: LearnPlanProgressBar
 * Description:TODO 学习计划进度条
 */
public class LearnPlanProgressBar extends LinearLayout {

    //设置的进度
    private int progress = 0;
    private String creditText;
    //显示文字的方框
    private LinearLayout llStarBarCredit;
    private TextView tvStarBarCredit;
    //进度条
    private ProgressBar pbStarBar;
    private LinearLayout llStarBarText;
    //指向进度条进度的小三角形
    private ImageView ivStarBarTriangle;
    //进度条最大值
    private int maxProgress = 100;
    //进度条长度
    private int starBarWidth;
    //屏幕宽度
    private int screenWidth;
    //进度条离两边的距离
    private int marginWidth;
    //设置文字方框的宽度
    private int llStarBarCreditWidth = 80;
    //偏移量
    private float offset;

    public LearnPlanProgressBar(Context context) {
        this(context, null);
    }

    public LearnPlanProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LearnPlanProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    //设置ProgressBar的progress
    public void setProgress(int progress) {
        pbStarBar.setProgress(progress);
        //获取progress在进度条的相应长度
        offset = progress * 1f / maxProgress * starBarWidth;

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        LayoutParams triangleParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        //判断当进度条滑到最右边的时候，上面显示文字的方框会移动到超过屏幕的地方，这里要分两种情况考虑
        if (screenWidth - (marginWidth + (int) offset - (int) dp2px(10)) > (int) dp2px(llStarBarCreditWidth)) {
            layoutParams.setMargins(marginWidth + (int) offset - (int) dp2px(10), 0, 0, 0);
        } else {
            layoutParams.setMargins(screenWidth - (int) dp2px(llStarBarCreditWidth), 0, 0, 0);
        }
        //设置显示文字方框的宽度，这里最好设置方框的宽度，因为调用这方法的时候onMeasure还没完成测量
        layoutParams.width = (int) dp2px(llStarBarCreditWidth);
        layoutParams.height = (int) dp2px(20);
        llStarBarCredit.setLayoutParams(layoutParams);
        triangleParams.setMargins(marginWidth + (int) offset - (int) dp2px(5), (int) dp2px(-3), 0, 0);
        ivStarBarTriangle.setLayoutParams(triangleParams);

    }

    //设置TextView里面的文字
    public void setCreditText(String creditText) {
        this.creditText = creditText;
        tvStarBarCredit.setText(creditText);
    }

    //初始化
    private void init(Context context) {
        View contentView = LayoutInflater.from(context).inflate(R.layout.asprogressbar, null);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        llStarBarCredit = (LinearLayout) contentView.findViewById(R.id.layout_xinyong);
        tvStarBarCredit = (TextView) contentView.findViewById(R.id.tv_startInfo_xinYong);
        pbStarBar = (ProgressBar) contentView.findViewById(R.id.sb_seetBar);
        llStarBarText = (LinearLayout) contentView.findViewById(R.id.ll_starbar_text);
        ivStarBarTriangle = (ImageView) contentView.findViewById(R.id.iv_star_triangle);

        setStarBarWidth(pbStarBar);
        setStarText();

        contentView.setLayoutParams(params);
        addView(contentView);
    }

    private void setStarBarWidth(View view) {
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        //获取屏幕宽度
        screenWidth = wm.getDefaultDisplay().getWidth();
        //设置进度条离屏幕两边的距离
        marginWidth = screenWidth / 16;
        params.setMargins(marginWidth, 0, marginWidth, 0);
        params.height = 6;
        starBarWidth = screenWidth - (marginWidth * 2);
        view.setLayoutParams(params);
    }

    private void setStarText() {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(marginWidth + (int) offset, (int) dp2px(15), 0, (int) dp2px(5));
        llStarBarText.setLayoutParams(layoutParams);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     *
     * @param dpValue 虚拟像素
     * @return 像素
     */
    public static int dp2px(float dpValue) {
        return (int) (0.5f + dpValue * Resources.getSystem().getDisplayMetrics().density);
    }
}
