package com.kevin.delegationadapter.extras;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.ItemData;

/**
 * ClickableDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 23:28:37
 *         Major Function：<b>可点击的 ViewHolder Delegate</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public abstract class ClickableDelegationAdapter<T, VH extends RecyclerView.ViewHolder> extends AdapterDelegate<T, VH> {

    public ClickableDelegationAdapter() {
    }

    public ClickableDelegationAdapter(String tag) {
        super(tag);
    }

    @Override
    protected void onBindViewHolder(final VH holder, int position, final T item) {
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
