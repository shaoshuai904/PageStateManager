package com.maple.pagestate;

import android.view.View;

/**
 * 页面状态改变监听
 *
 * @author : shaoshuai
 * @date ：2020/11/13
 */
public interface PageChangeListener {
    void onShowLoading(View loadingView);

    void onShowRetry(View retryView);

    void onShowEmpty(View emptyView);

    void onShowContent(View contentView);
}