package com.warm.pic.http;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * author: Trimph
 * data: 2017/4/24.
 * description:
 */

public class HttpApi {


    public static void reqest() {


        RequestBody formBody = new FormBody.Builder()
                .add("type", "1")
                .add("pagesize", "3")
                .add("page", "1")
                .build();
//        ?type=1&pagesize=3&page=1
        OkHttpClient mOkHttpClient;

        mOkHttpClient = new OkHttpClient();
        Request.Builder requestBuilder = new Request.Builder()
                .url("http://fljk.trendyactivity.com/API/news.ashx?type=1&pagesize=3&page=1");
//                .post(formBody);

        //可以省略，默认是GET请求
//        requestBuilder.method("GET", null);
        final Request request = requestBuilder.build();
        Call mcall = mOkHttpClient.newCall(request);


        try {
            mcall.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }


        mcall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

//                Log.e("wangshu", "cache--***-" + response.body().string());

                if (null != response.cacheResponse()) {
                    String str = response.cacheResponse().toString();
                    Log.e("wangshu", "cache---" + str);
                } else {

                    Log.e("wangshu", "network**---" + response.body().string());

                    String str = response.networkResponse().toString();
                    Log.e("wangshu", "network---" + str);
                }

//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(getApplicationContext(), "请求成功", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });


    }


}
