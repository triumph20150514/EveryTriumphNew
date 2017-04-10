package com.warm.pic.album;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.warm.pic.R;
import com.warm.pic.adapter.ImageItemAdapter;
import com.warm.pic.mode.Image;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/6.
 * description:
 */

public class AlbumActivity extends Activity {
    private RecyclerView recyclerGrid;
    private TextView open_folder;
    private ImageItemAdapter imageItemAdapter;
    private ListPopupWindow listPopupWindow;

    private List<Image> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.muilt_image_layout);
        recyclerGrid = (RecyclerView) findViewById(R.id.recyclerGrid);
        open_folder = (TextView) findViewById(R.id.open_all);
        getImageList();
        init();
    }

    private void init() {
        imageItemAdapter = new ImageItemAdapter(this, list);
        recyclerGrid.setAdapter(imageItemAdapter);
        recyclerGrid.setLayoutManager(new GridLayoutManager(this, 3));
    }

    private ContentResolver contentResolver;
    private Cursor cursor;

    /**
     * 获取相册所有图片
     */
    public void getImageList() {
        contentResolver = getContentResolver();
        String[] project = new String[]{MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATA};
        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, project, null, null, null);
        cursor.moveToFirst();
        Image image;
        while (cursor.moveToNext()) {
            String time = cursor.getString(0);
            String name = cursor.getString(1);
            String path = cursor.getString(2);
            Log.e("Image", "time:" + time + " name:" + name + " path:" + path);
            image = new Image(name, path, time);
            list.add(image);

            //文件夹
            File file = new File(path);
            Log.e("File:", file.getName());
            File parentFile = file.getParentFile();
            Log.e("File parent:", parentFile.getName());
            if (parentFile.exists() && parentFile != null) {



            }

        }
        cursor.close();
    }

    /**
     * 获取所有的文件夹
     */
    public void getFoldersData() {

    }

    public void showFolderWindow() {
        listPopupWindow = new ListPopupWindow(this);

    }
}
