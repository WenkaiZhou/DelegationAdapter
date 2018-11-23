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
package com.kevin.delegationadapter.extras

import android.support.v7.widget.RecyclerView
import android.view.View

import com.kevin.delegationadapter.AdapterDelegate
import com.kevin.delegationadapter.ItemData

/**
 * ClickableAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 23:28:37
 * Major Function：**Clickable ViewHolder Delegate**
 *
 *
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

abstract class ClickableAdapterDelegate<T, VH : RecyclerView.ViewHolder> : AdapterDelegate<T, VH> {

    constructor()

    constructor(tag: String) : super(tag)

    override fun onBindViewHolder(holder: VH, position: Int, item: T) {
        holder.itemView.setOnClickListener { v ->
            val position = getPosition(holder)
            // If the item can click
            if (clickable(position)) {
                if (item is ItemData) {
                    onItemClick(v, item.data as T, getPosition(holder))
                } else {
                    onItemClick(v, item, getPosition(holder))
                }
            }
        }

        holder.itemView.setOnLongClickListener(View.OnLongClickListener { v ->
            val position = getPosition(holder)
            // If the item can long click
            if (longClickable(position)) {
                return@OnLongClickListener if (item is ItemData) {
                    onItemLongClick(v, item.data as T, getPosition(holder))
                } else {
                    onItemLongClick(v, item, getPosition(holder))
                }
            }
            false
        })
    }

    /**
     * Whether the adapter item can click
     *
     * @param position
     * @return
     */
    open fun clickable(position: Int): Boolean {
        return true
    }

    /**
     * Whether the adapter item can long click
     *
     * @param position
     * @return
     */
    open fun longClickable(position: Int): Boolean {
        return true
    }

    /**
     * Called when a item view has been clicked.
     *
     * @param view
     * @param item
     * @param position
     */
    open fun onItemClick(view: View, item: T, position: Int) {
        // do nothing
    }

    /**
     * Called when a item view has been clicked and held.
     *
     * @param view
     * @param item
     * @param position
     * @return
     */
    open fun onItemLongClick(view: View, item: T, position: Int): Boolean {
        // do nothing
        return false
    }

    /**
     * Get the position of ViewHolder
     *
     * @param holder
     * @return
     */
    private fun getPosition(holder: VH): Int {
        return holder.adapterPosition
    }
}
