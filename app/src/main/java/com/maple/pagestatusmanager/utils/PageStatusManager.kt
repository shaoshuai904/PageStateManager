package com.maple.pagestatusmanager.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import kotlin.math.max

/**
 * 页面状态管理者
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
class PageStatusManager {
    private lateinit var mPageStatusLayout: PageStatusLayout
    private lateinit var mContext: Context

    companion object {
        const val NO_LAYOUT_ID = 0

        @JvmField
        @LayoutRes
        var BASE_LOADING_LAYOUT_ID: Int = NO_LAYOUT_ID

        @JvmField
        @LayoutRes
        var BASE_RETRY_LAYOUT_ID: Int = NO_LAYOUT_ID

        @JvmField
        @LayoutRes
        var BASE_EMPTY_LAYOUT_ID: Int = NO_LAYOUT_ID
    }


    constructor(activity: Activity) {
        this.mContext = activity
        val contentParent = activity.findViewById(android.R.id.content) as ViewGroup
        initView(contentParent)
    }

    constructor(fragment: Fragment) {
        this.mContext = fragment.requireContext()
        val contentParent = fragment.view?.parent as ViewGroup
        initView(contentParent)
    }

    constructor(view: View) {
        this.mContext = view.context
        val contentParent = view.parent as ViewGroup
        val index = max(contentParent.indexOfChild(view), 0)// 可能为-1，修正为0
        initView(contentParent, index)
    }

    private fun initView(contentParent: ViewGroup, index: Int = 0) {
        val oldContent = contentParent.getChildAt(index)
        contentParent.removeView(oldContent)
        mPageStatusLayout = PageStatusLayout(mContext)
        mPageStatusLayout.setContentView(oldContent)
        contentParent.addView(mPageStatusLayout, index, oldContent.layoutParams)

        // 初始化默认配置页面
        setLoadingView(BASE_LOADING_LAYOUT_ID)
        setRetryView(BASE_RETRY_LAYOUT_ID)
        setEmptyView(BASE_EMPTY_LAYOUT_ID)
    }

    // 设置页面回调
    fun setPageCallBack(mConfig: PageCallBack = PageCallBack()): PageStatusManager {
        mConfig.setRetryEvent(mPageStatusLayout.getRetryView())
        mConfig.setLoadingEvent(mPageStatusLayout.getLoadingView())
        mConfig.setEmptyEvent(mPageStatusLayout.getEmptyView())
        return this
    }

    open class PageCallBack {
        open fun setRetryEvent(retryView: View?) {}
        open fun setLoadingEvent(loadingView: View?) {}
        open fun setEmptyEvent(emptyView: View?) {}
    }

    // 各状态图的显示、隐藏
    fun dismissLoading() = showContent()
    fun showLoading() = mPageStatusLayout.showLoading()
    fun showRetry() = mPageStatusLayout.showRetry()
    fun showContent() = mPageStatusLayout.showContent()
    fun showEmpty() = mPageStatusLayout.showEmpty()

    // 设置状态图 by layoutId
    fun setLoadingView(@LayoutRes layoutId: Int) = mPageStatusLayout.setLoadingView(layoutId)
    fun setRetryView(@LayoutRes layoutId: Int) = mPageStatusLayout.setRetryView(layoutId)
    fun setEmptyView(@LayoutRes layoutId: Int) = mPageStatusLayout.setEmptyView(layoutId)
    fun setContentView(@LayoutRes layoutId: Int) = mPageStatusLayout.setContentView(layoutId)

    // 设置状态图 by view
    fun setLoadingView(view: View?) = mPageStatusLayout.setLoadingView(view)
    fun setRetryView(view: View?) = mPageStatusLayout.setRetryView(view)
    fun setEmptyView(view: View?) = mPageStatusLayout.setEmptyView(view)
    fun setContentView(view: View?) = mPageStatusLayout.setContentView(view)

}