package com.warm.everytriumph;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.SimpleAdapter;

import com.warm.everytriumph.view.CusListView;
import com.warm.everytriumph.view.CusRelativeLayout;
import com.warm.everytriumph.view.CusTextView;

import java.util.ArrayList;
import java.util.List;

import static android.os.AsyncTask.THREAD_POOL_EXECUTOR;

/**
 * author: Trimph
 * data: 2017/3/24.
 * description:
 */

public class TrainActivity extends Activity {

    CusRelativeLayout container;
    AsyncTask asyncTask;
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (!asyncTask.isCancelled())
                asyncTask.cancel(true);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_train);

        CusListView listView = (CusListView) findViewById(R.id.listview);
        ScrollView scrollview = (ScrollView) findViewById(R.id.scrollview);

        container = (CusRelativeLayout) findViewById(R.id.container);

        CusTextView cusTextView= (CusTextView) findViewById(R.id.tv);

//        cusTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });


        new AsyncTask<Void, Integer, String>() {
            @Override
            protected String doInBackground(Void... params) {
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected void onCancelled(String s) {
                super.onCancelled(s);
            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
            }
        };
        asyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] params) {  //执行后台任务 然后处理结果

                Log.e("asyncTask::", "doInBackground");          // 在这个方法中可以调用publishProgress来更新任务进度
                // （publishProgress内部会调用onProgressUpdate方法）
                try {
//                    Thread.sleep(500);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                Log.e("asyncTask::", "onPreExecute");

            }

            @Override
            protected void onPostExecute(Object o) {  //后台任务后 执行此方法
                super.onPostExecute(o);
                Log.e("asyncTask::", "onPostExecute");

            }

            @Override
            protected void onProgressUpdate(Object[] values) {     //若是下载任务 可以更新进度
                super.onProgressUpdate(values);
                Log.e("asyncTask::", "onProgressUpdate");

            }

            @Override
            protected void onCancelled(Object o) {
                super.onCancelled(o);
                Log.e("asyncTask::", "onCancelled1");

            }

            @Override
            protected void onCancelled() {
                super.onCancelled();
                Log.e("asyncTask::", "onCancelled2");   //先执行
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.e("asyncTask::", "Thread start");
                if (asyncTask != null)
                    asyncTask.execute();
            }
        }).start();

//        asyncTask.executeOnExecutor(THREAD_POOL_EXECUTOR,null);

        handler.sendEmptyMessageDelayed(100, 300);

        scrollview.smoothScrollTo(0, 0); //滚动到头部

        listView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_expandable_list_item_1, getData()));


    }


    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 0; i < 30; i++) {
            data.add("测试" + i);
        }
        return data;

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("activity::", "dispatchTouchEvent:"+super.dispatchTouchEvent(ev));   //先执行
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.e("activity::", "onTouchEvent:"+super.onTouchEvent(event));   //先执行
        return super.onTouchEvent(event);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
