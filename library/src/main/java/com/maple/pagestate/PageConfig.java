package com.maple.pagestate;

import android.view.View;

import androidx.annotation.LayoutRes;

/**
 * 页面配置
 *
 * @author : shaoshuai
 * @date ：2020/11/13
 */
public class PageConfig {
    public static final int NO_LAYOUT_ID = 0;

    @LayoutRes
    public int mBaseLoadingLayoutId = NO_LAYOUT_ID;
    @LayoutRes
    public int mBaseEmptyLayoutId = NO_LAYOUT_ID;
    @LayoutRes
    public int mBaseRetryLayoutId = NO_LAYOUT_ID;

    public PageConfig() {
    }

    public PageConfig(@LayoutRes int loadingLayoutId, @LayoutRes int emptyLayoutId, @LayoutRes int retryLayoutId) {
        mBaseLoadingLayoutId = loadingLayoutId;
        mBaseEmptyLayoutId = emptyLayoutId;
        mBaseRetryLayoutId = retryLayoutId;
    }

    public PageChangeAction mPageChangeAction = new PageChangeAction() {

        @Override
        public void onShowLoading(View loadingView) {
            super.onShowLoading(loadingView);

        }
    };

}
