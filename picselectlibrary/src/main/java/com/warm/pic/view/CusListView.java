package com.warm.pic.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ListView;

import java.util.List;

/**
 * author: Trimph
 * data: 2017/4/12.
 * description:
 */

public class CusListView extends ListView {
    public CusListView(Context context) {
        super(context);
    }

    public CusListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
//        requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(ev);
    }

}
