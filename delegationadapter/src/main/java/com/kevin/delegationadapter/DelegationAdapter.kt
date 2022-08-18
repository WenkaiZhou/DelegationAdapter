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

/**
 * DelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：<b>Delegation Adapter</b>
 *
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
open class DelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : AbsDelegationAdapter(AdapterDelegatesManager(hasConsistItemType)) {

    private var dataItems = ArrayList<Any>()
    private var headerItems = ArrayList<Any>()
    private var footerItems = ArrayList<Any>()

    val dataCount: Int
        get() = dataItems.size

    val headerCount: Int
        get() = headerItems.size

    val footerCount: Int
        get() = footerItems.size

    fun setHeaderItem(headerItem: Any?) {
        if (headerItem == null) {
            return
        }
        headerItems.clear()
        headerItems.add(headerItem)
        notifyDataSetChanged()
    }

    fun setHeaderItems(headerItems: List<Any?>?) {
        if (headerItems == null) {
            return
        }
        this.headerItems.clear()
        this.headerItems.addAll(headerItems.filterNotNull())
        notifyDataSetChanged()
    }

    fun addHeaderItem(headerItem: Any?) {
        addHeaderItem(headerCount, headerItem)
    }

    fun addHeaderItem(position: Int = headerCount, headerItem: Any?) {
        if (headerItem == null) {
            return
        }
        headerItems.add(position, headerItem)
        notifyItemRangeInserted(position, 1)
    }

    fun addHeaderItems(headerItems: List<Any?>?) {
        addHeaderItems(headerCount, headerItems)
    }

    fun addHeaderItems(position: Int = headerCount, headerItems: List<Any?>?) {
        if (headerItems == null) {
            return
        }
        val headerItems = headerItems.filterNotNull()
        this.headerItems.addAll(position, headerItems)
        notifyItemRangeInserted(position, headerItems.size)
    }

    fun setFooterItem(footerItem: Any?) {
        if (footerItem == null) {
            return
        }
        footerItems.clear()
        footerItems.add(footerItem)
        notifyDataSetChanged()
    }

    fun setFooterItems(footerItems: List<Any?>?) {
        if (footerItems == null) {
            return
        }
        this.footerItems.clear()
        this.footerItems.addAll(footerItems.filterNotNull())
        notifyDataSetChanged()
    }

    fun addFooterItem(footerItem: Any?) {
        addFooterItem(footerCount, footerItem)
    }

    fun addFooterItem(position: Int, footerItem: Any?) {
        if (footerItem == null) {
            return
        }
        footerItems.add(position, footerItem)
        notifyItemRangeInserted(headerCount + dataCount + position, 1)
    }

    fun addFooterItems(footerItems: List<Any?>?) {
        addFooterItems(footerCount, footerItems)
    }

    fun addFooterItems(position: Int, footerItems: List<Any?>?) {
        if (footerItems == null) {
            return
        }
        val footerItems = footerItems.filterNotNull()
        this.footerItems.addAll(position, footerItems)
        notifyItemRangeInserted(headerCount + dataCount + position, footerItems.size)
    }

    fun setDataItems(dataItems: List<Any?>?) {
        if (dataItems == null) {
            return
        }
        this.dataItems.clear()
        this.dataItems.addAll(dataItems.filterNotNull())
        notifyDataSetChanged()
    }

    fun addDataItem(dataItem: Any?) {
        addDataItem(dataCount, dataItem)
    }

    fun addDataItem(position: Int = dataCount, dataItem: Any?) {
        if (dataItem == null) {
            return
        }
        dataItems.add(position, dataItem)
        notifyItemRangeInserted(headerCount + position, 1)
    }

    fun addDataItems(dataItems: List<Any?>?) {
        addDataItems(dataCount, dataItems)
    }

    fun addDataItems(position: Int = dataCount, dataItems: List<Any?>?) {
        if (dataItems == null) {
            return
        }
        val dataItems = dataItems.filterNotNull()
        this.dataItems.addAll(position, dataItems)
        notifyItemRangeInserted(headerCount + position, dataItems.size)
    }

    fun moveDataItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < headerCount || toPosition < headerCount || fromPosition > headerCount + dataItems.size - 1 || toPosition > headerCount + dataItems.size - 1) {
            return
        }
        dataItems.add(toPosition - headerCount, dataItems.removeAt(fromPosition - headerCount))
        notifyItemMoved(fromPosition, toPosition)
    }

    fun swapDataItem(fromPosition: Int, toPosition: Int) {
        if (fromPosition < headerCount || toPosition < headerCount || fromPosition > headerCount + dataItems.size - 1 || toPosition > headerCount + dataItems.size - 1) {
            return
        }
        dataItems[fromPosition - headerCount] = dataItems.set(toPosition - headerCount, dataItems[fromPosition - headerCount])
        notifyItemMoved(fromPosition, toPosition)
    }

    @JvmOverloads
    fun removeDataItem(dataItem: Any?, tag: String = AdapterDelegate.DEFAULT_TAG) {
        if (dataItem == null) {
            return
        }

        val indexes = mutableListOf<Int>()
        dataItems.forEachIndexed { index, item ->
            if (item is ItemData && dataItem === item.data && tag == item.tag) {
                indexes.add(index)
            } else if (dataItem === item) {
                indexes.add(index)
            }
        }

        indexes.forEach { index ->
            removeDataItemAt(index)
        }

    }

    @JvmOverloads
    fun removeDataItemAt(position: Int, itemCount: Int = 1) {
        for (i in 0 until itemCount) {
            dataItems.removeAt(position)
        }
        notifyItemRangeRemoved(headerCount + position, itemCount)
    }

    @JvmOverloads
    fun updateDataItem(dataItem: Any?, tag: String = AdapterDelegate.DEFAULT_TAG) {
        if (dataItem == null) {
            return
        }

        dataItems.forEachIndexed { index, item ->
            if (item is ItemData && dataItem == item.data && tag == item.tag) {
                notifyItemChanged(headerCount + index)
            } else if (dataItem == item) {
                notifyItemChanged(headerCount + index)
            }
        }
    }

    fun getDataItems() = dataItems

    fun getHeaderItems() = headerItems

    fun getFooterItems() = footerItems

    override fun getItem(position: Int): Any {
        if (position < headerCount) {
            return headerItems[position]
        }

        var offsetPosition = position - headerCount
        if (offsetPosition < dataCount) {
            return dataItems[offsetPosition]
        }

        offsetPosition -= dataCount
        return footerItems[offsetPosition]
    }

    override fun getItemCount() = headerCount + dataCount + footerCount

    fun clearData() = dataItems.clear()

    fun clearHeader() = headerItems.clear()

    fun clearFooter() = footerItems.clear()

    fun clearAllData() {
        clearData()
        clearHeader()
        clearFooter()
    }

}
