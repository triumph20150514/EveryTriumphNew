package com.warm.pic.utils;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.warm.pic.contants.Contants;
import com.warm.pic.mode.UpdateMode;
import com.warm.pic.version.AppVersionCallBack;

import java.lang.reflect.Type;

import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;


/**
 * author: Trimph
 * data: 2017/4/19.
 * description:
 */

public class UpdateUtils{

    public static Type type;
    public static <T> T updateApp(final AppVersionCallBack<T> appVersionCallBack) {
        FIR.checkForUpdateInFIR(Contants.fir_update_key, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Log.e("fir", "check from fir.im success! " + "\n" + versionJson);
//                T updateMode1 = JsonUtils.JsonToClass(versionJson, );

//              /ersionCallBack.success(updateMode1);
//                new Gson().fromJson(versionJson, type);
            }

            @Override
            public void onFail(Exception exception) {
                Log.e("fir", "check fir.im fail! " + "\n" + exception.getMessage());
            }

            @Override
            public void onStart() {
//                Toast.makeText(getApplicationContext(), "正在获取", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
//                Toast.makeText(getApplicationContext(), "获取完成", Toast.LENGTH_SHORT).show();
            }
        });

        return null;
    }
}
