package com.kevin.delegationadapter.sample.multidataandtype.binding

import android.graphics.Rect
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * SpaceItemDecoration
 *
 * @author zhouwenkai@baidu.com, Created on 2019-12-17 19:48:12
 *         Major Function：<b></b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class SpaceItemDecoration : RecyclerView.ItemDecoration() {

    private val oneSideSpaces: MutableList<OneSideSpace> = mutableListOf()

    fun sideSpace(viewType: Int, spacePx: Int): SpaceItemDecoration {
        oneSideSpaces.add(OneSideSpace(viewType, spacePx))
        return this
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)
        if (position == RecyclerView.NO_POSITION || view.visibility == View.GONE || parent.adapter == null) {
            return
        }

        val viewType = parent.adapter!!.getItemViewType(position)

        // 左右边距设置
        oneSideSpaces.forEach { oneSideSpace ->
            if (oneSideSpace.viewType == viewType) {
                if (isLeftSide(view, parent)) {
                    outRect.set(oneSideSpace.spacePx, 0, 0, 0)
                } else if (isRightSide(view, parent)) {
                    outRect.set(0, 0, oneSideSpace.spacePx, 0)
                }
            }
        }
    }

    /**
     * 判断是否是最左侧条目
     *
     * @param view
     * @param parent
     */
    private fun isLeftSide(view: View, parent: RecyclerView): Boolean {
        val layoutManager = parent.layoutManager
        return if (layoutManager !is GridLayoutManager) {
            true
        } else {
            val spanIndex = layoutManager.spanSizeLookup
                    .getSpanIndex(parent.getChildAdapterPosition(view), layoutManager.spanCount)
            spanIndex == 0
        }
    }

    /**
     * 判断是否是最右侧条目
     *
     * @param view
     * @param parent
     */
    private fun isRightSide(view: View, parent: RecyclerView): Boolean {
        val layoutManager = parent.layoutManager
        return if (layoutManager !is GridLayoutManager) {
            true
        } else {
            val spanIndex = layoutManager.spanSizeLookup
                    .getSpanIndex(parent.getChildAdapterPosition(view), layoutManager.spanCount)
            spanIndex == layoutManager.spanCount - 1
        }
    }

    /**
     * 左右编辑实体类
     */
    private class OneSideSpace internal constructor(val viewType: Int, val spacePx: Int)
}