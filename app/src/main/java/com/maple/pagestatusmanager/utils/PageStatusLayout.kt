package com.maple.pagestatusmanager.utils

import android.content.Context
import android.os.Looper
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout

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

    companion object {
        private val TAG = PageStatusLayout::class.java.simpleName
    }

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

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
        loadingView?.visibility = if (view == loadingView) View.VISIBLE else View.GONE
        retryView?.visibility = if (view == retryView) View.VISIBLE else View.GONE
        contentView?.visibility = if (view == contentView) View.VISIBLE else View.GONE
        emptyView?.visibility = if (view == emptyView) View.VISIBLE else View.GONE
    }


    fun setContentView(layoutId: Int): View? {
        return setContentView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setLoadingView(layoutId: Int): View? {
        return setLoadingView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setEmptyView(layoutId: Int): View? {
        return setEmptyView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setRetryView(layoutId: Int): View? {
        return setRetryView(LayoutInflater.from(context).inflate(layoutId, this, false))
    }

    fun setLoadingView(view: View?): View? {
        val loadingView = loadingView
        if (loadingView != null) {
            Log.w(TAG, "已设置的LoadingView将被取代")
        }
        removeView(loadingView)
        addView(view)
        this.loadingView = view
        return this.loadingView
    }

    fun setEmptyView(view: View?): View? {
        val emptyView = emptyView
        if (emptyView != null) {
            Log.w(TAG, "you have already set a empty view and would be instead of this new one.")
        }
        removeView(emptyView)
        addView(view)
        this.emptyView = view
        return this.emptyView
    }

    fun setRetryView(view: View?): View? {
        val retryView = retryView
        if (retryView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.")
        }
        removeView(retryView)
        addView(view)
        this.retryView = view
        return this.retryView
    }

    fun setContentView(view: View?): View? {
        val contentView = contentView
        if (contentView != null) {
            Log.w(TAG, "you have already set a retry view and would be instead of this new one.")
        }
        removeView(contentView)
        addView(view)
        this.contentView = view
        return this.contentView
    }

}