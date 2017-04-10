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

public class CusRelativeLayout2 extends RelativeLayout {
    public CusRelativeLayout2(Context context) {
        this(context, null);
    }

    public CusRelativeLayout2(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusRelativeLayout2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("MeasureSpec 2", "UNSPECIFIED");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("MeasureSpec 2", "EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                Log.e("MeasureSpec 2", "AT_MOST");
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("activity CusRelative2", "dispatchTouchEvent");
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusRelative2", "dispatch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusRelative2", "dispatch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusRelative2", "dispatch ACTION_UP");
                break;
            default:
                Log.e("activity CusRelative", "dispatch default::" + ev.getAction());
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.e("activity CusRelative2", "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("activity CusRelative2", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusRelative2", " onTouch ACTION_DOWN");
                break;
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusRelative2", "onTouch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusRelative2", "onTouch ACTION_UP");
                break;
            default:
                Log.e("activity CusRelative", "onTouch default::" + event.getAction());
                break;
        }
        return super.onTouchEvent(event);
    }
}
