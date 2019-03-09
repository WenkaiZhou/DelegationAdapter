/*
 * Copyright (c) 2018 Kevin zhou
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
package com.kevin.delegationadapter

import android.support.v7.widget.RecyclerView
import android.view.ViewGroup

/**
 * AbsDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：**The base DelegationAdapter**
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class AbsDelegationAdapter (protected var delegatesManager: AdapterDelegatesManager) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    /**
     * Add an Adapter Delegate with tag, the role of tag is to distinguish Adapters with the
     * same data type.
     *
     * @param delegate
     * @param tag
     */
    @JvmOverloads
    fun addDelegate(delegate: AdapterDelegate<*, *>, tag: String = delegate.tag): AbsDelegationAdapter {
        delegate.tag = tag
        delegatesManager.addDelegate(delegate, tag)
        return this
    }

    fun setFallbackDelegate(delegate: AdapterDelegate<*, *>): AbsDelegationAdapter {
        @Suppress("UNCHECKED_CAST")
        delegatesManager.fallbackDelegate = delegate as AdapterDelegate<Any, RecyclerView.ViewHolder>?
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return delegatesManager.onCreateViewHolder(parent, viewType)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        delegatesManager.onBindViewHolder(holder, position, getItem(position))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>?) {
        onBindViewHolder(holder, position)
        delegatesManager.onBindViewHolder(holder, position, payloads, getItem(position))
    }

    override fun getItemViewType(position: Int): Int {
        return delegatesManager.getItemViewType(getItem(position), position)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder?) {
        delegatesManager.onViewRecycled(holder)
    }

    override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder?): Boolean {
        return delegatesManager.onFailedToRecycleView(holder)
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder?) {
        delegatesManager.onViewAttachedToWindow(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder?) {
        delegatesManager.onViewDetachedFromWindow(holder)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        delegatesManager.onAttachedToRecyclerView(recyclerView)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView?) {
        delegatesManager.onDetachedFromRecyclerView(recyclerView)
    }

    abstract fun getItem(position: Int): Any
}
