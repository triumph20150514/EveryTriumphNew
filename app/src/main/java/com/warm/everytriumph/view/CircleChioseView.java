package com.warm.everytriumph.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;

import com.warm.everytriumph.R;

import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/3/3.  伪3D效果
 * description: 参考原文：https://github.com/JustinFincher/JZMultiChoicesCircleButton
 */

public class CircleChioseView extends View {

    private Paint paint;
    private Camera camera;
    private Matrix matrix;
    private Matrix cMatrix;
    private boolean isParallaxEffect = true; //默认开启
    private float radius = dpToPx(20); //默认大小
    private float maxRadius = dpToPx(90); //默认大小
    private int color = R.color.color4gMenuPress;
    private Rect centerRect;
    private Rect[] rects = new Rect[]{};
    private Bitmap bitmap;
    private int mWidth, mHeight;
    private int bWidth, bHeight;
    private int centerX, centerY;
    private int CIRCLR_STATE;
    private int collpand = 1; //收缩
    private int collpanding = 2;  //正在收缩
    private int expand = 3; //展开状态
    private int expanding = 4; //展开完成
    public List<ChioseMode> chioseModeList = new ArrayList<>();
    private ImageView[] menus = new ImageView[5];
    private Context context;
    private Paint menuPaint;
    private int[] colors = new int[]{
            Color.parseColor("#ffF9BAE9"),
            Color.parseColor("#ff5BF998"),
            Color.parseColor("#ffCCE3F9"),
            Color.parseColor("#ffBB2EF9"),
            Color.parseColor("#ff7AA5F9"),
    };
    public Animation expandCircle, collpandCircle;
    public float currentProgress = 0;
    public float fromProgress;


    public List<ChioseMode> getChioseModeList() {
        return chioseModeList;
    }

    public void setChioseModeList(List<ChioseMode> chioseModeList) {
        this.chioseModeList = chioseModeList;
        invalidate();
    }

    public CircleChioseView(Context context) {
        this(context, null);
    }

    public CircleChioseView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleChioseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        init();
    }


    /**
     * 多少个菜单
     */
    private void initImages() {
        ImageView imageView;
        for (int i = 0; i < chioseModeList.size(); i++) {
            imageView = new ImageView(context);
            imageView.setBackgroundResource(chioseModeList.get(i).getRes());
//            menus[i] = imageView;
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.mWidth = w - getPaddingLeft() - getPaddingRight();
        this.mHeight = h - getPaddingTop() - getPaddingBottom();
        centerX = mWidth / 2;
        centerY = mHeight / 2;
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#ff43b2ff"));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(3);

        menuPaint = new Paint();
        menuPaint.setAntiAlias(true);
        menuPaint.setColor(R.color.hy_color);
        menuPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        cMatrix = new Matrix();
        camera = new Camera();
        Log.e("Camera X:", camera.getLocationX() + "");
        Log.e("Camera Y:", camera.getLocationY() + "");
        Log.e("Camera Z:", camera.getLocationZ() + "");

        matrix = new Matrix();
        BitmapFactory.Options b = new BitmapFactory.Options();
        b.inSampleSize = 2;
        bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.close, b);

        bHeight = bitmap.getHeight();
        bWidth = bitmap.getWidth();

        centerRect = new Rect();

        CIRCLR_STATE = collpand; //默认关闭
        ExpanCircle();  //初始化动画
        collpandCircle();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (isParallaxEffect && (CIRCLR_STATE == expand)) {
            canvas.concat(cMatrix);  //canvas与matrix相关连
        }
        canvas.translate(mWidth / 2, mHeight / 2);

        //先画一个圆扩散
        drawCircle(canvas);

        drawCenter(canvas);
        //先画圆
        drawItems(canvas);
    }

    float downX, downY;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        downX = event.getX();
        downY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (CIRCLR_STATE == collpand) {
                    if (centerRect.contains((int) downX, (int) downY)) {
                        startExpandAnimaor();
                    }
                } else if (CIRCLR_STATE == expand) {
                    calculateRotate(downX, downY);
                    if (centerRect.contains((int) downX, (int) downY)) {
                        startCollpandAnimaor();
                    }
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("TouchEvent: DOWN ", "downX:" + downX + " downY:" + downY);
                if (CIRCLR_STATE == expand) {
                    calculateRotate(downX, downY);
                }
                invalidate();
                return true;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                calculateRotate(centerX, centerY);
                invalidate();
                return true;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    /**
     *
     */
    private void startExpandAnimaor() {
        expandCircle.setDuration(600);
        startAnimation(expandCircle);
    }

    /**
     *
     */
    private void startCollpandAnimaor() {
        collpandCircle.setDuration(400);
        startAnimation(collpandCircle);
    }


    /**
     * 计算旋转角度
     */
    public void calculateRotate(float pointX, float pointY) {
        int size = Math.max(mWidth, mHeight);
        float offsetX = (centerX - pointX);
        float offsetY = (centerY - pointY);
        Log.e("TouchEvent: DOWN ", "offsetX:" + offsetX + " offsetY:" + offsetY);
        Log.e("TouchEvent: DOWN ", "size:" + size);
        float rotateX = offsetY / size * 45;    //45 表示最大旋转度数
        float rotateY = -offsetX / size * 45;  //若是往同一个方向旋转不会有视图差效果
        Log.e("TouchEvent: DOWN ", "rotateX:" + rotateX + " rotateY:" + rotateY);
        camera.save();
        camera.rotateX(rotateX);  //
        camera.rotateY(rotateY);  //旋转
        camera.getMatrix(cMatrix);
        camera.restore();

        //必须是视图中心位置
        cMatrix.preTranslate(-centerX, -centerY);  //左移
        cMatrix.postTranslate(centerX, centerY);   //这是一对不然 图形会偏移到左上角 相机的位置  不懂这里为什么这样写

    }

    private void drawCenter(Canvas canvas) {
        canvas.save();
        canvas.translate(currentProgress * 2f, currentProgress * 2f);
        matrix.setTranslate(-bWidth / 2, -bHeight / 2);
        canvas.drawBitmap(bitmap, matrix, paint);
        canvas.restore();
    }

    public float currentRadius;

    /**
     *
     */
    private void drawCircle(Canvas canvas) {
        canvas.save();
        canvas.translate(currentProgress * 2f, currentProgress * 2f);
        centerRect.set((int) (mWidth / 2 - radius), (int) (mHeight / 2 - radius),
                (int) (mWidth / 2 + radius), (int) (mHeight / 2 + radius));
        currentRadius = (maxRadius - radius) * currentProgress + radius;
        canvas.drawCircle(0, 0, currentRadius, paint);
//        canvas.drawPoint((mWidth / 2 - radius), (mHeight / 2 - radius), paint);
        canvas.restore();
    }


    /**
     * 收缩
     */
    public void collpandCircle() {
        collpandCircle = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.e("Tansformation:", interpolatedTime + "");
                currentProgress = (1 - interpolatedTime);
                invalidate();
            }
        };

        collpandCircle.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                CIRCLR_STATE = collpanding;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                CIRCLR_STATE = collpand;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });


    }


    /**
     * 扩散中间的圆
     */
    public void ExpanCircle() {
        expandCircle = new Animation() {
            @Override
            protected void applyTransformation(float interpolatedTime, Transformation t) {
                super.applyTransformation(interpolatedTime, t);
                Log.e("Tansformation:", interpolatedTime + "");
                currentProgress = fromProgress + interpolatedTime * (1 - fromProgress);
                invalidate();
            }
        };

        expandCircle.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                CIRCLR_STATE = expanding;
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                CIRCLR_STATE = expand;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

    }

    public int number;
    public Bitmap menuBitmap;
    public int menuHeight, menuWidth;

    /**
     * 绘制菜单项
     */
    private void drawItems(Canvas canvas) {

        canvas.save();
        canvas.translate(currentProgress * 2f, currentProgress * 2f);  //旋转画布形成远小进大的效果。

        int size = chioseModeList.size();

        if (bitmap == null) {
            Log.e("Item", "---------");
            return;
        }

//        menuBitmap = scaleBitmap(bitmap, 0.6f);
//        menuHeight = menuBitmap.getHeight();
//        menuWidth = menuBitmap.getWidth();
        if (size <= 1) {
            return;
        }
        float angle = (float) (2 * Math.PI / size); //每个按钮移动间隔
        mMx = new Matrix();
        for (int i = 0; i < 5; i++) {
            //画出菜单
            float pointX = (float) (Math.sin(angle * i) * maxRadius * 0.7f);
            float pointY = (float) (Math.cos(angle * i) * maxRadius * 0.7f);
            menuPaint.setColor(colors[i]);
            if (Math.abs(pointX) > bWidth || Math.abs(pointY) > bWidth) {
                if (currentProgress >= 0.4) {
//                    mMx.preScale(0.6f * currentProgress, 0.6f * currentProgress);
                    mMx.setTranslate(pointX * currentProgress - bWidth / 2, pointY * currentProgress - bHeight / 2);
//                    mMx.postScale(0.8f*currentProgress,0.8f*currentProgress);
                    canvas.drawCircle(pointX * currentProgress, pointY * currentProgress, radius * currentProgress - 10, menuPaint);
                    canvas.drawBitmap(bitmap, mMx, paint);
                }
            }
        }
        canvas.restore();
    }

    /**
     * 按比例缩放图片
     *
     * @param origin 原图
     * @param ratio  比例
     * @return 新的bitmap
     */
    private Bitmap scaleBitmap(Bitmap origin, float ratio) {
        if (origin == null) {
            return null;
        }
        int width = origin.getWidth();
        int height = origin.getHeight();
        Matrix matrix = new Matrix();
        matrix.preScale(ratio, ratio);
        Bitmap newBM = Bitmap.createBitmap(origin, 0, 0, width, height, matrix, false);
        if (newBM.equals(origin)) {
            return newBM;
        }
        origin.recycle();
        return newBM;
    }


    public Matrix mMx;

    public float dpToPx(float dp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, Resources.getSystem().getDisplayMetrics());
    }
}
