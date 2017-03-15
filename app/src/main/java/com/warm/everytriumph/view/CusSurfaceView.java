package com.warm.everytriumph.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * author: Trimph
 * data: 2017/3/1.
 * description:
 */

public class CusSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private SurfaceHolder mHolder;
    public Paint paint;
    public CurrentThread currentThread;

    public ThreadPoolExecutor threadPoolExecutor;

    public SensorManager sensorManager;
    public Sensor sensor;

    public CusSurfaceView(Context context) {
        this(context, null);
    }

    public CusSurfaceView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusSurfaceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }


    private void init(Context context) {
        /***
         * 线程 池使用
         *  核心线程数
         *   最大线程数
         *   保活时间  ，单位
         *
         *   任务队列（等待队列）
         *   异常处理
         */
        threadPoolExecutor = new ThreadPoolExecutor(5, 20, 2, TimeUnit.SECONDS, new ArrayBlockingQueue(3), new ThreadPoolExecutor.AbortPolicy());

        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);

        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);

        paint = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        mHolder = getHolder();//获取SurfaceHolder对象
        mHolder.addCallback(this); //添加监听    即是对surface生命周期

        sensorManager.registerListener(new SensorEventListener() {
            @Override
            public void onSensorChanged(SensorEvent event) {
                //x变化

            }

            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }
        }, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        currentThread = new CurrentThread(mHolder);
    }

    public boolean isRunning = true;
    public int raduis = 20;

    public class CurrentThread extends Thread {

        public Canvas canvas;
        public final SurfaceHolder surfaceHolder;

        public CurrentThread(SurfaceHolder surfaceHolder) {
            this.surfaceHolder = surfaceHolder;
        }

        @Override
        public void run() {
            super.run();

            while (isRunning) {
                try {
                    synchronized (surfaceHolder) {  //加锁
                        canvas = surfaceHolder.lockCanvas(); //
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR); //清除屏幕
//                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                        canvas.drawCircle(500, 500, raduis++, paint);
                        if (raduis > 120) {
                            raduis = 20;
                        }
                        Thread.sleep(50); //每次绘制完成后休眠50毫秒
                    }

                } catch (Exception e) {

                } finally {
                    if (surfaceHolder.getSurface().isValid()) {
                        surfaceHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e("Surface", "surfaceCreated");
//        currentThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e("Surface", "surfaceChanged");

        threadPoolExecutor.execute(runnable);
    }


    public Runnable runnable = new Runnable() {
        @Override
        public void run() {

            Canvas canvas = null;
            while (isRunning) {
                try {
                    synchronized (mHolder) {  //加锁
                        canvas = mHolder.lockCanvas(); //
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR); //清除屏幕
//                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.CLEAR);
                        canvas.drawCircle(500, 500, raduis++, paint);
                        if (raduis > 120) {
                            raduis = 20;
                        }
                        Thread.sleep(50); //每次绘制完成后休眠50毫秒
                    }

                } catch (Exception e) {

                } finally {
                    if (mHolder.getSurface().isValid()) {
                        mHolder.unlockCanvasAndPost(canvas);
                    }
                }
            }

        }
    };

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isRunning = false;
        Log.e("Surface", "surfaceDestroy");
    }
}
