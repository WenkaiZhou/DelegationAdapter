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
import com.kevin.delegationadapter.DelegationAdapter
import com.kevin.delegationadapter.extras.span.SpanAdapterDelegate

/**
 * LoadDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-08 23:39:15
 *         Major Function：<b>DelegationAdapter with load more</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class LoadDelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : DelegationAdapter(hasConsistItemType) {

    private var loadViewType = VIEW_TYPE_LOADING
    private var loadDelegate: LoadAdapterDelegate? = null
    private val scrollListener: ScrollListener
    private var onLoadListener: OnLoadListener? = null
    private var enabledLoad = false

    var loading: Boolean = false

    init {
        scrollListener = object : ScrollListener() {

            override fun loadMore() {
                if (loadViewType == VIEW_TYPE_LOAD_FAILED) {
                    setLoading()
                }

                if (!loading
                        && enabledLoad
                        && onLoadListener != null
                        && loadViewType == VIEW_TYPE_LOADING) {
                    loading = true
                    onLoadListener?.onLoadMore()
                }
            }

            override fun hasStateView(): Boolean {
                return hasLoadStateView()
            }
        }
    }

    fun setLoadDelegate(delegate: LoadAdapterDelegate): LoadDelegationAdapter {
        this.loadDelegate = delegate
        return this
    }

    override fun getItemCount(): Int =
            if (hasLoadStateView() && enabledLoad)
                super.getItemCount() + 1
            else
                super.getItemCount()

    override fun getItemViewType(position: Int): Int =
            if (hasLoadStateView() && isLoadStateItem(position)) {
                loadViewType
            } else super.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOADING,
            VIEW_TYPE_NO_MORE,
            VIEW_TYPE_LOAD_FAILED -> loadDelegate!!.onCreateLoadViewHolder(parent, viewType)
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>?) {
        if (hasLoadStateView() && isLoadStateItem(position)) {
            loadDelegate!!.onBindViewHolder(holder, position)

            if (holder.getItemViewType() == VIEW_TYPE_LOAD_FAILED) {
                holder.itemView.setOnClickListener {
                    setLoading()
                    onLoadListener?.onLoadMore()
                }
            }
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        if (holder != null && hasLoadStateView() && isLoadStateItem(holder.layoutPosition)) {
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
        super.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView?.layoutManager
        if (layoutManager is GridLayoutManager) {

            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (hasLoadStateView() && isLoadStateItem(position)) {
                        return layoutManager.spanCount
                    }

                    val delegate = delegatesManager.getDelegate(getItemViewType(position))
                    return if (null != delegate && delegate is SpanAdapterDelegate) {
                        delegate.spanSize
                    } else {
                        layoutManager.spanCount
                    }
                }
            }
        }

        recyclerView?.addOnScrollListener(scrollListener)
    }

    fun reset() {
        setLoading()
    }

    private fun setLoading() {
        loadViewType = VIEW_TYPE_LOADING
        loading = false
        if (!enabledLoad) {
            enabledLoad = true
            notifyDataSetChanged()
        } else {
            notifyItemChanged(itemCount)
        }
    }

    fun setLoadFailed() {
        loadViewType = VIEW_TYPE_LOAD_FAILED
        loading = false
        enabledLoad = true
        notifyItemChanged(itemCount)
    }

    fun setLoadCompleted() {
        loadViewType = VIEW_TYPE_NO_MORE
        loading = false
        enabledLoad = true
        notifyItemChanged(itemCount)
    }

    fun disableLoad() {
        loadViewType = VIEW_TYPE_NO_VIEW
        loading = false
        enabledLoad = false
        notifyDataSetChanged()
    }

    /**
     * Returns whether has extra load item.
     */
    private fun hasLoadStateView(): Boolean {
        return loadDelegate != null
    }

    /**
     * Returns whether is the load state item. 加载状态条目
     */
    private fun isLoadStateItem(position: Int): Boolean {
        return enabledLoad && position == itemCount - 1
    }

    companion object {
        const val VIEW_TYPE_LOADING = Integer.MAX_VALUE - 1
        const val VIEW_TYPE_NO_MORE = Integer.MAX_VALUE - 2
        const val VIEW_TYPE_LOAD_FAILED = Integer.MAX_VALUE - 3
        const val VIEW_TYPE_NO_VIEW = Integer.MAX_VALUE - 3
    }

    fun setOnLoadListener(listener: OnLoadListener) {
        this.enabledLoad = true
        this.onLoadListener = listener
    }

    interface OnLoadListener {
        fun onLoadMore()
    }
}