/*
 * Copyright (C) 2018 Baidu, Inc. All Rights Reserved.
 */

package com.kevin.delegationadapter.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.ViewGroup;


import com.kevin.delegationadapter.ClickableDelegationAdapter;
import com.kevin.delegationadapter.ItemData;

import java.util.List;

/**
 * BindingDelegationAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-11 10:29:52
 *         Major Function：<b></b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public abstract class BindingAdapterDelegate<T> extends ClickableDelegationAdapter<T, BindingViewHolder> {

    public BindingAdapterDelegate() {
        super();
    }

    public BindingAdapterDelegate(String tag) {
        super(tag);
    }

    @Override
    protected BindingViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
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
    protected void onBindViewHolder(@NonNull T item, int position, @NonNull BindingViewHolder holder, @NonNull List<Object> payloads) {
        super.onBindViewHolder(item, position, holder, payloads);
        if (item instanceof ItemData) {
            setVariable(holder.getBinding(), (T) ((ItemData) item).getData());
            setVariable(holder.getBinding(), (T) ((ItemData) item).getData(), position);
        } else {
            setVariable(holder.getBinding(), item);
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
     */
    public abstract void setVariable(ViewDataBinding binding, T data);

    public void setVariable(ViewDataBinding binding, T data, int position) {

    }
}
