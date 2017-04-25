package com.warm.pic.utils;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;

/**
 * author: Trimph
 * data: 2017/4/21.
 * description:
 */

public class DownLoadUtils {

    public static DownloadManager downloadManager;

    public static void getInstance(Context context) {
        if (downloadManager == null) {
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
        }
    }


    /**
     * @param downLoadUrl
     * @param folderName
     * @param fileName
     */
    public static void startDownLoad(String downLoadUrl, String folderName, String fileName) {

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downLoadUrl));
        request.setTitle("启动下载");
        request.setDestinationInExternalPublicDir(folderName, fileName);
        request.setDescription("介绍内容");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);


        if (downloadManager != null) {
            Long downId = downloadManager.enqueue(request);
        }
    }


}
