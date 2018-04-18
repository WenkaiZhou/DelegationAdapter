package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * AbsDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：<b></b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public abstract class AbsDelegationAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private AdapterDelegatesManager<VH> mDelegatesManager;

    public AbsDelegationAdapter() {
        this(new AdapterDelegatesManager());
    }

    public AbsDelegationAdapter(@NonNull AdapterDelegatesManager delegatesManager) {
        if (delegatesManager == null) {
            throw new NullPointerException("AdapterDelegatesManager is null.");
        }
        this.mDelegatesManager = delegatesManager;
    }

    /**
     * Add an Adapter Delegate
     *
     * @param delegate
     */
    public void addDelegate(AdapterDelegate delegate) {
        addDelegate(delegate, delegate.getTag());
    }

    /**
     * Add an Adapter Delegate with tag, the role of tag is to distinguish Adapters with the
     * same data type.
     *
     * @param delegate
     * @param tag
     */
    public void addDelegate(AdapterDelegate delegate, String tag) {
        delegate.setTag(tag);
        mDelegatesManager.addDelegate(delegate, tag);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return mDelegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        mDelegatesManager.onBindViewHolder(holder, position, getItem(position));
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        mDelegatesManager.onBindViewHolder(holder, position, payloads, getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return mDelegatesManager.getItemViewType(getItem(position), position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        mDelegatesManager.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        return mDelegatesManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        mDelegatesManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        mDelegatesManager.onViewDetachedFromWindow(holder);
    }

    protected abstract Object getItem(int position);
}
