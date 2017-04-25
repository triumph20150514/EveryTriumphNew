package com.warm.pic.download;

import android.app.DownloadManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * author: Trimph
 * data: 2017/4/17.
 * description:
 */

public class DownLoadService extends Service {

    DownloadManager downloadManager;
    public String downLoadUrl;
    public String folderName = "TrimphDownload";
    public String fileName = "trimph.apk";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.e("DownloadService","onBing");
        return null;
    }


    public void downloadUtil() {

    }


    @Override
    public void onCreate() {
        super.onCreate();
        Log.e("DownloadService","onCreate");
//        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
//
//
//        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
//        request.setTitle("启动下载");
//        request.setDestinationInExternalPublicDir(folderName, fileName);
//
//        Long downId = downloadManager.enqueue(request);


    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e("DownloadService","onStartCommand");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.e("DownloadService","onStart");

        super.onStart(intent, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.e("DownloadService","onRebind");

        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e("DownloadService","onUnbind");

        return super.onUnbind(intent);
    }
}
