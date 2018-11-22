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

import java.util.ArrayList

/**
 * DelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 * Major Function：**Delegation Adapter**
 *
 *
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

open class DelegationAdapter : AbsDelegationAdapter {

    private var dataItems: MutableList<Any> = ArrayList()
    private var headerItems: MutableList<Any> = ArrayList()
    private var footerItems: MutableList<Any> = ArrayList()

    val dataCount: Int
        get() = dataItems.size

    val headerCount: Int
        get() = headerItems.size

    val footerCount: Int
        get() = footerItems.size

    constructor()

    constructor(delegatesManager: AdapterDelegatesManager) : super(delegatesManager)

    fun setHeaderItem(headerItem: Any) {
        headerItems.clear()
        headerItems.add(headerItem)
        notifyDataSetChanged()
    }

    fun setHeaderItems(headerItems: MutableList<Nothing>) {
        this.headerItems = headerItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addHeaderItem(headerItem: Any) {
        addHeaderItem(headerCount, headerItem)
    }

    fun addHeaderItem(position: Int, headerItem: Any) {
        headerItems.add(position, headerItem)
        notifyItemRangeInserted(position, 1)
    }

    fun addHeaderItems(headerItems: MutableList<Nothing>) {
        addHeaderItems(headerCount, headerItems)
    }

    fun addHeaderItems(position: Int, headerItems: MutableList<Nothing>) {
        this.headerItems.addAll(position, headerItems)
        notifyItemRangeInserted(position, headerItems.size)
    }

    fun setFooterItem(footerItem: Any) {
        footerItems.clear()
        footerItems.add(footerItem)
        notifyDataSetChanged()
    }

    fun setFooterItems(footerItems: MutableList<Nothing>) {
        this.footerItems = footerItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addFooterItem(footerItem: Any) {
        addFooterItem(footerCount, footerItem)
    }

    fun addFooterItem(position: Int, footerItem: Any) {
        footerItems.add(position, footerItem)
        notifyItemRangeInserted(headerCount + dataCount + position, 1)
    }

    fun addFooterItems(footerItems: MutableList<Nothing>) {
        addFooterItems(footerCount, footerItems)
    }

    fun addFooterItems(position: Int, footerItems: MutableList<Nothing>) {
        this.footerItems.addAll(position, listOf(footerItems))
        notifyItemRangeInserted(headerCount + dataCount + position, footerItems.size)
    }

    fun setDataItems(dataItems: MutableList<Nothing>) {
        this.dataItems = dataItems.toMutableList()
        notifyDataSetChanged()
    }

    fun addDataItem(item: Any) {
        addDataItem(dataCount, item)
    }

    fun addDataItem(position: Int, item: Any) {
        dataItems.add(position, item)
        notifyItemRangeInserted(headerCount + position, 1)
    }

    fun addDataItems(dataItems: MutableList<Nothing>) {
        addDataItems(dataCount, dataItems)
    }

    fun addDataItems(position: Int, dataItems: MutableList<Nothing>) {
        this.dataItems.addAll(position, dataItems)
        notifyItemRangeInserted(headerCount + position, dataItems.size)
    }

    fun moveDataItem(fromPosition: Int, toPosition: Int) {
        var toPosition = toPosition
        toPosition = if (fromPosition < toPosition) toPosition - 1 else toPosition
        dataItems.add(toPosition, dataItems.removeAt(fromPosition))
        notifyItemMoved(fromPosition, toPosition)
    }

    fun removeDataItem(dataItem: Any) {
        val index = dataItems.indexOf(dataItem)
        if (index != -1 && index <= dataCount) {
            removeDataItem(index)
        }
    }

    @JvmOverloads
    fun removeDataItem(position: Int, itemCount: Int = 1) {
        for (i in 0 until itemCount) {
            dataItems.removeAt(position)
        }
        notifyItemRangeRemoved(headerCount + position, itemCount)
    }

    fun getDataItems(): List<Any> {
        return dataItems
    }

    fun getHeaderItems(): List<Any> {
        return headerItems
    }

    fun getFooterItems(): List<Any> {
        return footerItems
    }

    override fun getItem(position: Int): Any {
        var position = position
        if (position < headerCount) {
            return headerItems[position]
        }

        position -= headerCount
        if (position < dataCount) {
            return dataItems[position]
        }

        position -= dataCount
        return footerItems[position]
    }

    override fun getItemCount(): Int {
        return headerCount + dataCount + footerCount
    }

    fun clearData() {
        dataItems.clear()
    }

    fun clearHeader() {
        headerItems.clear()
    }

    fun clearFooter() {
        footerItems.clear()
    }

    fun clearAllData() {
        clearData()
        clearHeader()
        clearFooter()
    }

}
