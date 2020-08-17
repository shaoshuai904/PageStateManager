package com.maple.pagestatusmanager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestatusmanager.utils.LoadingAndRetryManager;
import com.maple.pagestatusmanager.utils.OnLoadingAndRetryListener;


public class MainActivity extends AppCompatActivity {
    LoadingAndRetryManager mLoadingAndRetryManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLoadingAndRetryManager = LoadingAndRetryManager.generate(this, new OnLoadingAndRetryListener() {
            @Override
            public void setRetryEvent(View retryView) {
                MainActivity.this.setRetryEvent(retryView);
            }
        });

        loadData();
    }

    private void loadData() {
        mLoadingAndRetryManager.showLoading();

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
                    mLoadingAndRetryManager.showContent();
                } else if (v > 0.4) {
                    mLoadingAndRetryManager.showRetry();
                } else {
                    mLoadingAndRetryManager.showEmpty();
                }
            }
        }.start();

    }


    public void setRetryEvent(View retryView) {
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                loadData();
            }
        });
    }
}
