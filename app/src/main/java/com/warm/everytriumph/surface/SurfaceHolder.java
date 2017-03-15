package com.warm.everytriumph.surface;

/**
 * author: Trimph
 * data: 2017/3/1.
 * description:
 */

public interface SurfaceHolder {

    public interface Callback {
        public void onFlingDown(SurfaceHolder surfaceHolder);
    }

    public void addCallBack(Callback callback);
}
