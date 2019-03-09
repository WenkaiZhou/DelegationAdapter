/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.extras.load

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter

/**
 * LoadMoreDelegationAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2019-03-08 23:39:15
 *         Major Function：<b>DelegationAdapter with load more</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class LoadDelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : SpanDelegationAdapter(hasConsistItemType) {

    private var loadDelegate: LoadAdapterDelegate? = null

    private val loadScrollListener: LoadScrollListener
    private var mOnLoadListener: OnLoadListener? = null

    private var enabledLoad = true
    private var currentItemType = VIEW_TYPE_LOAD_MORE

    /**
     * Whether it is loading
     */
    var loading: Boolean = false

    init {
        loadScrollListener = object : LoadScrollListener() {
            override fun loadMore() {
                if (currentItemType == VIEW_TYPE_LOAD_FAILED) {
                    showLoading()
                }

                if (!loading
                        && enabledLoad
                        && mOnLoadListener != null
                        && currentItemType == VIEW_TYPE_LOAD_MORE) {
                    mOnLoadListener?.onLoadMore()
                }
            }

            override fun hasStateView(): Boolean {
                return hasLoadStatesView()
            }
        }
    }

    fun setLoadDelegate(delegate: LoadAdapterDelegate) {
        this.loadDelegate = delegate
    }

    override fun getItemCount(): Int =
            if (hasLoadStatesView() && enabledLoad)
                super.getItemCount() + 1
            else
                super.getItemCount()

    override fun getItemViewType(position: Int): Int =
            if (hasLoadStatesView() && isLoadStatesItem(position)) {
                currentItemType
            } else super.getItemViewType(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_LOAD_MORE,
            VIEW_TYPE_NO_MORE,
            VIEW_TYPE_LOAD_FAILED -> loadDelegate!!.onCreateLoadViewHolder(parent, viewType)
            else -> super.onCreateViewHolder(parent, viewType)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>?) {
        if (hasLoadStatesView() && isLoadStatesItem(position)) {
            loadDelegate!!.onBindViewHolder(holder as LoadViewHolder, position)

            if (holder.getItemViewType() == VIEW_TYPE_LOAD_FAILED) {
                holder.itemView.setOnClickListener {
                    mOnLoadListener?.onRetry()
                }
            }
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        if (holder != null && hasLoadStatesView() && isLoadStatesItem(holder.layoutPosition)) {
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
        if (hasLoadStatesView() && recyclerView != null) {
            val layoutManager = recyclerView.layoutManager
            // When GridLayoutManager, the Load View takes up one line;
            if (layoutManager is GridLayoutManager) {
                val spanSizeLookup = layoutManager.spanSizeLookup
                layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                    override fun getSpanSize(position: Int): Int {
                        if (isLoadStatesItem(position)) {
                            return layoutManager.spanCount
                        }
                        return if (enabledLoad) {
                            spanSizeLookup.getSpanSize(position)
                        } else 1
                    }
                }
                layoutManager.spanCount = layoutManager.spanCount
            }
        }

        recyclerView?.addOnScrollListener(loadScrollListener)
    }

    fun showLoading() {
        currentItemType = VIEW_TYPE_LOAD_MORE
        if (!enabledLoad) {
            enabledLoad = true
            notifyDataSetChanged()
        } else {
            notifyItemChanged(itemCount)
        }
    }

    fun showLoadFailed() {
        currentItemType = VIEW_TYPE_LOAD_FAILED
        enabledLoad = true
        notifyItemChanged(itemCount)
    }

    fun showLoadComplete() {
        currentItemType = VIEW_TYPE_NO_MORE
        enabledLoad = true
        notifyItemChanged(itemCount)
    }

    fun disableLoad() {
        currentItemType = VIEW_TYPE_NO_VIEW
        enabledLoad = false
        notifyDataSetChanged()
    }

    /**
     * Returns whether has extra load item.
     */
    private fun hasLoadStatesView(): Boolean {
        return loadDelegate != null
    }

    /**
     * Returns whether is the load states item.
     */
    private fun isLoadStatesItem(position: Int): Boolean {
        return enabledLoad && position == itemCount - 1
    }

    companion object {
        const val VIEW_TYPE_LOAD_MORE = Integer.MAX_VALUE - 1
        const val VIEW_TYPE_NO_MORE = Integer.MAX_VALUE - 2
        const val VIEW_TYPE_LOAD_FAILED = Integer.MAX_VALUE - 3
        const val VIEW_TYPE_NO_VIEW = Integer.MAX_VALUE - 3
    }

    fun setOnLoadListener(onLoadListener: OnLoadListener) {
        mOnLoadListener = onLoadListener
    }

    interface OnLoadListener {
        fun onRetry()
        fun onLoadMore()
    }

}