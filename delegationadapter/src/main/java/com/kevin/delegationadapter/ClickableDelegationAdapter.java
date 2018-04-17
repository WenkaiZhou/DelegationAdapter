package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

/**
 * ClickableDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-06 23:28:37
 *         Major Function：<b>可点击的 ViewHolder Delegate</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public abstract class ClickableDelegationAdapter<T, VH extends RecyclerView.ViewHolder> extends AdapterDelegate<T, VH> {

    public ClickableDelegationAdapter() {
        super();
    }

    public ClickableDelegationAdapter(String tag) {
        super(tag);
    }

    @Override
    protected void onBindViewHolder(@NonNull final T item, int position, @NonNull final VH holder, @NonNull List<Object> payloads) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = getPosition(holder);
                // If the item can click
                if (clickable(position)) {
                    if (item instanceof ItemData) {
                        onItemClick(v, (T) ((ItemData) item).getData(), getPosition(holder));
                    } else {
                        onItemClick(v, (T) item, getPosition(holder));
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
                        return onItemLongClick(v, (T) item, getPosition(holder));
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
     * @param data
     * @param position
     */
    public void onItemClick(View view, T data, int position) {
        // do nothing
    }

    /**
     * Called when a item view has been clicked and held.
     *
     * @param view
     * @param data
     * @param position
     * @return
     */
    public boolean onItemLongClick(View view, T data, int position) {
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
