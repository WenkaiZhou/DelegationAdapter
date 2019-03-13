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
package com.kevin.delegationadapter.extras.span

import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView

import com.kevin.delegationadapter.DelegationAdapter

/**
 * SpanDelegationAdapter
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-10 11:08:37
 *         Major Function：**DelegationAdapter with span**
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

open class SpanDelegationAdapter @JvmOverloads constructor(hasConsistItemType: Boolean = false) : DelegationAdapter(hasConsistItemType) {

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {

            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    val delegate = delegatesManager.getDelegate(getItemViewType(position))
                    return if (null != delegate && delegate is SpanAdapterDelegate) {
                        delegate.spanSize
                    } else {
                        layoutManager.spanCount
                    }
                }
            }
        }
    }
}
