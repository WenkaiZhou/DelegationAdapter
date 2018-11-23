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

import android.support.v4.util.SparseArrayCompat
import android.support.v7.widget.RecyclerView
import android.util.SparseArray
import android.view.ViewGroup

import java.lang.reflect.ParameterizedType
import java.util.ArrayList

/**
 * AdapterDelegatesManager
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:24:58
 * Major Function：**This class is the element that ties [RecyclerView.Adapter]
 * together with [AdapterDelegate].
 *
 *
 * So you have to add / register your [AdapterDelegate]s to this manager by calling
 * [.addDelegate]
 * /b>
 *
 *
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 ** */

class AdapterDelegatesManager {

    private val dataTypeWithTags = SparseArray<String>()
    private val delegates = SparseArrayCompat<AdapterDelegate<Any, RecyclerView.ViewHolder>>()
    var fallbackDelegate: AdapterDelegate<Any, RecyclerView.ViewHolder>? = null

    /**
     * Add a Delegate
     *
     * @param delegate
     */
    fun addDelegate(delegate: AdapterDelegate<*, *>, tag: String): AdapterDelegatesManager {
        val superclass = delegate.javaClass.genericSuperclass
        try {
            val clazz = (superclass as ParameterizedType).actualTypeArguments[0] as Class<*>
            val typeWithTag = getTypeWithTag(clazz, tag)

            val viewType = delegates.size()
            // Save the delegate to the collection;
            delegates.put(viewType, delegate as AdapterDelegate<Any, RecyclerView.ViewHolder>?)
            // Save the index of the delegate to the collection;
            dataTypeWithTags.put(viewType, typeWithTag)
        } catch (e: Exception) {
            // Has no generics or generics not correct.
            throw IllegalArgumentException("Please set the correct generic parameters on ${delegate.javaClass.name}.")
        }

        return this
    }

    fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder {
        val delegate = getDelegate(viewType)
                ?: throw NullPointerException("No AdapterDelegate added for ViewType $viewType")

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
        delegate.onBindViewHolder(holder, position, item)
    }

    fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>?, item: Any) {
        val viewType = holder.itemViewType
        val delegate = getDelegate(viewType)
                ?: throw NullPointerException("No delegate found for item at position = "
                        + position
                        + " for viewType = "
                        + viewType)
        delegate.onBindViewHolder(holder, position, payloads, item)
    }

    /**
     * Return the view type of the item.
     *
     * @param item
     * @param position
     * @return
     */
    fun getItemViewType(item: Any, position: Int): Int {
        if (item == null) {
            throw NullPointerException("Item data source is null.")
        }

        val clazz = getTargetClass(item)
        val tag = getTargetTag(item)

        val typeWithTag = getTypeWithTag(clazz, tag!!)
        val indexList = indexesOfValue(dataTypeWithTags, typeWithTag)
        if (indexList.size > 0) {
            for (index in indexList) {
                val delegate = delegates.valueAt(index)
                if (delegate != null
                        && delegate.tag == tag
                        && delegate.isForViewType(item, position)) {
                    return index
                }
            }
        }

        // If has not add the AdapterDelegate for data type, returns the largest viewType + 1.
        if (fallbackDelegate != null) {
            return delegates.size()
        }

        throw NullPointerException("No AdapterDelegate added that matches position = $position item = $item in data source.")
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
            delegate.onAttachedToRecyclerView(recyclerView)
        }
    }

    fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        for (i in 0 until delegates.size()) {
            val delegate = delegates.get(delegates.keyAt(i))
            delegate.onDetachedFromRecyclerView(recyclerView)
        }
    }

    fun getDelegate(viewType: Int): AdapterDelegate<Any, RecyclerView.ViewHolder>? {
        return delegates.get(viewType, fallbackDelegate)
    }

    /**
     * Returns the class name with tag;
     *
     * @param clazz
     * @param tag
     * @return
     */
    private fun getTypeWithTag(clazz: Class<*>, tag: String): String {
        return if (tag.isEmpty())
            clazz.name
        else
            clazz.name + ":" + tag
    }

    /**
     * Returns the target class name
     *
     * @return
     */
    private fun getTargetClass(data: Any): Class<*> {
        return if (data is ItemData)
            data.data!!.javaClass
        else
            data.javaClass
    }

    /**
     * Returns the target tag
     *
     * @param data
     * @return
     */
    private fun getTargetTag(data: Any): String? {
        return if (data is ItemData)
            data.tag
        else AdapterDelegate.DEFAULT_TAG
    }

    /**
     * Returns all indexes for the specified value
     *
     * @param array
     * @param value
     * @return
     */
    private fun indexesOfValue(array: SparseArray<String>, value: String): ArrayList<Int> {
        val indexes = ArrayList<Int>()

        for (i in 0 until array.size()) {
            if (value == array.valueAt(i)) {
                indexes.add(i)
            }
        }
        return indexes
    }
}
