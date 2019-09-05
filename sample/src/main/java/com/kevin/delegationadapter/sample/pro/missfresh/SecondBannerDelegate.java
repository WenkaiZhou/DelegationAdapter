/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.AdapterDelegate;
import com.kevin.delegationadapter.sample.R;

/**
 * TextAdapterDelegate
 *
 * @author zwenkai@foxmail.com, Created on 2018-04-27 18:18:27
 * Major Function：<b></b>
 * <p/>
 * 注:如果您修改了本类请填写以下内容作为记录，如非本人操作劳烦通知，谢谢！！！
 * @author mender，Modified Date Modify Content:
 */

public class SecondBannerDelegate extends AdapterDelegate<Products.CellItem, SecondBannerDelegate.ViewHolder> {

    @Override
    public boolean isForViewType(Products.CellItem item, int position) {
        return item.cellType == Products.TYPE_SECOND_BANNER;
    }

    @Override
    public int getItemViewType() {
        return Products.TYPE_SECOND_BANNER;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_second_banner, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, Products.CellItem item) {
        Context context = holder.itemView.getContext();
        Glide.with(context)
                .load(item.secondBanner.path)
                .into(holder.image);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_image);
        }
    }
}
