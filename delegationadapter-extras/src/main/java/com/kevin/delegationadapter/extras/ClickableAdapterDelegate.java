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
package com.kevin.delegationadapter.extras;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.ItemData;

/**
 * ClickableAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 23:28:37
 *         Major Function：<b>Clickable ViewHolder Delegate</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public abstract class ClickableAdapterDelegate<T, VH extends RecyclerView.ViewHolder> extends AdapterDelegate<T, VH> {

    public ClickableAdapterDelegate() {
    }

    public ClickableAdapterDelegate(String tag) {
        super(tag);
    }

    @Override
    public void onBindViewHolder(final VH holder, int position, final T item) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition(holder);
                // If the item can click
                if (clickable(position)) {
                    if (item instanceof ItemData) {
                        onItemClick(v, (T) ((ItemData) item).getData(), getPosition(holder));
                    } else {
                        onItemClick(v, item, getPosition(holder));
                    }
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int position = getPosition(holder);
                // If the item can long click
                if (longClickable(position)) {
                    if (item instanceof ItemData) {
                        return onItemLongClick(v, (T) ((ItemData) item).getData(), getPosition(holder));
                    } else {
                        return onItemLongClick(v, item, getPosition(holder));
                    }
                }
                return false;
            }
        });
    }

    /**
     * Whether the adapter item can click
     *
     * @param position
     * @return
     */
    protected boolean clickable(int position) {
        return true;
    }

    /**
     * Whether the adapter item can long click
     *
     * @param position
     * @return
     */
    protected boolean longClickable(int position) {
        return true;
    }

    /**
     * Called when a item view has been clicked.
     *
     * @param view
     * @param item
     * @param position
     */
    public void onItemClick(View view, T item, int position) {
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
    public boolean onItemLongClick(View view, T item, int position) {
        // do nothing
        return false;
    }

    /**
     * Get the position of ViewHolder
     *
     * @param holder
     * @return
     */
    public final int getPosition(VH holder) {
        return holder.getAdapterPosition();
    }
}
