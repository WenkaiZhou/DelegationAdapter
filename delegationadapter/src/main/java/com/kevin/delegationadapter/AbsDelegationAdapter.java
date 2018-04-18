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
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
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
     * Add a Delegate
     *
     * @param delegate
     */
    public void addDelegate(AdapterDelegate delegate) {
        addDelegate(delegate, delegate.getTag());
    }

    /**
     * Add a Delegate
     *
     * @param delegate
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
        mDelegatesManager.onBindViewHolder(getItemData(position), position, holder);
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        mDelegatesManager.onBindViewHolder(getItemData(position), position, holder, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return mDelegatesManager.getItemViewType(getItemData(position), position);
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

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        mDelegatesManager.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        mDelegatesManager.onDetachedFromRecyclerView(recyclerView);
    }

    protected abstract Object getItemData(int position);
}
