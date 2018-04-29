package com.kevin.delegationadapter.extras.binding;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kevin.delegationadapter.ItemData;
import com.kevin.delegationadapter.extras.ClickableAdapterDelegate;

/**
 * BindingDelegationAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2018-04-11 10:29:52
 *         Major Function：<b>Binding Delegation Adapter</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public abstract class BindingAdapterDelegate<T> extends ClickableAdapterDelegate<T, BindingViewHolder> {

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
