package com.warm.pic.base;

import android.app.Application;

import im.fir.sdk.FIR;

/**
 * author: Trimph
 * data: 2017/4/10.
 * description:
 */

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        FIR.init(this);
    }
}
