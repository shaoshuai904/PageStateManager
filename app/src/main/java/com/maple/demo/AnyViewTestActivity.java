package com.maple.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestate.PageConfig;
import com.maple.pagestate.PageStatusManager;


public class AnyViewTestActivity extends AppCompatActivity {

    private TextView mTextView;

    PageStatusManager pageStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyview_test);

        mTextView = (TextView) findViewById(R.id.id_textview);

        PageConfig config = new PageConfig(R.layout.base_loading, R.layout.base_empty, R.layout.base_retry);
        pageStatusManager = new PageStatusManager(mTextView, config);
        View retryView = pageStatusManager.getRetryView();
        View view = retryView.findViewById(R.id.id_btn_retry);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnyViewTestActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                AnyViewTestActivity.this.refreshTextView();
            }
        });

        refreshTextView();
    }

    private void refreshTextView() {
        pageStatusManager.showLoading();

        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (Math.random() > 0.6) {
                    pageStatusManager.showContent();
                } else {
                    pageStatusManager.showRetry();
                }
            }
        }.start();
    }

}
