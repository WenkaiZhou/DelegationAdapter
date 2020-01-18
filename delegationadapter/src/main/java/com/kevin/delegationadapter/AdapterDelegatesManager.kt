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

import android.util.SparseArray
import android.view.ViewGroup
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.widget.RecyclerView

import java.lang.reflect.ParameterizedType

/**
 * AdapterDelegatesManager
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:24:58
 *         Major Function：<b>This class is the element that ties [RecyclerView.Adapter]
 *         together with [AdapterDelegate].
 *
 *         So you have to add delegate your [AdapterDelegate]s to this manager by calling
 *         [.addDelegate]
 *         /b>
 *
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
open class AdapterDelegatesManager(private val hasConsistItemType: Boolean) {

    private val dataTypeWithTags = SparseArray<String>()
    private val delegates = SparseArrayCompat<AdapterDelegate<Any, RecyclerView.ViewHolder>>()
    var fallbackDelegate: AdapterDelegate<Any, RecyclerView.ViewHolder>? = null

    /**
     * Add a Delegate
     *
     * @param delegate
     * @param tag
     */
    fun addDelegate(delegate: AdapterDelegate<*, *>, tag: String): AdapterDelegatesManager {
        val superclass = delegate.javaClass.genericSuperclass
        try {
            val clazz = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
            val typeWithTag = typeWithTag(clazz, tag)
            val viewType = if (hasConsistItemType) {
                delegate.getItemViewType()
            } else {
                delegates.size()
            }
            // Save the delegate to the collection;
            @Suppress("UNCHECKED_CAST")
            delegates.put(viewType, delegate as AdapterDelegate<Any, RecyclerView.ViewHolder>)
            // Save the index of the delegate to the collection;
            dataTypeWithTags.put(viewType, typeWithTag)
        } catch (e: Exception) {
            // Has no generics or generics not correct.
            throw IllegalArgumentException("Please set the correct generic parameters on ${delegate.javaClass.name}.")
        }

        return this
    }

    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val delegate = getDelegate(viewType)
                ?: throw NullPointerException("No AdapterDelegate added for ViewType $viewType")

        // It`s can be null when in Java.
        return delegate.onCreateViewHolder(parent)
                ?: throw NullPointerException("ViewHolder returned from AdapterDelegate ${delegate.javaClass}"
                        + " for ViewType = $viewType is null!")
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: Any) {
        val viewType = holder.itemViewType
        val delegate = getDelegate(viewType)
                ?: throw NullPointerException("No delegate found for item at position = "
                        + position
                        + " for viewType = "
                        + viewType)
        delegate.onBindViewHolder(holder, position, targetItem(item))
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>, item: Any) {
        val viewType = holder.itemViewType
        val delegate = getDelegate(viewType)
                ?: throw NullPointerException("No delegate found for item at position = $position for viewType = $viewType")
        delegate.onBindViewHolder(holder, position, payloads, targetItem(item))
    }

    /**
     * Returns the view type of the item.
     *
     * @param item
     * @param position
     * @return
     */
    fun getItemViewType(item: Any, position: Int): Int {
        val clazz = targetItem(item).javaClass
        val tag = targetTag(item)

        val typeWithTag = typeWithTag(clazz, tag)
        val indexList = indexesOfValue(dataTypeWithTags, typeWithTag)
        indexList.forEach { index ->
            val delegate = delegates.valueAt(index)
            if (delegate?.tag == tag && delegate.isForViewType(targetItem(item), position)) {
                return if (hasConsistItemType) {
                    delegate.getItemViewType()
                } else {
                    index
                }
            }
        }

        // If has not add the AdapterDelegate for data type, returns the largest viewType + 1.
        if (fallbackDelegate != null) {
            return delegates.size()
        }

        throw NullPointerException("No AdapterDelegate added that matches position = $position item = ${targetItem(item)} in data source.")
    }

    fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate?.onViewRecycled(holder)
    }

    fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
        val delegate = getDelegate(holder.itemViewType)
        return delegate?.onFailedToRecycleView(holder) ?: false
    }

    fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate?.onViewAttachedToWindow(holder)
    }

    fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val delegate = getDelegate(holder.itemViewType)
        delegate?.onViewDetachedFromWindow(holder)
    }

    fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        for (i in 0 until delegates.size()) {
            val delegate = delegates.get(delegates.keyAt(i))
            delegate?.onAttachedToRecyclerView(recyclerView)
        }
    }

    fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        for (i in 0 until delegates.size()) {
            val delegate = delegates.get(delegates.keyAt(i))
            delegate?.onDetachedFromRecyclerView(recyclerView)
        }
    }

    fun getDelegateViewType(delegate: AdapterDelegate<*, *>): Int {
        @Suppress("UNCHECKED_CAST")
        val index = delegates.indexOfValue(delegate as AdapterDelegate<Any, RecyclerView.ViewHolder>)
        return if (index >= 0) {
            delegates.keyAt(index)
        } else -1
    }

    fun getDelegate(viewType: Int): AdapterDelegate<Any, RecyclerView.ViewHolder>? = delegates.get(viewType, fallbackDelegate)

    private val typeWithTag = { clazz: Class<*>, tag: String ->
        if (tag.isEmpty()) clazz.name else clazz.name + ":" + tag
    }

    /**
     * Returns the target item data.
     */
    private val targetItem = { data: Any ->
        if (data is ItemData) data.data else data
    }

    /**
     * Returns the target item tag.
     */
    private val targetTag = { data: Any ->
        if (data is ItemData) data.tag else AdapterDelegate.DEFAULT_TAG
    }

    /**
     * Returns all indexes for the specified value
     *
     * @param array
     * @param value
     * @return
     */
    private fun indexesOfValue(array: SparseArray<String>, value: String): MutableList<Int> {
        val indexes = mutableListOf<Int>()

        for (i in 0 until array.size()) {
            if (value == array.valueAt(i)) {
                indexes.add(i)
            }
        }
        return indexes
    }
}
