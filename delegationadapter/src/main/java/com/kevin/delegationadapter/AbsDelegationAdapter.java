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
package com.kevin.delegationadapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * AbsDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-10 23:08:38
 *         Major Function：<b>The base DelegationAdapter</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public abstract class AbsDelegationAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    private AdapterDelegatesManager<VH> delegatesManager;

    public AbsDelegationAdapter() {
        this(new AdapterDelegatesManager());
    }

    public AbsDelegationAdapter(@NonNull AdapterDelegatesManager delegatesManager) {
        if (delegatesManager == null) {
            throw new NullPointerException("AdapterDelegatesManager is null.");
        }
        this.delegatesManager = delegatesManager;
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
        delegatesManager.addDelegate(delegate, tag);
    }

    public void setFallbackDelegate(AdapterDelegate delegate) {
        delegatesManager.setFallbackDelegate(delegate);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return delegatesManager.onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        delegatesManager.onBindViewHolder(holder, position, getItem(position));
    }

    @Override
    public void onBindViewHolder(VH holder, int position, List<Object> payloads) {
        onBindViewHolder(holder, position);
        delegatesManager.onBindViewHolder(holder, position, payloads, getItem(position));
    }

    @Override
    public int getItemViewType(int position) {
        return delegatesManager.getItemViewType(getItem(position), position);
    }

    @Override
    public void onViewRecycled(VH holder) {
        delegatesManager.onViewRecycled(holder);
    }

    @Override
    public boolean onFailedToRecycleView(VH holder) {
        return delegatesManager.onFailedToRecycleView(holder);
    }

    @Override
    public void onViewAttachedToWindow(VH holder) {
        delegatesManager.onViewAttachedToWindow(holder);
    }

    @Override
    public void onViewDetachedFromWindow(VH holder) {
        delegatesManager.onViewDetachedFromWindow(holder);
    }

    protected abstract Object getItem(int position);
}
