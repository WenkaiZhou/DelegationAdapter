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
package com.kevin.delegationadapter.extras.binding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

import com.kevin.delegationadapter.extras.ClickableAdapterDelegate

/**
 * BindingDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-11 10:29:52
 *         Major Function：**Binding Delegation Adapter**
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class BindingAdapterDelegate<T> : ClickableAdapterDelegate<T, BindingViewHolder> {

    /**
     * get layout resource
     *
     * @return
     */
    @get:LayoutRes
    abstract val layoutRes: Int

    constructor()

    constructor(tag: String) : super(tag)

    override fun onCreateViewHolder(parent: ViewGroup): BindingViewHolder {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
                LayoutInflater.from(parent.context),
                layoutRes,
                parent,
                false)
        val holder = BindingViewHolder(binding.root)
        holder.setBinding(binding)
        configureViewHolder(holder)
        return holder
    }

    override fun onBindViewHolder(holder: BindingViewHolder, position: Int, item: T) {
        super.onBindViewHolder(holder, position, item)
        setVariable(holder.getBinding(), item, position)
        holder.getBinding<ViewDataBinding>().executePendingBindings()
    }

    /**
     * configure ViewHolder
     *
     * @param holder
     */
    open fun configureViewHolder(holder: BindingViewHolder) {}

    /**
     * Set variable data
     *
     *
     * example：
     * binding.setVariable(BR.viewModel, mViewModel);
     *
     * @param binding
     * @param item
     * @param position
     */
    abstract fun setVariable(binding: ViewDataBinding, item: T, position: Int)
}
