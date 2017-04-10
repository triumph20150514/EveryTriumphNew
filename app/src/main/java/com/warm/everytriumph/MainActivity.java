package com.warm.everytriumph;

import android.Manifest;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.szdv.sdk.SDK;
import com.warm.everytriumph.receiver.MyAdminReceiver;
import com.warm.everytriumph.surface.SurfaceHolder;
import com.warm.everytriumph.utils.Utils;
import com.warm.everytriumph.view.ChioseMode;
import com.warm.everytriumph.view.CircleChioseView;
import com.warm.everytriumph.view.TrainingViewCallBack;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

public class MainActivity extends AppCompatActivity {

    private Handler handler = new Handler();

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void
    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ArrayDeque<String> arrayDeque = new ArrayDeque<>();
        arrayDeque.add("第一个加入");
        arrayDeque.add("第二个加入");
        arrayDeque.add("最后一个加入");

        ArrayDeque<String> arrayDeque1 = new ArrayDeque<>();
        arrayDeque1.push("第一个加入");
        arrayDeque1.push("第二个加入");
        arrayDeque1.push("最后一个加入");

        Log.e("ArrayDeque", arrayDeque.getFirst() + "");
        Log.e("ArrayDeque1", arrayDeque1 + "");

        Log.e("Current id:", handler.getLooper().getThread().getId() + "::" + handler.getLooper().getThread().getName());
        Log.e("Current id:", Thread.currentThread().getId() + "::::" + Thread.currentThread().getName());

        myThread = new MyThread(new Runnable() {
            @Override
            public void run() {

            }
        });

        Log.e("Current id:", myThread.getId() + "::" + myThread.getName());

//        //提示激活设备管理器
//        Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
//        ComponentName deviceComponentName = new ComponentName(getPackageName(), MyAdminReceiver.class.getName());
//        intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, deviceComponentName);
//        this.startActivity(intent);


        Button button = (Button) findViewById(R.id.get_permiss);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("Device", "getManager");
                Utils.getAdmin(MainActivity.this);
            }
        });

        Map map = new ArrayMap();
        ArrayList<String> strings = new ArrayList<>();

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

    private MyThread myThread;

    public class MyThread extends Thread {
        public MyThread(Runnable target) {
            super(target);
        }
    }


    public List<ChioseMode> chioseModeList = new ArrayList<>();

    public void initData() {
        ChioseMode chioseMode;
        for (int i = 0; i < 5; i++) {
            chioseMode = new ChioseMode(R.mipmap.close, "close");
            chioseModeList.add(chioseMode);
        }
    }


    public class MyCallable<String, Integer> implements Callable<Integer> {
        public String strings;

        @Override
        public Integer call() throws Exception {
            return null;
        }
    }

}
