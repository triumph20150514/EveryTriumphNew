package com.warm.everytriumph;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.szdv.sdk.SDK;
import com.warm.everytriumph.surface.SurfaceHolder;
import com.warm.everytriumph.view.ChioseMode;
import com.warm.everytriumph.view.CircleChioseView;
import com.warm.everytriumph.view.TrainingViewCallBack;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CircleChioseView circlechiose = (CircleChioseView) findViewById(R.id.circlechiose);

        initData();
        circlechiose.setChioseModeList(chioseModeList);

        TextView textView = (TextView) findViewById(R.id.tv);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            textView.setText(Html.fromHtml(
                    "<p style=\"font-weight:100\">法瑞和日语</p>", Html.FROM_HTML_MODE_COMPACT));
        }
        TrainingViewCallBack trainingViewCallBack = new TrainingViewCallBack(this);
        SurfaceHolder surfaceHolder = trainingViewCallBack.getSurfaceHolder();
        surfaceHolder.addCallBack(new SurfaceHolder.Callback() {
            @Override
            public void onFlingDown(SurfaceHolder surfaceHolder) {

            }
        });


    }

    public List<ChioseMode> chioseModeList = new ArrayList<>();

    public void initData() {
        ChioseMode chioseMode;
        for (int i = 0; i < 5; i++) {
            chioseMode = new ChioseMode(R.mipmap.close, "close");
            chioseModeList.add(chioseMode);
        }
    }
}
