package com.maple.pagestate;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


/**
 * 页面状态管理者
 * <p>
 * 核心思想：
 * 针对目标Activity、Fragment、View，新包裹一层ViewGroup {@link PageStateLayout}，
 * 目标View作为contentView，同时新增 loadingView、emptyView、retryView 等状态View.
 * 提供对外方法，控制不同状态view的显示隐藏。
 * <p>
 * 获取目标view(contentView)在父View中的显示位置。
 * 将contentView的layoutParams给PageStateLayout，
 * 同时设置contentView充满父View。实现完整的替换包裹效果。
 *
 * @author : shaoshuai
 * @date ：2020/8/17
 */
public class PageStateManager {
    private PageStateLayout mPageStateLayout;
    private Context mContext;
    private PageConfig mConfig;

    public PageStateManager(Activity activity) {
        this(activity, new PageConfig.Default());
    }

    public PageStateManager(Activity activity, PageConfig pageConfig) {
        this.mContext = activity;
        this.mConfig = pageConfig;
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        initView(contentParent);
    }

    public PageStateManager(Fragment fragment) {
        this(fragment, new PageConfig.Default());
    }

    public PageStateManager(Fragment fragment, PageConfig pageConfig) {
        this.mContext = fragment.requireContext();
        this.mConfig = pageConfig;
        ViewParent contentParent = null;
        View view = fragment.getView();
        if (view != null) {
            contentParent = view.getParent();
        }
        if (contentParent != null) {
            if (contentParent instanceof ViewGroup) {
                initView((ViewGroup) contentParent);
            }
        } else {
            throw new IllegalStateException("请在Fragment.onCreateView之后初始化");
        }
    }

    public PageStateManager(View view) {
        this(view, new PageConfig.Default());
    }

    public PageStateManager(View view, PageConfig pageConfig) {
        this.mContext = view.getContext();
        this.mConfig = pageConfig;
        ViewParent contentParent = view.getParent();
        if (contentParent instanceof ViewGroup) {
            if (contentParent.getClass().getName().contains("SmartRefreshLayout")) {
                throw new IllegalStateException("请避免view的parentView是SmartRefreshLayout，可对目标view包裹一层FrameLayout。");
            }
            int index = Math.max(((ViewGroup) contentParent).indexOfChild(view), 0);// 可能为-1，修正为0
            initView((ViewGroup) contentParent, index);
        }
    }

    private void initView(ViewGroup contentParent) {
        initView(contentParent, 0);
    }
    private void initView(ViewGroup contentParent, int index) {
        View oldContent = contentParent.getChildAt(index);
        ViewGroup.LayoutParams layoutParams = oldContent.getLayoutParams(); // 保存当前layout设置
        contentParent.removeView(oldContent);
        mPageStateLayout = new PageStateLayout(mContext);
        mPageStateLayout.setPageStatusChangeAction(mConfig.getPageChangeListener());
        mPageStateLayout.setContentView(oldContent);

        contentParent.addView(mPageStateLayout, index, layoutParams);

        // 初始化默认配置页面
        setEmptyView(mConfig.emptyView(mContext));
        setRetryView(mConfig.retryView(mContext));
        setLoadingView(mConfig.loadingView(mContext));
    }

    // 各状态图的显示、隐藏
    public void showRetry() {
        mPageStateLayout.showRetry();
    }
    public void showContent() {
        mPageStateLayout.showContent();
    }

    public void dismissLoading() {
        showLoadingView(false);
    }
    public void showLoading() {
        showLoadingView(true);
    }
    public void showLoadingView(boolean isShow) {
        if (isShow) {
            mPageStateLayout.showLoading();
        } else {
            mPageStateLayout.showContent();
        }
    }

    public void showEmpty() {
        showEmptyView(true);
    }
    public void showEmptyView(Boolean isShow) {
        if (isShow) {
            mPageStateLayout.showEmpty();
        } else {
            mPageStateLayout.showContent();
        }
    }

    // 设置状态图 by layoutId
    public void setLoadingView(@LayoutRes int layoutId) {
        mPageStateLayout.setLoadingView(layoutId);
    }
    public void setRetryView(@LayoutRes int layoutId) {
        mPageStateLayout.setRetryView(layoutId);
    }
    public void setEmptyView(@LayoutRes int layoutId) {
        mPageStateLayout.setEmptyView(layoutId);
    }
    public void setContentView(@LayoutRes int layoutId) {
        mPageStateLayout.setContentView(layoutId);
    }

    // 设置状态图 by view
    public void setLoadingView(@Nullable View view) {
        mPageStateLayout.setLoadingView(view);
    }
    public void setRetryView(@Nullable View view) {
        mPageStateLayout.setRetryView(view);
    }
    public void setEmptyView(@Nullable View view) {
        mPageStateLayout.setEmptyView(view);
    }
    public void setContentView(@Nullable View view) {
        mPageStateLayout.setContentView(view);
    }

    // 获取状态图
    @Nullable public View getLoadingView() {
        return mPageStateLayout.getLoadingView();
    }
    @Nullable public View getRetryView() {
        return mPageStateLayout.getRetryView();
    }
    @Nullable public View getEmptyView() {
        return mPageStateLayout.getEmptyView();
    }
    @Nullable public View getContentView() {
        return mPageStateLayout.getContentView();
    }

}
