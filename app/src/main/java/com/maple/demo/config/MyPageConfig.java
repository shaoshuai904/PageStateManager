package com.maple.demo.config;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

import com.maple.demo.R;
import com.maple.pagestate.PageChangeAction;
import com.maple.pagestate.PageConfig;


/**
 * 自定义页面配置
 *
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class MyPageConfig implements PageConfig {
    @LayoutRes int loadingLayoutId;
    @LayoutRes int emptyLayoutId;
    @LayoutRes int retryLayoutId;

    public MyPageConfig() {
        this.loadingLayoutId = R.layout.custom_loading_1;
        this.emptyLayoutId = R.layout.custom_empty_1;
        this.retryLayoutId = R.layout.custom_retry_2;
    }

    @Nullable
    @Override
    public View loadingView(Context context) {
        if (loadingLayoutId == 0)
            return null;
        return LayoutInflater.from(context).inflate(loadingLayoutId, null, false);
    }

    @Nullable
    @Override
    public View emptyView(Context context) {
        if (emptyLayoutId == 0)
            return null;
        return LayoutInflater.from(context).inflate(emptyLayoutId, null, false);
    }

    @Nullable
    @Override
    public View retryView(Context context) {
        if (retryLayoutId == 0)
            return null;
        return LayoutInflater.from(context).inflate(retryLayoutId, null, false);
    }

    @Nullable
    @Override
    public PageChangeAction getPageChangeAction() {
        return new PageChangeAction() {

        };
    }
}


