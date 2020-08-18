package com.maple.pagestatusmanager.utils

import android.view.View

/**
 * 页面状态监听
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
open class OnPageStatusListener {
    open fun setRetryEvent(retryView: View?) {}
    open fun setLoadingEvent(loadingView: View?) {}
    open fun setEmptyEvent(emptyView: View?) {}

    open fun generateLoadingLayoutId(): Int = PageStatusManager.NO_LAYOUT_ID
    open fun generateRetryLayoutId(): Int = PageStatusManager.NO_LAYOUT_ID
    open fun generateEmptyLayoutId(): Int = PageStatusManager.NO_LAYOUT_ID

    open fun generateLoadingLayout(): View? = null
    open fun generateRetryLayout(): View? = null
    open fun generateEmptyLayout(): View? = null

    fun isSetLoadingLayout(): Boolean = generateLoadingLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateLoadingLayout() != null
    fun isSetRetryLayout(): Boolean = generateRetryLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateRetryLayout() != null
    fun isSetEmptyLayout(): Boolean = generateEmptyLayoutId() != PageStatusManager.NO_LAYOUT_ID || generateEmptyLayout() != null
}