package com.example.dialogtest.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.example.dialogtest.R;

import java.text.DecimalFormat;

/**
 * Author: AND
 * Time: 2019-7-25.  上午 10:08
 * Package: com.example.dialogtest.customview
 * FileName: HorizontalProgressBar
 */
public class HorizontalProgressBar extends View {
    private Paint bgPaint;
    private Paint progressPaint;

    private Paint tipPaint;
    private Paint textPaint;

    private int mWidth;
    private int mHeight;
    private int mViewHeight;
    /**
     * 进度
     */
    private float mProgress;

    /**
     * 当前进度
     */
    private float currentProgress;

    /**
     * 进度动画
     */
    private ValueAnimator progressAnimator;

    /**
     * 动画执行时间
     */
    private int duration = 500;
    /**
     * 动画延时启动时间
     */
    private int startDelay = 300;

    /**
     * 进度条画笔的宽度
     */
    private int progressPaintWidth;

    /**
     * 百分比提示框画笔的宽度
     */
    private int tipPaintWidth;

    /**
     * 百分比提示框的高度
     */
    private int tipHeight;

    /**
     * 百分比提示框的宽度
     */
    private int tipWidth;

    /**
     * 画三角形的path
     */
    private Path path = new Path();
    /**
     * 三角形的高
     */
    private int triangleHeight;
    /**
     * 进度条距离提示框的高度
     */
    private int progressMarginTop;
    /**
     * 进度移动的距离
     */
    private float moveDis;

    private Rect textRect = new Rect();
    private String textString = "0";
    /**
     * 百分比文字字体大小
     */
    private int textPaintSize;

    /**
     * 进度条背景颜色
     */
    private int bgColor = 0xFFe1e5e8;
    /**
     * 进度条颜色
     */
    private int progressColor = 0xFFf66b12;

    private int indicatorColor = 0xFFFC7873;

    /**
     * 绘制提示框的矩形
     */
    private RectF rectF = new RectF();

    /**
     * 圆角矩形的圆角半径
     */
    private int roundRectRadius;

    /**
     * 进度监听回调
     */
    private ProgressListener progressListener;

    public HorizontalProgressBar(Context context) {
        super(context);
    }

    public HorizontalProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
        initPaint();
    }

    /**
     * 初始化画笔宽度及view大小
     */
    private void init() {
        progressPaintWidth = dp2px(4);
        tipHeight = dp2px(20);
        tipWidth = dp2px(70);
        tipPaintWidth = dp2px(1);
        triangleHeight = dp2px(5);
        roundRectRadius = dp2px(3);
        textPaintSize = sp2px(12);
        progressMarginTop = dp2px(10);
        //view真实的高度
        mViewHeight = tipHeight + tipPaintWidth + triangleHeight + progressPaintWidth + progressMarginTop;
    }

    /**
     * 初始化画笔
     */
    private void initPaint() {
        bgPaint = getPaint(progressPaintWidth, bgColor, Paint.Style.STROKE);
        progressPaint = getPaint(progressPaintWidth, progressColor, Paint.Style.STROKE);
        tipPaint = getTipPaint(tipPaintWidth, indicatorColor, Paint.Style.FILL);

        initTextPaint();
    }

    /**
     * 初始化文字画笔
     */
    private void initTextPaint() {
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(textPaintSize);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);
    }

    /**
     * 统一处理paint
     */
    private Paint getPaint(int strokeWidth, int color, Paint.Style style) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setColor(color);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    private Paint getTipPaint(int strokeWidth, int color, Paint.Style style) {
        //两个坐标形成变量，规定了渐变的方向和间距大小，着色器为镜像
        LinearGradient linearGradient = new LinearGradient(0, 0, 200, 200, Color.parseColor("#FBAC78"), Color.parseColor("#FC7873"), Shader.TileMode.MIRROR);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(strokeWidth);
        paint.setShader(linearGradient);
        paint.setAntiAlias(true);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(style);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        setMeasuredDimension(measureWidth(widthMode, width), measureHeight(heightMode, height));
    }

    /**
     * 测量宽度
     */
    private int measureWidth(int mode, int width) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                break;
            case MeasureSpec.EXACTLY:
                mWidth = width;
                break;
        }
        return mWidth;
    }

    /**
     * 测量高度
     */
    private int measureHeight(int mode, int height) {
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
            case MeasureSpec.AT_MOST:
                mHeight = mViewHeight;
                break;
            case MeasureSpec.EXACTLY:
                mHeight = height;
                break;
        }
        return mHeight;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawLine(getPaddingLeft(), tipHeight + progressMarginTop, getWidth(), tipHeight + progressMarginTop, bgPaint);

        canvas.drawLine(getPaddingLeft(), tipHeight + progressMarginTop, currentProgress, tipHeight + progressMarginTop, progressPaint);

        drawTipView(canvas);
        drawText(canvas, textString);

    }

    private void drawImage(Canvas canvas) {
        Paint mPaint = new Paint(); //创建画笔
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(R.color.colorAccent));
        mPaint.setStrokeWidth(20);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        canvas.save();
        //获取图片
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);
        //设置圆形bitmap
        Bitmap CircleBitmap = createCircleBitmap(bitmap);

        Matrix matrix = new Matrix();
        //设置bitmap图片的缩放
        matrix.postScale(0.05f, 0.05f);
        canvas.drawBitmap(CircleBitmap, matrix, mPaint);
        canvas.restore();
    }

    /**
     * 绘制进度上边提示百分比的view
     */
    private void drawTipView(Canvas canvas) {
        drawRoundRect(canvas);
        drawTriangle(canvas);
    }


    /**
     * 绘制圆角矩形
     */
    private void drawRoundRect(Canvas canvas) {
        rectF.set(moveDis, 0, tipWidth + moveDis, tipHeight);
        canvas.drawRoundRect(rectF, roundRectRadius, roundRectRadius, tipPaint);
    }

    /**
     * 绘制三角形
     */
    private void drawTriangle(Canvas canvas) {
        //path.moveTo(tipWidth / 2 - triangleHeight + moveDis, tipHeight);
        path.moveTo(tipWidth / 4 - triangleHeight + moveDis, tipHeight);
        path.lineTo(tipWidth / 4 + moveDis, tipHeight + triangleHeight);
        path.lineTo(tipWidth / 4 + triangleHeight + moveDis, tipHeight);
        canvas.drawPath(path, tipPaint);
        path.reset();
    }

    /**
     * 绘制文字
     */
    private void drawText(Canvas canvas, String textString) {
        textRect.left = (int) moveDis;
        textRect.top = 0;
        textRect.right = (int) (tipWidth + moveDis);
        textRect.bottom = tipHeight;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //文字绘制到整个布局的中心位置
        canvas.drawText(textString, textRect.centerX(), baseline, textPaint);
    }

    /**
     * 进度移动动画  通过插值的方式改变移动的距离
     */
    private void initAnimation() {
        progressAnimator = ValueAnimator.ofFloat(0, mProgress);
        progressAnimator.setDuration(duration);
        progressAnimator.setStartDelay(startDelay);
        progressAnimator.setInterpolator(new LinearInterpolator());
        progressAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                float value = (float) valueAnimator.getAnimatedValue();
                //进度数值只显示整数，我们自己的需求，可以忽略
                //textString = getResources().getString(R.string.progressbarIndetior, String.valueOf(formatNum(format2Int(value))));
                //把当前百分比进度转化成view宽度对应的比例
                currentProgress = value * mWidth / 100;
                //进度回调方法
                if (progressListener != null) {
                    progressListener.currentProgressListener(value);
                }
                //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
                //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
                //moveDis是tip框移动的距离
                if (currentProgress >= (tipWidth / 2) && currentProgress <= (mWidth - tipWidth / 2)) {
                    moveDis = currentProgress - tipWidth / 2;
                }
                invalidate();
                setCurrentProgress(value);
            }
        });
        progressAnimator.start();
    }


    /**
     * 设置进度条带动画效果
     */
    public HorizontalProgressBar setProgressWithAnimation(float progress) {
        mProgress = progress;
        initAnimation();
        return this;
    }

    public HorizontalProgressBar setProgressTips(String progress) {
        this.textString = progress;
        return this;
    }

    /**
     * 实时显示进度
     */
    public HorizontalProgressBar setCurrentProgress(float progress) {
        mProgress = progress;
        currentProgress = progress * mWidth / 100;
        //textString = formatNum(format2Int(progress));
        //textString = getResources().getString(R.string.progressbarIndetior, String.valueOf(formatNum(format2Int(progress))));

        //移动百分比提示框，只有当前进度到提示框中间位置之后开始移动，
        //当进度框移动到最右边的时候停止移动，但是进度条还可以继续移动
        //moveDis是tip框移动的距离
        if (currentProgress >= (tipWidth / 2) &&
                currentProgress <= (mWidth - tipWidth / 2)) {
            moveDis = currentProgress - tipWidth / 2;
        }

        invalidate();
        return this;
    }

    private Bitmap createCircleBitmap(Bitmap resource) {
        //获取图片的宽度
        int width = resource.getWidth();
        Paint paint = new Paint();
        //设置抗锯齿
        paint.setAntiAlias(true);

        //创建一个与原bitmap一样宽度的正方形bitmap
        Bitmap circleBitmap = Bitmap.createBitmap(width, width, Bitmap.Config.ARGB_8888);
        //以该bitmap为低创建一块画布
        Canvas canvas = new Canvas(circleBitmap);
        //以（width/2, width/2）为圆心，width/2为半径画一个圆
        canvas.drawCircle(width / 2 - 200, width / 2 - 200, width / 2 - 200, paint);

        //设置画笔为取交集模式
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        //裁剪图片
        canvas.drawBitmap(resource, 0, 0, paint);

        return circleBitmap;
    }

    /**
     * 开启动画
     */
    public void startProgressAnimation() {
        if (progressAnimator != null &&
                !progressAnimator.isRunning() &&
                !progressAnimator.isStarted())
            progressAnimator.start();
    }

    /**
     * 暂停动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void pauseProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.pause();
        }
    }

    /**
     * 恢复动画
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void resumeProgressAnimation() {
        if (progressAnimator != null)
            progressAnimator.resume();
    }

    /**
     * 停止动画
     */
    public void stopProgressAnimation() {
        if (progressAnimator != null) {
            progressAnimator.end();
        }
    }

    /**
     * 回调接口
     */
    public interface ProgressListener {
        void currentProgressListener(float currentProgress);
    }

    /**
     * 回调监听事件
     */
    public HorizontalProgressBar setProgressListener(ProgressListener listener) {
        progressListener = listener;
        return this;
    }

    /**
     * 格式化数字(保留两位小数)
     */
    public static String formatNumTwo(double money) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format(money);
    }

    /**
     * 格式化数字(保留一位小数)
     */
    public static String formatNum(int money) {
        DecimalFormat format = new DecimalFormat("0");
        return format.format(money);
    }

    /**
     * dp 2 px
     */
    protected int dp2px(int dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, spVal, getResources().getDisplayMetrics());
    }

    public static int format2Int(double i) {
        return (int) i;
    }
}
