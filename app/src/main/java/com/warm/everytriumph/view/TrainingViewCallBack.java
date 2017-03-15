package com.warm.everytriumph.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.warm.everytriumph.surface.SurfaceHolder;

import java.util.ArrayList;

/**
 * author: Trimph
 * data: 2017/3/1.
 * description:
 */

public class TrainingViewCallBack extends View {


    public SurfaceHolder.Callback callback;

    public ArrayList<SurfaceHolder.Callback> callbacks = new ArrayList<>();

    public TrainingViewCallBack(Context context) {
        super(context);
    }

    public TrainingViewCallBack(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TrainingViewCallBack(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }


    public ArrayList<SurfaceHolder.Callback> getCallbacks() {
        return callbacks;
    }

    /**
     *
     */
    public void addCallBack(SurfaceHolder.Callback callback) {
        this.callbacks.add(callback);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        for (SurfaceHolder.Callback callback : callbacks) {
            callback.onFlingDown(surfaceHolder);
        }
        return super.onTouchEvent(event);
    }

    public SurfaceHolder surfaceHolder = new SurfaceHolder() {
        @Override
        public void addCallBack(Callback callback) {
            callbacks.add(callback);
        }
    };

    public SurfaceHolder getSurfaceHolder() {
        return surfaceHolder;
    }
}
