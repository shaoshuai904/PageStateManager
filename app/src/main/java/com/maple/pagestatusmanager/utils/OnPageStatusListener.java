package com.maple.pagestatusmanager.utils;

import android.view.View;

/**
 * 页面状态监听
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
public class OnPageStatusListener {
    public void setRetryEvent(View retryView) {
    }

    public void setLoadingEvent(View loadingView) {
    }

    public void setEmptyEvent(View emptyView) {
    }

    public int generateLoadingLayoutId() {
        return PageStatusManager.NO_LAYOUT_ID;
    }

    public int generateRetryLayoutId() {
        return PageStatusManager.NO_LAYOUT_ID;
    }

    public int generateEmptyLayoutId() {
        return PageStatusManager.NO_LAYOUT_ID;
    }

    public View generateLoadingLayout() {
        return null;
    }

    public View generateRetryLayout() {
        return null;
    }

    public View generateEmptyLayout() {
        return null;
    }

    public boolean isSetLoadingLayout() {
        if (generateLoadingLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateLoadingLayout() != null)
            return true;
        return false;
    }

    public boolean isSetRetryLayout() {
        if (generateRetryLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateRetryLayout() != null)
            return true;
        return false;
    }

    public boolean isSetEmptyLayout() {
        if (generateEmptyLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateEmptyLayout() != null)
            return true;
        return false;
    }

}