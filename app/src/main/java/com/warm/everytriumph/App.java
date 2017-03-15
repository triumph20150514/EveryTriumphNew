package com.warm.everytriumph;

import android.app.Application;

import com.szdv.sdk.SDK;

/**
 * author: Trimph
 * data: 2017/3/13.
 * description:
 */

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        SDK.SetApplication(this);

    }
}
