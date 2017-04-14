package com.warm.pic.album;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.warm.pic.R;
import com.warm.pic.adapter.FlodersAdapter;
import com.warm.pic.adapter.FolderAdapter;
import com.warm.pic.adapter.ImageItemAdapter;
import com.warm.pic.mode.Floder;
import com.warm.pic.mode.Image;
import com.warm.pic.view.CusBottomDialog;
import com.warm.pic.view.CusListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/6.
 * description:
 */

public class AlbumActivity extends Activity implements ViewTreeObserver.OnGlobalLayoutListener {
    private RecyclerView recyclerGrid;
    private TextView open_folder;
    private ImageItemAdapter imageItemAdapter;
    private ListPopupWindow listPopupWindow;
    private Toolbar toolbar;
    private RelativeLayout root_view;

    private List<Image> list = new ArrayList<>();
    private List<Floder> folders = new ArrayList<>();
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
//        //去掉信息状态栏
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.muilt_image_layout);
        recyclerGrid = (RecyclerView) findViewById(R.id.recyclerGrid);
        open_folder = (TextView) findViewById(R.id.open_all);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        root_view = (RelativeLayout) findViewById(R.id.root_view);

        toolbar.setNavigationIcon(R.mipmap.back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(AlbumActivity.this, "llllllllll", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        getImageList();
        init();
        initLister();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {
        super.onResume();
        root_view.getViewTreeObserver().addOnGlobalLayoutListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        root_view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        //当内容导航栏重合时  需要设置内边距
//        getWindow().getDecorView().findViewById(android.R.id.content).setPadding(0, 0, 0, getNavigationBarHeight());

        //    <item name="android:windowBackground">@android:color/white</item>
        //通过它改变底部导航栏背景色
    }

    private void initLister() {
        open_folder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFolderWindow();
            }
        });
    }

    private void init() {
        imageItemAdapter = new ImageItemAdapter(this, list);
        recyclerGrid.setAdapter(imageItemAdapter);
        recyclerGrid.setLayoutManager(new GridLayoutManager(this, 3));

        folderAdapter = new FlodersAdapter(this, folders);


        View view = getLayoutInflater().inflate(R.layout.bottom_dialog, null, false);
        RecyclerView listView = (RecyclerView) view.findViewById(R.id.list);
        listView.setAdapter(folderAdapter);
        listView.setLayoutManager(new LinearLayoutManager(this));
//        BottomSheetBehavior bottomSheetBehavior=BottomSheetBehavior.from()

        bottomSheetDialog = new CusBottomDialog(this);
//        bottomSheetDialog.getWindow().addFlags(WindowManager.LayoutParams.Flag);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCanceledOnTouchOutside(true);
    }

    private ContentResolver contentResolver;
    private Cursor cursor;

    /**
     * 获取相册所有图片
     */
    public void getImageList() {
        contentResolver = getContentResolver();
        String[] project = new String[]{MediaStore.Images.Media.DATE_ADDED, MediaStore.Images.Media.TITLE, MediaStore.Images.Media.DATA};
        String selection = "";
        cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, project, null, null, null);
        cursor.moveToFirst();
        Image image;
        Floder floder;
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
            Log.e("File parent::::", parentFile.getAbsolutePath());
            if (parentFile.exists() && parentFile != null) {
                //判断列表中是否存在
                if (isHasCurrentFile(parentFile.getName())) {
                    floder = new Floder();
                    floder.setName(parentFile.getName());
                    floder.setPath(parentFile.getAbsolutePath());
                    floder.setFirstImage(image);
                    folders.add(floder);
                }
            }
        }
        cursor.close();
    }

    /*

     */
    private boolean isHasCurrentFile(String name) {
        for (Floder floder : folders) {
            if (floder.getName().equals(name)) {
                return false;  //存在返回false 不添加
            }
        }
        return true;
    }

    /**
     * 获取所有的文件夹
     */
    public void getFoldersData() {

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.KEYCODE_BACK) {
            if (listPopupWindow.isShowing()) {
                listPopupWindow.dismiss();
                return false;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    public FlodersAdapter folderAdapter;
    public CusBottomDialog bottomSheetDialog;

    public void showFolderWindow() {

//        if (listPopupWindow != null) {
//            if (!listPopupWindow.isShowing()) {
//                listPopupWindow.show();
//            }
//        } else {
//            listPopupWindow = new ListPopupWindow(this);
//
//            listPopupWindow.setAdapter(folderAdapter);
//
//            listPopupWindow.setDropDownGravity(Gravity.BOTTOM);
//            //指定anchor
//            listPopupWindow.setAnchorView(open_folder);
//            listPopupWindow.setWidth(getWindow().getDecorView().getMeasuredWidth());
//            listPopupWindow.setHeight(800);
//            listPopupWindow.show();
//        }

        bottomSheetDialog.show();


        String[] str1 = {"Hello", "world", "java"};
        String[] str2 = {"Veriable", "syntax", "interator"};
        int str1Length = str1.length;
        int str2length = str2.length;

        str1 = Arrays.copyOf(str1, str1Length + str2length);//数组扩容
        System.arraycopy(str2, 0, str1, str1Length, str2length);

        Log.e("Array:", str1.length + "");
        Log.e("Array:", str2.length + "");
    }


    /**
     * 判断是否是虚拟按键
     *
     * @return
     */
    public int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey();
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Album Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    @Override
    public void onGlobalLayout() {
        if (getNavigationBarHeight() == 0) {
//            open_folder.setVisibility(View.INVISIBLE);
        } else {
//            open_folder.setVisibility(View.VISIBLE);
        }
        bottomSheetDialog.setParamters();
    }
}
