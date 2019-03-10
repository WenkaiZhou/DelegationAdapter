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
package com.kevin.delegationadapter.sample.multitype.chat.common;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kevin.delegationadapter.extras.load.LoadAdapterDelegate;
import com.kevin.delegationadapter.extras.load.LoadDelegationAdapter;
import com.kevin.delegationadapter.extras.load.LoadViewHolder;
import com.kevin.delegationadapter.sample.R;

/**
 * ChatLoadAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2019-03-09 12:55:19
 * Major Function：<b></b>
 * <p/>
 * Note: If you modify this class please fill in the following content as a record.
 * @author mender，Modified Date Modify Content:
 */
public class ChatLoadAdapterDelegate extends LoadAdapterDelegate {

    @NonNull
    @Override
    public LoadViewHolder onCreateLoadViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = null;
        switch (viewType) {
            case LoadDelegationAdapter.VIEW_TYPE_LOAD_MORE:
                view = layoutInflater.inflate(R.layout.layout_load_more_footer, parent, false);
                break;
            case LoadDelegationAdapter.VIEW_TYPE_NO_MORE:
                view = layoutInflater.inflate(R.layout.layout_no_more_footer, parent, false);
                break;
            case LoadDelegationAdapter.VIEW_TYPE_LOAD_FAILED:
                view = layoutInflater.inflate(R.layout.layout_load_fail_footer, parent, false);
                break;
            default:
                // Can't reach;
                break;
        }
        return new LoadViewHolder(view);
    }
}
