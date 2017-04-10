package com.warm.pic;

import android.Manifest;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.warm.pic.album.AlbumActivity;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
//                startActivity(new Intent(MainActivity.this, AlbumActivity.class));
            }
        });

//        requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 100);

    }

    public void update() {
        FIR.checkForUpdateInFIR("36a532522e930578f34d422001c627e6", new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.e("fir", "check from fir.im success! " + "\n" + versionJson);
            }

            @Override
            public void onFail(Exception exception) {
                Log.e("fir", "check fir.im fail! " + "\n" + exception.getMessage());
            }

            @Override
            public void onStart() {
                Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Toast.makeText(getApplicationContext(), "获取完成", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
