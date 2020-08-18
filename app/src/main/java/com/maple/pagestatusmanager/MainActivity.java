package com.maple.pagestatusmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestatusmanager.utils.PageStatusManager;

import org.jetbrains.annotations.Nullable;


public class MainActivity extends AppCompatActivity {
    PageStatusManager pageStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        pageStatusManager = new PageStatusManager(this)
                .setPageCallBack(new PageStatusManager.PageCallBack() {
                    @Override
                    public void setRetryEvent(@Nullable View retryView) {
                        View view = retryView.findViewById(R.id.id_btn_retry);
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(MainActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                                loadData();
                            }
                        });
                    }
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
