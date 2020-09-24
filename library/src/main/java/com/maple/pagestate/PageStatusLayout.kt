package com.maple.pagestate

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.LayoutRes

/**
 * 状态布局
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
class PageStatusLayout : FrameLayout {
    private var contentView: View? = null // 内容页
    private var loadingView: View? = null // loading页
    private var retryView: View? = null // 重试页
    private var emptyView: View? = null // 空数据页

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        isFocusable = true // 获取焦点
        isClickable = true // 防止点击穿透
    }

    fun getContentView() = contentView
    fun getLoadingView() = loadingView
    fun getRetryView() = retryView
    fun getEmptyView() = emptyView

    private fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()

    fun showLoading() {
        if (isMainThread()) {
            showView(loadingView)
        } else {
            post { showView(loadingView) }
        }
    }

    fun showRetry() {
        if (isMainThread()) {
            showView(retryView)
        } else {
            post { showView(retryView) }
        }
    }

    fun showContent() {
        if (isMainThread()) {
            showView(contentView)
        } else {
            post { showView(contentView) }
        }
    }

    fun showEmpty() {
        if (isMainThread()) {
            showView(emptyView)
        } else {
            post { showView(emptyView) }
        }
    }

    private fun showView(view: View?) {
        if (view == null)
            return
        when (view) {
            loadingView -> {
                loadingView?.visibility = View.VISIBLE
                // contentView?.visibility = View.GONE
                emptyView?.visibility = View.GONE
                retryView?.visibility = View.GONE
                pageChangeAction?.onShowLoading(view)
            }
            contentView -> {
                loadingView?.visibility = View.GONE
                contentView?.visibility = View.VISIBLE
                emptyView?.visibility = View.GONE
                retryView?.visibility = View.GONE
                pageChangeAction?.onShowContent(view)
            }
            emptyView -> {
                loadingView?.visibility = View.GONE
                contentView?.visibility = View.GONE
                emptyView?.visibility = View.VISIBLE
                retryView?.visibility = View.GONE
                pageChangeAction?.onShowEmpty(view)
            }
            retryView -> {
                loadingView?.visibility = View.GONE
                contentView?.visibility = View.GONE
                emptyView?.visibility = View.GONE
                retryView?.visibility = View.VISIBLE
                pageChangeAction?.onShowRetry(view)
            }
        }
    }

    private var pageChangeAction: PageChangeAction? = null
    fun setPageStatusChangeAction(action: PageChangeAction) {
        pageChangeAction = action
    }

    fun setLoadingView(@LayoutRes layoutId: Int): View? {
        if (layoutId == 0)
            return null
        return setLoadingView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setLoadingView(view: View?): View? {
        if (view != null) {
            removeView(loadingView)
            addView(view)
            this.loadingView = view
        }
        return this.loadingView
    }

    fun setEmptyView(@LayoutRes layoutId: Int): View? {
        if (layoutId == 0)
            return null
        return setEmptyView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setEmptyView(view: View?): View? {
        if (view != null) {
            removeView(emptyView)
            addView(view)
            this.emptyView = view
        }
        return this.emptyView
    }

    fun setRetryView(@LayoutRes layoutId: Int): View? {
        if (layoutId == 0)
            return null
        return setRetryView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setRetryView(view: View?): View? {
        if (view != null) {
            removeView(retryView)
            addView(view)
            this.retryView = view
        }
        return this.retryView
    }

    fun setContentView(@LayoutRes layoutId: Int): View? {
        if (layoutId == 0)
            return null
        return setContentView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setContentView(view: View?): View? {
        if (view != null) {
            removeView(contentView)
            addView(view)
            this.contentView = view
        }
        return this.contentView
    }

}