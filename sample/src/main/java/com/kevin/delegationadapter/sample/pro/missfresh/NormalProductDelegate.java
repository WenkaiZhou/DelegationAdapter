/*
 * Copyright (C) 2019 Baidu, Inc. All Rights Reserved.
 */
package com.kevin.delegationadapter.sample.pro.missfresh;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kevin.delegationadapter.extras.span.SpanAdapterDelegate;
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

public class NormalProductDelegate extends SpanAdapterDelegate<Products.CellItem, NormalProductDelegate.ViewHolder> {

    @Override
    public boolean isForViewType(Products.CellItem item, int position) {
        return item.cellType == Products.TYPE_NORMAL_PRODUCT;
    }

    @Override
    public int getItemViewType() {
        return Products.TYPE_NORMAL_PRODUCT;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_normal_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position, final Products.CellItem item) {
        Products.NormalProduct product = item.normalProduct;
        Context context = holder.itemView.getContext();
        Glide.with(context)
                .load(product.image)
                .into(holder.image);
        holder.name.setText(product.name);
        holder.subTitle.setText(product.subtitle);
        holder.price.setText("￥" + ((float) product.price / 100));
        Glide.with(context)
                .load(product.cartImage)
                .into(holder.cart);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        TextView subTitle;
        TextView price;
        ImageView cart;

        public ViewHolder(View view) {
            super(view);
            image = view.findViewById(R.id.iv_image);
            name = view.findViewById(R.id.tv_name);
            subTitle = view.findViewById(R.id.tv_sub_title);
            price = view.findViewById(R.id.tv_price);
            cart = view.findViewById(R.id.iv_cart);
        }
    }
}
