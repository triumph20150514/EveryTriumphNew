package com.warm.everytriumph.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * author: Trimph
 * data: 2017/3/27.
 * description: ScrollView嵌套listview出现的问题解决方案
 */

public class CusListView extends ListView {
    public CusListView(Context context) {
        this(context,null);
    }

    public CusListView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CusListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
