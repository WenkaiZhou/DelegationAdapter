/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.multitype.chat.common;

import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.kevin.delegationadapter.extras.load.LoadAdapterDelegate;
import com.kevin.delegationadapter.extras.load.LoadDelegationAdapter;
import com.kevin.delegationadapter.extras.load.LoadViewHolder;
import com.kevin.delegationadapter.sample.R;

/**
 * ChatLoadAdapterDelegate
 *
 * @author zhouwenkai@baidu.com, Created on 2019-03-09 12:55:19
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

    @Override
    public void onBindViewHolder(LoadViewHolder holder, int position) {
        if (holder.getItemViewType() == LoadDelegationAdapter.VIEW_TYPE_LOAD_FAILED) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.print(1);
                }
            });
        }
    }
}
