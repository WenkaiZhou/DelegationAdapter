package com.kevin.delegationadapter.extras.load2

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.kevin.delegationadapter.AdapterDelegate
import com.kevin.delegationadapter.extras.load.ScrollListener
import com.kevin.delegationadapter.extras.span.SpanDelegationAdapter

// 2019-03-11 23:30:43

class Load2DelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : SpanDelegationAdapter(hasConsistItemType) {

    private var loadDelegate: AdapterDelegate<*, *>? = null
    private val scrollListener: ScrollListener
    private var onLoadListener: OnLoadListener? = null
    private var enabledLoad = false

    private var loading: Boolean = false

    private val loadFooter: LoadFooter = LoadFooter(LOAD_STATE_LOADING)

    init {
        scrollListener = object : ScrollListener() {

            override fun loadMore() {
                if (loadFooter.loadState == LOAD_TYPE_FAILED) {
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

    fun setLoadDelegate(delegate: AdapterDelegate<*, *>): Load2DelegationAdapter {
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
        recyclerView?.addOnScrollListener(scrollListener)
    }

    fun isLoading(): Boolean = loading

    fun setLoading(loading: Boolean) {
        this.loading = loading
    }

    fun reset() {
        setLoading()
    }

    private fun setLoading() {
        loadFooter.loadState = LOAD_STATE_LOADING
        loading = false
        if (!enabledLoad) {
            enabledLoad = true
            notifyDataSetChanged()
        } else {
            notifyItemChanged(itemCount)
        }
        // TODO remove
        notifyDataSetChanged()
    }

    fun setLoadFailed() {
        loadFooter.loadState = LOAD_TYPE_FAILED
        loading = false
        enabledLoad = true
        notifyItemChanged(itemCount)
        // TODO remove
        notifyDataSetChanged()
    }

    fun setLoadCompleted() {
        loadFooter.loadState = LOAD_STATE_COMPLETED
        loading = false
        enabledLoad = true
        notifyItemChanged(itemCount)
        // TODO remove
        notifyDataSetChanged()
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
     * Returns whether is the load state item. 加载状态条目
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
        const val LOAD_TYPE_FAILED = 4
    }

    class LoadFooter(var loadState: Int)
}