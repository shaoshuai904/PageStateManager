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
 * 针对目标Activity、Fragment、View，新包裹一层ViewGroup {@link PageStatusLayout}，
 * 目标View作为contentView，同时新增 loadingView、emptyView、retryView 等状态View.
 * 提供对外方法，控制不同状态view的显示隐藏。
 * <p>
 * 获取目标view(contentView)在父View中的显示位置。
 * 将contentView的layoutParams给PageStatusLayout，
 * 同时设置contentView充满父View。实现完整的替换包裹效果。
 *
 * @author : shaoshuai
 * @date ：2020/8/17
 */
public class PageStatusManager {
    private PageStatusLayout mPageStatusLayout;
    private Context mContext;
    private PageConfig mConfig;

    public PageStatusManager(Activity activity) {
        this(activity, new PageConfig.Default());
    }

    public PageStatusManager(Activity activity, PageConfig pageConfig) {
        this.mContext = activity;
        this.mConfig = pageConfig;
        ViewGroup contentParent = activity.findViewById(android.R.id.content);
        initView(contentParent);
    }

    public PageStatusManager(Fragment fragment) {
        this(fragment, new PageConfig.Default());
    }

    public PageStatusManager(Fragment fragment, PageConfig pageConfig) {
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

    public PageStatusManager(View view) {
        this(view, new PageConfig.Default());
    }

    public PageStatusManager(View view, PageConfig pageConfig) {
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
        mPageStatusLayout = new PageStatusLayout(mContext);
        mPageStatusLayout.setPageStatusChangeAction(mConfig.getPageChangeAction());
        mPageStatusLayout.setContentView(oldContent);

        contentParent.addView(mPageStatusLayout, index, layoutParams);

        // 初始化默认配置页面
//        setLoadingView(mConfig.loadingLayoutId());
//        setEmptyView(mConfig.emptyLayoutId());
//        setRetryView(mConfig.retryLayoutId());
        setLoadingView(mConfig.loadingView(mContext));
        setEmptyView(mConfig.emptyView(mContext));
        setRetryView(mConfig.retryView(mContext));
    }

    // 各状态图的显示、隐藏
    public void showRetry() {
        mPageStatusLayout.showRetry();
    }
    public void showContent() {
        mPageStatusLayout.showContent();
    }

    public void dismissLoading() {
        showLoadingView(false);
    }
    public void showLoading() {
        showLoadingView(true);
    }
    public void showLoadingView(boolean isShow) {
        if (isShow) {
            mPageStatusLayout.showLoading();
        } else {
            mPageStatusLayout.showContent();
        }
    }

    public void showEmpty() {
        showEmptyView(true);
    }
    public void showEmptyView(Boolean isShow) {
        if (isShow) {
            mPageStatusLayout.showEmpty();
        } else {
            mPageStatusLayout.showContent();
        }
    }

    // 设置状态图 by layoutId
    public void setLoadingView(@LayoutRes int layoutId) {
        mPageStatusLayout.setLoadingView(layoutId);
    }
    public void setRetryView(@LayoutRes int layoutId) {
        mPageStatusLayout.setRetryView(layoutId);
    }
    public void setEmptyView(@LayoutRes int layoutId) {
        mPageStatusLayout.setEmptyView(layoutId);
    }
    public void setContentView(@LayoutRes int layoutId) {
        mPageStatusLayout.setContentView(layoutId);
    }

    // 设置状态图 by view
    public void setLoadingView(@Nullable View view) {
        mPageStatusLayout.setLoadingView(view);
    }
    public void setRetryView(@Nullable View view) {
        mPageStatusLayout.setRetryView(view);
    }
    public void setEmptyView(@Nullable View view) {
        mPageStatusLayout.setEmptyView(view);
    }
    public void setContentView(@Nullable View view) {
        mPageStatusLayout.setContentView(view);
    }

    // 获取状态图
    @Nullable public View getLoadingView() {
        return mPageStatusLayout.getLoadingView();
    }
    @Nullable public View getRetryView() {
        return mPageStatusLayout.getRetryView();
    }
    @Nullable public View getEmptyView() {
        return mPageStatusLayout.getEmptyView();
    }
    @Nullable public View getContentView() {
        return mPageStatusLayout.getContentView();
    }

}
