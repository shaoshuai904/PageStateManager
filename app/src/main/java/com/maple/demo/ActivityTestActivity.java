package com.maple.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestate.PageStateManager;

/**
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class ActivityTestActivity extends AppCompatActivity {
    PageStateManager pageStateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_context);

        // pageStatusManager = new PageStatusManager(this, new MyPageConfig());
        // 不传Config，将使用默认的
        pageStateManager = new PageStateManager(this);
        View emptyView = pageStateManager.getEmptyView();
        if (emptyView != null) {
            emptyView.setOnClickListener(v -> loadData());
        }
        View retryView = pageStateManager.getRetryView();
        if (retryView != null) {
            retryView.setOnClickListener(v -> {
                Toast.makeText(ActivityTestActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                loadData();
            });
        }

        loadData();
    }

    private void loadData() {
        pageStateManager.showLoading();
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double v = Math.random();
                if (v > 0.6) {
                    pageStateManager.showContent();
                } else if (v > 0.3) {
                    pageStateManager.showRetry();
                } else {
                    pageStateManager.showEmpty();
                }
            }
        }.start();
    }

}
