package com.maple.pagestatusmanager;

import android.app.Application;

import com.maple.pagestatusmanager.utils.PageStatusManager;


/**
 * Created by zhy on 15/8/27.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PageStatusManager.BASE_RETRY_LAYOUT_ID = R.layout.base_retry;
        PageStatusManager.BASE_LOADING_LAYOUT_ID = R.layout.base_loading;
        PageStatusManager.BASE_EMPTY_LAYOUT_ID = R.layout.base_empty;
    }
}
