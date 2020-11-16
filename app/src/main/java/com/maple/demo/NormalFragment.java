package com.maple.demo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.maple.demo.config.PigConfig;
import com.maple.pagestate.PageStateManager;


/**
 * PageStateManager 直接在 Fragment 中的使用
 *
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class NormalFragment extends Fragment {
    PageStateManager pageStateManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_context, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pageStateManager = new PageStateManager(this, new PigConfig());
        View emptyView = pageStateManager.getEmptyView();
        if (emptyView != null) {
            emptyView.setOnClickListener(v -> loadData());
        }
        View retryView = pageStateManager.getRetryView();
        if (retryView != null) {
            retryView.setOnClickListener(v -> {
                Toast.makeText(getActivity(), "retry event invoked", Toast.LENGTH_SHORT).show();
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
                if (Math.random() > 0.6) {
                    pageStateManager.showContent();
                } else {
                    pageStateManager.showEmpty();
                }
            }
        }.start();
    }
}


