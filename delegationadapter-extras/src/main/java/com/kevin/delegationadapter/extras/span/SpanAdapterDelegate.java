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
package com.kevin.delegationadapter.extras.span;

import android.support.v7.widget.RecyclerView;

import com.kevin.delegationadapter.extras.ClickableAdapterDelegate;

/**
 * SpanAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-06-10 10:00:34
 *         Major Function：<b>AdapterDelegate with span</b>
 *         <p/>
 *         注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public abstract class SpanAdapterDelegate<T, VH extends RecyclerView.ViewHolder> extends ClickableAdapterDelegate<T, VH> {

    public SpanAdapterDelegate() {
    }

    public SpanAdapterDelegate(String tag) {
        super(tag);
    }

    protected int getSpanSize() {
        return 1;
    }

}
