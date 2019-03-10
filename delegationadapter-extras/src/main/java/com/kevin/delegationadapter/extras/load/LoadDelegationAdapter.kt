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

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter

/**
 * LoadDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-08 23:39:15
 *         Major Function：<b>DelegationAdapter with load more</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class LoadDelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : SpanDelegationAdapter(hasConsistItemType) {

    private var tailLoadViewType = VIEW_TYPE_LOAD_MORE
    private var tailLoadDelegate: LoadAdapterDelegate? = null
    private val loadScrollListener: LoadScrollListener
    private var onLoadListener: OnLoadListener? = null
    private var onLoadListener2: OnLoadListener2? = null
    private var enabledLoadFromHead = true
    private var enabledLoadFromTail = true

    private var headLoading: Boolean = false
    private var tailLoading: Boolean = false
    private var headLoadCompleted: Boolean = false

    init {
        loadScrollListener = object : LoadScrollListener() {

            override fun loadFromHead() {
                if (!headLoading
                        && enabledLoadFromHead
                        && !headLoadCompleted) {
                    headLoading = true
                    onLoadListener2?.onLoadFromHead()
                }
            }

            override fun loadFromTail() {
                if (tailLoadViewType == VIEW_TYPE_LOAD_FAILED) {
                    setTailLoading()
                }

                if (!tailLoading
                        && enabledLoadFromTail
                        && tailLoadViewType == VIEW_TYPE_LOAD_MORE) {
                    tailLoading = true
                    onLoadListener?.onLoadMore()
                    onLoadListener2?.onLoadFromTail()
                }
            }

            override fun hasTailStateView(): Boolean {
                return hasTailLoadStateView()
            }
        }
    }

    fun setLoadDelegate(delegate: LoadAdapterDelegate): LoadDelegationAdapter {
        return setTailLoadDelegate(delegate)
    }

    fun setTailLoadDelegate(delegate: LoadAdapterDelegate): LoadDelegationAdapter {
        this.tailLoadDelegate = delegate
        return this
    }

    override fun getItemCount(): Int =
            if (hasTailLoadStateView() && enabledLoadFromTail)
                super.getItemCount() + 1
            else
                super.getItemCount()

    override fun getItemViewType(position: Int): Int =
            if (hasTailLoadStateView() && isTailLoadItem(position)) {
                tailLoadViewType
            } else super.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOAD_MORE,
            VIEW_TYPE_NO_MORE,
            VIEW_TYPE_LOAD_FAILED -> tailLoadDelegate!!.onCreateLoadViewHolder(parent, viewType)
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>?) {
        if (hasTailLoadStateView() && isTailLoadItem(position)) {
            tailLoadDelegate!!.onBindViewHolder(holder as LoadViewHolder, position)

            if (holder.getItemViewType() == VIEW_TYPE_LOAD_FAILED) {
                holder.itemView.setOnClickListener {
                    setTailLoading()
                    onLoadListener?.onLoadMore()
                    onLoadListener2?.onLoadFromTail()
                }
            }
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        if (holder != null && hasTailLoadStateView() && isTailLoadItem(holder.layoutPosition)) {
            val layoutParams = holder.itemView.layoutParams
            // When StaggeredGridLayoutManager, the Load View takes up one line;
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        } else {
            super.onViewAttachedToWindow(holder)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        if (hasTailLoadStateView() && recyclerView != null) {
            val layoutManager = recyclerView.layoutManager
            // When GridLayoutManager, the Load View takes up one line;
            if (layoutManager is GridLayoutManager) {
                val spanSizeLookup = layoutManager.spanSizeLookup
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        if (isTailLoadItem(position)) {
                            return layoutManager.spanCount
                        }
                        return spanSizeLookup.getSpanSize(position)
                    }
                }
                layoutManager.spanCount = layoutManager.spanCount
            }
        }

        recyclerView?.addOnScrollListener(loadScrollListener)
    }

    fun finishLoading() {
        finishTailLoading()
    }

    fun finishHeadLoading() {
        this.headLoading = false
    }

    fun finishTailLoading() {
        this.tailLoading = false
    }

    fun setLoading() {
        setTailLoading()
    }

    fun setTailLoading() {
        tailLoadViewType = VIEW_TYPE_LOAD_MORE
        tailLoading = false
        if (!enabledLoadFromTail) {
            enabledLoadFromTail = true
            notifyDataSetChanged()
        } else {
            notifyItemChanged(itemCount)
        }
    }

    fun setLoadFailed() {
        setTailLoadFailed()
    }

    fun setTailLoadFailed() {
        tailLoadViewType = VIEW_TYPE_LOAD_FAILED
        tailLoading = false
        enabledLoadFromTail = true
        notifyItemChanged(itemCount)
    }

    fun setLoadCompleted() {
        setTailLoadCompleted()
    }

    fun setHeadLoadCompleted() {
        this.headLoadCompleted = true
        this.headLoading = false
    }

    fun setTailLoadCompleted() {
        tailLoadViewType = VIEW_TYPE_NO_MORE
        tailLoading = false
        enabledLoadFromTail = true
        notifyItemChanged(itemCount)
    }

    fun disableLoad() {
        disableTailLoad()
    }

    fun disableTailLoad() {
        tailLoadViewType = VIEW_TYPE_NO_VIEW
        tailLoading = false
        enabledLoadFromTail = false
        notifyDataSetChanged()
    }

    /**
     * Returns whether has extra load item.
     */
    private fun hasTailLoadStateView(): Boolean {
        return tailLoadDelegate != null
    }

    /**
     * Returns whether is the load states item.
     */
    private fun isTailLoadItem(position: Int): Boolean {
        return enabledLoadFromTail && position == itemCount - 1
    }

    companion object {
        const val VIEW_TYPE_LOAD_MORE = Integer.MAX_VALUE - 1
        const val VIEW_TYPE_NO_MORE = Integer.MAX_VALUE - 2
        const val VIEW_TYPE_LOAD_FAILED = Integer.MAX_VALUE - 3
        const val VIEW_TYPE_NO_VIEW = Integer.MAX_VALUE - 3
    }

    fun setOnLoadListener(listener: OnLoadListener) {
        this.onLoadListener = listener
    }

    fun setOnLoadListener2(listener: OnLoadListener2) {
        this.onLoadListener2 = listener
    }

    interface OnLoadListener {
        fun onLoadMore()
    }

    interface OnLoadListener2 {
        fun onLoadFromHead()
        fun onLoadFromTail()
    }

}