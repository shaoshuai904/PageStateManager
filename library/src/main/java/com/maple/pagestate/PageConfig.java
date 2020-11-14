package com.maple.pagestate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;

/**
 * 页面配置
 *
 * @author : shaoshuai
 * @date ：2020/11/13
 */
public interface PageConfig {
//    public static final int NO_LAYOUT_ID = 0;
//    @LayoutRes int loadingLayoutId();
//    @LayoutRes int emptyLayoutId();
//    @LayoutRes int retryLayoutId();

    @Nullable View loadingView(Context context);
    @Nullable View emptyView(Context context);
    @Nullable View retryView(Context context);

    PageChangeAction getPageChangeAction();

    // 默认配置
    class Default implements PageConfig {
        String loadingInfo = "loading";
        @DrawableRes int emptyIcon = R.drawable.ms_status_no_data;
        String emptyInfo = "暂无数据";
        @DrawableRes int retryIcon = R.drawable.ms_status_no_net;
        String retryInfo = "加载失败，请重试~";

        public Default() {
        }

        @Nullable
        @Override
        public View loadingView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_loading, null, false);
            TextView tvLoadingInfo = view.findViewById(R.id.tv_loading_info);
            tvLoadingInfo.setText(loadingInfo);
            return view;
        }

        @Nullable
        @Override
        public View emptyView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_empty, null, false);
            ImageView ivEmptyIcon = view.findViewById(R.id.iv_empty_icon);
            ivEmptyIcon.setImageResource(emptyIcon);
            TextView tvEmptyInfo = view.findViewById(R.id.tv_empty_info);
            tvEmptyInfo.setText(emptyInfo);
            return view;
        }

        @Nullable
        @Override
        public View retryView(Context context) {
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_retry, null, false);
            ImageView ivRetryIcon = view.findViewById(R.id.iv_retry_icon);
            ivRetryIcon.setImageResource(retryIcon);
            TextView tvRetryInfo = view.findViewById(R.id.tv_retry_info);
            tvRetryInfo.setText(retryInfo);
            return view;
        }

        @Nullable
        @Override
        public PageChangeAction getPageChangeAction() {
            return new PageChangeAction() {

            };
        }
    }
}
