package com.maple.demo;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestate.PageStatusManager;

/**
 *
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class ActivityTestActivity extends AppCompatActivity {
    PageStatusManager pageStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_context);

        // pageStatusManager = new PageStatusManager(this, new MyPageConfig());
        pageStatusManager = new PageStatusManager(this);
        pageStatusManager.getEmptyView().setOnClickListener(v -> loadData());
        pageStatusManager.getRetryView().setOnClickListener(v -> {
            Toast.makeText(ActivityTestActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
            loadData();
        });

        loadData();
    }

    private void loadData() {
        pageStatusManager.showLoading();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double v = Math.random();
                if (v > 0.8) {
                    pageStatusManager.showContent();
                } else if (v > 0.4) {
                    pageStatusManager.showRetry();
                } else {
                    pageStatusManager.showEmpty();
                }
            }
        }.start();
    }

}
