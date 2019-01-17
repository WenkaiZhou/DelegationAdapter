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
package com.kevin.delegationadapter.extras.wrap

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.kevin.delegationadapter.AdapterDelegate
import com.kevin.delegationadapter.AdapterDelegatesManager
import com.kevin.delegationadapter.DelegationAdapter

/**
 * WrapDelegationAdapter
 *
 * @author zhouwenkai@baidu.com, Created on 2019-01-16 14:26:03
 *         Major Function：<b>WrapDelegationAdapter</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
class WrapDelegationAdapter : DelegationAdapter {

    constructor()

    constructor(delegatesManager: AdapterDelegatesManager) : super(delegatesManager)

    init {
        addDelegate(ViewAdapterDelegate())
    }

    fun addHeaderView(view: View) {
        addHeaderItem(ViewWrapper(view))
    }

    fun addFooterView(view: View) {
        addFooterItem(ViewWrapper(view))
    }

    internal class ViewWrapper(var view: View)

    internal class ViewAdapterDelegate : AdapterDelegate<ViewWrapper, RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
            val frameLayout = FrameLayout(parent.context)
            frameLayout.layoutParams = FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.MATCH_PARENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT)
            return object : RecyclerView.ViewHolder(FrameLayout(parent.context)) {}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, item: ViewWrapper) {
            if (holder.itemView is FrameLayout) {
                if (item.view.parent != null) {
                    (item.view.parent as ViewGroup).removeView(item.view)
                }
                (holder.itemView as FrameLayout).addView(item.view)
            }
        }

    }

}