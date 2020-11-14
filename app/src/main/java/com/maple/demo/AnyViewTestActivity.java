package com.maple.demo;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.maple.pagestate.PageStatusManager;


public class AnyViewTestActivity extends AppCompatActivity {

    private TextView mTextView;

    PageStatusManager pageStatusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anyview_test);

        mTextView = (TextView) findViewById(R.id.id_textview);

        // pageStatusManager = new PageStatusManager(mTextView, new MyPageConfig());
        pageStatusManager = new PageStatusManager(mTextView);
        pageStatusManager.getEmptyView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refreshTextView();
            }
        });
        pageStatusManager.getRetryView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(AnyViewTestActivity.this, "retry event invoked", Toast.LENGTH_SHORT).show();
                refreshTextView();
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
