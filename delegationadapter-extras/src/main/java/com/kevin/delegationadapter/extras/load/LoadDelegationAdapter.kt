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

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.ViewGroup
import com.kevin.delegationadapter.AdapterDelegate
import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter
import java.lang.ref.WeakReference

/**
 * LoadDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-11 23:30:43
 *         Major Function：<b>DelegationAdapter with load more</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class LoadDelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : SpanDelegationAdapter(hasConsistItemType) {

    private var loadDelegate: AdapterDelegate<*, *>? = null
    private val scrollListener: LoadScrollListener
    private var onLoadListener: OnLoadListener? = null
    private var enabledLoad = false
    private var loading: Boolean = false

    private val loadFooter: LoadFooter = LoadFooter(LOAD_STATE_LOADING)

    private var viewHolderRef: WeakReference<RecyclerView.ViewHolder>? = null

    init {
        scrollListener = object : LoadScrollListener() {

            override fun loadMore() {
                if (loadFooter.loadState == LOAD_STATE_FAILED) {
                    setLoading()
                }

                if (!loading
                        && enabledLoad
                        && onLoadListener != null
                        && loadFooter.loadState == LOAD_STATE_LOADING) {
                    loading = true
                    onLoadListener?.onLoadMore()
                }
            }

            override fun hasStateView(): Boolean {
                return hasLoadStateView()
            }
        }
    }

    fun setLoadDelegate(delegate: AdapterDelegate<*, *>): LoadDelegationAdapter {
        addDelegate(delegate)
        this.loadDelegate = delegate
        return this
    }

    override fun getItemCount(): Int =
            if (hasLoadStateView() && enabledLoad)
                super.getItemCount() + 1
            else
                super.getItemCount()

    override fun getItem(position: Int): Any {
        return if (hasLoadStateView() && isLoadStateItem(position))
            loadFooter
        else
            super.getItem(position)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        if (hasLoadStateView() && isLoadStateItem(holder.layoutPosition)) {
            val layoutParams = holder.itemView.layoutParams
            // When StaggeredGridLayoutManager, the Load View takes up one line;
            if (layoutParams != null && layoutParams is StaggeredGridLayoutManager.LayoutParams) {
                layoutParams.isFullSpan = true
            }
        } else {
            super.onViewAttachedToWindow(holder)
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerView.addOnScrollListener(scrollListener)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerView.removeOnScrollListener(scrollListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (hasLoadStateView() && loadDelegate == delegatesManager.getDelegate(viewType)) {
            val viewHolder = super.onCreateViewHolder(parent, viewType)
            this.viewHolderRef = WeakReference(viewHolder)
            return viewHolder
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    fun isLoading(): Boolean = loading

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    fun reset() {
        setLoading()
    }

    fun retry() {
        scrollListener.loadMore()
    }

    private fun setLoading() {
        loadFooter.loadState = LOAD_STATE_LOADING
        loading = false
        if (!enabledLoad) {
            enabledLoad = true
            notifyDataSetChanged()
        } else if (hasLoadStateView()) {
            notifyLoadItemChanged(itemCount - 1)
        }
    }

    fun setLoadFailed() {
        loadFooter.loadState = LOAD_STATE_FAILED
        loading = false
        enabledLoad = true
        if (hasLoadStateView()) {
            notifyLoadItemChanged(itemCount - 1)
        }
    }

    fun setLoadCompleted() {
        loadFooter.loadState = LOAD_STATE_COMPLETED
        loading = false
        enabledLoad = true
        if (hasLoadStateView()) {
            notifyLoadItemChanged(itemCount - 1)
        }
    }

    private fun notifyLoadItemChanged(position: Int) {
        val viewHolder = viewHolderRef?.get()
        if (viewHolder != null) {
            @Suppress("UNCHECKED_CAST")
            val adapterDelegate = loadDelegate as AdapterDelegate<Any?, RecyclerView.ViewHolder>
            adapterDelegate.onBindViewHolder(viewHolder, position, loadFooter)
        } else {
            notifyDataSetChanged()
        }
    }

    fun disableLoad() {
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
     * Returns whether is the load state item.
     *
     * @param position
     */
    private fun isLoadStateItem(position: Int): Boolean {
        return enabledLoad && position == itemCount - 1
    }

    fun setOnLoadListener(listener: OnLoadListener) {
        this.enabledLoad = true
        this.onLoadListener = listener
    }

    interface OnLoadListener {
        fun onLoadMore()
    }

    companion object {
        const val LOAD_STATE_LOADING = 1
        const val LOAD_STATE_COMPLETED = 2
        const val LOAD_STATE_FAILED = 4
    }

}