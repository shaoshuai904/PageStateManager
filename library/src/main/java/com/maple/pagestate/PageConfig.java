package com.maple.pagestate;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

/**
 * 页面配置
 *
 * @author : shaoshuai
 * @date ：2020/11/13
 */
public interface PageConfig {
    // 加载中 视图
    @Nullable View loadingView(Context context);
    // 空数据 视图
    @Nullable View emptyView(Context context);
    // 重试 视图
    @Nullable View retryView(Context context);
    // 页面状态变化监听
    PageChangeListener getPageChangeListener();

    /**
     * 默认配置不满足需求的，请自定义配置
     */
    class Default implements PageConfig {
        @DrawableRes int loadingIconRes = R.drawable.ms_layer_loading;
        CharSequence loadingInfo = "loading";
        @DrawableRes int emptyIconRes = R.drawable.ms_status_no_data;
        CharSequence emptyInfo = "暂无数据";
        @DrawableRes int retryIconRes = R.drawable.ms_status_no_net;
        CharSequence retryInfo = "加载失败，请重试~";
        PageChangeListener pageChangeListener = null;

        public Default() {}

        public Default(
                @DrawableRes int loadingIconRes, CharSequence loadingInfo,
                @DrawableRes int emptyIconRes, CharSequence emptyInfo,
                @DrawableRes int retryIconRes, CharSequence retryInfo,
                @Nullable PageChangeListener pageChangeListener
        ) {
            this.loadingIconRes = loadingIconRes;
            this.loadingInfo = loadingInfo;
            this.emptyIconRes = emptyIconRes;
            this.emptyInfo = emptyInfo;
            this.retryIconRes = retryIconRes;
            this.retryInfo = retryInfo;
            this.pageChangeListener = pageChangeListener;
        }

        @Nullable
        @Override
        public View loadingView(Context context) {
            if (loadingIconRes == 0 && TextUtils.isEmpty(loadingInfo)) {
                // 不想设置的类型，可直接设置为null
                return null;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_loading, null, false);
            ProgressBar tvLoadingIcon = view.findViewById(R.id.pb_loading_icon);
            tvLoadingIcon.setIndeterminateDrawable(ContextCompat.getDrawable(context, loadingIconRes));
            TextView tvLoadingInfo = view.findViewById(R.id.tv_loading_info);
            tvLoadingInfo.setText(loadingInfo);
            return view;
        }

        @Nullable
        @Override
        public View emptyView(Context context) {
            if (emptyIconRes == 0 && TextUtils.isEmpty(emptyInfo)) {
                // 不想设置的类型，可直接设置为null
                return null;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_empty, null, false);
            ImageView ivEmptyIcon = view.findViewById(R.id.iv_empty_icon);
            ivEmptyIcon.setImageResource(emptyIconRes);
            TextView tvEmptyInfo = view.findViewById(R.id.tv_empty_info);
            tvEmptyInfo.setText(emptyInfo);
            return view;
        }

        @Nullable
        @Override
        public View retryView(Context context) {
            if (retryIconRes == 0 && TextUtils.isEmpty(retryInfo)) {
                // 不想设置的类型，可直接设置为null
                return null;
            }
            View view = LayoutInflater.from(context).inflate(R.layout.ms_base_retry, null, false);
            ImageView ivRetryIcon = view.findViewById(R.id.iv_retry_icon);
            ivRetryIcon.setImageResource(retryIconRes);
            TextView tvRetryInfo = view.findViewById(R.id.tv_retry_info);
            tvRetryInfo.setText(retryInfo);
            return view;
        }

        @Nullable
        @Override
        public PageChangeListener getPageChangeListener() {
            return pageChangeListener;
        }
    }
}
