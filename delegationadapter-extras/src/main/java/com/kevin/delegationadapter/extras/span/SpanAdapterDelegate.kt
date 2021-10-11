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

import androidx.recyclerview.widget.RecyclerView
import com.kevin.delegationadapter.extras.ClickableAdapterDelegate

/**
 * SpanAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-10 10:00:34
 *         Major Function：<b>AdapterDelegate with span<b>
 *         <p/>
 *         Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */

abstract class SpanAdapterDelegate<T, VH : RecyclerView.ViewHolder> : ClickableAdapterDelegate<T, VH> {

    open var spanSize: Int = DEFAULT_SPAN_SIZE

    constructor()

    constructor(tag: String) : super(tag)

    companion object {
        const val DEFAULT_SPAN_SIZE = 1
    }

}
