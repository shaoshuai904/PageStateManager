package com.maple.demo.config;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.maple.demo.R;
import com.maple.pagestate.PageChangeAction;
import com.maple.pagestate.PageConfig;


/**
 * 自定义奔跑小猪 🐖
 *
 * @author : shaoshuai
 * @date ：2020/08/17
 */
public class PigConfig implements PageConfig {

    public PigConfig() {
    }

    @Nullable
    @Override
    public View loadingView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_loading_pig, null, false);
    }

    @Nullable
    @Override
    public View emptyView(Context context) {
        return LayoutInflater.from(context).inflate(R.layout.custom_empty_2, null, false);
    }

    @Nullable
    @Override
    public View retryView(Context context) {
        // 不想设置的类型，可直接设置为null
        return null;
    }

    @Nullable
    @Override
    public PageChangeAction getPageChangeAction() {
        return new PageChangeAction() {
            @Override
            public void onShowLoading(View loadingView) {
                super.onShowLoading(loadingView);
                ImageView ivLoading = loadingView.findViewById(R.id.iv_loading);
                Drawable bg = ivLoading.getBackground();
                if (bg instanceof AnimationDrawable) {
                    AnimationDrawable ad = (AnimationDrawable) bg;
                    if (!ad.isRunning()) {
                        ad.start();
                    }
                }
            }
        };
    }
}


