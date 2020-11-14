package com.maple.demo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;

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
        this.loadingLayoutId = R.layout.base_loading;
        this.emptyLayoutId = R.layout.base_empty;
        this.retryLayoutId = R.layout.base_retry;
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

    @Override
    public PageChangeAction getPageChangeAction() {
        return new PageChangeAction() {

        };
    }
}


