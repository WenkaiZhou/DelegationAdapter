/*
 * Copyright (c) 2019 Kevin zhou
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
package com.kevin.delegationadapter.extras.load

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * LoadAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-09 10:59:27
 *         Major Function：<b>LoadAdapterDelegate</b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
abstract class LoadAdapterDelegate {

    @get:LayoutRes
    abstract val loadingLayoutRes: Int

    @get:LayoutRes
    abstract val noMoreLayoutRes: Int

    @get:LayoutRes
    abstract val loadFailedLayoutRes: Int

    fun onCreateLoadViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        var view: View? = null
        when (viewType) {
            LoadDelegationAdapter.VIEW_TYPE_LOADING -> view = layoutInflater.inflate(loadingLayoutRes, parent, false)
            LoadDelegationAdapter.VIEW_TYPE_NO_MORE -> view = layoutInflater.inflate(noMoreLayoutRes, parent, false)
            LoadDelegationAdapter.VIEW_TYPE_LOAD_FAILED -> view = layoutInflater.inflate(loadFailedLayoutRes, parent, false)
            else -> {
                // Can't reach;
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {}
    }

    open fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {}
}