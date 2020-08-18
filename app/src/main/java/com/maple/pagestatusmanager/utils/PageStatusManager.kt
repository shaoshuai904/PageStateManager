package com.maple.pagestatusmanager.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlin.math.max

/**
 * 页面状态管理者
 *
 * @author : shaoshuai27
 * @date ：2020/8/17
 */
class PageStatusManager(
    private val activityOrFragmentOrView: Any?,
    private val listener: OnPageStatusListener = OnPageStatusListener()
) {
    private var mPageStatusLayout: PageStatusLayout

    companion object {
        const val NO_LAYOUT_ID = 0

        @JvmField
        var BASE_LOADING_LAYOUT_ID = NO_LAYOUT_ID

        @JvmField
        var BASE_RETRY_LAYOUT_ID = NO_LAYOUT_ID

        @JvmField
        var BASE_EMPTY_LAYOUT_ID = NO_LAYOUT_ID
    }


    init {
        val context: Context?
        val contentParent: ViewGroup
        val oldContent: View
        var index = 0
        when (activityOrFragmentOrView) {
            is Activity -> {
                context = this.activityOrFragmentOrView
                contentParent = activityOrFragmentOrView.findViewById(android.R.id.content) as ViewGroup
                oldContent = contentParent.getChildAt(0)
            }
            is Fragment -> {
                context = activityOrFragmentOrView.activity
                contentParent = activityOrFragmentOrView.view?.parent as ViewGroup
                oldContent = contentParent.getChildAt(0)
            }
            is View -> {
                contentParent = activityOrFragmentOrView.parent as ViewGroup
                context = activityOrFragmentOrView.context
                oldContent = activityOrFragmentOrView
                index = contentParent.indexOfChild(oldContent)
            }
            else -> {
                throw IllegalArgumentException("the argument's type must be Fragment or Activity: init(context)")
            }
        }


        contentParent.removeView(oldContent)
        val pageStatusLayout = PageStatusLayout(context!!)
        // 可能为-1，修正为0
        contentParent.addView(pageStatusLayout, max(index, 0), oldContent.layoutParams)
        pageStatusLayout.setContentView(oldContent)
        setupLoadingLayout(listener, pageStatusLayout)
        setupRetryLayout(listener, pageStatusLayout)
        setupEmptyLayout(listener, pageStatusLayout)
        listener.setRetryEvent(pageStatusLayout.getRetryView())
        listener.setLoadingEvent(pageStatusLayout.getLoadingView())
        listener.setEmptyEvent(pageStatusLayout.getEmptyView())
        mPageStatusLayout = pageStatusLayout
    }

    private fun setupEmptyLayout(listener: OnPageStatusListener?, pageStatusLayout: PageStatusLayout) {
        if (listener?.isSetEmptyLayout() == true) {
            val layoutId = listener.generateEmptyLayoutId()
            if (layoutId != NO_LAYOUT_ID) {
                pageStatusLayout.setEmptyView(layoutId)
            } else {
                pageStatusLayout.setEmptyView(listener.generateEmptyLayout())
            }
        } else {
            if (BASE_EMPTY_LAYOUT_ID != NO_LAYOUT_ID) pageStatusLayout.setEmptyView(BASE_EMPTY_LAYOUT_ID)
        }
    }

    private fun setupLoadingLayout(listener: OnPageStatusListener?, pageStatusLayout: PageStatusLayout) {
        if (listener!!.isSetLoadingLayout()) {
            val layoutId = listener.generateLoadingLayoutId()
            if (layoutId != NO_LAYOUT_ID) {
                pageStatusLayout.setLoadingView(layoutId)
            } else {
                pageStatusLayout.setLoadingView(listener.generateLoadingLayout())
            }
        } else {
            if (BASE_LOADING_LAYOUT_ID != NO_LAYOUT_ID) pageStatusLayout.setLoadingView(BASE_LOADING_LAYOUT_ID)
        }
    }

    private fun setupRetryLayout(listener: OnPageStatusListener?, pageStatusLayout: PageStatusLayout) {
        if (listener!!.isSetRetryLayout()) {
            val layoutId = listener.generateRetryLayoutId()
            if (layoutId != NO_LAYOUT_ID) {
                pageStatusLayout.setLoadingView(layoutId)
            } else {
                pageStatusLayout.setLoadingView(listener.generateRetryLayout())
            }
        } else {
            if (BASE_RETRY_LAYOUT_ID != NO_LAYOUT_ID) pageStatusLayout.setRetryView(BASE_RETRY_LAYOUT_ID)
        }
    }

    fun showLoading() {
        mPageStatusLayout.showLoading()
    }

    fun showRetry() {
        mPageStatusLayout.showRetry()
    }

    fun showContent() {
        mPageStatusLayout.showContent()
    }

    fun showEmpty() {
        mPageStatusLayout.showEmpty()
    }


}