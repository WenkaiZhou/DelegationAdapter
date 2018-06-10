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
package com.kevin.delegationadapter.extras.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kevin.delegationadapter.ItemData;
import com.kevin.delegationadapter.extras.span.SpanAdapterDelegate;

/**
 * BindingDelegationAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-11 10:29:52
 *         Major Function：<b>Binding Delegation Adapter</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

public abstract class BindingAdapterDelegate<T> extends SpanAdapterDelegate<T, BindingViewHolder> {

    public BindingAdapterDelegate() {
    }

    public BindingAdapterDelegate(String tag) {
        super(tag);
    }

    @Override
    protected final BindingViewHolder onCreateViewHolder(ViewGroup parent) {
        ViewDataBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()),
                getLayoutRes(),
                parent,
                false);
        BindingViewHolder holder = new BindingViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    protected void onBindViewHolder(BindingViewHolder holder, int position, T item) {
        super.onBindViewHolder(holder, position, item);
        if (item instanceof ItemData) {
            setVariable(holder.getBinding(), (T) ((ItemData) item).getData(), position);
        } else {
            setVariable(holder.getBinding(), item, position);
        }
        holder.getBinding().executePendingBindings();
    }

    /**
     * get layout resource
     *
     * @return
     */
    @LayoutRes
    public abstract int getLayoutRes();

    /**
     * Set variable data
     * <p>
     * example：
     * binding.setVariable(BR.viewModel, mViewModel);
     *
     * @param binding
     * @param item
     * @param position
     */
    public abstract void setVariable(ViewDataBinding binding, T item, int position);
}
