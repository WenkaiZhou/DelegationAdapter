/*
 * Copyright (c) 2019 Kevin zhou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kevin.delegationadapter.extras.load

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * LoadScrollListener
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-09 00:08:35
 *         Major Function：<b></b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class LoadScrollListener : RecyclerView.OnScrollListener() {

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        if (newState == RecyclerView.SCROLL_STATE_IDLE && isFirstItemVisible(recyclerView)) {
            refresh()
        }

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

    private fun isFirstItemVisible(recyclerView: RecyclerView): Boolean {
        return getFirstVisiblePosition(recyclerView) == 0
    }

    private fun getFirstVisiblePosition(recyclerView: RecyclerView): Int {
        val firstVisibleChild = recyclerView.getChildAt(0)
        return if (firstVisibleChild != null)
            recyclerView.getChildAdapterPosition(firstVisibleChild)
        else
            RecyclerView.NO_POSITION
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

    abstract fun refresh()

    abstract fun loadMore()

    abstract fun hasStateView(): Boolean
}