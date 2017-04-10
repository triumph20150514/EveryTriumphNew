package com.warm.everytriumph.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.TextView;

/**
 * author: Trimph
 * data: 2017/3/29.
 * description:
 */

public class CusTextView extends TextView {
    public CusTextView(Context context) {
        this(context, null);
    }

    public CusTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CusTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("activity CusTextView", "onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusTextView", " onTouch ACTION_DOWN");
                return true;
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusTextView", "onTouch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusTextView", "onTouch ACTION_UP");
                break;
            default:
                Log.e("activity CusTextView", "onTouch Default" + event.getAction());
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int size = MeasureSpec.getSize(widthMeasureSpec);
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Log.e("MeasureSpec T", "UNSPECIFIED");
                break;
            case MeasureSpec.EXACTLY:
                Log.e("MeasureSpec T", "EXACTLY");
                break;
            case MeasureSpec.AT_MOST:
                Log.e("MeasureSpec T", "AT_MOST");
                break;
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.e("activity CusTextView", "dispatchTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.e("activity CusTextView", " dispatch ACTION_DOWN");
                break; //子类View拦截ACTION_DOWN事件后不会分发了 onTouchEvent()接收不到Down事件了，但是仍会接收到MOVE UP事件。
            case MotionEvent.ACTION_MOVE:
                Log.e("activity CusTextView", "dispatch ACTION_MOVE");
                break;
            case MotionEvent.ACTION_UP:
                Log.e("activity CusTextView", "dispatch ACTION_UP");
                break;
        }
        return super.dispatchTouchEvent(event);
    }

}
