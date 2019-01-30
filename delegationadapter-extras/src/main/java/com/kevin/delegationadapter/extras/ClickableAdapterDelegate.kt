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

/**
 * ClickableAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 23:28:37
 *         Major Function：**Clickable ViewHolder Delegate**
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class ClickableAdapterDelegate<T, VH : RecyclerView.ViewHolder> : AdapterDelegate<T, VH>, View.OnClickListener, View.OnLongClickListener {

    constructor()

    constructor(tag: String) : super(tag)

    override fun onBindViewHolder(holder: VH, position: Int, item: T) {
        if (clickable(position) || longClickable(position)) {

            holder.itemView.setTag(R.id.tag_clickable_adapter_delegate_holder, holder)
            holder.itemView.setTag(R.id.tag_clickable_adapter_delegate_data, item)

            if (clickable(position)) {
                holder.itemView.setOnClickListener(this)
            }

            if (clickable(position)) {
                holder.itemView.setOnLongClickListener(this)
            }
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onClick(view: View) {
        val holder = view.getTag(R.id.tag_clickable_adapter_delegate_holder) as VH
        val item = view.getTag(R.id.tag_clickable_adapter_delegate_data) as T
        val position = getPosition(holder)
        if (position == RecyclerView.NO_POSITION) {
            // ignore
            return
        }

        if (clickable(position)) {
            onItemClick(view, item, position)
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun onLongClick(view: View): Boolean {
        val holder = view.getTag(R.id.tag_clickable_adapter_delegate_holder) as VH
        val item = view.getTag(R.id.tag_clickable_adapter_delegate_data) as T
        val position = getPosition(holder)
        if (position == RecyclerView.NO_POSITION) {
            // ignore
            return false
        }

        return if (longClickable(position)) {
            onItemLongClick(view, item, position)
        } else false
    }

    /**
     * Whether the adapter item can click
     *
     * @param position
     * @return
     */
    open fun clickable(position: Int) = true

    /**
     * Whether the adapter item can long click
     *
     * @param position
     * @return
     */
    open fun longClickable(position: Int) = true

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
    open fun onItemLongClick(view: View, item: T, position: Int) = false

    /**
     * Get the position of ViewHolder
     *
     * @param holder
     * @return
     */
    private fun getPosition(holder: VH) = holder.adapterPosition
}
