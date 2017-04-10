package com.warm.everytriumph.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * author: Trimph
 * data: 2017/3/20.
 * description: 拦截事件
 */

public class CusRelativeLayout extends RelativeLayout {
    public CusRelativeLayout(Context context) {
        this(context, null);
    }

    public CusRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("activity CusRelative", "dispatchTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusRelative", "dispatch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusRelative", "dispatch ACTION_MOVE");
                //当父类在分发的时候拦截ACTION_MOVE事件事件将不会向下传递了，viewGroup中onTouchEvent不会接收到事件了，
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusRelative", "dispatch ACTION_UP");
                break;
            default:
                Log.e("activity CusRelative", "dispatch default::" + ev.getAction());
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("activity CusRelative", "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        measureChildren(widthMeasureSpec,heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);


        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        Log.e("ViewGroup", "Measure mode:" + mode);
        Log.e("ViewGroup", "Measure size:" + size);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("MeasureSpec 1", "UNSPECIFIED");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("MeasureSpec 1", "EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                Log.e("MeasureSpec 1", "AT_MOST");
                break;
        }

        measureChildren(widthMeasureSpec,heightMeasureSpec);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("activity CusRelative", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusRelative", " onTouch ACTION_DOWN");
                break;  //父ViewGroup中onTouchEvent拦截 ACTION_DOWNS事件，然后子类ViewGroup中onTouchEvent()接受不到事件了，子类View onTouchEvent()
            //可以接收到ACTION_MOVE ACTION_UP事件，  当父类拦截ACTION_MOVE ACTION_UP事件类似。
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusRelative", "onTouch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusRelative", "onTouch ACTION_UP");
                break;
            default:
                Log.e("activity CusRelative", "onTouch default::" + event.getAction());
                break;
        }
        return super.onTouchEvent(event);
    }
}
