package com.maple.pagestate;

import android.content.Context;
import android.os.Looper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


/**
 * 状态布局
 *
 * @author : shaoshuai
 * @date ：2020/8/17
 */
public class PageStateLayout extends FrameLayout {
    private View contentView = null; // 内容页
    private View loadingView = null;// loading页
    private View retryView = null;// 重试页
    private View emptyView = null;// 空数据页o

    public PageStateLayout(@NonNull Context context) {
        super(context);
        init();
    }

    public PageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PageStateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true); // 获取焦点
        setClickable(true);  // 防止点击穿透
    }

    @Nullable View getContentView() {
        return contentView;
    }
    @Nullable View getLoadingView() {
        return loadingView;
    }
    @Nullable View getRetryView() {
        return retryView;
    }
    @Nullable View getEmptyView() {
        return emptyView;
    }

    private boolean isMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public void showLoading() {
        if (isMainThread()) {
            showView(loadingView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(loadingView);
                }
            });
        }
    }

    public void showRetry() {
        if (isMainThread()) {
            showView(retryView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(retryView);
                }
            });
        }
    }

    public void showContent() {
        if (isMainThread()) {
            showView(contentView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(contentView);
                }
            });
        }
    }

    public void showEmpty() {
        if (isMainThread()) {
            showView(emptyView);
        } else {
            post(new Runnable() {
                @Override
                public void run() {
                    showView(emptyView);
                }
            });
        }
    }

    private void showView(@Nullable View view) {
        if (view == null)
            return;
        if (view == loadingView) {
            if (loadingView != null) loadingView.setVisibility(View.VISIBLE);
            // loading的时候 显示底层View
            // if (contentView != null) contentView.setVisibility(View.GONE);
            if (emptyView != null) emptyView.setVisibility(GONE);
            if (retryView != null) retryView.setVisibility(GONE);
            if (pageChangeAction != null) pageChangeAction.onShowLoading(view);
        } else if (view == contentView) {
            if (loadingView != null) loadingView.setVisibility(View.GONE);
            if (contentView != null) contentView.setVisibility(View.VISIBLE);
            if (emptyView != null) emptyView.setVisibility(GONE);
            if (retryView != null) retryView.setVisibility(GONE);
            if (pageChangeAction != null) pageChangeAction.onShowContent(view);
        } else if (view == emptyView) {
            if (loadingView != null) loadingView.setVisibility(View.GONE);
            if (contentView != null) contentView.setVisibility(View.GONE);
            if (emptyView != null) emptyView.setVisibility(VISIBLE);
            if (retryView != null) retryView.setVisibility(GONE);
            if (pageChangeAction != null) pageChangeAction.onShowEmpty(view);
        } else if (view == retryView) {
            if (loadingView != null) loadingView.setVisibility(View.GONE);
            if (contentView != null) contentView.setVisibility(View.GONE);
            if (emptyView != null) emptyView.setVisibility(GONE);
            if (retryView != null) retryView.setVisibility(VISIBLE);
            if (pageChangeAction != null) pageChangeAction.onShowRetry(view);
        }
    }

    private PageChangeListener pageChangeAction = null;
    // 设置页面变化监听
    public void setPageStatusChangeAction(@Nullable PageChangeListener action) {
        pageChangeAction = action;
    }

    @Nullable
    private View getViewByLayoutId(@LayoutRes int layoutId) {
        View view = null;
        if (layoutId != 0) {
            view = LayoutInflater.from(getContext()).inflate(layoutId, this, false);
        }
        return view;
    }

    @Nullable
    public View setLoadingView(@LayoutRes int layoutId) {
        return setLoadingView(getViewByLayoutId(layoutId));
    }

    @Nullable
    public View setLoadingView(@Nullable View view) {
        if (loadingView != null) removeView(loadingView);
        if (view != null) addView(view);
        this.loadingView = view;
        return this.loadingView;
    }

    @Nullable
    public View setEmptyView(@LayoutRes int layoutId) {
        return setEmptyView(getViewByLayoutId(layoutId));
    }

    @Nullable
    public View setEmptyView(@Nullable View view) {
        if (emptyView != null) removeView(emptyView);
        if (view != null) addView(view);
        this.emptyView = view;
        return this.emptyView;
    }

    @Nullable
    public View setRetryView(@LayoutRes int layoutId) {
        return setRetryView(getViewByLayoutId(layoutId));
    }

    @Nullable
    public View setRetryView(@Nullable View view) {
        if (retryView != null) removeView(retryView);
        if (view != null) addView(view);
        this.retryView = view;
        return this.retryView;
    }

    @Nullable
    public View setContentView(@LayoutRes int layoutId) {
        return setContentView(getViewByLayoutId(layoutId));
    }

    @Nullable
    public View setContentView(@Nullable View view) {
        if (contentView != null) removeView(contentView);
        if (view != null) {
            // contentView 把自己的 layoutParams 给了 PageStateLayout，此时设置contentView充满父View
            addView(view, -1, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        this.contentView = view;
        return this.contentView;
    }

}
