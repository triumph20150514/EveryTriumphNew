package com.warm.pic.view;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.support.design.widget.BottomSheetDialog;
import android.util.Log;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * author: Trimph
 * data: 2017/4/12.
 * description:
 */

public class CusBottomDialog extends BottomSheetDialog {

    public CusBottomDialog(@NonNull Context context) {
        super(context);
    }

    public CusBottomDialog(@NonNull Context context, @StyleRes int theme) {
        super(context, theme);
    }

    protected CusBottomDialog(@NonNull Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int scrren = windowManager.getDefaultDisplay().getHeight();
        int status = getStatusBarHeight(getContext());

        Log.e("Height:", "scrren" + scrren + " status:" + status+" naginbar:"+getNavigationBarHeight());
        int dialogOffset = scrren - status;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogOffset == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogOffset);
        WindowManager.LayoutParams w = getWindow().getAttributes();
    }

    public void setParamters(){
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        int scrren = windowManager.getDefaultDisplay().getHeight();
        int status = getStatusBarHeight(getContext());
        int dialogOffset = scrren - status;
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, dialogOffset == 0 ? ViewGroup.LayoutParams.MATCH_PARENT : dialogOffset);
    }


    private static int getStatusBarHeight(Context context) {
        int statusBarHeight = 0;
        Resources res = context.getResources();
        int resourceId = res.getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = res.getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }

    /**
     * 判断是否是虚拟按键
     *
     * @return
     */
    public int getNavigationBarHeight() {
        boolean hasMenuKey = ViewConfiguration.get(getContext()).hasPermanentMenuKey();//是否有物理菜单键
        boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK); //是否有物理返回键
        Log.e("Navigation:", hasBackKey + " backKey:" + hasBackKey);
        if (!hasMenuKey && !hasBackKey) {
            Resources resources = getContext().getResources();
            int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
            //获取NavigationBar的高度
            int height = resources.getDimensionPixelSize(resourceId);
            return height;
        } else {
            return 0;
        }
    }

}
