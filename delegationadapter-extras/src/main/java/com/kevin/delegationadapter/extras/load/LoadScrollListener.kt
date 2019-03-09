/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.extras.load

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * LoadScrollListener
 *
 * @author zhouwenkai@baidu.com, Created on 2019-03-09 00:08:35
 *         Major Function：<b></b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class LoadScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == RecyclerView.SCROLL_STATE_IDLE && isLastItemVisible(recyclerView)) {
            loadMore()
        }
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        if (recyclerView.scrollState == RecyclerView.SCROLL_STATE_IDLE && lessThanOneScreen(recyclerView)) {
            loadMore()
        }
    }

    /**
     * Whether the last item is visible.
     *
     * @param recyclerView
     * @return
     */
    private fun isLastItemVisible(recyclerView: RecyclerView): Boolean {
        val lastVisiblePosition = getLastVisiblePosition(recyclerView)
        return lastVisiblePosition == recyclerView.adapter.itemCount - 1
    }

    /**
     * 判断是否未满一屏
     *
     * @param recyclerView
     * @return
     */
    private fun lessThanOneScreen(recyclerView: RecyclerView): Boolean {
        val lastVisiblePosition = getLastVisiblePosition(recyclerView)
        return if (isLastItemVisible(recyclerView) && lastVisiblePosition > (if (hasStateView()) 1 else 0)) {
            // 最后一条的底部小于RecyclerView的底部，即未满一屏幕
            recyclerView.getChildAt(recyclerView.childCount - 1).bottom < recyclerView.bottom
        } else {
            false
        }
    }

    /**
     * Returns the last visible position.
     *
     * @param recyclerView
     * @return
     */
    private fun getLastVisiblePosition(recyclerView: RecyclerView): Int {
        val layoutManager = recyclerView.layoutManager
        return if (layoutManager is LinearLayoutManager) {
            layoutManager.findLastVisibleItemPosition()
        } else {
            val lastVisibleChild = recyclerView.getChildAt(recyclerView.childCount - 1)
            if (lastVisibleChild != null) recyclerView.getChildAdapterPosition(lastVisibleChild) else RecyclerView.NO_POSITION
        }
    }

    abstract fun loadMore()

    abstract fun hasStateView(): Boolean
}